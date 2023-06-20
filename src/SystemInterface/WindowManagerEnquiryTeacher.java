package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class WindowManagerEnquiryTeacher extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JFrame frame1 = new JFrame();
    JPanel panelNorth = new JPanel();
    JPanel panelSouth = new JPanel();
    JLabel labelNo = new JLabel("工号");
    JTextField textNo = new JTextField(12);
    JLabel labelName = new JLabel("姓名");
    JTextField textName = new JTextField(12);
    JLabel labelSex = new JLabel("性别");
    JTextField textSex = new JTextField(12);
    JLabel labelSdept = new JLabel("所属专业编号");
    JTextField textSdept = new JTextField(12);
    JLabel labelPosition = new JLabel("职称");
    JTextField textPosition = new JTextField(12);
    JButton btnEnquiry = new JButton("查询/一览");
    JLabel labelStatus = new JLabel("上方空格均不填即查询所有记录，否则按照填写的信息查询");
    JTable table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);

    String[] columnNames = {"工号","姓名","性别","所属专业编号","职称"};
    Object[][] data = new Object[getColumns()][10];
    public WindowManagerEnquiryTeacher() {
        table = new JTable(data, columnNames);
        frame1.setTitle("教务管理系统管理员界面-信息维护功能-教师信息查询");
        frame1.setVisible(true);
        frame1.setSize(800, 680);
        frame1.setLocation(300, 100);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panelNorth, BorderLayout.NORTH);
        frame1.add(panelSouth, BorderLayout.CENTER);
        panelNorth.setLayout(new GridLayout(9, 2));
        panelNorth.add(labelNo);panelNorth.add(textNo);
        panelNorth.add(labelName);panelNorth.add(textName);
        panelNorth.add(labelSex);panelNorth.add(textSex);
        panelNorth.add(labelSdept);panelNorth.add(textSdept);
        panelNorth.add(labelPosition);panelNorth.add(textPosition);
        panelSouth.add(btnEnquiry, BorderLayout.NORTH);
        panelSouth.add(labelStatus, BorderLayout.NORTH);
        panelSouth.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(table);
        btnEnquiry.addActionListener(this);
    }

    public static void main(String[] args) {
        new WindowManagerEnquiryTeacher();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEnquiry) {
            //清空表格
            for (int i = 0; i < getColumns(); i++) {
                for (int j = 0; j < 10; j++) {
                    data[i][j] = "";
                }
            }
            if (textNo.getText().toString().equals("") == true
                    && textName.getText().toString().equals("") == true
                    && textSex.getText().toString().equals("") == true
                    && textSdept.getText().toString().equals("") == true
                    && textPosition.getText().toString().equals("") == true){
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Teacher_view";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    Statement stmt = dbConn.createStatement();
                    System.out.println(sql);
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
                //清空表格
                for (int i = 0; i < getColumns(); i++) {
                    for (int j = 0; j < 10; j++) {
                        data[i][j] = "";
                    }
                }
                int flag = 0;
                String Input = null;
                String sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Teacher_view where 1=1 ";
                if (textNo.getText().toString().equals("") == false) {
                    sql += "and Emp_no = " + "'" + textNo.getText().toString() + "'";
                }
                if (textName.getText().toString().equals("") == false) {
                    sql += "and Emp_name = " + "'" + textName.getText().toString() + "'";
                }
                if (textSex.getText().toString().equals("") == false) {
                    sql += "and Sex = " + "'" + textSex.getText().toString() + "'";
                }
                if (textSdept.getText().toString().equals("") == false) {
                    sql += "and Sdept_no = " + "'" + textSdept.getText().toString() + "'";
                }
                if (textPosition.getText().toString().equals("") == false) {
                    System.out.println(textPosition.getText().toString());
                    sql += "and Position = " + "'" + textPosition.getText().toString() + "'";
                }
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    Statement stmt = dbConn.createStatement();
                    System.out.println(sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    int count = 0;
                    while(rs.next()){
                        data[count][0] = rs.getString("Emp_no");
                        data[count][1] = rs.getString("Emp_name");
                        data[count][2] = rs.getString("Sex");
                        data[count][3] = rs.getString("Sde_no");
                        data[count][4] = rs.getString("Position");
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
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int columnCount;
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery("use Academic_Affairs_Management_System_20211576 select * from Manager_Teacher_view");
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