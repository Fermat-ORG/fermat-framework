package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.util.NegotiationClauseHelper;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Random;

import provisory_data.ClauseInformationImpl;
import provisory_data.CustomerBrokerNegotiationInformationImpl;


/**
 * Created by nelsonalfo on 29/07/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerBrokerSaleNegotiationImplTest {
    public static final String BROKER_LOCATION_1 = "C.C. Sambil Maracaibo, Piso 2, Local 5A, al lado de Farmatodo";
    private static final String CUSTOMER_CRYPTO_ADDRESS = "ioajpviq3489f9r8fj208245nds";
    final String CANCELLATION_REASON = "Cancellation reason";
    final String MEMO = "Test Note";

    Random random = new Random(321515131);
    DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

    float currencyQtyVal;
    float exchangeRateVal;
    String customerCurrencyQty;
    String exchangeRate;
    String brokerCurrencyQty;
    long timeInMillisVal;
    String timeInMillisStr;

    @Before
    public void setUp() {
        currencyQtyVal = random.nextFloat() * 100;
        exchangeRateVal = random.nextFloat();
        customerCurrencyQty = decimalFormat.format(currencyQtyVal);
        exchangeRate = decimalFormat.format(exchangeRateVal);
        brokerCurrencyQty = decimalFormat.format(currencyQtyVal * exchangeRateVal);
        timeInMillisVal = System.currentTimeMillis();
        timeInMillisStr = String.valueOf(timeInMillisVal);
    }

    @Test
    public void givenNegotiationInfoIsEqual_thenDataHasChangedMustBeFalse() throws Exception {

        CustomerBrokerSaleNegotiationMock actualNegotiationInfo;
        actualNegotiationInfo = new CustomerBrokerSaleNegotiationMock("brokerPK", "customerPK", NegotiationStatus.WAITING_FOR_BROKER);
        actualNegotiationInfo.setLastNegotiationUpdateDate(timeInMillisVal);
        actualNegotiationInfo.setMemo(MEMO);
        actualNegotiationInfo.setCancelReason(CANCELLATION_REASON);
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, FiatCurrency.VENEZUELAN_BOLIVAR.getCode(), ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, CryptoCurrency.BITCOIN.getCode(), ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, MoneyType.CASH_DELIVERY.getCode(), ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.BROKER_PLACE_TO_DELIVER, BROKER_LOCATION_1, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, MoneyType.CRYPTO.getCode(), ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.CUSTOMER_CRYPTO_ADDRESS, CUSTOMER_CRYPTO_ADDRESS, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.BROKER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT), ""));

        CustomerBrokerNegotiationInformationImpl changedNegotiationInfo;
        changedNegotiationInfo = new CustomerBrokerNegotiationInformationImpl("nelsonalfo", NegotiationStatus.WAITING_FOR_BROKER);
        changedNegotiationInfo.setLastNegotiationUpdateDate(timeInMillisVal);
        changedNegotiationInfo.setExpirationDatetime(timeInMillisVal);
        changedNegotiationInfo.setNote(MEMO);
        changedNegotiationInfo.setCancelReason(CANCELLATION_REASON);
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, FiatCurrency.VENEZUELAN_BOLIVAR.getCode(), ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, CryptoCurrency.BITCOIN.getCode(), ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, MoneyType.CASH_DELIVERY.getCode(), ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.BROKER_PLACE_TO_DELIVER, BROKER_LOCATION_1, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, MoneyType.CRYPTO.getCode(), ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CRYPTO_ADDRESS, CUSTOMER_CRYPTO_ADDRESS, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.BROKER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));


        CustomerBrokerSaleNegotiationImpl expectedNegotiationInfo;
        expectedNegotiationInfo = new CustomerBrokerSaleNegotiationImpl(actualNegotiationInfo);
        expectedNegotiationInfo.changeInfo(changedNegotiationInfo);
        Assertions.assertThat(expectedNegotiationInfo.dataHasChanged()).isFalse();

        actualNegotiationInfo.setCancelReason(null);
        changedNegotiationInfo.setCancelReason(null);
        expectedNegotiationInfo = new CustomerBrokerSaleNegotiationImpl(actualNegotiationInfo);
        expectedNegotiationInfo.changeInfo(changedNegotiationInfo);
        Assertions.assertThat(expectedNegotiationInfo.dataHasChanged()).isFalse();

        actualNegotiationInfo.setMemo(null);
        changedNegotiationInfo.setMemo(null);
        expectedNegotiationInfo = new CustomerBrokerSaleNegotiationImpl(actualNegotiationInfo);
        expectedNegotiationInfo.changeInfo(changedNegotiationInfo);
        Assertions.assertThat(expectedNegotiationInfo.dataHasChanged()).isFalse();
    }

    @Test
    public void givenMemoAndCancellationReasonNowIsNull_thenDataHasChangedMustBeTrue() throws Exception {

        CustomerBrokerSaleNegotiationMock actualNegotiationInfo;
        actualNegotiationInfo = new CustomerBrokerSaleNegotiationMock("brokerPK", "customerPK", NegotiationStatus.WAITING_FOR_BROKER);
        actualNegotiationInfo.setLastNegotiationUpdateDate(timeInMillisVal);
        actualNegotiationInfo.setMemo(MEMO);
        actualNegotiationInfo.setCancelReason(CANCELLATION_REASON);
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, FiatCurrency.VENEZUELAN_BOLIVAR.getCode(), ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, CryptoCurrency.BITCOIN.getCode(), ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, MoneyType.CASH_DELIVERY.getCode(), ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.BROKER_PLACE_TO_DELIVER, BROKER_LOCATION_1, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, MoneyType.CRYPTO.getCode(), ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.CUSTOMER_CRYPTO_ADDRESS, CUSTOMER_CRYPTO_ADDRESS, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.BROKER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT), ""));

        CustomerBrokerNegotiationInformationImpl changedNegotiationInfo;
        changedNegotiationInfo = new CustomerBrokerNegotiationInformationImpl("nelsonalfo", NegotiationStatus.WAITING_FOR_BROKER);
        changedNegotiationInfo.setLastNegotiationUpdateDate(timeInMillisVal);
        changedNegotiationInfo.setExpirationDatetime(timeInMillisVal);
        changedNegotiationInfo.setMemo(null);
        changedNegotiationInfo.setCancelReason(null);
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, FiatCurrency.VENEZUELAN_BOLIVAR.getCode(), ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, CryptoCurrency.BITCOIN.getCode(), ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, MoneyType.CASH_DELIVERY.getCode(), ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.BROKER_PLACE_TO_DELIVER, BROKER_LOCATION_1, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, MoneyType.CRYPTO.getCode(), ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CRYPTO_ADDRESS, CUSTOMER_CRYPTO_ADDRESS, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.BROKER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));


        CustomerBrokerSaleNegotiationImpl expectedNegotiationInfo;
        expectedNegotiationInfo = new CustomerBrokerSaleNegotiationImpl(actualNegotiationInfo);
        expectedNegotiationInfo.changeInfo(changedNegotiationInfo);
        Assertions.assertThat(expectedNegotiationInfo.dataHasChanged()).isTrue();

        actualNegotiationInfo.setMemo(null);
        changedNegotiationInfo.setMemo(MEMO);
        expectedNegotiationInfo = new CustomerBrokerSaleNegotiationImpl(actualNegotiationInfo);
        expectedNegotiationInfo.changeInfo(changedNegotiationInfo);
        Assertions.assertThat(expectedNegotiationInfo.dataHasChanged()).isTrue();

        actualNegotiationInfo.setCancelReason(null);
        changedNegotiationInfo.setCancelReason(CANCELLATION_REASON);
        expectedNegotiationInfo = new CustomerBrokerSaleNegotiationImpl(actualNegotiationInfo);
        expectedNegotiationInfo.changeInfo(changedNegotiationInfo);
        Assertions.assertThat(expectedNegotiationInfo.dataHasChanged()).isTrue();
    }

    @Test
    public void givenAClauseChanged_thenDataHasChangedMustBeTrue() throws Exception {

        CustomerBrokerSaleNegotiationMock actualNegotiationInfo;
        actualNegotiationInfo = new CustomerBrokerSaleNegotiationMock("brokerPK", "customerPK", NegotiationStatus.WAITING_FOR_BROKER);
        actualNegotiationInfo.setLastNegotiationUpdateDate(timeInMillisVal);
        actualNegotiationInfo.setMemo(null);
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, FiatCurrency.VENEZUELAN_BOLIVAR.getCode(), ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, CryptoCurrency.BITCOIN.getCode(), ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, MoneyType.CASH_DELIVERY.getCode(), ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.BROKER_PLACE_TO_DELIVER, BROKER_LOCATION_1, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, MoneyType.CRYPTO.getCode(), ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.CUSTOMER_CRYPTO_ADDRESS, CUSTOMER_CRYPTO_ADDRESS, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.BROKER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT), ""));
        actualNegotiationInfo.addClause(new ClauseImpl(new ClauseInformationImpl(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT), ""));

        CustomerBrokerNegotiationInformationImpl changedNegotiationInfo;
        changedNegotiationInfo = new CustomerBrokerNegotiationInformationImpl("nelsonalfo", NegotiationStatus.WAITING_FOR_BROKER);
        changedNegotiationInfo.setLastNegotiationUpdateDate(timeInMillisVal);
        changedNegotiationInfo.setExpirationDatetime(timeInMillisVal);
        changedNegotiationInfo.setNote(null);
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, FiatCurrency.VENEZUELAN_BOLIVAR.getCode(), ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY_QUANTITY, brokerCurrencyQty, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, CryptoCurrency.BITCOIN.getCode(), ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerCurrencyQty, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_PAYMENT_METHOD, MoneyType.CASH_ON_HAND.getCode(), ClauseStatus.CHANGED));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.BROKER_PLACE_TO_DELIVER, BROKER_LOCATION_1, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, MoneyType.CRYPTO.getCode(), ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_CRYPTO_ADDRESS, CUSTOMER_CRYPTO_ADDRESS, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.BROKER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));
        changedNegotiationInfo.addClause(new ClauseInformationImpl(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, timeInMillisStr, ClauseStatus.DRAFT));


        CustomerBrokerSaleNegotiationImpl expectedNegotiationInfo = new CustomerBrokerSaleNegotiationImpl(actualNegotiationInfo);
        expectedNegotiationInfo.changeInfo(changedNegotiationInfo);
        Assertions.assertThat(expectedNegotiationInfo.dataHasChanged()).isTrue();

        final Collection<Clause> clauses = expectedNegotiationInfo.getClauses();
        Clause changedClause = NegotiationClauseHelper.getNegotiationClause(clauses, ClauseType.CUSTOMER_PAYMENT_METHOD);

        assert changedClause != null;
        Assertions.assertThat(changedClause.getValue()).isEqualTo(MoneyType.CASH_ON_HAND.getCode());
        Assertions.assertThat(changedClause.getStatus()).isEqualTo(ClauseStatus.CHANGED);
    }
}