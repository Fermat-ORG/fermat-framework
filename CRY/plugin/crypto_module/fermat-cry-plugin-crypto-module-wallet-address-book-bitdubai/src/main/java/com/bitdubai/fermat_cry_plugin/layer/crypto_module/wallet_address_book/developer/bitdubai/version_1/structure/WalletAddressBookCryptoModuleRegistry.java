package com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantRegisterWalletAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.WalletAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.WalletAddressBookNotFoundException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookRecord;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookRegistry;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletAddressBookException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.exceptions.CantInitializeWalletAddressBookCryptoModuleException;

import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure.WalletAddressBookCryptoModuleRegistry</code>
 * haves all consumable methods from the plugin Wallet Crypto Address Book
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/06/15.
 * @version 1.0
 */
public class WalletAddressBookCryptoModuleRegistry implements DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity, WalletAddressBookRegistry {

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
    private WalletAddressBookCryptoModuleDao walletCryptoAddressBookDao;

    public void initialize() throws CantInitializeWalletAddressBookCryptoModuleException {
        /**
         * I will try to create and initialize a new DAO
         */
        walletCryptoAddressBookDao = new WalletAddressBookCryptoModuleDao(errorManager, pluginDatabaseSystem, pluginId);
        walletCryptoAddressBookDao.initialize();
    }

    /**
     * Wallet Address Book Registry implementation.
     */
    @Override
    public WalletAddressBookRecord getWalletCryptoAddressBookByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetWalletAddressBookException, WalletAddressBookNotFoundException {
        try {
            return walletCryptoAddressBookDao.getWalletAddressBookModuleByCryptoAddress(cryptoAddress);
        } catch (CantGetWalletAddressBookException|WalletAddressBookNotFoundException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }
    }

    @Override
    public List<WalletAddressBookRecord> getAllWalletCryptoAddressBookByWalletId(UUID walletId) throws CantGetWalletAddressBookException, WalletAddressBookNotFoundException {
        try {
            return walletCryptoAddressBookDao.getAllWalletAddressBookModuleByWalletId(walletId);
        } catch (CantGetWalletAddressBookException|WalletAddressBookNotFoundException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }
    }

    @Override
    public void registerWalletCryptoAddressBook(CryptoAddress cryptoAddress, PlatformWalletType platformWalletType, UUID walletId) throws CantRegisterWalletAddressBookException {

        /**
         * Here I create the Wallet Crypto Address book record for new address.
         */
        try {
            walletCryptoAddressBookDao.registerWalletAddressBookModule(cryptoAddress, platformWalletType, walletId);
        } catch (CantRegisterWalletAddressBookException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }
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
