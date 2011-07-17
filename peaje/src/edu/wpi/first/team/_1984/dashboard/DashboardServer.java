/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team._1984.dashboard;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sid
 */
public class DashboardServer implements Runnable
{
    protected int port;
    
     /**
     * Listening socket. Normally, ServerSocketChannel would be used if the
     * server would be required to handle multiple requests. Since the dashboard
     * will only be servicing one robot, we don't need any multiplexing. We do,
     * however, need to use ServerSocketChannel to be able to create (or rather,
     * obtain) the SocketChannel for the client (documentation notes you can't
     * create a SocketChannel for a pre-existing socket).
     */
    protected ServerSocketChannel listen;

    /**
     * Client socket. This is a SocketChannel as this class supports
     * non-blocking IO (normally when a socket is read, it will block/hang until
     * information is received).
     */
    protected SocketChannel cli;

    /**
     * This is the wrapper class for the non-blocking IO function select(),
     * provided by most operating systems. select() is used to poll file
     * descriptors and check if their respective read/write/error buffers have
     * any data.
     */
    protected Selector sel;

    /**
     * Buffer which an outside class can access to see received messages.
     */
    protected LinkedBlockingQueue<String> recvBuf;

    /**
     * Listens on port p. The rest of this code uses 7450.
     *
     * @param p port to listen on.
     */
    public DashboardServer(int p) throws IOException
    {
        listen = ServerSocketChannel.open();
        sel    = Selector.open();

        recvBuf = new LinkedBlockingQueue<String>();

        (listen.socket()).bind(new InetSocketAddress(p));
    }

    public void run()
    {
        try
        {
            // We will only have one client, so... Blocking accept() call.
            cli = listen.accept();
            cli.configureBlocking(false);
            cli.register(sel, SelectionKey.OP_READ);
            
            for(;;)
            {
                sel.select(1);
                Set keys = sel.selectedKeys();
                Iterator i = keys.iterator();
                while(i.hasNext())
                {
                    SelectionKey key = (SelectionKey) i.next();
                    i.remove();
                    if(key.isReadable())
                    {
                        int rlen = 0;
                        SocketChannel tc = (SocketChannel) key.channel();
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        
                        // Disconnect, wait for reconnect.
                        if((rlen = tc.read(buf)) == -1)
                        {
                            key.cancel();
                            cli = null;
                            cli = listen.accept();
                            cli.configureBlocking(false);
                            cli.register(sel, SelectionKey.OP_READ);
                            continue;
                        }
                        
                        recvBuf.put(new String(buf.array(), 0, rlen));
                    }
                }
            }
        } catch(InterruptedException ex)
        {
            Logger.getLogger(DashboardServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex)
        {
            Logger.getLogger(DashboardServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean connected()
    {
        return cli != null;
    }

    public boolean hasMessage()
    {
        return !recvBuf.isEmpty();
    }

    public String getMessage()
    {
        return recvBuf.poll();
    }

    public boolean putMessage(String s)
    {
        try
        {
            cli.write(ByteBuffer.wrap(s.getBytes()));
        } catch(IOException ex)
        {
            return false;
        }

        return true;
    }
}
