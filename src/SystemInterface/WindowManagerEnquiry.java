package SystemInterface;

import javax.swing.*;
import java.awt.*;

public class WindowManagerEnquiry {
    JPanel panel = new JPanel();
    JFrame frame1 = new JFrame();
    JButton btnTeacher = new JButton("教师信息查询");
    JButton btnStudent = new JButton("学生信息查询");
    public WindowManagerEnquiry() {
        frame1.setTitle("教务管理系统管理员界面--查询师生信息");
        frame1.setVisible(true);
        frame1.setSize(400,200);
        frame1.setLocation(300, 249);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panel);
        panel.setLayout(new GridLayout(1,2));
        panel.add(btnTeacher, BorderLayout.NORTH);
        panel.add(btnStudent, BorderLayout.SOUTH);
        btnTeacher.addActionListener(e -> {
            WindowManagerEnquiryTeacher enquiryTeacher = new WindowManagerEnquiryTeacher();
        });
        btnStudent.addActionListener(e -> {
            WindowManagerEnquiryStudent enquiryStudent = new WindowManagerEnquiryStudent();
        });
    }
    public static void main(String[] args) {
        new WindowManagerEnquiry();
    }
}
