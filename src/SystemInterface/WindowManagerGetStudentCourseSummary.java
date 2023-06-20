package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WindowManagerGetStudentCourseSummary extends JFrame implements ActionListener {
    JPanel panelNorth = new JPanel();
    JPanel panelCenter = new JPanel();
    JPanel panelSouth = new JPanel();
    JFrame frame1 = new JFrame();
    JLabel labelStudentID = new JLabel("学生学号");
    JTextField textFieldStudentID = new JTextField(10);
    JLabel labelSemester = new JLabel("学期");
    JComboBox comboSemester = new JComboBox();
    JButton btnGetStudentCourseSummary = new JButton("查询");
    String[] tableTitle = {"学号", "所在学期", "总学分", "总学时", "已获得学分", "平均成绩"};
    JTable table = new JTable();
    Object[][] data = new Object[1][6];
    public WindowManagerGetStudentCourseSummary() {
        frame1.setTitle("学生选修课程成绩汇总");
        frame1.setVisible(true);
        frame1.setSize(480,620);
        frame1.setLocation(10, 10);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panelNorth, BorderLayout.NORTH);
        frame1.add(panelCenter, BorderLayout.CENTER);
        frame1.add(panelSouth, BorderLayout.SOUTH);
        panelNorth.setLayout(new GridLayout(3,2));
        panelNorth.add(labelStudentID);panelNorth.add(textFieldStudentID);
        panelNorth.add(labelSemester);panelNorth.add(comboSemester);
        //获取当前日期对应的年份
        int date = Integer.parseInt(new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()));
        for(int i=date-10;i<date+2;i++){
            comboSemester.addItem(i+"-"+(i+1)+"年第1学期");
            comboSemester.addItem(i+"-"+(i+1)+"年第2学期");
        }
        comboSemester.setSelectedItem(date+"-"+(date+1)+"年第1学期");
        panelCenter.add(btnGetStudentCourseSummary);
        JScrollPane scrollPane = new JScrollPane();
        panelSouth.add(scrollPane, BorderLayout.SOUTH);
        scrollPane.setViewportView(table);
        btnGetStudentCourseSummary.addActionListener(this);
    }
    public static void main(String[] args) {
        new WindowManagerGetStudentCourseSummary();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnGetStudentCourseSummary){
            String Driver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
            String userName = "s20211576"; // 默认用户名
            String userPwd = "s20211576"; // 密码
            Connection dbConn = null;
            try {
                Class.forName(Driver2);
                dbConn = DriverManager.getConnection(url, userName, userPwd);
            } catch (ClassNotFoundException e0) {
                throw new RuntimeException(e0);
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
            int columnCount;
            try {
                Statement stmt = dbConn.createStatement();
                ResultSet rs = stmt.executeQuery("EXEC GetStudentCourseSummary '" + comboSemester.getSelectedItem() + "', '" + textFieldStudentID.getText() + "'");
                ResultSetMetaData rsmd = rs.getMetaData();
                while (rs.next()) {
                    data[0][0]  = rs.getString(1);
                    data[0][1]  = rs.getString(2);
                    data[0][2]  = rs.getString(3);
                    data[0][3]  = rs.getString(4);
                    data[0][4]  = rs.getString(5);
                    data[0][5]  = rs.getString(6);
                }

            } catch (SQLException e2) {
                throw new RuntimeException(e2);
            } finally {
                try {
                    dbConn.close();
                } catch (SQLException e3) {
                    throw new RuntimeException(e3);
                }
            }
            DefaultTableModel model = new DefaultTableModel(data, tableTitle);
            table.setModel(model);
        }
    }
    public void getSno(String sname){
        String Driver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        Connection dbConn = null;
        String sql = "SELECT Sno FROM Manager_Student_view WHERE Sname = '" + sname + "'";
        try {
            Class.forName(Driver2);
            dbConn = DriverManager.getConnection(url, userName, userPwd);
        } catch (ClassNotFoundException e0) {
            throw new RuntimeException(e0);
        } catch (SQLException e1) {
            throw new RuntimeException(e1);
        }
        int columnCount;
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                textFieldStudentID.setText(rs.getString("Sno"));
            }
        } catch (SQLException e2) {
            throw new RuntimeException(e2);
        } finally {
            try {
                dbConn.close();
            } catch (SQLException e3) {
                throw new RuntimeException(e3);
            }
        }
    }
}
