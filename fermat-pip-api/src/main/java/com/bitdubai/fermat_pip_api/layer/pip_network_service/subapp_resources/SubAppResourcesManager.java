package com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources;



import com.bitdubai.fermat_pip_api.layer.pip_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.exceptions.CantGetSubAppNavigationStructureException;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.exceptions.CantGetSubAppResourcesException;

import java.util.UUID;

/**
 * Created by natalia on 2015.07.28..
 */
public interface SubAppResourcesManager {

    /**
     * This method will give us the resources associated to the resourcesId
     *
     * @param resourcesId identifier of the resources we are looking for
     * @return An object that encapsulates the resources asked.
     */
    public SubAppResources getSubAppResources(UUID resourcesId) throws CantGetSubAppResourcesException;

    public SubAppNavigationStructure getSubAppNavigationStructure(UUID subappNavigationStructureId) throws CantGetSubAppNavigationStructureException;

}
