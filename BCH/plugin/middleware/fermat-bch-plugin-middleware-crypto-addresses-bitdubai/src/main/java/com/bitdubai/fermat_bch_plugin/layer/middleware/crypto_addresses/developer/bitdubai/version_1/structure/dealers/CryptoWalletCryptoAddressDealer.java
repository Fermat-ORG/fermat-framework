package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure.dealers;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantGenerateAndRegisterCryptoAddressException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressesNewException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.DefaultWalletNotFoundException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.interfaces.CryptoAddressDealer;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.utils.CryptoVaultSelector;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.utils.WalletManagerSelector;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantAcceptAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantDenyAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;

/**
 * The class <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure.address_generators.CryptoWalletCryptoAddressDealer</code>
 * manages all the crypto addresses news for the .
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 */
public final class CryptoWalletCryptoAddressDealer extends CryptoAddressDealer {

    private final CryptoAddressesManager   cryptoAddressesManager  ;

    public CryptoWalletCryptoAddressDealer(final CryptoAddressesManager   cryptoAddressesManager  ,
                                           final CryptoAddressBookManager cryptoAddressBookManager,
                                           final CryptoVaultSelector      cryptoVaultSelector     ,
                                           final WalletManagerSelector    walletManagerSelector   ) {

        super(cryptoAddressBookManager, cryptoVaultSelector, walletManagerSelector);

        this.cryptoAddressesManager   = cryptoAddressesManager  ;
    }

    @Override
    public void handleCryptoAddressesNew(final CryptoAddressRequest request) throws CantHandleCryptoAddressesNewException {

        try {

            try {

                Platforms platform  = Platforms.CRYPTO_CURRENCY_PLATFORM;
                VaultType vaultType = VaultType.CRYPTO_CURRENCY_VAULT;

                CryptoAddress cryptoAddress = this.generateAndRegisterCryptoAddress(
                        platform,
                        vaultType,
                        request
                );

                cryptoAddressesManager.acceptAddressExchangeRequest(
                        request.getRequestId(),
                        cryptoAddress
                );

            } catch(DefaultWalletNotFoundException z) {

                cryptoAddressesManager.denyAddressExchangeRequest(request.getRequestId());
            }

        } catch(PendingRequestNotFoundException           |
                CantAcceptAddressExchangeRequestException |
                CantDenyAddressExchangeRequestException   e) {

            throw new CantHandleCryptoAddressesNewException(e, "", "There was an error with the network service.");
        } catch(CantGenerateAndRegisterCryptoAddressException e) {

            throw new CantHandleCryptoAddressesNewException(e, "", "There was an error trying to generate the crypto address.");
        }
    }
}
