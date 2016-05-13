package com.bitdubai.fermat_pip_api.layer.platform_service.error_manager;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

/**
 * Created by ciencias on 05.02.15.
 */
public interface DealsWithErrors {

    // We're not using anymore DealsWith interfaces... Now we use Annotations,,, And inside the plug-ins, please pass parameter through constructors.
    @Deprecated
    void setErrorManager (ErrorManager errorManager);

}
