package org.example;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    public static TextPanel textPanel;
    public static FormPanel formPanel;
    public static FormCurrentConnection formCurrentConnection;
    private JTabbedPane jTabbedPane;
    private JSplitPane jSplitPane;
    private JTabbedPane jTabbedPane2;
    public MainFrame(){
        super("EVN Testing System");
        setLayout(new BorderLayout());

        textPanel = new TextPanel();
        formPanel = new FormPanel();
        formCurrentConnection = new FormCurrentConnection();

        jTabbedPane2 = new JTabbedPane();
        jTabbedPane2.addTab("Console Messages", textPanel);

        jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab("EVN_Database", formPanel);
        jTabbedPane.addTab("EVN_Tableau", formCurrentConnection);
        jTabbedPane.setPreferredSize(new Dimension(400, 200));

        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jTabbedPane, jTabbedPane2);
        jSplitPane.setOneTouchExpandable(true);
        add(jSplitPane, BorderLayout.CENTER);

        setSize(1200, 1000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
