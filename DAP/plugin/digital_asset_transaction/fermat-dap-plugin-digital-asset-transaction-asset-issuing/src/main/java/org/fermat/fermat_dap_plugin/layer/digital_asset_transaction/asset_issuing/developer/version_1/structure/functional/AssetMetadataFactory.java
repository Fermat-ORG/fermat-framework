package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletBalance;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletWallet;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.IntraActorCryptoTransactionManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.events.AssetIssuingMonitorAgent;

import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/03/16.
 */
public class AssetMetadataFactory implements Callable<Boolean> {
    //VARIABLE DECLARATION
    private final IssuingRecord issuingRecord;
    private final AssetVaultManager assetVaultManager;
    private final ActorAssetIssuer actorAssetIssuer;
    private final IntraWalletUserIdentity intraActor;
    private final org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO dao;
    private final IntraActorCryptoTransactionManager manager;
    private final CryptoAddressBookManager cryptoAddressBookManager;
    private final CryptoWalletManager cryptoWalletManager;

    //CONSTRUCTORS

    public AssetMetadataFactory(IssuingRecord issuingRecord, AssetVaultManager assetVaultManager, ActorAssetIssuer actorAssetIssuer, IntraWalletUserIdentity intraActor, org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO dao, IntraActorCryptoTransactionManager manager, CryptoAddressBookManager cryptoAddressBookManager, CryptoWalletManager cryptoWalletManager) {
        this.issuingRecord = issuingRecord;
        this.assetVaultManager = assetVaultManager;
        this.actorAssetIssuer = actorAssetIssuer;
        this.intraActor = intraActor;
        this.dao = dao;
        this.manager = manager;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.cryptoWalletManager = cryptoWalletManager;
    }


    //PUBLIC METHODS

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Boolean call() throws Exception {
        if (assetVaultManager.getAvailableKeyCount() <= AssetIssuingMonitorAgent.MINIMUN_KEY_COUNT) {
            dao.unProcessingAsset(issuingRecord.getAsset().getPublicKey());
            return Boolean.FALSE;
        }
        CryptoWalletWallet wallet = cryptoWalletManager.loadWallet(issuingRecord.getBtcWalletPk());
        CryptoWalletBalance balance = wallet.getBalance(BalanceType.AVAILABLE);
        if (issuingRecord.getAsset().getGenesisAmount() > balance.getBalance(issuingRecord.getNetworkType())) {
            dao.updateIssuingStatus(issuingRecord.getAsset().getPublicKey(), IssuingStatus.INSUFFICIENT_FONDS);
            dao.unProcessingAsset(issuingRecord.getAsset().getPublicKey());
            return Boolean.FALSE;
        }
        CryptoAddress cryptoAddress = assetVaultManager.getNewAssetVaultCryptoAddress(issuingRecord.getNetworkType());
        //We'll need to copy the asset so the other threads won't modify its content.
        //Trust me, its necessary.
        DigitalAsset asset = DigitalAsset.copyAsset(issuingRecord.getAsset());
        asset.setGenesisAddress(cryptoAddress);
        DigitalAssetMetadata metadata = new DigitalAssetMetadata(asset);
        metadata.setNetworkType(issuingRecord.getNetworkType());
        metadata.setLastOwner(actorAssetIssuer);
        registerOnCryptoAddressBook(cryptoAddress);
        dao.newMetadata(metadata, sendCrypto(metadata));
        return Boolean.TRUE;
    }

    //PRIVATE METHODS
    private void registerOnCryptoAddressBook(CryptoAddress cryptoAddress) throws CantRegisterCryptoAddressBookRecordException {
        cryptoAddressBookManager.registerCryptoAddress(cryptoAddress,
                intraActor.getPublicKey(),
                Actors.INTRA_USER,
                actorAssetIssuer.getActorPublicKey(),
                Actors.DAP_ASSET_ISSUER,
                Platforms.DIGITAL_ASSET_PLATFORM,
                VaultType.CRYPTO_ASSET_VAULT,
                CryptoCurrencyVault.BITCOIN_VAULT.getCode(),
                issuingRecord.getBtcWalletPk(),
                ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);
    }

    private UUID sendCrypto(DigitalAssetMetadata metadata) throws OutgoingIntraActorInsufficientFundsException, OutgoingIntraActorCantSendFundsExceptions {
        CryptoAddress cryptoAddress = metadata.getDigitalAsset().getGenesisAddress();
        DigitalAsset asset = metadata.getDigitalAsset();
        String assetHash = metadata.getDigitalAssetHash();
        return manager.sendCrypto(issuingRecord.getBtcWalletPk(),
                cryptoAddress,
                asset.getGenesisAmount(),
                assetHash,
                asset.getDescription(),
                intraActor.getPublicKey(),
                actorAssetIssuer.getActorPublicKey(),
                Actors.INTRA_USER,
                Actors.DAP_ASSET_ISSUER,
                ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET,
                true,
                issuingRecord.getNetworkType(),
                CryptoCurrency.BITCOIN,
                0,
                FeeOrigin.SUBSTRACT_FEE_FROM_AMOUNT);
    }
    //GETTER AND SETTERS

    //INNER CLASSES
}
