package function;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DataTransformation {
    private static final Random random = new Random();

    public static List<String[]> transformData(List<String[]> data) {
        List<String[]> result = new ArrayList<>();
        for (String[] item : data) {
            int year = random.nextInt(2023 - Integer.parseInt(item[0].substring(0, 4))) + Integer.parseInt(item[0].substring(0, 4)) + 1;
            int term = random.nextInt(2) + 1;
            String semester = year + "-" + (year+1) + "年第" + term + "学期";
            String[] transformedItem = {semester, item[0], item[1], item[2]};
            result.add(transformedItem);
        }
        return result;
    }

    public static void main(String[] args) {
        List<String[]> data = List.of(new String[][]{
                {"202105720","0003","012"},
                {"202105720","0017","012"},
                {"202106620","0001","002"},
                {"202106620","0015","002"},
                {"202108010","0001","001"},
                {"202108010","0015","001"},
                {"202111240","0003","012"},
                {"202111240","0017","012"},
                {"202111390","0002","009"},
                {"202111390","0016","009"},
                {"202201110","0002","006"},
                {"202201110","0016","006"},
                {"202207680","0002","008"},
                {"202207680","0016","008"},
                {"202209750","0003","015"},
                {"202209750","0017","015"},
                {"202212330","0001","003"},
                {"202212330","0015","003"}
        });

        List<String[]> transformedData = transformData(data);
        for (String[] item : transformedData) {
            System.out.println(String.join(",", item));
        }
    }
}
