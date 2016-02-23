package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerQuote;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angel on 21/02/16.
 */
public final class QuotesHelpers {

    private final ErrorManager errorManager;
    private final PluginVersionReference pluginVersionReference;

    public QuotesHelpers(
            final ErrorManager             errorManager,
            final PluginVersionReference   pluginVersionReference
    ){
        this.errorManager           = errorManager;
        this.pluginVersionReference = pluginVersionReference;
    }

    public List<CryptoBrokerQuote> getListQuotesForString(String _quotes) throws InvalidParameterException{

        if(_quotes == null || _quotes.equals("")) {
            return null;
        }

        List<CryptoBrokerQuote> quotes = new ArrayList<>();

        String[] quos = _quotes.split(";");
        for (int i = 0; i < quos.length; i++) {
            String[] quo = quos[i].split(":");
            Currency mer = null;
            Currency pay = null;
            Float pre = 0f;
            if(quo.length == 3) {
                try {
                    mer = CryptoCurrency.getByCode(quo[0]);
                } catch (InvalidParameterException e) {
                    try {
                        mer = FiatCurrency.getByCode(quo[0]);
                    } catch (InvalidParameterException e2) {
                        this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                        throw new InvalidParameterException(e2, "", "Error invalid parameter.");
                    }
                }
                try {
                    pay = CryptoCurrency.getByCode(quo[1]);
                } catch (InvalidParameterException e) {
                    try {
                        pay = FiatCurrency.getByCode(quo[1]);
                    } catch (InvalidParameterException e2) {
                        this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                        throw new InvalidParameterException(e2, "", "Error invalid parameter.");
                    }
                }
                pre = Float.parseFloat(quo[2]);
            }

            CryptoBrokerQuote q = new CryptoBrokerQuote(mer, pay, pre);
            quotes.add(q);
        }

        return quotes;
    }
}
