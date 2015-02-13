package com.bitdubai.wallet_platform_api.layer._9_network_service.user;

import java.util.UUID;

/**
 * Created by ciencias on 2/13/15.
 */
public interface UserNetworkService {
    
    public SystemUser getSystemUser (UUID userId);
}
