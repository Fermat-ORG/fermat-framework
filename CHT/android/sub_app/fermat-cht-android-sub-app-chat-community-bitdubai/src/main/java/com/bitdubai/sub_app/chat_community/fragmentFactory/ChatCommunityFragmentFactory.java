package com.bitdubai.sub_app.chat_community.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.chat_community.fragments.ConnectionNotificationsFragment;
import com.bitdubai.sub_app.chat_community.fragments.ConnectionOtherProfileFragment;
import com.bitdubai.sub_app.chat_community.fragments.ConnectionsWorldFragment;
import com.bitdubai.sub_app.chat_community.fragments.ContactsListFragment;
import com.bitdubai.sub_app.chat_community.session.ChatUserSubAppSessionReferenceApp;


/**
 * ChatCommunityFragmentFactory
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */

public class ChatCommunityFragmentFactory extends FermatFragmentFactory<ChatUserSubAppSessionReferenceApp, SubAppResourcesProviderManager, ChatCommunityFragmentsEnumType> {


    @Override
    public AbstractFermatFragment getFermatFragment(ChatCommunityFragmentsEnumType fragments) throws FragmentNotFoundException {
        AbstractFermatFragment currentFragment = null;

        switch (fragments) {
            case CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_DETAIL_FRAGMENT:
                currentFragment = null;
                break;
            case CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT:
                currentFragment = ConnectionNotificationsFragment.newInstance();
                break;
            case CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT:
                currentFragment = ConnectionOtherProfileFragment.newInstance();
                break;
            case CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD_FRAGMENT:
                currentFragment = ConnectionsWorldFragment.newInstance();
                break;
            case CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST_FRAGMENT:
                currentFragment = ContactsListFragment.newInstance();
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found", new Exception(), fragments.toString(), "Swith failed");
        }
        return currentFragment;
    }

    @Override
    public ChatCommunityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return ChatCommunityFragmentsEnumType.getValue(key);
    }

}
