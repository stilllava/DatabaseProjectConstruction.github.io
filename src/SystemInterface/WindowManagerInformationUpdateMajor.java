package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WindowManagerInformationUpdateMajor extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JFrame frame1 = new JFrame();
    JPanel panelNorth = new JPanel();
    JPanel panelCenter = new JPanel();
    JPanel panelSouth = new JPanel();
    JLabel labelSdeNo = new JLabel("专业号");
    JTextField textSdeNo = new JTextField(12);
    JLabel labelSdeName = new JLabel("专业名");
    JTextField textSdeName = new JTextField(12);
    JLabel labelSdeIntroduction = new JLabel("专业简介");
    JTextField textSdeIntroduction = new JTextField(12);
    JLabel labelAcaNo = new JLabel("学院编号");
    JTextField textAcaNo = new JTextField(12);
    JLabel labelAcaName = new JLabel("学院名");
    JTextField textAcaName = new JTextField(12);
    JButton btnAdd = new JButton("添加");
    JButton btnSearch = new JButton("查询");
    JButton btnInsert = new JButton("从excel表/.csv文件中读取信息");
    JTable table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);
    String [] columnNames = {"专业号","专业名","专业简介","学院编号","学院名"};
    Object[][] data = new Object[getColumns()][10];
    public WindowManagerInformationUpdateMajor() {
        frame1.setTitle("教务管理系统管理员界面-信息维护功能-专业信息维护");
        System.out.println(getColumns());
        frame1.setVisible(true);
        frame1.setSize(1200,800);
        frame1.setLocation(600, 800);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        table.setSize(350,400);
        scrollPane.setBounds(0,0,352,250);
        frame1.add(panel);
        panel.add(panelNorth, BorderLayout.NORTH);
        panel.add(panelCenter, BorderLayout.CENTER);
        panel.add(panelSouth, BorderLayout.SOUTH);
        panelNorth.setLayout(new GridLayout(6,2));
        panelNorth.add(labelSdeNo);panelNorth.add(textSdeNo);
        panelNorth.add(labelSdeName);panelNorth.add(textSdeName);
        panelNorth.add(labelSdeIntroduction);panelNorth.add(textSdeIntroduction);
        panelNorth.add(labelAcaNo);panelNorth.add(textAcaNo);
        panelNorth.add(labelAcaName);panelNorth.add(textAcaName);
        panelNorth.add(btnAdd);panelNorth.add(btnSearch);
        panelCenter.add(scrollPane);
        scrollPane.setViewportView(table);
        panelSouth.add(btnInsert);
        btnAdd.addActionListener(this);
        btnSearch.addActionListener(this);
        btnInsert.addActionListener(this);
    }
    public static void main(String[] args) {
        new WindowManagerInformationUpdateMajor();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd){
            if(textSdeNo.getText().trim().equals("") == true
                    && textSdeName.getText().trim().equals("") == true
                    && textSdeIntroduction.getText().trim().equals("") == true
                    && textAcaNo.getText().trim().equals("") == true
                    && textAcaName.getText().trim().equals("") == true){}
            else {
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = "insert into Academic_Affairs_Management_System_20211576.dbo.Sdept_20211576(Sde_no,Sde_name,Sde_introduction,Aca_no,Aca_name) values('"+textSdeNo.getText()+"','"+textSdeName.getText()+"','"+textSdeIntroduction.getText()+"','"+textAcaNo.getText()+"','"+textAcaName.getText()+"')";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    System.out.println(sql); // 如果连接成功 控制台输出
                    Statement stmt = dbConn.createStatement();
                    JOptionPane.showMessageDialog(null, "专业名为"+textSdeName.getText()+"的专业信息添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    ResultSet rs = stmt.executeQuery(sql);
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
        }
        if (e.getSource() == btnSearch){
            //清空表格
            for (int i = 0; i < getColumns(); i++) {
                for (int j = 0; j < 10; j++) {
                    data[i][j] = "";
                }
            }
            if (textSdeNo.getText().toString().equals("") == true
                    && textSdeName.getText().toString().equals("") == true
                    && textSdeIntroduction.getText().toString().equals("") == true
                    && textAcaNo.getText().toString().equals("") == true
                    && textAcaName.getText().toString().equals("") == true){
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Sdept_view";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    System.out.println("Connection Successful!"); // 如果连接成功 控制台输出
                    Statement stmt = dbConn.createStatement();
                    System.out.println(sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    // Get the number of columns in the ResultSet
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int numColumns = rsmd.getColumnCount();

                    // Retrieve all rows of data from the ResultSet and store in the data array
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
                //在数据库中进行查询,先对textAcaNo中内容进行查询，当textAcaNo中为空时方可对textAcaName进行查询,往后以此类推
                int flag = 0;
                String Input = null;
                String sql = null;
                if (textSdeNo.getText().toString().equals("") == false) {
                    Input = textSdeNo.getText().toString();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Sdept_view where Sde_no = " + "'" +Input + "'";
                    flag = 1;
                } else if (textSdeName.getText().toString().equals("") == false) {
                    Input = textSdeName.getText().toString();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Sdept_view where Sde_name = " + "'" +Input + "'";
                    flag = 2;
                } else if (textSdeIntroduction.getText().toString().equals("") == false) {
                    Input = textSdeIntroduction.getText().toString();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Sdept_view where Sde_introduction = " + "'" +Input + "'";
                    flag = 3;
                }else if (textAcaNo.getText().toString().equals("") == false) {
                    Input = textAcaNo.getText().toString();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Sdept_view where Aca_no = " + "'" +Input + "'";
                    flag = 4;
                }else if (textAcaName.getText().toString().equals("") == false) {
                    Input = textAcaName.getText().toString();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Sdept_view where Aca_name = " + "'" +Input + "'";
                    flag = 5;
                }
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    System.out.println("Connection Successful!"); // 如果连接成功 控制台输出
                    Statement stmt = dbConn.createStatement();
                    System.out.println(sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    int count = 0;
                    while(rs.next()){
                        data[count][0] = rs.getString("Sde_no");
                        data[count][1] = rs.getString("Sde_name");
                        data[count][2] = rs.getString("Sde_introduction");
                        data[count][3] = rs.getString("Aca_no");
                        data[count][4] = rs.getString("Aca_name");
                        count++;
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
            // Create a new DefaultTableModel with the data and column names
            DefaultTableModel model = new DefaultTableModel(data, columnNames);

            // Set the model for the JTable
            table.setModel(model);
        }
        if (e.getSource() == btnInsert){
            WindowManagerInformationUpdateStudentInsert insert = new WindowManagerInformationUpdateStudentInsert();
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
            ResultSet rs = stmt.executeQuery("use Academic_Affairs_Management_System_20211576 select * from Manager_Sdept_view");
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
    }}