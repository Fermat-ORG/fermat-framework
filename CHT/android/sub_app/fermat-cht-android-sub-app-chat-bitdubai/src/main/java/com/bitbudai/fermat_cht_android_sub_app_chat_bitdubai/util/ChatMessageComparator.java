package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatMessage;

import java.util.Comparator;

/**
 * The class <code>com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.ChatMessageComparator</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/09/2016.
 */
public class ChatMessageComparator implements Comparator<ChatMessage> {

    @Override
    public int compare(ChatMessage obj1, ChatMessage obj2) {
        return obj1.getDate().compareTo(obj2.getDate());
    }
}