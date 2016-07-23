package org.fermat.osa.addon.system.developer.bitdubai;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddonDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAddonDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;

import org.fermat.osa.addon.system.developer.bitdubai.version_1.PluginBroadcasterSystemLinuxAddonRoot;

/**
 * The class <code>org.fermat.osa.addon.system.developer.bitdubai.PluginBroadcasterSystemDeveloperBitDubai</code>
 * Haves the logic of instantiation of all versions of Plugin Broadcaster System Android Addon.
 * <p/>
 * Here we can choose between the different versions of the Plugin Broadcaster System Addon.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PluginBroadcasterSystemDeveloperBitDubai extends AbstractAddonDeveloper {

    public PluginBroadcasterSystemDeveloperBitDubai() {
        super(new AddonDeveloperReference(Developers.BITDUBAI));
    }

    @Override
    public void start() throws CantStartAddonDeveloperException {
        try {

            this.registerVersion(new PluginBroadcasterSystemLinuxAddonRoot());

        } catch (CantRegisterVersionException e) {

            throw new CantStartAddonDeveloperException(e, "", "Error registering addon versions for the developer.");
        }
    }
}
