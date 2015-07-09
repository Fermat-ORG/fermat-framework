package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources;

import com.bitdubai.fermat_api.layer.dmp_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Wallets;

import java.util.UUID;

/**
 *
 *  <p>The abstract class <code>com.bitdubai.fermat_api.layer.network_service.wallet_resources.WalletResourcesManager/code> is a interface
 *     that define the methods to retrieve wallets resource files.
 *
 *
 *  @author  Loui
 *  @version 1.0.0
 *  @since   18/02/15.
 * */
public interface  WalletResourcesManager {

/*
    Esto venía de un prototipo/prueba de concepto
    // DEPRECATED - NO USAR - SE ANALIZAR SI SE ELIMINARÁ
    */
    public void checkResources() throws CantCheckResourcesException;
/*
    // DEPRECATED - NO USAR - SE DEBE LIMPIAR
    public byte[] getImageResource(String imageName) throws CantGetResourcesException;

    // DEPRECATED - NO USAR - SE DEBE LIMPIAR
    public String getLayoutResource(String layoutName) throws CantGetResourcesException;

    // DEPRECATED - NO USAR - SE DEBE LIMPIAR
    public void setwalletType(Wallets type);
*/
    public WalletResources getWalletResources(UUID resourcesId);

    public WalletNavigationStructure getWalletNavigationStructure(UUID walletNavigationStructureId);

    public void sevaaNavigationStructure(UUID walletNavigationStructureId, WalletNavigationStructure walletNavigationStructure);

    public void sevaaWalletResources(UUID resourcesId, WalletResources walletResources);

}
