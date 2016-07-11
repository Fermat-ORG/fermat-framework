package com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletTransactionRecord;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletTransactionRecord;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.transfer_intra_wallet_users.exceptions.TransferIntraWalletUsersNotEnoughFundsException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.transfer_intra_wallet_users.interfaces.TransferIntraWalletUsers;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.database.TransferIntraWalletUsersDao;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.enums.TransactionState;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.transfer_intra_wallet_users.exceptions.CantSendTransactionException;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.exceptions.CantReceiveWalletTransactionException;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.utils.CryptoWalletTransactionWalletRecord;

import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.exceptions.TransferIntraWalletUsersCantInsertRecordException;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.utils.BitcoinLossProtectedWalletTransactionWalletRecord;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.utils.TransferIntraWalletUsersWrapper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Joaquin Carrasquero on 18/03/16.
 */
public class TransferIntraWalletUsersModuleManager implements TransferIntraWalletUsers {


    private final BitcoinLossProtectedWalletManager bitcoinLossWalletManager;
    private final CryptoWalletManager cryptoWalletManager;
    private final ErrorManager errorManager;
    private final TransferIntraWalletUsersDao dao;



    public TransferIntraWalletUsersModuleManager(final BitcoinLossProtectedWalletManager bitcoinLossWalletManager,
                                                 final CryptoWalletManager cryptoWalletManager,
                                                 final ErrorManager errorManager,
                                                 final TransferIntraWalletUsersDao dao
                                                ) {
        this.bitcoinLossWalletManager = bitcoinLossWalletManager;
        this.cryptoWalletManager = cryptoWalletManager;
        this.errorManager = errorManager;
        this.dao = dao;
    }


