package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;

public class WindowManagerCourseAvailable extends JFrame implements ActionListener {
    JPanel panelEast = new JPanel();
    JPanel panelNorth = new JPanel();
    JPanel panelCenter = new JPanel();
    JPanel panelNorthEast = new JPanel();
    JPanel panelNorthWest = new JPanel();
    JFrame frame1 = new JFrame();
    JLabel labelSemester = new JLabel("学期");
    JComboBox comSemester = new JComboBox();
    JLabel labelSdept = new JLabel("系");
    JComboBox comSdept = new JComboBox();
    JLabel labelCno = new JLabel("课程号");
    JTextField txtCno = new JTextField(12);
    JLabel labelCname = new JLabel("课程名");
    JTextField txtCname = new JTextField(12);
    JLabel labelEmp_Name = new JLabel("教师名");
    JTextField txtEmp_Name = new JTextField(12);
    JLabel labelCredit = new JLabel("学分");
    JTextField txtCredit = new JTextField(12);
    JButton btnList = new JButton("信息一览");
    JButton btnSemestersEnquire = new JButton("当前学期内可选课程查询");
    JButton btnSdeptEnquire = new JButton("当前系可选课程查询");
    JButton btnSemesterandSdeptEnquire = new JButton("当前学期内当前系可选课程查询");
    JButton btnCnoEnquire = new JButton("课程号查询");
    JButton btnTeacherEnquire = new JButton("授课教师查询");
    JButton btnCreditEnquire = new JButton("学分对应课程查询");
    JButton btnCnameEnquire = new JButton("课程名查询");
    JTable table = new JTable();
    String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
    Connection dbConn = null;
    String userName = "s20211576"; // 默认用户名
    String userPwd = "s20211576"; // 密码
    String[] columnNames = {"学期","可选修系","可选修专业","课程号","课程名","教师名","学分","选课人数"};
    String[][] data = new String[getColumns()][8];
    public WindowManagerCourseAvailable() {
        frame1.setTitle("教务管理系统管理员界面-学生可选课程管理");
        frame1.setVisible(true);
        frame1.setSize(900,800);
        frame1.setLocation(300, 249);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panelNorth, BorderLayout.NORTH);
        frame1.add(panelCenter, BorderLayout.CENTER);
        panelNorth.add(panelNorthEast,BorderLayout.EAST);
        panelNorth.add(panelNorthWest,BorderLayout.WEST);
        panelNorthWest.setLayout(new GridLayout(6,2));
        panelNorthEast.setLayout(new GridLayout(4,2));
        panelNorthWest.add(labelSemester);panelNorthWest.add(comSemester);
        int date = Calendar.getInstance().get(Calendar.YEAR);
        int i=date-10;
        for(;i<date+1;i++){
            comSemester.addItem(i+"-"+(i+1)+"年第1学期");
            comSemester.addItem(i+"-"+(i+1)+"年第2学期");
        }
        comSemester.setSelectedIndex(17);
        panelNorthWest.add(labelSdept);panelNorthWest.add(comSdept);
        //通过数据库内Sdept_20211576表内的数据添加系
        try {
            Class.forName(Driver);
            dbConn = DriverManager.getConnection(url, userName, userPwd);

            Statement stml = dbConn.createStatement();
            String sql = "SELECT Sde_name FROM Sdept_20211576";
            ResultSet rs = stml.executeQuery(sql);

            while (rs.next()) {
                comSdept.addItem(rs.getString("Sde_name"));
            }

            rs.close();
            stml.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        comSdept.setSelectedItem("软件工程");
        panelNorthWest.add(labelCno);panelNorthWest.add(txtCno);
        panelNorthWest.add(labelCname);panelNorthWest.add(txtCname);
        panelNorthWest.add(labelEmp_Name);panelNorthWest.add(txtEmp_Name);
        panelNorthWest.add(labelCredit);panelNorthWest.add(txtCredit);
        panelNorthEast.add(btnList);panelNorthEast.add(btnSemestersEnquire);
        panelNorthEast.add(btnSdeptEnquire);panelNorthEast.add(btnSemesterandSdeptEnquire);
        panelNorthEast.add(btnCnoEnquire);panelNorthEast.add(btnCnameEnquire);
        panelNorthEast.add(btnTeacherEnquire);panelNorthEast.add(btnCreditEnquire);
        JScrollPane scrollPane = new JScrollPane(table);
        panelCenter.add(scrollPane);
        scrollPane.setViewportView(table);
        btnList.addActionListener(this);
        btnSemestersEnquire.addActionListener(this);
        btnSdeptEnquire.addActionListener(this);
        btnCnoEnquire.addActionListener(this);
        btnCnameEnquire.addActionListener(this);
        btnTeacherEnquire.addActionListener(this);
        btnCreditEnquire.addActionListener(this);
        btnSemesterandSdeptEnquire.addActionListener(this);
    }
    public static void main(String[] args) {
        new WindowManagerCourseAvailable();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnList) {//数据一览
            String sql = "SELECT * FROM Manager_Course_Teacher_manage_view";
            Connect(sql);
        }else if (e.getSource() == btnSemestersEnquire) {
            String sql = "SELECT * FROM Manager_Course_Teacher_manage_view where Semester='"+comSemester.getSelectedItem()+"'";
            Connect(sql);
        }else if (e.getSource() == btnSdeptEnquire) {
            String sql = "SELECT * FROM Manager_Course_Teacher_manage_view where Sde_name='"+comSdept.getSelectedItem().toString().trim()+"'";
            Connect(sql);
        }else if (e.getSource() == btnSemesterandSdeptEnquire) {
            String sql = "SELECT * FROM Manager_Course_Teacher_manage_view where Semester='"+comSemester.getSelectedItem()+"' and Sde_name='"+comSdept.getSelectedItem().toString().trim()+"'";
            Connect(sql);
        }else if (e.getSource() == btnCnoEnquire) {
            String sql = "SELECT * FROM Manager_Course_Teacher_manage_view where Cno='" + txtCno.getText().trim() + "'";
            Connect(sql);
        } else if (e.getSource() == btnCnameEnquire) {
            String sql = "SELECT * FROM Manager_Course_Teacher_manage_view where Cname='" + txtCname.getText().trim() + "'";
            Connect(sql);
        } else if (e.getSource() == btnTeacherEnquire) {
            String sql = "SELECT * FROM Manager_Course_Teacher_manage_view where Emp_Name='" + txtEmp_Name.getText().trim() + "'";
            Connect(sql);
        } else if (e.getSource() == btnCreditEnquire) {
            String sql = "SELECT * FROM Manager_Course_Teacher_manage_view where Credit='" + txtCredit.getText().trim() + "'";
            Connect(sql);
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
}
