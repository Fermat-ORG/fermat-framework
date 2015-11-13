package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.Stock;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockTransaction;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantAddCreditCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantAddDebitCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantGetAvailableBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantGetBookedBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantAddDebitException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantAddCreditException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseDao;

/**
 * Created by jorge on 26-10-2015.
 * Modified by Yordin Alayn 27.10.15
 */
public class CryptoBrokerStock implements Stock {

    private final FermatEnum stockType;
    private KeyPair walletKeys;
    private final CryptoBrokerWalletDatabaseDao databaseDao;

    public CryptoBrokerStock(final FermatEnum stockType, final KeyPair walletKeys ,final CryptoBrokerWalletDatabaseDao databaseDao){
        this.stockType = stockType;
        this.walletKeys = walletKeys;
        this.databaseDao = databaseDao;
    }

    @Override
    public float getBookedBalance() throws CantGetBookedBalanceCryptoBrokerWalletException {
        try {
            return databaseDao.getCalculateBookBalance(this.stockType,this.walletKeys.getPublicKey());
        } catch (CantCalculateBalanceException e) {
            throw new CantGetBookedBalanceCryptoBrokerWalletException("CAN'T GET CRYPTO BROKER WALLET BOOKED BALANCE", e, "", "");
        } catch (Exception e) {
            throw new CantGetBookedBalanceCryptoBrokerWalletException("CAN'T GET CRYPTO BROKER WALLET BOOKED BALANCE", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public float getAvailableBalance() throws CantGetAvailableBalanceCryptoBrokerWalletException {
        try {
            return databaseDao.getCalculateAvailableBalance(this.stockType,this.walletKeys.getPublicKey());
        } catch (CantCalculateBalanceException e) {
            throw new CantGetAvailableBalanceCryptoBrokerWalletException("CAN'T GET CRYPTO BROKER WALLET BOOKED BALANCE", e, "", "");
        } catch (Exception e) {
            throw new CantGetAvailableBalanceCryptoBrokerWalletException("CAN'T GET CRYPTO BROKER WALLET BOOKED BALANCE", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void addDebit(StockTransaction transaction) throws CantAddDebitCryptoBrokerWalletException {
        try {
            databaseDao.addDebit(transaction);
        } catch (CantAddDebitException e) {
            throw new CantAddDebitCryptoBrokerWalletException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", e, "", "");
        } catch (Exception e) {
            throw new CantAddDebitCryptoBrokerWalletException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void addCredit(StockTransaction transaction) throws CantAddCreditCryptoBrokerWalletException {
        try {
            databaseDao.addCredit(transaction);
        } catch (CantAddCreditException e) {
            throw new CantAddCreditCryptoBrokerWalletException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", e, "", "");
        } catch (Exception e) {
            throw new CantAddCreditCryptoBrokerWalletException("CAN'T ADD CRYPTO BROKER WALLET TRANSACTION DEBIT", FermatException.wrapException(e), "", "");
        }
    }
}
