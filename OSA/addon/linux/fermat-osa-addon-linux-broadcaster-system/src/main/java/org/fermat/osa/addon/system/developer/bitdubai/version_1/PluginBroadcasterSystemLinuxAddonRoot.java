package org.fermat.osa.addon.system.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import org.fermat.osa.addon.system.developer.bitdubai.version_1.structure.LinuxPluginBroadcaster;

/**
 * This addon handles a TODO description
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PluginBroadcasterSystemLinuxAddonRoot extends AbstractAddon {

    private FermatManager fermatManager;

    public PluginBroadcasterSystemLinuxAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    @Override
    public final void start() throws CantStartPluginException {

        try {

            fermatManager = new LinuxPluginBroadcaster();

        } catch (final Exception e) {

            throw new CantStartPluginException(e, "Plugin Broadcaster System Manager starting.", "Unhandled Exception trying to start the Plugin File System manager.");
        }
    }

    public final FermatManager getManager() {
        return fermatManager;
    }

}
