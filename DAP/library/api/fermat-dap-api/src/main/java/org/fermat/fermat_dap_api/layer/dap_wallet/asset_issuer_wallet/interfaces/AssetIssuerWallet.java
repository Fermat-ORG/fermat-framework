package org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetCurrentStatus;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetAssetStatisticException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantStoreMemoException;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 07/09/15.
 */
public interface AssetIssuerWallet {

    //TODO:Documentar y manejo de excepciones

    AssetIssuerWalletBalance getBalance() throws CantGetTransactionsException;

    List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction> getTransactionsAll(org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType,
                                                          org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType transactionType,
                                                          String assetPublicKey) throws CantGetTransactionsException;

    List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction> getTransactions(org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType,
                                                       org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType transactionType,
                                                       int max,
                                                       int offset, String assetPublicKey) throws CantGetTransactionsException;

    List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction> getAvailableTransactions(String assetPublicKey) throws CantGetTransactionsException;

    List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction> getTransactionsByActor(String actorPublicKey,
                                                              org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType,
                                                              int max,
                                                              int offset) throws CantGetTransactionsException;

    List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction> gettLastActorTransactionsByTransactionType(org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType,
                                                                                  org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType transactionType,
                                                                                  int max,
                                                                                  int offset) throws CantGetTransactionsException;

    void setTransactionDescription(UUID transactionID,
                                   String description) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantFindTransactionException, CantStoreMemoException;

    AssetIssuerWalletTransactionSummary getActorTransactionSummary(String actorPublicKey,
                                                                   org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetActorTransactionSummaryException;

    List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction> getTransactionsAssetAll(String assetPublicKey) throws CantGetTransactionsException;

    List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction> getTransactionsForDisplay(String assetPublicKey) throws CantGetTransactionsException;

    DigitalAssetMetadata getDigitalAssetMetadata(String transactionHash) throws CantGetDigitalAssetFromLocalStorageException;

    org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset getAssetByPublicKey(String assetPublicKey);

    String getUserDeliveredToPublicKey(UUID transactionId) throws RecordsNotFoundException, CantGetAssetStatisticException;

    List<DigitalAssetMetadata> getAllUnusedAssets() throws CantGetDigitalAssetFromLocalStorageException;

    //ASSET STATISTIC METHODS

    void createdNewAsset(DigitalAssetMetadata assetMetadata) throws org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantSaveStatisticException;

    void newMovement(DAPActor actorFrom, DAPActor actorTo, String assetPk, org.fermat.fermat_dap_api.layer.all_definition.enums.AssetMovementType type) throws org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantSaveStatisticException;

    void assetDistributed(UUID transactionId, String actorAssetUserPublicKey) throws RecordsNotFoundException, CantGetAssetStatisticException;

    void assetRedeemed(UUID transactionId, String userPublicKey, String redeemPointPublicKey) throws RecordsNotFoundException, CantGetAssetStatisticException;

    void assetAppropriated(UUID transactionId, String userPublicKey) throws RecordsNotFoundException, CantGetAssetStatisticException;

    List<AssetStatistic> getAllStatisticForAllAssets() throws CantGetAssetStatisticException;

    List<AssetStatistic> getStatisticForAllAssetsByStatus(AssetCurrentStatus status) throws CantGetAssetStatisticException;

    List<AssetStatistic> getStatisticForGivenAssetByStatus(String assetName, AssetCurrentStatus status) throws CantGetAssetStatisticException;

    List<AssetStatistic> getAllStatisticForGivenAsset(String assetName) throws CantGetAssetStatisticException;

    int getUnusedAmountForAssetByStatus(AssetCurrentStatus status, String assetName) throws CantGetAssetStatisticException;
}
