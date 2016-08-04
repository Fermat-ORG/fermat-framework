package com.bitdubai.fermat_cbp_api.layer.middleware.sub_app_manager.interfaces;


import java.util.List;

/**
 * Created by angel on 16/9/15.
 */

public interface subAppManger {

    List<subAppMiddleware> getAllSubAppInstalled();

    List<subAppMiddleware> getAllSubAppNotInstalled();

    void installedSubApp(String name);

    void uninstalledSubApp(String name);

}
