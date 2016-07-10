package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker;

import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.CantCreateProxyException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import java.lang.reflect.Method;

/**
 * Created by mati on 2016.04.20..
 */
public interface ClientBrokerService {

    ModuleManager getModuleManager(PluginVersionReference pluginVersionReference) throws CantCreateProxyException;

    ModuleManager[] getModuleManager(PluginVersionReference[] pluginVersionReference) throws CantCreateProxyException;

    Object sendMessage(PluginVersionReference pluginVersionReference, Object proxy, Method method, Object[] args) throws Exception;

    boolean isFermatBackgroundServiceRunning();
}
