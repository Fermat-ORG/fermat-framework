package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CryptoAddressDealerNotSupportedException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.interfaces.CryptoAddressDealer;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure.dealers.CryptoAssetCryptoAddressDealer;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure.dealers.CryptoWalletCryptoAddressDealer;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.utils.CryptoVaultSelector;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.utils.WalletManagerSelector;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.CryptoAddressDealers;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;

/**
 * The class <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressesMiddlewareDealersFactory</code>
 * is a factory of Crypto Address dealers.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 */
public final class CryptoAddressesMiddlewareDealersFactory {

    private final CryptoAddressesManager   cryptoAddressesManager  ;
    private final CryptoAddressBookManager cryptoAddressBookManager;
    private final CryptoVaultSelector      cryptoVaultSelector     ;
    private final WalletManagerSelector    walletManagerSelector   ;

    public CryptoAddressesMiddlewareDealersFactory(final CryptoAddressesManager   cryptoAddressesManager  ,
                                                   final CryptoAddressBookManager cryptoAddressBookManager,
                                                   final CryptoVaultSelector      cryptoVaultSelector     ,
                                                   final WalletManagerSelector    walletManagerSelector   ) {

        this.cryptoAddressesManager   = cryptoAddressesManager  ;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.cryptoVaultSelector      = cryptoVaultSelector     ;
        this.walletManagerSelector    = walletManagerSelector   ;
    }

    public final CryptoAddressDealer getCryptoAddressDealer(final CryptoAddressDealers dealer) throws CryptoAddressDealerNotSupportedException {

        switch (dealer) {

            case CRYPTO_WALLET:
                return new CryptoWalletCryptoAddressDealer(
                        cryptoAddressesManager,
                        cryptoAddressBookManager,
                        cryptoVaultSelector,
                        walletManagerSelector
                );
            case DAP_ASSET:
                return new CryptoAssetCryptoAddressDealer(
                        cryptoAddressesManager,
                        cryptoAddressBookManager,
                        cryptoVaultSelector,
                        walletManagerSelector
                );
            default: throw new CryptoAddressDealerNotSupportedException(
                    "Dealer requested: "+dealer,
                    "Dealer not supported. Please add the Crypto Address Dealer to the switch."
            );
        }
    }
}
