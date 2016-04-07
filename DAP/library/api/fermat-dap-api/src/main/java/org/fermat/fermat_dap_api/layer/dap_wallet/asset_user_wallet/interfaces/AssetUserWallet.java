package org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;

import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantExecuteLockOperationException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantStoreMemoException;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 05/10/15.
 */
public interface AssetUserWallet {

    //TODO:Documentar y manejo de excepciones

    AssetUserWalletBalance getBalance() throws CantGetTransactionsException;

    List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction> getAllTransactions(String assetPublicKey) throws CantGetTransactionsException;

    List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction> getAllAvailableTransactions(String assetPublicKey) throws CantGetTransactionsException;

    List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction> getTransactionsForDisplay(String assetPublicKey) throws CantGetTransactionsException;

    List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction> getTransactions(BalanceType balanceType,
                                                     TransactionType transactionType,
                                                     String assetPublicKey) throws CantGetTransactionsException;

    List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction> getTransactions(BalanceType balanceType,
                                                     TransactionType transactionType,
                                                     int max,
                                                     int offset, String assetPublicKey) throws CantGetTransactionsException;

    List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction> getTransactionsByActor(String actorPublicKey,
                                                            BalanceType balanceType,
                                                            int max,
                                                            int offset) throws CantGetTransactionsException;

    List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction> gettLastActorTransactionsByTransactionType(BalanceType balanceType,
                                                                                TransactionType transactionType,
                                                                                int max,
                                                                                int offset) throws CantGetTransactionsException;

    void lockFunds(DigitalAssetMetadata metadata) throws RecordsNotFoundException, CantExecuteLockOperationException;

    void unlockFunds(DigitalAssetMetadata metadata) throws RecordsNotFoundException, CantExecuteLockOperationException;

    void setTransactionDescription(UUID transactionID,
                                   String description) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantFindTransactionException, CantStoreMemoException;

    org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransactionSummary getActorTransactionSummary(String actorPublicKey,
                                                                 BalanceType balanceType) throws CantGetActorTransactionSummaryException;

    DigitalAssetMetadata getDigitalAssetMetadata(String transactionHash) throws CantGetDigitalAssetFromLocalStorageException;

    DigitalAsset getDigitalAsset(String assetPublicKey) throws CantGetDigitalAssetFromLocalStorageException;
}
