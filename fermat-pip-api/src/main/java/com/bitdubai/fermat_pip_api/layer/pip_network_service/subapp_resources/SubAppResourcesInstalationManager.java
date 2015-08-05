package com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources;



import com.bitdubai.fermat_pip_api.layer.pip_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.exceptions.CantGetSubAppNavigationStructureException;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.exceptions.CantGetSubAppResourcesException;

import java.util.UUID;

/**
 * Created by natalia on 2015.07.28..
 */
public interface SubAppResourcesInstalationManager {


    /**
     *
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param screenSize
     * @param screenDensity
     * @param skinName
     * @param languageName
     * @param navigationStructureVersion
     */

    public void installResources(String walletCategory, String walletType,String developer,String screenSize,String screenDensity,String skinName,String languageName,String navigationStructureVersion);


    /**
     *
     * @param walletPath
     * @param skinId
     * @param navigationStructureVersion
     */

    public void unninstallResources(String walletPath,UUID skinId,String navigationStructureVersion);

}
