package com.bitdubai.fermat_ccp_core.test_classes;

import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CCPPlugins;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_core.test_classes.TestPluginClass</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public class TestPluginClass extends FermatPlugin {

    private UUID pluginId;

    public TestPluginClass() {
        super(Developers.BITDUBAI, new Version("1.0.0"));
    }

    @Override
    public List<FermatAddonReference> getNeededAddonReferences() {

        return new ArrayList<>();
    }

    @Override
    public List<FermatPluginReference> getNeededPluginReferences() {

        List<FermatPluginReference> pluginReferences = new ArrayList<>();

        pluginReferences.add(new FermatPluginReference(CCPPlugins.BITDUBAI_INTRA_WALLET_USER_ACTOR, null));

        return pluginReferences;
    }

    @Override
    public void setId(final UUID pluginId) {
        this.pluginId = pluginId;
    }

}
