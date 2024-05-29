package nerd.example.inha_project.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import nerd.example.inha_project.MainActivity;
import nerd.example.inha_project.R;
import nerd.example.inha_project.account.register.RegisterFragmentManager;
import nerd.example.inha_project.database.AccountManager;
import nerd.example.inha_project.database.callback.LoginCallback;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView error = (TextView) findViewById(R.id.text_error);

        EditText id = (EditText) findViewById(R.id.text_id);
        EditText pw = (EditText) findViewById(R.id.text_pw);

        Button btnLogin = (Button) findViewById(R.id.btn_login);
        Button btnRegister = (Button) findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString().isEmpty()) error.setText("학번을 입력해 주세요.");
                else if (pw.getText().toString().isEmpty()) error.setText("비밀번호를 입력해 주세요.");
                else if (id.getText().toString().length() != 8) error.setText("학번은 8자리로 입력해 주세요.");
                else {
                    AccountManager.loginRequest(id.getText().toString(), pw.getText().toString(), new LoginCallback() {
                        @Override
                        public void onLoginResult(boolean success) {
                            if (success) {
                                error.setText("로그인 성공");
                                //Intent intent = new Intent(getApplicationContext(), NicknameActivity.class);
                                //startActivity(intent);
                                //finish();
                            } else {
                                error.setText("학번 또는 비밀번호가 올바르지 않습니다.");
                            }
                        }
                    });
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterFragmentManager.class);
                startActivity(intent);
            }
        });
    }

}