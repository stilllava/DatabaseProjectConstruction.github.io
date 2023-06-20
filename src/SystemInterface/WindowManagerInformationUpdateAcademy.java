package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WindowManagerInformationUpdateAcademy extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JFrame frame1 = new JFrame();
    JPanel panelNorth = new JPanel();
    JPanel panelCenter = new JPanel();
    JPanel panelSouth = new JPanel();
    JLabel labelAcaNo = new JLabel("学院编号");
    JTextField textAcaNo = new JTextField(12);
    JLabel labelAcaName = new JLabel("学院名");
    JTextField textAcaName = new JTextField(12);
    JLabel labelAcaIntroduction = new JLabel("学院简介");
    JTextField textAcaIntroduction = new JTextField(12);
    JButton btnAdd = new JButton("添加");
    JButton btnSearch = new JButton("查询");
    JTable table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);
    String [] columnNames = {"学院编号","学院名","学院简介"};
    Object[][] data = new Object[getColumns()][10];
    public WindowManagerInformationUpdateAcademy() {
        frame1.setTitle("教务管理系统管理员界面-信息维护功能-学院信息维护");
        System.out.println(getColumns());
        frame1.setVisible(true);
        frame1.setSize(720,660);
        frame1.setLocation(10,10);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        table.setSize(350,400);
        scrollPane.setBounds(0,0,352,250);
        frame1.add(panel);
        panel.add(panelNorth, BorderLayout.NORTH);
        panel.add(panelCenter, BorderLayout.CENTER);
        panel.add(panelSouth, BorderLayout.SOUTH);
        panelNorth.setLayout(new GridLayout(4,2));
        panelNorth.add(labelAcaNo);panelNorth.add(textAcaNo);
        panelNorth.add(labelAcaName);panelNorth.add(textAcaName);
        panelNorth.add(labelAcaIntroduction);panelNorth.add(textAcaIntroduction);
        panelNorth.add(btnAdd);panelNorth.add(btnSearch);
        panelCenter.add(scrollPane);
        scrollPane.setViewportView(table);
        btnAdd.addActionListener(this);
        btnSearch.addActionListener(this);
    }
    public static void main(String[] args) {
        new WindowManagerInformationUpdateAcademy();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd){
            if(textAcaNo.getText().trim().equals("") == false
                    && textAcaName.getText().trim().equals("") == false
                    && textAcaIntroduction.getText().trim().equals("") == false){
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql0 = "use Academic_Affairs_Management_System_20211576 select * from Manager_Academy_view where Aca_no ='"+textAcaNo.getText()+"'";
                String sql = "insert into Academic_Affairs_Management_System_20211576.dbo.Academy_20211576(Aca_no,Aca_name,Aca_introduction) " +
                        "values('"+textAcaNo.getText()+"','"+textAcaName.getText()+"','"+textAcaIntroduction.getText()+"')";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    System.out.println(sql0); // 如果连接成功 控制台输出
                    System.out.println(sql);
                    Statement stmt = dbConn.createStatement();
                    ResultSet rs0 = stmt.executeQuery(sql0);
                    String Aca_no = "";
                    while(rs0.next()){
                        Aca_no += rs0.getString("Aca_no");
                    }
                    if(Aca_no.equals("") == false){
                        JOptionPane.showMessageDialog(null, "名字为"+textAcaName.getText()+"的学院信息已存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "名字为"+textAcaName.getText()+"的学院信息添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        ResultSet rs = stmt.executeQuery(sql);
                        rs.close();
                    }
                    rs0.close();
                    stmt.close();
                    dbConn.close();
                } catch (Exception em) {
                    em.printStackTrace();
                } finally {
                    try {
                    } catch (Exception ec) {
                        ec.printStackTrace();
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"请填写完整信息!");
            }
        }
        if (e.getSource() == btnSearch){
            for (int i = 0; i < getColumns(); i++) {
                for (int j = 0; j < 10; j++) {
                    data[i][j] = "";
                }
            }
            if (textAcaNo.getText().toString().equals("") == true
                    && textAcaName.getText().toString().equals("") == true
                    && textAcaIntroduction.getText().toString().equals("") == true){
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Academy_view";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    Statement stmt = dbConn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int numColumns = rsmd.getColumnCount();
                    int row = 0;
                    while (rs.next()) {
                        for (int col = 0; col < numColumns; col++) {
                            data[row][col] = rs.getString(col + 1);
                        }
                        row++;
                    }
                    rs.close();
                    stmt.close();
                    dbConn.close();
                } catch (Exception em) {
                    em.printStackTrace();
                } finally {
                    try {
                    } catch (Exception ec) {
                        ec.printStackTrace();
                    }
                }
            }
            else {
                for (int i = 0; i < getColumns(); i++) {
                    for (int j = 0; j < 8; j++) {
                        data[i][j] = "";
                    }
                }
                String input = null;
                String sql = null;
                if (!textAcaNo.getText().isEmpty()) {
                    input = textAcaNo.getText();
                    sql = "SELECT * FROM Manager_Academy_view WHERE Aca_no = ?";
                } else if (!textAcaName.getText().isEmpty()) {
                    input = textAcaName.getText();
                    sql = "SELECT * FROM Manager_Academy_view WHERE Aca_name = ?";
                } else if (!textAcaIntroduction.getText().isEmpty()) {
                    input = textAcaIntroduction.getText();
                    sql = "SELECT * FROM Manager_Academy_view WHERE Aca_introduction = ?";
                }
                String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String username = "s20211576";
                String password = "s20211576";
                try (Connection conn = DriverManager.getConnection(url, username, password);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, input);
                    ResultSet rs = pstmt.executeQuery();
                    int count = 0;
                    while (rs.next()) {
                        data[count][0] = rs.getString("Aca_no");
                        data[count][1] = rs.getString("Aca_name");
                        data[count][2] = rs.getString("Aca_introduction");
                        count++;
                    }
                    rs.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            table.setModel(model);
        }
    }
    public int getColumns() {
        String Driver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        Connection dbConn = null;
        try {
            Class.forName(Driver2);
            dbConn = DriverManager.getConnection(url, userName, userPwd);
            System.out.println("Connection Successful!"); // 如果连接成功 控制台输出
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int columnCount;
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery("use Academic_Affairs_Management_System_20211576 select * from Manager_Academy_view");
            ResultSetMetaData rsmd = rs.getMetaData();
            columnCount = 0;
            while (rs.next()) {
                //统计表中共有多少行数据
                columnCount++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                dbConn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return columnCount;
    }
}