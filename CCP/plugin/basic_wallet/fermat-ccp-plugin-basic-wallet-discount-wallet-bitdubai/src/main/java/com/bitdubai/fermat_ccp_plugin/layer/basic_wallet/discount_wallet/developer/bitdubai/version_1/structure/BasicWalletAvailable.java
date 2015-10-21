package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.CryptoIndexManager;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.DealsWithCryptoIndex;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.CryptoCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.FiatCurrencyNotSupportedException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums.CryptoValueChunkStatus;
//import com.bitdubai.fermat_api.layer.basic_wallet.discount_wallet.exceptions.AvailableFailedException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.CantCalculateAvailableAmountException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.util.Converter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;


/**
 * Created by eze on 28/04/15.
 */

/*
 * The purpose of this class is to encapsulate the
 * algorithm that calculates the wallet available funds.
 */
class BasicWalletAvailable implements DealsWithCryptoIndex, DealsWithErrors {


    /**
     * DealsWithCryptoIndex Interface member variables.
     */
    private CryptoIndexManager cryptoIndexManager;


    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /*
     * BasicWalletAvailable member variables
     */
    private FiatCurrency fiatCurrency;
    private CryptoCurrency cryptoCurrency;
    private Database database;

    /*
     * Class constructor
     * PROCEDURE TO CREATE AN INSTANCE OF THIS CLASS:_
     * AFTER YOU CALL THE CONSTTRUCTOR YOU NEED TO CALL setCrypyoInderManager, setErrorManager and
     * setDatabase.
     * Example:
     *   BasicWalletAvailable basicWalletAvailable = new BasicWalletAvailable(this.fiatCurrency, this.cryptoCurrency);
     *   basicWalletAvailable.setCryptoIndexManager(this.cryptoIndexManager);
     *   basicWalletAvailable.setErrorManager(this.errorManager);
     *   basicWalletAvailable.setDatabase(this.database);
    */
    BasicWalletAvailable(FiatCurrency fiatCurrency, CryptoCurrency cryptoCurrency){
        this.fiatCurrency = fiatCurrency;
        this.cryptoCurrency = cryptoCurrency;
    }

    /*
     * BasicWalletAvailable methods
     */

    void setDatabase(Database database) {
        this.database = database;
    }

    /*
     * This wallet manage crypto currency to store the value of the user money.
     * The user never see this fact, he just see fiat money. So this wallet will
     * buy crypto currency when the user deposit new money.
     * The available funds is the sum of the fiat amounts that were not already spent
     * and were bought at a lower exchange rate than the actual rate.
     */
    long getAvailableAmount() throws CantCalculateAvailableAmountException {

        /*
         * This variables will be used later
         */
        long chunkFiatAmount;
        long chunkCryptoAmount;
        long currentFiatValue;
        boolean lower;

        double currentIndex;
        // we also need the actual index
        try {

            currentIndex = this.cryptoIndexManager.getMarketPrice(this.fiatCurrency, this.cryptoCurrency,System.currentTimeMillis());

        } catch (CryptoCurrencyNotSupportedException e) {
            /*
             * This wallet was created with the fiat Currency asked to the method.
             * This should be supported.
             */
            System.err.println("CryptoCurrencyNotSupportedException: " + e.getMessage());
            e.printStackTrace();
            /*
               this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
             */
            throw new CantCalculateAvailableAmountException();

        } catch (FiatCurrencyNotSupportedException e) {
            /*
             * This wallet was created with the crypto Currency asked to the method.
             * It should be supported.
             */
            System.err.println("FiatCurrencyNotSupportedException: " + e.getMessage());
            e.printStackTrace();
            /*
               this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
             */
            throw new CantCalculateAvailableAmountException();
        }

        // Here we will add the available money
        long availableAmount = 0;
        // Here we will add the balance of the wallet
        long balance = 0;

        DatabaseTable valueChunksTable = database.getTable(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_NAME);

        // We set the filter to get the UNSPENT chunks
        valueChunksTable.setStringFilter(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, CryptoValueChunkStatus.UNSPENT.getCode(), DatabaseFilterType.EQUAL);

        // now we apply the filter
        try {
            valueChunksTable.loadToMemory();
        }
        catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantLoadTableToMemoryException: " + cantLoadTableToMemory.getMessage());
            cantLoadTableToMemory.printStackTrace();
            throw new CantCalculateAvailableAmountException();
        }

        // Let's calculate the available amount
        for(DatabaseTableRecord d: valueChunksTable.getRecords()) {

            /* As mentioned in the documentation
             * availableAmount is the minimum between balance
             * and the sum of currentFiatAmount(ca_i) for the
             * unspent chunks created with an exchange rate below
             * the current one, i.e. the values chunks where it holds
             * that currentFiatAmount(ca_i) >= fa_1 and also s = UNSPENT.
             */

            // All the records in this loop are UNSPENT so we add to the balance
            balance += d.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME);

            // Lets check the remaining condition
            chunkFiatAmount = d.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME);
            chunkCryptoAmount = d.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME);
            currentFiatValue = Converter.getCurrentFiatAmountRoundedDown(currentIndex,chunkCryptoAmount);
            lower = currentFiatValue >= chunkFiatAmount;

            if (lower)
                availableAmount += currentFiatValue;
        }

        // Now, the user never sees more money that the balance, so we have to
        // return the minimum between balance and total
        if(balance < availableAmount)
            return balance;
        return availableAmount;
    }

    /**
     * DealsWithCryptoIndex Interface member variables.
     */
    @Override
    public void setCryptoIndexManager(CryptoIndexManager cryptoIndexManager) {
        this.cryptoIndexManager = cryptoIndexManager;
    }

    /**
     * DealsWithErrors Interface member variables.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager){this.errorManager = errorManager;}

}
