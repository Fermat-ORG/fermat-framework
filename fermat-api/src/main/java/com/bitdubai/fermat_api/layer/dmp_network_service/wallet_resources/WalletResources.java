package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources;

import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;

import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface WalletResources {

    public UUID getResourcesId();

    public byte[] getImageResource(String imageName) throws CantGetResourcesException;

    public String getLayoutResource(String layoutName) throws CantGetResourcesException;
}
