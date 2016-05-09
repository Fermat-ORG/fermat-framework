package com.bitdubai.sub_app.chat_community.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * ChatUserSubAppSession
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
public class ChatUserSubAppSession extends AbstractFermatSession<InstalledSubApp,
        ChatActorCommunitySubAppModuleManager,SubAppResourcesProviderManager>{

    public static final String BASIC_DATA = "catalog item";
    public static final String PREVIEW_IMGS = "preview images";
    public static final String DEVELOPER_NAME = "developer name";

    public ChatUserSubAppSession() {}
}
