package com.bitdubai.fermat_api.layer._15_middleware.app_runtime;

import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.Apps;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.SubApps;

import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public interface App {

    public Apps getType();

    public Map<SubApps, SubApp> getSubApps();
}
