package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.CantGetWalletNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.CantGetWalletResourcesException;

import java.util.UUID;

/**
 *
 *  <p>The abstract class <code>com.bitdubai.fermat_api.layer.network_service.wallet_resources.WalletResourcesInstalationManager/code> is a interface
 *     that define the methods to retrieve wallets resource files.
 *
 *
 *  @author  Loui
 *  @version 1.0.0
 *  @since   18/02/15.
 * */
public interface WalletResourcesInstalationManager {

/*
    Esto venía de un prototipo/prueba de concepto
    // DEPRECATED - NO USAR - SE ANALIZAR SI SE ELIMINARÁ
    */
    public void checkResources(String repoName) throws CantCheckResourcesException;
/*
    // DEPRECATED - NO USAR - SE DEBE LIMPIAR
    public byte[] getImageResource(String imageName) throws CantGetResourcesException;

    // DEPRECATED - NO USAR - SE DEBE LIMPIAR
    public String getLayoutResource(String layoutName) throws CantGetResourcesException;

    // DEPRECATED - NO USAR - SE DEBE LIMPIAR
    public void setwalletType(Wallets type);
*/

    /**
     * This method will give us the resources associated to the resourcesId
     *
     * @return An object that encapsulates the resources asked.
     */
    public WalletResources getWalletResources(String resourceName,String publicKey,Version version) throws CantGetWalletResourcesException;

    public WalletNavigationStructure getWalletNavigationStructure(UUID walletNavigationStructureId) throws CantGetWalletNavigationStructureException;


    public void installResources(String walletCategory, String walletType,String developer,String screenSize,String screenDensity,String skinName,String languageName);



}
