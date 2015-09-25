package com.bitdubai.fermat_api.layer.ccp_network_service.wallet_resources;

import com.bitdubai.fermat_api.layer.ccp_network_service.wallet_resources.exceptions.CantGetWalletNavigationStructureException;

import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface WalletNavigationStructure {

    public UUID getNavigationStructureId();

    public String getWalletNavigationStructure() throws CantGetWalletNavigationStructureException;
}
