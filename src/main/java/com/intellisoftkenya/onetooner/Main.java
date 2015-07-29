package com.intellisoftkenya.onetooner;

import com.intellisoftkenya.onetooner.gui.MainFrame;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * The Main class.
 *
 * @author gitahi
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLoger(Main.class.getName());

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the MainForm and start the LeaveManager thread to auto-create leave events */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                mainFrame.setTitle("ADT-FDT Migrator");
                mainFrame.setVisible(true);
            }
        });
    }
}
