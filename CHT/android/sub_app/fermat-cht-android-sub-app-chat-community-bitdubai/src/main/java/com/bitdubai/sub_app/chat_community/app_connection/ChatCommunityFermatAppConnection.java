package com.bitdubai.sub_app.chat_community.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.sub_app.chat_community.fragmentFactory.ChatCommunityFragmentFactory;
import com.bitdubai.sub_app.chat_community.navigation_drawer.ChatCommunityNavigationViewPainter;
import com.bitdubai.sub_app.chat_community.notifications.CommunityNotificationPainterBuilder;
import com.bitdubai.sub_app.chat_community.session.ChatUserSubAppSessionReferenceApp;

/**
 * ChatCommunityFermatAppConnection
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
public class ChatCommunityFermatAppConnection
        extends AppConnections<ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager>> {

    private ChatActorCommunitySubAppModuleManager moduleManager;
    private ChatActorCommunitySelectableIdentity activeIdentity;
    ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> chatUserSubAppSession;
    private ChatCommunityNavigationViewPainter navPainter;

    public ChatCommunityFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new ChatCommunityFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return new PluginVersionReference[]{new PluginVersionReference(
                Platforms.CHAT_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.CHAT_COMMUNITY_SUP_APP_MODULE,
                Developers.BITDUBAI,
                new Version()
        )};
    }

    @Override
    public AbstractReferenceAppFermatSession getSession() {
        return new ChatUserSubAppSessionReferenceApp();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        navPainter = new ChatCommunityNavigationViewPainter(getContext(), getFullyLoadedSession(), getApplicationManager());
        return navPainter;
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return null;
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }

    @Override
    public NotificationPainter getNotificationPainter(FermatBundle fermatBundle) {
        return CommunityNotificationPainterBuilder.getNotification(fermatBundle);
    }

    @Override
    public ResourceSearcher getResourceSearcher() {
        return new ChatCommunityResourceSearcher();
    }
}
