package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.provisory_data.ClauseInformationImpl;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.provisory_data.ContractBasicInformationImpl;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.provisory_data.CustomerBrokerNegotiationInformationImpl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Nelson Ramirez
 * @since 07/01/16.
 */
public class TestData {

    public static final String BROKER_BANK_ACCOUNT_1 = "Banco: BOD\nTipo de cuenta: Corriente\nNro: 0105-2255-2221548739\nCliente: Brokers Asociados";

    public static final String CUSTOMER_BANK_ACCOUNT_1 = "Banco: Venezuela\nTipo de cuenta: Ahorro\nNro: 0001-2051-2221548714\nCliente: Angel Lacret";

    public static final String BROKER_LOCATION_1 = "C.C. Sambil Maracaibo, Piso 2, Local 5A, al lado de Farmatodo";

    public static final String CUSTOMER_LOCATION_2 = "Instituto de Calculo Aplicado, LUZ Facultad de Ingenieria";

    private static final String BROKER_CRYPTO_ADDRESS = "jn384jfnqirfjqn4834232039dj";
    private static final String CUSTOMER_CRYPTO_ADDRESS = "ioajpviq3489f9r8fj208245nds";

    private static List<ContractBasicInformation> contractsHistory;
    private static List<CustomerBrokerNegotiationInformation> openNegotiations;


    public static List<CustomerBrokerNegotiationInformation> getOpenNegotiations(NegotiationStatus status) {
        List<CustomerBrokerNegotiationInformation> openNegotiationsTestData = getOpenNegotiationsTestData();
        List<CustomerBrokerNegotiationInformation> data = new ArrayList<>();

        for (CustomerBrokerNegotiationInformation negotiation : openNegotiationsTestData) {
            if (negotiation.getStatus().equals(status))
                data.add(negotiation);
        }

        return data;
    }

    public static List<ContractBasicInformation> getContractsHistory(ContractStatus status) {
        List<ContractBasicInformation> openNegotiationsTestData = getContractHistoryTestData();
        List<ContractBasicInformation> data = new ArrayList<>();

        if (status == null) {
            for (ContractBasicInformation information : openNegotiationsTestData) {
                if (information.getStatus().equals(ContractStatus.CANCELLED))
                    data.add(information);
                else if (information.getStatus().equals(ContractStatus.COMPLETED))
                    data.add(information);
            }

        } else {
            for (ContractBasicInformation information : openNegotiationsTestData) {
                if (information.getStatus().equals(status))
                    data.add(information);
            }
        }

        return data;
    }

    public static List<ContractBasicInformation> getContractsWaitingForBroker() {
        List<ContractBasicInformation> openNegotiationsTestData = getContractHistoryTestData();
        List<ContractBasicInformation> data = new ArrayList<>();

        for (ContractBasicInformation information : openNegotiationsTestData) {
            if (information.getStatus().equals(ContractStatus.PENDING_MERCHANDISE))
                data.add(information);
            else if (information.getStatus().equals(ContractStatus.PAYMENT_SUBMIT))
                data.add(information);
        }

        return data;
    }

    public static List<ContractBasicInformation> getContractsWaitingForCustomer() {
        List<ContractBasicInformation> openNegotiationsTestData = getContractHistoryTestData();
        List<ContractBasicInformation> data = new ArrayList<>();

        for (ContractBasicInformation information : openNegotiationsTestData) {
            if (information.getStatus().equals(ContractStatus.PENDING_PAYMENT))
                data.add(information);
            else if (information.getStatus().equals(ContractStatus.MERCHANDISE_SUBMIT))
                data.add(information);
        }

        return data;
    }

    public static List<StockStatisticsData> getStockStadisticsData() {
        ArrayList<StockStatisticsData> stockStatisticsData = new ArrayList<>();
        stockStatisticsData.add(new StockStatisticsData(FiatCurrency.VENEZUELAN_BOLIVAR, 14));
        stockStatisticsData.add(new StockStatisticsData(CryptoCurrency.BITCOIN, 3));
        stockStatisticsData.add(new StockStatisticsData(FiatCurrency.US_DOLLAR, 1));

        return stockStatisticsData;
    }

