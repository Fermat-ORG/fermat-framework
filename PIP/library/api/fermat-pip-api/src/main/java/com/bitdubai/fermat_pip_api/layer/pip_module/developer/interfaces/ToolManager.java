package com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetDataBaseTool;
import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetLogTool;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.exception.CantGetDataBaseToolException;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.exception.CantGetLogToolException;

/**
 * Created by ciencias on 6/25/15.
 */
public interface ToolManager extends ModuleManager {

public DatabaseTool getDatabaseTool() throws CantGetDataBaseToolException;

public LogTool getLogTool() throws CantGetLogToolException;

}
