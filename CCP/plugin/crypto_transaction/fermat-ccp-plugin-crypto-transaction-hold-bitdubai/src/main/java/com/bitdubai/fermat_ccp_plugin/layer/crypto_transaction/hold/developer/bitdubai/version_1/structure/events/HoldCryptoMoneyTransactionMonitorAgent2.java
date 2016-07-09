package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
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
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransaction;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.HoldCryptoMoneyTransactionPluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.database.HoldCryptoMoneyTransactionDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.structure.HoldCryptoMoneyTransactionManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.utils.CryptoWalletTransactionRecordImpl;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/07/16.
 */
public class HoldCryptoMoneyTransactionMonitorAgent2
        extends AbstractHoldingTransactionAgent<HoldCryptoMoneyTransactionPluginRoot> {

    private final HoldCryptoMoneyTransactionManager holdCryptoMoneyTransactionManager;
    private final CryptoWalletManager cryptoWalletManager;

    public HoldCryptoMoneyTransactionMonitorAgent2(
            long sleepTime,
            TimeUnit timeUnit,
            long initDelayTime,
            HoldCryptoMoneyTransactionPluginRoot pluginRoot,
            HoldCryptoMoneyTransactionManager holdCryptoMoneyTransactionManager,
            CryptoWalletManager cryptoWalletManager) {
        super(sleepTime, timeUnit, initDelayTime, pluginRoot);
        this.holdCryptoMoneyTransactionManager = holdCryptoMoneyTransactionManager;
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
                return HoldCryptoMoneyTransactionDatabaseConstants.HOLD_STATUS_COLUMN_NAME;
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
            long availableBalance = 0;
            for (CryptoHoldTransaction cryptoHoldTransaction : holdCryptoMoneyTransactionManager.getHoldCryptoMoneyTransactionList(filter)){
                //TODO: Buscar el saldo disponible en la Bitcoin Wallet
                BlockchainNetworkType blockchainNetworkType = cryptoHoldTransaction.getBlockchainNetworkType();
                if(blockchainNetworkType==null){
                    blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
                }
                availableBalance = cryptoWalletManager.loadWallet(cryptoHoldTransaction.getPublicKeyWallet()).getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);

                if(availableBalance >= cryptoHoldTransaction.getAmount()) {
                    //TODO: llamar metodo Hold de la wallet que se implementara luego que se pruebe las wallet CSH y BNK;

                    long total = 0;

                    FeeOrigin feeOrigin = cryptoHoldTransaction.getFeeOrigin();
                    if(feeOrigin==null){
                        feeOrigin = FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS;
                    }

                    long longBitcoinFee = cryptoHoldTransaction.getFee();
                    if(longBitcoinFee< BitcoinFee.SLOW.getFee()){
                        longBitcoinFee = BitcoinFee.SLOW.getFee();
                    }

                    if(cryptoHoldTransaction.getFeeOrigin().equals(FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS))
                        total = (long)cryptoHoldTransaction.getAmount() + cryptoHoldTransaction.getFee();
                    else
                        total = (long)cryptoHoldTransaction.getAmount() - cryptoHoldTransaction.getFee();

                    CryptoAddress cryptoAddress = new CryptoAddress(cryptoHoldTransaction.getPublicKeyActor(), CryptoCurrency.BITCOIN);
                    CryptoWalletTransactionRecordImpl bitcoinWalletTransactionRecord = new CryptoWalletTransactionRecordImpl(UUID.randomUUID(),
                            cryptoHoldTransaction.getTransactionId(),
                            cryptoHoldTransaction.getPublicKeyActor(),
                            cryptoHoldTransaction.getPublicKeyActor(),
                            Actors.CBP_CRYPTO_BROKER,
                            Actors.CBP_CRYPTO_BROKER,
                            cryptoHoldTransaction.getTransactionId().toString(), //Hash
                            cryptoAddress, //addressFrom
                            cryptoAddress, //addressTo
                            (long)cryptoHoldTransaction.getAmount(),
                            new Date().getTime(),
                            cryptoHoldTransaction.getMemo(),
                            cryptoHoldTransaction.getBlockchainNetworkType(), cryptoHoldTransaction.getCurrency(),
                            feeOrigin,
                            longBitcoinFee, total); //TODO:Esto debe venir en la transaccion que a su vez se le debe pasar desde la Crypto Broker Wallet

                    cryptoWalletManager.loadWallet(cryptoHoldTransaction.getPublicKeyWallet()).getBalance(BalanceType.AVAILABLE).debit(bitcoinWalletTransactionRecord);
                    cryptoHoldTransaction.setStatus(CryptoTransactionStatus.CONFIRMED);
                    cryptoHoldTransaction.setTimestampAcknowledged(new Date().getTime() / 1000);
                    holdCryptoMoneyTransactionManager.saveHoldCryptoMoneyTransactionData(cryptoHoldTransaction);
                }else{
                    cryptoHoldTransaction.setStatus(CryptoTransactionStatus.REJECTED);
                    cryptoHoldTransaction.setMemo("REJECTED AVAILABLE BALANCE");
                    cryptoHoldTransaction.setTimestampConfirmedRejected(new Date().getTime() / 1000);
                    holdCryptoMoneyTransactionManager.saveHoldCryptoMoneyTransactionData(cryptoHoldTransaction);
                }
            }
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }
}
