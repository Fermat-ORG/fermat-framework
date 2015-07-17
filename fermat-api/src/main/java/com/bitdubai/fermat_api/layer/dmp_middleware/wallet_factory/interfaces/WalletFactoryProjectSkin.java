package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectResourcesException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectResourceException;

import java.util.List;

/**
 * Created by ciencias on 7/15/15.
 */
public interface WalletFactoryProjectSkin {

    // name of skin, first skin is default
    String getName();

    // hash of the skin, calculated each time you do a change in a resource
    String getHash();

    // project proposal to which it belongs
    WalletFactoryProjectProposal getWalletFactoryProjectProposal();

    // get all resources from a skin
    List<WalletFactoryProjectResource> getAllResources() throws CantGetWalletFactoryProjectResourcesException;

    // get all resources of a skin by type
    List<WalletFactoryProjectResource> getAllResourcesByResourceType(ResourceType resourceType) throws CantGetWalletFactoryProjectResourcesException;

    // get a specific resource
    WalletFactoryProjectResource getResource(String name, ResourceType resourceType) throws CantGetWalletFactoryProjectResourceException;

    // update a resource of the skin
    void updateResource(String name, byte[] resource, ResourceType resourceType) throws CantUpdateWalletFactoryProjectResourceException;

}
