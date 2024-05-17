package nerd.example.inha_project.account;

public class AccountManager {
    public static boolean loginRequest(String id, String pw) {
        return id.equals("12240000") && pw.equals("1234");
    }

}
