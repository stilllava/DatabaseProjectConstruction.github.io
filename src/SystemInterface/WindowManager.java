package SystemInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WindowManager extends JFrame implements ActionListener{
    JPanel panel = new JPanel();
    JFrame frame1 = new JFrame();
    JButton btnInformationUpdate = new JButton("各部分信息维护");
    JButton btnTeacherCourse = new JButton("教师授课信息维护");
    JButton btnStudentCourse = new JButton("学生可选课程信息维护");
    JButton btnEnquiry = new JButton("教师学生信息查询系统");
    JButton btnAddTeacherPassword = new JButton("添加教务管理系统管理员用户");
    JButton btnGetStudentCourseSummary = new JButton("学生选修课程成绩汇总");
    JButton btnGetClassSummary = new JButton("班级选修课程成绩汇总");
    JButton btnCountTeachingHours = new JButton("教师授课学时统计");
    public WindowManager() {
        frame1.setTitle("教务管理系统管理员界面");
        frame1.setVisible(true);
        frame1.setSize(600,300);
        frame1.setLocation(10, 10);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(3,3));
        panel.add(btnInformationUpdate);
        panel.add(btnTeacherCourse);
        panel.add(btnStudentCourse);
        panel.add(btnEnquiry);
        panel.add(btnAddTeacherPassword);
        panel.add(btnGetStudentCourseSummary);
        panel.add(btnGetClassSummary);
        panel.add(btnCountTeachingHours);
        btnInformationUpdate.addActionListener(this);
        btnTeacherCourse.addActionListener(this);
        btnStudentCourse.addActionListener(this);
        btnEnquiry.addActionListener(this);
        btnAddTeacherPassword.addActionListener(this);
        btnGetStudentCourseSummary.addActionListener(this);
        btnGetClassSummary.addActionListener(this);
        btnCountTeachingHours.addActionListener(this);
    }
    public static void main(String[] args) {
        new WindowManager();
    }
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource() == btnInformationUpdate) {
            WindowManagerInformationUpdate informationUpdate = new WindowManagerInformationUpdate();
        }
        if (e.getSource() == btnTeacherCourse) {
            WindowManagerTeacherCourse teacherCourse = new WindowManagerTeacherCourse();
        }
        if (e.getSource() == btnStudentCourse) {
            WindowManagerCourseAvailable courseAvailable  = new WindowManagerCourseAvailable();
        }
        if (e.getSource() == btnEnquiry) {
            WindowManagerEnquiry enquiry = new WindowManagerEnquiry();
        }
        if (e.getSource() == btnAddTeacherPassword) {
            WindowManagerAddTeacherPassword addTeacherPassword = new WindowManagerAddTeacherPassword();
        }
        if(e.getSource() == btnGetStudentCourseSummary){
            WindowManagerGetStudentCourseSummary getStudentCourseSummary = new WindowManagerGetStudentCourseSummary();
        }
        if (e.getSource() == btnGetClassSummary) {
            WindowManagerGetClassSummary getClassSummary = new WindowManagerGetClassSummary();
        }
        if (e.getSource() == btnCountTeachingHours) {
            WindowManagerCountTeachingHours countTeachingHours = new WindowManagerCountTeachingHours();
        }
    }
}
