package SystemInterface;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class WindowStudentCourseChoose extends JFrame implements ActionListener {
    String loginID = windowsRegister.loginID;
    JPanel panelNorth = new JPanel();
    JPanel panelCenter = new JPanel();
    JPanel panelNorthEast = new JPanel();
    JPanel panelNorthWest = new JPanel();
    JFrame frame1 = new JFrame();
    JLabel labelSemester = new JLabel("学期");
    JComboBox comSemester = new JComboBox();
    JLabel labelSdept = new JLabel("系");
    JLabel comSdept = new JLabel(getSdept());
    JLabel labelCno = new JLabel("课程号");
    JTextField txtCno = new JTextField(12);
    JLabel labelCname = new JLabel("课程名");
    JTextField txtCname = new JTextField(12);
    JLabel labelEmp_Name = new JLabel("教师名");
    JTextField txtEmp_Name = new JTextField(12);
    JLabel labelCredit = new JLabel("学分");
    JTextField txtCredit = new JTextField(12);
    JButton btnSemestersEnquire = new JButton("当前学期内可选课程查询");
    JButton btnCnoEnquire = new JButton("课程号查询");
    JButton btnTeacherEnquire = new JButton("授课教师查询");
    JButton btnCreditEnquire = new JButton("学分对应课程查询");
    JButton btnCnameEnquire = new JButton("课程名查询");
    JButton btnChoose = new JButton("选课");
    JTable table = new JTable();
    String sqlInsert = "";
    String sqlCheck = "";
    String[] columnNames = {"学期","可选修系","可选修专业","课程号","课程名","教师名","学分","可选课人数"};
    String[][] data = new String[getColumns()][8];
    public WindowStudentCourseChoose() {
        frame1.setTitle("教务管理系统学生界面-学生选课界面");
        frame1.setVisible(true);
        frame1.setSize(800,540);
        frame1.setLocation(10, 10);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panelNorth, BorderLayout.NORTH);
        panelNorth.add(panelNorthEast,BorderLayout.EAST);
        panelNorth.add(panelNorthWest,BorderLayout.WEST);
        panelNorth.add(panelCenter,BorderLayout.CENTER);
        panelNorthWest.setLayout(new GridLayout(6,2));
        panelNorthEast.setLayout(new GridLayout(4,2));
        panelCenter.setLayout(new GridLayout(2,1));
        panelNorthEast.add(btnSemestersEnquire);
        panelNorthEast.add(btnCnoEnquire);panelNorthEast.add(btnCnameEnquire);
        panelNorthEast.add(btnTeacherEnquire);panelNorthEast.add(btnCreditEnquire);
        panelNorthEast.add(btnChoose);
        btnChoose.setPreferredSize(new Dimension(25,30));

        panelNorthWest.add(labelSemester);panelNorthWest.add(comSemester);
        int date = Calendar.getInstance().get(Calendar.YEAR);
        LocalDate dateStart = LocalDate.of(date, 1, 1);
        LocalDate dateSpring = LocalDate.of(date, 3, 1);
        LocalDate dateAutumn = LocalDate.of(date, 9, 1);
        LocalDate today = LocalDate.now();
        long dateSpringChoose = dateSpring.until(dateStart, ChronoUnit.DAYS);
        long dateAutumnChoose = dateAutumn.until(dateStart, ChronoUnit.DAYS);
        long todayChoose = today.until(dateStart, ChronoUnit.DAYS);
        System.out.print(today);
        if(todayChoose<=dateSpringChoose){
            comSemester.addItem((date-1)+"-"+date+"年第1学期");
            comSemester.addItem((date-1)+"-"+date+"年第2学期");
            comSemester.addItem(date+"-"+(date+1)+"年第1学期");
            comSemester.setSelectedIndex(2);
        }
        else if(todayChoose<=dateAutumnChoose&&todayChoose>dateSpringChoose){
            comSemester.addItem((date-1)+"-"+date+"年第2学期");
            comSemester.addItem(date+"-"+(date+1)+"年第1学期");
            comSemester.addItem(date+"-"+(date+1)+"年第2学期");
            comSemester.setSelectedIndex(2);
        }
        else if(todayChoose>dateAutumnChoose){
            comSemester.addItem(date+"-"+(date+1)+"年第1学期");
            comSemester.addItem(date+"-"+(date+1)+"年第2学期");
            comSemester.addItem((date+1)+"-"+(date+2)+"年第1学期");
            comSemester.setSelectedIndex(2);
        }
        panelNorthWest.add(labelSdept);panelNorthWest.add(comSdept);
        panelNorthWest.add(labelCno);panelNorthWest.add(txtCno);
        panelNorthWest.add(labelCname);panelNorthWest.add(txtCname);
        panelNorthWest.add(labelEmp_Name);panelNorthWest.add(txtEmp_Name);
        panelNorthWest.add(labelCredit);panelNorthWest.add(txtCredit);
        JScrollPane scrollPane = new JScrollPane(table);
        frame1.add(scrollPane,BorderLayout.CENTER);
        scrollPane.setViewportView(table);
        btnSemestersEnquire.addActionListener(this);
        btnCnoEnquire.addActionListener(this);
        btnCnameEnquire.addActionListener(this);
        btnTeacherEnquire.addActionListener(this);
        btnCreditEnquire.addActionListener(this);
        btnChoose.addActionListener(this);
    }
    public static void main(String[] args) {
        new WindowStudentCourseChoose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSemestersEnquire) {
            String sql = "SELECT * FROM Manager_Course_Teacher_manage_view where Semester='"+comSemester.getSelectedItem()+"' and Sde_name='"+comSdept.getText().trim()+"' and Semester='"+comSemester.getSelectedItem()+"'";
            Connect(sql);
        }else if (e.getSource() == btnCnoEnquire) {
            String sql = "SELECT * FROM Manager_Course_Teacher_manage_view where Cno='" + txtCno.getText().trim() + "'and Sde_name='"+comSdept.getText().trim()+"' and Semester='"+comSemester.getSelectedItem()+"'";
            Connect(sql);
        } else if (e.getSource() == btnCnameEnquire) {
            String sql = "SELECT * FROM Manager_Course_Teacher_manage_view where Cname='" + txtCname.getText().trim() + "'and Sde_name='"+comSdept.getText().trim()+"' and Semester='"+comSemester.getSelectedItem()+"'";
            Connect(sql);
        } else if (e.getSource() == btnTeacherEnquire) {
            String sql = "SELECT * FROM Manager_Course_Teacher_manage_view where Emp_Name='" + txtEmp_Name.getText().trim() + "'and Sde_name='"+comSdept.getText().trim()+"' and Semester='"+comSemester.getSelectedItem()+"'";
            Connect(sql);
        } else if (e.getSource() == btnCreditEnquire) {
            String sql = "SELECT * FROM Manager_Course_Teacher_manage_view where Credit='" + txtCredit.getText().trim() + "'and Sde_name='"+comSdept.getText().trim()+"' and Semester='"+comSemester.getSelectedItem()+"'";
            Connect(sql);
        } else if (e.getSource() == btnChoose) {
            if(sqlInsert.equals("")) {
                JOptionPane.showMessageDialog(null, "请先选择课程！", "提示", JOptionPane.ERROR_MESSAGE);
            }else {
                String Driver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                Connection dbConn = null;
                try {
                    Class.forName(Driver2);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                } catch (ClassNotFoundException ec) {
                    throw new RuntimeException(ec);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    Statement stmt = dbConn.createStatement();
                    System.out.println("sqlInsert:"+sqlInsert);
                    if(checkInsert(sqlCheck) == true){//checkInsert:检查该生是否已选过这门课
                        JOptionPane.showMessageDialog(null, "该课程已选！", "提示", JOptionPane.ERROR_MESSAGE);
                        dbConn.close();
                        return;
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "选课成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                    ResultSet rs = stmt.executeQuery(sqlInsert);

                } catch (SQLException ee) {
                    throw new RuntimeException(ee);
                } finally {
                    try {
                        dbConn.close();
                    } catch (SQLException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            }
        }
        // 添加监听器，监听表格的行选中事件
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // 获取当前选中行的索引
                int row = table.getSelectedRow();
                if (row >= 0) {
                    // 获取选中行的各列的数据
                    String insertSemester = table.getValueAt(row, 0).toString();
                    String insertSno = loginID.toString();
                    String insertCourseOfferedSdeNo = table.getValueAt(row, 1).toString();
                    String insertCno = table.getValueAt(row, 3).toString();
                    String insertCname = table.getValueAt(row, 4).toString();
                    //中间变量insertEmpName
                    String insertEmpName = table.getValueAt(row, 5).toString();
                    String insertEmpNo = getEmpNo(insertEmpName);
                    String insertCredit = table.getValueAt(row, 6).toString();
                    String insertStuTotal = table.getValueAt(row, 7).toString();
                    sqlInsert = "INSERT INTO Course_chosen_20211576(Semester,Sno,Cno,Cname,Emp_no,Credit,Course_Offered_Sde_no) VALUES ('" + insertSemester.trim() + "','" + insertSno.trim() + "','" + insertCno.trim() + "','" + insertCname.trim() + "','" + insertEmpNo.trim() + "'," + insertCredit + ",'" + insertCourseOfferedSdeNo.trim() + "')";
                    sqlCheck = "SELECT * FROM Course_chosen_20211576 where Sno='" + insertSno.trim() + "' and Cno='" + insertCno.trim() + "' and Semester='" + insertSemester.trim() + "' and Course_Offered_Sde_no='" + insertCourseOfferedSdeNo.trim() + "' and Emp_no='" + insertEmpNo.trim() + "'";
                }
            }
        });
    }
    private boolean checkInsert(String sqlCheck) {
        String Driver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        boolean flag = false;
        Connection dbConn = null;
        try {
            Class.forName(Driver2);
            dbConn = DriverManager.getConnection(url, userName, userPwd);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
        int columnCount;
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCheck);
            ResultSetMetaData rsmd = rs.getMetaData();
            columnCount = 0;
            while (rs.next()) {
                flag = true;
                break;
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
        return flag;
    }
    private String getEmpNo(String insertEmpName) {
        String insertEmpNo = null;
        String Driver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        Connection dbConn = null;
        try {
            Class.forName(Driver2);
            dbConn = DriverManager.getConnection(url, userName, userPwd);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
        int columnCount;
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery("use Academic_Affairs_Management_System_20211576 select * from Teacher_view where Emp_Name='" + insertEmpName + "'");
            ResultSetMetaData rsmd = rs.getMetaData();
            columnCount = 0;
            while (rs.next()) {
                insertEmpNo = rs.getString("Emp_no");
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
        return insertEmpNo;
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
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
        int columnCount;
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery("use Academic_Affairs_Management_System_20211576 select * from Manager_Course_Teacher_manage_view");
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
    public void Connect(String sql){
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < 8; j++) {
                data[i][j] = "";
            }
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
    }
    public String getSdept(){
        String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        String txtSdept = null;
        Connection dbConn = null;
        try {
            Class.forName(Driver);
            dbConn = DriverManager.getConnection(url, userName, userPwd);

            Statement stml = dbConn.createStatement();
            String sql = "SELECT * FROM Student_view where Sno = '"+loginID+"'";
            ResultSet rs = stml.executeQuery(sql);

            while (rs.next()) {
                txtSdept = rs.getString("Sde_name");
            }

            rs.close();
            stml.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return txtSdept;
    }
}
