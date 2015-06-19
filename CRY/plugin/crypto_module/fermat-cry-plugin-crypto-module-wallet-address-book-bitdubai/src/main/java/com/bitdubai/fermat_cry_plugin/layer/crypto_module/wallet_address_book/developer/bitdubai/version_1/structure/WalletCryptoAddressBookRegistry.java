package com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookRecord;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookRegistry;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletCryptoAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantRegisterWalletCryptoAddressBookException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.exceptions.CantInitializeWalletCryptoAddressBookException;

import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure.WalletCryptoAddressBookRegistry</code>
 * haves all consumable methods from the plugin Wallet Crypto Address Book
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/06/15.
 * @version 1.0
 */
public class WalletCryptoAddressBookRegistry implements DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity, WalletAddressBookRegistry {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentityInterface member variables.
     */
    private UUID pluginId;

    /**
     * WalletAddressBookRegistry Interface member variables.
     */
    private WalletCryptoAddressBookDao walletCryptoAddressBookDao;

    public void initialize() throws CantInitializeWalletCryptoAddressBookException {
        /**
         * I will try to create and initialize a new DAO
         */
        walletCryptoAddressBookDao = new WalletCryptoAddressBookDao(errorManager, pluginDatabaseSystem, pluginId);
        walletCryptoAddressBookDao.initialize();

    }

    /**
     * Address Book Manager implementation.
     */

    @Override

    public WalletAddressBookRecord getWalletCryptoAddressBookByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetWalletCryptoAddressBookException {
        return walletCryptoAddressBookDao.getWalletCryptoAddressBookByCryptoAddress(cryptoAddress);
    }

    @Override
    public List<WalletAddressBookRecord> getAllWalletCryptoAddressBookByWalletId(UUID walletId) throws CantGetWalletCryptoAddressBookException {
        return walletCryptoAddressBookDao.getAllWalletCryptoAddressBookByActorId(walletId);
    }


    @Override
    public void registerWalletCryptoAddressBook(CryptoAddress cryptoAddress, PlatformWalletType platformWalletType, UUID walletId) throws CantRegisterWalletCryptoAddressBookException {

        /**
         * Here I create the Wallet Crypto Address book record for new address.
         */
        walletCryptoAddressBookDao.registerWalletCryptoAddressBook(cryptoAddress, platformWalletType, walletId);

    }


    /**
     *DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
