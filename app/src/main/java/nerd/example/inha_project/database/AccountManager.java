package nerd.example.inha_project.database;

import static nerd.example.inha_project.util.Util.*;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import nerd.example.inha_project.account.User;

public class AccountManager {

    @SuppressLint("StaticFieldLeak")
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static User data = new User();

    public static void createAccount(Context context, User user) {
        if (user.getID() == null) {
            sendToast(context, "ID is null");
            return;
        }
        if (user.getPassword() == null) {
            sendToast(context, "Password is null");
            return;
        }
        if (user.getNickname() == null) {
            user.createRandomName();
        }
        db.collection("users").document(user.getID())
                .set(user)
                .addOnSuccessListener(aVoid -> sendToast(context, "Success"))
                .addOnFailureListener(e -> sendToast(context, e.getMessage()));
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
