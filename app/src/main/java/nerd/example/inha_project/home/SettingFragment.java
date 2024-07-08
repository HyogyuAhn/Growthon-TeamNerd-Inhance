package nerd.example.inha_project.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import nerd.example.inha_project.R;
import nerd.example.inha_project.account.LoginActivity;
import nerd.example.inha_project.database.AccountManager;

public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        TextView userEmail = view.findViewById(R.id.setting_email);
        userEmail.setText(LoginActivity.loginInstance.getEmail());

        Switch pushMessage = view.findViewById(R.id.push_switch);
        pushMessage.setChecked(LoginActivity.loginInstance.getPushMessage());

        pushMessage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            LoginActivity.loginInstance.setPushMessage(isChecked);
            AccountManager.updateUser(LoginActivity.loginInstance);
        });

        return view;
    }
}