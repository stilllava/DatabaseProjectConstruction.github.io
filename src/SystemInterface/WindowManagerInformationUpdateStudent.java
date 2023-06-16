package SystemInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WindowManagerInformationUpdateStudent extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JFrame frame1 = new JFrame();
    JPanel panelNorth = new JPanel();
    JPanel panelCenter = new JPanel();
    JPanel panelSouth = new JPanel();
    JLabel labelNo = new JLabel("学号");
    JTextField textNo = new JTextField(12);
    JLabel labelName = new JLabel("姓名");
    JTextField textName = new JTextField(12);
    JLabel labelSex = new JLabel("性别");
    JTextField textSex = new JTextField(12);
    JLabel labelBirth = new JLabel("出生日期");
    JTextField textBirth = new JTextField(12);
    JLabel labelEntrancedate = new JLabel("入学日期");
    JTextField textEntrancedate = new JTextField(12);
    JLabel labelClassno = new JLabel("班级号");
    JTextField textClassno = new JTextField(12);
    JLabel labelHomeaddress = new JLabel("家庭住址");
    JTextField textHomeaddress = new JTextField(50);
    JLabel labelSdept = new JLabel("所在系");
    JTextField textSdept = new JTextField(12);
    JLabel labelPostcode = new JLabel("邮政编码");
    JTextField textPostcode = new JTextField(12);
    /*
    JLabel labelStatusAttendance = new JLabel("就读状态");
    JTextField textStatusAttendance = new JTextField(12);
     */
    JButton btnAdd = new JButton("添加");
    JButton btnUpdate = new JButton("修改");
    JButton btnSearch = new JButton("查询");
    JLabel labelWarning1 = new JLabel("提示：当上方所有项为空时查询键为查询所有信息，否则为查询指定信息" );
    JLabel labelWarning2 = new JLabel("出生日期/入学日期格式为yyyy-mm-dd/(yyyy/mm/dd)");
    JButton btnInsert = new JButton("从excel表/.csv文件中读取信息");
    JTable table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);
    String [] columnNames = {"学号","姓名","性别","出生日期","入学日期","班级号","家庭住址","所在系","邮政编码","就读状态"};
    Object[][] data = new Object[getColumns()][10];
    public WindowManagerInformationUpdateStudent() {
        frame1.setTitle("教务管理系统管理员界面-信息维护功能-学生信息维护");
        System.out.println(getColumns());
        frame1.setVisible(true);
        frame1.setSize(1200,800);
        frame1.setLocation(600, 800);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        table.setSize(350,400);
        scrollPane.setBounds(0,0,352,250);
        frame1.add(panel);
        panel.add(panelNorth, BorderLayout.NORTH);
        panel.add(panelCenter, BorderLayout.CENTER);
        panel.add(panelSouth, BorderLayout.SOUTH);
        panelNorth.setLayout(new GridLayout(1,2));
        panelNorth.setLayout(new GridLayout(9,2));
        panelNorth.add(labelNo);panelNorth.add(textNo);
        panelNorth.add(labelName);panelNorth.add(textName);
        panelNorth.add(labelSex);panelNorth.add(textSex);
        panelNorth.add(labelBirth);panelNorth.add(textBirth);
        panelNorth.add(labelEntrancedate);panelNorth.add(textEntrancedate);
        panelNorth.add(labelClassno);panelNorth.add(textClassno);
        panelNorth.add(labelHomeaddress);panelNorth.add(textHomeaddress);
        panelNorth.add(labelSdept);panelNorth.add(textSdept);
        panelNorth.add(labelPostcode);panelNorth.add(textPostcode);
        panelCenter.setLayout(new GridLayout(6,1));
        panelCenter.add(btnAdd);
        panelCenter.add(btnUpdate);panelCenter.add(btnSearch);
        panelCenter.add(labelWarning1);panelCenter.add(labelWarning2);
        panelSouth.add(scrollPane);
        scrollPane.setViewportView(table);
        panelSouth.add(btnInsert);
        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnSearch.addActionListener(this);
        btnInsert.addActionListener(this);
    }
    public static void main(String[] args) {
        new WindowManagerInformationUpdateStudent();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd){
            if(textNo.getText().trim().equals("") == true
            && textName.getText().trim().equals("") == true
            && textSex.getText().trim().equals("") == true
            && textBirth.getText().trim().equals("") == true
            && textEntrancedate.getText().trim().equals("") == true
            && textClassno.getText().trim().equals("") == true
            && textHomeaddress.getText().trim().equals("") == true
            && textSdept.getText().trim().equals("") == true){}
            else {
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = "insert into Academic_Affairs_Management_System_20211576.dbo.Student_20211576(Sno,Sname,Sex,Birth,Entrance_date,Class_no,Home_addr,Sde_no,Postcode) values('"+textNo.getText()+"','"+textName.getText()+"','"+textSex.getText()+"',"+textBirth.getText()+","+textEntrancedate.getText()+",'"+textClassno.getText()+"','"+textHomeaddress.getText()+"','"+textSdept.getText()+"','"+textPostcode.getText()+"')";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    System.out.println(sql); // 如果连接成功 控制台输出
                    Statement stmt = dbConn.createStatement();
                    JOptionPane.showMessageDialog(null, "学号为"+textNo+"的同学信息添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
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
            &&(textName.getText().trim().equals("") == false
                    || textSex.getText().trim().equals("") == false
                    || textBirth.getText().trim().equals("") == false
                    || textEntrancedate.getText().trim().equals("") == false
                    || textClassno.getText().trim().equals("") == false
                    || textHomeaddress.getText().trim().equals("") == false
                    || textSdept.getText().trim().equals("") == false)){
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = null;
                if(textName.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Student_20211576 set Sname = '"+textName.getText()+"' where Sno = '"+textNo.getText()+"'";
                else if(textSex.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Student_20211576 set Sex = '"+textSex.getText()+"' where Sno = '"+textNo.getText()+"'";
                else if(textBirth.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Student_20211576 set Birth = "+textBirth.getText()+" where Sno = '"+textNo.getText()+"'";
                else if(textEntrancedate.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Student_20211576 set Entrance_date = '"+textEntrancedate.getText()+"' where Sno = '"+textNo.getText()+"'";
                else if(textClassno.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Student_20211576 set Class_no = '"+textClassno.getText()+"' where Sno = '"+textNo.getText()+"'";
                else if(textHomeaddress.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Student_20211576 set Home_addr = '"+textHomeaddress.getText()+"' where Sno = '"+textNo.getText()+"'";
                else if(textSdept.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Student_20211576 set Sde_no = '"+textSdept.getText()+"' where Sno = '"+textNo.getText()+"'";
                else if(textPostcode.getText().trim().equals("") == false)
                    sql = "update Academic_Affairs_Management_System_20211576.dbo.Student_20211576 set Postcode = '"+textPostcode.getText()+"' where Sno = '"+textNo.getText()+"'";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    System.out.println(sql); // 如果连接成功 控制台输出
                    JOptionPane.showMessageDialog(null, "学号为"+textNo.getText()+"的同学信息修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
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
            if (textNo.getText().equals("") == true
                    && textName.getText().equals("") == true
                    && textSex.getText().equals("") == true
                    && textBirth.getText().equals("") == true
                    && textEntrancedate.getText().equals("") == true
                    && textClassno.getText().equals("") == true
                    && textHomeaddress.getText().equals("") == true
                    && textSdept.getText().equals("") == true
                    && textPostcode.getText().equals("") == true){
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Student_view";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    System.out.println("Connection Successful!"); // 如果连接成功 控制台输出
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
                if (textNo.getText().equals("") == false) {
                    Input = textNo.getText();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Student_view where Sno = " + "'" +Input + "'";
                    flag = 1;
                } else if (textName.getText().equals("") == false) {
                    Input = textName.getText();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Student_view where Sname = " + "'" +Input + "'";
                    flag = 2;
                } else if (textSex.getText().equals("") == false) {
                    Input = textSex.getText();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Student_view where Sex = " + "'" +Input + "'";
                } else if (textBirth.getText().equals("") == false) {
                    Input = textBirth.getText();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Student_view where Birth BETWEEN '" + Input + "' AND dateadd(second,-1,dateadd(day,1,'" + Input + "'))";
                    flag = 4;
                } else if (textEntrancedate.getText().equals("") == false) {
                    Input = textEntrancedate.getText();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Student_view where Entrance_date BETWEEN '" + Input + "' AND dateadd(second,-1,dateadd(day,1,'" + Input + "'))";
                    flag = 5;
                } else if (textClassno.getText().equals("") == false) {
                    Input = textClassno.getText();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Student_view where Class_no = " + "'" +Input + "'";
                    flag = 6;
                } else if (textHomeaddress.getText().equals("") == false) {
                    Input = textHomeaddress.getText();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Student_view where Home_addr = " + "'" +Input + "'";
                    flag = 7;
                } else if (textSdept.getText().equals("") == false) {
                    Input = textSdept.getText();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Student_view where Sde_no = " + "'" +Input + "'";
                    flag = 8;
                } else if (textPostcode.getText().equals("") == false) {
                    Input = textPostcode.getText();
                    sql = "use Academic_Affairs_Management_System_20211576 select * from Manager_Student_view where Postcode = " + "'" +Input + "'";
                    flag = 9;
                }
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    System.out.println("Connection Successful!"); // 如果连接成功 控制台输出
                    Statement stmt = dbConn.createStatement();
                    System.out.println(sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    int count = 0;
                    while(rs.next()){
                        data[count][0] = rs.getString("Sno");
                        data[count][1] = rs.getString("Sname");
                        data[count][2] = rs.getString("Sex");
                        data[count][3] = rs.getString("Birth");
                        data[count][4] = rs.getString("Entrance_date");
                        data[count][5] = rs.getString("Class_no");
                        data[count][6] = rs.getString("Home_addr");
                        data[count][7] = rs.getString("Sde_no");
                        data[count][8] = rs.getString("Postcode");
                        data[count][9] = rs.getString("AttendanceStatus");
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
            System.out.println("Connection Successful!"); // 如果连接成功 控制台输出
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int columnCount;
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery("use Academic_Affairs_Management_System_20211576 select * from Manager_Student_view");
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
