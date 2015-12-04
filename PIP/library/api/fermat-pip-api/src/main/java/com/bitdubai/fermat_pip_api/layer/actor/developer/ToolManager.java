package com.bitdubai.fermat_pip_api.layer.actor.developer;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_pip_api.layer.actor.exception.CantGetLogTool;

/**
 * Created by ciencias on 6/25/15.
 */
public interface ToolManager extends FermatManager {

public DatabaseTool getDatabaseTool() throws com.bitdubai.fermat_pip_api.layer.actor.exception.CantGetDataBaseTool;

public LogTool getLogTool() throws CantGetLogTool;

}
