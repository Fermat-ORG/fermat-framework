package com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.exceptions.CantLoadPlatformInformationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.exceptions.CantSetPlatformInformationException;

import java.util.List;

/**
 * The Interface <code>PlatformInfoManager</code>
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
