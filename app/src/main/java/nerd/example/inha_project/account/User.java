package nerd.example.inha_project.account;

import java.util.Random;

public class User {

    private String id;
    private String pw;
    private String email;
    private String nickname;
    private String profileImage;
    private String statusMessage;

    private boolean pushMessage;

    public User() {

    }

    public User(String id) {
        this.id = id;
    }

    public User(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public User(String id, String pw, String email) {
        this.id = id;
        this.pw = pw;
        this.email = email;
    }

    public User(String id, String pw, String email, String nickname) {
        this.id = id;
        this.pw = pw;
        this.email = email;
        this.nickname = nickname;
    }


    public User(String id, String pw, String email, String nickname, String profileImage, String statusMessage) {
        this.id = id;
        this.pw = pw;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.statusMessage = statusMessage;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setPassword(String pw) {
        this.pw = pw;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setPushMessage(boolean pushMessage) {
        this.pushMessage = pushMessage;
    }

    public void setData(String id, String pw, String email, String nickname, String profileImage, String statusMessage, boolean pushMessage) {
        this.id = id;
        this.pw = pw;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.statusMessage = statusMessage;
        this.pushMessage = pushMessage;
    }

    public String getID() {
        return this.id;
    }

    public String getPassword() {
        return this.pw;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getProfileImage() {
        return this.profileImage;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public boolean getPushMessage() {
        return this.pushMessage;
    }

    public void createRandomName() {
        Random random = new Random();
        this.nickname = "User" + (random.nextInt((999999 - 100000) + 1) + 100000);
    }

    public static String getRandomName() {
        Random random = new Random();
        return "User" + (random.nextInt((999999 - 100000) + 1) + 100000);
    }
}
