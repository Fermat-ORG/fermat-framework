package com.bitdubai.fermat_pip_api.layer.pip_actor.developer;

import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetDataBaseTool;
import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetLogTool;

/**
 * Created by ciencias on 6/25/15.
 */
public interface ToolManager {

public DatabaseTool getDatabaseTool() throws CantGetDataBaseTool;

public LogTool getLogTool() throws CantGetLogTool;

}
