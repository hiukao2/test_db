package org.example;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.Vector;

public class Oracle extends AbstractTableModel {
    private String type;
    private String host;
    private int port;
    private String servicename;
    private String username;
    private String password;
    Vector<String[]> cache;
    int colCount;
    private static Connection connection = null;
    public Oracle() {
    }
    public Oracle(String type, String host, String servicename, int port, String username, String password){
        this.type = type;
        this.host = host;
        this.servicename = servicename;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public static void forName(String className)throws ClassNotFoundException{
        Class.forName(className);
    }
    public Connection connectDB(String host, String servicename, int port, String username, String password) throws SQLException {
        this.host = host;
        this.servicename = servicename;
        this.port = port;
        this.username = username;
        this.password = password;
        String conn = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + servicename;
        if(connection != null && !connection.isClosed()) {
            connection.close();
        }
        connection = DriverManager.getConnection(conn, username, password);
        return connection;
    }
    public void testDB(String host, String servicename, int port, String username, String password) {
        try {
            String conn = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + servicename;
            Connection connectionTest = DriverManager.getConnection(conn, username, password);
            JOptionPane.showMessageDialog(null, "Successful");
            FormPanel.cnnBtn.setVisible(true);
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR \n" + ex);
        }
    }
    public String getHost() {
        return host;
    }
    public String getServicename() {
        return servicename;
    }
    public int getPort() {
        return port;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public ResultSet executeQuery(String queryString) throws SQLException {
        Statement stm = connection.createStatement();
        return stm.executeQuery(queryString);
    }
    public void closeDatabaseConnection() throws SQLException {
        if(connection != null && !connection.isClosed()){
            connection.close();
        }
        connection = null;
    }


    @Override
    public int getRowCount() {
        return cache.size();
    }

    @Override
    public int getColumnCount() {
        return colCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((String[])cache.elementAt(rowIndex))[columnIndex];
    }
}
