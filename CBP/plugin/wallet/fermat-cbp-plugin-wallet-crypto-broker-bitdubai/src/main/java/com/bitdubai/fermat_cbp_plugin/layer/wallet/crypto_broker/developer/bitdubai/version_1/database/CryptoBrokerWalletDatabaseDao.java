package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CurrencyTypes;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_api.layer.world.interfaces.CurrencyHelper;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantClearCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetAvailableBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetBookedBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerMarketRateException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerQuoteException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerStockTransactionException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetTransactionCryptoBrokerWalletMatchingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantMarkAsSeenException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantSaveCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransaction;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletBalanceRecord;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.Quote;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CurrencyMatching;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.util.CurrencyMatchingImp;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantAddCreditException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantAddDebitException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantExecuteCryptoBrokerTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantGetBalanceRecordException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerStockTransactionImpl;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletAssociatedSettingImpl;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletBalanceRecordImpl;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletProviderSettingImpl;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletSettingSpreadImpl;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.FiatIndexImpl;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.QuoteImpl;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.all_definition.utils.CurrencyPairImpl;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.all_definition.enums.Plugins.CRYPTO_BROKER_WALLET;
import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity.NOT_IMPORTANT;


/**
 * Created by Yordin Alayn on 17.10.15.
 * Modified by Franklin Marcano
 */
public class CryptoBrokerWalletDatabaseDao implements DealsWithPluginFileSystem {
    //TODO: Documentar y Manejo de excepciones
    public static final String PATH_DIRECTORY = "cryptobrokerwallet-swap/";
    PluginFileSystem pluginFileSystem;
    UUID plugin;
    private CurrencyExchangeProviderFilterManager providerFilter;

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    public void setPlugin(UUID plugin) {
        this.plugin = plugin;
    }

    public void setProviderFilter(CurrencyExchangeProviderFilterManager providerFilter) {
        this.providerFilter = providerFilter;
    }

    private Database database;
    private ErrorManager errorManager;

    public CryptoBrokerWalletDatabaseDao(Database database, ErrorManager errorManager) {
        this.database = database;
        this.errorManager = errorManager;
    }

    public List<CurrencyMatching> getCryptoBrokerTransactionCurrencyMatchings() throws CantGetTransactionCryptoBrokerWalletMatchingException {
        List<CurrencyMatching> currencyMatchingList = new ArrayList<>();

        try {
            final List<DatabaseTableRecord> saleCreditRecords = getAvailableSaleRecordsFromTable(TransactionType.CREDIT);
            final List<DatabaseTableRecord> saleDebitRecords = getAvailableSaleRecordsFromTable(TransactionType.DEBIT);

            for (final DatabaseTableRecord creditRecord : saleCreditRecords) {
                String originTransactionId = creditRecord.getStringValue(CryptoBrokerWalletDatabaseConstants.
                        CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_ID_COLUMN_NAME);

                for (final DatabaseTableRecord debitRecord : saleDebitRecords) {
                    String debitOriginTransactionId = debitRecord.getStringValue(CryptoBrokerWalletDatabaseConstants.
                            CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_ID_COLUMN_NAME);

                    if (originTransactionId == null || debitOriginTransactionId == null)
                        continue;

                    if (originTransactionId.equals(debitOriginTransactionId)) {
                        String currencyCode, moneyTypeCode;

                        currencyCode = debitRecord.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME);
                        moneyTypeCode = debitRecord.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MONEY_TYPE_COLUMN_NAME);
                        final Currency currencyGiving = MoneyType.CRYPTO.getCode().equals(moneyTypeCode) ?
                                CurrencyHelper.getCurrency(CurrencyTypes.CRYPTO, currencyCode) :
                                CurrencyHelper.getCurrency(CurrencyTypes.FIAT, currencyCode);

                        currencyCode = creditRecord.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME);
                        moneyTypeCode = creditRecord.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MONEY_TYPE_COLUMN_NAME);
                        final Currency currencyReceiving = MoneyType.CRYPTO.getCode().equals(moneyTypeCode) ?
                                CurrencyHelper.getCurrency(CurrencyTypes.CRYPTO, currencyCode) :
                                CurrencyHelper.getCurrency(CurrencyTypes.FIAT, currencyCode);

                        if (currencyGiving != currencyReceiving) {
                            final float amountReceiving = new BigDecimal(creditRecord.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME)).floatValue();
                            final float amountGiving = new BigDecimal(debitRecord.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME)).floatValue();

