package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_device_user.exceptions.OutgoingDeviceUserNotEnoughFundsException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_device_user.interfaces.OutgoingDeviceUser;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.database.OutgoingDeviceUserTransactionDao;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.enums.TransactionState;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_device_user.exceptions.CantSendTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantInsertRecordException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.utils.BitcoinLossProtectedWalletTransactionWalletRecord;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.utils.BitcoinWalletTransactionWalletRecord;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.utils.OutgoingDeviceUserTransactionWrapper;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Joaquin Carrasquero on 18/03/16.
 */
public class OutgoingDeviceUserModuleManager implements OutgoingDeviceUser{




    private final BitcoinLossProtectedWalletManager bitcoinLossWalletManager;
    private final BitcoinWalletManager bitcoinWalletManager;
    private final ErrorManager errorManager;
    private final OutgoingDeviceUserTransactionDao dao;


    public OutgoingDeviceUserModuleManager(final BitcoinLossProtectedWalletManager bitcoinLossWalletManager,
                                           final BitcoinWalletManager bitcoinWalletManager,
                                           final ErrorManager errorManager,
                                           final OutgoingDeviceUserTransactionDao dao) {
        this.bitcoinLossWalletManager = bitcoinLossWalletManager;
        this.bitcoinWalletManager = bitcoinWalletManager;
        this.errorManager = errorManager;
        this.dao = dao;
    }

