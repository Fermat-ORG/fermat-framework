package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * The class <code>AbstractNetworkService</code>
 * contains the basic functionality of a Fermat Network Service.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/10/2015.
 */
public abstract class AbstractNetworkService extends AbstractPlugin implements NetworkService {

    /**
     * Network Service details.
     */
    private final PlatformComponentType    platformComponentType   ;
    private final NetworkServiceType       networkServiceType      ;
    private final String                   name                    ;
    private final String                   alias                   ;
    private final String                   extraData               ;

    private final EventSource              eventSource             ;

    private       PlatformComponentProfile platformComponentProfile;

    protected     boolean                  register                ;

    public AbstractNetworkService(final PluginVersionReference pluginVersionReference,
                                  final PlatformComponentType  platformComponentType ,
                                  final NetworkServiceType     networkServiceType    ,
                                  final String                 name                  ,
                                  final String                 alias                 ,
                                  final String                 extraData             ,
                                  final EventSource            eventSource           ) {

        super(pluginVersionReference);

        this.platformComponentType = platformComponentType;
        this.networkServiceType    = networkServiceType   ;
        this.name                  = name                 ;
        this.alias                 = alias                ;
        this.extraData             = extraData            ;
        this.eventSource           = eventSource          ;

    }

    public final boolean isRegister() {
        return register;
    }

    public abstract String getIdentityPublicKey();

    public final String getName() {
        return name;
    }

    public final String getAlias() {
        return alias;
    }

    public final String getExtraData() {
        return extraData;
    }

    public final PlatformComponentType getPlatformComponentType() {
        return platformComponentType;
    }

    public final NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    public final EventSource getEventSource() {
        return eventSource;
    }

    public final PlatformComponentProfile getPlatformComponentProfilePluginRoot() {
        return platformComponentProfile;
    }

    public void setPlatformComponentProfilePluginRoot(PlatformComponentProfile platformComponentProfile) {
        this.platformComponentProfile = platformComponentProfile;
    }

    public abstract void initializeCommunicationNetworkServiceConnectionManager();

    public abstract void handleNewMessages(FermatMessage message);

}
