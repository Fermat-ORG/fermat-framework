package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.estructure;

import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppNavigationStructure;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.exceptions.CantGetSubAppNavigationStructureException;

import java.util.UUID;

/**
 * Created by natalia on 28/07/15.
 */
public class SubAppNavigationStructureNetworkService implements SubAppNavigationStructure {

    private UUID subAppNavigationStructureId;

    /**
     * Constructor
     */

    public SubAppNavigationStructureNetworkService(UUID subAppNavigationStructureId) {
        this.subAppNavigationStructureId = subAppNavigationStructureId;
    }


    /**
     * @return
     */
    @Override
    public UUID getNavigationStructureId() {
        return this.subAppNavigationStructureId;
    }

    /**
     * @return
     * @throws CantGetSubAppNavigationStructureException
     */
    @Override
    public String getSubAppNavigationStructure() throws CantGetSubAppNavigationStructureException {
        return "Method: getSubAppNavigationStructure - NO TIENE valor ASIGNADO para RETURN";
    }
}
