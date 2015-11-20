package com.bitdubai.fermat_api.layer.dmp_network_service.template;

import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;

import java.util.UUID;

/**
 * Created by ciencias on 2/13/15.
 */
public interface TemplateNetworkService extends NetworkService {
    
    public Template getSystemUser(UUID userId);
}
