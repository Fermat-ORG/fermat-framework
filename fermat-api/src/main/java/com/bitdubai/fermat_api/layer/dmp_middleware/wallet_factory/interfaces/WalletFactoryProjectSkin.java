package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetObjectStructureFromXmlException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetObjectStructureXmlException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectResourcesException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ResourceAlreadyExistsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 7/15/15.
 */
public interface WalletFactoryProjectSkin {

    // TODO DESIGNER?

    UUID getId();

    // name of skin, first skin is default
    String getName();

    // hash of the skin, calculated each time you do a change in a resource
    String getHash();

    // get all resources from a skin
    List<WalletFactoryProjectResource> getResources() throws CantGetWalletFactoryProjectResourcesException;

    // project proposal to which it belongs
    WalletFactoryProjectProposal getWalletFactoryProjectProposal();

    // get all resources of a skin by type
    List<WalletFactoryProjectResource> getAllResourcesByResourceType(ResourceType resourceType);

    // get a specific resource
    WalletFactoryProjectResource getResource(String name, ResourceType resourceType) throws CantGetWalletFactoryProjectResourceException, ResourceNotFoundException;

    // add a resource of the skin
    void addResource(String name, byte[] resource, ResourceType resourceType) throws CantAddWalletFactoryProjectResourceException, ResourceAlreadyExistsException;

    // update a resource of the skin
    void updateResource(String name, byte[] resource, ResourceType resourceType) throws CantUpdateWalletFactoryProjectResourceException;

    // delete a resource of the skin
    void deleteResource(String name, byte[] resource, ResourceType resourceType) throws CantDeleteWalletFactoryProjectResourceException;

    String getResourceTypePath(ResourceType resourceType);

    String getSkinXml(WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantGetObjectStructureXmlException;

    WalletFactoryProjectSkin getSkinFromXml(String stringXml) throws CantGetObjectStructureFromXmlException;
}
