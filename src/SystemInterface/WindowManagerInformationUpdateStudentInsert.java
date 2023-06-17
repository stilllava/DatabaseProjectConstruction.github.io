package SystemInterface;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WindowManagerInformationUpdateStudentInsert {
    JPanel panel = new JPanel();
    JFrame frame1 = new JFrame();
    JFileChooser fileChooser = new JFileChooser();

    public WindowManagerInformationUpdateStudentInsert() {
        frame1.setTitle("1-2-3");
        frame1.setVisible(true);
        frame1.setSize(600,800);
        frame1.setLocation(300, 249);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // 设置文件过滤器
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            private String[] extensions = {"xls", "csv", "xlsx"};

            public String getDescription() {
                return "Excel文件(*.xls, *.csv, *.xlsx)";
            }

            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    String name = file.getName().toLowerCase();
                    for (String extension : extensions) {
                        if (name.endsWith("." + extension)) {
                            return true;
                        }
                    }
                    return false;
                }
            }
        });
        // 添加文件选择器选择事件监听器
        fileChooser.addActionListener(e -> {
            frame1.setVisible(false);
            if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                File selectedFile = fileChooser.getSelectedFile();

                // 输出文件绝对路径
                String filePath = selectedFile.getAbsolutePath();
                JOptionPane.showMessageDialog(null, "选择文件成功，文件绝对路径为：" + filePath);

                // 导入文件内容到数据库
                // 1. 建立数据库连接
                Connection conn = null;
                try {
                    conn = getConnection();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                // 2. 遍历 Excel 文件中的所有 Sheet，并将数据导入到数据库中
                File file = new File(filePath);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = null;
                Document doc;
                try {
                    db = dbf.newDocumentBuilder();
                    //
                    doc = db.parse(file);
                } catch (ParserConfigurationException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SAXException ex) {
                    throw new RuntimeException(ex);
                }
                NodeList sheets = doc.getElementsByTagName("Worksheet");
                for (int i = 0; i < sheets.getLength(); i++) {
                    Element sheet = (Element) sheets.item(i);
                    String tableName = sheet.getAttribute("ss:Name").replace("'", "''");  // 将表名中的单引号转义
                    NodeList rows = sheet.getElementsByTagName("Row");
                    for (int j = 0; j < rows.getLength(); j++) {
                        Element row = (Element) rows.item(j);
                        NodeList cells = row.getElementsByTagName("Cell");
                        if (cells.getLength() <= 0) continue;
                        String sql = generateInsertSql(tableName, cells);
                        try {
                            PreparedStatement statement = conn.prepareStatement(sql);
                            statement.executeUpdate();
                        } catch (SQLException exc) {
                            System.err.println("Insert failed: " + sql);
                            exc.printStackTrace();
                        }
                    }
                }
                // 3. 关闭数据库连接
                closeConnection(conn);
                frame1.setVisible(true);
            }else if (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
                JOptionPane.showMessageDialog(null, "取消选择文件");
            }
        });
        // 设置面板布局，并将文件选择器添加到面板上
        panel.setLayout(new BorderLayout());
        panel.add(fileChooser, BorderLayout.CENTER);

        // 将面板添加到框架中
        frame1.add(panel);
        frame1.pack();

        // 显示文件选择器，在窗口中显示
        fileChooser.showOpenDialog(frame1);

    }
    public static void main(String[] args) {
        new WindowManagerInformationUpdateStudentInsert();
    }
    // 生成插入语句，例如 "INSERT INTO table_name (col1, col2, col3) VALUES ('value1', 'value2', 'value3')"
    private static String generateInsertSql(String tableName, NodeList cells) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(tableName).append("(");
        for (int i = 0; i < cells.getLength(); i++) {
            Element cell = (Element) cells.item(i);
            String columnName = getColumnFromCellAddress(cell.getAttribute("ss:Index"));
            sb.append(columnName);
            if (i != cells.getLength() - 1) sb.append(", ");
        }
        sb.append(") VALUES (");
        for (int i = 0; i < cells.getLength(); i++) {
            Element cell = (Element) cells.item(i);
            String value = cell.getTextContent().replace("'", "''");  // 将值中的单引号转义
            sb.append("'").append(value).append("'");
            if (i != cells.getLength() - 1) sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }

    // 根据 Excel 单元格地址（如 "B4"）返回对应的列名（如 "col2"）
    private static String getColumnFromCellAddress(String address) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < address.length(); i++) {
            char c = address.charAt(i);
            if (Character.isLetter(c)) sb.append(c);
        }
        return "col" + (getColumnNumber(sb.toString()) - 1);
    }

    private static int getColumnNumber(String column) {
        int result = 0;
        for (int i = 0; i < column.length(); i++) {
            result *= 26;
            result += column.charAt(i) - 'A' + 1;
        }
        return result;
    }

    // 建立数据库连接
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Academic_Affairs_Management_System_20211576;encrypt=false";
        String user = "s20211576";
        String password = "s20211576";
        return DriverManager.getConnection(url, user, password);
    }

    // 关闭数据库连接
    private static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
