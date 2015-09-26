package com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.ClassHierarchyLevels;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.exception.CantGetClasessHierarchyAddonsException;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.exception.CantGetClasessHierarchyPluginsException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public interface LogTool {

    public List<Plugins> getAvailablePluginList();

    public List<Addons> getAvailableAddonList();

    public List<ClassHierarchyLevels> getClassesHierarchyPlugins(Plugins plugin) throws CantGetClasessHierarchyPluginsException;

    public List<ClassHierarchyLevels> getClassesHierarchyAddons(Addons addon) throws CantGetClasessHierarchyAddonsException;

    public void setNewLogLevelInClass(Plugins plugin, HashMap<String, LogLevel> newLogLevelInClass);


}
