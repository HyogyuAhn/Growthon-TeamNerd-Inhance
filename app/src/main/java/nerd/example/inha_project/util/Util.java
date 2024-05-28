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

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@#*]{8,20}$");

    public static boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean isValidNickname(String nickname) {
        int bitCount = 0;

        for (int i = 0; i < nickname.length(); i++) {
            char c = nickname.charAt(i);
            if (Character.isDigit(c) || Character.isAlphabetic(c)) {
                bitCount += 1;
            } else if (isKorean(c)) {
                bitCount += 2;
            }
        }

        return bitCount >= 4 && bitCount <= 16;
    }

    public static int getNicknameBit(String nickname) {
        int bitCount = 0;

        for (int i = 0; i < nickname.length(); i++) {
            char c = nickname.charAt(i);
            if (Character.isDigit(c) || Character.isAlphabetic(c)) {
                bitCount += 1;
            } else if (isKorean(c)) {
                bitCount += 2;
            }
        }

        return bitCount;
    }

    private static boolean isKorean(char c) {
        return (c >= 0xAC00 && c <= 0xD7A3);
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
