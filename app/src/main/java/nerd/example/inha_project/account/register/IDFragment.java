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

public class IDFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_id_input, container, false);

        ImageView back = view.findViewById(R.id.id_back);

        TextView alert = view.findViewById(R.id.id_text_alert);

        EditText id = view.findViewById(R.id.text_id);

        Button btnNext = view.findViewById(R.id.id_btn_next);

        id.addTextChangedListener(new TextWatcher() {
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
                if (id.getText().toString().length() != 8) {
                    alert.setText("학번은 8자리로 입력해 주세요.");
                    btnNext.setEnabled(false);
                } else {
                    AccountManager.checkDuplicate(CHECK_TYPE.ID, id.getText().toString(), new DuplicateCallback() {
                        @Override
                        public void onDuplicateFound(String email) {
                            alert.setText("이미 존재하는 아이디(학번)입니다.");
                        }

                        @Override
                        public void onNoDuplicateFound() {
                            alert.setText("사용 가능한 아이디(학번)입니다!");
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
            RegisterFragmentManager.user.setID(id.getText().toString());
            alert.setText("success");
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragmentManager.user.setEmail(null);
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}