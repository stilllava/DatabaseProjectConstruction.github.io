package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WindowStudentEnquiry extends JFrame implements ActionListener {
    String loginID = windowsRegister.loginID;
    JFrame frame1 = new JFrame();
    JPanel panelNorth = new JPanel();
    JPanel panelSouth = new JPanel();
    JPanel panelCenter = new JPanel();
    JLabel labelSno = new JLabel("学号");
    JLabel txtSno;
    JLabel labelSname = new JLabel("姓名");
    JLabel txtSname;
    JLabel labelSex = new JLabel("性别");
    JLabel txtSex;
    JLabel labelBirth = new JLabel("出生日期");
    JLabel txtBirth;
    JLabel labelEntranceDate = new JLabel("入学日期");
    JLabel txtEntranceDate;
    JLabel labelClass = new JLabel("班级号");
    JLabel txtClass;
    JLabel labelAddress = new JLabel("家庭住址");
    JLabel txtAddress;
    JLabel labelSdeNo = new JLabel("系号");
    JLabel txtSdeNo;
    JLabel labelSdeName = new JLabel("所在系");
    JLabel txtSdeName;
    JLabel labelPostcode = new JLabel("邮政编码");
    JLabel txtPostcode;
    JLabel labelStatus = new JLabel("就读状态");
    JLabel txtStatus;
    JButton btnEnquiry = new JButton("已选课程列表");
    JTable table = new JTable();
    String[] columnNames = { "序号", "学期", "课程号", "课程名", "任课教师工号", "学分"};
    Object[][] data = new Object[getColumns()][7];

    public WindowStudentEnquiry() {
        System.out.println(loginID);
        Object[] StudentRelated = new Object[11];
        getInformation(StudentRelated);
        txtSno = new JLabel((String) StudentRelated[0]);
        txtSname = new JLabel((String) StudentRelated[1]);
        txtSex = new JLabel((String) StudentRelated[2]);
        txtBirth = new JLabel((String) StudentRelated[3]);
        txtEntranceDate = new JLabel((String) StudentRelated[4]);
        txtClass = new JLabel((String) StudentRelated[5]);
        txtAddress = new JLabel((String) StudentRelated[6]);
        txtSdeNo = new JLabel((String) StudentRelated[7]);
        txtSdeName = new JLabel((String) StudentRelated[8]);
        txtPostcode = new JLabel((String) StudentRelated[9]);
        txtStatus = new JLabel((String) StudentRelated[10]);
        table = new JTable(data, columnNames);
        frame1.setTitle("教务管理系统学生界面-学生信息查询");
        frame1.setVisible(true);
        frame1.setSize(800, 680);
        frame1.setLocation(300, 249);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panelNorth, BorderLayout.NORTH);
        frame1.add(panelCenter, BorderLayout.CENTER);
        frame1.add(panelSouth, BorderLayout.SOUTH);
        panelNorth.setLayout(new GridLayout(6, 4));
        panelNorth.add(labelSno);panelNorth.add(txtSno);
        panelNorth.add(labelSname);panelNorth.add(txtSname);
        panelNorth.add(labelSex);panelNorth.add(txtSex);
        panelNorth.add(labelBirth);panelNorth.add(txtBirth);
        panelNorth.add(labelEntranceDate);panelNorth.add(txtEntranceDate);
        panelNorth.add(labelClass);panelNorth.add(txtClass);
        panelNorth.add(labelAddress);panelNorth.add(txtAddress);
        panelNorth.add(labelSdeNo);panelNorth.add(txtSdeNo);
        panelNorth.add(labelSdeName);panelNorth.add(txtSdeName);
        panelNorth.add(labelPostcode);panelNorth.add(txtPostcode);
        panelNorth.add(labelStatus);panelNorth.add(txtStatus);
        JScrollPane scrollPane = new JScrollPane(table);
        panelCenter.add(btnEnquiry);
        frame1.add(scrollPane,BorderLayout.SOUTH);
        scrollPane.setViewportView(table);
        btnEnquiry.addActionListener(this);
    }


    public static void main(String[] args) {
        new WindowStudentEnquiry();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEnquiry) {
            String sqlEnquiryStudent = "use Academic_Affairs_Management_System_20211576 select * from Student_Enquiry_view where Sno = '" + loginID.toString() + "'";
            btnEnquiry(sqlEnquiryStudent);
        }
    }
    public void btnEnquiry(String sql){
        String Driver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        Connection dbConn = null;
        try {
            Class.forName(Driver2);
            dbConn = DriverManager.getConnection(url, userName, userPwd);
        } catch (ClassNotFoundException ea) {
            throw new RuntimeException(ea);
        } catch (SQLException eb) {
            throw new RuntimeException(eb);
        }
        int columnCount = 0;
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                data[0][0] = rs.getString("Serial_Number");
                data[0][1] = rs.getString("Semester");
                data[0][2] = rs.getString("Cno");
                data[0][3] = rs.getString("Cname");
                data[0][4] = rs.getString("Emp_name");
                data[0][5] = rs.getString("Credit");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                dbConn.close();
            } catch (SQLException exc) {
                throw new RuntimeException(exc);
            }
        }
        // Create a new DefaultTableModel with the data and column names
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Set the model for the JTable
        table.setModel(model);
    }
    public int getColumns () {
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
            ResultSet rs = stmt.executeQuery("use Academic_Affairs_Management_System_20211576 select * from Student_Enquiry_view");
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
    public void getInformation(Object[] StudentRelated){
        String Driver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        String sql = "use Academic_Affairs_Management_System_20211576 select * from Student_view where Sno = " + loginID.trim();
        Connection dbConn = null;
        try {
            Class.forName(Driver2);
            dbConn = DriverManager.getConnection(url, userName, userPwd);
        } catch (ClassNotFoundException ea) {
            throw new RuntimeException(ea);
        } catch (SQLException eb) {
            throw new RuntimeException(eb);
        }
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                StudentRelated[0] = rs.getString("Sno");
                StudentRelated[1] = rs.getString("Sname");
                StudentRelated[2] = rs.getString("Sex");
                StudentRelated[3] = rs.getString("Birth").substring(0,10);
                StudentRelated[4] = rs.getString("Entrance_date").substring(0,10);
                StudentRelated[5] = rs.getString("Class_no");
                StudentRelated[6] = rs.getString("Home_addr");
                StudentRelated[7] = rs.getString("Sde_no");
                StudentRelated[8] = rs.getString("Sde_name");
                StudentRelated[9] = rs.getString("Postcode");
                StudentRelated[10] = rs.getString("AttendanceStatus");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                dbConn.close();
            } catch (SQLException exc) {
                throw new RuntimeException(exc);
            }
        }
    }
}