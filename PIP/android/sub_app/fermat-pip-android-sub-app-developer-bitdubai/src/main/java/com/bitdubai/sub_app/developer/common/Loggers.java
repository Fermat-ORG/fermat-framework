package com.bitdubai.sub_app.developer.common;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_pip_api.layer.module.developer.ClassHierarchyLevels;

/**
 * Created by mati on 2015.07.03..
 */
public class Loggers {

    public static final int TYPE_PLUGIN=1;
    public static final int TYPE_ADDON=2;

    public static final int LOGGER_LEVEL_NOT_LOGGING=0;
    public static final int LOGGER_LEVEL_MINIMAL_LOGGING=1;
    public static final int LOGGER_LEVEL_MODERATE_LOGGING=2;
    public static final int LOGGER_LEVEL_AGGRESSIVE_LOGGING=3;



    private static final long serialVersionUID = -8775437026050196758L;

    public String picture;

    public String pluginKey;

    public ClassHierarchyLevels classHierarchyLevels;

    public LogLevel logLevel;

    public String developer;

    public int type;
}
