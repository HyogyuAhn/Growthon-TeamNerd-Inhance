package nerd.example.inha_project.chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nerd.example.inha_project.R;

public class ChatMsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ChatMsg> dataList = new ArrayList<>();

    public void setDataList(List<ChatMsg> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void addChatMsg(ChatMsg chatMsg) {
        dataList.add(chatMsg);
        notifyItemInserted(dataList.size() - 1);
    }
    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == ChatMsg.TYPE_MY_CHAT) {
            return new MyChatViewHolder(inflater.inflate(R.layout.item_my_chat, parent, false));
        }

        return new BotChatViewHolder(inflater.inflate(R.layout.item_bot_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMsg chatMsg = dataList.get(position);
        if (chatMsg.type == ChatMsg.TYPE_MY_CHAT) {
            ((MyChatViewHolder) holder).setMsg(chatMsg);
        } else {
            ((BotChatViewHolder) holder).setMsg(chatMsg);
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class MyChatViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMsg;

        public MyChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.tv_msg);
        }

        public void setMsg(ChatMsg chatMsg) {
            tvMsg.setText(chatMsg.msg);
        }
    }

    class BotChatViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMsg;

        public BotChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.tv_msg);
        }

        public void setMsg(ChatMsg chatMsg) {
            tvMsg.setText(chatMsg.msg);
        }
    }
}
