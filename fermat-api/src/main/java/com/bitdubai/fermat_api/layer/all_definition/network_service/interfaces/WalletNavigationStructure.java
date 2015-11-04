package com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.network_service.exceptions.CantGetWalletNavigationStructureException;

import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface WalletNavigationStructure {

    public UUID getNavigationStructureId();

    public String getWalletNavigationStructure() throws CantGetWalletNavigationStructureException;
}
