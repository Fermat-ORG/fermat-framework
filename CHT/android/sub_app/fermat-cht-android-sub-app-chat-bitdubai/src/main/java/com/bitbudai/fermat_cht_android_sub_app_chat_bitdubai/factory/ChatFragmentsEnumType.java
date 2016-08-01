package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * ChatFragmentsEnumType
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com)  on 29/12/15.
 * @version 1.0
 */
public enum ChatFragmentsEnumType implements FermatFragmentsEnumType<ChatFragmentsEnumType> {


    CHT_CHAT_OPEN_CHATLIST_TAB_FRAGMENT("CHTOCHLTF"),
    CHT_CHAT_OPEN_CONTACTLIST_TAB_FRAGMENT("CHTOCONTLTF"),
    CHT_CHAT_OPEN_CONNECTIONLIST_TAB_FRAGMENT("CHTOCONNTLTF"),
    CHT_CHAT_OPEN_CONTACTLIST_FRAGMENT("CHTOCONLTF"),
    CHT_CHAT_OPEN_CHAT_DETAIL_FRAGMENT("CHTOCHDF"),
    CHT_CHAT_OPEN_CONTACT_DETAIL_FRAGMENT("CHTOCODF"),
    CHT_CHAT_EDIT_CONTACT_FRAGMENT("CHTEDCOF"),
    CHT_CHAT_OPEN_MESSAGE_LIST_FRAGMENT("CHTOMLF"),
    CHT_CHAT_OPEN_PROFILELIST_FRAGMENT("CHTOPLF"),
    CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL("CHTBWTD"),
    CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL("CHTBWOD"),
    CHT_CHAT_OPEN_PROFILE_DETAIL_FRAGMENT("CHTOPODF"),
    CHT_CHAT_OPEN_SEND_ERROR_REPORT_FRAGMENT("CHTOSERF"),
    CHT_CHAT_BROADCAST_WIZARD_TWO_SCHEDULED_DETAIL("CHTBWTSD"),
    CHT_CHAT_OPEN_CONNECTIONLIST_FRAGMENT("CHTOCONNLTF");


    private String key;

    ChatFragmentsEnumType(String key) {
        this.key = key;
    }

    public static ChatFragmentsEnumType getValue(String name) {
        for (ChatFragmentsEnumType fragments : ChatFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }

    @Override
    public String getKey() {
        return key;
    }

}
