package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.factory;


import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.fragments.CreateChatIdentityFragment;
import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.fragments.EditChatIdentityFragment;
import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.sessions.ChatIdentitySession;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * FERMAT-ORG
 * Developed by Lozadaa on 05/04/16.
 */
public class ChatIdentityFragmentFactory extends FermatFragmentFactory<ChatIdentitySession, SubAppResourcesProviderManager, ChatIdentityFragmentsEnumType> {

    @Override
    public AbstractFermatFragment getFermatFragment(ChatIdentityFragmentsEnumType fragments) throws FragmentNotFoundException {
        switch (fragments) {
            case CHT_CHAT_CREATE_IDENTITY_FRAGMENT:
                return CreateChatIdentityFragment.newInstance();

            case CHT_CHAT_EDIT_IDENTITY:
                return EditChatIdentityFragment.newInstance();
            default:
                throw new FragmentNotFoundException(String.format("Fragment: %s not found", fragments.getKey()),
                        new Exception(), "fermat-cht-android-sub-app-identity", "fragment not found");
        }
    }

    @Override
    public ChatIdentityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return ChatIdentityFragmentsEnumType.getValue(key);
    }
}