package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatUserIdentity;
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
    public static final String PROFILE_DATA = "PROFILE_DATA";
    public static final String CHAT_DATA = "CHAT_DATA";
    public static final String CHATS_ID = "CHATS_ID";

    public ChatSessionReferenceApp() {
    }

    public ChatUserIdentity getSelectedProfile() {
        Object data = getData(PROFILE_DATA);
        return (data != null) ? (ChatUserIdentity) data : null;
    }

}
