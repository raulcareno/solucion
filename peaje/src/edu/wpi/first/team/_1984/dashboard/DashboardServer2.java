package edu.wpi.first.team._1984.dashboard;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author sid
 */
public class DashboardServer2 implements Callable<Integer>
{
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
     * Blocking Queue of data (picture/status) packets.
     */
    protected LinkedBlockingQueue<ByteBuffer> dataRecvBuf;

    /**
     * Blocking Queue of message packets.
     */
    protected LinkedBlockingQueue<String> messageRecvBuf;

    /**
     * Blocking Queue containing commands to be submitted.
     */

    protected LinkedBlockingQueue<String> sendBuf;

    /**
     * We're gonna have to expose our JImagePanel to this class, otherwise
     * things get slower than they already are.
     */
    protected JImagePanel update;

    /**
     * Listens on port p. The rest of this code uses 7450.
     *
     * @param p port to listen on.
     */
    public DashboardServer2(int p, JImagePanel up) throws IOException
    {
        listen = ServerSocketChannel.open();
        sel    = Selector.open();

        sendBuf = new LinkedBlockingQueue<String>();
        dataRecvBuf = new LinkedBlockingQueue<ByteBuffer>();
        messageRecvBuf = new LinkedBlockingQueue<String>();

        update = up;

        (listen.socket()).bind(new InetSocketAddress(p));
    }

    public ByteBuffer pollData() { return dataRecvBuf.poll(); }
    public ByteBuffer takeData() throws InterruptedException
    { return dataRecvBuf.take(); }

    public String pollMessage() { return messageRecvBuf.poll(); }
    public String takeMessage() throws InterruptedException
    { return messageRecvBuf.take(); }

    public void putCommand(String m) throws InterruptedException
    { sendBuf.put(m); }

    public boolean hasClient()
    {
        return cli != null;
    }

    /**
     * Instead of using Thread and Runnable, it is possible to use Callable
     * with the full Java library (I do not know if it is in the Squawk VM,
     * however). It's now possible to return an error code and throw non-runtime
     * exceptions. Code for submitting a Callable to be run is found in the GUI
     * file, under main().
     *
     * @return status code, where non-zero is an error.
     * @throws Exception
     */
    public Integer call() throws Exception
    {
        // We will only have one client, so... Blocking accept() call.
        cli = listen.accept();

        cli.configureBlocking(false);
        cli.register(sel, SelectionKey.OP_READ);

        byte[] image = new byte[230400];
        int read = 0;
        //ByteBuffer accum = ByteBuffer.allocate(230400);
        for(;;)
        {
            sel.select(1);

            Set keys = sel.selectedKeys();
            Iterator i = keys.iterator();

            while(i.hasNext())
            {
                SelectionKey key = (SelectionKey)i.next();
                i.remove();

                if(key.isReadable())
                {
                    int rlen = 0;
                    SocketChannel tc = (SocketChannel)key.channel();
                    ByteBuffer buf = ByteBuffer.allocate(10240);

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

                    try
                    {
                        if(read + rlen >= 230400)
                        {
                            rlen = 230400 - read;
                        }

                        for(int k = 0; k < rlen; k++)
                            image[k + read] = buf.get(k);

                        read += rlen;
                    } catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            i = sendBuf.iterator();
            while(i.hasNext())
            {
                String curr = (String)i.next();
                i.remove();

                cli.write(ByteBuffer.wrap(curr.getBytes()));
            }
        }
    }
}
