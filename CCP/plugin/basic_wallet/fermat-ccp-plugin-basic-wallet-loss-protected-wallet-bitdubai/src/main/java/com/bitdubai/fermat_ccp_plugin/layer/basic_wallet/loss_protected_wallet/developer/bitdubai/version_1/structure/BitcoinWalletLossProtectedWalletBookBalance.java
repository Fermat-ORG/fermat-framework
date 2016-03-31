package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.loss_protected_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
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
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletTransactionRecord;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCurrencyExchangeException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletManager;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.all_definition.utils.CurrencyPairImpl;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;

import java.util.UUID;


/**
 * Created by ciencias on 7/6/15.
 *
 */
public class BitcoinWalletLossProtectedWalletBookBalance implements BitcoinLossProtectedWalletBalance {

    /**
     * BitcoinWalletBasicWallet member variables.
     */
    private Database database;

    private BitcoinWalletLossProtectedWalletDao bitcoinWalletBasicWalletDao;

    private Broadcaster broadcaster;

    private CurrencyExchangeProviderFilterManager exchangeProviderFilterManagerproviderFilter;

    private UUID exchangeProviderId;

    private String WALLET_PUBLIC_KEY = "loss_protected_wallet";




    /**
     * Constructor.
     */
    public BitcoinWalletLossProtectedWalletBookBalance(final Database database,final Broadcaster broadcaster, final UUID exchangeProviderId, final CurrencyExchangeProviderFilterManager exchangeProviderFilterManagerproviderFilter){
        this.database = database;
        this.broadcaster = broadcaster;
        this.exchangeProviderId = exchangeProviderId;
        this.exchangeProviderFilterManagerproviderFilter = exchangeProviderFilterManagerproviderFilter;
    }


    @Override
    public long getBalance(BlockchainNetworkType blockchainNetworkType) throws CantCalculateBalanceException {
        try {
            bitcoinWalletBasicWalletDao = new BitcoinWalletLossProtectedWalletDao(this.database);
            return bitcoinWalletBasicWalletDao.getBookBalance(blockchainNetworkType);
        } catch(CantCalculateBalanceException exception){
            database.closeDatabase();
            throw exception;
        } catch(Exception exception){
            database.closeDatabase();
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /**
     * Get wallet available balance for actual exchange rate. Evaluates few blocks value have available to spend
     * @param blockchainNetworkType
     * @param exchangeRate
     * @return
     * @throws CantCalculateBalanceException
     */
    @Override
    public long getBalance(BlockchainNetworkType blockchainNetworkType, long exchangeRate) throws CantCalculateBalanceException {
        try {

            //calculate how many btc can spend based on the exchangeRate
            //Balance Type available

            bitcoinWalletBasicWalletDao = new BitcoinWalletLossProtectedWalletDao(this.database);

            return bitcoinWalletBasicWalletDao.getAvailableBalance(blockchainNetworkType, exchangeRate);


        } catch(CantListTransactionsException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception  ), null, null);

        } catch(Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception  ), null, null);
        }
    }

    /*
        * NOTA:
        *  El debit y el credit debería mirar primero si la tramsacción que
        *  se quiere aplicar existe. Si no existe aplica los cambios normalmente, pero si existe
        *  debería ignorar la transacción.
        */
    @Override
    public void debit(BitcoinLossProtectedWalletTransactionRecord cryptoTransaction) throws CantRegisterDebitException {
        try {
              double purchasePrice = 0;

            bitcoinWalletBasicWalletDao = new BitcoinWalletLossProtectedWalletDao(this.database);
            bitcoinWalletBasicWalletDao.addDebit(cryptoTransaction, BalanceType.BOOK,purchasePrice);
            //broadcaster balance amount
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, cryptoTransaction.getTransactionHash());
        } catch(CantRegisterDebitException exception){
            throw exception;
        } catch(Exception exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public void debit(BitcoinLossProtectedWalletTransactionRecord cryptoTransaction,double exchangeRate) throws CantRegisterDebitException {
        try {
            double purchasePrice = 0;
//            ExchangeRate rate = getActualExchangeRate();
//
//            if(rate != null)
//                purchasePrice = rate.getPurchasePrice();

            bitcoinWalletBasicWalletDao = new BitcoinWalletLossProtectedWalletDao(this.database);
            bitcoinWalletBasicWalletDao.addDebit(cryptoTransaction, BalanceType.BOOK,purchasePrice);
            //broadcaster balance amount
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, cryptoTransaction.getTransactionHash());
        } catch(CantRegisterDebitException exception){
            throw exception;
        } catch(Exception exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    //get exchange rate
    @Override
    public void credit(BitcoinLossProtectedWalletTransactionRecord cryptoTransaction) throws CantRegisterCreditException {
        try {
            double purchasePrice = 0;
            bitcoinWalletBasicWalletDao = new BitcoinWalletLossProtectedWalletDao(this.database);
            ExchangeRate rate = getActualExchangeRate();
            bitcoinWalletBasicWalletDao.addCredit(cryptoTransaction, BalanceType.BOOK, purchasePrice);
            //broadcaster balance amount
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, cryptoTransaction.getTransactionHash());
        } catch(CantRegisterCreditException exception){
            throw exception;
        } catch(Exception exception){
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

           if(rate != null)
               purchasePrice = rate.getPurchasePrice();
    //get exchange rate
    @Override
    public void credit(BitcoinLossProtectedWalletTransactionRecord cryptoTransaction, double exchangeRate) throws CantRegisterCreditException {
        try {
            double purchasePrice = 0;
            bitcoinWalletBasicWalletDao = new BitcoinWalletLossProtectedWalletDao(this.database);
             ExchangeRate rate = getActualExchangeRate();

               if(rate != null)
               purchasePrice = rate.getPurchasePrice();

            bitcoinWalletBasicWalletDao.addCredit(cryptoTransaction, BalanceType.BOOK,purchasePrice);
            //broadcaster balance amount
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, cryptoTransaction.getTransactionHash());
        } catch(CantRegisterCreditException exception){
            throw exception;
        } catch(Exception exception){
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }
    @Override
    public void revertCredit(BitcoinLossProtectedWalletTransactionRecord cryptoTransaction) throws CantRegisterCreditException {

        bitcoinWalletBasicWalletDao = new BitcoinWalletLossProtectedWalletDao(this.database);
        try {
            bitcoinWalletBasicWalletDao.revertCredit(cryptoTransaction, BalanceType.BOOK);
        } catch (CantRegisterDebitException e) {
            throw new CantRegisterCreditException("CANT REVERT CREDIT EN BOOK", FermatException.wrapException(e), null, null);

        }


    }

    private ExchangeRate getActualExchangeRate()
    {
        final ExchangeRate[] rate = new ExchangeRate[1];
        try {
            BitcoinLossProtectedWalletSettings basicWalletSettings = null;

          final UUID rateProviderManagerId = exchangeProviderId;

            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        CurrencyPair wantedCurrencyPair = new CurrencyPairImpl(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR);
                        CurrencyExchangeRateProviderManager rateProviderManager = exchangeProviderFilterManagerproviderFilter.getProviderReference(rateProviderManagerId);
                        //your exchange rate.
                        rate[0] = rateProviderManager.getCurrentExchangeRate(wantedCurrencyPair);

                    } catch (CantGetExchangeRateException e) {

                    } catch (UnsupportedCurrencyPairException e) {

                    }catch(Exception e){

                    }
                }
            });

            } catch (Exception e) {
                e.printStackTrace();
            }
        return rate[0];
    }


}
