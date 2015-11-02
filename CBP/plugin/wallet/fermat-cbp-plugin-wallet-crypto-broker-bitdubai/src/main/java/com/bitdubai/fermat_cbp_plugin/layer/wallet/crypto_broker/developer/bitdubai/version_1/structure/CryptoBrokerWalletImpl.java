package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.Stock;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.WalletTransaction;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantPerformTransactionException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantAddStockCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantAddDebitCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jorge on 26-10-2015.
 */
public class CryptoBrokerWalletImpl implements CryptoBrokerWallet {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 5147;
    private static final int HASH_PRIME_NUMBER_ADD = 4789;

    private final KeyPair walletKeyPair;
    private final String ownerPublicKey;
    private final ConcurrentHashMap<FermatEnum, Stock> stockMap;
    private final CryptoBrokerWalletDatabaseDao databaseDao;

    public CryptoBrokerWalletImpl(final KeyPair walletKeyPair, final String ownerPublicKey, final CryptoBrokerWalletDatabaseDao databaseDao){
        this.walletKeyPair = walletKeyPair;
        this.ownerPublicKey = ownerPublicKey;
        this.databaseDao = databaseDao;
        stockMap = new ConcurrentHashMap<>();

    }

    @Override
    public String getWalletPublicKey() { return this.walletKeyPair.getPublicKey(); }

    @Override
    public String getOwnerPublicKey() {return this.ownerPublicKey;}

    @Override
    public void addStock(FermatEnum stockType)  throws CantAddStockCryptoBrokerWalletException {
        try {
            StockTransaction stockRecordBook = stockRecord(stockType, BalanceType.BOOK);
            StockTransaction stockRecordAvailable = stockRecord(stockType, BalanceType.AVAILABLE);
            CryptoBrokerStock stock = new CryptoBrokerStock(stockType, this.walletKeyPair,this.databaseDao);
            stock.addDebit(stockRecordBook);
            stock.addDebit(stockRecordAvailable);
        } catch (CantAddDebitCryptoBrokerWalletException e) {
            throw new CantAddStockCryptoBrokerWalletException("CRYPTO BROKER WALLET", e, "CAN'T ADD STOCK CRYPTO BROKER WALLET", "");
        } catch (Exception e) {
            throw new CantAddStockCryptoBrokerWalletException("CRYPTO BROKER WALLET", e, "CAN'T ADD STOCK CRYPTO BROKER WALLET", "");
        }
    }

    @Override
    public Stock getStock(FermatEnum stockType) {
        return null;
    }

    @Override
    public Collection<Stock> getStocks() { return null; }

    @Override
    public void performTransaction(WalletTransaction transaction) { }

    public boolean equals(Object o){
        if(!(o instanceof CryptoBrokerStockTransactionRecord))
            return false;
        CryptoBrokerStockTransactionRecord compare = (CryptoBrokerStockTransactionRecord) o;
        return ownerPublicKey.equals(compare.getOwnerPublicKey()) && walletKeyPair.getPublicKey().equals(compare.getWalletPublicKey());
    }

    @Override
    public int hashCode(){
        int c = 0;
        c += ownerPublicKey.hashCode();
        c += walletKeyPair.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    private StockTransaction stockRecord(FermatEnum stockType, BalanceType balanceType){
        UUID transactionId = UUID.randomUUID();
        //TODO VALOR DE FermatEnum stockType
        CurrencyType currencyType = CurrencyType.CRYPTO_MONEY;
        long timestamp = 0;
        String memo = "";
        StockTransaction record = new CryptoBrokerStockTransactionRecordImpl(
                transactionId,
                this.walletKeyPair,
                this.ownerPublicKey,
                balanceType,
                TransactionType.DEBIT,
                currencyType,
                0,
                0,
                0,
                timestamp,
                memo
        );
        return record;
    }
}
