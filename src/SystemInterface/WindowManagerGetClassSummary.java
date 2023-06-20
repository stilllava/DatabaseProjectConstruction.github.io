package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WindowManagerGetClassSummary extends JFrame implements ActionListener {
    JLabel labelClassno = new JLabel("班级号");
    JTextField textClassno = new JTextField(10);
    JLabel labelSemester = new JLabel("学期");
    JComboBox comboSemester = new JComboBox();
    JPanel panelNorth = new JPanel();
    JPanel panelCenter = new JPanel();
    JPanel panelSouth = new JPanel();
    JFrame frame1 = new JFrame();
    JButton btnGetClassSummary = new JButton("查询");
    String[] tableTitle = {"学号","姓名","平均绩点"};
    JTable table = new JTable();
    Object[][] data = new Object[getColumns()+1][3];
    public WindowManagerGetClassSummary() {
        frame1.setTitle("班级学生平均绩点汇总");
        frame1.setVisible(true);
        frame1.setSize(480,620);
        frame1.setLocation(10, 10);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panelNorth, BorderLayout.NORTH);
        frame1.add(panelCenter, BorderLayout.CENTER);
        frame1.add(panelSouth, BorderLayout.SOUTH);
        panelNorth.setLayout(new GridLayout(3,2));
        panelNorth.add(labelClassno);panelNorth.add(textClassno);
        panelNorth.add(labelSemester);panelNorth.add(comboSemester);
        //获取当前日期对应的年份
        int date = Integer.parseInt(new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()));
        for(int i=date-10;i<date+2;i++){
            comboSemester.addItem(i+"-"+(i+1)+"年第1学期");
            comboSemester.addItem(i+"-"+(i+1)+"年第2学期");
        }
        comboSemester.setSelectedItem(date+"-"+(date+1)+"年第1学期");
        panelCenter.add(btnGetClassSummary);
        JScrollPane scrollPane = new JScrollPane();
        panelSouth.add(scrollPane, BorderLayout.SOUTH);
        scrollPane.setViewportView(table);
        btnGetClassSummary.addActionListener(this);
    }
    public static void main(String[] args) {
        new WindowManagerGetClassSummary();
    }
    private int getColumns() {
        String Driver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String userName = "s20211576"; // 默认用户名
        String userPwd = "s20211576"; // 密码
        Connection dbConn = null;
        String sql = "EXEC GetClassSummary '" + comboSemester.getSelectedItem() + "','" + textClassno.getText() + "'";
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
            String sql = "EXEC GetClassSummary '"+comboSemester.getSelectedItem()+"','"+textClassno.getText()+"'";
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
                int count = 0;
                while (rs.next()) {
                    data[count][0] = rs.getString("Sno");
                    data[count][1] = rs.getString("Sname");
                    data[count][2] = rs.getString("AverageGPA");
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
