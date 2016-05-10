package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.loss_protected_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;

import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;


import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletBalance;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletTransactionRecord;

import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.all_definition.utils.CurrencyPairImpl;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;

import java.util.UUID;

/**
 * Created by ciencias on 7/6/15.
 *
 */
public class BitcoinWalletLossProtectedWalletAvailableBalance implements BitcoinLossProtectedWalletBalance {

    /**
     * BitcoinWalletBasicWallet member variables.
     */
    private Database database;
    private BitcoinWalletLossProtectedWalletDao bitcoinWalletBasicWalletDao;

    private Broadcaster broadcaster;

    private CurrencyExchangeProviderFilterManager exchangeProviderFilterManagerproviderFilter;

   private UUID exchangeProviderId;


    CurrencyPair wantedCurrencyPair = new CurrencyPairImpl(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR);
    CurrencyExchangeRateProviderManager rateProviderManager;


    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */

    /**
     * Constructor.
     */
    public BitcoinWalletLossProtectedWalletAvailableBalance(final Database database, final Broadcaster broadcaster, final UUID exchangeProviderId,final CurrencyExchangeProviderFilterManager exchangeProviderFilterManagerproviderFilter){
        this.database = database;
        this.broadcaster = broadcaster;
        this.exchangeProviderId = exchangeProviderId;
        this.exchangeProviderFilterManagerproviderFilter = exchangeProviderFilterManagerproviderFilter;

        try {
            rateProviderManager = exchangeProviderFilterManagerproviderFilter.getProviderReference(exchangeProviderId);
        } catch (CantGetProviderException e) {
            e.printStackTrace();
        }
    }


    @Override
    public long getRealBalance(BlockchainNetworkType blockchainNetworkType) throws CantCalculateBalanceException {
        try {
            bitcoinWalletBasicWalletDao = new BitcoinWalletLossProtectedWalletDao(this.database);
            return bitcoinWalletBasicWalletDao.getAvailableBalance(blockchainNetworkType);
        } catch(CantCalculateBalanceException exception){
            throw exception;
        } catch(Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception  ), null, null);
        }
    }


    @Override
    public long getBalance(BlockchainNetworkType blockchainNetworkType, String exchangeRate) throws CantCalculateBalanceException {
        try {
            //calculate how many btc can spend based on the exchangeRate

            bitcoinWalletBasicWalletDao = new BitcoinWalletLossProtectedWalletDao(this.database);

            return bitcoinWalletBasicWalletDao.getAvailableBalance(blockchainNetworkType,exchangeRate);


        } catch(CantListTransactionsException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception  ), null, null);

        } catch(Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception  ), null, null);
        }
    }

    @Override
    public long getBalance(BlockchainNetworkType blockchainNetworkType) throws CantCalculateBalanceException {
        try {
            bitcoinWalletBasicWalletDao = new BitcoinWalletLossProtectedWalletDao(this.database);
            return bitcoinWalletBasicWalletDao.getAvailableBalance(blockchainNetworkType);
        } catch(CantCalculateBalanceException exception){
            throw exception;
        } catch(Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception  ), null, null);
        }
    }

    /*d
        * NOTA:
        *  El debit y el credit debería mirar primero si la transacción que
        *  se quiere aplicar existe. Si no existe aplica los cambios normalmente, pero si existe
        *  debería ignorar la transacción.
        */


    @Override
    public void debit(BitcoinLossProtectedWalletTransactionRecord cryptoTransaction) throws CantRegisterDebitException {
        try {

            double purchasePrice = 0;

            bitcoinWalletBasicWalletDao = new BitcoinWalletLossProtectedWalletDao(this.database);
            bitcoinWalletBasicWalletDao.addDebit(cryptoTransaction, BalanceType.AVAILABLE, String.valueOf(purchasePrice),exchangeProviderFilterManagerproviderFilter, exchangeProviderId,rateProviderManager);
            //broadcaster balance amount
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, "BalanceChange");
            //get exchange rate on background
            //and insert spendings
            setActualExchangeRate(TransactionType.DEBIT,cryptoTransaction);
        } catch(CantRegisterDebitException exception){
            throw exception;
        } catch(Exception exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }



    @Override
    public void credit(BitcoinLossProtectedWalletTransactionRecord cryptoTransaction) throws CantRegisterCreditException {
        try {

            double purchasePrice = 0;

            bitcoinWalletBasicWalletDao = new BitcoinWalletLossProtectedWalletDao(this.database);
            bitcoinWalletBasicWalletDao.addCredit(cryptoTransaction, BalanceType.AVAILABLE,String.valueOf(purchasePrice));

            //calculate value blocks to spend and inserted into the table

            //broadcaster balance amount
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, "BalanceChange");

            //get exchange rate on background
            setActualExchangeRate(TransactionType.CREDIT,cryptoTransaction);
        } catch(CantRegisterCreditException exception){
            throw exception;
        } catch(Exception exception){
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }



    @Override
    public void revertCredit(BitcoinLossProtectedWalletTransactionRecord cryptoTransaction) throws CantRegisterCreditException {
        try {
            bitcoinWalletBasicWalletDao = new BitcoinWalletLossProtectedWalletDao(this.database);
            bitcoinWalletBasicWalletDao.revertCredit(cryptoTransaction, BalanceType.AVAILABLE);


        } catch(Exception exception){
            throw new CantRegisterCreditException("CANT REVERT CREDIT EN AVAILABLE", FermatException.wrapException(exception), null, null);
        }
    }

    private void setActualExchangeRate(final TransactionType transactionType,final BitcoinLossProtectedWalletTransactionRecord transactionRecord)
    {
        final ExchangeRate[] rate = new ExchangeRate[1];
        try {

            //get setting exchange provider manager
            //update transaction rate

            final UUID rateProviderManagerId = exchangeProviderId;

            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                          //your exchange rate.
                        rate[0] = rateProviderManager.getCurrentExchangeRate(wantedCurrencyPair);

                        //update transaction record
                        bitcoinWalletBasicWalletDao = new BitcoinWalletLossProtectedWalletDao(database);
                        bitcoinWalletBasicWalletDao.updateTransactionRate(transactionRecord.getTransactionId(), rate[0].getPurchasePrice());

                        //insert transaction spendings
                        //calculate chunck values spent
                        if(transactionType.getCode().equals(TransactionType.DEBIT.getCode()))
                            bitcoinWalletBasicWalletDao.insertSpending(transactionRecord, String.valueOf(rate[0].getPurchasePrice()));

                    } catch (CantGetExchangeRateException e) {
                        e.printStackTrace();

                    } catch (UnsupportedCurrencyPairException e) {
                        e.printStackTrace();

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
