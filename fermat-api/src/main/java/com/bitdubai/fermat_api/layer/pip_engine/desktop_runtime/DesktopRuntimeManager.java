package com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.09.16..
 */
public interface DesktopRuntimeManager {


    public DesktopObject getLastDesktopObject();

    public DesktopObject getDesktopObject(String desktopObjectType);

    public List<DesktopObject> listDesktops();
}
