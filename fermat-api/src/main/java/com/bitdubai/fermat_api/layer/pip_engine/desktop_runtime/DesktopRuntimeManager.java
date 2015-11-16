package com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime;

import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.09.16..
 */
public interface DesktopRuntimeManager extends RuntimeManager {


    public DesktopObject getLastDesktopObject();

    public DesktopObject getDesktopObject(String desktopObjectType);

    public List<DesktopObject> listDesktops();
}
