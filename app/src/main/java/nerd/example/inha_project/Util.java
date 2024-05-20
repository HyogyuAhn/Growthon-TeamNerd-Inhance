package nerd.example.inha_project;

import android.content.Context;
import android.widget.Toast;

public class Util {

    public static void sendToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
