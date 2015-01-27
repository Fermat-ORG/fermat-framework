package com.bitdubai.platform.layer._11_module;

import com.bitdubai.platform.layer._1_definition.enums.ServiceStatus;

/**
 * Created by ciencias on 30.12.14.
 */
public interface ModuleService {

    public void run();

    public void pause();

    public void stop();

    public ServiceStatus getStatus();

}
