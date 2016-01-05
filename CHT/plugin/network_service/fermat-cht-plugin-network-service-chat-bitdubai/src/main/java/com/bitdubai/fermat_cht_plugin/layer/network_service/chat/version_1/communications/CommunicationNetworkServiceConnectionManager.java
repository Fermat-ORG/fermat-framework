package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.version_1.communications;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.AbstractCommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 29/12/2015.
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
                                                        final PluginVersionReference pluginVersionReference,
                                                        AbstractNetworkService abstractNetworkService) {

        super(
                abstractNetworkService,
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
