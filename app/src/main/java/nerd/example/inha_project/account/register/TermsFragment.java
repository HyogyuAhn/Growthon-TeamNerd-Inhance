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

import nerd.example.inha_project.R;
import nerd.example.inha_project.util.Util;

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

        addAllcheckListener(checkAll, new CheckBox[] { checkTerm, checkPrivacy, checkPush });

        CheckBox.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            boolean allChecked = checkTerm.isChecked() && checkPrivacy.isChecked() && checkPush.isChecked();
            if (!isChecked) {
                checkAll.setOnCheckedChangeListener(null);
                checkAll.setChecked(false);
                addAllcheckListener(checkAll, new CheckBox[] { checkTerm, checkPrivacy, checkPush });
            } else if (allChecked) {
                checkAll.setOnCheckedChangeListener(null);
                checkAll.setChecked(true);
                addAllcheckListener(checkAll, new CheckBox[] { checkTerm, checkPrivacy, checkPush });
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

    private static void addAllcheckListener(CheckBox checkAll, CheckBox[] checkboxes) {
        checkAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (CheckBox checkbox : checkboxes) {
                checkbox.setChecked(isChecked);
            }
        });
    }


}