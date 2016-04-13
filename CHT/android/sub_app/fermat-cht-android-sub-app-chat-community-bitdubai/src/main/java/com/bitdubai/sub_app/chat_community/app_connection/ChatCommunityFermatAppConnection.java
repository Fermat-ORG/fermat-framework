package com.bitdubai.sub_app.chat_community.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;

/**
 * ChatCommunityFermatAppConnection
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
public class ChatCommunityFermatAppConnection extends AppConnections<IntraUserSubAppSession> {

    private IntraUserSubAppSession intraUserSubAppSession;
    private IntraUserModuleManager moduleManager;

    public ChatCommunityFermatAppConnection(Context activity) {
        super(activity);

    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new IntraUserFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return  new PluginVersionReference(
                Platforms.CHAT_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.CHAT_ACTOR_CONNECTION,
                Developers.BITDUBAI,
                new Version()
        );
    }

    @Override
    public AbstractFermatSession getSession() {
        return new IntraUserSubAppSession();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new ChatCommunityNavigationViewPainter(getContext(),getActiveIdentity());
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
    public NotificationPainter getNotificationPainter(String code){
        try
        {
            this.intraUserSubAppSession = (IntraUserSubAppSession)this.getSession();
            if(intraUserSubAppSession!=  null)
                moduleManager = intraUserSubAppSession.getModuleManager();
            return ChatCommunityBuildNotification.getNotification(moduleManager,code);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
