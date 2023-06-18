package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WindowManagerInformationUpdateTeacher extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JFrame frame1 = new JFrame();
    JPanel panelNorth = new JPanel();
    JLabel labelNo = new JLabel("教工号");
    JTextField textNo = new JTextField(12);
    JLabel labelName = new JLabel("姓名");
    JTextField textName = new JTextField(12);
    JLabel labelSex = new JLabel("性别");
    JTextField textSex = new JTextField(12);
    JLabel labelSdept = new JLabel("所属系别编号");
    JTextField textSdept = new JTextField(12);
    JLabel labelPosition = new JLabel("职位");
    JTextField textPosition = new JTextField(12);
    JButton btnAdd = new JButton("添加");
    JButton btnUpdate = new JButton("修改");
    JButton btnSearch = new JButton("查询");
    JButton btnInsert = new JButton("从excel表/.csv文件中读取信息");
    JTable table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);
    String [] columnNames = {"教工号","姓名","性别","所属系别编号","职位"};
    Object[][] data = new Object[getColumns()][10];
    public WindowManagerInformationUpdateTeacher() {
        frame1.setTitle("教务管理系统管理员界面-信息维护功能-教师信息维护");
        System.out.println(getColumns());
        frame1.setVisible(true);
        frame1.setSize(540,640);
        frame1.setLocation(10,10);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        table.setSize(350,400);
        scrollPane.setBounds(0,0,352,250);
        frame1.add(panel);
        panel.add(panelNorth, BorderLayout.NORTH);
        panelNorth.setLayout(new GridLayout(11,2));
        panelNorth.add(labelNo);panelNorth.add(textNo);
        panelNorth.add(labelName);panelNorth.add(textName);
        panelNorth.add(labelSex);panelNorth.add(textSex);
        panelNorth.add(labelSdept);panelNorth.add(textSdept);
        panelNorth.add(labelPosition);panelNorth.add(textPosition);
        panelNorth.add(btnAdd);
        panelNorth.add(btnUpdate);panelNorth.add(btnSearch);
        panelNorth.add(btnInsert);
        frame1.add(scrollPane, BorderLayout.SOUTH);
        scrollPane.setViewportView(table);
        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnSearch.addActionListener(this);
        btnInsert.addActionListener(this);
    }
    public static void main(String[] args) {
        new WindowManagerInformationUpdateTeacher();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd){
            if(textNo.getText().trim().equals("") == true
                    && textName.getText().trim().equals("") == true
                    && textSex.getText().trim().equals("") == true
                    && textSdept.getText().trim().equals("") == true
                    && textPosition.getText().trim().equals("") == true){}
            else {
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = "insert into Academic_Affairs_Management_System_20211576.dbo.Teacher_20211576(Emp_no,Emp_name,Sex,Sde_no,Position) values('"+textNo.getText()+"','"+textName.getText()+"','"+textSex.getText()+"','"+textSdept.getText()+"','"+textPosition.getText()+"')";
                System.out.println(sql);
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    System.out.println(sql); // 如果连接成功 控制台输出
                    Statement stmt = dbConn.createStatement();
                    JOptionPane.showMessageDialog(null, "工号为"+textNo.getText()+"的教师信息添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
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
        }
        if (e.getSource() == btnUpdate){
            if(textNo.getText().trim().equals("") == false
                    &&(textName.getText().trim().equals("") == true
                    || textSex.getText().trim().equals("") == true
                    || textSdept.getText().trim().equals("") == true
                    || textPosition.getText().trim().equals("") == true)){
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = null;
                if(textName.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Teacher_20211576 set Emp_name = '"+textName.getText()+"' where Emp_no = '"+textNo.getText()+"'";
                else if(textSex.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Teacher_20211576 set Sex = "+textSex.getText()+" where Emp_no = '"+textNo.getText()+"'";
                else if(textSdept.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Teacher_20211576 set Sdept = '"+textSdept.getText()+"' where Emp_no = '"+textNo.getText()+"'";
                else if(textPosition.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Teacher_20211576 set Position = '"+textPosition.getText()+"' where Emp_no = '"+textNo.getText()+"'";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    System.out.println(sql); // 如果连接成功 控制台输出
                    JOptionPane.showMessageDialog(null, "工号为"+textNo.getText()+"的教师信息修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
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
        }
        if (e.getSource() == btnSearch){
            //清空表格
            for (int i = 0; i < getColumns(); i++) {
                for (int j = 0; j < 10; j++) {
                    data[i][j] = "";
                }
            }
            if (textNo.getText().toString().equals("") == true
                    && textName.getText().toString().equals("") == true
                    && textSex.getText().toString().equals("") == true
                    && textSdept.getText().toString().equals("") == true
                    && textPosition.getText().toString().equals("") == true){
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Teacher_view";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                     // 如果连接成功 控制台输出
                    Statement stmt = dbConn.createStatement();
                    System.out.println(sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    // Get the number of columns in the ResultSet
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int numColumns = rsmd.getColumnCount();

                    // Retrieve all rows of data from the ResultSet and store in the data array
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
            }
            else {
                //在数据库中进行查询,先对textNo中内容进行查询，当textNo中为空时方可对textName进行查询,往后以此类推
                int flag = 0;
                String Input = null;
                String sql = null;
                if (textNo.getText().toString().equals("") == false) {
                    Input = textNo.getText().toString();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Teacher_view where Emp_no = " + "'" +Input + "'";
                    flag = 1;
                } else if (textName.getText().toString().equals("") == false) {
                    Input = textName.getText().toString();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Teacher_view where Emp_name = " + "'" +Input + "'";
                    flag = 2;
                } else if (textSex.getText().toString().equals("") == false) {
                    Input = textSex.getText().toString();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Teacher_view where Sex = " + "'" +Input + "'";
                } else if (textSdept.getText().toString().equals("") == false) {
                    Input = textSdept.getText().toString();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Teacher_view where Sde_no = " +"''"+ Input + "''";
                    flag = 4;
                } else if (textPosition.getText().toString().equals("") == false) {
                    Input = textPosition.getText().toString();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Teacher_view where Position = " + "'" +Input + "'";
                    flag = 5;
                }
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                     // 如果连接成功 控制台输出
                    Statement stmt = dbConn.createStatement();
                    System.out.println(sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    int count = 0;
                    while(rs.next()){
                        data[count][0] = rs.getString("Emp_no");
                        data[count][1] = rs.getString("Emp_name");
                        data[count][2] = rs.getString("Sex");
                        data[count][3] = rs.getString("Sde_no");
                        data[count][4] = rs.getString("Position");
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
            // Create a new DefaultTableModel with the data and column names
            DefaultTableModel model = new DefaultTableModel(data, columnNames);

            // Set the model for the JTable
            table.setModel(model);
        }
        if (e.getSource() == btnInsert){
            WindowManagerInformationUpdateStudentInsert insert = new WindowManagerInformationUpdateStudentInsert();
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
             // 如果连接成功 控制台输出
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int columnCount;
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery("use Academic_Affairs_Management_System_20211576 select * from Manager_Teacher_view");
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

