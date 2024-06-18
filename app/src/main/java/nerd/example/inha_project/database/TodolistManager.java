package nerd.example.inha_project.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import nerd.example.inha_project.util.todo.TodoItem;

public class TodolistManager {

    private FirebaseFirestore db;
    private CollectionReference todoListRef;
    private String userId;

    public TodolistManager(String userId) {
        this.userId = userId;
        db = FirebaseFirestore.getInstance();
        todoListRef = db.collection("users").document(userId).collection("todolists");
    }

    public Task<QuerySnapshot> getTodoList() {
        return todoListRef.get();
    }

    public void addTodoItem(String id, TodoItem item) {
        todoListRef.document(id).set(item);
    }

    public void updateTodoItem(String id, TodoItem item) {
        todoListRef.document(id).update("title", item.getTitle(), "completed", item.isCompleted());
    }

    public void deleteTodoItem(String id) {
        todoListRef.document(id).delete();
    }

    public Query getTopTodoList(int limit) {
        return todoListRef.orderBy("title").limit(limit);
    }
}
