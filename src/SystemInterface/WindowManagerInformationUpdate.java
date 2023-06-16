package SystemInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

public class WindowManagerInformationUpdate extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JFrame frame1 = new JFrame();
    JButton btnAcademyManage = new JButton("添加学院信息");
    JButton btnMajorManage = new JButton("专业信息维护");
    JButton btnClassManage = new JButton("班级信息维护");
    JButton btnTeacherManage = new JButton("教师信息维护");
    JButton btnStudentManage = new JButton("学生信息维护");
    public WindowManagerInformationUpdate() {
        frame1.setTitle("教务管理系统管理员界面--信息维护界面");
        frame1.setVisible(true);
        frame1.setSize(400,300);
        frame1.setLocation(300, 249);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panel);
        panel.setLayout(new GridLayout(2,3));
        panel.add(btnAcademyManage);
        panel.add(btnMajorManage);
        panel.add(btnClassManage);
        panel.add(btnTeacherManage);
        panel.add(btnStudentManage);
        btnAcademyManage.addActionListener(this);
        btnMajorManage.addActionListener(this);
        btnClassManage.addActionListener(this);
        btnTeacherManage.addActionListener(this);
        btnStudentManage.addActionListener(this);
        panel.setLayout(new GridLayout(2,3));
    }
    public static void main(String[] args) {
        new WindowManagerInformationUpdate();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAcademyManage) {
            WindowManagerInformationUpdateAcademy academyManage = new WindowManagerInformationUpdateAcademy();
        }
        if (e.getSource() == btnMajorManage) {
            WindowManagerInformationUpdateMajor majorManage = new WindowManagerInformationUpdateMajor();
        }
        if (e.getSource() == btnClassManage) {
            WindowManagerInformationUpdateClass classManage = new WindowManagerInformationUpdateClass();
        }
        if (e.getSource() == btnTeacherManage) {
            WindowManagerInformationUpdateTeacher teacherManage = new WindowManagerInformationUpdateTeacher();
        }
        if (e.getSource() == btnStudentManage) {
            WindowManagerInformationUpdateStudent studentManage = new WindowManagerInformationUpdateStudent();
        }
    }
}
