/*
 *  Big Brother - monitoring software for FRC robots
 *   Copyright (C) 2011  Sidney J. Spry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.wpi.first.team._1984;

import edu.wpi.first.team._1984.dashboard.AxisCamera;
import edu.wpi.first.team._1984.dashboard.AxisCameraProcessor;
import edu.wpi.first.team._1984.dashboard.DashboardServer;
import edu.wpi.first.team._1984.dashboard.JImagePanel;
import edu.wpi.first.team._1984.image.DetectionType;
import edu.wpi.first.team._1984.image.EdgeDetector;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Main class, instantiates itself and opens a window.
 *
 * @author sid
 */
public class BigBrotherGUI extends JFrame
{

    protected AxisCamera cam;
    protected EdgeDetector edge;

    protected DashboardServer dash;

    public BigBrotherGUI(int port) throws IOException, InterruptedException
    {
        super("Big Brother");

        shouldScroll = true;

        //cam = new AxisCamera("http://10.19.84.10", "320x240");
        cam = new AxisCamera("http://192.168.1.10", "320x240");
        edge = new EdgeDetector(0, 0, DetectionType.Sobel, false, 0, 0);

        // XXX: Make sure to use 1180, only port available during competition.
        dash = new DashboardServer(1180);
        (new Thread(dash)).start();

        initComponents();
    }

    private void initComponents() throws IOException
    {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        camImage = new JImagePanel(320, 240);
        camImage.setPreferredSize(new Dimension(320, 240));
        camImage.setFocusable(false);

        stats = genDefaultTree();
        statusTree = new JTree(stats);
        //statusTree.setPreferredSize(new Dimension(360, 245));
        statusTree.setPreferredSize(new Dimension(360, 0));
        statusTree.setVisibleRowCount(0);
        statusTree.setFocusable(false);

        //statusTree.

        recvText = new JTextArea();
        recvText.setFont(new Font("Monospaced", 0, 11));
        recvText.setRows(8);
        recvText.setColumns(20);
        recvText.setText("Waiting for connection on port 1180.\n");
        recvText.setEditable(false);
        recvText.setFocusable(false);

        sendText = new JTextField();
        sendText.setFont(new Font("Monospaced", 0, 11));
        sendText.setEnabled(false);

        sendText.addKeyListener(new KeyAdapter()
        {

            @Override
            public void keyTyped(KeyEvent ke)
            {
                if(ke.getKeyChar() == KeyEvent.VK_ENTER)
                {
                    dash.putMessage(sendText.getText() + "\n");
                        
                    // TODO: Add scrollback limit.
                    recvText.append(">>> " + sendText.getText() + "\n");
                    sendText.setText(null);
                }
            }
        });

        statusTreeScroll = new JScrollPane(statusTree);
        recvTextScroll = new JScrollPane(recvText);
        recvTextScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
        {
            public void adjustmentValueChanged(AdjustmentEvent ae)
            {
                JScrollBar vbar = (JScrollBar)ae.getSource();

                if(!ae.getValueIsAdjusting()) return;

                if((vbar.getValue() + vbar.getVisibleAmount()) >= vbar.getMaximum())
                    shouldScroll = true;
                else if(shouldScroll)
                    shouldScroll = false;
            }
        });

        horiz = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, camImage, statusTreeScroll);
        vert = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, horiz, recvTextScroll);

        setLayout(new BorderLayout());
        add(vert, BorderLayout.CENTER);
        add(sendText, BorderLayout.SOUTH);
        pack();
    }

    public DefaultMutableTreeNode[] genDefaultTree()
    {
        DefaultMutableTreeNode[] tree = new DefaultMutableTreeNode[4];
        String[] labels = new String[] {"Digital IO", "Analog IO", "PWM", "Custom"};

        for(int i = 0; i < tree.length; i++)
            tree[i] = new DefaultMutableTreeNode(labels[i]);

        return tree;
    }

    public void scrollRecvText()
    {
        if(shouldScroll)
            recvText.setCaretPosition(recvText.getText().length());
    }

    public static void main(String[] args) throws IOException, InterruptedException
    {
        final BigBrotherGUI inst = new BigBrotherGUI(7450);

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                inst.setVisible(true);
            }
        });

        new Thread(new AxisCameraProcessor(inst.cam, inst.edge, inst.camImage)).start();
        
        do {
            while(!inst.dash.connected()) Thread.sleep(1);
            
            inst.sendText.setEnabled(true);
            inst.recvText.append("Connected.\n");
            inst.scrollRecvText();

            String msg = "";
            while(inst.dash.connected())
            {
                Thread.sleep(1);
                if(!inst.dash.hasMessage()) continue;
                
                inst.recvText.append(msg = inst.dash.getMessage());
                inst.scrollRecvText();

                if(msg.indexOf("STATUP") == 0)
                {
                    String end = "";

                    int t = msg.indexOf(':');
                    if(t > 0)
                    {
                        end = msg.substring(t + 1, msg.length());
                        msg = msg.substring(0, t - 1);
                    }

                    String[] arg = msg.split(" ");

                    System.out.println(Arrays.toString(arg) + " \"" + end + "\"");
                }
            }

            inst.sendText.setEnabled(false);
            inst.recvText.append("Disconnected, awaiting reconnect on port 7450.\n");
            inst.scrollRecvText();
        } while(true);
    }

    private boolean shouldScroll;
    private DefaultMutableTreeNode[] stats;

    private JTree statusTree;
    private JTextArea recvText;
    private JTextField sendText;
    private JSplitPane horiz, vert;
    private JImagePanel camImage;
    private JScrollPane statusTreeScroll, recvTextScroll;
}
