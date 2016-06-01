package com.bitdubai.fermat_cer_core.layer.provider;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cer_core.layer.provider.bitcoinvenezuela.BitcoinVenezuelaSubsystem;
import com.bitdubai.fermat_cer_core.layer.provider.bitfinex.BitfinexSubsystem;
import com.bitdubai.fermat_cer_core.layer.provider.btce.BtceSubsystem;
import com.bitdubai.fermat_cer_core.layer.provider.bter.BterSubsystem;
import com.bitdubai.fermat_cer_core.layer.provider.ccex.CcexSubsystem;
import com.bitdubai.fermat_cer_core.layer.provider.dolartoday.DolarTodaySubsystem;
import com.bitdubai.fermat_cer_core.layer.provider.elcronista.ElCronistaSubsystem;
import com.bitdubai.fermat_cer_core.layer.provider.europeancentralbank.EuropeanCentralBankSubsystem;
import com.bitdubai.fermat_cer_core.layer.provider.fermatexchange.FermatExchangeSubsystem;
import com.bitdubai.fermat_cer_core.layer.provider.lanacion.LaNacionSubsystem;
import com.bitdubai.fermat_cer_core.layer.provider.yahoo.YahooSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by Alejandro Bicelis on 11/25/2015.
 */

public class ProviderLayer extends AbstractLayer {

    public ProviderLayer() {
        super(Layers.PROVIDER);
    }

    public void start() throws CantStartLayerException {

        try {
            registerPlugin(new BitcoinVenezuelaSubsystem());
            registerPlugin(new BitfinexSubsystem());
            registerPlugin(new BtceSubsystem());
            registerPlugin(new BterSubsystem());
            registerPlugin(new CcexSubsystem());
            registerPlugin(new DolarTodaySubsystem());
            registerPlugin(new ElCronistaSubsystem());
            registerPlugin(new EuropeanCentralBankSubsystem());
            registerPlugin(new FermatExchangeSubsystem());
            registerPlugin(new LaNacionSubsystem());
            registerPlugin(new YahooSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