                            final CurrencyMatching currencyMatchingImp = new CurrencyMatchingImp(originTransactionId, currencyGiving, currencyReceiving, amountGiving, amountReceiving);
                            currencyMatchingList.add(currencyMatchingImp);

                        } else {
                            errorManager.reportUnexpectedPluginException(CRYPTO_BROKER_WALLET, NOT_IMPORTANT, new InvalidParameterException(
                                    "Cant create a CurrencyMatching for the given currencies", null,
                                    "currencyGiving: " + currencyGiving + "\ncurrencyReceiving: " + currencyReceiving,
                                    "Please verify that the credit and debit transactions are correct in the crypto broker transaction database"));
                        }
                    }
                }
            }

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetTransactionCryptoBrokerWalletMatchingException("Cant Load Table Memory", e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetTransactionCryptoBrokerWalletMatchingException("Cant Get one of the Currencies", e, "", "");
        }

        return currencyMatchingList;
    }

    public List<DatabaseTableRecord> getAvailableSaleRecordsFromTable(TransactionType transactionType) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME);

        table.clearAllFilters();
        table.addStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_SEEN_COLUMN_NAME, "false", DatabaseFilterType.EQUAL);
        table.addStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_COLUMN_NAME, OriginTransaction.SALE.getCode(), DatabaseFilterType.EQUAL);
        table.addStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME, BalanceType.AVAILABLE.getCode(), DatabaseFilterType.EQUAL);
        table.addStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        table.addFilterOrder(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_ID_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
        table.loadToMemory();

        return table.getRecords();
    }

    public void markAsSeen(List<String> transactionIds) throws CantMarkAsSeenException {
        for (String ids : transactionIds) {
            DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME);
            try {
                for (DatabaseTableRecord record : getCryptoBrokerWalletStockTransactionData(ids)) {
                    record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_SEEN_COLUMN_NAME, "true");
                    table.updateRecord(record);
                }

            } catch (CantLoadTableToMemoryException e) {
                throw new CantMarkAsSeenException("Cant Load Table Memory", e, "", "");
            } catch (CantUpdateRecordException e) {
                throw new CantMarkAsSeenException("Cant Update Record", e, "", "");
            }
        }
    }

    public float getBookedBalance(FermatEnum merchandise) throws CantGetBookedBalanceCryptoBrokerWalletException {
        return getCurrentBalanceByMerchandise(BalanceType.BOOK, merchandise.getCode());
    }

    public float geAvailableBalance(FermatEnum merchandise) throws CantGetAvailableBalanceCryptoBrokerWalletException {
        return getCurrentBalanceByMerchandise(BalanceType.AVAILABLE, merchandise.getCode());
    }

    public float getAvailableBalanceFrozen(FermatEnum merchandise) throws CantGetAvailableBalanceCryptoBrokerWalletException {
        return getCurrentBalanceByMerchandise(BalanceType.AVAILABLE, merchandise.getCode());
    }

    public List<CryptoBrokerWalletBalanceRecord> getAvailableBalanceByMerchandise() throws CantCalculateBalanceException, CantGetBalanceRecordException {
        return getCurrentAvailableBalanceByMerchandise();
    }

    public List<CryptoBrokerWalletBalanceRecord> getBookBalanceByMerchandise() throws CantCalculateBalanceException, CantGetBalanceRecordException {
        return getCurrentBookBalanceByMerchandise();
    }

    public List<CryptoBrokerWalletBalanceRecord> getAvailableBalanceByMerchandiseFrozen() throws CantCalculateBalanceException {
        DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_TABLE_NAME);
        CryptoBrokerWalletBalanceRecordImpl cryptoBrokerWalletBalanceRecord;
        List<CryptoBrokerWalletBalanceRecord> cryptoBrokerWalletBalanceRecords = new ArrayList<>();

        float availableBalanceFrozen;
        try {
            table.loadToMemory();
            for (DatabaseTableRecord records : table.getRecords()) {
                availableBalanceFrozen = getCurrentBalanceByMerchandise(BalanceType.AVAILABLE, records.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME));
                cryptoBrokerWalletBalanceRecord = new CryptoBrokerWalletBalanceRecordImpl();
                cryptoBrokerWalletBalanceRecord.setAvailableBalance(new BigDecimal(records.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME)).subtract(new BigDecimal(availableBalanceFrozen)));
                cryptoBrokerWalletBalanceRecord.setMoneyType(MoneyType.getByCode(records.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MONEY_TYPE_COLUMN_NAME)));
                if (MoneyType.CRYPTO != MoneyType.getByCode(records.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MONEY_TYPE_COLUMN_NAME))) {
                    cryptoBrokerWalletBalanceRecord.setMerchandise(FiatCurrency.getByCode(records.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME)));
                } else {
                    cryptoBrokerWalletBalanceRecord.setMerchandise(CryptoCurrency.getByCode(records.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME)));
                }
                cryptoBrokerWalletBalanceRecord.setBookBalance(new BigDecimal(0));
                cryptoBrokerWalletBalanceRecord.setBrokerPublicKey(records.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BROKER_PUBLIC_KEY_COLUMN_NAME));

                cryptoBrokerWalletBalanceRecords.add(cryptoBrokerWalletBalanceRecord);


            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantCalculateBalanceException("Cant Load Table Memory", e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantCalculateBalanceException("Invalid Parameter", e, "", "");
        }
        return cryptoBrokerWalletBalanceRecords;
    }

    public List<CryptoBrokerWalletBalanceRecord> getBookBalanceByMerchandiseFrozen() throws CantCalculateBalanceException {
        DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_TABLE_NAME);
        CryptoBrokerWalletBalanceRecordImpl cryptoBrokerWalletBalanceRecord;
        List<CryptoBrokerWalletBalanceRecord> cryptoBrokerWalletBalanceRecords = new ArrayList<>();

        float bookBalanceFrozen;
        try {
            table.loadToMemory();
            for (DatabaseTableRecord records : table.getRecords()) {
                bookBalanceFrozen = getCurrentBalanceByMerchandise(BalanceType.BOOK, records.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME));
                cryptoBrokerWalletBalanceRecord = new CryptoBrokerWalletBalanceRecordImpl();
                cryptoBrokerWalletBalanceRecord.setBookBalance(new BigDecimal(records.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME)).subtract(new BigDecimal(bookBalanceFrozen)));
                cryptoBrokerWalletBalanceRecord.setMoneyType(MoneyType.getByCode(records.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MONEY_TYPE_COLUMN_NAME)));
                if (MoneyType.CRYPTO != MoneyType.getByCode(records.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MONEY_TYPE_COLUMN_NAME))) {
                    cryptoBrokerWalletBalanceRecord.setMerchandise(FiatCurrency.getByCode(records.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME)));
                } else {
                    cryptoBrokerWalletBalanceRecord.setMerchandise(CryptoCurrency.getByCode(records.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME)));
                }
                cryptoBrokerWalletBalanceRecord.setAvailableBalance(new BigDecimal(0));
                cryptoBrokerWalletBalanceRecord.setBrokerPublicKey(records.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BROKER_PUBLIC_KEY_COLUMN_NAME));

                cryptoBrokerWalletBalanceRecords.add(cryptoBrokerWalletBalanceRecord);


            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantCalculateBalanceException("Cant Load Table Memory", e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantCalculateBalanceException("Invalid Parameter", e, "", "");
        }
        return cryptoBrokerWalletBalanceRecords;
    }

    public Quote getQuote(final Currency merchandise, final Currency payment) throws CantGetCryptoBrokerQuoteException {
        //Debemos de conocer el valor AvailableBalance menos los congelado, de esa forma tengo lo que puedo vender
        //Tambien podemos determinar devolver segun la volatilidad del mercado
        //Determinar mediante el precio del mercado a como esta esa mercancia
        float priceReference;
        float availableBalanceFroze;
        CurrencyPair currencyPair = new CurrencyPairImpl(merchandise, payment);

        try {
            List<CryptoBrokerWalletProviderSetting> providerSettings = getCryptoBrokerWalletProviderSettings();
            CryptoBrokerWalletProviderSetting associatedProvider = null;
            ExchangeRate rate = null;

            for (CryptoBrokerWalletProviderSetting setting : providerSettings) {
                final String currencyFrom = setting.getCurrencyFrom();
                final String currencyTo = setting.getCurrencyTo();

                if (currencyFrom.equals(merchandise.getCode()) && currencyTo.equals(payment.getCode())) {
                    associatedProvider = setting;
                    break;
                }
            }

            if (associatedProvider != null) {
                CurrencyExchangeRateProviderManager provider = providerFilter.getProviderReference(associatedProvider.getPlugin());
                rate = provider.getCurrentExchangeRate(currencyPair);
            } else {
                List<CurrencyExchangeRateProviderManager> providers = new ArrayList<>(providerFilter.getProviderReferencesFromCurrencyPair(currencyPair));
                if (!providers.isEmpty())
                    rate = providers.get(0).getCurrentExchangeRate(currencyPair);
            }

            priceReference = (float) (rate != null ? rate.getSalePrice() : 0);

            final float balanceFrozen = getBalanceFrozenByMerchandise(merchandise, null, BalanceType.AVAILABLE, priceReference);
            final float currentBalance = getCurrentBalanceByMerchandise(BalanceType.AVAILABLE, merchandise.getCode());

            availableBalanceFroze = currentBalance - balanceFrozen;

        } catch (CantGetProviderException e) {
            throw new CantGetCryptoBrokerQuoteException("Cant Get Provider Exception", e, "", "");
        } catch (UnsupportedCurrencyPairException e) {
            throw new CantGetCryptoBrokerQuoteException("Unsupported Currency Pair Exception", e, "", "");
        } catch (CantGetCryptoBrokerWalletSettingException e) {
            throw new CantGetCryptoBrokerQuoteException("Cant get the associated Providers", e, "", "");
        } catch (CantGetExchangeRateException e) {
            throw new CantGetCryptoBrokerQuoteException("Cant get Exchange Rate from Provider", e, "", "");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetCryptoBrokerQuoteException("Cant Load Table", e, "", "");
        }

        return new QuoteImpl(merchandise, payment, priceReference, availableBalanceFroze);
    }

    public FiatIndex getMarketRate(final Currency merchandise, FiatCurrency fiatCurrency, MoneyType moneyType) throws CantGetCryptoBrokerMarketRateException {
        //Buscar el Spread en el setting de la wallet
        FiatIndexImpl fiatIndex;
        float spread;
        ExchangeRate rate = null;
        try {
            spread = getSpread();

            //Determinar luego si ya se vendio o compro esa mercaderia para calcular el rate sobre el precio inicial y final
            //Tambien podemos determinar devolver segun la volatilidad del mercado
            float volatility = getVolatilityCalculation(merchandise, moneyType);

            //Determinar mediante el precio del mercado a como esta esa mercancia
            float priceReference; //Precio en dolar devuelto al pedir el precio del mercado
            float priceRateSale;
            float priceRatePurchase;
            if (!getCryptoBrokerStockTransactionsByMerchandise(merchandise, moneyType, TransactionType.CREDIT, BalanceType.AVAILABLE).isEmpty())
                priceRateSale = getCryptoBrokerStockTransactionsByMerchandise(merchandise, moneyType, TransactionType.CREDIT, BalanceType.AVAILABLE).get(0).getPriceReference().floatValue();
            if (!getCryptoBrokerStockTransactionsByMerchandise(merchandise, moneyType, TransactionType.DEBIT, BalanceType.AVAILABLE).isEmpty())
                priceRatePurchase = getCryptoBrokerStockTransactionsByMerchandise(merchandise, moneyType, TransactionType.DEBIT, BalanceType.AVAILABLE).get(0).getPriceReference().floatValue();

            CurrencyPair usdVefCurrencyPair = new CurrencyPairImpl(merchandise, fiatCurrency);

            Collection<CurrencyExchangeRateProviderManager> usdVefProviders = providerFilter.getProviderReferencesFromCurrencyPair(usdVefCurrencyPair);
            for (CurrencyExchangeRateProviderManager provider : usdVefProviders) {
                rate = provider.getCurrentExchangeRate(usdVefCurrencyPair);
            }

            priceRateSale = (float) (rate != null ? rate.getSalePrice() : 0);
            priceRatePurchase = (float) (rate != null ? rate.getPurchasePrice() : 0);

            final float priceSaleUp = (priceRateSale * ((spread / 2) / 100)) + priceRateSale;
            final float priceSaleDown = (priceRateSale * ((spread / 2) / 100)) - priceRateSale;
            final float pricePurchaseUp = (priceRatePurchase * ((spread / 2) / 100)) + priceRatePurchase;
            final float pricePurchaseDown = (priceRatePurchase * ((spread / 2) / 100)) + priceRatePurchase;

            priceReference = (float) (rate != null ? rate.getSalePrice() : 0);

            fiatIndex = new FiatIndexImpl(
                    merchandise,
                    priceRateSale,
                    priceRatePurchase,
                    priceSaleUp,
                    priceSaleDown,
                    pricePurchaseUp,
                    pricePurchaseDown,
                    priceReference,
                    fiatCurrency
            );
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetCryptoBrokerMarketRateException("Cant Load Table Memory", e, "", "");
        } catch (CantGetCryptoBrokerStockTransactionException e) {
            throw new CantGetCryptoBrokerMarketRateException("Cant Get Broker Stock Transaction", e, "", "");
        } catch (CantGetProviderException e) {
            throw new CantGetCryptoBrokerMarketRateException("Cant Get Provider Exception", e, "", "");
        } catch (CantGetExchangeRateException e) {
            throw new CantGetCryptoBrokerMarketRateException("Cant Get Exchange Rate Exception", e, "", "");
        } catch (UnsupportedCurrencyPairException e) {
            throw new CantGetCryptoBrokerMarketRateException("Cant Get Crypto Broker Market Rate Exception", e, "", "");
        }
        return fiatIndex;
    }


    public List<CryptoBrokerStockTransaction> getCryptoBrokerStockTransactionsByMerchandise(Currency merchandise, MoneyType moneyType, TransactionType transactionType, BalanceType balanceType) throws CantGetCryptoBrokerStockTransactionException {
        DatabaseTable databaseTable = getStockWalletTransactionTable();
        FiatCurrency fiatCurrency;
        CryptoCurrency cryptoCurrency;
        Currency fermatEnum;

        if (MoneyType.CRYPTO != moneyType) {
            try {
                fiatCurrency = FiatCurrency.getByCode(merchandise.getCode());
            } catch (InvalidParameterException e) {
                throw new CantGetCryptoBrokerStockTransactionException("Invalid Parameter", e, "", "");
            }
            fermatEnum = fiatCurrency;

        } else {
            try {
                cryptoCurrency = CryptoCurrency.getByCode(merchandise.getCode());
            } catch (InvalidParameterException e) {
                throw new CantGetCryptoBrokerStockTransactionException("Invalid Parameter", e, "", "");
            }
            fermatEnum = cryptoCurrency;
        }
        String query = "SELECT * FROM " +
                CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME +
                " WHERE " +
                CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME +
                " = '" +
                fermatEnum.getCode() +
                "' AND " +
                CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME +
                " = '" +
                transactionType.getCode() +
                "' AND " +
                CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME +
                " = '" +
                balanceType.getCode() +
                "' ORDER BY " +
                CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME +
                "' DESC";

        List<CryptoBrokerStockTransaction> transactions = new ArrayList<>();

        try {
            Collection<DatabaseTableRecord> records = databaseTable.customQuery(query, true);
            for (DatabaseTableRecord record : records) {
                if (MoneyType.CRYPTO != MoneyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MONEY_TYPE_COLUMN_NAME))) {
                    fiatCurrency = FiatCurrency.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME));
                    fermatEnum = fiatCurrency;
                } else {
                    cryptoCurrency = CryptoCurrency.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME));
                    fermatEnum = cryptoCurrency;
                }

                CryptoBrokerStockTransactionImpl cryptoBrokerStockTransaction;

                cryptoBrokerStockTransaction = new CryptoBrokerStockTransactionImpl(
                        new BigDecimal(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_BOOK_BALANCE_COLUMN_NAME)),
                        new BigDecimal(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME)),
                        new BigDecimal(0),
                        new BigDecimal(0),
                        record.getUUIDValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME),
                        BalanceType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME)),
                        TransactionType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME)),
                        MoneyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MONEY_TYPE_COLUMN_NAME)),
                        fermatEnum,
                        record.getStringValue(""),
                        record.getStringValue(""),
                        new BigDecimal(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME)),
                        record.getLongValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME),
                        record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME),
                        new BigDecimal(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_PRICE_REFERENCE_COLUMN_NAME)),
                        OriginTransaction.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_COLUMN_NAME)),
                        record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME),
                        Boolean.valueOf(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_SEEN_COLUMN_NAME))
                );

                transactions.add(cryptoBrokerStockTransaction);

            }

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetCryptoBrokerStockTransactionException("Cant Load Table", e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetCryptoBrokerStockTransactionException("Invalid Parameter", e, "", "");
        }

        return transactions;
    }

    public List<CryptoBrokerStockTransaction> getStockHistory(Currency merchandise, MoneyType moneyType, int offset, long timeStamp) throws CantGetCryptoBrokerStockTransactionException {
        DatabaseTable databaseTable = getStockWalletTransactionTable();
        FiatCurrency fiatCurrency;
        CryptoCurrency cryptoCurrency;
        Currency fermatEnum;

        long dateEnd = (1000 * 60 * 60 * 24);

        dateEnd *= offset;

        dateEnd += Math.abs(timeStamp);

        if (MoneyType.CRYPTO != moneyType) {
            try {
                fiatCurrency = FiatCurrency.getByCode(merchandise.getCode());
            } catch (InvalidParameterException e) {
                throw new CantGetCryptoBrokerStockTransactionException("Invalid Parameter", e, "", "");
            }
            fermatEnum = fiatCurrency;
        } else {
            try {
                cryptoCurrency = CryptoCurrency.getByCode(merchandise.getCode());
            } catch (InvalidParameterException e) {
                throw new CantGetCryptoBrokerStockTransactionException("Invalid Parameter", e, "", "");
            }
            fermatEnum = cryptoCurrency;
        }

        databaseTable.addStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME, fermatEnum.getCode(), DatabaseFilterType.EQUAL);
        databaseTable.addStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME, BalanceType.AVAILABLE.getCode(), DatabaseFilterType.EQUAL);

        databaseTable.addFilterOrder(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

        List<CryptoBrokerStockTransaction> transactions = new ArrayList<>();

        try {
            databaseTable.loadToMemory();
            Collection<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records) {
                Long date = record.getLongValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME);

                if (timeStamp <= date && date <= dateEnd) {

                    if (MoneyType.CRYPTO != MoneyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MONEY_TYPE_COLUMN_NAME))) {
                        fiatCurrency = FiatCurrency.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME));
                        fermatEnum = fiatCurrency;
                    } else {
                        cryptoCurrency = CryptoCurrency.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME));
                        fermatEnum = cryptoCurrency;
                    }


                    CryptoBrokerStockTransactionImpl cryptoBrokerStockTransaction = new CryptoBrokerStockTransactionImpl(
                            new BigDecimal(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_BOOK_BALANCE_COLUMN_NAME)),
                            new BigDecimal(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME)),
                            new BigDecimal(0),
                            new BigDecimal(0),
                            record.getUUIDValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME),
                            BalanceType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME)),
                            TransactionType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME)),
                            MoneyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MONEY_TYPE_COLUMN_NAME)),
                            fermatEnum,
                            record.getStringValue(""),
                            record.getStringValue(""),
                            new BigDecimal(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME)),
                            record.getLongValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME),
                            record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME),
                            new BigDecimal(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_PRICE_REFERENCE_COLUMN_NAME)),
                            OriginTransaction.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_COLUMN_NAME)),
                            record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME),
                            Boolean.valueOf(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_SEEN_COLUMN_NAME))
                    );

                    transactions.add(cryptoBrokerStockTransaction);

                }

            }

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetCryptoBrokerStockTransactionException("Cant Load Table", e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetCryptoBrokerStockTransactionException("Invalid Parameter", e, "", "");
        }

        return transactions;
    }

    /*
    * Add a new debit transaction.
    */
    public void addDebit(final CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, final BalanceType balanceType) throws CantAddDebitException {
        //System.out.println("Agregando Debito-----------------------------------------------------------");
        try {
            if (isTransactionInTable(cryptoBrokerStockTransactionRecord.getTransactionId().toString(), TransactionType.DEBIT, balanceType))
                throw new CantAddDebitException(CantAddDebitException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

            float availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? cryptoBrokerStockTransactionRecord.getAmount().floatValue() : 0L;
            float bookAmount = balanceType.equals(BalanceType.BOOK) ? cryptoBrokerStockTransactionRecord.getAmount().floatValue() : 0L;

            float availableRunningBalance = calculateAvailableRunningBalanceByMerchandise(-availableAmount, cryptoBrokerStockTransactionRecord.getMerchandise().getCode());
            float bookRunningBalance = calculateBookRunningBalanceByMerchandise(-bookAmount, cryptoBrokerStockTransactionRecord.getMerchandise().getCode());

            executeTransaction(cryptoBrokerStockTransactionRecord, TransactionType.DEBIT, balanceType, bookRunningBalance, availableRunningBalance);
        } catch (CantLoadTableToMemoryException e) {
            throw new CantAddDebitException("Error Add Debit", e, "", "");
        } catch (CantGetBalanceRecordException e) {
            throw new CantAddDebitException("Error Get Balance Record", e, "", "");
        } catch (CantExecuteCryptoBrokerTransactionException e) {
            throw new CantAddDebitException("Error Execute Transaction", e, "", "");
        }
    }

    /*
    * Add a new credit transaction.
    */
    public void addCredit(final CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, final BalanceType balanceType) throws CantAddCreditException {
        //System.out.println("Agregando Credito-----------------------------------------------------------");
        try {
            if (isTransactionInTable(cryptoBrokerStockTransactionRecord.getTransactionId().toString(), TransactionType.CREDIT, balanceType))
                throw new CantAddCreditException(CantAddCreditException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

            float availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? cryptoBrokerStockTransactionRecord.getAmount().floatValue() : 0L;
            float bookAmount = balanceType.equals(BalanceType.BOOK) ? cryptoBrokerStockTransactionRecord.getAmount().floatValue() : 0L;

            float availableRunningBalance = calculateAvailableRunningBalanceByMerchandise(availableAmount, cryptoBrokerStockTransactionRecord.getMerchandise().getCode());
            float bookRunningBalance = calculateBookRunningBalanceByMerchandise(bookAmount, cryptoBrokerStockTransactionRecord.getMerchandise().getCode());

            executeTransaction(cryptoBrokerStockTransactionRecord, TransactionType.CREDIT, balanceType, bookRunningBalance, availableRunningBalance);
        } catch (CantLoadTableToMemoryException e) {
            throw new CantAddCreditException("Error Add Credit", e, "", "");
        } catch (CantGetBalanceRecordException e) {
            throw new CantAddCreditException("Error Get Balance Record", e, "", "");
        } catch (CantExecuteCryptoBrokerTransactionException e) {
            throw new CantAddCreditException("Error Execute Transaction", e, "", "");
        }
    }

    public void saveCryptoBrokerWalletSpreadSetting(CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread) throws CantSaveCryptoBrokerWalletSettingException {
        DatabaseTransaction transaction = database.newTransaction();

        //TODO:Solo para Testing y prueba luego eliminar
        cryptoBrokerWalletSettingSpread.setId(UUID.randomUUID());

        DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_TABLE_NAME);
        try {
            DatabaseTableRecord Record = getCryptoBrokerWalletSpreadSettingRecord(cryptoBrokerWalletSettingSpread);

            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(cryptoBrokerWalletSettingSpread.getId().toString());
            filter.setColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_ID_COLUMN_NAME);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, Record);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, Record);
            }
            //I execute the transaction and persist the database
            database.executeTransaction(transaction);

        } catch (Exception e) {
            throw new CantSaveCryptoBrokerWalletSettingException(CantSaveCryptoBrokerWalletSettingException.DEFAULT_MESSAGE, e, "Error trying to save the Crypto Broker Wallet Setting Spread in the database.", null);
        }
    }

    public void clearCryptoBrokerWalletSpreadSetting() throws CantClearCryptoBrokerWalletSettingException {
        DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_TABLE_NAME);
        try {
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();

            for (DatabaseTableRecord record : records)
                table.deleteRecord(record);

        } catch (CantLoadTableToMemoryException e) {
            throw new CantClearCryptoBrokerWalletSettingException("Cant load table to memory", e, "", "");
        } catch (CantDeleteRecordException e) {
            throw new CantClearCryptoBrokerWalletSettingException("Cant clear settings from wallet", e, "", "");
        }
    }


    public CryptoBrokerWalletSettingSpread getCryptoBrokerWalletSpreadSetting() throws CantGetCryptoBrokerWalletSettingException {
        CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread = null;
        try {
            for (DatabaseTableRecord record : getCryptoBrokerWalletSpreadSettingData()) {
                cryptoBrokerWalletSettingSpread = getCryptoBrokerWalletSpreadSetting(record);
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetCryptoBrokerWalletSettingException("Cant Load Table Memory", e, "", "");
        } catch (DatabaseOperationException e) {
            throw new CantGetCryptoBrokerWalletSettingException("Database Operation", e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetCryptoBrokerWalletSettingException("Invalid Parameter", e, "", "");
        }
        return cryptoBrokerWalletSettingSpread;
    }

    public void saveCryptoBrokerWalletAssociatedSetting(CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting) throws CantSaveCryptoBrokerWalletSettingException {
        DatabaseTransaction transaction = database.newTransaction();

        DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_TABLE_NAME);

        try {
            DatabaseTableRecord Record = getCryptoBrokerWalletAssociatedSettingRecord(cryptoBrokerWalletAssociatedSetting);

            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(cryptoBrokerWalletAssociatedSetting.getBankAccount());
            filter.setColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_ID_COLUMN_NAME);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, Record);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, Record);
            }
            //I execute the transaction and persist the database
            database.executeTransaction(transaction);

        } catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new CantSaveCryptoBrokerWalletSettingException(CantSaveCryptoBrokerWalletSettingException.DEFAULT_MESSAGE, e, "Error trying to save the Crypto Broker Wallet Setting Associated in the database.", null);
        }
    }

    public void clearCryptoBrokerWalletAssociatedSetting(Platforms platform) throws CantClearCryptoBrokerWalletSettingException {
        DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_TABLE_NAME);

        try {
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();

            for (DatabaseTableRecord record : records) {
                String platformValue = record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_PLATFORM_COLUMN_NAME);
                if (platformValue.equalsIgnoreCase(platform.getCode())) {
                    table.deleteRecord(record);
                }
            }

        } catch (CantLoadTableToMemoryException e) {
            throw new CantClearCryptoBrokerWalletSettingException("Cant load table to memory", e, "", "");
        } catch (CantDeleteRecordException e) {
            throw new CantClearCryptoBrokerWalletSettingException("Cant clear settings from wallet", e, "", "");
        }

    }


    public List<CryptoBrokerWalletAssociatedSetting> getCryptoBrokerWalletAssociatedSettings() throws CantGetCryptoBrokerWalletSettingException {
        List<CryptoBrokerWalletAssociatedSetting> cryptoBrokerWalletAssociatedSettings = new ArrayList<>();
        try {
            for (DatabaseTableRecord record : getCryptoBrokerWalletAssociatedSettingData()) {
                CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting = getCryptoBrokerWalletAssociatedSetting(record);
                cryptoBrokerWalletAssociatedSettings.add(cryptoBrokerWalletAssociatedSetting);
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetCryptoBrokerWalletSettingException("Cant Load Table Memory", e, "", "");
        } catch (DatabaseOperationException e) {
            throw new CantGetCryptoBrokerWalletSettingException("Database Operation", e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetCryptoBrokerWalletSettingException("Invalid Parameter", e, "", "");
        }
        return cryptoBrokerWalletAssociatedSettings;
    }

    public void saveCryptoBrokerWalletProviderSetting(CryptoBrokerWalletProviderSetting cryptoBrokerWalletProviderSetting) throws CantSaveCryptoBrokerWalletSettingException {
        DatabaseTransaction transaction = database.newTransaction();

        //TODO:Solo para Testing y prueba luego eliminar
        cryptoBrokerWalletProviderSetting.setId(UUID.randomUUID());

        DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_TABLE_NAME);
        try {
            DatabaseTableRecord Record = getCryptoBrokerWalletProviderSettingRecord(cryptoBrokerWalletProviderSetting);

            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(cryptoBrokerWalletProviderSetting.getId().toString());
            filter.setColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_ID_COLUMN_NAME);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, Record);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, Record);
            }
            //I execute the transaction and persist the database
            database.executeTransaction(transaction);

        } catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new CantSaveCryptoBrokerWalletSettingException(CantSaveCryptoBrokerWalletSettingException.DEFAULT_MESSAGE, e, "Error trying to save the Crypto Broker Wallet Setting Provider in the database.", null);
        }
    }

    public void clearCryptoBrokerWalletProviderSetting() throws CantClearCryptoBrokerWalletSettingException {
        DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_TABLE_NAME);
        try {
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();

            for (DatabaseTableRecord record : records)
                table.deleteRecord(record);

        } catch (CantLoadTableToMemoryException e) {
            throw new CantClearCryptoBrokerWalletSettingException("Cant load table to memory", e, "", "");
        } catch (CantDeleteRecordException e) {
            throw new CantClearCryptoBrokerWalletSettingException("Cant clear settings from wallet", e, "", "");
        }

    }


    public List<CryptoBrokerWalletProviderSetting> getCryptoBrokerWalletProviderSettings() throws CantGetCryptoBrokerWalletSettingException {
        List<CryptoBrokerWalletProviderSetting> cryptoBrokerWalletProviderSettings = new ArrayList<>();
        try {
            for (DatabaseTableRecord record : getCryptoBrokerWalletProviderSettingData()) {
                CryptoBrokerWalletProviderSetting cryptoBrokerWalletProviderSetting = getCryptoBrokerWalletProviderSetting(record);
                cryptoBrokerWalletProviderSettings.add(cryptoBrokerWalletProviderSetting);
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetCryptoBrokerWalletSettingException("Cant Load Table Memory", e, "", "");
        } catch (DatabaseOperationException e) {
            throw new CantGetCryptoBrokerWalletSettingException("Database Operation", e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetCryptoBrokerWalletSettingException("Invalid Parameter", e, "", "");
        }
        return cryptoBrokerWalletProviderSettings;
    }

    private float getVolatilityCalculation(final Currency merchandise, MoneyType moneyType) throws CantGetCryptoBrokerStockTransactionException {
        float volatility, priceMinimum = 0, priceMaximum = 0;

        boolean sw = true;
        try {
            for (CryptoBrokerStockTransaction cryptoBrokerStockTransaction : getCryptoBrokerStockTransactionsByMerchandise(merchandise, moneyType, TransactionType.CREDIT, BalanceType.AVAILABLE)) {
                if (sw) {
                    priceMaximum = cryptoBrokerStockTransaction.getPriceReference().floatValue();
                    sw = false;
                }
                priceMinimum = cryptoBrokerStockTransaction.getPriceReference().floatValue();
            }
        } catch (CantGetCryptoBrokerStockTransactionException e) {
            throw new CantGetCryptoBrokerStockTransactionException("Cant Get Crypto Broker Stock Transaction", e, "", "");
        }

        volatility = (priceMaximum - priceMinimum) / (priceMaximum + priceMinimum);

        return volatility;
    }

    private float getBalanceFrozenByMerchandise(FermatEnum merchandise, MoneyType moneyType, BalanceType balanceType, float priceReference) throws CantLoadTableToMemoryException {
        float rateFrozen = 0;

        DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME);
        table.addStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME, merchandise.getCode(), DatabaseFilterType.EQUAL);
        table.addStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

        table.loadToMemory();

        for (DatabaseTableRecord records : table.getRecords()) {
            records.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME);

            try {
                if (TransactionType.CREDIT.equals(TransactionType.getByCode(records.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME)))) {
                    if (records.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME) <= priceReference) {
                        rateFrozen += records.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME);
                    }
                }
            } catch (InvalidParameterException e) {
                throw new CantLoadTableToMemoryException("Invalid Parameter", e, "", "");
            }
        }
        return rateFrozen;
    }

    private float getSpread() throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_TABLE_NAME);

        table.loadToMemory();
        DatabaseTableRecord record = table.getRecords().get(0);

        return record.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_VALUE_COLUMN_NAME);
    }

    private CryptoBrokerWalletSettingSpread getCryptoBrokerWalletSpreadSetting(DatabaseTableRecord record) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {
        CryptoBrokerWalletSettingSpreadImpl cryptoBrokerWalletSettingSpread = new CryptoBrokerWalletSettingSpreadImpl();

        cryptoBrokerWalletSettingSpread.setId(record.getUUIDValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_ID_COLUMN_NAME));
        cryptoBrokerWalletSettingSpread.setBrokerPublicKey(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_BROKER_PUBLIC_KEY_COLUMN_NAME));
        cryptoBrokerWalletSettingSpread.setSpread(record.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_VALUE_COLUMN_NAME));
        cryptoBrokerWalletSettingSpread.setRestockAutomatic(Boolean.valueOf(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_RESTOCK_AUTOMATIC)));

        return cryptoBrokerWalletSettingSpread;
    }

    private CryptoBrokerWalletProviderSetting getCryptoBrokerWalletProviderSetting(DatabaseTableRecord record) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {
        CryptoBrokerWalletProviderSettingImpl cryptoBrokerWalletProviderSetting = new CryptoBrokerWalletProviderSettingImpl();

        cryptoBrokerWalletProviderSetting.setId(record.getUUIDValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_ID_COLUMN_NAME));
        cryptoBrokerWalletProviderSetting.setBrokerPublicKey(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_BROKER_PUBLIC_KEY_COLUMN_NAME));
        cryptoBrokerWalletProviderSetting.setDescription(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_DESCRIPTION_COLUMN_NAME));
        cryptoBrokerWalletProviderSetting.setPlugin(record.getUUIDValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_PLUGIN_COLUMN_NAME));
        cryptoBrokerWalletProviderSetting.setCurrencyFrom(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_CURRENCY_FROM_COLUMN_NAME));
        cryptoBrokerWalletProviderSetting.setCurrencyTo(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_CURRENCY_TO_COLUMN_NAME));

        return cryptoBrokerWalletProviderSetting;
    }

    private CryptoBrokerWalletAssociatedSetting getCryptoBrokerWalletAssociatedSetting(DatabaseTableRecord record) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {
        CryptoBrokerWalletAssociatedSettingImpl cryptoBrokerWalletAssociatedSetting = new CryptoBrokerWalletAssociatedSettingImpl();

        cryptoBrokerWalletAssociatedSetting.setId(record.getUUIDValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_ID_COLUMN_NAME));
        cryptoBrokerWalletAssociatedSetting.setBrokerPublicKey(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_BROKER_PUBLIC_KEY_COLUMN_NAME));
        cryptoBrokerWalletAssociatedSetting.setWalletPublicKey(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_PUBLIC_KEY_COLUMN_NAME));
        cryptoBrokerWalletAssociatedSetting.setMoneyType(MoneyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_MONEY_TYPE_COLUMN_NAME)));
        if (MoneyType.CRYPTO != MoneyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_MONEY_TYPE_COLUMN_NAME)))
            cryptoBrokerWalletAssociatedSetting.setMerchandise(FiatCurrency.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_MERCHANDISE_COLUMN_NAME)));
        else
            cryptoBrokerWalletAssociatedSetting.setMerchandise(CryptoCurrency.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_MERCHANDISE_COLUMN_NAME)));
        cryptoBrokerWalletAssociatedSetting.setPlatform(Platforms.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_PLATFORM_COLUMN_NAME)));
        if (cryptoBrokerWalletAssociatedSetting.getPlatform() == Platforms.BANKING_PLATFORM)
            cryptoBrokerWalletAssociatedSetting.setBankAccount(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_BANK_ACCOUNT_COLUMN_NAME));
        return cryptoBrokerWalletAssociatedSetting;
    }

    private List<DatabaseTableRecord> getCryptoBrokerWalletSpreadSettingData() throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_TABLE_NAME);

        //table.addStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_ID_COLUMN_NAME, Id, DatabaseFilterType.EQUAL);
        table.loadToMemory();

        return table.getRecords();
    }

    private List<DatabaseTableRecord> getCryptoBrokerWalletStockTransactionData(String originTransactionId) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME);

        table.addStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_ID_COLUMN_NAME, originTransactionId, DatabaseFilterType.EQUAL);
        table.loadToMemory();

        return table.getRecords();
    }

    private List<DatabaseTableRecord> getCryptoBrokerWalletAssociatedSettingData() throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_TABLE_NAME);

        table.loadToMemory();

        return table.getRecords();
    }

    private List<DatabaseTableRecord> getCryptoBrokerWalletProviderSettingData() throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_TABLE_NAME);

        table.loadToMemory();

        return table.getRecords();
    }

    private DatabaseTable getDatabaseTable(String tableName) {

        return database.getTable(tableName);
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        return table.getRecords().isEmpty();
    }

    private DatabaseTableRecord getCryptoBrokerWalletSpreadSettingRecord(CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_ID_COLUMN_NAME, cryptoBrokerWalletSettingSpread.getId());
        record.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_VALUE_COLUMN_NAME, cryptoBrokerWalletSettingSpread.getSpread());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_BROKER_PUBLIC_KEY_COLUMN_NAME, cryptoBrokerWalletSettingSpread.getBrokerPublicKey());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_RESTOCK_AUTOMATIC, String.valueOf(cryptoBrokerWalletSettingSpread.getRestockAutomatic()));

        return record;
    }

    private DatabaseTableRecord getCryptoBrokerWalletAssociatedSettingRecord(CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_ID_COLUMN_NAME, cryptoBrokerWalletAssociatedSetting.getId());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_PUBLIC_KEY_COLUMN_NAME, cryptoBrokerWalletAssociatedSetting.getWalletPublicKey());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_BROKER_PUBLIC_KEY_COLUMN_NAME, cryptoBrokerWalletAssociatedSetting.getBrokerPublicKey());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_PLATFORM_COLUMN_NAME, cryptoBrokerWalletAssociatedSetting.getPlatform().getCode());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_BANK_ACCOUNT_COLUMN_NAME, cryptoBrokerWalletAssociatedSetting.getBankAccount());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_MERCHANDISE_COLUMN_NAME, cryptoBrokerWalletAssociatedSetting.getMerchandise().getCode());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_MONEY_TYPE_COLUMN_NAME, cryptoBrokerWalletAssociatedSetting.getMoneyType().getCode());

        return record;
    }

    private DatabaseTableRecord getCryptoBrokerWalletProviderSettingRecord(CryptoBrokerWalletProviderSetting cryptoBrokerWalletProviderSetting) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_ID_COLUMN_NAME, cryptoBrokerWalletProviderSetting.getId());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_PLUGIN_COLUMN_NAME, cryptoBrokerWalletProviderSetting.getPlugin().toString());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_BROKER_PUBLIC_KEY_COLUMN_NAME, cryptoBrokerWalletProviderSetting.getBrokerPublicKey());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_DESCRIPTION_COLUMN_NAME, cryptoBrokerWalletProviderSetting.getDescription());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_CURRENCY_FROM_COLUMN_NAME, cryptoBrokerWalletProviderSetting.getCurrencyFrom());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_CURRENCY_TO_COLUMN_NAME, cryptoBrokerWalletProviderSetting.getCurrencyTo());


        return record;
    }

    private void executeTransaction(final CryptoBrokerStockTransactionRecord stockTransaction, final TransactionType transactionType, final BalanceType balanceType, final float runningBookBalance, final float runningAvailableBalance) throws CantExecuteCryptoBrokerTransactionException {
        try {
            DatabaseTableRecord stockWalletTransactionRecord = constructStockWalletTransactionRecord(stockTransaction, transactionType, balanceType, runningAvailableBalance, runningBookBalance);
            DatabaseTableRecord stockWalletBalance = constructStockWalletBalanceRecord(stockTransaction, runningAvailableBalance, runningBookBalance);
            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToInsert(getStockWalletTransactionTable(), stockWalletTransactionRecord);

            DatabaseTable databaseTable = getBalancesTable();
            //if (MoneyType.CRYPTO != MoneyType.getByCode(stockTransaction.getMerchandise().getCode()))
            databaseTable.addStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME, stockTransaction.getMerchandise().getCode(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            if (databaseTable.getRecords().isEmpty()) {
                transaction.addRecordToInsert(databaseTable, stockWalletBalance);
            } else {
                transaction.addRecordToUpdate(databaseTable, stockWalletBalance);
            }

            database.executeTransaction(transaction);

        } catch (CantLoadTableToMemoryException e) {
            throw new CantExecuteCryptoBrokerTransactionException("Cant Load Table To Memory", e, "", "");
        } catch (DatabaseTransactionFailedException e) {
            throw new CantExecuteCryptoBrokerTransactionException("Database Transaction Failed", e, "", "");
        }
    }

    private DatabaseTableRecord constructStockWalletTransactionRecord(final CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final float availableRunningBalance, final float bookRunningBalance) {
        DatabaseTableRecord record = getStockWalletTransactionTable().getEmptyRecord();

        record.setUUIDValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getTransactionId());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getTransactionType().getCode());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getAmount().toPlainString());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getMerchandise().getCode());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MONEY_TYPE_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getMoneyType().getCode());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getTransactionType().getCode());
        record.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        record.setLongValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getTimestamp());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getMemo());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getOriginTransaction().getCode());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_PRICE_REFERENCE_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getPriceReference().toPlainString());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_ID_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getOriginTransactionId());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_SEEN_COLUMN_NAME, String.valueOf(cryptoBrokerStockTransactionRecord.getSeen()));

        return record;
    }

    private DatabaseTableRecord constructStockWalletBalanceRecord(final CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, float availableRunningBalance, float bookRunningBalance) {
        DatabaseTableRecord record = getBalancesTable().getEmptyRecord();

        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getMerchandise().getCode());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MONEY_TYPE_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getMoneyType().getCode());
        record.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BROKER_PUBLIC_KEY_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getBrokerPublicKey());

        return record;

    }

    private float calculateAvailableRunningBalanceByMerchandise(final float transactionAmount, String merchandise) throws CantGetBalanceRecordException {
        return getCurrentAvailableBalanceByMerchandise(merchandise) + transactionAmount;
    }

    private float calculateBookRunningBalanceByMerchandise(final float transactionAmount, String merchandise) throws CantGetBalanceRecordException {
        return getCurrentBookBalanceByMerchandise(merchandise) + transactionAmount;
    }

    private float getCurrentAvailableBalanceByMerchandise(String merchandise) throws CantGetBalanceRecordException {
        return getCurrentBalanceByMerchandise(BalanceType.AVAILABLE, merchandise);
    }

    private float getCurrentBookBalanceByMerchandise(String merchandise) throws CantGetBalanceRecordException {
        return getCurrentBalanceByMerchandise(BalanceType.BOOK, merchandise);
    }

    private float getCurrentBalanceByMerchandise(BalanceType balanceType, String merchandise) {
        float balanceAmount = 0;
        try {

            if (balanceType.equals(BalanceType.AVAILABLE))
                balanceAmount = getBalancesByMerchandiseRecord(merchandise).getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME);
            else
                balanceAmount = getBalancesByMerchandiseRecord(merchandise).getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME);

            return balanceAmount;
        } catch (Exception exception) {
            return balanceAmount;
        }
    }

    private DatabaseTableRecord getBalancesByMerchandiseRecord(String merchandise) throws CantGetBalanceRecordException {
        try {
            DatabaseTable balancesTable = getBalancesTable();
            balancesTable.addStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME, merchandise, DatabaseFilterType.EQUAL);
            balancesTable.loadToMemory();
            if (!balancesTable.getRecords().isEmpty()) {
                return balancesTable.getRecords().get(0);
            } else {
                return balancesTable.getRecords().get(0);
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record", exception, "Can't load balance table", "");
        }
    }

    private boolean isTransactionInTable(final String transactionId, final TransactionType transactionType, final BalanceType balanceType) throws CantLoadTableToMemoryException {
        DatabaseTable assetIssuerWalletTable = getStockWalletTransactionTable();
        assetIssuerWalletTable.addStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.addStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.addStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.loadToMemory();
        return !assetIssuerWalletTable.getRecords().isEmpty();
    }

    private DatabaseTable getStockWalletTransactionTable() {
        return database.getTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME);
    }

    private List<CryptoBrokerWalletBalanceRecord> getCurrentBookBalanceByMerchandise() throws CantGetBalanceRecordException {
        return getCurrentBalanceByMerchandise(BalanceType.BOOK);
    }

    private List<CryptoBrokerWalletBalanceRecord> getCurrentAvailableBalanceByMerchandise() throws CantGetBalanceRecordException {
        return getCurrentBalanceByMerchandise(BalanceType.AVAILABLE);
    }

    private List<CryptoBrokerWalletBalanceRecord> getCurrentBalanceByMerchandise(final BalanceType balanceType) throws CantGetBalanceRecordException {
        List<CryptoBrokerWalletBalanceRecord> stockWalletBalances = new ArrayList<>();
        CryptoBrokerWalletBalanceRecordImpl cryptoBrokerWalletBalanceRecord = new CryptoBrokerWalletBalanceRecordImpl();
        if (balanceType.equals(BalanceType.AVAILABLE)) {
            for (DatabaseTableRecord record : getBalancesRecord()) {
                try {
                    if (MoneyType.CRYPTO != MoneyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MONEY_TYPE_COLUMN_NAME))) {
                        cryptoBrokerWalletBalanceRecord.setMerchandise(FiatCurrency.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME)));
                    } else {
                        cryptoBrokerWalletBalanceRecord.setMerchandise(CryptoCurrency.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME)));
                    }
                    cryptoBrokerWalletBalanceRecord.setAvailableBalance(new BigDecimal(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME)));
                    cryptoBrokerWalletBalanceRecord.setBookBalance(new BigDecimal(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME)));
                    cryptoBrokerWalletBalanceRecord.setMoneyType(MoneyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MONEY_TYPE_COLUMN_NAME)));
                    cryptoBrokerWalletBalanceRecord.setBrokerPublicKey(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BROKER_PUBLIC_KEY_COLUMN_NAME));
                    stockWalletBalances.add(cryptoBrokerWalletBalanceRecord);

                } catch (InvalidParameterException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (DatabaseTableRecord record : getBalancesRecord()) {
                try {
                    if (MoneyType.CRYPTO != MoneyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MONEY_TYPE_COLUMN_NAME))) {
                        cryptoBrokerWalletBalanceRecord.setMerchandise(FiatCurrency.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME)));
                    } else {
                        cryptoBrokerWalletBalanceRecord.setMerchandise(CryptoCurrency.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME)));
                    }
                    cryptoBrokerWalletBalanceRecord.setAvailableBalance(new BigDecimal(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME)));
                    cryptoBrokerWalletBalanceRecord.setBookBalance(new BigDecimal(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME)));
                    cryptoBrokerWalletBalanceRecord.setMoneyType(MoneyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MONEY_TYPE_COLUMN_NAME)));
                    cryptoBrokerWalletBalanceRecord.setBrokerPublicKey(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BROKER_PUBLIC_KEY_COLUMN_NAME));
                    stockWalletBalances.add(cryptoBrokerWalletBalanceRecord);

                } catch (InvalidParameterException e) {
                    e.printStackTrace();
                }
            }

        }
        return stockWalletBalances;
    }

    private List<DatabaseTableRecord> getBalancesRecord() throws CantGetBalanceRecordException {
        try {
            DatabaseTable balancesTable = getBalancesTable();
            balancesTable.loadToMemory();
            return balancesTable.getRecords();
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record", exception, "Can't load balance table", "");
        }
    }

    private DatabaseTable getBalancesTable() {
        return database.getTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_TABLE_NAME);
    }
}