package com.bitdubai.sub_app.developer.common;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_pip_api.layer.module.developer.ClassHierarchyLevels;

import java.io.Serializable;

/**
 * Created by mati on 2015.07.03..
 */
public class Loggers implements Serializable {

    public static final int TYPE_PLUGIN = 1;

    public String picture;

    public PluginVersionReference pluginVersionReference;

    public ClassHierarchyLevels classHierarchyLevels;

    public LogLevel logLevel;

    public String developer;

    public int type;

}