    //hay q ver si no puede completar la transaccion de avisar con una notificacion
    @Override
    public void sendToWallet(
                             String txHash,
                             long cryptoAmount,
                             String notes,
                             Actors actortype,
                             ReferenceWallet reference_wallet_sending,
                             ReferenceWallet reference_wallet_receiving,
                             String wallet_public_key_sending,
                             String wallet_public_key_receiving,
                             BlockchainNetworkType blockchainNetworkType) throws CantSendTransactionException, OutgoingDeviceUserNotEnoughFundsException {


        Long balanceBeforeCredit = null;
        Long amountRecord = null;

        Long initialBalance = null;

        UUID id = UUID.randomUUID();

        //Chooses wallet to be credited.

        switch (reference_wallet_receiving){

            case  BASIC_WALLET_BITCOIN_WALLET:

                //consult current balance of the sending wallet

                try {
                    initialBalance =   this.bitcoinLossWalletManager.loadWallet(wallet_public_key_sending).getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
                } catch (CantCalculateBalanceException e) {
                    e.printStackTrace();
                    throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                } catch (CantLoadWalletException e) {
                    e.printStackTrace();
                    throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                }


                //Checks the balance of the debiting wallet in order to decide if to continue or not.

                if (initialBalance != null && initialBalance >= cryptoAmount){


                    //Prepares the record to be used within transactions

                    BitcoinWalletTransactionWalletRecord bitcoinWalletTransactionWalletRecord =  buildBitcoinWalletRecord(id,
                            null,
                            null,
                            cryptoAmount,
                            null,
                            notes,
                            System.currentTimeMillis(),
                            "",
                            "",
                            "",
                            actortype,
                            actortype,
                            blockchainNetworkType);


                    try {
                        balanceBeforeCredit =   this.bitcoinWalletManager.loadWallet(wallet_public_key_receiving).getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
                    } catch (CantLoadWalletsException e) {
                        e.printStackTrace();
                        throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                    } catch (CantCalculateBalanceException e) {
                        e.printStackTrace();
                        throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                    }

                    //Checks that the current balance for the crediting wallet is not null in order to proceed.

                    if (balanceBeforeCredit!=null){


                        //Register the new transaction
                        try {
                            dao.registerNewTransaction(id,
                                    txHash,
                                    cryptoAmount,
                                    notes,
                                    actortype,
                                    reference_wallet_sending,
                                    reference_wallet_receiving,
                                    wallet_public_key_sending,
                                    wallet_public_key_receiving,
                                    TransactionState.SENT_TO_WALLET,
                                    blockchainNetworkType);
                        } catch (OutgoingIntraActorCantInsertRecordException e) {
                            e.printStackTrace();
                            throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                        }




                        try {
                            this.bitcoinWalletManager.loadWallet(wallet_public_key_receiving).getBalance(BalanceType.BOOK).credit(bitcoinWalletTransactionWalletRecord);
                            this.bitcoinWalletManager.loadWallet(wallet_public_key_receiving).getBalance(BalanceType.AVAILABLE).credit(bitcoinWalletTransactionWalletRecord);
                        } catch (CantRegisterCreditException e) {
                            e.printStackTrace();
                            throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                        } catch (CantLoadWalletsException e) {
                            e.printStackTrace();
                            throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                        }
                    }




                    try {
                        amountRecord =   this.bitcoinWalletManager.loadWallet(wallet_public_key_receiving).getTransactionById(bitcoinWalletTransactionWalletRecord.getTransactionId()).getAmount();
                    } catch (CantFindTransactionException e) {
                        e.printStackTrace();
                        throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                    } catch (CantLoadWalletsException e) {
                        e.printStackTrace();
                        throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                    }

                    //check if amount within DB record correspond with the amount sent,
                    // if it does, then it should debit it within the wallet.
                    //if it does not, then it will not continue.


                    if (amountRecord == cryptoAmount){


                        //checks what is the corresponding wallet to be debited.

                        switch (reference_wallet_sending) {

                            case BASIC_WALLET_BITCOIN_WALLET:
                                break;
                            case BASIC_WALLET_DISCOUNT_WALLET:
                                break;
                            case BASIC_WALLET_FIAT_WALLET:
                                break;
                            case BASIC_WALLET_LOSS_PROTECTED_WALLET:

                                //Prepares the record to be used within transactions

                                BitcoinLossProtectedWalletTransactionWalletRecord bitcoinLossProtectedWalletTransactionWalletRecord =  buildLossWalletRecord(id,
                                        null,
                                        null,
                                        cryptoAmount,
                                        null,
                                        notes,
                                        System.currentTimeMillis(),
                                        "",
                                        "",
                                        "",
                                        actortype,
                                        actortype,
                                        blockchainNetworkType);

                                try {
                                    this.bitcoinLossWalletManager.loadWallet(wallet_public_key_sending).getBalance(BalanceType.AVAILABLE).debit(bitcoinLossProtectedWalletTransactionWalletRecord);
                                    this.bitcoinLossWalletManager.loadWallet(wallet_public_key_sending).getBalance(BalanceType.BOOK).debit(bitcoinLossProtectedWalletTransactionWalletRecord);
                                } catch (CantRegisterDebitException e) {
                                    e.printStackTrace();
                                    throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                                } catch (CantLoadWalletException e) {
                                    e.printStackTrace();
                                    throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                                }

                                //Changes record state to completed.

                                try {
                                    OutgoingDeviceUserTransactionWrapper transactionWrapper = dao.getTransaction(id);
                                    dao.setToCompleted(transactionWrapper);

                                } catch (CantLoadTableToMemoryException e) {
                                    e.printStackTrace();
                                    throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                                } catch (InvalidParameterException e) {
                                    e.printStackTrace();
                                    throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                                } catch (OutgoingIntraActorCantCancelTransactionException e) {
                                    e.printStackTrace();
                                    throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                                }

                                break;


                        }

                    }//end if



                }else{
                    //There are not enough funds to perform this transaction
                    throw new OutgoingDeviceUserNotEnoughFundsException("There are not enough funds to perform this transaction",null,"","NotEnoughFunds");
                }


                break;
            case  BASIC_WALLET_DISCOUNT_WALLET:
                break;
            case  BASIC_WALLET_FIAT_WALLET:
                break;
            case  BASIC_WALLET_LOSS_PROTECTED_WALLET:


                //consult current balance of the sending wallet

                try {
                    initialBalance =   this.bitcoinWalletManager.loadWallet(wallet_public_key_sending).getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
                } catch (CantLoadWalletsException e) {
                    e.printStackTrace();
                    throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                } catch (CantCalculateBalanceException e) {
                    e.printStackTrace();
                    throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                }

                //Checks the balance of the debiting wallet in order to decide if to continue or not.

                if (initialBalance != null && initialBalance >= cryptoAmount){

                    //Prepares the record to be used within transactions

                    BitcoinLossProtectedWalletTransactionWalletRecord bitcoinLossProtectedWalletTransactionWalletRecord2 =  buildLossWalletRecord(id,
                            null,
                            null,
                            cryptoAmount,
                            null,
                            notes,
                            System.currentTimeMillis(),
                            "",
                            "",
                            "",
                            actortype,
                            actortype,
                            blockchainNetworkType);



                    try {
                        balanceBeforeCredit =   this.bitcoinLossWalletManager.loadWallet(wallet_public_key_receiving).getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
                    } catch (CantCalculateBalanceException e) {
                        e.printStackTrace();
                        throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                    } catch (CantLoadWalletException e) {
                        e.printStackTrace();
                        throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                    }


                    //Checks that the current balance for the crediting wallet is not null in order to proceed.


                    if (balanceBeforeCredit!=null){


                        try {
                            dao.registerNewTransaction(id,
                                    txHash,
                                    cryptoAmount,
                                    notes,
                                    actortype,
                                    reference_wallet_sending,
                                    reference_wallet_receiving,
                                    wallet_public_key_sending,
                                    wallet_public_key_receiving,
                                    TransactionState.SENT_TO_WALLET,
                                    blockchainNetworkType);
                        } catch (OutgoingIntraActorCantInsertRecordException e) {
                            e.printStackTrace();
                            throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                        }

                        try {
                            this.bitcoinLossWalletManager.loadWallet(wallet_public_key_receiving).getBalance(BalanceType.BOOK).credit(bitcoinLossProtectedWalletTransactionWalletRecord2);
                            this.bitcoinLossWalletManager.loadWallet(wallet_public_key_receiving).getBalance(BalanceType.AVAILABLE).credit(bitcoinLossProtectedWalletTransactionWalletRecord2);
                        } catch (CantLoadWalletException e) {
                            e.printStackTrace();
                            throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                        } catch (CantRegisterCreditException e) {
                            e.printStackTrace();
                            throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                        }
                    }




                    try {
                        amountRecord =   this.bitcoinLossWalletManager.loadWallet(wallet_public_key_receiving).getTransactionById(bitcoinLossProtectedWalletTransactionWalletRecord2.getTransactionId()).getAmount();
                    } catch (CantFindTransactionException e) {
                        e.printStackTrace();
                        throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                    } catch (CantLoadWalletException e) {
                        e.printStackTrace();
                        throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                    }



                    //check if amount within DB record correspond with the amount sent,
                    // if it does, then it should debit it within the wallet.
                    //if it does not, then it will not continue.


                    if (amountRecord == cryptoAmount){

                        //checks what is the corresponding wallet to be debited.

                        switch (reference_wallet_sending) {

                            case BASIC_WALLET_BITCOIN_WALLET:

                                //Prepares the record to be used within transactions

                                BitcoinWalletTransactionWalletRecord bitcoinWalletTransactionWalletRecord2 =  buildBitcoinWalletRecord(id,
                                        null,
                                        null,
                                        cryptoAmount,
                                        null,
                                        notes,
                                        System.currentTimeMillis(),
                                        "",
                                        "",
                                        "",
                                        actortype,
                                        actortype,
                                        blockchainNetworkType);

                                try {

                                    try {
                                        this.bitcoinWalletManager.loadWallet(wallet_public_key_sending).getBalance(BalanceType.AVAILABLE).debit(bitcoinWalletTransactionWalletRecord2);
                                        this.bitcoinWalletManager.loadWallet(wallet_public_key_sending).getBalance(BalanceType.BOOK).debit(bitcoinWalletTransactionWalletRecord2);
                                    } catch (CantRegisterDebitException e) {
                                        e.printStackTrace();
                                        throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                                    }

                                } catch (CantLoadWalletsException e) {
                                    e.printStackTrace();
                                    throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                                }

                                //Change record state to completed.

                                try {
                                    OutgoingDeviceUserTransactionWrapper transactionWrapper = dao.getTransaction(id);
                                    dao.setToCompleted(transactionWrapper);

                                } catch (CantLoadTableToMemoryException e) {
                                    e.printStackTrace();
                                    throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                                } catch (InvalidParameterException e) {
                                    e.printStackTrace();
                                    throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                                } catch (OutgoingIntraActorCantCancelTransactionException e) {
                                    e.printStackTrace();
                                    throw new CantSendTransactionException("I could not send the transaction",e,"OutgoingDeviceUserModuleManager","unknown reason");
                                }




                                break;
                            case BASIC_WALLET_DISCOUNT_WALLET:
                                break;
                            case BASIC_WALLET_FIAT_WALLET:
                                break;
                            case BASIC_WALLET_LOSS_PROTECTED_WALLET:
                                break;


                        }

                    }//end if



                }else{
                    //There are not enough funds to perform this transaction
                    throw new OutgoingDeviceUserNotEnoughFundsException("There are not enough funds to perform this transaction",null,"","NotEnoughFunds");
                }


                break;
            case    COMPOSITE_WALLET_MULTI_ACCOUNT:
                break;


        }


    }



