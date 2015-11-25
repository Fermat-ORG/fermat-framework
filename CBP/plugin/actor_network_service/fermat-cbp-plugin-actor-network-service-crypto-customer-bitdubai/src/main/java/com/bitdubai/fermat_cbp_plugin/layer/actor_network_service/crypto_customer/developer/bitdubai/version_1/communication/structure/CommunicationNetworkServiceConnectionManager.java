package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.communication.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.AbstractCommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.communication.structure.CommunicationNetworkServiceConnectionManager</code>
 * <p/>
 * Created by lnacosta (laion.cj91@gmail.com) on 02/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class CommunicationNetworkServiceConnectionManager extends AbstractCommunicationNetworkServiceConnectionManager {

    public CommunicationNetworkServiceConnectionManager(final PlatformComponentProfile platformComponentProfile,
                                                        final ECCKeyPair identity,
                                                        final CommunicationsClientConnection communicationsClientConnection,
                                                        final Database dataBase,
                                                        final ErrorManager errorManager,
                                                        final EventManager eventManager,
                                                        final EventSource eventSource,
                                                        final PluginVersionReference pluginVersionReference) {

        super(
                platformComponentProfile      ,
                identity                      ,
                communicationsClientConnection,
                dataBase                      ,
                errorManager                  ,
                eventManager                  ,
                eventSource                   ,
                pluginVersionReference
        );
    }


}