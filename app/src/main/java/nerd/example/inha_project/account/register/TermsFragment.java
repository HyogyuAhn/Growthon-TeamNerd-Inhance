package nerd.example.inha_project.account.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import nerd.example.inha_project.IntroActivity;
import nerd.example.inha_project.R;
import nerd.example.inha_project.Util;

public class TermsFragment extends Fragment {

    private CheckBox checkAll, checkTerm, checkPrivacy, checkPush;
    private Button btnNext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms, container, false);

        checkAll = view.findViewById(R.id.terms_check_all);
        checkTerm = view.findViewById(R.id.terms_check_term);
        checkPrivacy = view.findViewById(R.id.terms_check_privacy);
        checkPush = view.findViewById(R.id.terms_check_push);

        btnNext = view.findViewById(R.id.terms_btn_next);

        checkAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkTerm.setChecked(isChecked);
            checkPrivacy.setChecked(isChecked);
            checkPush.setChecked(isChecked);
        });

        CheckBox.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            boolean allChecked = checkTerm.isChecked() && checkPrivacy.isChecked() && checkPush.isChecked();
            if (!isChecked) {
                checkAll.setOnCheckedChangeListener(null);
                checkAll.setChecked(false);
                checkAll.setOnCheckedChangeListener((buttonView1, isChecked1) -> {
                    checkTerm.setChecked(isChecked1);
                    checkPrivacy.setChecked(isChecked1);
                    checkPush.setChecked(isChecked1);
                });
            } else if (allChecked) {
                checkAll.setOnCheckedChangeListener(null);
                checkAll.setChecked(true);
                checkAll.setOnCheckedChangeListener((buttonView1, isChecked1) -> {
                    checkTerm.setChecked(isChecked1);
                    checkPrivacy.setChecked(isChecked1);
                    checkPush.setChecked(isChecked1);
                });
            }
            btnNext.setEnabled(checkTerm.isChecked() && checkPrivacy.isChecked());
        };

        checkTerm.setOnCheckedChangeListener(listener);
        checkPrivacy.setOnCheckedChangeListener(listener);
        checkPush.setOnCheckedChangeListener(listener);

        btnNext.setOnClickListener(v -> {
            // ((RegisterFragmentManager) getActivity()).loadFragment();
            Util.sendToast((RegisterFragmentManager) getActivity(), "Btn On");
        });

        return view;
    }
}