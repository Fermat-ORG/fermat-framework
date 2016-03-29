package com.bitdubai.fermat_pip_core.layer.agent;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_pip_core.layer.agent.timeout_notifier.TimeOutNotifierPluginSubsystem;

/**
 * Created by rodrigo on 3/26/16.
 */
public class AgentLayer extends AbstractLayer {

    public AgentLayer() {
        super(Layers.AGENT);
    }

    @Override
    public void start() throws CantStartLayerException {
        try {
            registerPlugin(new TimeOutNotifierPluginSubsystem());
        } catch (CantRegisterPluginException e) {
            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }
}
