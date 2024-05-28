package nerd.example.inha_project.account.register;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import nerd.example.inha_project.R;
import nerd.example.inha_project.account.User;
import nerd.example.inha_project.database.AccountManager;
import nerd.example.inha_project.database.callback.CreateAccountCallback;
import nerd.example.inha_project.database.callback.DuplicateCallback;
import nerd.example.inha_project.util.Util;


public class NicknameFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nickname, container, false);

        ImageView back = view.findViewById(R.id.name_back);

        TextView info = view.findViewById(R.id.name_info);
        TextView bit = view.findViewById(R.id.name_bit);
        TextView alert = view.findViewById(R.id.name_text_alert);

        EditText name = view.findViewById(R.id.text_name);

        Button btnNext = view.findViewById(R.id.name_btn_next);

        name.setText(User.getRandomName());

        String infoText = getString(R.string.name_info);

        int start = infoText.indexOf("LOGO");
        int end = start + "LOGO".length();

        SpannableString infoSpan = new SpannableString(infoText);

        Drawable logo = ContextCompat.getDrawable(requireContext(), R.drawable.login_logo);
        if (logo != null) {
            logo.setBounds(0, 0, 350, 58);

            ImageSpan imageSpan = new ImageSpan(logo, ImageSpan.ALIGN_BASELINE);
            infoSpan.setSpan(imageSpan, start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        info.setText(infoSpan);

        name.addTextChangedListener(new TextWatcher() {
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
                bit.setText("( " + Util.getNicknameBit(name.getText().toString()) + "/16 )");
                if (!Util.isValidNickname(name.getText().toString())) {
                    alert.setText("닉네임의 길이를 조절하여 주세요.");
                    btnNext.setEnabled(false);
                } else if (!name.getText().toString().matches("^[가-힣a-zA-Z0-9]+$")) {
                    alert.setText("닉네임에 특수문자는 포함될 수 없습니다.");
                    btnNext.setEnabled(false);
                } else {
                    AccountManager.checkDuplicate(CHECK_TYPE.NICKNAME, name.getText().toString(), new DuplicateCallback() {
                        @Override
                        public void onDuplicateFound(String email) {
                            alert.setText("이미 사용중인 닉네임입니다.");
                            btnNext.setEnabled(false);
                        }

                        @Override
                        public void onNoDuplicateFound() {
                            alert.setText("사용 가능한 닉네임입니다!");
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
            RegisterFragmentManager.user.setNickname(name.getText().toString());
            AccountManager.createAccount(RegisterFragmentManager.user, new CreateAccountCallback() {
                @Override
                public void onSuccess() {
                    Intent intent = new Intent(getContext(), RegisterCompleteActivity.class);
                    startActivity(intent);
                    ((RegisterFragmentManager) getActivity()).finish();
                }

                @Override
                public void onFailure(String error) {
                    alert.setText("계정 생성에 문제가 발생하였습니다. 개발자에게 문의해주세요.");
                }
            });

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragmentManager.user.setPassword(null);
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}