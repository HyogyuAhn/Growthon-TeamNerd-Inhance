package nerd.example.inha_project;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import nerd.example.inha_project.account.LoginActivity;

public class IntroActivity extends AppCompatActivity {

    private static final String INTRO_TEXT = "INHA+nce";
    private TextView textIntro;
    private int index = 0;
    private int originalTopMargin;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textIntro = (TextView) findViewById(R.id.intro_logo);

        textIntro.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textIntro.getLayoutParams();
                originalTopMargin = params.topMargin;
                textIntro.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        handler.postDelayed(textAdder, 200);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private final Runnable textAdder = new Runnable() {
        @Override
        public void run() {
            if (index <= INTRO_TEXT.length()) {
                textIntro.setText(INTRO_TEXT.substring(0, index));
                index++;
                handler.postDelayed(this, 120);
            } else {
                handler.postDelayed(changeText, 850);
            }
        }
    };

    private Runnable changeText = new Runnable() {
        @Override
        public void run() {
            sizeUpAnimation();

            handler.postDelayed(startNextActivity, 2500);
        }
    };

    private void sizeUpAnimation() {
        final String changeText = "A+";
        final int start = INTRO_TEXT.indexOf(changeText);
        final int end = start + changeText.length();

        ValueAnimator sizeAnimator = ValueAnimator.ofFloat(1.0f, 1.2f);
        sizeAnimator.setDuration(1000);
        sizeAnimator.addUpdateListener(animator -> {
            float animatedValue = (float) animator.getAnimatedValue();
            SpannableString spannableString = new SpannableString(INTRO_TEXT);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4285F4")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new RelativeSizeSpan(animatedValue), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textIntro.setText(spannableString);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textIntro.getLayoutParams();
            params.topMargin = originalTopMargin - (int) ((animatedValue - 1.0f) * textIntro.getTextSize() / 2);
            textIntro.setLayoutParams(params);
        });
        sizeAnimator.start();

    }

    private final Runnable startNextActivity = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}