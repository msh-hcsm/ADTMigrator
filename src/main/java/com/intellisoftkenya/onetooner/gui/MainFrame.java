package com.intellisoftkenya.onetooner.gui;

import com.intellisoftkenya.onetooner.PropertyManager;
import com.intellisoftkenya.onetooner.api.imp.processor.DeepDeleter;
import com.intellisoftkenya.onetooner.business.MigrationException;
import com.intellisoftkenya.onetooner.business.OneToOneMigrator;
import com.intellisoftkenya.onetooner.business.TableConfigurator;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import com.intellisoftkenya.onetooner.log.UILogHandler;
import java.awt.Color;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author gitahi
 */
public class MainFrame extends javax.swing.JFrame {

    private static Logger LOGGER;
    private boolean pause = false;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        showSettings();
        showStatus("", Color.BLACK);
        setIconImage(new ImageIcon(getClass().getResource("/icons/migrator-icon.png")).getImage());
        registerLogHandlers();
        LOGGER = LoggerFactory.getLoger(MainFrame.class.getName());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonsPanel = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        clearDestinationButton = new javax.swing.JButton();
        schedulePauseButton = new javax.swing.JButton();
        settingsButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        settingsScrollPane = new javax.swing.JScrollPane();
        settingsTextArea = new javax.swing.JTextArea();
        logsPanel = new javax.swing.JPanel();
        logsScrollPane = new javax.swing.JScrollPane();
        logsTextArea = new javax.swing.JTextArea();
        clearLogsButton = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        startButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/start-icon.png"))); // NOI18N
        startButton.setText("Start");
        startButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        startButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        clearDestinationButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clear-icon.png"))); // NOI18N
        clearDestinationButton.setText("Clear destination");
        clearDestinationButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        clearDestinationButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        clearDestinationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearDestinationButtonActionPerformed(evt);
            }
        });

        schedulePauseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pause-icon.png"))); // NOI18N
        schedulePauseButton.setText("Schedule pause");
        schedulePauseButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        schedulePauseButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        schedulePauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schedulePauseButtonActionPerformed(evt);
            }
        });

        settingsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/settings-icon.png"))); // NOI18N
        settingsButton.setText("Settings");
        settingsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        settingsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });

        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/exit-icon.png"))); // NOI18N
        exitButton.setText("Exit");
        exitButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        exitButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        settingsTextArea.setEditable(false);
        settingsTextArea.setColumns(20);
        settingsTextArea.setForeground(new java.awt.Color(0, 0, 204));
        settingsTextArea.setLineWrap(true);
        settingsTextArea.setRows(5);
        settingsTextArea.setWrapStyleWord(true);
        settingsScrollPane.setViewportView(settingsTextArea);

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(startButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearDestinationButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(schedulePauseButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(settingsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(settingsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addContainerGap())
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(settingsButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clearDestinationButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(schedulePauseButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(settingsScrollPane))
                .addContainerGap())
        );

        logsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Logs"));

        logsTextArea.setEditable(false);
        logsTextArea.setColumns(20);
        logsTextArea.setRows(5);
        logsScrollPane.setViewportView(logsTextArea);

        javax.swing.GroupLayout logsPanelLayout = new javax.swing.GroupLayout(logsPanel);
        logsPanel.setLayout(logsPanelLayout);
        logsPanelLayout.setHorizontalGroup(
            logsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logsScrollPane)
                .addContainerGap())
        );
        logsPanelLayout.setVerticalGroup(
            logsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logsPanelLayout.createSequentialGroup()
                .addComponent(logsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );

        clearLogsButton.setText("Clear logs");
        clearLogsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearLogsButtonActionPerformed(evt);
            }
        });

        statusLabel.setText("status");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(clearLogsButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearLogsButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        pause = false;
        showStatus("", Color.BLACK);
        new MigrationWorker(progressBar).execute();
    }//GEN-LAST:event_startButtonActionPerformed

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
        SettingsDialog sd = new SettingsDialog(this, true);
        sd.setLocationRelativeTo(null);
        sd.setVisible(true);
        showSettings();
    }//GEN-LAST:event_settingsButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        close();
    }//GEN-LAST:event_exitButtonActionPerformed

    private void clearLogsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearLogsButtonActionPerformed
        logsTextArea.setText("");
    }//GEN-LAST:event_clearLogsButtonActionPerformed

    private void clearDestinationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearDestinationButtonActionPerformed
        pause = false;
        showStatus("", Color.BLACK);
        new DeletionWorker(progressBar).execute();
    }//GEN-LAST:event_clearDestinationButtonActionPerformed

    private void schedulePauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schedulePauseButtonActionPerformed
        pause = true;
    }//GEN-LAST:event_schedulePauseButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton clearDestinationButton;
    private javax.swing.JButton clearLogsButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JPanel logsPanel;
    private javax.swing.JScrollPane logsScrollPane;
    private javax.swing.JTextArea logsTextArea;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton schedulePauseButton;
    private javax.swing.JButton settingsButton;
    private javax.swing.JScrollPane settingsScrollPane;
    private javax.swing.JTextArea settingsTextArea;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables

    private void registerLogHandlers() {
        SimpleFormatter formatter = new SimpleFormatter();

        UILogHandler uiLogHandler = new UILogHandler(this);
        uiLogHandler.setFormatter(formatter);
        uiLogHandler.setLevel(Level.INFO);

        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("log.txt", 102400, 1);
            fileHandler.setFormatter(formatter);
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (LoggerFactory.getLevel().intValue() >= uiLogHandler.getLevel().intValue()) {
            LoggerFactory.addHandler(uiLogHandler);
            clearLogsButton.setEnabled(true);
        } else {
            showLog("Logging level is set too high to be shown here. Only Loging level:"
                    + "INFO or higher can be displayed in the GUI. See the 'log.txt' "
                    + "file instead.");
            clearLogsButton.setEnabled(false);
        }
        if (fileHandler != null) {
            LoggerFactory.addHandler(fileHandler);
        }
    }

    public void showLog(final String message) {
        logsTextArea.append(message);
        this.validate();
    }

    public void clearLog() {
        logsTextArea.setText("");
        this.validate();
    }

    private void showStatus(String status, Color color) {
        statusLabel.setForeground(color);
        statusLabel.setText(status);
    }

    private void close() {
        if (!startButton.isEnabled()) {
            Object[] options = {"Yes",
                "No"};
            if (JOptionPane.showOptionDialog(this, "The process is still in progress. "
                    + "Exiting now might leave your destination database in an inconsistent state.\n"
                    + "Do you still want to quit?",
                    "Are you sure?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null, options, options[1]) == JOptionPane.NO_OPTION) {
                return;
            }
        }
        System.exit(0);
    }

    private void logTimeSpent(long start, long finish) {
        String message = "Time spent: ";
        final int millisecondsPerSecond = 1000;
        final int secondsPerMinute = 60;
        final int minutesPerHour = 60;
        long timeTaken = finish - start;
        if (timeTaken < millisecondsPerSecond) {
            message = message + " " + timeTaken + " millisecond(s).";
        } else {
            timeTaken = timeTaken / millisecondsPerSecond;
            if (timeTaken < secondsPerMinute) {
                message = message + " about " + timeTaken + " second(s).";
            } else {
                timeTaken = timeTaken / secondsPerMinute;
                if (timeTaken < secondsPerMinute) {
                    message = message + " about " + timeTaken + " minute(s).";
                } else {
                    timeTaken = timeTaken / minutesPerHour;
                    message = message + " about " + timeTaken + " hour(s).";
                }
            }
        }
        LOGGER.info(message);
    }

    private void showSettings() {
        settingsTextArea.setText("Logging Level: " + PropertyManager.getProperty("logging.level") + "\n\n");
        
        settingsTextArea.append("Deep Delete: " + PropertyManager.getProperty("deep.delete") + "\n\n");

        settingsTextArea.append("Source Driver: " + PropertyManager.getProperty("source.driver") + "\n");
        settingsTextArea.append("Source URL: " + PropertyManager.getProperty("source.url") + "\n");
        settingsTextArea.append("Source Username: " + PropertyManager.getProperty("source.username") + "\n");
        settingsTextArea.append("Source Password: " + PropertyManager.getProperty("source.password") + "\n\n");

        settingsTextArea.append("Destination Driver: " + PropertyManager.getProperty("destination.driver") + "\n");
        settingsTextArea.append("Destination URL: " + PropertyManager.getProperty("destination.url") + "\n");
        settingsTextArea.append("Destination Username: " + PropertyManager.getProperty("destination.username") + "\n");
        settingsTextArea.append("Destination Password: " + PropertyManager.getProperty("destination.password"));

        settingsTextArea.setCaretPosition(0);
    }

    @Override
    public void dispose() {
        close();
    }

    private class MigrationWorker extends SwingWorker<Object, Integer> {

        private final JProgressBar pb;

        public MigrationWorker(JProgressBar pb) {
            this.pb = pb;
        }

        @Override
        protected Object doInBackground() throws Exception {
            long start = new Date().getTime();
            long finish;
            startButton.setEnabled(false);
            clearDestinationButton.setEnabled(false);
            OneToOneMigrator migrator = new OneToOneMigrator();
            try {
                LOGGER.log(Level.INFO, "Process started.");

                List<OneToOne> otos = new TableConfigurator().configureTables();
                int i = 0;
                int n = otos.size();
                pb.setMaximum(n);
                for (OneToOne oto : otos) {
                    try {
                        migrator.migrateOneToOne(oto);
                        i++;
                        publish(i);
                        showStatus("Executed step " + i + " of " + n, Color.BLACK);
                        if (pause) {
                            showStatus("Paused! (Executed " + i + " out of " + n + " steps)", Color.BLACK);
                            LOGGER.log(Level.INFO, "Process paused!");
                            return null;
                        }
                    } catch (Exception ex) {
                        showStatus("Aborted! (Executed " + i + " out of " + n + " steps)", Color.RED);
                        String source = oto.getSourceTable().getName();
                        String destination = oto.getDestinationTable().getName();
                        LOGGER.log(Level.SEVERE, "An error occured while migrating data from ''{0}'' to ''{1}''.",
                                new Object[]{source, destination});
                        if (migrator.isDeleteOnFailure()) {
                            LOGGER.log(Level.INFO, "Attempting to delete any inconsistent data from ''{0}''.",
                                    new Object[]{destination});
                            try {
                                int affected = migrator.deleteOneToOne(oto);
                                if (affected != -1) {
                                    LOGGER.log(Level.INFO, "Deleted {0} records from ''{1}''. Fix the errors and re-run the process.",
                                            new Object[]{affected, destination});
                                }
                            } catch (SQLException sex) {
                                LOGGER.log(Level.INFO, "Deleting from from ''{0}'' failed. Manually delete records "
                                        + "from ''{0}''. fix the errors and re-run the process.",
                                        new Object[]{destination});
                                throw sex;
                            }
                        }
                        throw new MigrationException("Process aborted! See logs for details.",
                                oto, ex);
                    }
                }
                showStatus("Completed! (Executed " + i + " out of " + n + " steps)", Color.BLUE);
                LOGGER.log(Level.INFO, "Process successfully completed!");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Migration error!.", ex);
            } finally {
                migrator.close();
                finish = new Date().getTime();
                logTimeSpent(start, finish);
            }
            return null;
        }

        @Override
        protected void done() {
            startButton.setEnabled(true);
            clearDestinationButton.setEnabled(true);
        }

        @Override
        protected void process(List<Integer> chunks) {
            int i = chunks.get(chunks.size() - 1);
            pb.setValue(i);
        }
    }

    private class DeletionWorker extends SwingWorker<Object, Integer> {

        private final JProgressBar pb;

        public DeletionWorker(JProgressBar pb) {
            this.pb = pb;
        }

        @Override
        protected Object doInBackground() throws Exception {
            long start = new Date().getTime();
            long finish;
            startButton.setEnabled(false);
            clearDestinationButton.setEnabled(false);
            OneToOneMigrator migrator = new OneToOneMigrator();
            try {
                LOGGER.log(Level.INFO, "Process started.");

                List<OneToOne> otos = new TableConfigurator().configureTables();
                Collections.sort(otos);
                int i = 0;
                int n = otos.size();
                pb.setMaximum(n);
                for (OneToOne oto : otos) {
                    try {
                        int affected = migrator.deleteOneToOne(oto);
                        if (affected != -1) {
                            LOGGER.log(Level.INFO, "Deleted {0} records from ''{1}''.",
                                    new Object[]{affected, oto.getDestinationTable().getName()});
                        }
                        i++;
                        publish(i);
                        showStatus("Executed step " + i + " of " + n, Color.BLACK);
                        if (pause) {
                            showStatus("Paused! (Executed " + i + " out of " + n + " steps)", Color.BLACK);
                            LOGGER.log(Level.INFO, "Process paused!");
                            return null;
                        }
                    } catch (SQLException ex) {
                        showStatus("Aborted! (Executed " + i + " out of " + n + " steps)", Color.RED);
                        String destination = oto.getDestinationTable().getName();
                        LOGGER.log(Level.SEVERE, "An error occured while deleting data from ''{0}''.",
                                new Object[]{destination});
                        throw new MigrationException("Process aborted! See logs for details.",
                                oto, ex);
                    }
                }
                new DeepDeleter().process(null);
                showStatus("Completed! (Executed " + i + " out of " + n + " steps)", Color.BLUE);
                LOGGER.log(Level.INFO, "Process successfully completed!");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Deletion error!.", ex);
            } finally {
                migrator.close();
                finish = new Date().getTime();
                logTimeSpent(start, finish);
            }
            return null;
        }

        @Override
        protected void done() {
            startButton.setEnabled(true);
            clearDestinationButton.setEnabled(true);
        }

        @Override
        protected void process(List<Integer> chunks) {
            int i = chunks.get(chunks.size() - 1);
            pb.setValue(i);
        }
    }
}
