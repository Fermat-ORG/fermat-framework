package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;


/**
 * FERMAT-ORG
 * Developed by Lozadaa on 05/04/16.
 */
public class ChatIdentitySessionReferenceApp extends AbstractReferenceAppFermatSession<InstalledSubApp, ChatIdentityModuleManager, SubAppResourcesProviderManager> {
    public static final String IDENTITY_INFO = "CHAT_IDENTITY_INFO";
    public ChatIdentitySessionReferenceApp(){
    }
}
