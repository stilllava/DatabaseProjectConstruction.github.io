package SystemInterface;

import javax.swing.*;
import java.awt.*;

public class WindowManagerInformationUpdateStudentUpdate {
    JPanel panel = new JPanel();
    JFrame frame1 = new JFrame();
    JLabel labelNo = new JLabel("学号/教工号");
    JTextField txtLabelNo = new JTextField(12);
    JLabel labelPassword = new JLabel("密码");
    JPasswordField txtLabelPassword = new JPasswordField(12);
    public WindowManagerInformationUpdateStudentUpdate() {
        frame1.setTitle("教务管理系统管理员界面-信息维护功能-班级信息维护");
        frame1.setVisible(true);
        frame1.setSize(600,800);
        frame1.setLocation(300, 249);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.add(panel, BorderLayout.CENTER);
        panel.add(labelNo);panel.add(txtLabelNo);
        panel.add(labelPassword);panel.add(txtLabelPassword);
    }
    public static void main(String[] args) {
        new WindowManagerInformationUpdateStudentUpdate();
    }

}