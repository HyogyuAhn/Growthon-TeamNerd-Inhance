package nerd.example.inha_project.database;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import nerd.example.inha_project.account.User;
import nerd.example.inha_project.account.register.CHECK_TYPE;
import nerd.example.inha_project.database.callback.CreateAccountCallback;
import nerd.example.inha_project.database.callback.DBCallback;
import nerd.example.inha_project.database.callback.DuplicateCallback;
import nerd.example.inha_project.database.callback.LoginCallback;

public class AccountManager {

    @SuppressLint("StaticFieldLeak")
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();


    public static void createAccount(User user, CreateAccountCallback callback) {
        if (user.getID() == null) {
            callback.onFailure("학번(id)를 입력해 주세요.");
            return;
        }
        if (user.getPassword() == null) {
            callback.onFailure("비밀번호를 입력해 주세요.");
            return;
        }
        if (user.getNickname() == null) {
            user.createRandomName();
        }
        db.collection("users").document(user.getID())
                .set(user)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public static void getAccountData(String id, DBCallback callback) {
        try {
            DocumentReference docRef = db.collection("users").document(id);

            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            User user = document.toObject(User.class);
                            callback.onCallback(user);
                        } else {
                            callback.onCallback(null);
                        }
                    } else {
                        callback.onCallback(null);
                    }
                }
            });
        } catch (Exception e) {
            callback.onCallback(null);
        }
    }

    public static void loginRequest(String id, String pw, LoginCallback callback) {
        getAccountData(id, new DBCallback() {
            @Override
            public void onCallback(User user) {
                callback.onLoginResult(user != null && user.getPassword().equals(pw));
            }
        });
    }

    public static void checkDuplicate(CHECK_TYPE type, String compareStr, DuplicateCallback callback) {
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean isDuplicate = false;

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String data = document.getString(type.toString());
                            if (compareStr.equalsIgnoreCase(data) && !data.equals("null")) {
                                isDuplicate = true;
                                break;
                            }
                        }
                        if (isDuplicate) {
                            callback.onDuplicateFound(compareStr);
                        } else {
                            callback.onNoDuplicateFound();
                        }
                    } else {
                        callback.onError(task.getException());
                    }
                });
    }

}
