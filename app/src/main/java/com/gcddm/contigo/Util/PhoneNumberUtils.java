package com.gcddm.contigo.Util;

public class PhoneNumberUtils {
    public static String trimPhone(String str) {
        String result = str;
        if (is99Number(str)) {
            result = str.substring(2, str.length() - 2);
        }
        if (result.length() > 8) {
            result = result.substring(result.length() - 8);
        }
        return result;
    }

    public static boolean is99Number(String number) {
        return number.startsWith("99") && number.endsWith("99");
    }

    public static boolean isValidCellPhone(String phone) {
        return phone.startsWith("5") && phone.length() == 8;
    }
}
