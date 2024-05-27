package nerd.example.inha_project.database.callback;

public interface DuplicateCallback {

    void onDuplicateFound(String email);
    void onNoDuplicateFound();
    void onError(Exception exception);

}
