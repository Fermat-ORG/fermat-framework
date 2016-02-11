package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;

import java.math.BigDecimal;

/**
 * Created by franklin on 30/11/15.
 */
public interface CryptoBrokerWalletBalanceRecord {
    //TODO: Documentar y excepciones
//    String getExternalWalletPublicKey();
//    void   setExternalWalletPublicKey(String walletPublicKey);
//

    /**
     * The method <code>getBrokerPublicKey</code> returns the broker public key of the CryptoBrokerWalletBalanceRecord
     *
     * @return an String of the broker public key
     */
    String getBrokerPublicKey();

    /**
     * The method <code>setBrokerPublicKey</code> sets the broker public key of the CryptoBrokerWalletBalanceRecord
     *
     * @param brokerPublicKey
     */
    void setBrokerPublicKey(String brokerPublicKey);

//    FiatCurrency getFiatCurrency();
//    void         setFiatCurrency(FiatCurrency fiatCurrency);

    /**
     * The method <code>getMerchandise</code> returns the merchandise of the CryptoBrokerWalletBalanceRecord
     *
     * @return a FermatEnum of the merchandise
     */
    FermatEnum getMerchandise();

    /**
     * The method <code>setMerchandise</code> sets the merchandise of the CryptoBrokerWalletBalanceRecord
     *
     * @param merchandise
     */
    void setMerchandise(FermatEnum merchandise);

    /**
     * The method <code>getBookBalance</code> returns the book balance of the CryptoBrokerWalletBalanceRecord
     *
     * @return a BigDecimal of the book balance
     */
    BigDecimal getBookBalance();

    /**
     * The method <code>setBookBalance</code> sets the book balance of the CryptoBrokerWalletBalanceRecord
     *
     * @param bookBalance
     */
    void setBookBalance(BigDecimal bookBalance);

    /**
     * The method <code>getAvailableBalance</code> returns the available balance of the CryptoBrokerWalletBalanceRecord
     *
     * @return a BigDecimal of the available balance
     */
    BigDecimal getAvailableBalance();

    /**
     * The method <code>setAvailableBalance</code> sets the available balance of the CryptoBrokerWalletBalanceRecord
     *
     * @param availableBalance
     */
    void setAvailableBalance(BigDecimal availableBalance);

    /**
     * The method <code>getMoneyType</code> returns the currency type of the CryptoBrokerWalletBalanceRecord
     *
     * @return MoneyType
     */
    MoneyType getMoneyType();

    /**
     * The method <code>setMoneyType</code> sets the currency type of the CryptoBrokerWalletBalanceRecord
     *
     * @param moneyType
     */
    void setMoneyType(MoneyType moneyType);

}
