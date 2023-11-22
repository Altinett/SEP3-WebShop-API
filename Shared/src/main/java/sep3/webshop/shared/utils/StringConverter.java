package sep3.webshop.shared.utils;

import java.util.ArrayList;
import java.util.List;

public class StringConverter {
    public static List<List<Integer>> to2DArray(String input) {
        List<List<Integer>> result = new ArrayList<>();

        String[] outerArray = input.substring(2, input.length() - 2).split("\\},\\{");

        for (String innerArray : outerArray) {
            String[] elements = innerArray.split(",");

            List<Integer> innerList = new ArrayList<>();
            for (String element : elements) {
                innerList.add(Integer.parseInt(element.trim()));
            }

            result.add(innerList);
        }

        return result;
    }
}
