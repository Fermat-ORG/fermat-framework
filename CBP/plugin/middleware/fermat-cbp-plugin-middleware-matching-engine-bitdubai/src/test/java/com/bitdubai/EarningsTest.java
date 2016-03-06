package com.bitdubai;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.InputTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.InputTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareInputTransaction;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon Acosta (laion.cj91@gmail.com) on 13/01/16.
 *
 * @author lnacosta
 */
public class EarningsTest {

    @Test
    public void earningsTest() throws Exception {

        List<InputTransaction> sellTransactions = listInputTransactionsSell();
        List<InputTransaction> buyTransactions  = listInputTransactionsBuy();

        for (InputTransaction sellTransaction : sellTransactions)
            System.out.println(sellTransaction);

        for (InputTransaction buyTransaction : buyTransactions)
            System.out.println(buyTransaction);
    }

    private List<InputTransaction> listInputTransactionsSell() {


        List<InputTransaction> inputTransactions = new ArrayList<>();

        inputTransactions.add(
                new MatchingEngineMiddlewareInputTransaction(
                        "SELL1"             ,
                        FiatCurrency.ARGENTINE_PESO,
                        1000,
                        CryptoCurrency.BITCOIN,
                        1,
                        InputTransactionState.UNMATCHED
                )
        );

        inputTransactions.add(
                new MatchingEngineMiddlewareInputTransaction(
                        "SELL2"             ,
                        FiatCurrency.ARGENTINE_PESO,
                        1100,
                        CryptoCurrency.BITCOIN,
                        1.1f,
                        InputTransactionState.UNMATCHED
                )
        );

        inputTransactions.add(
                new MatchingEngineMiddlewareInputTransaction(
                        "SELL3"             ,
                        FiatCurrency.ARGENTINE_PESO,
                        800,
                        CryptoCurrency.BITCOIN,
                        0.8f,
                        InputTransactionState.UNMATCHED
                )
        );

        inputTransactions.add(
                new MatchingEngineMiddlewareInputTransaction(
                        "SELL4"             ,
                        FiatCurrency.ARGENTINE_PESO,
                        700,
                        CryptoCurrency.BITCOIN,
                        0.7f,
                        InputTransactionState.UNMATCHED
                )
        );

        return inputTransactions;
    }

    private List<InputTransaction> listInputTransactionsBuy() {


        List<InputTransaction> inputTransactions = new ArrayList<>();

        inputTransactions.add(
                new MatchingEngineMiddlewareInputTransaction(
                        "BUY1"             ,
                        CryptoCurrency.BITCOIN,
                        1,
                        FiatCurrency.ARGENTINE_PESO,
                        950,
                        InputTransactionState.UNMATCHED
                )
        );

        inputTransactions.add(
                new MatchingEngineMiddlewareInputTransaction(
                        "BUY2"             ,
                        CryptoCurrency.BITCOIN,
                        0.5f,
                        FiatCurrency.ARGENTINE_PESO,
                        425,
                        InputTransactionState.UNMATCHED
                )
        );

        inputTransactions.add(
                new MatchingEngineMiddlewareInputTransaction(
                        "BUY3"             ,
                        CryptoCurrency.BITCOIN,
                        0.250f,
                        FiatCurrency.ARGENTINE_PESO,
                        212.5f,
                        InputTransactionState.UNMATCHED
                )
        );

        inputTransactions.add(
                new MatchingEngineMiddlewareInputTransaction(
                        "BUY4"             ,
                        CryptoCurrency.BITCOIN,
                        0.250f,
                        FiatCurrency.ARGENTINE_PESO,
                        212.5f,
                        InputTransactionState.UNMATCHED
                )
        );

        inputTransactions.add(
                new MatchingEngineMiddlewareInputTransaction(
                        "BUY5"             ,
                        CryptoCurrency.BITCOIN,
                        1.250f,
                        FiatCurrency.ARGENTINE_PESO,
                        1162.5f,
                        InputTransactionState.UNMATCHED
                )
        );

        return inputTransactions;
    }

}
