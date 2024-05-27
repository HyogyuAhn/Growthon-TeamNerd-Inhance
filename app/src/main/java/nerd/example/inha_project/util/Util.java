package nerd.example.inha_project.util;

import android.content.Context;
import android.widget.Toast;

public class Util {

    // Toast 전송 | 테스트용
    public static void sendToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    // 학번 8자리 확인용 메서드
    public static boolean isEightDigitInteger(String id) {
        return id.length() == 8 && isPositiveInteger(id);

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
