package com.intellisoftkenya.onetooner.log;

import com.intellisoftkenya.onetooner.gui.MainFrame;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import javax.swing.SwingUtilities;

/**
 *
 * @author gitahi
 */
public class UILogHandler extends Handler {

    private final MainFrame mainFrame;

    public UILogHandler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public synchronized void publish(final LogRecord record) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFrame.showLog(getFormatter().format(record));
            }
        });
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }

}
