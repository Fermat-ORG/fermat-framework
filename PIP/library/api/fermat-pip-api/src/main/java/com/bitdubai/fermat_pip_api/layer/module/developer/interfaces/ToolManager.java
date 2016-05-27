package com.bitdubai.fermat_pip_api.layer.module.developer.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.basic_classes.BasicSubAppSettings;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetDataBaseToolException;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetLogToolException;

/**
 * Created by ciencias on 6/25/15.
 */
public interface ToolManager extends ModuleManager<BasicSubAppSettings, ActiveActorIdentityInformation>, ModuleSettingsImpl<BasicSubAppSettings> {

    DatabaseTool getDatabaseTool() throws CantGetDataBaseToolException;

    LogTool getLogTool() throws CantGetLogToolException;

}
