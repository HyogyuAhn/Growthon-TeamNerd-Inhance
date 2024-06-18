package nerd.example.inha_project.util.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nerd.example.inha_project.R;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {

    private List<TodoItem> todoList;
    private OnTodoItemLongClickListener longClickListener;
    private OnTodoItemCheckedChangeListener checkedChangeListener;

    public TodoListAdapter(List<TodoItem> todoList) {
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todoitem, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        TodoItem todoItem = todoList.get(position);
        holder.bind(todoItem);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void setOnTodoItemLongClickListener(OnTodoItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public void setOnTodoItemCheckedChangeListener(OnTodoItemCheckedChangeListener listener) {
        this.checkedChangeListener = listener;
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private CheckBox cbCompleted;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            cbCompleted = itemView.findViewById(R.id.cb_completed);

            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    longClickListener.onTodoItemLongClick(getAdapterPosition());
                }
                return true;
            });

            cbCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (checkedChangeListener != null) {
                    checkedChangeListener.onTodoItemCheckedChange(getAdapterPosition(), isChecked);
                }
            });
        }

        public void bind(TodoItem todoItem) {
            tvTitle.setText(todoItem.getTitle());
            cbCompleted.setChecked(todoItem.isCompleted());
        }
    }

    public interface OnTodoItemLongClickListener {
        void onTodoItemLongClick(int position);
    }

    public interface OnTodoItemCheckedChangeListener {
        void onTodoItemCheckedChange(int position, boolean isChecked);
    }
}