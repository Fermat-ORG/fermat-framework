package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectResource;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectResource;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectResourcesException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectResource;

import java.util.List;

/**
 * Created by ciencias on 7/15/15.
 */
public interface WalletFactoryProjectSkin {

    String getName();

    WalletFactoryProjectProposal getWalletFactoryProjectProposal();

    List<WalletFactoryProjectResource> getAllResources() throws CantGetWalletFactoryProjectResourcesException;

    List<WalletFactoryProjectResource> getAllResourcesByResourceType(ResourceType resourceType) throws CantGetWalletFactoryProjectResourcesException;

    WalletFactoryProjectResource getResource(String name, ResourceType resourceType) throws CantGetWalletFactoryProjectResourceException;

    void addResource(String name, byte[] resource, ResourceType resourceType) throws CantAddWalletFactoryProjectResource;

    void deleteResource(String name, byte[] resource, ResourceType resourceType) throws CantDeleteWalletFactoryProjectResource;

    void updateResource(String name, byte[] resource, ResourceType resourceType) throws CantUpdateWalletFactoryProjectResource;

}
