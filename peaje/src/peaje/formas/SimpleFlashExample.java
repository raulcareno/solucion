/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peaje.formas;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import chrriis.dj.nativeswing.swtimpl.components.JFlashPlayer;

/**
 * @author Christopher Deckers
 */
public class SimpleFlashExample extends JPanel {

  public SimpleFlashExample(String abrir) {
    super(new BorderLayout());
    JFlashPlayer flashPlayer = new JFlashPlayer();
    flashPlayer.load(getClass(), abrir);
    add(flashPlayer, BorderLayout.CENTER);
  }

  /* Standard main method to try that test as a standalone application. */
//  public static void main(String[] args) {
//    UIUtils.setPreferredLookAndFeel();
//    NativeInterface.open();
//    SwingUtilities.invokeLater(new Runnable() {
//      public void run() {
//        JFrame frame = new JFrame("DJ Native Swing Test");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(new SimpleFlashExample(), BorderLayout.CENTER);
//        frame.setSize(800, 600);
//        frame.setLocationByPlatform(true);
//        frame.setVisible(true);
//      }
//    });
//    NativeInterface.runEventPump();
//  }

} 
