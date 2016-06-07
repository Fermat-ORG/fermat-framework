package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.CryptoCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.FiatCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetMarketPriceException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantSelectBestIndexException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndex;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.IndexProvider;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.CryptoIndexHelper;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.CryptoIndexImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by francisco on 04/12/15.
 */
public class CryptoProvidersManager implements IndexProvider {

    BtceProvider btceProvider = new BtceProvider();
    BterProvider bterProvider = new BterProvider();
    CcexProvider ccexProvider = new CcexProvider();
    CexioProvider cexioProvider = new CexioProvider();
    BitfinexProvider bitfinexProvider = new BitfinexProvider();
    CryptoCoinChartsProvider cryptoCoinChartsProvider = new CryptoCoinChartsProvider();

    /**
     * @param cryptoCurrency
     * @param fiatCurrency
     * @return
     * @throws CantGetIndexException
     */

    @Override
    public CryptoIndex getCurrentIndex(CryptoCurrency cryptoCurrency, FiatCurrency fiatCurrency) throws CantGetIndexException {

        try {
            List<CryptoIndex> indexList = new ArrayList<>();

            long timestamp = (new Date().getTime() / 1000);

            CryptoIndexImp btceIndex = new CryptoIndexImp(
                    CryptoCurrency.BITCOIN,
                    CryptoCurrency.BITCOIN,//TODO REVISAR !!!!
                    btceProvider.getMarketPrice(cryptoCurrency, fiatCurrency, timestamp),
                    btceProvider.getMarketPrice(cryptoCurrency, fiatCurrency, timestamp),
                    (new Date().getTime() / 1000),
                    "btc-e.com");
            CryptoIndexImp ccexIndex = new CryptoIndexImp(
                    CryptoCurrency.BITCOIN,
                    CryptoCurrency.BITCOIN,//TODO REVISAR !!!!
                    ccexProvider.getMarketPrice(cryptoCurrency, fiatCurrency, timestamp),
                    ccexProvider.getMarketPrice(cryptoCurrency, fiatCurrency, timestamp),
                    (new Date().getTime() / 1000),
                    "c-cex.com");
            CryptoIndexImp cexioIndex = new CryptoIndexImp(
                    CryptoCurrency.BITCOIN,
                    CryptoCurrency.BITCOIN,//TODO REVISAR !!!!
                    cexioProvider.getMarketPrice(cryptoCurrency, fiatCurrency, timestamp),
                    cexioProvider.getMarketPrice(cryptoCurrency, fiatCurrency, timestamp),
                    (new Date().getTime() / 1000),
                    "cex.io");
            CryptoIndexImp cryptoCoinChartsIndex = new CryptoIndexImp(
                    CryptoCurrency.BITCOIN,
                    CryptoCurrency.BITCOIN,//TODO REVISAR !!!!
                    cryptoCoinChartsProvider.getMarketPrice(cryptoCurrency, fiatCurrency, timestamp),
                    cryptoCoinChartsProvider.getMarketPrice(cryptoCurrency, fiatCurrency, timestamp),
                    (new Date().getTime() / 1000),
                    "www.cryptocoincharts.info");
            CryptoIndexImp bitFinexIndex = new CryptoIndexImp(
                    CryptoCurrency.BITCOIN,
                    CryptoCurrency.BITCOIN,//TODO REVISAR !!!!
                    cryptoCoinChartsProvider.getMarketPrice(cryptoCurrency, fiatCurrency, timestamp),
                    cryptoCoinChartsProvider.getMarketPrice(cryptoCurrency, fiatCurrency, timestamp),
                    (new Date().getTime() / 1000),
                    "bitfinex.com");

            indexList.add(btceIndex);
            indexList.add(ccexIndex);
            indexList.add(cexioIndex);
            indexList.add(cryptoCoinChartsIndex);
            indexList.add(bitFinexIndex);

            CryptoIndex index;
            index = CryptoIndexHelper.selectBestIndex(indexList);
            return index;
        } catch (FiatCurrencyNotSupportedException e) {
            throw new CantGetIndexException(CantGetIndexException.DEFAULT_MESSAGE, e, "CantGetIndexException BTC - USD", "FiatCurrency Not Supported Exception");
        } catch (CryptoCurrencyNotSupportedException e) {
            throw new CantGetIndexException(CantGetIndexException.DEFAULT_MESSAGE, e, "CantGetIndexException BTC - USD", "CryptoCurrency Not Supported Exception");
        } catch (CantGetMarketPriceException e) {
            throw new CantGetIndexException(CantGetIndexException.DEFAULT_MESSAGE, e, "CantGetIndexException BTC - USD", "Cant Get Market Price Exception");
        } catch (CantSelectBestIndexException e) {
            throw new CantGetIndexException(CantGetIndexException.DEFAULT_MESSAGE, e, "CantGetIndexException BTC - USD", "Cant Select Best Index Exception");
        }


    }
}
