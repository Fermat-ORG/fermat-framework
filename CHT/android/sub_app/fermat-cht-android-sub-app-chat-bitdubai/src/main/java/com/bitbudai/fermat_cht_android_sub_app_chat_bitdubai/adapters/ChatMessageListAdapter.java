package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.filters.ChatMessageListFilter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders.ChatMessageHolder;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatMessage;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.ChatMessageComparator;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.Utils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * ChatMessageListAdapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 05/01/15.
 * @version 1.0
 */

public class ChatMessageListAdapter extends FermatAdapter<ChatMessage, ChatMessageHolder> implements Filterable {

    private String filterString;

    long time, nanos, milliseconds;

    public ChatMessageListAdapter(Context context, ArrayList<ChatMessage> chatMessages) {
        super(context, chatMessages);
    }

    @Override
    protected ChatMessageHolder createHolder(View itemView, int type) {
        return new ChatMessageHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.chat_list_item;
    }

    @Override
    public void changeDataSet(List<ChatMessage> dataSet) {

        Collections.sort(dataSet, new ChatMessageComparator());

        super.changeDataSet(dataSet);
    }

    @Override
    protected void bindHolder(ChatMessageHolder holder, ChatMessage data, int position) {

        try {
            if (data != null) {
                boolean myMsg = data.getIsme();
                setAlignment(holder, myMsg, data);
                final String copiedMessage = holder.txtMessage.getText().toString();
                holder.content.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("simple text", copiedMessage);
                            clipboard.setPrimaryClip(clip);
                        } else {
                            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            clipboard.setText(copiedMessage);
                        }
                        if (copiedMessage.length() <= 10) {
                            Toast.makeText(context, context.getText(R.string.copy_message_toast) + " " + copiedMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, context.getText(R.string.copy_message_toast) + " " + copiedMessage.substring(0, 11) + "...", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public View getView() {
        LayoutInflater vi = LayoutInflater.from(context);
        View convertView = vi.inflate(R.layout.chat_list_item, null);
        return convertView;
    }

    public void add(ChatMessage message) {
        dataSet.add(message);
    }

    private void setAlignment(ChatMessageHolder holder, boolean isMe, ChatMessage data) {
        try {
            holder.tickstatusimage.setImageResource(0);

            holder.txtMessage.setText(Utils.avoidingScientificNot(data.getMessage()));
            holder.txtInfo.setText(getFormattedDate(data.getDate()));
            if (isMe) {
                holder.contentWithBG.setBackgroundResource(R.drawable.cht_burble_green);

                LinearLayout.LayoutParams layoutParams =
                        (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
                layoutParams.gravity = Gravity.RIGHT;
                holder.contentWithBG.setLayoutParams(layoutParams);

                RelativeLayout.LayoutParams lp =
                        (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                holder.content.setLayoutParams(lp);
                layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
                layoutParams.gravity = Gravity.RIGHT;
                holder.txtMessage.setLayoutParams(layoutParams);

                layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
                layoutParams.gravity = Gravity.RIGHT;
                holder.txtInfo.setLayoutParams(layoutParams);

                if (data.getStatus() != null) {
                    switch (data.getStatus()) {
                        case SENT:
                            holder.tickstatusimage.setVisibility(View.VISIBLE);
                            holder.tickstatusimage.setImageResource(R.drawable.cht_ticksent);
                            break;
                        case DELIVERED:
                            holder.tickstatusimage.setVisibility(View.VISIBLE);
                            holder.tickstatusimage.setImageResource(R.drawable.cht_tickdelivered);
                            break;
                        case RECEIVED:
                            holder.tickstatusimage.setVisibility(View.VISIBLE);
                            holder.tickstatusimage.setImageResource(R.drawable.cht_tickdelivered);
                            break;
                        case READ:
                            holder.tickstatusimage.setVisibility(View.VISIBLE);
                            holder.tickstatusimage.setImageResource(R.drawable.cht_tickread);
                            break;
                        case CANNOT_SEND:
                            holder.tickstatusimage.setVisibility(View.VISIBLE);
                            holder.tickstatusimage.setImageResource(R.drawable.cht_equis_icon);
                            break;
                        default:
                            holder.tickstatusimage.setImageResource(0);
                            holder.tickstatusimage.setVisibility(View.GONE);
                            break;
                    }
                } else {
                    holder.tickstatusimage.setImageResource(0);
                    holder.tickstatusimage.setVisibility(View.GONE);
                }
            } else {
                holder.contentWithBG.setBackgroundResource(R.drawable.cht_burble_white);

                LinearLayout.LayoutParams layoutParams =
                        (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
                layoutParams.gravity = Gravity.LEFT;
                holder.contentWithBG.setLayoutParams(layoutParams);

                RelativeLayout.LayoutParams lp =
                        (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                holder.content.setLayoutParams(lp);
                layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
                layoutParams.gravity = Gravity.LEFT;
                holder.txtMessage.setLayoutParams(layoutParams);

                layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
                layoutParams.gravity = Gravity.LEFT;
                holder.txtInfo.setLayoutParams(layoutParams);
                //holder.txtInfo.setPadding(20,0,20,7);
                holder.tickstatusimage.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DateFormat commonFormatter;
    private DateFormat todayFormatter;

    private String getFormattedDate(Timestamp timestamp) {

        if (commonFormatter == null || todayFormatter == null) {

            if (android.text.format.DateFormat.is24HourFormat(context)) {
                commonFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            } else {
                commonFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
            }
            if (android.text.format.DateFormat.is24HourFormat(context)) {
                todayFormatter = new SimpleDateFormat("HH:mm");
            } else {
                todayFormatter = new SimpleDateFormat("hh:mm aa");
            }
            commonFormatter.setTimeZone(TimeZone.getDefault());
            todayFormatter.setTimeZone(TimeZone.getDefault());
        }

        time = timestamp.getTime();
        nanos = (timestamp.getNanos() / 1000000);
        milliseconds = time + nanos;
        Date dated = new Date(milliseconds);

        if (Validate.isDateToday(dated))
            return todayFormatter.format(dated);
        else
            return commonFormatter.format(dated);
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    @Override
    public ChatMessage getItem(int position) {

        return dataSet != null ? (!dataSet.isEmpty()
                && position < dataSet.size()) ? dataSet.get(position) : null : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Filter getFilter() {
        return new ChatMessageListFilter(dataSet, this);
    }

    public void setFilterString(String filterString) {
        this.filterString = filterString;
    }

}