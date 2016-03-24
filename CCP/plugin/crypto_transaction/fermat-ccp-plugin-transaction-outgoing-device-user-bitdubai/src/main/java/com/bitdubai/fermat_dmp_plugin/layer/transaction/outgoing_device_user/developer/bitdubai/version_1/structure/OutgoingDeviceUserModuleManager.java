package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_device_user.interfaces.OutgoingDeviceUser;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.OutgoingExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentManager;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.database.OutgoingDeviceUserTransactionDao;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.enums.TransactionState;
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

    //TODO: el metodo tiene que atrapar y hacer throw de errores para que quien lo llamo sepa que paso
    //hay q ver si no puede completar la transaccion de avisar con una notificacion
    @Override
    public void sendToWallet(UUID trxId,
                             String txHash,
                             long cryptoAmount,
                             String notes,
                             Actors actortype,
                             ReferenceWallet reference_wallet_sending,
                             ReferenceWallet reference_wallet_receiving,
                             String wallet_public_key_sending,
                             String wallet_public_key_receiving,
                             BlockchainNetworkType blockchainNetworkType) {


        Long balanceBeforeCredit = null;
        Long balanceAfterCredit = null;

        UUID id = UUID.randomUUID();



//TODO: Esto va dentro del switch de tipo de wallet porque los record son proios de cada wallet
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




        switch (reference_wallet_receiving){

            case  BASIC_WALLET_BITCOIN_WALLET:



                try {
                    balanceBeforeCredit =   this.bitcoinWalletManager.loadWallet(wallet_public_key_receiving).getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
                } catch (CantCalculateBalanceException e) {
                    e.printStackTrace();
                } catch (CantLoadWalletsException e) {
                    e.printStackTrace();
                }

                if (balanceBeforeCredit!=null){
                    try {
                        this.bitcoinWalletManager.loadWallet(wallet_public_key_receiving).getBalance(BalanceType.BOOK).credit(bitcoinWalletTransactionWalletRecord);
                        this.bitcoinWalletManager.loadWallet(wallet_public_key_receiving).getBalance(BalanceType.AVAILABLE).credit(bitcoinWalletTransactionWalletRecord);
                    } catch (CantRegisterCreditException e) {
                        e.printStackTrace();
                    } catch (CantLoadWalletsException e) {
                        e.printStackTrace();
                    }
                }

                //Register the new transaction
//TODO: esto es lo primero que tiene que hacer el metodo, registrar la transaccion en su base de datos
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
                }


                try {
                    balanceAfterCredit =   this.bitcoinWalletManager.loadWallet(wallet_public_key_receiving).getTransactionById(bitcoinWalletTransactionWalletRecord.getTransactionId()).getAmount();
                } catch (CantFindTransactionException e) {
                    e.printStackTrace();
                } catch (CantLoadWalletsException e) {
                    e.printStackTrace();
                }

                if (balanceAfterCredit == cryptoAmount){

                    //TODO: este switch me parece que puede ir afuera, tenes una verificacion de que wallet envia y otra que que wallet manda

                    switch (reference_wallet_sending) {

                        case BASIC_WALLET_BITCOIN_WALLET:
                            break;
                        case BASIC_WALLET_DISCOUNT_WALLET:
                            break;
                        case BASIC_WALLET_FIAT_WALLET:
                            break;
                        case BASIC_WALLET_LOSS_PROTECTED_WALLET:

                            try {
                                this.bitcoinLossWalletManager.loadWallet(wallet_public_key_sending).getBalance(BalanceType.AVAILABLE).debit(bitcoinLossProtectedWalletTransactionWalletRecord);
                                this.bitcoinLossWalletManager.loadWallet(wallet_public_key_sending).getBalance(BalanceType.BOOK).debit(bitcoinLossProtectedWalletTransactionWalletRecord);
                            } catch (CantRegisterDebitException e) {
                                e.printStackTrace();
                            } catch (CantLoadWalletException e) {
                                e.printStackTrace();
                            }


                            try {
                                OutgoingDeviceUserTransactionWrapper transactionWrapper = dao.getTransaction(id);
                                dao.setToCompleted(transactionWrapper);

                            } catch (CantLoadTableToMemoryException e) {
                                e.printStackTrace();
                            } catch (InvalidParameterException e) {
                                e.printStackTrace();
                            } catch (OutgoingIntraActorCantCancelTransactionException e) {
                                e.printStackTrace();
                            }

                            break;


                    }

                }



                break;
            case  BASIC_WALLET_DISCOUNT_WALLET:
                break;
            case  BASIC_WALLET_FIAT_WALLET:
                break;
            case  BASIC_WALLET_LOSS_PROTECTED_WALLET:


                try {
                    balanceBeforeCredit =   this.bitcoinLossWalletManager.loadWallet(wallet_public_key_receiving).getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
                } catch (CantCalculateBalanceException e) {
                    e.printStackTrace();
                } catch (CantLoadWalletException e) {
                    e.printStackTrace();
                }

                if (balanceBeforeCredit!=null){
                    try {
                        this.bitcoinLossWalletManager.loadWallet(wallet_public_key_receiving).getBalance(BalanceType.BOOK).credit(bitcoinLossProtectedWalletTransactionWalletRecord);
                        this.bitcoinLossWalletManager.loadWallet(wallet_public_key_receiving).getBalance(BalanceType.AVAILABLE).credit(bitcoinLossProtectedWalletTransactionWalletRecord);
                    } catch (CantLoadWalletException e) {
                        e.printStackTrace();
                    } catch (CantRegisterCreditException e) {
                        e.printStackTrace();
                    }
                }


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
                }


                try {
                    balanceAfterCredit =   this.bitcoinLossWalletManager.loadWallet(wallet_public_key_receiving).getTransactionById(bitcoinLossProtectedWalletTransactionWalletRecord.getTransactionId()).getAmount();
                } catch (CantFindTransactionException e) {
                    e.printStackTrace();
                } catch (CantLoadWalletException e) {
                    e.printStackTrace();
                }

                if (balanceAfterCredit == cryptoAmount){

                    switch (reference_wallet_sending) {

                        case BASIC_WALLET_BITCOIN_WALLET:

                            try {

                                try {
                                    this.bitcoinWalletManager.loadWallet(wallet_public_key_sending).getBalance(BalanceType.AVAILABLE).debit(bitcoinWalletTransactionWalletRecord);
                                    this.bitcoinWalletManager.loadWallet(wallet_public_key_sending).getBalance(BalanceType.BOOK).debit(bitcoinWalletTransactionWalletRecord);
                                } catch (CantRegisterDebitException e) {
                                    e.printStackTrace();
                                }

                            } catch (CantLoadWalletsException e) {
                                e.printStackTrace();
                            }

                            try {
                                OutgoingDeviceUserTransactionWrapper transactionWrapper = dao.getTransaction(id);
                                dao.setToCompleted(transactionWrapper);

                            } catch (CantLoadTableToMemoryException e) {
                                e.printStackTrace();
                            } catch (InvalidParameterException e) {
                                e.printStackTrace();
                            } catch (OutgoingIntraActorCantCancelTransactionException e) {
                                e.printStackTrace();
                            }




                            break;
                        case BASIC_WALLET_DISCOUNT_WALLET:
                            break;
                        case BASIC_WALLET_FIAT_WALLET:
                            break;
                        case BASIC_WALLET_LOSS_PROTECTED_WALLET:
                            break;


                    }

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
