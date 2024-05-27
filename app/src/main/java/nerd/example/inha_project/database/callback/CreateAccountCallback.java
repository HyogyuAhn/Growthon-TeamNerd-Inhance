package nerd.example.inha_project.database.callback;

public interface CreateAccountCallback {

    void onSuccess();
    void onFailure(String error);

}
