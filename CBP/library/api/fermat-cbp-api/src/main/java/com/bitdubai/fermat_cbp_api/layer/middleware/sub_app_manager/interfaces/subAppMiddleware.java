package com.bitdubai.fermat_cbp_api.layer.middleware.sub_app_manager.interfaces;

import java.util.UUID;

/**
 * Created by Angel on 16.09.15
 */
public interface subAppMiddleware {

    UUID getSubAppID();

    String getName();

    boolean getStatus();
}
