package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.AbstractCommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 25/11/15.
 */
public class CommunicationNetworkServiceConnectionManager extends AbstractCommunicationNetworkServiceConnectionManager {
    /**
     * Constructor with parameters.
     *
     * @param platformComponentProfile
     * @param identity
     * @param communicationsClientConnection
     * @param dataBase
     * @param errorManager
     * @param eventManager
     * @param eventSource
     * @param pluginVersionReference
     */
    public CommunicationNetworkServiceConnectionManager(final PlatformComponentProfile platformComponentProfile,
                                                        final ECCKeyPair identity,
                                                        final CommunicationsClientConnection communicationsClientConnection,
                                                        final Database dataBase,
                                                        final ErrorManager errorManager,
                                                        final EventManager eventManager,
                                                        final EventSource eventSource,
                                                        final PluginVersionReference pluginVersionReference) {

        super(
                platformComponentProfile,
                identity,
                communicationsClientConnection,
                dataBase,
                errorManager,
                eventManager,
                eventSource,
                pluginVersionReference);
    }
}
