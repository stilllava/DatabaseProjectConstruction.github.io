package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WindowManagerInformationUpdateClass extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JFrame frame1 = new JFrame();
    JPanel panelNorth = new JPanel();
    JPanel panelCenter = new JPanel();
    JPanel panelSouth = new JPanel();
    JLabel labelClassNo = new JLabel("班级号");
    JTextField textClassNo = new JTextField(12);
    JLabel labelSdeNo = new JLabel("所属系号");
    JTextField textSdeNo = new JTextField(12);
    JLabel labelEntranceDate = new JLabel("入学时间");
    JTextField textEntranceDate = new JTextField(12);
    JLabel labelNum = new JLabel("学生人数");
    JTextField textNum = new JTextField(12);
    JButton btnAdd = new JButton("添加");
    JButton btnUpdate = new JButton("修改");
    JButton btnSearch = new JButton("查询");
    JTable table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);
    String[] columnNames = {"班级号", "所属系号", "入学时间", "学生人数"};
    Object[][] data = new Object[getColumns()][10];

    public WindowManagerInformationUpdateClass() {
        frame1.setTitle("教务管理系统管理员界面-信息维护功能-班级信息维护");
        System.out.println(getColumns());
        frame1.setVisible(true);
        frame1.setSize(660, 500);
        frame1.setLocation(10, 10);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        table.setSize(350, 400);
        scrollPane.setBounds(0, 0, 352, 250);
        frame1.add(panel);
        panel.add(panelNorth, BorderLayout.NORTH);
        panel.add(panelCenter, BorderLayout.CENTER);
        panel.add(panelSouth, BorderLayout.SOUTH);
        panelNorth.setLayout(new GridLayout(11, 1));
        panelNorth.add(labelClassNo);
        panelNorth.add(textClassNo);
        panelNorth.add(labelSdeNo);
        panelNorth.add(textSdeNo);
        panelNorth.add(labelEntranceDate);
        panelNorth.add(textEntranceDate);
        panelNorth.add(labelNum);
        panelNorth.add(textNum);
        panelNorth.add(btnAdd);
        panelNorth.add(btnUpdate);
        panelNorth.add(btnSearch);
        panelCenter.add(scrollPane);
        scrollPane.setViewportView(table);
        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnSearch.addActionListener(this);
    }

    public static void main(String[] args) {
        new WindowManagerInformationUpdateClass();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            if (textClassNo.getText().trim().equals("") == false
                    && textSdeNo.getText().trim().equals("") == false
                    && textEntranceDate.getText().trim().equals("") == false
                    && textNum.getText().trim().equals("") == false) {
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql0 = "use Academic_Affairs_Management_System_20211576 select * from Manager_Class_view where Class_no ='" + textClassNo.getText() + "'";
                String sql = "insert into Academic_Affairs_Management_System_20211576.dbo.Class_20211576(Class_no,Sde_no,Entrance_date,Num) values('" + textClassNo.getText() + "','" + textSdeNo.getText() + "','" + textEntranceDate.getText() + "'," + textNum.getText() + ")";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    System.out.println(sql0); // 如果连接成功 控制台输出
                    System.out.println(sql);
                    Statement stmt = dbConn.createStatement();
                    ResultSet rs0 = stmt.executeQuery(sql0);
                    String Class_no = "";
                    while (rs0.next()) {
                        Class_no += rs0.getString("Class_no");
                    }
                    if (Class_no.equals("") == false) {
                        JOptionPane.showMessageDialog(null, "班级号为" + textClassNo.getText() + "的班级信息已存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "班级号为" + textClassNo.getText() + "的班级信息添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        ResultSet rs = stmt.executeQuery(sql);
                        rs.close();
                    }
                    rs0.close();
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
            } else {
                JOptionPane.showMessageDialog(null, "请填写完整信息!");
            }
        }
        if (e.getSource() == btnUpdate) {
            if (textClassNo.getText().trim().equals("") == false) {
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = null;
                if (textSdeNo.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Class_20211576 set Sde_no = '" + textSdeNo.getText() + "' where Class_no = '" + textClassNo.getText() + "'";
                else if (textEntranceDate.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Class_20211576 set Entrance_date = '" + textEntranceDate.getText() + "' where Class_no = '" + textClassNo.getText() + "'";
                else if (textNum.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Class_20211576 set Num = " + textNum.getText() + " where Class_no = '" + textClassNo.getText() + "'";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    System.out.println(sql); // 如果连接成功 控制台输出
                    JOptionPane.showMessageDialog(null, "班级号为" + textClassNo.getText() + "的班级信息修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    Statement stmt = dbConn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
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
            }
            else{
                JOptionPane.showMessageDialog(null, "班级号和（所属系号/入学时间/学生人数中至少有一项）不为空!", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (e.getSource() == btnSearch) {
            //清空表格
            for (int i = 0; i < getColumns(); i++) {
                for (int j = 0; j < 10; j++) {
                    data[i][j] = "";
                }
            }
            if (textClassNo.getText().equals("") == true
                    && textSdeNo.getText().equals("") == true
                    && textEntranceDate.getText().equals("") == true
                    && textNum.getText().equals("") == true) {
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Class_view";
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
                            if (col == 2) {
                                data[row][col] = rs.getString(col + 1).substring(0, 10);
                            } else {
                                data[row][col] = rs.getString(col + 1);
                            }
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
            } else {
                int flag = 0;
                String Input = null;
                String sql = null;
                if (textClassNo.getText().equals("") == false) {
                    Input = textClassNo.getText();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Class_view where Class_no = " + "'" + Input + "'";
                    flag = 1;
                } else if (textSdeNo.getText().equals("") == false) {
                    Input = textSdeNo.getText();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Class_view where Sde_no = " + "'" + Input + "'";
                    flag = 2;
                } else if (textEntranceDate.getText().equals("") == false) {
                    Input = textEntranceDate.getText();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Class_view where Entrance_date = " + "'" + Input + "'";
                } else if (textNum.getText().equals("") == false) {
                    Input = textNum.getText();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Class_view where Num = " + Input;
                    flag = 4;
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
                    int count = 0;
                    while (rs.next()) {
                        data[count][0] = rs.getString("Class_no");
                        data[count][1] = rs.getString("Sde_no");
                        data[count][2] = rs.getString("Entrance_date");
                        data[count][3] = rs.getString("Num");
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
            }
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            table.setModel(model);
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
            ResultSet rs = stmt.executeQuery("use Academic_Affairs_Management_System_20211576 select * from Manager_Class_view");
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