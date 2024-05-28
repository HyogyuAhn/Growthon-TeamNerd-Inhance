package nerd.example.inha_project.account.register;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import nerd.example.inha_project.R;

public class RegisterCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_complete);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView info = (TextView) findViewById(R.id.regcomplete_info);

        Button endBtn = (Button) findViewById(R.id.regcomplete_btn_end);

        String infoText = getString(R.string.regcom_info);

        int start = infoText.indexOf("LOGO");
        int end = start + "LOGO".length();

        SpannableString infoSpan = new SpannableString(infoText);

        Drawable logo = ContextCompat.getDrawable(getApplicationContext(), R.drawable.login_logo);
        if (logo != null) {
            logo.setBounds(0, 0, 400, 67);

            ImageSpan imageSpan = new ImageSpan(logo, ImageSpan.ALIGN_BASELINE);
            infoSpan.setSpan(imageSpan, start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        info.setText(infoSpan);

        endBtn.setOnClickListener(v -> {
            finish();
        });
    }
}