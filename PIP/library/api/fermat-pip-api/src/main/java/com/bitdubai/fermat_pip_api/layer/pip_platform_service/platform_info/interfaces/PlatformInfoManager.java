package com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.exceptions.CantLoadPlatformInformationException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.exceptions.CantSetPlatformInformationException;

/**
 * The Interface <code>com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.PlatformInfoManager</code>
 * indicates the functionality of a PlatformInfoManager
 * <p/>
 *
 * Created by natalia on 29/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */

public interface PlatformInfoManager {

    PlatformInfo getPlatformInfo() throws CantLoadPlatformInformationException;

    void setPlatformInfo(final PlatformInfo platformInfo) throws CantSetPlatformInformationException;

}
