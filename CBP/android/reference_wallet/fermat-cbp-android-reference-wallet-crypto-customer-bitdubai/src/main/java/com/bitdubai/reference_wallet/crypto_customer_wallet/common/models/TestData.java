package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.BrokerIdentityBusinessInfo;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.provisory_data.ClauseInformationImpl;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.provisory_data.ContractBasicInformationImpl;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.provisory_data.CryptoCustomerWalletModuleBrokerIdentityBusinessInfo;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.provisory_data.CustomerBrokerNegotiationInformationImpl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by nelson on 07/01/16.
 */
public class TestData {
    public static final String CASH_IN_HAND = MoneyType.CASH_ON_HAND.getFriendlyName();
    public static final String CASH_DELIVERY = MoneyType.CASH_DELIVERY.getFriendlyName();
    public static final String BANK_TRANSFER = MoneyType.BANK.getFriendlyName();
    public static final String CRYPTO_TRANSFER = MoneyType.CRYPTO.getFriendlyName();

    private static final List<String> BROKER_BANK_ACCOUNTS = new ArrayList<>();
    public static final String BROKER_BANK_ACCOUNT_1 = "Banco: BOD\nTipo de cuenta: Corriente\nNro: 0105-2255-2221548739\nCliente: Brokers Asociados";
    public static final String BROKER_BANK_ACCOUNT_2 = "Banco: Provincial\nTipo de cuenta: Ahorro\nNro: 0114-3154-268548735\nCliente: Brokers Asociados";
    public static final String BROKER_BANK_ACCOUNT_3 = "Banco: Banesco\nTipo de cuenta: Corriente\nNro: 1124-0235-9981548701\nCliente: Brokers Asociados";

    public static final String CUSTOMER_BANK_ACCOUNT_1 = "Banco: Venezuela\nTipo de cuenta: Ahorro\nNro: 0001-2051-2221548714\nCliente: Angel Lacret";

    private static final List<String> BROKER_LOCATIONS = new ArrayList<>();
    public static final String BROKER_LOCATION_1 = "C.C. Sambil Maracaibo, Piso 2, Local 5A, al lado de Farmatodo";
    public static final String BROKER_LOCATION_2 = "C.C. Galerias, Piso 3, Local 5A, cerca de la feria de la comida";
    public static final String BROKER_LOCATION_3 = "Av. Padilla, Residencias San Martin, Casa #4-5";

    public static final String CUSTOMER_LOCATION_1 = "Centro Empresarial Totuma, Piso 2, Local 5A";
    public static final String CUSTOMER_LOCATION_2 = "Instituto de Calculo Aplicado, LUZ Facultad de Ingenieria";

    private static final String BROKER_CRYPTO_ADDRESS = "jn384jfnqirfjqn4834232039dj";
    private static final String CUSTOMER_CRYPTO_ADDRESS = "ioajpviq3489f9r8fj208245nds";

