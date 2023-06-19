package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WindowStudentScoreEnquiry extends JFrame implements ActionListener {
    String loginID = windowsRegister.loginID;
    String loginName = windowsRegister.loginName;
    JFrame frame1 = new JFrame();
    JPanel panelNorth = new JPanel();
    JPanel panelSouth = new JPanel();
    JPanel panelCenter = new JPanel();
    JLabel labelSno = new JLabel("学号");
    JLabel txtSno;
    JLabel labelSname = new JLabel("姓名");
    JLabel txtSname;
    JLabel labelEntranceDate = new JLabel("入学日期");
    JLabel txtEntranceDate;
    JLabel labelClass = new JLabel("班级号");
    JLabel txtClass;
    JLabel labelSdeNo = new JLabel("系号");
    JLabel txtSdeNo;
    JLabel labelSdeName = new JLabel("所在系");
    JLabel txtSdeName;
    JLabel labelStatus = new JLabel("就读状态");
    JLabel txtStatus;
    JButton btnEnquiry = new JButton("成绩查询");
    JLabel btnWarning = new JLabel("分数为空白或0表示该课程尚未结课或教师尚未赋分，请稍后查询!");
    JTable table = new JTable();
    String[] columnNames = {"学期","课程号","课程名","授课教师","成绩"};
    Object[][] data = new Object[getColumns()][5];

    public WindowStudentScoreEnquiry() {
        System.out.println(loginID);
        System.out.println(getColumns());
        Object[] StudentRelated = new Object[11];
        getInformation(StudentRelated);
        txtSno = new JLabel(loginID);
        txtSname = new JLabel(loginName);
        txtEntranceDate = new JLabel((String) StudentRelated[4]);
        txtClass = new JLabel((String) StudentRelated[5]);
        txtSdeNo = new JLabel((String) StudentRelated[7]);
        txtSdeName = new JLabel((String) StudentRelated[8]);
        txtStatus = new JLabel((String) StudentRelated[10]);
        table = new JTable(data, columnNames);
        frame1.setTitle("教务管理系统学生界面-学生成绩查询");
        frame1.setVisible(true);
        frame1.setSize(680, 600);
        frame1.setLocation(10, 10);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panelNorth, BorderLayout.NORTH);
        frame1.add(panelCenter, BorderLayout.CENTER);
        frame1.add(panelSouth, BorderLayout.SOUTH);
        panelNorth.setLayout(new GridLayout(4, 4));
        panelNorth.add(labelSno);panelNorth.add(txtSno);
        panelNorth.add(labelSname);panelNorth.add(txtSname);
        panelNorth.add(labelEntranceDate);panelNorth.add(txtEntranceDate);
        panelNorth.add(labelClass);panelNorth.add(txtClass);
        panelNorth.add(labelSdeNo);panelNorth.add(txtSdeNo);
        panelNorth.add(labelSdeName);panelNorth.add(txtSdeName);
        panelNorth.add(labelStatus);panelNorth.add(txtStatus);
        JScrollPane scrollPane = new JScrollPane(table);
        panelCenter.setLayout(new GridLayout(2, 1));
        panelCenter.add(btnEnquiry);panelCenter.add(btnWarning);
        frame1.add(scrollPane,BorderLayout.SOUTH);
        scrollPane.setViewportView(table);
        btnEnquiry.addActionListener(this);
    }


    public static void main(String[] args) {
        new WindowStudentScoreEnquiry();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEnquiry) {
            String Driver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
            String userName = "s20211576"; // 默认用户名
            String userPwd = "s20211576"; // 密码
            String sqlCourseEnquiry = "select * from Student_Score_Enquiry_view where Sno = '" + loginID + "' and Sname = '" + loginName + "' order by Semester asc";
            System.out.println(sqlCourseEnquiry);
            Connection dbConn = null;
            try {
                Class.forName(Driver2);
                dbConn = DriverManager.getConnection(url, userName, userPwd);
            } catch (ClassNotFoundException e1) {
                throw new RuntimeException(e1);
            } catch (SQLException e2) {
                throw new RuntimeException(e2);
            }
            try {
                Statement stmt = dbConn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlCourseEnquiry);
                ResultSetMetaData rsmd = rs.getMetaData();
                int count = 0;
                while (rs.next()&&count<getColumns()) {
                    data[count][0] = rs.getString("Semester");
                    data[count][1] = rs.getString("Cno");
                    data[count][2] = rs.getString("Cname");
                    data[count][3] = rs.getString("Emp_name");
                    data[count][4] = rs.getString("Grade");
                    count++;
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
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            table.setModel(model);
        }
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
            ResultSet rs = stmt.executeQuery("use Academic_Affairs_Management_System_20211576 select * from Student_Score_Enquiry_view");
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
    //保留
    public void getInformation(Object[] StudentRelated){
        String Driver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        String sql = "use Academic_Affairs_Management_System_20211576 select * from Student_view where Sno = '" + loginID.trim() + "'";
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
