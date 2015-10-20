package com.bitdubai.fermat_ccp_core.test_classes;

import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.HashMap;
import java.util.Map;

/**
 * The abstract class <code>com.bitdubai.fermat_ccp_core.test_classes.FermatPlugin</code>
 * contains the basic functionality of a Fermat Plugin.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class FermatPlugin {

    private Map<FermatAddonsEnum , FermatAddon > addons ;
    private Map<FermatPluginsEnum, FermatPlugin> plugins;

    private final Developers developer;
    private final Version    version  ;

    public FermatPlugin(final Developers developer,
                        final Version    version  ) {

        this.developer = developer;
        this.version   = version  ;
    }

    public final void addAddonReference(final FermatAddonsEnum descriptor,
                                        final FermatAddon      addon     ) {

        if(addons.isEmpty())
            addons = new HashMap<>();

        addons.put(descriptor, addon);
    }

    public final void addPluginReference(final FermatPluginsEnum descriptor,
                                         final FermatPlugin      plugin    ) {

        if(plugins.isEmpty())
            plugins = new HashMap<>();

        plugins.put(descriptor, plugin);
    }

    protected final FermatAddon getAddonReference(final FermatAddonsEnum descriptor) {

        return addons.get(descriptor);
    }

    protected final FermatPlugin getPluginReference(final FermatPluginsEnum descriptor) {

        return plugins.get(descriptor);
    }

    public final Developers getDeveloper() {
        return developer;
    }

    public final Version getVersion() {
        return version;
    }

}
