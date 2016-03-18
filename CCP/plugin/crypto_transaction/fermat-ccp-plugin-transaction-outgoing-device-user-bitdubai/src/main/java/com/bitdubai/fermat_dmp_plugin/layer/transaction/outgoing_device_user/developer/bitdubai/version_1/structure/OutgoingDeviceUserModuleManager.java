package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_device_user.interfaces.OutgoingDeviceUser;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.OutgoingExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Joaquin Carrasquero on 18/03/16.
 */
public class OutgoingDeviceUserModuleManager implements OutgoingDeviceUser{




    private final BitcoinLossProtectedWalletManager bitcoinLossWalletManager;
    private final BitcoinWalletManager bitcoinWalletManager;
    private final ErrorManager errorManager;


    public OutgoingDeviceUserModuleManager(final BitcoinLossProtectedWalletManager bitcoinLossWalletManager,
                                           final BitcoinWalletManager bitcoinWalletManager,
                                           final ErrorManager errorManager) {
        this.bitcoinLossWalletManager = bitcoinLossWalletManager;
        this.bitcoinWalletManager = bitcoinWalletManager;
        this.errorManager = errorManager;
    }

    @Override
    public void sendToWallet(UUID trxId, String txHash, long cryptoAmount, String notes, Actors actortype, ReferenceWallet reference_wallet_sending, ReferenceWallet reference_wallet_receiving, String wallet_public_key_sending, String wallet_public_key_receiving, BlockchainNetworkType blockchainNetworkType) {

    }
}
