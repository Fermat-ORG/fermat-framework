package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Owner;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_ccp_api.all_definition.constants.CCPBroadcasterConstants;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantGetCryptoAddressBookRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CryptoAddressBookRecordNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_ACTIVITY_TO_OPEN_CODE;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_NOTIFICATION_PAINTER_FROM;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_TO_OPEN_PUBLIC_KEY;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.NOTIFICATION_ID;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.SOURCE_PLUGIN;

/**
 * Created by eze on 2015.09.11..
 */
public class IncomingIntraUserTransactionHandler {
    private EventManager             eventManager;
    private CryptoWalletManager cryptoWalletManager;
    private CryptoAddressBookManager cryptoAddressBookManager;
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;
    private Broadcaster broadcaster;
    private BitcoinLossProtectedWalletManager lossProtectedWalletManager;

    public IncomingIntraUserTransactionHandler(EventManager eventManager, CryptoWalletManager cryptoWalletManager, CryptoAddressBookManager cryptoAddressBookManager,CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager,
                                               Broadcaster broadcaster,
                                               BitcoinLossProtectedWalletManager lossProtectedWalletManager) {
        this.eventManager             = eventManager;
        this.cryptoWalletManager = cryptoWalletManager;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
        this.broadcaster = broadcaster;
        this.lossProtectedWalletManager = lossProtectedWalletManager;

    }

    public void handleTransaction(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.TransactionCompleteInformation transaction) throws CantGetCryptoAddressBookRecordException, CantLoadWalletsException, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantExecuteTransactionException {
        try {
            try {
                CryptoAddressBookRecord cryptoAddressBookRecord = this.cryptoAddressBookManager.getCryptoAddressBookRecordByCryptoAddress(transaction.getCryptoTransaction().getInformation().getAddressTo());
                ReferenceWallet         referenceWallet         = cryptoAddressBookRecord.getWalletType();
                String                  walletPublicKey         = cryptoAddressBookRecord.getWalletPublicKey();

                com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.IncomingIntraUserTransactionExecutorFactory executorFactory = new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.IncomingIntraUserTransactionExecutorFactory(this.cryptoWalletManager, this.cryptoAddressBookManager,
                                                                                                                                                                                                                                                                                                                                                            this.eventManager,
                                                                                                                                                                                                                                                                                                                                                            cryptoTransmissionNetworkServiceManager,
                                                                                                                                                                                                                                                                                                                                                              lossProtectedWalletManager);
                com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.interfaces.TransactionExecutor executor        = executorFactory.newTransactionExecutor(referenceWallet, walletPublicKey);

                executor.executeTransaction(transaction);

                launchIncomingMoneyNotificationEvent(cryptoAddressBookRecord, transaction.getCryptoTransaction());

            } catch (CryptoAddressBookRecordNotFoundException exception) {
                //TODO LUIS we should define what is going to happen in this case, in the meantime we throw an exception
                throw new CantGetCryptoAddressBookRecordException(CantGetCryptoAddressBookRecordException.DEFAULT_MESSAGE, exception, "", "Check the cause to see what happened");
            }
        } catch (com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantExecuteTransactionException | CantGetCryptoAddressBookRecordException | CantLoadWalletsException e){
            throw e;
        } catch (Exception exception) {
            //TODO LUIS we should define what is going to happen in this case, in the meantime we throw an exception
            throw new CantGetCryptoAddressBookRecordException(CantGetCryptoAddressBookRecordException.DEFAULT_MESSAGE, exception, "", "Check the cause to see what happened");
        }
    }

    private void launchIncomingMoneyNotificationEvent(CryptoAddressBookRecord cryptoAddressBookRecord,Transaction<CryptoTransaction> transaction) {

        FermatBundle fermatBundle = new FermatBundle();
        fermatBundle.put(SOURCE_PLUGIN, Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION.getCode());
        try {
            fermatBundle.put(APP_NOTIFICATION_PAINTER_FROM, new Owner(WalletsPublicKeys.getByCode(cryptoAddressBookRecord.getWalletPublicKey()).getCode()));
            fermatBundle.put(APP_TO_OPEN_PUBLIC_KEY, WalletsPublicKeys.getByCode(cryptoAddressBookRecord.getWalletPublicKey()).getCode());
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }


        fermatBundle.put(NOTIFICATION_ID, CCPBroadcasterConstants.TRANSACTION_ARRIVE);
        fermatBundle.put(APP_ACTIVITY_TO_OPEN_CODE, Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN.getCode());
        fermatBundle.put("InvolvedActor",cryptoAddressBookRecord.getDeliveredByActorPublicKey());
        fermatBundle.put("Amount", transaction.getInformation().getCryptoAmount());

        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE,fermatBundle);


    }
}
