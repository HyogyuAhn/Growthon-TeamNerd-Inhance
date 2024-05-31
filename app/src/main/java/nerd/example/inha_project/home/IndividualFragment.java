package nerd.example.inha_project.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import nerd.example.inha_project.R;
import nerd.example.inha_project.account.LoginActivity;

public class IndividualFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_individual, container, false);


        TextView current_name = (TextView) view.findViewById(R.id.NickName);
        TextView statusMessage = (TextView) view.findViewById(R.id.Current_Message);

        current_name.setText(LoginActivity.loginInstance.getNickname());
        statusMessage.setText(LoginActivity.loginInstance.getStatusMessage() != null ? LoginActivity.loginInstance.getStatusMessage() : "상태메시지를 설정하여 주세요.");

        return view;
    }
}