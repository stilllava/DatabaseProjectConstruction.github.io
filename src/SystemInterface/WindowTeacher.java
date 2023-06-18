package SystemInterface;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WindowTeacher extends JFrame implements ActionListener{
    String loginID = windowsRegister.loginID;
    String loginName = windowsRegister.loginName;
    static String sqlScoreManage = "";
    JFrame frame1 = new JFrame();
    JPanel panelNorth = new JPanel();
    JPanel panelSouth = new JPanel();
    JPanel panelCenter = new JPanel();
    JLabel labelNo = new JLabel("工号");
    JLabel txtNo;
    JLabel labelName = new JLabel("姓名");
    JLabel txtName;
    JLabel labelSex = new JLabel("性别");
    JLabel txtSex;
    JLabel labelSdeNo = new JLabel("所在系");
    JLabel txtSdeNo;
    JLabel labelSdeName = new JLabel("系名");
    JLabel txtSdeName;
    JLabel labelPosition = new JLabel("职称");
    JLabel txtPosition;
    JButton btnEnquiry = new JButton("授课列表");
    JButton btnScoreManage = new JButton("成绩管理");
    JTable table = new JTable();
    String[] columnNames = {"学期","允许选课系","系名","课程号","课程名","学分","可选课人数"};
    Object[][] data = new Object[getColumns()][7];
    Object[] TeacherRelated = new Object[6];
    public WindowTeacher() {
        System.out.println(loginID);
        System.out.println(loginName);
        getInformation(TeacherRelated);
        txtNo = new JLabel((String)TeacherRelated[0]);
        txtName = new JLabel((String)TeacherRelated[1]);
        txtSex = new JLabel((String)TeacherRelated[2]);
        txtSdeNo = new JLabel((String)TeacherRelated[3]);
        txtSdeName = new JLabel((String)TeacherRelated[4]);
        txtPosition = new JLabel((String)TeacherRelated[5]);
        System.out.println(TeacherRelated[0]+" "+TeacherRelated[1]+" "+TeacherRelated[2]+" "+TeacherRelated[3]+" "+TeacherRelated[4]+" "+TeacherRelated[5]);
        table = new JTable(data, columnNames);
        frame1.setTitle("教务管理系统-教师界面");
        frame1.setVisible(true);
        frame1.setSize(680, 540);
        frame1.setLocation(10, 10);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panelNorth, BorderLayout.NORTH);
        frame1.add(panelCenter, BorderLayout.CENTER);
        frame1.add(panelSouth, BorderLayout.SOUTH);
        panelNorth.setLayout(new GridLayout(3, 4));
        panelNorth.add(labelNo);panelNorth.add(txtNo);
        panelNorth.add(labelName);panelNorth.add(txtName);
        panelNorth.add(labelSex);panelNorth.add(txtSex);
        panelNorth.add(labelSdeNo);panelNorth.add(txtSdeNo);
        panelNorth.add(labelSdeName);panelNorth.add(txtSdeName);
        panelNorth.add(labelPosition);panelNorth.add(txtPosition);
        panelCenter.add(btnEnquiry);panelCenter.add(btnScoreManage);
        JScrollPane scrollPane = new JScrollPane(table);
        frame1.add(scrollPane, BorderLayout.SOUTH);
        scrollPane.setViewportView(table);
        btnEnquiry.addActionListener(this);
        btnScoreManage.addActionListener(this);
        // 添加监听器，监听表格的行选中事件
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // 获取当前选中行的索引
                int row = table.getSelectedRow();
                if (row >= 0) {
                    // 获取选中行的各列的数据
                    String insertSemester = table.getValueAt(row, 0).toString();
                    String insertEmpNo = loginID.toString();
                    String insertEmpName = table.getValueAt(row, 1).toString();
                    String insertSex = table.getValueAt(row, 3).toString();
                    String insertSdeNo = table.getValueAt(row, 4).toString();
                    String insertSdeName = table.getValueAt(row, 5).toString();
                    String insertPosition = table.getValueAt(row, 6).toString();
                    String sqlScoreManage = "use Academic_Affairs_Management_System_20211576 select * from Teacher_Score_Manage_view where Emp_no = '" + loginID.trim() + "'";
                }
            }
        });
    }


    public static void main(String[] args) {
        new WindowTeacher();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEnquiry) {
            Enquiry();
        }
        if (e.getSource() == btnScoreManage){
            //如果没有选中行，则弹出提示框
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "请先选中课程再进行此操作！", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            WindowTeacherScoreManage windowTeacherScoreManage = new WindowTeacherScoreManage();
        }
    }
    public void Enquiry(){
        String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Course_Teacher_manage_view where Emp_name = '" + loginName.trim() + "'";
        System.out.println(sql);
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        Connection dbConn = null;
        try {
            Class.forName(Driver);
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
                data[0][0] = rs.getString("Semester");
                data[0][1] = rs.getString("Course_Offered_Sde_no");
                data[0][2] = rs.getString("Sde_name");
                data[0][3] = rs.getString("Cno");
                data[0][4] = rs.getString("Cname");
                data[0][5] = rs.getString("Credit");
                data[0][6] = rs.getString("Stu_total");
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

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        table.setModel(model);
    }
    public int getColumns () {
        String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        Connection dbConn = null;
        try {
            Class.forName(Driver);
            dbConn = DriverManager.getConnection(url, userName, userPwd);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int columnCount;
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery("use Academic_Affairs_Management_System_20211576 select * from Manager_Course_Teacher_manage_view where Emp_name = '" + loginName.trim() + "'");
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
    public void getInformation(Object[] TeacherRelated){
        String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        String sql = "use Academic_Affairs_Management_System_20211576 select * from Teacher_view where Emp_no = '" + loginID.trim() + "'";
        System.out.println(sql);
        Connection dbConn = null;
        try {
            Class.forName(Driver);
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
                TeacherRelated[0] = rs.getString("Emp_no");
                TeacherRelated[1] = rs.getString("Emp_name");
                TeacherRelated[2] = rs.getString("Sex");
                TeacherRelated[3] = rs.getString("Sde_no");
                TeacherRelated[4] = rs.getString("Sde_name");
                TeacherRelated[5] = rs.getString("Position");
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
