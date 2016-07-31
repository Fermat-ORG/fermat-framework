package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatUserIdentity;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Chat SubApp Session
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 30/12/15.
 * @version 1.0
 */
public class ChatSessionReferenceApp extends AbstractReferenceAppFermatSession<InstalledSubApp, ChatManager, SubAppResourcesProviderManager> {

    public static final String CONTACT_DATA = "CONTACT_DATA";
    public static final String CONTACTTOUPDATE_DATA = "CONTACTTOUPDATE_DATA";
    public static final String CONNECTION_DATA = "CONNECTION_DATA";
    public static final String PROFILE_DATA = "PROFILE_DATA";
    public static final String CHAT_DATA = "CHAT_DATA";
    public static final String whocallme = "whocallme";
    public static final String chatvalues = "chatvalues";
    public static final String chatid = "chatid";

    public ChatSessionReferenceApp() {
    }

    public Contact getSelectedContact() {
        Object data = getData(CONTACT_DATA);
        return (data != null) ? (Contact) data : null;
    }

    public ChatUserIdentity getSelectedProfile() {
        Object data = getData(PROFILE_DATA);
        return (data != null) ? (ChatUserIdentity) data : null;
    }

    //    public ContactConnection getSelectedConnection() {
//        Object data = getData(CONNECTION_DATA);
//        return (data != null) ? (ContactConnection) data : null;
//    }
//
    public Chat getSelectedChat() {
        Object data = getData(CHAT_DATA);
        return (data != null) ? (Chat) data : null;
    }

    //
    public Contact getSelectedContactToUpdate() {
        Object data = getData(CONTACTTOUPDATE_DATA);
        return (data != null) ? (Contact) data : null;
    }
}
