package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

/**
 * Created by rodrigo on 3/27/16.
 */
public class TimeOutNotifierAgentPluginRoot extends AbstractPlugin {

    public TimeOutNotifierAgentPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }
}
