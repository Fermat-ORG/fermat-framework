package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.OutgoingIntraUserCantSendFundsExceptions;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.OutgoingIntraUserInsufficientFundsException;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.interfaces.IntraUserCryptoTransactionManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.database.OutgoingIntraUserDao;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraUserDaoException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserCantInsertRecordException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.UUID;

/**
 * Created by eze on 2015.09.19..
 */
public class OutgoingIntraUserTransactionManager implements IntraUserCryptoTransactionManager {

    private UUID                 pluginId;
    private ErrorManager         errorManager;
    private BitcoinWalletManager bitcoinWalletManager;
    private PluginDatabaseSystem pluginDatabaseSystem;

    public OutgoingIntraUserTransactionManager(UUID                 pluginId,
                                               ErrorManager         errorManager,
                                               BitcoinWalletManager bitcoinWalletManager,
                                               PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginId             = pluginId;
        this.errorManager         = errorManager;
        this.bitcoinWalletManager = bitcoinWalletManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void payCryptoRequest(UUID requestId, String walletPublicKey, CryptoAddress destinationAddress, long cryptoAmount, String description, String senderPublicKey, String receptorPublicKey, Actors senderActorType, Actors receptorActorType) throws OutgoingIntraUserCantSendFundsExceptions, OutgoingIntraUserInsufficientFundsException {
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
     * @throws OutgoingIntraUserCantSendFundsExceptions
     * @throws OutgoingIntraUserInsufficientFundsException
     */
    @Override
    public void sendCrypto(String          walletPublicKey,
                           CryptoAddress   destinationAddress,
                           long            cryptoAmount,
                           String          description,
                           String          senderPublicKey,
                           String          receptorPublicKey,
                           Actors          senderActorType,
                           Actors          receptorActorType,
                           ReferenceWallet referenceWallet) throws OutgoingIntraUserCantSendFundsExceptions, OutgoingIntraUserInsufficientFundsException {
        try {
            BitcoinWalletWallet bitcoinWalletWallet = this.bitcoinWalletManager.loadWallet(walletPublicKey);
            ;
            long funds = bitcoinWalletWallet.getBalance(BalanceType.AVAILABLE).getBalance();

            if (cryptoAmount > funds)
                throw new OutgoingIntraUserInsufficientFundsException("We don't have enough funds", null, "CryptoAmount: " + cryptoAmount + "\nBalance: " + funds, "Many transactions were accepted before discounting from basic wallet balanace");

            OutgoingIntraUserDao dao = new OutgoingIntraUserDao(this.errorManager, this.pluginDatabaseSystem);
            dao.initialize(this.pluginId);
            dao.registerNewTransaction(walletPublicKey, destinationAddress, cryptoAmount, description, senderPublicKey, senderActorType, receptorPublicKey, receptorActorType, referenceWallet);
        } catch (OutgoingIntraUserInsufficientFundsException e) {
            throw e;
        } catch (OutgoingIntraUserCantInsertRecordException | CantLoadWalletException | CantCalculateBalanceException | CantInitializeOutgoingIntraUserDaoException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new OutgoingIntraUserCantSendFundsExceptions("An exception happened",e,"","");
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,FermatException.wrapException(e));
            throw new OutgoingIntraUserCantSendFundsExceptions("An unexpected exception happened", FermatException.wrapException(e),"","");
        }
    }
}
