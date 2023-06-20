package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class WindowManagerEnquiryStudent extends JFrame implements ActionListener {
    JFrame frame1 = new JFrame();
    JPanel panelNorth = new JPanel();
    JPanel panelSouth = new JPanel();
    JLabel labelNo = new JLabel("学号");
    JTextField textNo = new JTextField(12);
    JLabel labelName = new JLabel("姓名");
    JTextField textName = new JTextField(12);
    JLabel labelSex = new JLabel("性别");
    JTextField textSex = new JTextField(12);
    JLabel labelBirth = new JLabel("出生日期");
    JTextField textBirth = new JTextField(12);
    JLabel labelEntrancedate = new JLabel("入学日期");
    JTextField textEntrancedate = new JTextField(12);
    JLabel labelClassno = new JLabel("班级号");
    JTextField textClassno = new JTextField(12);
    JLabel labelHomeaddress = new JLabel("家庭住址");
    JTextField textHomeaddress = new JTextField(50);
    JLabel labelSdept = new JLabel("所在系");
    JTextField textSdept = new JTextField(12);
    JLabel labelPostcode = new JLabel("邮政编码");
    JTextField textPostcode = new JTextField(12);
    JButton btnEnquiry = new JButton("查询/一览");
    JLabel labelStatus = new JLabel("上方空格均不填即查询所有记录，否则按照填写的信息查询");
    JTable table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);

    String[] columnNames = {"学号", "姓名", "性别", "出生日期", "入学日期", "班级号", "家庭住址", "所在系", "邮政编码", "就读状态"};
    Object[][] data = new Object[getColumns()][10];

    public WindowManagerEnquiryStudent() {
        table = new JTable(data, columnNames);
        frame1.setTitle("教务管理系统管理员界面-信息维护功能-学生信息查询");
        frame1.setVisible(true);
        frame1.setSize(800, 750);
        frame1.setLocation(10, 10);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panelNorth, BorderLayout.NORTH);
        frame1.add(panelSouth, BorderLayout.CENTER);
        panelNorth.setLayout(new GridLayout(11, 2));
        panelNorth.add(labelNo);
        panelNorth.add(textNo);
        panelNorth.add(labelName);
        panelNorth.add(textName);
        panelNorth.add(labelSex);
        panelNorth.add(textSex);
        panelNorth.add(labelBirth);
        panelNorth.add(textBirth);
        panelNorth.add(labelEntrancedate);
        panelNorth.add(textEntrancedate);
        panelNorth.add(labelClassno);
        panelNorth.add(textClassno);
        panelNorth.add(labelHomeaddress);
        panelNorth.add(textHomeaddress);
        panelNorth.add(labelSdept);
        panelNorth.add(textSdept);
        panelNorth.add(labelPostcode);
        panelNorth.add(textPostcode);
        panelNorth.add(btnEnquiry, BorderLayout.NORTH);
        panelNorth.add(labelStatus, BorderLayout.NORTH);
        frame1.add(scrollPane, BorderLayout.SOUTH);
        scrollPane.setViewportView(table);
        btnEnquiry.addActionListener(this);
    }

    public static void main(String[] args) {
        new WindowManagerEnquiryStudent();
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
                    && textBirth.getText().toString().equals("") == true
                    && textEntrancedate.getText().toString().equals("") == true
                    && textClassno.getText().toString().equals("") == true
                    && textHomeaddress.getText().toString().equals("") == true
                    && textSdept.getText().toString().equals("") == true
                    && textPostcode.getText().toString().equals("") == true) {
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Student_view";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    Statement stmt = dbConn.createStatement();
                    System.out.println(sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    // Get the number of columns in the ResultSet
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
                DefaultTableModel model = new DefaultTableModel(data, columnNames);
                table.setModel(model);
            } else {
                //清空表格
                for (int i = 0; i < getColumns(); i++) {
                    for (int j = 0; j < 10; j++) {
                        data[i][j] = "";
                    }
                }
                int flag = 0;
                String Input = null;
                String sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Student_view where 1=1 ";
                if (textNo.getText().toString().equals("") == false)
                    sql += " and Sno = " + "'" + textNo.getText() + "'";
                if (textName.getText().toString().equals("") == false)
                    sql += " and Sname = " + "'" + textName.getText() + "'";
                if (textSex.getText().toString().equals("") == false)
                    sql += "and Sex = " + "'" + textSex.getText() + "'";
                if (textBirth.getText().toString().equals("") == false)
                    sql += "and Birth = " + "'" + textBirth.getText() + "'";
                if (textEntrancedate.getText().toString().equals("") == false)
                    sql += "and Entrance_date = " + "'" + textEntrancedate.getText() + "'";
                if (textClassno.getText().toString().equals("") == false)
                    sql += "and Class_no = " + "'" + textClassno.getText() + "'";
                if (textHomeaddress.getText().toString().equals("") == false)
                    sql += "and Home_address = " + "'" + textHomeaddress.getText() + "'";
                if (textSdept.getText().toString().equals("") == false)
                    sql += "and Sdept = " + "'" + textSdept.getText() + "'";
                if (textPostcode.getText().toString().equals("") == false)
                    sql += "and Postcode = " + "'" + textPostcode.getText() + "'";
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
                    while (rs.next()) {
                        data[count][0] = rs.getString("Sno");
                        data[count][1] = rs.getString("Sname");
                        data[count][2] = rs.getString("Sex");
                        data[count][3] = rs.getString("Birth");
                        data[count][4] = rs.getString("Entrance_date");
                        data[count][5] = rs.getString("Class_no");
                        data[count][6] = rs.getString("Home_addr");
                        data[count][7] = rs.getString("Sde_no");
                        data[count][8] = rs.getString("Postcode");
                        data[count][9] = rs.getString("AttendanceStatus");
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
                DefaultTableModel model = new DefaultTableModel(data, columnNames);
                table.setModel(model);
            }
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
        } catch (ClassNotFoundException e1) {
            throw new RuntimeException(e1);
        } catch (SQLException e2) {
            throw new RuntimeException(e2);
        }
        int columnCount;
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery("use Academic_Affairs_Management_System_20211576 select * from Manager_Student_view");
            ResultSetMetaData rsmd = rs.getMetaData();
            columnCount = 0;
            while (rs.next()) {
                //统计表中共有多少行数据
                columnCount++;
            }
        } catch (SQLException e3) {
            throw new RuntimeException(e3);
        } finally {
            try {
                dbConn.close();
            } catch (SQLException e4) {
                throw new RuntimeException(e4);
            }
        }
        return columnCount;
    }
}