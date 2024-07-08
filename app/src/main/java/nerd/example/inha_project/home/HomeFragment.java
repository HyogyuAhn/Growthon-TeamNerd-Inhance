package nerd.example.inha_project.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import nerd.example.inha_project.R;
import nerd.example.inha_project.database.TodolistManager;
import nerd.example.inha_project.others.StudyBoardActivity;
import nerd.example.inha_project.account.LoginActivity;
import nerd.example.inha_project.account.User;
import nerd.example.inha_project.database.AccountManager;
import nerd.example.inha_project.database.callback.DBCallback;
import nerd.example.inha_project.others.AnnounceActivity;
import nerd.example.inha_project.others.SchoolScheduleActivity;
import nerd.example.inha_project.util.DateUtil;
import nerd.example.inha_project.util.todo.TodoItem;
import nerd.example.inha_project.util.todo.TodoListAdapter;

public class HomeFragment extends Fragment {

    private TodolistManager todolistManager;
    private TodoListAdapter adapter;
    private List<TodoItem> todoList = new ArrayList<>();

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

        RecyclerView recyclerView = view.findViewById(R.id.todo_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        todolistManager = new TodolistManager(LoginActivity.loginInstance.getID());

        adapter = new TodoListAdapter(todoList);
        recyclerView.setAdapter(adapter);

        loadTopTodoList();

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

        ImageView announce = view.findViewById(R.id.home_btn_announce);
        announce.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), AnnounceActivity.class);
            startActivity(intent);
        });

        ImageView todo = view.findViewById(R.id.home_todo_image);
        todo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), TodoListActivity.class);
            startActivity(intent);
        });

        ImageView schoolSchedule = view.findViewById(R.id.home_school_schedule_image);
        schoolSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), SchoolScheduleActivity.class);
            startActivity(intent);
        });

        ImageView study = view.findViewById(R.id.home_study_image);
        study.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), StudyBoardActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTopTodoList();
    }

    private void loadTopTodoList() {
        todolistManager.getTopTodoList(2).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                todoList.clear();
                for (DocumentSnapshot document : task.getResult()) {
                    TodoItem todoItem = document.toObject(TodoItem.class);
                    if (todoItem != null) {
                        todoItem.setId(document.getId());
                        todoList.add(todoItem);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}