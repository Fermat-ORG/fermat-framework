package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Chat SubApp Session
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 30/12/15.
 * @version 1.0
 */
public class ChatSession extends AbstractFermatSession<InstalledSubApp, ChatModuleManager,SubAppResourcesProviderManager> {

    public static final String CONTACT_DATA = "CONTACT_DATA";
    public static final String CONNECTION_DATA = "CONNECTION_DATA";

    public ChatSession() {}

    public Contact getSelectedContact() {
        Object data = getData(CONTACT_DATA);
        return (data != null) ? (Contact) data : null;
    }

    public Contact getSelectedConnection() {
        Object data = getData(CONNECTION_DATA);
        return (data != null) ? (Contact) data : null;
    }

}
