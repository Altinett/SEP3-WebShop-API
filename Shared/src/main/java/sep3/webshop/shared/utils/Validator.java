package sep3.webshop.shared.utils;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Validator {
    public static String
        EmailRegex = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$",
        AddressRegex = "^\\d{1,3}.,.[a-zA-Z]+$"
        ;

    public static boolean validEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        return Pattern.matches(EmailRegex, email);
    }
    public static boolean validAddress(String address) {
        if (address == null || address.isEmpty()) return false;
        return Pattern.matches(AddressRegex, address);
    }
    public static boolean validPhoneNumber(int phonenumber) {
        return phonenumber >= 10000000 && phonenumber <= 99999999;
    }
    public static boolean validPostCode(int postcode) {
        return postcode >= 1000 && postcode <= 10000;
    }
    public static boolean isNullOrEmpty(String text) {
        return text == null || text.isEmpty();
    }
    public static <T> boolean isNullOrEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }
    public static <T, R> boolean isNullOrEmpty(Map<T, R> map) {
        return map == null || map.isEmpty();
    }
}
