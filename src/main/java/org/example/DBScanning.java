package org.example;

import javax.swing.*;

public class DBScanning extends SwingWorker<Void, Void> {

    @Override
    protected Void doInBackground() throws Exception {
        FormPanel.checkTargetDB(FormPanel.cnn, FormPanel.account);
        return null;
    }
    @Override
    protected void done() {
        MainFrame.textPanel.appendText("Scanning Successful");
    }
}
