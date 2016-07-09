package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CryptoTransactionStatus;
import com.bitdubai.fermat_ccp_api.all_definition.util.AbstractHoldingTransactionAgent;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.unhold.interfaces.CryptoUnholdTransaction;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.UnHoldCryptoMoneyTransactionPluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.database.UnHoldCryptoMoneyTransactionDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.structure.UnHoldCryptoMoneyTransactionManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.utils.CryptoWalletTransactionRecordImpl;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/07/16.
 */
public class UnHoldCryptoMoneyTransactionMonitorAgent2 extends
        AbstractHoldingTransactionAgent<UnHoldCryptoMoneyTransactionPluginRoot> {

    private final UnHoldCryptoMoneyTransactionManager unHoldCryptoMoneyTransactionManager;
    private final CryptoWalletManager cryptoWalletManager;

    public UnHoldCryptoMoneyTransactionMonitorAgent2(
            long sleepTime,
            TimeUnit timeUnit,
            long initDelayTime,
            UnHoldCryptoMoneyTransactionPluginRoot pluginRoot,
            UnHoldCryptoMoneyTransactionManager unHoldCryptoMoneyTransactionManager,
            CryptoWalletManager cryptoWalletManager) {
        super(sleepTime, timeUnit, initDelayTime, pluginRoot);
        this.unHoldCryptoMoneyTransactionManager = unHoldCryptoMoneyTransactionManager;
        this.cryptoWalletManager = cryptoWalletManager;
    }

    @Override
    protected void doTheMainTask() {
        // I define the filter
        DatabaseTableFilter filter = new DatabaseTableFilter() {
            @Override
            public void setColumn(String column) {

            }

            @Override
            public void setType(DatabaseFilterType type) {

            }

            @Override
            public void setValue(String value) {

            }

            @Override
            public String getColumn() {
                return UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_STATUS_COLUMN_NAME;
            }

            @Override
            public String getValue() {
                return CryptoTransactionStatus.ACKNOWLEDGED.getCode();
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };

        try {
            //I comment this line, is not used right now
            //long availableBalance = 0;
            for (CryptoUnholdTransaction cryptoUnholdTransaction : unHoldCryptoMoneyTransactionManager.getUnHoldCryptoMoneyTransactionList(filter)){
                //TODO: Buscar el saldo disponible en la Bitcoin Wallet
                //I comment this line, is not used right now
                //availableBalance = cryptoWalletManager.loadWallet(cryptoUnholdTransaction.getPublicKeyWallet()).getBalance(BalanceType.AVAILABLE).getBalance(cryptoUnholdTransaction.getBlockchainNetworkType());

                //TODO: Este if esta mal, el availableBalance no tiene nada que ver con permitir o no un unhold, pues un unhold es un credito.
                //if(availableBalance >= cryptoUnholdTransaction.getAmount()) {
                //TODO: llamar metodo Hold de la wallet que se implementara luego que se pruebe las wallet CSH y BNK;

                long total = 0;

                FeeOrigin feeOrigin = cryptoUnholdTransaction.getFeeOrigin();
                if(feeOrigin==null){
                    feeOrigin = FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS;
                }

                long longBitcoinFee = cryptoUnholdTransaction.getFee();
                if(longBitcoinFee< BitcoinFee.SLOW.getFee()){
                    longBitcoinFee = BitcoinFee.SLOW.getFee();
                }

                if( feeOrigin.equals(FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS))
                    total = (long)cryptoUnholdTransaction.getAmount() + cryptoUnholdTransaction.getFee();
                else
                    total = (long)cryptoUnholdTransaction.getAmount() - cryptoUnholdTransaction.getFee();

                CryptoAddress cryptoAddress = new CryptoAddress(cryptoUnholdTransaction.getPublicKeyActor(), CryptoCurrency.BITCOIN);
                CryptoWalletTransactionRecordImpl bitcoinWalletTransactionRecord = new CryptoWalletTransactionRecordImpl(UUID.randomUUID(),
                        cryptoUnholdTransaction.getTransactionId(),
                        cryptoUnholdTransaction.getPublicKeyActor(),
                        cryptoUnholdTransaction.getPublicKeyActor(),
                        Actors.CBP_CRYPTO_BROKER,
                        Actors.CBP_CRYPTO_BROKER,
                        cryptoUnholdTransaction.getTransactionId().toString(), //Hash
                        cryptoAddress, //addressFrom
                        cryptoAddress, //addressTo
                        (long)cryptoUnholdTransaction.getAmount(),
                        new Date().getTime(),
                        cryptoUnholdTransaction.getMemo(),
                        cryptoUnholdTransaction.getBlockchainNetworkType(), cryptoUnholdTransaction.getCurrency(),
                        longBitcoinFee,
                        feeOrigin, total);

                cryptoWalletManager.loadWallet(cryptoUnholdTransaction.getPublicKeyWallet()).getBalance(BalanceType.AVAILABLE).credit(bitcoinWalletTransactionRecord);
                cryptoUnholdTransaction.setStatus(CryptoTransactionStatus.CONFIRMED);
                cryptoUnholdTransaction.setTimestampAcknowledged(new Date().getTime() / 1000);
                unHoldCryptoMoneyTransactionManager.saveUnHoldCryptoMoneyTransactionData(cryptoUnholdTransaction);
                //}else{
                //    cryptoUnholdTransaction.setStatus(CryptoTransactionStatus.REJECTED);
                //    cryptoUnholdTransaction.setTimestampConfirmedRejected(new Date().getTime() / 1000);
                //    cryptoUnholdTransaction.setMemo("REJECTED AVAILABLE BALANCE");
                //    cryptoUnholdTransaction.setBlockChainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                //    unHoldCryptoMoneyTransactionManager.saveUnHoldCryptoMoneyTransactionData(cryptoUnholdTransaction);
                //}
            }
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }
}
