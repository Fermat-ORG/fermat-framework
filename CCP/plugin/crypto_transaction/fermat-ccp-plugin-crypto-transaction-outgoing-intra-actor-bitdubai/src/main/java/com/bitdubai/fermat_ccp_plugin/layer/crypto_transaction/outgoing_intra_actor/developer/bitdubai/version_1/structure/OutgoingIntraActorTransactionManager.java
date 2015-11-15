package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetSendCryptoTransactionHashException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.IntraActorCryptoTransactionManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.database.OutgoingIntraActorDao;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionHashException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantInsertRecordException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorDaoException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.UUID;

/**
 * Created by eze on 2015.09.19..
 */
public class OutgoingIntraActorTransactionManager implements IntraActorCryptoTransactionManager {

    private UUID                 pluginId;
    private ErrorManager         errorManager;
    private BitcoinWalletManager bitcoinWalletManager;
    private PluginDatabaseSystem pluginDatabaseSystem;

    public OutgoingIntraActorTransactionManager(UUID pluginId,
                                                ErrorManager errorManager,
                                                BitcoinWalletManager bitcoinWalletManager,
                                                PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginId             = pluginId;
        this.errorManager         = errorManager;
        this.bitcoinWalletManager = bitcoinWalletManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void payCryptoRequest(UUID requestId, String walletPublicKey, CryptoAddress destinationAddress, long cryptoAmount, String description, String senderPublicKey, String receptorPublicKey, Actors senderActorType, Actors receptorActorType) throws OutgoingIntraActorCantSendFundsExceptions, OutgoingIntraActorInsufficientFundsException {
        // TODO: COMPLETE WHEN MONEY REQUESTS ARE IMPLEMENTED
    }

    /**
     * The method <code>sendCrypto</code> is used to send crypto currency to another intra user
     *
     * @param walletPublicKey           The public key of the wallet sending the transaction
     * @param destinationAddress        The crypto address of the user to send the money to
     * @param cryptoAmount              The amount of crypto currency to be sent
     * @param description               A note to register in the wallet balance to describe the transaction
     * @param senderPublicKey           The public key of the actor sending the transaction
     * @param receptorPublicKey         The public key of the actor that we are sending the transaction to
     * @param senderActorType           The type of actor sending the transaction
     * @param receptorActorType         The type of actor receiving the transaction
     * @throws OutgoingIntraActorCantSendFundsExceptions
     * @throws OutgoingIntraActorInsufficientFundsException
     */
    @Override
    public UUID sendCrypto(String          walletPublicKey,
                           CryptoAddress   destinationAddress,
                           long            cryptoAmount,
                           String          description,
                           String          senderPublicKey,
                           String          receptorPublicKey,
                           Actors          senderActorType,
                           Actors          receptorActorType,
                           ReferenceWallet referenceWallet) throws OutgoingIntraActorCantSendFundsExceptions, OutgoingIntraActorInsufficientFundsException {
        try {
            BitcoinWalletWallet bitcoinWalletWallet = this.bitcoinWalletManager.loadWallet(walletPublicKey);
            ;
            long funds = bitcoinWalletWallet.getBalance(BalanceType.AVAILABLE).getBalance();

            if (cryptoAmount > funds)
                throw new OutgoingIntraActorInsufficientFundsException("We don't have enough funds", null, "CryptoAmount: " + cryptoAmount + "\nBalance: " + funds, "Many transactions were accepted before discounting from basic wallet balanace");

            OutgoingIntraActorDao dao = new OutgoingIntraActorDao(this.errorManager, this.pluginDatabaseSystem);
            dao.initialize(this.pluginId);
            UUID transactionId = UUID.randomUUID();
            dao.registerNewTransaction(transactionId, walletPublicKey, destinationAddress, cryptoAmount, null, description, senderPublicKey, senderActorType, receptorPublicKey, receptorActorType, referenceWallet);
            return transactionId;
        } catch (OutgoingIntraActorInsufficientFundsException e) {
            throw e;
        } catch (OutgoingIntraActorCantInsertRecordException | CantLoadWalletException | CantCalculateBalanceException | CantInitializeOutgoingIntraActorDaoException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new OutgoingIntraActorCantSendFundsExceptions("An exception happened",e,"","");
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,FermatException.wrapException(e));
            throw new OutgoingIntraActorCantSendFundsExceptions("An unexpected exception happened", FermatException.wrapException(e),"","");
        }
    }

    @Override
    public UUID sendCrypto(String           walletPublicKey,
                           CryptoAddress    destinationAddress,
                           long             cryptoAmount,
                           String           op_Return,
                           String           description,
                           String           senderPublicKey,
                           String           receptorPublicKey,
                           Actors           senderActorType,
                           Actors           receptorActorType,
                           ReferenceWallet  referenceWallet) throws OutgoingIntraActorCantSendFundsExceptions, OutgoingIntraActorInsufficientFundsException {

        BitcoinWalletWallet bitcoinWalletWallet = null;
        try {
            bitcoinWalletWallet = this.bitcoinWalletManager.loadWallet(walletPublicKey);
            long funds = bitcoinWalletWallet.getBalance(BalanceType.AVAILABLE).getBalance();

            if (cryptoAmount > funds) {
                throw new OutgoingIntraActorInsufficientFundsException("We don't have enough funds", null, "CryptoAmount: " + cryptoAmount + "\nBalance: " + funds, "Many transactions were accepted before discounting from basic wallet balance");
            }
        } catch (CantLoadWalletException | CantCalculateBalanceException  e) {
            e.printStackTrace();
        }

        OutgoingIntraActorDao dao = new OutgoingIntraActorDao(this.errorManager, this.pluginDatabaseSystem);
        try {
            UUID transactionId = UUID.randomUUID();
            dao.initialize(this.pluginId);
            dao.registerNewTransaction(transactionId, walletPublicKey, destinationAddress, cryptoAmount, op_Return, description, senderPublicKey, senderActorType, receptorPublicKey, receptorActorType, referenceWallet);
            return transactionId;
        } catch (CantInitializeOutgoingIntraActorDaoException | OutgoingIntraActorCantInsertRecordException e) {
            throw new OutgoingIntraActorCantSendFundsExceptions("An exception happened",e,"","");
        }
    }

    @Override
    public String getSendCryptoTransactionHash(UUID transactionId) throws OutgoingIntraActorCantGetSendCryptoTransactionHashException {
        OutgoingIntraActorDao dao = new OutgoingIntraActorDao(this.errorManager, this.pluginDatabaseSystem);
        try {
            dao.initialize(this.pluginId);
            return dao.getSendCryptoTransactionHash(transactionId);
        } catch (CantInitializeOutgoingIntraActorDaoException e) {
            throw new OutgoingIntraActorCantGetSendCryptoTransactionHashException("There was an error initializing the DAO object.", e, null, "database issue");
        } catch (OutgoingIntraActorCantGetTransactionHashException e) {
            throw new OutgoingIntraActorCantGetSendCryptoTransactionHashException("There was an getting the transaction from the table.", e, null, "database issue");
        }
    }
}