    //Acordate que la transaccion tiene que tener un tipo Intra Wallets y la transaccion que se guarda en la loss lleva el Exchange rate en cero
    //hay q ver si no puede completar la transaccion de avisar con una notificacion
    @Override
    public void sendToWallet(
            String txHash, //ESTE TX HASH PARA QUE SERIA?
            long cryptoAmount,
            String notes,
            Actors actortypeFrom,
            Actors actortypeTo,
            ReferenceWallet reference_wallet_sending,
            ReferenceWallet reference_wallet_receiving,
            String wallet_public_key_sending,
            String wallet_public_key_receiving,
            BlockchainNetworkType blockchainNetworkType,
            CryptoCurrency cryptoCurrency) throws CantSendTransactionException, TransferIntraWalletUsersNotEnoughFundsException {

        Long initialBalance = null;

        UUID id = UUID.randomUUID();

        try {

            //Register the new transaction
            dao.registerNewTransaction(id,
                    txHash,
                    cryptoAmount,
                    notes,
                    actortypeTo,
                    reference_wallet_sending,
                    reference_wallet_receiving,
                    wallet_public_key_sending,
                    wallet_public_key_receiving,
                    TransactionState.NEW,
                    blockchainNetworkType);

            TransferIntraWalletUsersWrapper transferIntraWalletUsersWrapper = dao.getTransaction(id);

            //DEBIT TO WALLET SENDING

            switch (reference_wallet_sending) {

                case BASIC_WALLET_BITCOIN_WALLET:

                    //Consult current balance of the sending wallet

                    CryptoWalletWallet cryptoWalletWallet = this.cryptoWalletManager.loadWallet(wallet_public_key_sending);
                    initialBalance = cryptoWalletWallet.getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);


                    //Checks the balance of the debiting wallet in order to decide if to continue or not.

                    if (initialBalance != null && initialBalance >= cryptoAmount) {


                        //Prepares the record to be used within transactions

                        //TODO: los actors de estas transacciones van a ser la wallets, hay que distinguir
                        // esto con el actor type para poder mostrar despues bien el que seria el contacto asociado
                        CryptoWalletTransactionRecord bitcoinWalletTransactionWalletRecord = buildBitcoinWalletRecord(id,
                                new CryptoAddress("", CryptoCurrency.BITCOIN),
                                null,
                                cryptoAmount,
                                new CryptoAddress("", CryptoCurrency.BITCOIN),
                                notes,
                                System.currentTimeMillis(),
                                "",
                                wallet_public_key_sending,
                                wallet_public_key_receiving,
                                actortypeTo,
                                actortypeFrom,
                                blockchainNetworkType,
                                cryptoCurrency);



                        cryptoWalletWallet.getBalance(BalanceType.BOOK).debit(bitcoinWalletTransactionWalletRecord);
                        cryptoWalletWallet.getBalance(BalanceType.AVAILABLE).debit(bitcoinWalletTransactionWalletRecord);

                        //Proceeds to credit in the destination wallet
                        try
                        {
                        receivedToWallet(id,
                                txHash,
                                cryptoAmount,
                                notes,
                                actortypeFrom,
                                actortypeTo,
                                reference_wallet_sending,
                                reference_wallet_receiving,
                                wallet_public_key_sending,
                                wallet_public_key_receiving,
                                blockchainNetworkType,
                                cryptoCurrency);
                        } catch (CantReceiveWalletTransactionException e){
                            //change transaction state to reversed and update balance to revert
                            cryptoWalletWallet.revertTransaction(bitcoinWalletTransactionWalletRecord, false);
                            dao.setToError(transferIntraWalletUsersWrapper);
                            //broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, wallet_public_key_sending,"TRANSACTIONWALLETREVERSE");

                            throw new CantSendTransactionException("I could not send the transaction", e, "TransferIntraWalletUsersModuleManager", "Recived Wallet process error");
                        }

                        //process oK
                        dao.setToCompleted(transferIntraWalletUsersWrapper);


                    } else {
                       // broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, wallet_public_key_sending,"TRANSACTIONWALLETREVERSE");
                        //change transaction state to error
                        dao.setToError(transferIntraWalletUsersWrapper);
                        //There are not enough funds to perform this transaction
                        throw new TransferIntraWalletUsersNotEnoughFundsException("There are not enough funds to perform this transaction", null, "", "NotEnoughFunds");

                    }

                    break;
                case BASIC_WALLET_DISCOUNT_WALLET:
                    break;
                case BASIC_WALLET_FIAT_WALLET:
                    break;
                case BASIC_WALLET_LOSS_PROTECTED_WALLET:

                    BitcoinLossProtectedWallet bitcoinLossProtectedWallet = this.bitcoinLossWalletManager.loadWallet(wallet_public_key_sending);
                    //consult current balance of the sending wallet
                    initialBalance = bitcoinLossProtectedWallet.getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);

                    //Checks the balance of the debiting wallet in order to decide if to continue or not.

                    if (initialBalance != null && initialBalance >= cryptoAmount) {

                        //Prepares the record to be used within transactions

                        BitcoinLossProtectedWalletTransactionRecord bitcoinLossProtectedWalletTransactionWalletRecord2 = buildLossWalletRecord(id,
                                new CryptoAddress("", CryptoCurrency.BITCOIN),
                                null,
                                cryptoAmount,
                                new CryptoAddress("", CryptoCurrency.BITCOIN),
                                notes,
                                System.currentTimeMillis(),
                                "",
                                "",
                                "",
                                actortypeTo,
                                actortypeFrom,
                                blockchainNetworkType,
                                cryptoCurrency);


                        bitcoinLossProtectedWallet.getBalance(BalanceType.BOOK).debit(bitcoinLossProtectedWalletTransactionWalletRecord2);
                        bitcoinLossProtectedWallet.getBalance(BalanceType.AVAILABLE).debit(bitcoinLossProtectedWalletTransactionWalletRecord2);

                            try{
                                receivedToWallet(id,
                                        txHash,
                                        cryptoAmount,
                                        notes,
                                        actortypeFrom,
                                        actortypeTo,
                                        reference_wallet_sending,
                                        reference_wallet_receiving,
                                        wallet_public_key_sending,
                                        wallet_public_key_receiving,
                                        blockchainNetworkType,
                                        cryptoCurrency);

                            } catch (CantReceiveWalletTransactionException e){

                                bitcoinLossProtectedWallet.revertTransaction(bitcoinLossProtectedWalletTransactionWalletRecord2, false);
                                //broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, wallet_public_key_sending,"TRANSACTIONWALLETREVERSE");

                                dao.setToError(transferIntraWalletUsersWrapper);
                                throw new CantSendTransactionException("I could not send the transaction", e, "TransferIntraWalletUsersModuleManager", "Recived Wallet process error");
                            }


                        //process oK
                        dao.setToCompleted(transferIntraWalletUsersWrapper);

                    } else {
                        dao.setToError(transferIntraWalletUsersWrapper);
                        //There are not enough funds to perform this transaction
                        //broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, wallet_public_key_sending,"TRANSACTIONWALLETREVERSE");

                        throw new TransferIntraWalletUsersNotEnoughFundsException("There are not enough funds to perform this transaction", null, "", "NotEnoughFunds");
                    }

                    break;
                case COMPOSITE_WALLET_MULTI_ACCOUNT:
                    break;


            }



       } catch (CantLoadWalletException e) {
            throw new CantSendTransactionException("I could not send the transaction", e, "TransferIntraWalletUsersModuleManager", "unknown reason");

        } catch (TransferIntraWalletUsersCantInsertRecordException e) {
            throw new CantSendTransactionException("I could not send the transaction", e, "TransferIntraWalletUsersModuleManager", "unknown reason");
        } catch (CantCalculateBalanceException e) {
            throw new CantSendTransactionException("I could not send the transaction", e, "TransferIntraWalletUsersModuleManager", "unknown reason");
        } catch (CantLoadWalletsException e) {
           throw new CantSendTransactionException("I could not send the transaction", e, "TransferIntraWalletUsersModuleManager", "unknown reason");
        }  catch (InvalidParameterException e) {
            throw new CantSendTransactionException("I could not send the transaction", e, "TransferIntraWalletUsersModuleManager", "unknown reason");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantSendTransactionException("I could not send the transaction", e, "TransferIntraWalletUsersModuleManager", "unknown reason");
        }
        catch(TransferIntraWalletUsersNotEnoughFundsException e){
            throw new TransferIntraWalletUsersNotEnoughFundsException("I could not send the transaction", e, "TransferIntraWalletUsersModuleManager", "Not Enough Funds Exception");

        }
        catch (Exception e){
            throw new CantSendTransactionException("I could not send the transaction", FermatException.wrapException(e), "TransferIntraWalletUsersModuleManager", "unknown reason");

        }

    }

    //CREDIT TO DESTINATION WALLET

    private void receivedToWallet(UUID id,
                                 String txHash,
                                 long cryptoAmount,
                                 String notes,
                                  Actors actortypeFrom,
                                 Actors actortypeTo,
                                 ReferenceWallet reference_wallet_sending,
                                 ReferenceWallet reference_wallet_receiving,
                                 String wallet_public_key_sending,
                                 String wallet_public_key_receiving,
                                 BlockchainNetworkType blockchainNetworkType,
                                  CryptoCurrency cryptoCurrency) throws CantReceiveWalletTransactionException {

        try {
            TransferIntraWalletUsersWrapper transactionWrapper = dao.getTransaction(id);

            //checks what is the corresponding wallet to be debited.
            switch (reference_wallet_receiving) {

                case BASIC_WALLET_BITCOIN_WALLET:

                    //Prepares the record to be used within transactions

                    CryptoWalletTransactionWalletRecord bitcoinWalletTransactionWalletRecord2 = buildBitcoinWalletRecord(id,
                            new CryptoAddress("", CryptoCurrency.BITCOIN),
                            null,
                            cryptoAmount,
                            new CryptoAddress("", CryptoCurrency.BITCOIN),
                            notes,
                            System.currentTimeMillis(),
                            "",
                            wallet_public_key_sending,
                            wallet_public_key_receiving,
                            actortypeTo,
                            actortypeFrom,
                            blockchainNetworkType,
                            cryptoCurrency);

                    CryptoWalletWallet cryptoWalletWallet = this.cryptoWalletManager.loadWallet(wallet_public_key_receiving);

                    try {
                        cryptoWalletWallet.getBalance(BalanceType.AVAILABLE).credit(bitcoinWalletTransactionWalletRecord2);

                    }catch (CantRegisterCreditException e){
                        throw new CantReceiveWalletTransactionException(CantReceiveWalletTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
                    }
                    try {
                        cryptoWalletWallet.getBalance(BalanceType.BOOK).credit(bitcoinWalletTransactionWalletRecord2);
                    }catch (CantRegisterCreditException e){
                        cryptoWalletWallet.getBalance(BalanceType.AVAILABLE).debit(bitcoinWalletTransactionWalletRecord2);
                        throw new CantReceiveWalletTransactionException(CantReceiveWalletTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
                    }
                    break;
                case BASIC_WALLET_DISCOUNT_WALLET:
                    break;
                case BASIC_WALLET_FIAT_WALLET:
                    break;
                case BASIC_WALLET_LOSS_PROTECTED_WALLET:
                    //Prepares the record to be used within transactions
                    BitcoinLossProtectedWallet bitcoinLossProtectedWallet = this.bitcoinLossWalletManager.loadWallet(wallet_public_key_receiving);
                    BitcoinLossProtectedWalletTransactionRecord bitcoinLossProtectedWalletTransactionWalletRecord = buildLossWalletRecord(id,
                            new CryptoAddress("", CryptoCurrency.BITCOIN),
                            null,
                            cryptoAmount,
                            new CryptoAddress("", CryptoCurrency.BITCOIN),
                            notes,
                            System.currentTimeMillis(),
                            "",
                            wallet_public_key_sending,
                            wallet_public_key_receiving,
                            actortypeTo,
                            actortypeFrom,
                            blockchainNetworkType,
                            cryptoCurrency);

                 try {
                     bitcoinLossProtectedWallet.getBalance(BalanceType.AVAILABLE).credit(bitcoinLossProtectedWalletTransactionWalletRecord);
                  
                 }catch (CantRegisterCreditException e){
                     throw new CantReceiveWalletTransactionException(CantReceiveWalletTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
                 }
                    try {
                        bitcoinLossProtectedWallet.getBalance(BalanceType.BOOK).credit(bitcoinLossProtectedWalletTransactionWalletRecord);
                    }catch (CantRegisterCreditException e){
                        bitcoinLossProtectedWallet.getBalance(BalanceType.AVAILABLE).debit(bitcoinLossProtectedWalletTransactionWalletRecord);
                        throw new CantReceiveWalletTransactionException(CantReceiveWalletTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
                    }
                    break;


            }
        } catch (CantLoadTableToMemoryException e) {
           throw new CantReceiveWalletTransactionException(CantReceiveWalletTransactionException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantReceiveWalletTransactionException(CantReceiveWalletTransactionException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantLoadWalletException e) {
            throw new CantReceiveWalletTransactionException(CantReceiveWalletTransactionException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantLoadWalletsException e) {
            throw new CantReceiveWalletTransactionException(CantReceiveWalletTransactionException.DEFAULT_MESSAGE, e, "", "");
        }catch (Exception e){
            throw new CantReceiveWalletTransactionException(CantReceiveWalletTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }


    }


    public BitcoinLossProtectedWalletTransactionRecord buildLossWalletRecord(UUID transactionId,
                                                                                   CryptoAddress addressFrom,
                                                                                   UUID requestId,
                                                                                   long amount,
                                                                                   CryptoAddress addressTo,
                                                                                   String memo,
                                                                                   long timestamp,
                                                                                   String transactionHash,
                                                                                   String actorFromPublicKey,
                                                                                   String actorToPublicKey,
                                                                                   Actors actorToType,
                                                                                   Actors actorFromType,
                                                                                   com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType blockchainNetworkType,
                                                                                    CryptoCurrency cryptoCurrency
                                                                            ) {



        BitcoinLossProtectedWalletTransactionRecord bitcoinLossProtectedWalletTransactionRecord = new BitcoinLossProtectedWalletTransactionWalletRecord(transactionId,
                addressFrom,
                requestId,
                amount,
                addressTo,
                memo,
                timestamp,
                transactionHash,
                actorFromPublicKey,
                actorToPublicKey,
                actorToType,
                actorFromType,
                blockchainNetworkType,
                0,
                cryptoCurrency);

        return bitcoinLossProtectedWalletTransactionRecord;

    }


    public CryptoWalletTransactionWalletRecord buildBitcoinWalletRecord(UUID transactionId,
                                                                         CryptoAddress addressFrom,
                                                                         UUID requestId,
                                                                         long amount,
                                                                         CryptoAddress addressTo,
                                                                         String memo,
                                                                         long timestamp,
                                                                         String transactionHash,
                                                                         String actorFromPublicKey,
                                                                         String actorToPublicKey,
                                                                         Actors actorToType,
                                                                         Actors actorFromType,
                                                                         com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType blockchainNetworkType,
                                                                        CryptoCurrency cryptoCurrency) {


        CryptoWalletTransactionWalletRecord bitcoinLossProtectedWalletTransactionRecord = new CryptoWalletTransactionWalletRecord(transactionId,
                addressFrom,
                requestId,
                amount,
                addressTo,
                memo,
                timestamp,
                transactionHash,
                actorFromPublicKey,
                actorToPublicKey,
                actorToType,
                actorFromType,
                blockchainNetworkType,
                cryptoCurrency,
                0,
                FeeOrigin.SUBSTRACT_FEE_FROM_AMOUNT,
                amount);

        return bitcoinLossProtectedWalletTransactionRecord;

    }


}
