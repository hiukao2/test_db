package org.example;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {
    private JTextArea textArea;
    public TextPanel(){
        textArea = new JTextArea();
        textArea.setEditable(false);
        setLayout(new BorderLayout());
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }
    public void appendText(String text){
        textArea.append(text);
    }
    public void setText(String text){
        textArea.setText(text);
    }
}
