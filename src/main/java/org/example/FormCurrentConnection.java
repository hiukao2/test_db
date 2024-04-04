package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.IOException;

public class FormCurrentConnection extends JPanel{
    private JFileChooser fileChooser;
    private JFileChooser jFileChooserTestSuite;
    private JLabel conName;
    public static JTextField conField;
    private JButton jButton;
    private JButton executeBtn;
    private JButton stopBtn;
    public static String path;
    private String testSuitePath;
    private JLabel testSuite;
    private JButton testSuiteBtn;
    private BITesting biTesting;
    public static String testSuitePathshort;
    private JLabel jApikey;
    public static JTextField apiKey;
    public FormCurrentConnection(){
        Dimension dim = getPreferredSize();
        dim.width = 300;
        setPreferredSize(dim);
        conName = new JLabel("Project Path: ");
        jApikey = new JLabel(" API Key: ");
        testSuite = new JLabel("Test Suite Path: ");

        executeBtn = new JButton("Execute BI Testing");
        jButton = new JButton("New Project File");
        stopBtn = new JButton("Stop");
        testSuiteBtn = new JButton("Select Test Suite");
        apiKey = new JTextField("", 10);


        Border innerBorder = BorderFactory.createTitledBorder("Current Connection");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 1;
        gc.weighty = 0.01;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        add(conName, gc);
        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        add(jButton, gc);

        gc.weightx = 1;
        gc.weighty = 0.01;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        add(testSuite, gc);
        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(testSuiteBtn, gc);

        gc.weightx = 1;
        gc.weighty = 0.01;
        gc.gridx = 0;
        gc.gridy = 2;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        add(jApikey, gc);
        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        add(apiKey, gc);


        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridy = 3;
        gc.gridx = 4;
        gc.anchor = GridBagConstraints.LINE_START;
        add(executeBtn, gc);

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridy = 3;
        gc.gridx = 5;
        gc.anchor = GridBagConstraints.LINE_START;
        add(stopBtn, gc);

        jButton.addActionListener(e -> {
            fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            FileFilter filter = new FileNameExtensionFilter("prj File", "prj");
            fileChooser.setFileFilter(filter);
            fileChooser.showOpenDialog(null);
            path = String.valueOf(fileChooser.getSelectedFile().toPath());
            MainFrame.textPanel.appendText("\nProject file is: " + fileChooser.getSelectedFile().toPath());
        });

        executeBtn.addActionListener(e -> {
            biTesting = new BITesting();
            biTesting.execute();
        });

        stopBtn.addActionListener(e -> {
            try {
                biTesting.stop();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        });

        testSuiteBtn.addActionListener(e -> {
            jFileChooserTestSuite = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            FileFilter filter = new FileNameExtensionFilter("TS File", "ts");
            jFileChooserTestSuite.setFileFilter(filter);
            jFileChooserTestSuite.showOpenDialog(null);
            testSuitePath = String.valueOf(jFileChooserTestSuite.getSelectedFile().toPath());
            String temp = testSuitePath.substring(testSuitePath.indexOf("Test Suites"));
            testSuitePathshort = temp.substring(0, temp.lastIndexOf('.')).replace("\\", "/");
            MainFrame.textPanel.appendText("\nTest Suite file is: " + testSuitePathshort);
        });
    }
}