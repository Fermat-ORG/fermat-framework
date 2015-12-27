package com.bitdubai.fermat_pip_api.layer.module.developer.interfaces;

import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetDataBaseToolException;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetLogToolException;

/**
 * Created by ciencias on 6/25/15.
 */
public interface ToolManager extends ModuleManager {

    DatabaseTool getDatabaseTool() throws CantGetDataBaseToolException;

    LogTool getLogTool() throws CantGetLogToolException;

}
