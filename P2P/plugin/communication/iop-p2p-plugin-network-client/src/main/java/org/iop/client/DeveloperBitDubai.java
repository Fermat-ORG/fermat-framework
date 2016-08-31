package org.iop.client;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;

import org.iop.client.version_1.IoPClientPluginRoot;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.DeveloperBitDubai</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeveloperBitDubai extends AbstractPluginDeveloper implements PluginLicensor {

    public DeveloperBitDubai() {
        super(new PluginDeveloperReference(Developers.BITDUBAI));
    }

    @Override
    public void start() throws CantStartPluginDeveloperException {
        try {

            this.registerVersion(new IoPClientPluginRoot());

        } catch (Exception e) {

            throw new CantStartPluginDeveloperException(e, "", "Error registering plugin versions for the developer.");
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
        return "19qRypu7wrndwW4FRCxU1JPr5hvMmcQ3eh";
    }

    @Override
    public TimeFrequency getTimePeriod() {
        return TimeFrequency.MONTHLY;
    }


}