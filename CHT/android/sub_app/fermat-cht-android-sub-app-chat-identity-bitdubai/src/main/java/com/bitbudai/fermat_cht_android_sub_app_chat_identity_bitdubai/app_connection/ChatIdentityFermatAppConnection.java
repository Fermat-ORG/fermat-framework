package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.app_connection;

import android.content.Context;

import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.factory.ChatIdentityFragmentFactory;
import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.sessions.ChatIdentitySessionReferenceApp;
import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

/**
 * FERMAT-ORG
 * Developed by Lozadaa on 05/04/16.
 */
public class ChatIdentityFermatAppConnection extends AppConnections {
    public ChatIdentityFermatAppConnection(Context activity) {
        super(activity);
    }


    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new ChatIdentityFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return new PluginVersionReference[]{new PluginVersionReference(
                Platforms.CHAT_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.CHAT_IDENTITY_SUP_APP_MODULE,
                Developers.BITDUBAI,
                new Version()
        )};
    }

    @Override
    public AbstractReferenceAppFermatSession getSession() {
        return new ChatIdentitySessionReferenceApp();
    }


    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        System.out.println("chttttt");
        return null;
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        System.out.println("chttttt");
        return null;
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        System.out.println("chttttt");
        return null;
    }

    @Override
    public ResourceSearcher getResourceSearcher() {
        return new ChatIdentityResourceSearcher();
    }
}
