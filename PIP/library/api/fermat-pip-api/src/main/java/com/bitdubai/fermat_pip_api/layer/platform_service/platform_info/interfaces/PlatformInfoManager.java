package com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions.CantLoadPlatformInformationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions.CantSetPlatformInformationException;

/**
 * The Interface <code>PlatformInfoManager</code>
 * indicates the functionality of a PlatformInfoManager
 * <p/>
 * <p/>
 * Created by natalia on 29/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public interface PlatformInfoManager extends FermatManager {

    PlatformInfo getPlatformInfo() throws CantLoadPlatformInformationException;

    void setPlatformInfo(final PlatformInfo platformInfo) throws CantSetPlatformInformationException;


}
