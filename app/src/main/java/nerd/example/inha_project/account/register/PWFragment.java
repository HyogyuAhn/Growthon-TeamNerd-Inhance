package nerd.example.inha_project.account.register;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
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
import nerd.example.inha_project.util.Util;

public class PWFragment extends Fragment {

    private boolean checking = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password, container, false);

        ImageView back = view.findViewById(R.id.pw_back);
        ImageView checkbox1 = view.findViewById(R.id.pw_checkbox_general);
        ImageView checkbox2 = view.findViewById(R.id.pw_checkbox_check);

        Drawable checked = ContextCompat.getDrawable(requireContext(), R.drawable.password_checked);
        Drawable unchecked = ContextCompat.getDrawable(requireContext(), R.drawable.password_unchecked);

        EditText pw = view.findViewById(R.id.pw_general_password);
        EditText pwCheck = view.findViewById(R.id.pw_check_password);

        TextView alert1 = view.findViewById(R.id.pw_text_general);
        TextView alert2 = view.findViewById(R.id.pw_text_check);
        TextView warn = view.findViewById(R.id.pw_text_alert);

        Button btnNext = view.findViewById(R.id.pw_btn_next);

        pw.addTextChangedListener(new TextWatcher() {
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
                String password = pw.getText().toString();

                if (password.length() >= 8) {
                    if (Util.isValidPassword(password)) {
                        checkbox1.setImageDrawable(checked);
                        alert1.setTextColor(getResources().getColor(R.color.pw_enabled));
                        warn.setText("");
                        checking = true;
                        if (pwCheck.getText().toString().equals(pw.getText().toString())) {
                            checkbox2.setImageDrawable(checked);
                            alert2.setTextColor(getResources().getColor(R.color.pw_enabled));
                            btnNext.setEnabled(true);

                        } else {
                            checkbox2.setImageDrawable(unchecked);
                            alert2.setTextColor(getResources().getColor(R.color.pw_disabled));
                            btnNext.setEnabled(false);
                        }
                    } else {
                        checkbox1.setImageDrawable(unchecked);
                        checkbox2.setImageDrawable(unchecked);
                        alert1.setTextColor(getResources().getColor(R.color.pw_disabled));
                        alert2.setTextColor(getResources().getColor(R.color.pw_disabled));
                        warn.setText(R.string.password_warn);
                        btnNext.setEnabled(false);
                        checking = false;
                    }
                } else {
                    checkbox1.setImageDrawable(unchecked);
                    checkbox2.setImageDrawable(unchecked);
                    alert1.setTextColor(getResources().getColor(R.color.pw_disabled));
                    alert2.setTextColor(getResources().getColor(R.color.pw_disabled));
                    btnNext.setEnabled(false);
                    checking = false;
                    if (password.isEmpty() || password.matches("^[a-zA-Z0-9!@#*]+$")) {
                        warn.setText("");
                    } else {
                        warn.setText(R.string.password_warn);
                    }
                }
            }
        });

        pwCheck.addTextChangedListener(new TextWatcher() {
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
                if (checking) {
                    if (pwCheck.getText().toString().equals(pw.getText().toString())) {
                        checkbox2.setImageDrawable(checked);
                        alert2.setTextColor(getResources().getColor(R.color.pw_enabled));
                        btnNext.setEnabled(true);
                    } else {
                        checkbox2.setImageDrawable(unchecked);
                        alert2.setTextColor(getResources().getColor(R.color.pw_disabled));
                        btnNext.setEnabled(false);
                    }
                }
            }
        });

        btnNext.setOnClickListener(v -> {
            RegisterFragmentManager.user.setPassword(pw.getText().toString());
            ((RegisterFragmentManager) getActivity()).loadFragment(new NicknameFragment());
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragmentManager.user.setID(null);
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}