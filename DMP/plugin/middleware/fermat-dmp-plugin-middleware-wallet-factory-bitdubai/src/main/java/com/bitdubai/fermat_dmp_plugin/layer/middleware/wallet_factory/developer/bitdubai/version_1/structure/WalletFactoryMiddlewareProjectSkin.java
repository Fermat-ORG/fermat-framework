package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectResourcesException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectResource;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectSkin</code>
 * implementation of WalletFactoryProjectResource.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewareProjectSkin implements WalletFactoryProjectSkin {

    String name;

    String hash;

    WalletFactoryProjectProposal walletFactoryProjectProposal;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public WalletFactoryProjectProposal getWalletFactoryProjectProposal() {
        return walletFactoryProjectProposal;
    }

    @Override
    public List<WalletFactoryProjectResource> getAllResources() throws CantGetWalletFactoryProjectResourcesException {
        // TODO VER COMO A PARTIR DE UN XML GENERAR LA ESTRUCTURA DE CLASES
        return null;
    }

    @Override
    public List<WalletFactoryProjectResource> getAllResourcesByResourceType(ResourceType resourceType) throws CantGetWalletFactoryProjectResourcesException {
        // TODO VER COMO A PARTIR DE UN XML GENERAR LA ESTRUCTURA DE CLASES
        return null;
    }

    @Override
    public WalletFactoryProjectResource getResource(String name, ResourceType resourceType) throws CantGetWalletFactoryProjectResourceException {
        // TODO VER COMO A PARTIR DE UN XML GENERAR LA ESTRUCTURA DE CLASES
        return null;
    }

    @Override
    public void addResource(String name, byte[] resource, ResourceType resourceType) throws CantAddWalletFactoryProjectResourceException {

    }

    @Override
    public void updateResource(String name, byte[] resource, ResourceType resourceType) throws CantUpdateWalletFactoryProjectResourceException {

    }

    @Override
    public void deleteResource(String name, byte[] resource, ResourceType resourceType) throws CantDeleteWalletFactoryProjectResourceException {

    }
}
