package sep3.webshop.shared.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Printer {
    public static ObjectWriter ow = new ObjectMapper().writerWithDefaultPrettyPrinter();
    public static String toString(Object obj) {
        try {
            return ow.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "SOMETHING WENT WRONG TRYING TO WRITE VALUE AS STRING";
        }
    }
    public static void print(Object obj) {
        System.out.println(Printer.toString(obj));
    }
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static void log(String message) {
        LocalTime currentTime = LocalTime.now();

        String formattedTime = currentTime.format(formatter);
        String formattedMessage = String.format("[%s] %s", formattedTime, message);

        System.out.println(formattedMessage);
    }
}
