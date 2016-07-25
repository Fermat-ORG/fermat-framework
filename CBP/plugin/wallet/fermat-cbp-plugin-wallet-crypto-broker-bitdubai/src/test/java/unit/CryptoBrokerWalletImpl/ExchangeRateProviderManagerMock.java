package unit.CryptoBrokerWalletImpl;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.all_definition.utils.ExchangeRateImpl;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;

import java.util.Calendar;
import java.util.Collection;
import java.util.UUID;


/**
 * Created by nelsonalfo on 25/07/16.
 */
public class ExchangeRateProviderManagerMock implements CurrencyExchangeRateProviderManager {
    private final UUID providerId;
    private final ExchangeRate currentExchangeRate;

    public ExchangeRateProviderManagerMock(Currency fromCurrency, Currency toCurrency, double currentSalePrice, double currentPurchasePrice) {
        currentExchangeRate = new ExchangeRateImpl(fromCurrency, toCurrency, currentSalePrice, currentPurchasePrice, System.currentTimeMillis());
        providerId = UUID.randomUUID();
    }

    @Override
    public String getProviderName() throws CantGetProviderInfoException {
        return null;
    }

    @Override
    public UUID getProviderId() throws CantGetProviderInfoException {
        return providerId;
    }

    @Override
    public Collection<CurrencyPair> getSupportedCurrencyPairs() {
        return null;
    }

    @Override
    public boolean isCurrencyPairSupported(CurrencyPair currencyPair) throws IllegalArgumentException {
        return false;
    }

    @Override
    public ExchangeRate getCurrentExchangeRate(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
        return currentExchangeRate;
    }

    @Override
    public ExchangeRate getExchangeRateFromDate(CurrencyPair currencyPair, Calendar calendar) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
        return null;
    }

    @Override
    public Collection<ExchangeRate> getDailyExchangeRatesForPeriod(CurrencyPair currencyPair, Calendar startCalendar, Calendar endCalendar) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
        return null;
    }

    @Override
    public Collection<ExchangeRate> getQueriedExchangeRates(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
        return null;
    }
}
