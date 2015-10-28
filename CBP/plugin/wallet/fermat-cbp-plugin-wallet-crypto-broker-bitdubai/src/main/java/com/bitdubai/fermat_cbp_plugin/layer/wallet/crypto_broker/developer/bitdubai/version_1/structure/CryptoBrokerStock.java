package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.Stock;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockTransaction;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantGetAddCreditCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantGetAddDebitCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantGetAvailableBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantGetBookedBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantAddDebitException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantAddCreditException;
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
    public float getBookedBalance() throws CantGetBookedBalanceCryptoBrokerWalletException{
        //esto se tiene que responder con una consulta al DAO para obtener el booked balance
        try{
            return databaseDao.getCalculateBookBalance();
        } catch (CantCalculateBalanceException e) {
            throw new CantGetBookedBalanceCryptoBrokerWalletException("CAN'T GET CRYPTO BROKER WALLET BOOKED BALANCE", e, "", "");
        } catch (Exception e) {
            throw new CantGetBookedBalanceCryptoBrokerWalletException("CAN'T GET CRYPTO BROKER WALLET BOOKED BALANCE", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public float getAvailableBalance() throws CantGetAvailableBalanceCryptoBrokerWalletException{
        //esto se tiene que responder con una consulta al DAO para obtener el available balance
        try{
            return databaseDao.getCalculateBookBalance();
        } catch (CantCalculateBalanceException e) {
            throw new CantGetAvailableBalanceCryptoBrokerWalletException("CAN'T GET CRYPTO BROKER WALLET BOOKED BALANCE", e, "", "");
        } catch (Exception e) {
            throw new CantGetAvailableBalanceCryptoBrokerWalletException("CAN'T GET CRYPTO BROKER WALLET BOOKED BALANCE", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void addDebit(StockTransaction transaction) throws CantGetAddDebitCryptoBrokerWalletException{
        //aqui se crea un record a partir de la stock transaction y se le envia al DAO
        try{
            databaseDao.addDebit(transaction);
        } catch (CantAddDebitException e) {
            throw new CantGetAddDebitCryptoBrokerWalletException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", e, "", "");
        } catch (Exception e) {
            throw new CantGetAddDebitCryptoBrokerWalletException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void addCredit(StockTransaction transaction) throws CantGetAddCreditCryptoBrokerWalletException{
        //aqui se crea un record a partir de la stock transaction y se le envia al DAO
        try{
            databaseDao.addCredit(transaction);
        } catch (CantAddCreditException e) {
            throw new CantGetAddCreditCryptoBrokerWalletException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", e, "", "");
        } catch (Exception e) {
            throw new CantGetAddCreditCryptoBrokerWalletException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", FermatException.wrapException(e), "", "");
        }
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
