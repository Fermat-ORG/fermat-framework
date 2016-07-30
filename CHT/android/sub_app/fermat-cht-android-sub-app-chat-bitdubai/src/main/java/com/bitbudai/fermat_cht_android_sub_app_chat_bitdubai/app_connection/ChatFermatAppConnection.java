package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.app_connection;

import android.content.Context;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.factory.ChatFragmentFactory;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.notifications.ChatNotificationPainterBuilder;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
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
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.util.ChatBroadcasterConstants;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;

/**
 * ChatFermatAppConnection
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com)  on 08/01/16.
 * @version 1.0
 */
public class ChatFermatAppConnection
        extends AppConnections<ReferenceAppFermatSession<ChatManager>> {

    public ChatFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new ChatFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return new PluginVersionReference[]{new PluginVersionReference(
                Platforms.CHAT_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.CHAT_SUP_APP_MODULE,
                Developers.BITDUBAI,
                new Version()
        )};
    }

    @Override
    public AbstractReferenceAppFermatSession getSession() {
        return new ChatSessionReferenceApp();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return null;
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
        int notificationID = fermatBundle.getInt(NotificationBundleConstants.NOTIFICATION_ID);

        switch (notificationID) {
            case ChatBroadcasterConstants.CHAT_NEW_INCOMING_MESSAGE_NOTIFICATION:
                return new ChatNotificationPainterBuilder("New Message.", "New message in Chat.", "", R.drawable.chat_subapp);
            default:
                return null;
        }
//
//        NotificationPainter notification = null;
//        try
//        {
//
//            String usersend = code.split("@#@#")[0];
//            String message = code.split("@#@#")[1];
//            //find last transaction
//            notification = new ChatNotificationPainter("New Message Receive", usersend+" : "+message ,"","", android.R.drawable.ic_notification_overlay);
//
//        }
//        catch(Exception e)
//        {
//            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//        }
//
//        return notification;
    }

    @Override
    public ResourceSearcher getResourceSearcher() {
        return new ChatResourceSearcher();
    }
}
