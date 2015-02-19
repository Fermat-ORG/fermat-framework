package com.bitdubai.wallet_platform_api.layer._11_middleware.app_runtime;

import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public interface App {

    public Apps getType();

    public Map<SubApps, SubApp> getSubApps();
}
