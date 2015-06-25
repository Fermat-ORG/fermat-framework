package com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.developer.LoggingLevel;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.pip_actor.developer.LogTool;

import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public class DeveloperActorLogTool implements LogTool {
    @Override
    public List<Plugins> getAvailablePluginList() {
        return null;
    }

    @Override
    public List<Addons> getAvailableAddonList() {
        return null;
    }

    @Override
    public LoggingLevel getLoggingLevel(Plugins plugin) {
        return null;
    }

    @Override
    public LoggingLevel getLoggingLevel(Addons addon) {
        return null;
    }

    @Override
    public void changeLoggingLevel(Plugins plugin, LoggingLevel newLoggingLevel) {

    }

    @Override
    public void changeLoggingLevel(Addons addon, LoggingLevel newLoggingLevel) {

    }
}
