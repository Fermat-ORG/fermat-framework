package com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ComboAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2016.06.02..
 */
public class AbstractComboFermatSession<A extends FermatApp, R extends ResourceProviderManager> extends BaseFermatSession<A, R> implements ComboAppFermatSession {

    private Map<Class, ModuleManager> moduleManagerMap;

    public AbstractComboFermatSession(String publicKey, A fermatApp, R resourceProviderManager, ErrorManager errorManager, ModuleManager... modules) {
        super(publicKey, fermatApp, resourceProviderManager, errorManager);
        moduleManagerMap = new HashMap<>();
        init(modules);
    }

    private void init(ModuleManager[] modules) {
        for (ModuleManager moduleManager : modules) {
            moduleManagerMap.put(moduleManager.getClass(), moduleManager);
        }
    }

    @Override
    public <T extends ModuleManager> T getModuleManager(Class<T> classType) throws InvalidParameterException {
        if (!moduleManagerMap.containsKey(classType)) throw new InvalidParameterException();
        return (T) moduleManagerMap.get(classType);
    }
}
