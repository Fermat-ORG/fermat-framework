package org.fermat.fermat_dap_plugin.layer.metadata.digital_asset.developer;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;

import org.fermat.fermat_dap_plugin.layer.metadata.digital_asset.developer.version_1.DigitalAssetMetadataPluginRoot;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class DeveloperBitDubai extends AbstractPluginDeveloper implements PluginLicensor {
    //VARIABLE DECLARATION

    //CONSTRUCTORS
    public DeveloperBitDubai() {
        super(new PluginDeveloperReference(Developers.BITDUBAI));
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartPluginDeveloperException {
        try {

            this.registerVersion(new DigitalAssetMetadataPluginRoot());

        } catch (CantRegisterVersionException e) {

            throw new CantStartPluginDeveloperException(e, "", "Error registering plugin versions for the developer.");
        }
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS


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
        return "19qRypu7wrndwW4FRCxU1JPr5hvMmcQ3eh";
    }

    @Override
    public TimeFrequency getTimePeriod() {
        return TimeFrequency.MONTHLY;
    }
    //INNER CLASSES
}
