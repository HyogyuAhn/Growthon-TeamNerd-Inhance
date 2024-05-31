package nerd.example.inha_project.chatbot;

public class ChatMsg {

    public static final int TYPE_MY_CHAT = 0;
    public static final int TYPE_BOT_CHAT = 1;

    public int type;
    public String msg;

    public ChatMsg(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }
}