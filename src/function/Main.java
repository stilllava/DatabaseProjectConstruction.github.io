package function;

import java.util.Scanner;

public class Main {
    public void change(String str) {
        int length = str.length();
        System.out.println("转换前的字符串为：" + str);
        System.out.println(length);
        System.out.print("转换后的字符串为：");
        char[] ch = new char[length];
        char[] ch2 = new char[length];
        for (int i = 0; i < length; i++) {
            //将字符串中的字符存到数组中
            ch[i] = str.charAt(i);
        }
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] >= 'a' && ch[i] <= 'z') {
                ch2[i] = (char) (ch[i] - 32);
            }
            else if(ch[i] >= 'A' && ch[i] <= 'Z'){
                ch2[i] = (char) (ch[i] + 32);
            }
            else{
                ch2[i] = ch[i];
            }
            System.out.print(ch2[i]);
        }
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str = scan.next();
        Main m = new Main();
        m.change(str);
    }
}
