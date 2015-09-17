package com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime;

/**
 * Created by Matias Furszyfer on 2015.09.16..
 */
public interface DesktopRuntimeManager {


    public DesktopObject getLastDesktopObject();

    public DesktopObject getDesktopObject(String desktopObjectType);
}
