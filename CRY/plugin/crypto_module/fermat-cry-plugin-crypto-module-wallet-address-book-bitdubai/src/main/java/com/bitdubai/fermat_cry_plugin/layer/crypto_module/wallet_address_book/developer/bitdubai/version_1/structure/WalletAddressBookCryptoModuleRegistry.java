package com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantRegisterWalletAddressBookException;
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
        walletCryptoAddressBookDao = new WalletAddressBookCryptoModuleDao(pluginDatabaseSystem, pluginId);
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
            throw exception;
        } catch (Exception exception){
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, FermatException.wrapException(exception));
        }
    }

    @Override
    public List<WalletAddressBookRecord> getAllWalletCryptoAddressBookByWalletPublicKey(String walletPublicKey) throws CantGetWalletAddressBookException, WalletAddressBookNotFoundException {
        try {
            return walletCryptoAddressBookDao.getAllWalletAddressBookModuleByWalletPublicKey(walletPublicKey);
        } catch (CantGetWalletAddressBookException|WalletAddressBookNotFoundException exception) {
            throw exception;
        } catch (Exception exception){
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, FermatException.wrapException(exception));
        }
    }

    @Override
    public void registerWalletCryptoAddressBook(CryptoAddress cryptoAddress, ReferenceWallet referenceWallet, String walletPublicKey) throws CantRegisterWalletAddressBookException {
        try {
            walletCryptoAddressBookDao.registerWalletAddressBookModule(cryptoAddress, referenceWallet, walletPublicKey);
        } catch (CantRegisterWalletAddressBookException exception) {
            throw exception;
        } catch (Exception exception){
            throw new CantRegisterWalletAddressBookException(CantRegisterWalletAddressBookException.DEFAULT_MESSAGE, FermatException.wrapException(exception));
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
