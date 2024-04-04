package org.example;

import javax.swing.*;
import java.io.*;

public class BITesting  extends SwingWorker<Void, Void> {
    public static Process p;
    @Override
    protected Void doInBackground() {
        String command = "katalonc -noSplash -runMode=console -projectPath=\""  +  FormCurrentConnection.path  + "\"" +
                " -retry=0 -testSuitePath=\"" + FormCurrentConnection.testSuitePathshort + "\" -browserType=\"Chrome\"" +
                " -executionProfile=\"default\" -apiKey=\"" + FormCurrentConnection.apiKey.getText() + "\"" +
                " --config -proxy.auth.option=NO_PROXY -proxy.system.option=NO_PROXY" +
                " -proxy.system.applyToDesiredCapabilities=true -webui.autoUpdateDrivers=true";

        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", command);
        builder.redirectErrorStream(true);
        try {
            if (p != null){
                p.destroy();
                p = null;
            }
            p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) { break; }
                System.out.println(line);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        return null;
    }
    protected void stop() throws IOException {
        if (p != null) {
            p.destroy();
            p.getInputStream().close();
            p.getOutputStream().close();
            p.getErrorStream().close();
            p = null;
        }
    }
    @Override
    protected void done() {
        MainFrame.textPanel.appendText("\nExecuted at " + FormPanel.time_converted());
    }
}