    private static List<CustomerBrokerNegotiationInformation> getOpenNegotiationsTestData() {
        if (openNegotiations != null) {
            return openNegotiations;
        }

        Random random = new Random(321515131);
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        float currencyQtyVal = random.nextFloat() * 100;
        float exchangeRateVal = random.nextFloat();
        String customerCurrencyQty = decimalFormat.format(currencyQtyVal);
        String exchangeRate = decimalFormat.format(exchangeRateVal);
        String brokerCurrencyQty = decimalFormat.format(currencyQtyVal * exchangeRateVal);
        long timeInMillisVal = System.currentTimeMillis();
        String timeInMillisStr = String.valueOf(timeInMillisVal);

        openNegotiations = new ArrayList<>();
        CustomerBrokerNegotiationInformationImpl item;

        item = new CustomerBrokerNegotiationInformationImpl("nelsonalfo", NegotiationStatus.WAITING_FOR_BROKER);
        item.setLastNegotiationUpdateDate(timeInMillisVal);
        item.setExpirationDatetime(timeInMillisVal);
        item.setNote("Le dices al portero que vas a nombre del señor Bastidas");
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, FiatCurrency.VENEZUELAN_BOLIVAR.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, CryptoCurrency.BITCOIN.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, MoneyType.CASH_DELIVERY.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PLACE_TO_DELIVER, BROKER_LOCATION_1, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, MoneyType.CRYPTO.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CRYPTO_ADDRESS, CUSTOMER_CRYPTO_ADDRESS, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));
        openNegotiations.add(item);

        currencyQtyVal = random.nextFloat() * 100;
        exchangeRateVal = random.nextFloat();
        customerCurrencyQty = decimalFormat.format(currencyQtyVal);
        exchangeRate = decimalFormat.format(exchangeRateVal);
        brokerCurrencyQty = decimalFormat.format(currencyQtyVal * exchangeRateVal);
        timeInMillisVal = System.currentTimeMillis();

        item = new CustomerBrokerNegotiationInformationImpl("Mirian Noguera", NegotiationStatus.WAITING_FOR_BROKER);
        item.setLastNegotiationUpdateDate(timeInMillisVal);
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, FiatCurrency.VENEZUELAN_BOLIVAR.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, FiatCurrency.US_DOLLAR.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        openNegotiations.add(item);

        currencyQtyVal = random.nextFloat() * 100;
        exchangeRateVal = random.nextFloat();
        customerCurrencyQty = decimalFormat.format(currencyQtyVal);
        exchangeRate = decimalFormat.format(exchangeRateVal);
        brokerCurrencyQty = decimalFormat.format(currencyQtyVal * exchangeRateVal);
        timeInMillisVal = System.currentTimeMillis();

        item = new CustomerBrokerNegotiationInformationImpl("Adriana Moronta", NegotiationStatus.WAITING_FOR_BROKER);
        item.setLastNegotiationUpdateDate(timeInMillisVal);
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, CryptoCurrency.BITCOIN.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, FiatCurrency.VENEZUELAN_BOLIVAR.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        openNegotiations.add(item);

        currencyQtyVal = random.nextFloat() * 100;
        exchangeRateVal = random.nextFloat();
        customerCurrencyQty = decimalFormat.format(currencyQtyVal);
        exchangeRate = decimalFormat.format(exchangeRateVal);
        brokerCurrencyQty = decimalFormat.format(currencyQtyVal * exchangeRateVal);
        timeInMillisVal = System.currentTimeMillis();
        timeInMillisStr = String.valueOf(timeInMillisVal);

        item = new CustomerBrokerNegotiationInformationImpl("jorgeegonzalez", NegotiationStatus.WAITING_FOR_BROKER);
        item.setLastNegotiationUpdateDate(timeInMillisVal);
        item.setExpirationDatetime(timeInMillisVal);
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, FiatCurrency.VENEZUELAN_BOLIVAR.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, FiatCurrency.US_DOLLAR.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, MoneyType.CASH_ON_HAND.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PLACE_TO_DELIVER, BROKER_LOCATION_1, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, MoneyType.BANK.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_BANK_ACCOUNT, CUSTOMER_BANK_ACCOUNT_1, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));
        openNegotiations.add(item);

        currencyQtyVal = random.nextFloat() * 100;
        exchangeRateVal = random.nextFloat();
        customerCurrencyQty = decimalFormat.format(currencyQtyVal);
        exchangeRate = decimalFormat.format(exchangeRateVal);
        brokerCurrencyQty = decimalFormat.format(currencyQtyVal * exchangeRateVal);
        timeInMillisVal = System.currentTimeMillis();
        timeInMillisStr = String.valueOf(timeInMillisVal);

        item = new CustomerBrokerNegotiationInformationImpl("Matias Furzyfer", NegotiationStatus.WAITING_FOR_BROKER);
        item.setLastNegotiationUpdateDate(timeInMillisVal);
        item.setExpirationDatetime(timeInMillisVal);
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, FiatCurrency.ARGENTINE_PESO.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, FiatCurrency.US_DOLLAR.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, MoneyType.BANK.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_BANK_ACCOUNT, BROKER_BANK_ACCOUNT_1, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, MoneyType.CASH_DELIVERY.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PLACE_TO_DELIVER, CUSTOMER_LOCATION_2, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));
        openNegotiations.add(item);


        currencyQtyVal = random.nextFloat() * 100;
        exchangeRateVal = random.nextFloat();
        customerCurrencyQty = decimalFormat.format(currencyQtyVal);
        exchangeRate = decimalFormat.format(exchangeRateVal);
        brokerCurrencyQty = decimalFormat.format(currencyQtyVal * exchangeRateVal);
        timeInMillisVal = System.currentTimeMillis();
        timeInMillisStr = String.valueOf(timeInMillisVal);

        item = new CustomerBrokerNegotiationInformationImpl("neoperol", NegotiationStatus.WAITING_FOR_BROKER);
        item.setLastNegotiationUpdateDate(timeInMillisVal);
        item.setExpirationDatetime(timeInMillisVal);
        item.setNote("Nos vemos cerca de la entrada principal. Voy vestido de franela amarílla y pantalón de Jean");
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, CryptoCurrency.BITCOIN.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, FiatCurrency.US_DOLLAR.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, MoneyType.CRYPTO.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CRYPTO_ADDRESS, BROKER_CRYPTO_ADDRESS, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, MoneyType.BANK.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_BANK_ACCOUNT, CUSTOMER_BANK_ACCOUNT_1, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));
        openNegotiations.add(item);


        currencyQtyVal = random.nextFloat() * 100;
        exchangeRateVal = random.nextFloat();
        customerCurrencyQty = decimalFormat.format(currencyQtyVal);
        exchangeRate = decimalFormat.format(exchangeRateVal);
        brokerCurrencyQty = decimalFormat.format(currencyQtyVal * exchangeRateVal);
        timeInMillisVal = System.currentTimeMillis();
        timeInMillisStr = String.valueOf(timeInMillisVal);

        item = new CustomerBrokerNegotiationInformationImpl("Nelson Orlando", NegotiationStatus.WAITING_FOR_CUSTOMER);
        item.setLastNegotiationUpdateDate(timeInMillisVal);
        item.setExpirationDatetime(timeInMillisVal);
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, CryptoCurrency.BITCOIN.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, FiatCurrency.VENEZUELAN_BOLIVAR.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, MoneyType.CRYPTO.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CRYPTO_ADDRESS, BROKER_CRYPTO_ADDRESS, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, MoneyType.CASH_DELIVERY.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PLACE_TO_DELIVER, CUSTOMER_LOCATION_2, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));
        openNegotiations.add(item);

        currencyQtyVal = random.nextFloat() * 100;
        exchangeRateVal = random.nextFloat();
        customerCurrencyQty = decimalFormat.format(currencyQtyVal);
        exchangeRate = decimalFormat.format(exchangeRateVal);
        brokerCurrencyQty = decimalFormat.format(currencyQtyVal * exchangeRateVal);
        timeInMillisVal = System.currentTimeMillis();
        timeInMillisStr = String.valueOf(timeInMillisVal);

        item = new CustomerBrokerNegotiationInformationImpl("Customer 5", NegotiationStatus.WAITING_FOR_CUSTOMER);
        item.setLastNegotiationUpdateDate(timeInMillisVal);
        item.setExpirationDatetime(timeInMillisVal);
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, CryptoCurrency.LITECOIN.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, CryptoCurrency.BITCOIN.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, MoneyType.CRYPTO.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CRYPTO_ADDRESS, BROKER_CRYPTO_ADDRESS, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, MoneyType.CRYPTO.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CRYPTO_ADDRESS, CUSTOMER_CRYPTO_ADDRESS, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));
        openNegotiations.add(item);

        return openNegotiations;
    }

    private static List<ContractBasicInformation> getContractHistoryTestData() {
        if (contractsHistory == null) {
            List<CustomerBrokerNegotiationInformation> openNegotiations = getOpenNegotiationsTestData();

            ContractBasicInformationImpl contract;
            contractsHistory = new ArrayList<>();

            contract = new ContractBasicInformationImpl("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED, null, null);
            contract.setNegotiationId(openNegotiations.get(0).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("nelsoanlfo", "BTC", "Bank Transfer", "Arg $", ContractStatus.COMPLETED, null, null);
            contract.setNegotiationId(openNegotiations.get(1).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("neoperol", "USD", "Cash in Hand", "BsF", ContractStatus.COMPLETED, null, null);
            contract.setNegotiationId(openNegotiations.get(2).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("nairovene", "USD", "Cash Delivery", "BsF", ContractStatus.CANCELLED, null, null);
            contract.setNegotiationId(openNegotiations.get(3).getNegotiationId());
            contract.setCancellationReason("No se realizo el pago en el tiempo establecido");
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("Luis Pineda", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT, null, null);
            contract.setNegotiationId(openNegotiations.get(4).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("Carlos Ruiz", "USD", "Bank Transfer", "Col $", ContractStatus.CANCELLED, null, null);
            contract.setNegotiationId(openNegotiations.get(5).getNegotiationId());
            contract.setCancellationReason("No se entrego la mercancia en el tiempo establecido");
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("josePres", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED, null, null);
            contract.setNegotiationId(openNegotiations.get(0).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("nairo300", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED, null, null);
            contract.setNegotiationId(openNegotiations.get(1).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("dbz_brokers", "USD", "Crypto Transfer", "BTC", ContractStatus.CANCELLED, null, null);
            contract.setNegotiationId(openNegotiations.get(2).getNegotiationId());
            contract.setCancellationReason("Tardo mucho para responder");
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("Mirian Margarita Noguera", "USD", "Crypto Transfer", "BTC", ContractStatus.CANCELLED, null, null);
            contract.setNegotiationId(openNegotiations.get(3).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT, null, null);
            contract.setNegotiationId(openNegotiations.get(4).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("nelsoanlfo", "BTC", "Bank Transfer", "Arg $", ContractStatus.CANCELLED, null, null);
            contract.setNegotiationId(openNegotiations.get(5).getNegotiationId());
            contract.setCancellationReason("No llegamos a un acuerdo con respecto al precio de venta");
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("neoperol", "USD", "Cash in Hand", "BsF", ContractStatus.COMPLETED, null, null);
            contract.setNegotiationId(openNegotiations.get(0).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("nairovene", "USD", "Cash Delivery", "BsF", ContractStatus.COMPLETED, null, null);
            contract.setNegotiationId(openNegotiations.get(1).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("Luis Pineda", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT, null, null);
            contract.setNegotiationId(openNegotiations.get(2).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("Carlos Ruiz", "USD", "Crypto Transfer", "BTC", ContractStatus.CANCELLED, null, null);
            contract.setNegotiationId(openNegotiations.get(3).getNegotiationId());
            contract.setCancellationReason("No se realizo el pago en el tiempo establecido");
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("josePres", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED, null, null);
            contract.setNegotiationId(openNegotiations.get(4).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("nairo300", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED, null, null);
            contract.setNegotiationId(openNegotiations.get(5).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("dbz_brokers", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT, null, null);
            contract.setNegotiationId(openNegotiations.get(0).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("Mirian Margarita Noguera", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED, null, null);
            contract.setNegotiationId(openNegotiations.get(1).getNegotiationId());
            contractsHistory.add(contract);
        }

        return contractsHistory;
    }

    public static List<EarningsPair> getEarningsPairs() {
        ArrayList<EarningsPair> earningsPairs = new ArrayList<>();
        earningsPairs.add(new EarningsPairTestData(FiatCurrency.US_DOLLAR, FiatCurrency.VENEZUELAN_BOLIVAR));
        earningsPairs.add(new EarningsPairTestData(FiatCurrency.US_DOLLAR, CryptoCurrency.BITCOIN));
        earningsPairs.add(new EarningsPairTestData(CryptoCurrency.BITCOIN, FiatCurrency.VENEZUELAN_BOLIVAR));

        return earningsPairs;
    }

    public static List<EarningTestData> getEarnings(Currency currency, int timeFilter) {
        ArrayList<EarningTestData> dataArrayList = new ArrayList<>();
        double previousValue;
        EarningTestData earningTestData;

        Calendar calendar = Calendar.getInstance();

        if (currency.equals(CryptoCurrency.BITCOIN)) {
            calendar.add(timeFilter, -1);
            earningTestData = new EarningTestData(currency, 0.1, 0.1, calendar.getTimeInMillis());
            previousValue = earningTestData.getEarningValue();
            dataArrayList.add(earningTestData);

            calendar.add(timeFilter, -1);
            earningTestData = new EarningTestData(currency, 0.2, previousValue, calendar.getTimeInMillis());
            previousValue = earningTestData.getEarningValue();
            dataArrayList.add(earningTestData);

            calendar.add(timeFilter, -1);
            earningTestData = new EarningTestData(currency, 1.2, previousValue, calendar.getTimeInMillis());
            previousValue = earningTestData.getEarningValue();
            dataArrayList.add(earningTestData);

            calendar.add(timeFilter, -1);
            earningTestData = new EarningTestData(currency, 5.2, previousValue, calendar.getTimeInMillis());
            previousValue = earningTestData.getEarningValue();
            dataArrayList.add(earningTestData);

            calendar.add(timeFilter, -1);
            earningTestData = new EarningTestData(currency, 1.2, previousValue, calendar.getTimeInMillis());
            previousValue = earningTestData.getEarningValue();
            dataArrayList.add(earningTestData);

            calendar.add(timeFilter, -1);
            earningTestData = new EarningTestData(currency, 0.02, previousValue, calendar.getTimeInMillis());
            previousValue = earningTestData.getEarningValue();
            dataArrayList.add(earningTestData);

            calendar.add(timeFilter, -1);
            earningTestData = new EarningTestData(currency, 0.122, previousValue, calendar.getTimeInMillis());
            previousValue = earningTestData.getEarningValue();
            dataArrayList.add(earningTestData);

            calendar.add(timeFilter, -1);
            earningTestData = new EarningTestData(currency, 0.5465482, previousValue, calendar.getTimeInMillis());
            dataArrayList.add(earningTestData);


        } else if (currency.equals(FiatCurrency.US_DOLLAR)) {

            calendar.add(timeFilter, -1);
            earningTestData = new EarningTestData(currency, 12330.11, 12330.11, calendar.getTimeInMillis());
            previousValue = earningTestData.getEarningValue();
            dataArrayList.add(earningTestData);

            calendar.add(timeFilter, -1);
            earningTestData = new EarningTestData(currency, 2340.9552, previousValue, calendar.getTimeInMillis());
            previousValue = earningTestData.getEarningValue();
            dataArrayList.add(earningTestData);

            calendar.add(timeFilter, -1);
            earningTestData = new EarningTestData(currency, 1231.88002, previousValue, calendar.getTimeInMillis());
            previousValue = earningTestData.getEarningValue();
            dataArrayList.add(earningTestData);

            calendar.add(timeFilter, -1);
            earningTestData = new EarningTestData(currency, 51.12342, previousValue, calendar.getTimeInMillis());
            dataArrayList.add(earningTestData);


        } else if (currency.equals(FiatCurrency.VENEZUELAN_BOLIVAR)) {

            calendar.add(timeFilter, -1);
            earningTestData = new EarningTestData(currency, 60230.112123, 60230.112123, calendar.getTimeInMillis());
            previousValue = earningTestData.getEarningValue();
            dataArrayList.add(earningTestData);

            calendar.add(timeFilter, -1);
            earningTestData = new EarningTestData(currency, 160230.456456, previousValue, calendar.getTimeInMillis());
            dataArrayList.add(earningTestData);
        }

        return dataArrayList;
    }

    public static double getCurrentEarning(Currency currency) {
        if (currency.equals(CryptoCurrency.BITCOIN)) {
            return 2.25;

        } else if (currency.equals(FiatCurrency.US_DOLLAR)) {
            return 6598.25;

        } else if (currency.equals(FiatCurrency.VENEZUELAN_BOLIVAR)) {
            return 6598.25;
        }

        return 0.0;
    }
}
