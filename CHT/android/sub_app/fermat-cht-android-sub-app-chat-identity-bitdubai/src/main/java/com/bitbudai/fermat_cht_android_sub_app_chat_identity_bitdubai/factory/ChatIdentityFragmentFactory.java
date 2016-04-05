package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.factory;


import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.fragments.CreateChatIdentityFragment;
import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.sessions.ChatIdentitySession;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * FERMAT-ORG
 * Developed by Lozadaa on 05/04/16.
 */

public class ChatIdentityFragmentFactory extends FermatFragmentFactory<ChatIdentitySession, SubAppResourcesProviderManager, ChatIdentityFragmentsEnumType> {

    @Override
    public AbstractFermatFragment getFermatFragment(ChatIdentityFragmentsEnumType fragments) throws FragmentNotFoundException {
        if (fragments == null) {
            throw createFragmentNotFoundException(null);
        }

        if(fragments.equals(ChatIdentityFragmentsEnumType.CHT_CHAT_CREATE_IDENTITY)){
            CreateChatIdentityFragment.newInstance();
        }

        throw createFragmentNotFoundException(fragments);
    }

    @Override
    public ChatIdentityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return ChatIdentityFragmentsEnumType.getValue(key);
    }

    private FragmentNotFoundException createFragmentNotFoundException(FermatFragmentsEnumType fragments) {
        String possibleReason, context;

        if (fragments == null) {
            possibleReason = "The parameter 'fragments' is NULL";
            context = "Null Value";
        } else {
            possibleReason = "Not found in switch block";
            context = fragments.toString();
        }

        return new FragmentNotFoundException("Fragment not found", new Exception(), context, possibleReason);
    }
}