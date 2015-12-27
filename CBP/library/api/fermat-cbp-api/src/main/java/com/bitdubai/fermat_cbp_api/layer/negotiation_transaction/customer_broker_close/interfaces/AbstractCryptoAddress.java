package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGenerateAndRegisterCryptoAddressException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGenerateCryptoAddressException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGetDefaultWalletException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGetDefaultWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGetNewCryptoAddressExceptions;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantHandleCryptoAddressesNewException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantRegisterCryptoAddressBookException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils.CryptoVaultSelector;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils.WalletManagerSelector;

/**
 * Created by Yordin Alayn on 27.12.15.
 */
public abstract class AbstractCryptoAddress {

    private final CryptoAddressBookManager  cryptoAddressBookManager;
    private final CryptoVaultSelector       cryptoVaultSelector;
    private final WalletManagerSelector     walletManagerSelector;

    public AbstractCryptoAddress (
        final CryptoAddressBookManager  cryptoAddressBookManager,
        final CryptoVaultSelector       cryptoVaultSelector     ,
        final WalletManagerSelector     walletManagerSelector
    ){
        this.cryptoAddressBookManager   = cryptoAddressBookManager;
        this.cryptoVaultSelector        = cryptoVaultSelector;
        this.walletManagerSelector      = walletManagerSelector;
    }

    /*GET THE CRYPTO ADDRESS*/
    protected final CryptoAddress getAddress(
        final VaultType vaultType     ,
        final CryptoCurrency cryptoCurrency)
    throws CantGenerateCryptoAddressException, CantGetNewCryptoAddressExceptions{

        return null;
    }

    /*GET THE DEFAULT WALLET FOR CRPYTO ADDRESS*/
    protected final InstalledWallet getDefaultWallet(
        final Actors actorType     ,
        final CryptoCurrency        cryptoCurrency,
        final BlockchainNetworkType networkType   ,
        final Platforms platform)
    throws CantGetDefaultWalletException, CantGetDefaultWalletNotFoundException{

        return null;
    }

    /*REGISTER THE CRYPTO ADDRESS*/
    protected final void registerCryptoAddress(
        final CryptoAddress        cryptoAddress  ,
        /*final CryptoAddressRequest request        ,*/
        final InstalledWallet      installedWallet,
        final VaultType            vaultType
    ) throws CantRegisterCryptoAddressBookException{

    }

    /*GENERATE AND REGISTER THE CRYPTO ADDRESS*/
    protected final CryptoAddress generateAndRegisterCryptoAddress(
        final Platforms            platform ,
        final VaultType            vaultType
        /*final CryptoAddressRequest request*/
    ) throws CantGenerateAndRegisterCryptoAddressException, CantGetDefaultWalletNotFoundException{
        return null;
    }


    public abstract void handleCryptoAddressesNew(/*final CryptoAddressRequest cryptoAddressRequest*/)throws CantHandleCryptoAddressesNewException;
}
