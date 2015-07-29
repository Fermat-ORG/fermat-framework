package com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources;

import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.exceptions.CantGetSubAppNavigationStructureException;

import java.util.UUID;

/**
 * Created by natalia on 2015.07.28..
 */
public interface SubAppNavigationStructure {

    public UUID getNavigationStructureId();

    public String getSubAppNavigationStructure() throws CantGetSubAppNavigationStructureException;
}
