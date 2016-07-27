package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.ChatMiddlewarePluginRoot;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/01/16.
 */
public class DeveloperBitDubai extends AbstractPluginDeveloper implements PluginLicensor {

    public static void main(String[] args) {

        DeveloperBitDubai deb = new DeveloperBitDubai();
    }

    public DeveloperBitDubai() {
        super(new PluginDeveloperReference(Developers.BITDUBAI));
    }

    @Override
    public void start() throws CantStartPluginDeveloperException {
        try {

            this.registerVersion(new ChatMiddlewarePluginRoot());

        } catch (CantRegisterVersionException e) {

            throw new CantStartPluginDeveloperException(e, "", "Error registering plugin versions for the developer.");

        } catch (Exception exception) {

            throw new CantStartPluginDeveloperException(exception, "", "CAN'T START PLUGIN");

        }


    }


    @Override
    public int getAmountToPay() {
        return 100;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return CryptoCurrency.BITCOIN;
    }

    @Override
    public String getAddress() {
        return "19qRypu7wrndwW4FRCxU1JPr5we134Q3eh";
    }

    @Override
    public TimeFrequency getTimePeriod() {
        return TimeFrequency.MONTHLY;
    }

}

