package SystemInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WindowForget extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();
    JFrame frame1 = new JFrame();
    JLabel labelWarning = new JLabel("注意：忘记密码请联系辅导员寻找教务管理人员修改！");
    JLabel labelNo = new JLabel("学号/工号");
    JTextField txtNo = new JTextField(10);
    JLabel labelOldPassword = new JLabel("原密码");
    JTextField txtOldPassword = new JTextField(10);
    JLabel labelNewPassword = new JLabel("新密码");
    JTextField txtNewPassword = new JTextField(10);
    JLabel labelNewPasswordTwice = new JLabel("新密码（确认一次）");
    JTextField txtNewPasswordTwice = new JTextField(10);
    JButton btnUpdate = new JButton("修改密码");

    public WindowForget() {
        frame1.setTitle("教务管理系统学生界面--修改密码");
        frame1.setVisible(true);
        frame1.setSize(400, 400);
        frame1.setLocation(300, 249);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(4,2));
        panel1.setLayout(new GridLayout(2,1));
        frame1.add(panel1, BorderLayout.SOUTH);
        panel.add(labelNo);panel.add(txtNo);
        panel.add(labelOldPassword);panel.add(txtOldPassword);
        panel.add(labelNewPassword);panel.add(txtNewPassword);
        panel.add(labelNewPasswordTwice);panel.add(txtNewPasswordTwice);
        panel1.add(btnUpdate);panel1.add(labelWarning);
        btnUpdate.addActionListener(this);
    }

    public static void main(String[] args) {
        new WindowForget();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnUpdate) {
            if(txtNo.getText().trim().equals("") || txtOldPassword.getText().trim().equals("") || txtNewPassword.getText().trim().equals("") || txtNewPasswordTwice.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "学号/学工号，原密码，新密码等四项均需输入!", "提示", JOptionPane.ERROR_MESSAGE);
            }
            else{
                if(txtNewPassword.getText().equals(txtNewPasswordTwice.getText()) == false) {
                    JOptionPane.showMessageDialog(null, "两次输入的新密码不一致！");
                    txtNewPassword.setText("");
                    txtNewPasswordTwice.setText("");
                    return;
                }
                frame1.dispose();
                String Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
                String userName = "s20211576"; // 默认用户名
                String userPwd = "s20211576"; // 密码
                String sql = "use Academic_Affairs_Management_System_20211576 select Position from Password_20211576 where id='" + txtNo.getText() + "'";
                String sql1 = "use Academic_Affairs_Management_System_20211576 select Password from Password_20211576 where id='" + txtNo.getText() + "'";
                Connection dbConn = null;
                try {
                    Class.forName(Driver);
                    dbConn = DriverManager.getConnection(url, userName, userPwd);
                    System.out.println("Connection Successful!"); // 如果连接成功 控制台输出
                    Statement stmt = dbConn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        String position = rs.getString("Position");
                        if (position.toString().trim().equals("教务管理人员")) {
                            JOptionPane.showMessageDialog(null, "教务管理人员无法修改密码！请至数据库内修改！");
                        }
                        else {
                            ResultSet rs1 = stmt.executeQuery(sql1);
                            while(rs1.next()){
                                String passwordcorrect = rs1.getString("password");
                                if (passwordcorrect.toString().trim().equals(txtOldPassword.getText())) {
                                    String sql2 = "use Academic_Affairs_Management_System_20211576 update Password_20211576 set Password='" + txtNewPassword.getText() + "' where id='" + txtNo.getText() + "'";
                                    System.out.println(sql2);
                                    stmt.executeUpdate(sql2);
                                    JOptionPane.showMessageDialog(null, "修改成功！");
                                } else {
                                    JOptionPane.showMessageDialog(null, "原密码错误！");
                                }
                            }
                        }

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
        }
    }
}
