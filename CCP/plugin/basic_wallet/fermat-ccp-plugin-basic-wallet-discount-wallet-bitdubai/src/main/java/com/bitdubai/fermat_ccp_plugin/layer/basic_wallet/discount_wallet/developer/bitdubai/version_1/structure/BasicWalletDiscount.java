package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.CryptoIndexManager;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.DealsWithCryptoIndex;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums.CryptoValueChunkStatus;
//import com.bitdubai.fermat_api.layer.basic_wallet.discount_wallet.exceptions.AvailableFailedException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.CalculateDiscountFailedException;
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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by eze on 03/05/15.
 */
class BasicWalletDiscount implements DealsWithCryptoIndex, DealsWithErrors {

    /**
     * DealsWithCryptoIndex Interface member variables.
     */
    private CryptoIndexManager cryptoIndexManager;


    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;


    /*
     * BasicWalletDiscount member variables
     */
    private Database database;
    private FiatCurrency fiatCurrency;
    private CryptoCurrency cryptoCurrency;

    /*
     * BasicWalletDiscount constructor
     */
    BasicWalletDiscount(FiatCurrency fiatCurrency, CryptoCurrency cryptoCurrency){
        this.fiatCurrency = fiatCurrency;
        this.cryptoCurrency = cryptoCurrency;
    }

    /*
     * BasicWalletDiscount methods implementation
     */
    void setDatabase(Database database) {
        this.database = database;
    }

    /*
    * Given a fiat amount and its equivalent crypto amount this methods calculates the
    * discount that would be produced if the user debit the same amounts.
    * The return value represents fiat currency.
    */
    long calculateDiscount(long fiatAmount, long cryptoAmount) throws CalculateDiscountFailedException {

        long spent = 0;

        BasicWalletAvailable basicWalletAvailable = new BasicWalletAvailable(this.fiatCurrency, this.cryptoCurrency);
        basicWalletAvailable.setCryptoIndexManager(this.cryptoIndexManager);
        basicWalletAvailable.setErrorManager(this.errorManager);
        basicWalletAvailable.setDatabase(this.database);
        long available;

        try {
            available = basicWalletAvailable.getAvailableAmount();
        } catch (CantCalculateAvailableAmountException e) {
            System.err.println("AvailableFailedException" + e.getMessage());
            e.printStackTrace();
            throw new CalculateDiscountFailedException();
        }

        if(available < fiatAmount) {
            System.err.println("Not enough funds");
            throw new CalculateDiscountFailedException();
        }

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
            throw new CalculateDiscountFailedException();
        }


        /* We first sort the chunks of the list putting the chunks generated
         * at lower exchange rate at the beginning of the list. The exchange
         * rate of a chunk (fa,ca,state) is equal to the quotient fa/ca. (see documentation)
         */
        List<DatabaseTableRecord> recordList = valueChunksTable.getRecords();


        Collections.sort(recordList, new ChunksComparator());


        /* Let's calculate the discount as explained in the
         * documentation. we will add up the fiat amounts stored
         * in the chunks that would be used to pay the extraction
         * equivalent to cryptoAmount
         */
        for(DatabaseTableRecord d: recordList){
            long fa = d.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME);
            long ca = d.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME);

            if(cryptoAmount == 0)
                break;

            if(cryptoAmount >= ca) {
                cryptoAmount = cryptoAmount - ca;
                spent = spent + fa;
            }else{
                long fa1 = Converter.getProportionalFiatAmountRoundedDown(ca,fa,cryptoAmount);
                cryptoAmount = 0;
                spent = spent + fa1;
            }
        }

        return fiatAmount - spent;
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


    /*
     * Private member variables
     */
    private class ChunksComparator implements Comparator<DatabaseTableRecord>{
        @Override
        public int compare(DatabaseTableRecord databaseTableRecord1, DatabaseTableRecord databaseTableRecord2) {

            long chunkFiatAmount1 = databaseTableRecord1.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME);
            long chunkCryptoAmount1 = databaseTableRecord1.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME);

            long chunkFiatAmount2 = databaseTableRecord2.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME);
            long chunkCryptoAmount2 = databaseTableRecord2.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME);

            double rate1 = ((double) chunkFiatAmount1) / chunkCryptoAmount1;
            double rate2 = ((double) chunkFiatAmount2) / chunkCryptoAmount2;

            return Double.compare(rate1,rate2);
        }
    }
}
