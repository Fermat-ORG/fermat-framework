package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_navigation_structure.interfaces;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_middleware.wallet_navigation_structure.interfaces.DealsWithWalletNavigationStructure</code>
 * indicates that the plugin needs the functionality of a WalletNavigationStructureManager
 * <p/>
 *
 * Created by natalia on 07/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithWalletNavigationStructure  {

      /**
     * Method that inject a instance of the WalletNavigationStructureManager
     *
     * @param walletNavigationStructureManager
     */
    void setWalletPublisherManager(WalletNavigationStructureManager walletNavigationStructureManager);
}
