package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WindowTeacherScoreManage extends JFrame implements ActionListener{
    String loginID = windowsRegister.loginID;
    String loginName = windowsRegister.loginName;
    JPanel panelNorth = new JPanel();
    JPanel panelCenter = new JPanel();
    JPanel panelSouth = new JPanel();
    JFrame frame1 = new JFrame();
    JLabel labelSemester = new JLabel("学期");
    JLabel txtLabelSemester;
    JLabel labelCno = new JLabel("课程号");
    JLabel txtLabelCno;
    JLabel labelCname = new JLabel("课程名");
    JLabel txtLabelCname;
    JLabel labelEmpname = new JLabel("教师姓名");
    JLabel txtLabelEmpname = new JLabel(loginName);
    JLabel labelScore = new JLabel("分数");
    JTextField txtLabelScore = new JTextField(12);
    JButton btnInsertScore = new JButton("成绩录入");
    JTable table = new JTable();
    String[] columnNames = {"学期","课程号","课程名","教师姓名","学号","分数"};
    Object[][] data = new Object[getColumns()][6];
    Object[] TeacherRelated = new Object[3];
    public WindowTeacherScoreManage() {
        getInformation(TeacherRelated);
        txtLabelSemester = new JLabel((String)TeacherRelated[0]);
        txtLabelCno = new JLabel((String)TeacherRelated[1]);
        txtLabelCname = new JLabel((String)TeacherRelated[2]);
        frame1.setTitle("教务管理系统-教师界面-'"+txtLabelCname.getText()+"'课程成绩管理界面");
        frame1.setVisible(true);
        frame1.setSize(800,600);
        frame1.setLocation(300, 249);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getTable();
        frame1.add(panelNorth, BorderLayout.NORTH);
        frame1.add(panelCenter, BorderLayout.CENTER);
        frame1.add(panelSouth, BorderLayout.SOUTH);
        panelNorth.setLayout(new GridLayout(5,2));
        panelNorth.add(labelSemester);panelNorth.add(txtLabelSemester);
        panelNorth.add(labelCno);panelNorth.add(txtLabelCno);
        panelNorth.add(labelCname);panelNorth.add(txtLabelCname);
        panelNorth.add(labelEmpname);panelNorth.add(txtLabelEmpname);
        panelNorth.add(labelScore);panelNorth.add(txtLabelScore);
        panelCenter.add(btnInsertScore, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(table);
        frame1.add(scrollPane,BorderLayout.SOUTH);
        scrollPane.setViewportView(table);
        btnInsertScore.addActionListener(this);
    }
    public static void main(String[] args) {
        new WindowTeacherScoreManage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnInsertScore){
            //如果没有选中行，弹窗提示
            if(table.getSelectedRow() == -1){
                JOptionPane.showMessageDialog(null, "请选中学生后再进行赋分!", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                //如果选中行，若有成绩则弹窗无法修改，若无成绩则直接录入
                if(table.getValueAt(table.getSelectedRow(),5) != null){
                    JOptionPane.showMessageDialog(null, "该学生已有成绩，无法修改!（请联系教务进行修改!）", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null, "录入成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
                    //将录入的分数写入数据库
                    String sql = "use Academic_Affairs_Management_System_20211576 update Teacher_Manage_SC_view set Grade = " + txtLabelScore.getText().trim() + " where Sno = '" + table.getValueAt(table.getSelectedRow(),4).toString().trim() + "' and Cno = '" + txtLabelCno.getText().trim() + "' and Semester = '" + txtLabelSemester.getText().trim() + "' and Emp_no = '" + loginID.trim() + "'";
                    System.out.println(sql);
                    GiveGrade(sql);
                    //刷新表格
                    getTable();
                }
            }
        }
    }
    public void GiveGrade(String sql){
        String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
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
            stmt.executeUpdate(sql);
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
    public void getInformation(Object[] TeacherRelated){
        String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        String sql = "use Academic_Affairs_Management_System_20211576 select * from Teacher_Score_Manage_view where Emp_no = '" + loginID.trim() + "'";
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
                TeacherRelated[0] = rs.getString("Semester");
                TeacherRelated[1] = rs.getString("Cno");
                TeacherRelated[2] = rs.getString("Cname");
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
    public void getTable(){
        String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        String sql = "use Academic_Affairs_Management_System_20211576 select * from Teacher_Manage_SC_view where Emp_name = '" + loginName.trim() + "' and Semester = '" + txtLabelSemester.getText().trim() + "' and Cno = '" + txtLabelCno.getText().trim() + "' and Cname = '" + txtLabelCname.getText().trim() + "'";
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
            int i = 0;
            while (rs.next()) {
                //String[] columnNames = {"学期","课程号","课程名","教师姓名","学号","分数"};
                data[i][0] = rs.getString("Semester");
                data[i][1] = rs.getString("Cno");
                data[i][2] = rs.getString("Cname");
                data[i][3] = rs.getString("Emp_name");
                data[i][4] = rs.getString("Sno");
                data[i][5] = rs.getString("Grade");
                i++;
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames);

            table.setModel(model);

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
            ResultSet rs = stmt.executeQuery("use Academic_Affairs_Management_System_20211576 select * from Teacher_Score_Manage_view where Emp_no = '" + loginID.trim() + "'");
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
