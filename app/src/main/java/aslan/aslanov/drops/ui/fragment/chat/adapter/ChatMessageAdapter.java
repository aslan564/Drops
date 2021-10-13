package aslan.aslanov.drops.ui.fragment.chat.adapter;

import static aslan.aslanov.drops.util.UtilConstant.RECEIVER;
import static aslan.aslanov.drops.util.UtilConstant.SENDER;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import aslan.aslanov.drops.databinding.LayoutItemMessageReceiverBinding;
import aslan.aslanov.drops.databinding.LayoutItemMessageSenderBinding;
import aslan.aslanov.drops.model.TextMessage;

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<TextMessage> textMessages = new ArrayList<>();


    public ChatMessageAdapter() {
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                LayoutItemMessageSenderBinding layoutItemMessageSenderBinding = LayoutItemMessageSenderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ChatMessageSenderViewHolder(layoutItemMessageSenderBinding);
            case 2:
                LayoutItemMessageReceiverBinding layoutItemMessageReceiverBinding = LayoutItemMessageReceiverBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ChatMessageReceiverViewHolder(layoutItemMessageReceiverBinding);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1:
                ChatMessageSenderViewHolder chatMessageSenderViewHolder = (ChatMessageSenderViewHolder) holder;
                chatMessageSenderViewHolder.bind(textMessages.get(position));
                break;
            case 2:
                ChatMessageReceiverViewHolder chatMessageReceiverViewHolder = (ChatMessageReceiverViewHolder) holder;
                chatMessageReceiverViewHolder.bind(textMessages.get(position));
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (textMessages.get(position).getWriterType() == SENDER) {
            return 1;
        } else if (textMessages.get(position).getWriterType() == RECEIVER) {
            return 2;
        } else
            return -1;
    }

    @Override
    public int getItemCount() {
        return textMessages.size();
    }

    public void addMessageItemToList(HashMap<Date, TextMessage> message) {
        textMessages.addAll(message.values());
    }


    public static class ChatMessageSenderViewHolder extends RecyclerView.ViewHolder {
        private final LayoutItemMessageSenderBinding itemMessageSenderBinding;

        public ChatMessageSenderViewHolder(LayoutItemMessageSenderBinding binding) {
            super(binding.getRoot());
            itemMessageSenderBinding = binding;
        }

        public void bind(TextMessage message) {
            itemMessageSenderBinding.setSenderMessage(message);
        }
    }

    public static class ChatMessageReceiverViewHolder extends RecyclerView.ViewHolder {
        private final LayoutItemMessageReceiverBinding itemMessageReceiverBinding;

        public ChatMessageReceiverViewHolder(LayoutItemMessageReceiverBinding binding) {
            super(binding.getRoot());
            itemMessageReceiverBinding = binding;
        }

        public void bind(TextMessage message) {
            itemMessageReceiverBinding.setReceiverMessage(message);
        }
    }
}
