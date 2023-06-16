package SystemInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowStudent extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JFrame frame1 = new JFrame();
    JButton btnEnquiry = new JButton("信息查询+已选课列表");
    JButton btnCourseChoose = new JButton("选课");
    public WindowStudent() {
        frame1.setTitle("教务管理系统学生界面");
        frame1.setVisible(true);
        frame1.setSize(320,170);
        frame1.setLocation(300, 249);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panel);
        panel.setLayout(new GridLayout(1,3));
        panel.add(btnEnquiry, BorderLayout.CENTER);
        panel.add(btnCourseChoose, BorderLayout.EAST);
        btnEnquiry.addActionListener(this);
        btnCourseChoose.addActionListener(this);
    }
    public static void main(String[] args) {
        new WindowStudent();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEnquiry) {
            WindowStudentEnquiry wse = new WindowStudentEnquiry();
        }
        if (e.getSource() == btnCourseChoose) {
            WindowStudentCourseChoose wscc = new WindowStudentCourseChoose();
        }
    }
}
