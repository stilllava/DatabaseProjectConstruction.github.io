package SystemInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String[] comSemester;
        // 所有可选的学期、Sde_no 和课程信息
        int date = Calendar.getInstance().get(Calendar.YEAR);
        int i=date-20;
        for(;i<date+10;i++){
            System.out.println("('"+i+"-"+(i+1)+"年第1学期'),");
            System.out.println("('"+i+"-"+(i+1)+"年第2学期'),");
        }
    }
}
