package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WindowManagerCountTeachingHours extends JFrame implements ActionListener {
    JLabel labelSemester = new JLabel("学期");
    JComboBox comboSemester = new JComboBox();
    JPanel panelNorth = new JPanel();
    JPanel panelCenter = new JPanel();
    JPanel panelSouth = new JPanel();
    JFrame frame1 = new JFrame();
    JButton btnGetClassSummary = new JButton("查询");
    String[] tableTitle = {"工号","学期","授课数","授课总学时"};
    JTable table = new JTable();
    Object[][] data;

    public WindowManagerCountTeachingHours() {
        frame1.setTitle("教师授课总学时统计");
        frame1.setVisible(true);
        frame1.setSize(480,580);
        frame1.setLocation(10, 10);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panelNorth, BorderLayout.NORTH);
        frame1.add(panelCenter, BorderLayout.CENTER);
        frame1.add(panelSouth, BorderLayout.SOUTH);
        panelNorth.setLayout(new GridLayout(1,2));
        panelNorth.add(labelSemester);panelNorth.add(comboSemester);
        //获取当前日期对应的年份
        int date = Integer.parseInt(new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()));
        for(int i=date-10;i<date+2;i++){
            comboSemester.addItem(i+"-"+(i+1)+"年第1学期");
            comboSemester.addItem(i+"-"+(i+1)+"年第2学期");
        }
        comboSemester.setSelectedItem(date+"-"+(date+1)+"年第1学期");
        panelCenter.add(btnGetClassSummary);
        data = new Object[getColumns()][4];
        JScrollPane scrollPane = new JScrollPane();
        panelSouth.add(scrollPane, BorderLayout.SOUTH);
        scrollPane.setViewportView(table);
        btnGetClassSummary.addActionListener(this);
    }
    public static void main(String[] args) {
        new WindowManagerCountTeachingHours();
    }

    private int getColumns() {
        String Driver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        Connection dbConn = null;
        String sql = "EXEC sp_CountTeachingHours '" + comboSemester.getSelectedItem() + "'";
        System.out.println(sql);
        try {
            Class.forName(Driver2);
            dbConn = DriverManager.getConnection(url, userName, userPwd);
        } catch (ClassNotFoundException e0) {
            throw new RuntimeException(e0);
        } catch (SQLException e1) {
            throw new RuntimeException(e1);
        }
        int count;
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            count = 0;
            while (rs.next()) {
                count++;
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
        System.out.println(count);
        return count;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnGetClassSummary){
            String Driver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
            String userName = "s20211576"; // 默认用户名
            String userPwd = "s20211576"; // 密码
            Connection dbConn = null;
            String sql = "EXEC sp_CountTeachingHours '" + comboSemester.getSelectedItem() + "'";
            try {
                Class.forName(Driver2);
                dbConn = DriverManager.getConnection(url, userName, userPwd);
            } catch (ClassNotFoundException e0) {
                throw new RuntimeException(e0);
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
            try {
                Statement stmt = dbConn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData rsmd = rs.getMetaData();
                int i = 0;
                while (rs.next()) {
                    data[i][0] = rs.getString(1);
                    data[i][1] = comboSemester.getSelectedItem();
                    data[i][2] = rs.getString(2);
                    data[i][3] = rs.getString(3);
                    i++;
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
}
