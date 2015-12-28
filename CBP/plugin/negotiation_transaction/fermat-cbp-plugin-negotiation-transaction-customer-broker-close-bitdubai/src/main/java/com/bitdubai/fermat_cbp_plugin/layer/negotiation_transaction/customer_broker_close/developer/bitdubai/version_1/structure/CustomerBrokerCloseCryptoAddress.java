package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGenerateAndRegisterCryptoAddressException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantCryptoAddressesNewException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.AbstractCryptoAddress;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils.CryptoVaultSelector;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils.WalletManagerSelector;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;

/**
 * Created by Yordin Alayn on 27.12.15.
 */
public class CustomerBrokerCloseCryptoAddress extends AbstractCryptoAddress{

    public CustomerBrokerCloseCryptoAddress(
        final CryptoAddressBookManager cryptoAddressBookManager,
        final CryptoVaultSelector cryptoVaultSelector,
        final WalletManagerSelector walletManagerSelector
    ){
        super(cryptoAddressBookManager, cryptoVaultSelector, walletManagerSelector);
    }

    @Override
    public CryptoAddress CryptoAddressesNew(final CryptoAddressRequest request) throws CantCryptoAddressesNewException {

        try {
            CryptoAddress cryptoAddress = null;

            Platforms platform = Platforms.CRYPTO_BROKER_PLATFORM;
            VaultType vaultType = VaultType.CRYPTO_CURRENCY_VAULT;

            cryptoAddress = this.generateAndRegisterCryptoAddress(
                    platform,
                    vaultType,
                    request
            );

            return cryptoAddress;

        } catch (CantGenerateAndRegisterCryptoAddressException e){
            throw new CantCryptoAddressesNewException(e, "", "There was an error trying to generate the crypto address.");
        }

    }
}
