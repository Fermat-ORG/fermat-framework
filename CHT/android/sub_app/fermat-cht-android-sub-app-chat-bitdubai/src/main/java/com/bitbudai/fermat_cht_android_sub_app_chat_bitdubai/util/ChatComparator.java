package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.Chat;

import java.util.Comparator;

/**
 * The class <code>com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.ChatMessageComparator</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/09/2016.
 *
 * @author  lnacosta
 * @version 1.0
 */
public class ChatComparator implements Comparator<Chat> {

    @Override
    public int compare(Chat obj1, Chat obj2) {
        return obj2.getDateMessage().compareTo(obj1.getDateMessage());
    }
}