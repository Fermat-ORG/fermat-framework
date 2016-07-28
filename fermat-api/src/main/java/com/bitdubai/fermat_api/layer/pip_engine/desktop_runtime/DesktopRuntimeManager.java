package com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime;

import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;

import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.09.16..
 */
public interface DesktopRuntimeManager extends RuntimeManager {


    DesktopObject getLastDesktopObject();

    DesktopObject getDesktopObject(String desktopObjectType);

    Map<String, DesktopObject> listDesktops();
}
