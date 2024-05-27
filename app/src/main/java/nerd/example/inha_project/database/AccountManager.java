package nerd.example.inha_project.database;

import static nerd.example.inha_project.util.Util.*;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import nerd.example.inha_project.account.User;
import nerd.example.inha_project.database.callback.CreateAccountCallback;
import nerd.example.inha_project.database.callback.DBCallback;
import nerd.example.inha_project.database.callback.LoginCallback;

public class AccountManager {

    @SuppressLint("StaticFieldLeak")
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static User data = new User();

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
                if (user != null && user.getPassword().equals(pw)) callback.onLoginResult(true);
                else callback.onLoginResult(false);
            }
        });
    }

}
