package org.example;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) throws IOException {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
        }


}