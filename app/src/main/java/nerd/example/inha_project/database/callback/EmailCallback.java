package nerd.example.inha_project.database.callback;

public interface EmailCallback {

    void onDuplicateFound(String email);
    void onNoDuplicateFound();
    void onError(Exception exception);

}
