package com.bitdubai.fermat_api.layer.pip_actor.developer;

import com.bitdubai.fermat_api.layer.all_definition.developer.LoggingStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public interface LogTool {

    public List<Plugins> getAvailablePluginList ();

    public List<Addons> getAvailableAddonList ();

    public LoggingStatus getLoggingStatus(Plugins plugin);

    public LoggingStatus getLoggingStatus(Addons addon);

    public void changeLoggingStatus(Plugins plugin, LoggingStatus newLoggingStatus);

    public void changeLoggingStatus(Addons addon, LoggingStatus newLoggingStatus);
}
