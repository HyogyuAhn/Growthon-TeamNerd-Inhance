package nerd.example.inha_project.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import nerd.example.inha_project.R;
import nerd.example.inha_project.account.LoginActivity;
import nerd.example.inha_project.account.LogoutDialogFragment;

public class IndividualFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_individual, container, false);


        TextView current_name = (TextView) view.findViewById(R.id.NickName);
        TextView statusMessage = (TextView) view.findViewById(R.id.Current_Message);

        current_name.setText(LoginActivity.loginInstance.getNickname());
        statusMessage.setText(LoginActivity.loginInstance.getStatusMessage() != null ? LoginActivity.loginInstance.getStatusMessage() : "상태메시지를 설정하여 주세요.");



        TextView logout_text = (TextView) view.findViewById(R.id.Logout_Button);
        ImageView logout_image = (ImageView) view.findViewById(R.id.Logout_Img);
        logout_text.setOnClickListener(v -> showLogoutAlert());
        logout_image.setOnClickListener(v -> showLogoutAlert());

        return view;
    }

    private void showLogoutAlert() {
        LogoutDialogFragment logoutDialog = new LogoutDialogFragment();
        logoutDialog.show(getParentFragmentManager(), "LogoutDialogFragment");
    }
}