package nerd.example.inha_project.util;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Util {

    // Toast 전송 | 테스트용
    public static void sendToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    // 학번 8자리 확인용 메서드
    public static boolean isEightDigitInteger(String id) {
        return id.length() == 8 && isPositiveInteger(id);

    }

    private static final Pattern LOCAL_EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    public static boolean isInhaEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.endsWith("@inha.edu");
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        if (!isInhaEmail(email)) {
            return false;
        }
        return LOCAL_EMAIL_PATTERN.matcher(email.replace("@inha.edu", "")).matches();
    }

    // 정수 확인
    public static boolean isInteger(String str) {
        return str.matches("^-?\\d+$");
    }

    // 양의 정수 확인
    public static boolean isPositiveInteger(String str) {
        return str.matches("^\\d+$");
    }


}
