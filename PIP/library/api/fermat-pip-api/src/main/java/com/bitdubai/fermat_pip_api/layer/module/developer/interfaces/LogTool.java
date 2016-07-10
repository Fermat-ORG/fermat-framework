package com.bitdubai.fermat_pip_api.layer.module.developer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_pip_api.layer.module.developer.ClassHierarchyLevels;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetClasessHierarchyAddonsException;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetClasessHierarchyPluginsException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public interface LogTool extends Serializable {

    List<PluginVersionReference> getAvailablePluginList();

    List<AddonVersionReference> getAvailableAddonList();

    List<ClassHierarchyLevels> getClassesHierarchyPlugins(PluginVersionReference plugin) throws CantGetClasessHierarchyPluginsException;

    List<ClassHierarchyLevels> getClassesHierarchyAddons(AddonVersionReference addon) throws CantGetClasessHierarchyAddonsException;

    void setNewLogLevelInClass(PluginVersionReference plugin, HashMap<String, LogLevel> newLogLevelInClass);
}
