package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetSendCryptoTransactionHashException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.IntraActorCryptoTransactionManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.database.OutgoingIntraActorDao;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorDaoException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionHashException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantInsertRecordException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by eze on 2015.09.19..
 */
public class OutgoingIntraActorTransactionManager implements IntraActorCryptoTransactionManager {

    private UUID                 pluginId;
    private ErrorManager         errorManager;
    private CryptoWalletManager cryptoWalletManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private BitcoinLossProtectedWalletManager bitcoinLossProtectedWalletManager;

    public OutgoingIntraActorTransactionManager(UUID pluginId,
                                                ErrorManager errorManager,
                                                CryptoWalletManager cryptoWalletManager,
                                                PluginDatabaseSystem pluginDatabaseSystem,
                                                BitcoinLossProtectedWalletManager bitcoinLossProtectedWalletManager) {

        this.pluginId             = pluginId;
        this.errorManager         = errorManager;
        this.cryptoWalletManager = cryptoWalletManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.bitcoinLossProtectedWalletManager = bitcoinLossProtectedWalletManager;
    }

    @Override
    public void payCryptoRequest(UUID requestId, String walletPublicKey, CryptoAddress destinationAddress,
                                 long cryptoAmount, String description, String senderPublicKey,
                                 String receptorPublicKey, Actors senderActorType,
                                 Actors receptorActorType,ReferenceWallet referenceWallet,
                                 BlockchainNetworkType blockchainNetworkType,
                                 CryptoCurrency cryptoCurrency,
                                 long fee, FeeOrigin feeOrigin ) throws OutgoingIntraActorCantSendFundsExceptions, OutgoingIntraActorInsufficientFundsException {

        try {
            long funds = 0;
            CryptoWalletWallet cryptoWalletWallet;
            switch (referenceWallet) {

                case BASIC_WALLET_BITCOIN_WALLET:
                    cryptoWalletWallet = this.cryptoWalletManager.loadWallet(walletPublicKey);
                    funds = cryptoWalletWallet.getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
                    break;
                case BASIC_WALLET_FERMAT_WALLET:
                   cryptoWalletWallet = this.cryptoWalletManager.loadWallet(walletPublicKey);
                    funds = cryptoWalletWallet.getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
                    break;
                case BASIC_WALLET_LOSS_PROTECTED_WALLET:
                    BitcoinLossProtectedWallet lossProtectedWalletWallet = this.bitcoinLossProtectedWalletManager.loadWallet(walletPublicKey);
                    funds = lossProtectedWalletWallet.getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
                    break;
            }


            if (cryptoAmount > funds)
                throw new OutgoingIntraActorInsufficientFundsException("We don't have enough funds", null, "CryptoAmount: " + cryptoAmount + "\nBalance: " + funds, "Many transactions were accepted before discounting from basic wallet balanace");

            OutgoingIntraActorDao dao = new OutgoingIntraActorDao(this.errorManager, this.pluginDatabaseSystem);
            dao.initialize(this.pluginId);
            UUID transactionId = UUID.randomUUID();

            dao.registerNewTransaction(transactionId, requestId, walletPublicKey,
                    destinationAddress, cryptoAmount, null, description, senderPublicKey,
                    senderActorType, receptorPublicKey, receptorActorType, referenceWallet, false, blockchainNetworkType,
                    cryptoCurrency,
                    fee,
                    feeOrigin);

        } catch (OutgoingIntraActorInsufficientFundsException e) {
            throw e;
        } catch (OutgoingIntraActorCantInsertRecordException | CantLoadWalletsException | CantCalculateBalanceException | CantInitializeOutgoingIntraActorDaoException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new OutgoingIntraActorCantSendFundsExceptions("An exception happened",e,"","");
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,FermatException.wrapException(e));
            throw new OutgoingIntraActorCantSendFundsExceptions("An unexpected exception happened", FermatException.wrapException(e),"","");
        }
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
                           ReferenceWallet referenceWallet,
                           BlockchainNetworkType blockchainNetworkType,
                           CryptoCurrency cryptoCurrency,
                           long fee,
                           FeeOrigin feeOrigin) throws OutgoingIntraActorCantSendFundsExceptions, OutgoingIntraActorInsufficientFundsException {
        try {

            long funds = 0;
            CryptoWalletWallet cryptoWalletWallet;
            switch (referenceWallet) {
                case BASIC_WALLET_BITCOIN_WALLET:
                     cryptoWalletWallet = this.cryptoWalletManager.loadWallet(walletPublicKey);
                     funds = cryptoWalletWallet.getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
                break;

                case BASIC_WALLET_FERMAT_WALLET:
                     cryptoWalletWallet = this.cryptoWalletManager.loadWallet(walletPublicKey);
                    funds = cryptoWalletWallet.getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
                    break;
                case BASIC_WALLET_LOSS_PROTECTED_WALLET:
                    BitcoinLossProtectedWallet lossProtectedWalletWallet = this.bitcoinLossProtectedWalletManager.loadWallet(walletPublicKey);
                    funds = lossProtectedWalletWallet.getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
                    break;
            }
            if (cryptoAmount > funds)
                throw new OutgoingIntraActorInsufficientFundsException("We don't have enough funds", null, "CryptoAmount: " + cryptoAmount + "\nBalance: " + funds, "Many transactions were accepted before discounting from basic wallet balanace");

            OutgoingIntraActorDao dao = new OutgoingIntraActorDao(this.errorManager, this.pluginDatabaseSystem);
            dao.initialize(this.pluginId);
            UUID transactionId = UUID.randomUUID();
            dao.registerNewTransaction(transactionId, null, walletPublicKey, destinationAddress, cryptoAmount, null,
                    description, senderPublicKey, senderActorType, receptorPublicKey,
                    receptorActorType, referenceWallet, false,blockchainNetworkType,
                    cryptoCurrency,
                     fee,
                    feeOrigin);

            return transactionId;
        } catch (OutgoingIntraActorInsufficientFundsException e) {
            throw e;
        } catch (OutgoingIntraActorCantInsertRecordException | CantLoadWalletsException | CantCalculateBalanceException | CantInitializeOutgoingIntraActorDaoException e) {
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
                           ReferenceWallet referenceWallet,
                           boolean sendFromSameDevice,
                           BlockchainNetworkType blockchainNetworkType,
                           CryptoCurrency cryptoCurrency,
                           long fee,FeeOrigin feeOrigin) throws OutgoingIntraActorCantSendFundsExceptions, OutgoingIntraActorInsufficientFundsException {

        CryptoWalletWallet cryptoWalletWallet = null;
        try {

            long funds =0;
            switch (referenceWallet) {
                case BASIC_WALLET_BITCOIN_WALLET:
                    cryptoWalletWallet = this.cryptoWalletManager.loadWallet(walletPublicKey);
                    funds = cryptoWalletWallet.getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
                    break;

                case BASIC_WALLET_LOSS_PROTECTED_WALLET:
                    BitcoinLossProtectedWallet lossProtectedWalletWallet = this.bitcoinLossProtectedWalletManager.loadWallet(walletPublicKey);
                    funds = lossProtectedWalletWallet.getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
                    break;
            }
            if (cryptoAmount > funds) {
                throw new OutgoingIntraActorInsufficientFundsException("We don't have enough funds", null, "CryptoAmount: " + cryptoAmount + "\nBalance: " + funds, "Many transactions were accepted before discounting from basic wallet balance");
            }
        } catch (CantLoadWalletsException | CantCalculateBalanceException  e) {
            e.printStackTrace();
        } catch (CantLoadWalletException e) {
            e.printStackTrace();
        }

        OutgoingIntraActorDao dao = new OutgoingIntraActorDao(this.errorManager, this.pluginDatabaseSystem);
        try {
            UUID transactionId = UUID.randomUUID();
            dao.initialize(this.pluginId);
            dao.registerNewTransaction(transactionId, null, walletPublicKey, destinationAddress, cryptoAmount, op_Return, description, senderPublicKey, senderActorType, receptorPublicKey, receptorActorType, referenceWallet, sendFromSameDevice, blockchainNetworkType, cryptoCurrency, fee, feeOrigin);
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