    private static List<ContractBasicInformation> contractsHistory;
    private static List<CustomerBrokerNegotiationInformation> openNegotiations;
    private static List<BrokerIdentityBusinessInfo> connectedBrokers;


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
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, CASH_DELIVERY, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PLACE_TO_DELIVER, BROKER_LOCATION_1, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, CRYPTO_TRANSFER, ClauseStatus.DRAFT));
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
        timeInMillisStr = String.valueOf(timeInMillisVal);

        item = new CustomerBrokerNegotiationInformationImpl("jorgeegonzalez", NegotiationStatus.WAITING_FOR_BROKER);
        item.setLastNegotiationUpdateDate(timeInMillisVal);
        item.setExpirationDatetime(timeInMillisVal);
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, FiatCurrency.VENEZUELAN_BOLIVAR.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, FiatCurrency.US_DOLLAR.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, CASH_IN_HAND, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PLACE_TO_DELIVER, BROKER_LOCATION_1, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, BANK_TRANSFER, ClauseStatus.DRAFT));
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
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, BANK_TRANSFER, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_BANK_ACCOUNT, BROKER_BANK_ACCOUNT_1, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, CASH_DELIVERY, ClauseStatus.DRAFT));
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
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, CRYPTO_TRANSFER, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CRYPTO_ADDRESS, BROKER_CRYPTO_ADDRESS, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, BANK_TRANSFER, ClauseStatus.DRAFT));
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
        item.setNote("Nos vemos cerca de la entrada principal. Voy vestido de franela amarílla y pantalón de Jean");
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, CryptoCurrency.BITCOIN.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, FiatCurrency.VENEZUELAN_BOLIVAR.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, CRYPTO_TRANSFER, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CRYPTO_ADDRESS, BROKER_CRYPTO_ADDRESS, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, CASH_DELIVERY, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PLACE_TO_DELIVER, CUSTOMER_LOCATION_1, ClauseStatus.DRAFT));
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
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, CRYPTO_TRANSFER, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CRYPTO_ADDRESS, BROKER_CRYPTO_ADDRESS, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, CRYPTO_TRANSFER, ClauseStatus.DRAFT));
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
        timeInMillisStr = String.valueOf(timeInMillisVal);

        item = new CustomerBrokerNegotiationInformationImpl("Customer 5", NegotiationStatus.WAITING_FOR_CUSTOMER);
        item.setLastNegotiationUpdateDate(timeInMillisVal);
        item.setExpirationDatetime(timeInMillisVal);
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, CryptoCurrency.LITECOIN.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, CryptoCurrency.BITCOIN.getCode(), ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, CRYPTO_TRANSFER, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_CRYPTO_ADDRESS, BROKER_CRYPTO_ADDRESS, ClauseStatus.DRAFT));
        item.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, CRYPTO_TRANSFER, ClauseStatus.DRAFT));
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

            contract = new ContractBasicInformationImpl("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED, false);
            contract.setNegotiationId(openNegotiations.get(0).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("nelsoanlfo", "BTC", "Bank Transfer", "Arg $", ContractStatus.COMPLETED, false);
            contract.setNegotiationId(openNegotiations.get(1).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("neoperol", "USD", "Cash in Hand", "BsF", ContractStatus.COMPLETED, false);
            contract.setNegotiationId(openNegotiations.get(2).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("nairovene", "USD", "Cash Delivery", "BsF", ContractStatus.CANCELLED, false);
            contract.setNegotiationId(openNegotiations.get(3).getNegotiationId());
            contract.setCancellationReason("No se realizo el pago en el tiempo establecido");
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("Luis Pineda", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT, false);
            contract.setNegotiationId(openNegotiations.get(4).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("Carlos Ruiz", "USD", "Bank Transfer", "Col $", ContractStatus.CANCELLED, false);
            contract.setNegotiationId(openNegotiations.get(5).getNegotiationId());
            contract.setCancellationReason("No se entrego la mercancia en el tiempo establecido");
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("josePres", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED, false);
            contract.setNegotiationId(openNegotiations.get(0).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("nairo300", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED, false);
            contract.setNegotiationId(openNegotiations.get(1).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("dbz_brokers", "USD", "Crypto Transfer", "BTC", ContractStatus.CANCELLED, false);
            contract.setNegotiationId(openNegotiations.get(2).getNegotiationId());
            contract.setCancellationReason("Tardo mucho para responder");
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("Mirian Margarita Noguera", "USD", "Crypto Transfer", "BTC", ContractStatus.CANCELLED, false);
            contract.setNegotiationId(openNegotiations.get(3).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT, false);
            contract.setNegotiationId(openNegotiations.get(4).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("nelsoanlfo", "BTC", "Bank Transfer", "Arg $", ContractStatus.CANCELLED, false);
            contract.setNegotiationId(openNegotiations.get(5).getNegotiationId());
            contract.setCancellationReason("No llegamos a un acuerdo con respecto al precio de venta");
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("neoperol", "USD", "Cash in Hand", "BsF", ContractStatus.COMPLETED, false);
            contract.setNegotiationId(openNegotiations.get(0).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("nairovene", "USD", "Cash Delivery", "BsF", ContractStatus.COMPLETED, false);
            contract.setNegotiationId(openNegotiations.get(1).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("Luis Pineda", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT, false);
            contract.setNegotiationId(openNegotiations.get(2).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("Carlos Ruiz", "USD", "Crypto Transfer", "BTC", ContractStatus.CANCELLED, false);
            contract.setNegotiationId(openNegotiations.get(3).getNegotiationId());
            contract.setCancellationReason("No se realizo el pago en el tiempo establecido");
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("josePres", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED, false);
            contract.setNegotiationId(openNegotiations.get(4).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("nairo300", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED, false);
            contract.setNegotiationId(openNegotiations.get(5).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("dbz_brokers", "USD", "Crypto Transfer", "BTC", ContractStatus.PENDING_PAYMENT, false);
            contract.setNegotiationId(openNegotiations.get(0).getNegotiationId());
            contractsHistory.add(contract);
            contract = new ContractBasicInformationImpl("Mirian Margarita Noguera", "USD", "Crypto Transfer", "BTC", ContractStatus.COMPLETED, false);
            contract.setNegotiationId(openNegotiations.get(1).getNegotiationId());
            contractsHistory.add(contract);
        }

        return contractsHistory;
    }

    // TODO Revisar Franklin
    public static EmptyCustomerBrokerNegotiationInformation newEmptyNegotiationInformation() {
        return new EmptyCustomerBrokerNegotiationInformation();
    }

    public static List<BrokerIdentityBusinessInfo> getBrokerListTestData() {
        if (connectedBrokers == null) {
            connectedBrokers = new ArrayList<>();

            BrokerIdentityBusinessInfo broker;
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("elBrokerVerdugo", new byte[0], "elBrokerVerdugo", CryptoCurrency.BITCOIN);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("elBrokerVerdugo", new byte[0], "elBrokerVerdugo", FiatCurrency.US_DOLLAR);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("Brokers de Argentina", new byte[0], "BrokersDeArgentina", FiatCurrency.US_DOLLAR);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("Brokers de Argentina", new byte[0], "BrokersDeArgentina", FiatCurrency.AUSTRALIAN_DOLLAR);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("Brokers de Argentina", new byte[0], "BrokersDeArgentina", CryptoCurrency.BITCOIN);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("leonAcostaBroker", new byte[0], "leonAcostaBroker", CryptoCurrency.BITCOIN);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("leonAcostaBroker", new byte[0], "leonAcostaBroker", CryptoCurrency.BITCOIN);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("leonAcostaBroker", new byte[0], "leonAcostaBroker", CryptoCurrency.LITECOIN);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("leonAcostaBroker", new byte[0], "leonAcostaBroker", FiatCurrency.EURO);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("Nelson Ramirez", new byte[0], "NelsonRamirez", FiatCurrency.US_DOLLAR);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("Luis Molina", new byte[0], "LuisMolina", FiatCurrency.CHINESE_YUAN);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("Luis Molina", new byte[0], "LuisMolina", FiatCurrency.ARGENTINE_PESO);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("Mayorista La Asuncion", new byte[0], "MayoristaLaAsuncion", FiatCurrency.BRITISH_POUND);
            connectedBrokers.add(broker);
            broker = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo("brokers_mcbo", new byte[0], "brokers_mcbo", FiatCurrency.US_DOLLAR);
            connectedBrokers.add(broker);
        }

        return connectedBrokers;
    }

    //LIST OF BROKER CURRENCY QUOTATUION  TEST
    public static List<BrokerCurrencyQuotationImpl> getMarketRateForCurrencyTest() {

        List<BrokerCurrencyQuotationImpl> list = new ArrayList<>();

        list.add(new BrokerCurrencyQuotationImpl(CryptoCurrency.BITCOIN.getCode(), FiatCurrency.ARGENTINE_PESO.getCode(), "100000"));
        list.add(new BrokerCurrencyQuotationImpl(CryptoCurrency.BITCOIN.getCode(), FiatCurrency.VENEZUELAN_BOLIVAR.getCode(), "350000"));
        list.add(new BrokerCurrencyQuotationImpl(CryptoCurrency.BITCOIN.getCode(), FiatCurrency.US_DOLLAR.getCode(), "410"));
        list.add(new BrokerCurrencyQuotationImpl(CryptoCurrency.BITCOIN.getCode(), CryptoCurrency.LITECOIN.getCode(), "130"));

        list.add(new BrokerCurrencyQuotationImpl(FiatCurrency.US_DOLLAR.getCode(), FiatCurrency.VENEZUELAN_BOLIVAR.getCode(), "950"));
        list.add(new BrokerCurrencyQuotationImpl(FiatCurrency.US_DOLLAR.getCode(), FiatCurrency.ARGENTINE_PESO.getCode(), "390"));

        return list;
    }
}
