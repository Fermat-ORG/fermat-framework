package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure;

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
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/11/15.
 */
public class TransactionTransmissionCommunicationNetworkServiceConnectionManager extends AbstractCommunicationNetworkServiceConnectionManager {
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
    public TransactionTransmissionCommunicationNetworkServiceConnectionManager(
            PlatformComponentProfile platformComponentProfile,
            ECCKeyPair identity,
            CommunicationsClientConnection communicationsClientConnection,
            Database dataBase,
            ErrorManager errorManager,
            EventManager eventManager,
            EventSource eventSource,
            PluginVersionReference pluginVersionReference) {
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
