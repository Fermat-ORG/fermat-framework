package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.Stock;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseDao;

import java.util.UUID;

/**
 * Created by jorge on 26-10-2015.
 */
public class CryptoBrokerStock implements Stock {

    private final FermatEnum stockType;
    private final CryptoBrokerWalletDatabaseDao databaseDao;

    public CryptoBrokerStock(final FermatEnum stockType, final CryptoBrokerWalletDatabaseDao databaseDao){
        this.stockType = stockType;
        this.databaseDao = databaseDao;
    }

    @Override
    public float getBookedBalance() {
        //esto se tiene que responder con una consulta al DAO para obtener el booked balance
        return 0;
    }

    @Override
    public float getAvailableBalance() {
        //esto se tiene que responder con una consulta al DAO para obtener el available balance
        return 0;
    }

    @Override
    public void addDebit(StockTransaction transaction) {
        //aqui se crea un record a partir de la stock transaction y se le envia al DAO
    }

    @Override
    public void addCrebit(StockTransaction transaction) {
    //aqui se crea un record a partir de la stock transaction y se le envia al DAO
    }

    /*
    public CryptoBrokerStockTransactionRecord debit(String publickeyWalle, String publickeyBroker, String publicKeyCustomer, BalanceType balanceType, CurrencyType currencyType, float amount, String memo) throws CantRegisterDebitException {
        try {
            UUID transactionId              = UUID.randomUUID();
            KeyPair keyPairWallet           = AsymmetricCryptography.createKeyPair(publickeyWalle);
            KeyPair keyPairBroker           = AsymmetricCryptography.createKeyPair(publickeyBroker);
            KeyPair keyPairCustomer         = AsymmetricCryptography.createKeyPair(publicKeyCustomer);
            TransactionType transactionType = TransactionType.DEBIT;
            float availableAmount           = balanceType.equals(BalanceType.AVAILABLE) ? amount : 0L;
            float bookAmount                = balanceType.equals(BalanceType.BOOK) ? amount : 0L;
            float runningBookBalance        = cryptoBrokerWalletDatabaseDao.calculateBookRunningBalanceByAsset(-bookAmount, keyPairWallet.getPrivateKey());
            float runningAvailableBalance   = cryptoBrokerWalletDatabaseDao.calculateAvailableRunningBalanceByAsset(-availableAmount, keyPairWallet.getPrivateKey());
//            long timeStamp = Timestamp(long time);
            long timeStamp = 0;

            cryptoBrokerWalletDatabaseDao.addDebit(cryptoBrokerTransaction, balanceType, keyPairWallet.getPrivateKey());
            return cryptoBrokerTransaction;
        } catch (CantAddDebitException e) {
            throw new CantRegisterDebitException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", e, "", "");
        } catch (Exception e) {
            throw new CantRegisterDebitException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", FermatException.wrapException(e), "", "");
        }
    }

    public CryptoBrokerStockTransactionRecord credit(String publickeyWalle, String publickeyBroker, String publicKeyCustomer, BalanceType balanceType, CurrencyType currencyType, float amount, String memo) throws CantRegisterCreditException {
        try {
            UUID transactionId              = UUID.randomUUID();
            KeyPair keyPairWallet           = AsymmetricCryptography.createKeyPair(publickeyWalle);
            KeyPair keyPairBroker           = AsymmetricCryptography.createKeyPair(publickeyBroker);
            KeyPair keyPairCustomer         = AsymmetricCryptography.createKeyPair(publicKeyCustomer);
            TransactionType transactionType = TransactionType.DEBIT;
            float availableAmount           = balanceType.equals(BalanceType.AVAILABLE) ? amount : 0L;
            float bookAmount                = balanceType.equals(BalanceType.BOOK) ? amount : 0L;
            float runningBookBalance        = cryptoBrokerWalletDatabaseDao.calculateBookRunningBalanceByAsset(-bookAmount, keyPairWallet.getPrivateKey());
            float runningAvailableBalance   = cryptoBrokerWalletDatabaseDao.calculateAvailableRunningBalanceByAsset(-availableAmount, keyPairWallet.getPrivateKey());
//            long timeStamp = Timestamp(long time);
            long timeStamp = 0;
            CryptoBrokerStockTransactionRecord cryptoBrokerTransaction = new CryptoBrokerStockTransactionRecordImpl(
                    transactionId,
                    keyPairWallet,
                    keyPairBroker,
                    keyPairCustomer,
                    balanceType,
                    transactionType,
                    currencyType,
                    amount,
                    runningBookBalance,
                    runningAvailableBalance,
                    timeStamp,
                    memo
            );
            cryptoBrokerWalletDatabaseDao.addCredit(cryptoBrokerTransaction, balanceType, keyPairWallet.getPrivateKey());
            return cryptoBrokerTransaction;
        } catch (CantAddCreditException e) {
            throw new CantRegisterCreditException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", e, "", "");
        } catch (Exception e) {
            throw new CantRegisterCreditException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", FermatException.wrapException(e), "", "");
        }
    }
     */
}
