package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.structure.MarketPrice;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.MarketPrice;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by francisco on 31/08/15.
 */
public class TestGetBestMarketPrice {

    MarketPrice marketPrice = new MarketPrice();
    double bestMarketPrice;
    @Test
    public void getBestMarketPriceTest(){
    bestMarketPrice=marketPrice.getBestMarketPrice("BTC","USD");
        System.out.println(bestMarketPrice);
    }
}
