package nerd.example.inha_project.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nerd.example.inha_project.R;
import nerd.example.inha_project.account.LoginActivity;
import nerd.example.inha_project.account.User;
import nerd.example.inha_project.database.AccountManager;
import nerd.example.inha_project.database.callback.DBCallback;
import nerd.example.inha_project.util.DateUtil;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView nickname = view.findViewById(R.id.home_name);
        TextView status = view.findViewById(R.id.home_status);

        TextView[] dayViews = {
                view.findViewById(R.id.home_day1),
                view.findViewById(R.id.home_day2),
                view.findViewById(R.id.home_day3),
                view.findViewById(R.id.home_day4),
                view.findViewById(R.id.home_day5),
                view.findViewById(R.id.home_day6),
                view.findViewById(R.id.home_day7)
        };

        TextView[] dateViews = {
                view.findViewById(R.id.home_date1),
                view.findViewById(R.id.home_date2),
                view.findViewById(R.id.home_date3),
                view.findViewById(R.id.home_date4),
                view.findViewById(R.id.home_date5),
                view.findViewById(R.id.home_date6),
                view.findViewById(R.id.home_date7)
        };

        AccountManager.getAccountData(LoginActivity.loginInstance.getID(), new DBCallback() {
            @Override
            public void onCallback(User user) {
                nickname.setText(user.getNickname() + "님");
                status.setText(user.getStatusMessage() != null ? user.getStatusMessage() : "상태메시지를 설정하여 주세요.");
            }
        });

        List<String> weekDays = DateUtil.getWeekDaysForHome();
        List<String> weekDates = DateUtil.getWeekDatesForHome();

        for (int i = 0; i < 7; i++) {
            dayViews[i].setText(weekDays.get(i));
            dateViews[i].setText(weekDates.get(i));
        }

        return view;
    }
}