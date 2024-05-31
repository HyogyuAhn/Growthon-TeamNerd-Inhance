package nerd.example.inha_project.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nerd.example.inha_project.R;
import nerd.example.inha_project.chatbot.ChatMsg;
import nerd.example.inha_project.chatbot.ChatMsgAdapter;
import nerd.example.inha_project.chatbot.Retrofit_Builder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AIChatFragment extends Fragment {

    RecyclerView recyclerView;
    ChatMsgAdapter adp;
    Button btnSend;
    EditText etMsg;
    List<ChatMsg> chatMsgList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aichat, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("INHAnce Chat Bot");
            }
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        btnSend = view.findViewById(R.id.btn_send);
        etMsg = view.findViewById(R.id.et_msg);

        chatMsgList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adp = new ChatMsgAdapter();
        recyclerView.setAdapter(adp);

        btnSend.setOnClickListener(v -> {
            String msg = etMsg.getText().toString();
            adp.addChatMsg(new ChatMsg(ChatMsg.TYPE_MY_CHAT, msg));
            etMsg.setText(null);
            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            Retrofit_Builder.sendMessage(getContext(), msg, new Callback<Retrofit_Builder.OpenAIResponse>() {
                @Override
                public void onResponse(Call<Retrofit_Builder.OpenAIResponse> call, Response<Retrofit_Builder.OpenAIResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String botMessage = response.body().getChoices().get(0).getMessage().getContent();
                        getActivity().runOnUiThread(() -> adp.addChatMsg(new ChatMsg(ChatMsg.TYPE_BOT_CHAT, botMessage)));
                    } else {
                        getActivity().runOnUiThread(() -> adp.addChatMsg(new ChatMsg(ChatMsg.TYPE_BOT_CHAT, "Error: " + response.message())));
                    }
                }

                @Override
                public void onFailure(Call<Retrofit_Builder.OpenAIResponse> call, Throwable t) {
                    getActivity().runOnUiThread(() -> adp.addChatMsg(new ChatMsg(ChatMsg.TYPE_BOT_CHAT, "Error: " + t.getMessage())));
                }
            });
        });

        etMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnSend.setEnabled(s.length() > 0);
            }
        });

        return view;
    }

}