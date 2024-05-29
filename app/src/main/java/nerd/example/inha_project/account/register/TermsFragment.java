package nerd.example.inha_project.account.register;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import nerd.example.inha_project.R;
import nerd.example.inha_project.account.LoginActivity;
import nerd.example.inha_project.util.Util;

public class TermsFragment extends Fragment {

    private CheckBox checkAll, checkTerm, checkPrivacy, checkPush;
    private Button btnNext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms, container, false);

        TextView info = view.findViewById(R.id.terms_info);

        String infoText = getString(R.string.terms_info);

        int start = infoText.indexOf("LOGO");
        int end = start + "LOGO".length();

        SpannableString infoSpan = new SpannableString(infoText);

        Drawable logo = ContextCompat.getDrawable(requireContext(), R.drawable.login_logo);
        if (logo != null) {
            logo.setBounds(0, 0, 400, 67);

            ImageSpan imageSpan = new ImageSpan(logo, ImageSpan.ALIGN_BASELINE);
            infoSpan.setSpan(imageSpan, start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        info.setText(infoSpan);

        ImageView back = view.findViewById(R.id.terms_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        checkAll = view.findViewById(R.id.terms_check_all);
        checkTerm = view.findViewById(R.id.terms_check_term);
        checkPrivacy = view.findViewById(R.id.terms_check_privacy);
        checkPush = view.findViewById(R.id.terms_check_push);

        TextView textAll = view.findViewById(R.id.terms_text_all);
        TextView textTerm = view.findViewById(R.id.terms_text_term);
        TextView textPrivacy = view.findViewById(R.id.terms_text_privacy);
        TextView textPush = view.findViewById(R.id.terms_text_push);

        btnNext = view.findViewById(R.id.terms_btn_next);

        textAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAll.toggle();
            }
        });

        textTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTerm.toggle();
            }
        });

        textPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPrivacy.toggle();
            };
        });

        textPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPush.toggle();
            }
        });

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
            RegisterFragmentManager.user.setPushMessage(checkPush.isChecked());
            if (checkPush.isChecked()) {
                Util.sendToast(getContext(), "푸시 알림을 동의하셨습니다.");
            } else {
                Util.sendToast(getContext(), "푸시 알림을 거부하셨습니다.");
            }
            ((RegisterFragmentManager) getActivity()).loadFragment(new EmailFragment());
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