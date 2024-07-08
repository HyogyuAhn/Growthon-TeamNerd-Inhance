package nerd.example.inha_project.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import nerd.example.inha_project.R;
import nerd.example.inha_project.account.LoginActivity;
import nerd.example.inha_project.database.TodolistManager;
import nerd.example.inha_project.util.todo.TodoItem;
import nerd.example.inha_project.util.todo.TodoListAdapter;

public class TodoListActivity extends AppCompatActivity {

    private TodolistManager todolistManager;
    private TodoListAdapter adapter;
    private List<TodoItem> todoList = new ArrayList<>();
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        userID = LoginActivity.loginInstance.getID();

        TextView todoDate = findViewById(R.id.todo_date);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        ImageView back = findViewById(R.id.back_arrow);

        back.setOnClickListener(v -> finish());

        todolistManager = new TodolistManager(userID);

        adapter = new TodoListAdapter(todoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadTodoList();

        adapter.setOnTodoItemLongClickListener(position -> {
            TodoItem item = todoList.get(position);
            showEditTodoDialog(item);
        });

        adapter.setOnTodoItemCheckedChangeListener((position, isChecked) -> {
            TodoItem item = todoList.get(position);
            item.setCompleted(isChecked);
            todolistManager.updateTodoItem(item.getId(), item);
        });

        fabAdd.setOnClickListener(v -> showAddTodoDialog());

    }

    private void loadTodoList() {
        todolistManager.getTodoList().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                todoList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    TodoItem todoItem = document.toObject(TodoItem.class);
                    todoItem.setId(document.getId());
                    todoList.add(todoItem);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void showAddTodoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_todo, null);
        builder.setView(dialogView);

        EditText etTodoTitle = dialogView.findViewById(R.id.et_todo_title);
        Button btnAdd = dialogView.findViewById(R.id.btn_add);

        AlertDialog dialog = builder.create();

        btnAdd.setOnClickListener(v -> {
            String title = etTodoTitle.getText().toString().trim();
            if (!title.isEmpty()) {
                TodoItem newItem = new TodoItem(title, false);
                String newItemId = String.valueOf(System.currentTimeMillis());
                newItem.setId(newItemId);
                todolistManager.addTodoItem(newItemId, newItem);
                todoList.add(newItem);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void showEditTodoDialog(TodoItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_todo, null);
        builder.setView(dialogView);

        EditText etTodoTitle = dialogView.findViewById(R.id.et_todo_title);
        Button btnUpdate = dialogView.findViewById(R.id.btn_update);
        Button btnDelete = dialogView.findViewById(R.id.btn_delete);

        etTodoTitle.setText(item.getTitle());

        AlertDialog dialog = builder.create();

        btnUpdate.setOnClickListener(v -> {
            String title = etTodoTitle.getText().toString().trim();
            if (!title.isEmpty()) {
                item.setTitle(title);
                todolistManager.updateTodoItem(item.getId(), item);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(v -> {
            todolistManager.deleteTodoItem(item.getId());
            todoList.remove(item);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        dialog.show();
    }
}