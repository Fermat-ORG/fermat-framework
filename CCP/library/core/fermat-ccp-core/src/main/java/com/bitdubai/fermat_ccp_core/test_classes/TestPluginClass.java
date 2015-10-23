package com.bitdubai.fermat_ccp_core.test_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginReference;
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
public class TestPluginClass extends AbstractPlugin {

    private UUID pluginId;

    public TestPluginClass() {
        super();
    }

    @Override
    public List<AddonReference> getNeededAddonReferences() {
        return new ArrayList<>();
    }

    @Override
    public List<PluginReference> getNeededPluginReferences() {

        List<PluginReference> pluginReferences = new ArrayList<>();

        pluginReferences.add(new PluginReference(CCPPlugins.BITDUBAI_INTRA_WALLET_USER_ACTOR));

        return pluginReferences;
    }

    @Override
    public void setId(final UUID pluginId) {
        this.pluginId = pluginId;
    }

}