    public BitcoinLossProtectedWalletTransactionWalletRecord buildLossWalletRecord(UUID transactionId,
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
                                                                                   com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType blockchainNetworkType){


        //UUID pluginId = UUID.randomUUID();

        BitcoinLossProtectedWalletTransactionWalletRecord bitcoinLossProtectedWalletTransactionRecord = new BitcoinLossProtectedWalletTransactionWalletRecord(transactionId,
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
                blockchainNetworkType);

//        BitcoinLossProtectedWalletTransactionWalletRecord bitcoinLossProtectedWalletTransactionRecord = new BitcoinLossProtectedWalletTransactionWalletRecord(pluginId,
//                null,
//                null,
//                cryptoAmount,
//                null,
//                notes,
//                System.currentTimeMillis(),
//                "",
//                "",
//                "",
//                actorType,
//                actorType,
//                blockchainNetworkType);





        return bitcoinLossProtectedWalletTransactionRecord;

    }


    public BitcoinWalletTransactionWalletRecord buildBitcoinWalletRecord(UUID transactionId,
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
                                                                         com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType blockchainNetworkType){


        //UUID pluginId = UUID.randomUUID();



        BitcoinWalletTransactionWalletRecord bitcoinLossProtectedWalletTransactionRecord = new BitcoinWalletTransactionWalletRecord(transactionId,
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
                blockchainNetworkType);



//        BitcoinWalletTransactionWalletRecord bitcoinLossProtectedWalletTransactionRecord = new BitcoinWalletTransactionWalletRecord(pluginId,
//                null,
//                null,
//                cryptoAmount,
//                null,
//                notes,
//                System.currentTimeMillis(),
//                "",
//                "",
//                "",
//                actorType,
//                actorType,
//                blockchainNetworkType);


        return bitcoinLossProtectedWalletTransactionRecord;

    }






}
