package nerd.example.inha_project.account.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import nerd.example.inha_project.R;
import nerd.example.inha_project.database.AccountManager;
import nerd.example.inha_project.database.callback.DuplicateCallback;
import nerd.example.inha_project.util.Util;

public class EmailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email, container, false);

        ImageView back = view.findViewById(R.id.email_back);

        TextView alert = view.findViewById(R.id.email_text_alert);

        EditText email = view.findViewById(R.id.text_email);

        Button btnNext = view.findViewById(R.id.email_btn_next);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // pass
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // pass
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Util.isInhaEmail(email.getText().toString())) {
                    alert.setText("이메일 형식은 @inha.edu로 끝나야합니다.");
                    btnNext.setEnabled(false);
                } else if (!Util.isValidEmail(email.getText().toString())) {
                    alert.setText("이메일 형식이 올바르지 않습니다.");
                    btnNext.setEnabled(false);
                } else {
                    AccountManager.checkDuplicate(CHECK_TYPE.EMAIL, email.getText().toString(), new DuplicateCallback() {
                        @Override
                        public void onDuplicateFound(String email) {
                            alert.setText("이미 존재하는 이메일입니다.");
                        }

                        @Override
                        public void onNoDuplicateFound() {
                            alert.setText("가입 가능한 이메일입니다!");
                            btnNext.setEnabled(true);
                        }

                        @Override
                        public void onError(Exception exception) {
                            alert.setText("오류가 발생하였습니다. 개발자에게 문의해주세요.");
                        }
                    });
                }
            }
        });

        btnNext.setOnClickListener(v -> {
            RegisterFragmentManager.user.setEmail(email.getText().toString());
            ((RegisterFragmentManager) getActivity()).loadFragment(new IDFragment());
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragmentManager.user.setPushMessage(false);
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}