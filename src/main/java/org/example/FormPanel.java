package org.example;



import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FormPanel extends JPanel{
    private Oracle orcl;
    private JLabel hostLabel;
    private JLabel portLabel;
    private JLabel serviceLabel;
    private JLabel accountLabel;
    private JLabel passwordLabel;
    private static JTextField hostField;
    private static JTextField portField;
    private static JTextField serviceField;
    private static JTextField accountField;
    private static JPasswordField passwordField;
    private JButton okBtn;
    private JButton checkBtn;
    public static JButton cnnBtn;
    private static String host;
    private static int port;
    private static String serviceName;
    public static String account;
    private static String password;
    public static Connection cnn;
    private static int IDTEST = 1;
    private static java.util.List<Integer> data_check;
    private static String flag = "NORMAL";
    private static double avg = 0.0;
    private static double stddev = 0.0;
    private static int vers = 1;
    public FormPanel(){
        loadDBSettings();
        orcl = new Oracle();
        Dimension dim = getPreferredSize();
        dim.width = 300;
        setPreferredSize(dim);
        setMinimumSize(dim);
        hostLabel = new JLabel("Host: ");
        portLabel = new JLabel("Port: ");
        serviceLabel = new JLabel("Service Name: ");
        accountLabel = new JLabel("Account: ");
        passwordLabel = new JLabel("Password: ");

        hostField = new JTextField(host,10);
        portField = new JTextField(String.valueOf(port),10);
        serviceField = new JTextField(serviceName,10);
        accountField = new JTextField(account,10);
        passwordField = new JPasswordField(password,10);

        okBtn = new JButton("TEST CONNECTION");
        checkBtn = new JButton("Save");
        cnnBtn = new JButton("Scan");

        Border innerBorder = BorderFactory.createTitledBorder("Config Connection");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        add(hostLabel, gc);
        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(hostField, gc);

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridy = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        add(portLabel, gc);
        gc.gridy = 2;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(portField, gc);

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridy = 3;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        add(serviceLabel, gc);
        gc.gridy = 3;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(serviceField, gc);

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridy = 4;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        add(accountLabel, gc);
        gc.gridy = 4;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(accountField, gc);

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridy = 5;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        add(passwordLabel, gc);
        gc.gridy = 5;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(passwordField, gc);

        gc.weightx = 1;
        gc.weighty = 2.0;
        gc.gridy = 6;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(okBtn, gc);

        gc.weightx = 1;
        gc.weighty = 2.0;
        gc.gridy = 6;
        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        add(checkBtn, gc);

        gc.weightx = 1;
        gc.weighty = 2.0;
        gc.gridy = 6;
        gc.gridx = 3;
        gc.anchor = GridBagConstraints.LINE_START;
        add(cnnBtn, gc);
        cnnBtn.setVisible(false);

        okBtn.addActionListener(e -> {
            getText();

            orcl.testDB(host, serviceName, port, account, password);
        });
        checkBtn.addActionListener(e -> {
            getText();
            saveDBSettings(host, port, serviceName, account, password);
        });
        cnnBtn.addActionListener(e -> {
            getText();
            MainFrame.textPanel.setText("");
            Properties propTarget = new Properties();
            FileReader inTarget = null;
            try {
                inTarget = new FileReader("Asset/Property/config.properties");
                propTarget.load(inTarget);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "No Property File \n" + ex);
            }

            try {
                cnn = orcl.connectDB(host, serviceName, port, account, password);
                DBScanning dbScanning = new DBScanning();
                dbScanning.execute();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        });
    }
    private static void getText() {
        host = hostField.getText();
        port = Integer.parseInt(portField.getText());
        serviceName = serviceField.getText();
        account = accountField.getText();
        password = new String(passwordField.getPassword());
    }
    private static void saveDBSettings(String host, int port, String serviceName, String account, String password) {
        Properties properties = new Properties();
        properties.setProperty("oracle.host", host );
        properties.setProperty("oracle.port", String.valueOf(port));
        properties.setProperty("oracle.serviceName", serviceName);
        properties.setProperty("oracle.username", account);
        properties.setProperty("oracle.password", password);
        properties.setProperty("sheet", String.valueOf(0));
        try (FileOutputStream output = new FileOutputStream("Asset/Property/oracleConfig.properties")){
            properties.store(output, "Database Connection Settings");
        }catch(IOException ex) {
            JOptionPane.showMessageDialog(null, "Error \n" + ex);
        }
    }
    private static void loadDBSettings(){
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("Asset/Property/oracleConfig.properties")) {
            properties.load(input);
            host = properties.getProperty("oracle.host");
            port = Integer.parseInt(properties.getProperty("oracle.port"));
            serviceName = properties.getProperty("oracle.serviceName");
            account = properties.getProperty("oracle.username");
            password = properties.getProperty("oracle.password");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error \n" + ex);
        }
    }
    public static void checkTargetDB(Connection cnnTarget,
                                      String username) throws SQLException {
        String query = "SELECT TABLE_NAME FROM ALL_TABLES where OWNER = '" + username + "' ORDER BY TABLE_NAME ASC";
        String version = "select trunc(UPDATED_TIME), MAX(VERSION) AS VERSION from data_quality_monitor where trunc(updated_time) = trunc(SYSDATE) group by trunc(UPDATED_TIME)";

        ResultSet rs;
        rs = cnnTarget.createStatement().executeQuery(query);

        ResultSet rs_Version;
        rs_Version = cnnTarget.createStatement().executeQuery(version);

        try {
            while(rs_Version.next()) {
                int compare = rs_Version.getInt("VERSION");
                while (vers <= compare)
                    vers++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error \n" + ex);
        } finally {
            rs_Version.close();
        }

        try {
            while (rs.next()) {
                IDTEST++;
                data_check = new ArrayList<>();
                String tablename = rs.getString("TABLE_NAME");
                String query_warning = "SELECT ROW_AFFECTED FROM DATA_QUALITY_MONITOR WHERE ETL_NAME = '" + tablename + "' AND ABNOMAL_FLAG = 'NORMAL'";
                ResultSet rs_Check = cnnTarget.createStatement().executeQuery(query_warning);
                while (rs_Check.next()) {
                    int c = rs_Check.getInt("ROW_AFFECTED");
                    data_check.add(c);
                }

                String qr = "SELECT COLUMN_NAME FROM ALL_TAB_COLUMNS where OWNER = '" + account + "' and TABLE_NAME = " + "'" + tablename + "'";
                ResultSet column_checkName = cnnTarget.createStatement().executeQuery(qr);
                boolean check_columnName = checkColumn(column_checkName, "TG_CAPNHAT_DULIEU", "column_Name");
                if (check_columnName) {
                    String qr1 = "SELECT count(*) as RECORD_COUNT FROM " + '"' + tablename + '"' + " " + "WHERE TRUNC(TG_CAPNHAT_DULIEU) = to_date(to_char(to_date('" + java.time.LocalDate.now() + "', 'YYYY-MM-DD'), 'MM/DD/YYYY'), 'MM/DD/YYYY')";
                    String time_begin = time_converted();
                    ResultSet record_result = cnnTarget.createStatement().executeQuery(qr1);
                    while (record_result.next()) {
                        int count = record_result.getInt("RECORD_COUNT");
                        String Status;
                        if(!data_check.isEmpty()) {
                            flag = checkOutlier(data_check, count);
                            avg = calculateMean(data_check);
                            stddev = calculateStandardDeviation(data_check);
                        }
                        if (count == 0) {
                            Status = "FAIL";
                            MainFrame.textPanel.appendText("Table " + tablename + " chua duoc dong bo" + "\n");
                        } else {
                            Status = "SUCCESS";
                            MainFrame.textPanel.appendText("Table " + tablename + " da duoc dong bo" + "\n");
                        }

                        String time_end = time_converted();
                        String query2 = "INSERT INTO " + account + ".DATA_QUALITY_MONITOR values(" + IDTEST + ", '"+ tablename
                                + "', n'CHECKING DATABASE', " + "n'org.example', to_date('" + time_begin + "', 'MM/DD/YYYY HH:MI:SS AM')"
                                + ", to_date('" + time_end + "', 'MM/DD/YYYY HH:MI:SS AM')"
                                + ", n'" + Status + "', " + count + ", " + avg + ", " + stddev + ", n'" + flag + "',null, sysdate, " + vers + ")";

                        cnnTarget.createStatement().execute(query2);
                    }
                    record_result.close();
                }
                column_checkName.close();
            }
            rs.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    private static boolean checkColumn(ResultSet rs, String name, String type) throws SQLException {
        while(rs.next()) {
            String tableName = rs.getString(type);
            if(Objects.equals(tableName, name)) {
                return true;
            }
        }
        return false;
    }
    public static String time_converted() {
        String time = LocalDateTime.now().toString().replaceAll("T", " ").substring(0, 19);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        LocalDateTime dateTime = LocalDateTime.parse(time, inputFormatter);
        return dateTime.format(outputFormatter);
    }
    private static String checkOutlier(java.util.List<Integer> data, int count) {
        Collections.sort(data);
        java.util.List<Integer> data1;
        java.util.List<Integer> data2;
        if (data.size() % 2 == 0) {
            data1 = data.subList(0, data.size() / 2);
            data2 = data.subList(data.size() / 2, data.size());
        } else {
            data1 = data.subList(0, data.size() / 2);
            data2 = data.subList(data.size() / 2 + 1, data.size());
        }
        double q1 = getMedian(data1);
        double q3 = getMedian(data2);
        double iqr = q3 - q1;
        double lowerFence = q1 - 1.5 * iqr;
        double upperFence = q3 + 1.5 * iqr;
        if (count == 0) {
            return "ERROR";
        } else if (count > upperFence) {
            return "HIGH";
        } else if (count < lowerFence)
            return "LOW";
        else {
            return "NORMAL";
        }
    }
    private static double getMedian(java.util.List<Integer> data) {
        if (data.size() % 2 == 0)
            return (double) (data.get(data.size() / 2) + data.get(data.size() / 2 - 1)) / 2;
        else
            return (double) data.get(data.size() / 2);
    }
    private static double calculateMean(java.util.List<Integer> data) {
        return data.stream().mapToDouble(d -> d).average().orElse(0.0);
    }
    private static double calculateStandardDeviation(java.util.List<Integer> data) {
        double avg = calculateMean(data);
        double stddev = 0.0;
        for (int num : data) {
            stddev += Math.pow(num - avg, 2);
        }
        return Math.sqrt(stddev/data.size());
    }
}
