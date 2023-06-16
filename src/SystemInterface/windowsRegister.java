package SystemInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class windowsRegister extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JFrame frame1 = new JFrame();
    public static String loginID = "";
    JLabel labelNo = new JLabel("学号/教工号");
    JTextField txtLabelNo = new JTextField(12);
    JLabel labelPassword = new JLabel("密码");
    JPasswordField txtLabelPassword = new JPasswordField(12);
    JButton buttonRegister = new JButton("登陆");
    JButton buttonForget = new JButton("修改密码");

    public windowsRegister() {
        //创建界面
        frame1.setTitle("教务管理系统登陆界面");
        frame1.setVisible(true);
        frame1.setSize(250, 150);
        frame1.setLocation(300, 249);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(3, 2));
        panel.add(labelNo);panel.add(txtLabelNo);
        panel.add(labelPassword);panel.add(txtLabelPassword);
        panel.add(buttonRegister);
        panel.add(buttonForget);
        buttonRegister.addActionListener(this);
        buttonForget.addActionListener(this);

    }

    public static void main(String[] args) {
        new windowsRegister();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonRegister) {
            if (txtLabelNo.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "请输入学号/教工号", "提示", JOptionPane.ERROR_MESSAGE);
            } else if (txtLabelPassword.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "密码不能为空", "提示", JOptionPane.ERROR_MESSAGE);
            } else {
                String userInput = null;
                if(txtLabelNo.getText().length()<7){
                    userInput = "'" + txtLabelNo.getText() + "'";
                }
                else{
                    userInput = txtLabelNo.getText();
                }
                System.out.println(userInput);
                String passwordInput = txtLabelPassword.getText().toString();
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;database=Academic_Affairs_Management_System_20211576;encrypt=false";
                String sql="use Academic_Affairs_Management_System_20211576 select Position from Password_20211576 where id = " + userInput;
                String sql1="use Academic_Affairs_Management_System_20211576 select password from Password_20211576 where id = " + userInput;
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                } catch (Exception em) {
                    em.printStackTrace();
                }
                Statement stml;
                ResultSet rs,rs1;
                try {
                    stml = dbConn.createStatement();
                    rs = stml.executeQuery(sql);//SQL查询语句
                    while (rs.next()) {
                        String passwordCorrect;
                        String position = rs.getString("Position");
                        System.out.println(position);
                        rs1 = stml.executeQuery(sql1);
                        while (rs1.next()) {
                            passwordCorrect = rs1.getString("password");
                            if(passwordInput.equals(passwordCorrect.toString().trim())) {
                                JOptionPane.showMessageDialog(null, "登陆成功");
                                //关闭界面，打开新界面

                                if (position.toString().trim().equals("学生")){
                                    loginID = userInput;
                                    WindowStudent student = new WindowStudent();
                                }else if (position.trim().equals("教务管理人员")){
                                    WindowManager manager = new WindowManager();
                                }else{
                                    WindowTeacher teacher = new WindowTeacher();
                                }
                                frame1.dispose();
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "密码错误", "提示", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    dbConn.close();

                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        else if (e.getSource() == buttonForget) {
            WindowForget forget = new WindowForget();
        }
    }
}