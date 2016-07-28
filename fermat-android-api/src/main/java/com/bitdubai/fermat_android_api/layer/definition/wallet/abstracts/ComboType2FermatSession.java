package com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ComboAppType2FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2016.06.04..
 */
public class ComboType2FermatSession<A extends FermatApp, R extends ResourceProviderManager> extends BaseFermatSession<A, R> implements ComboAppType2FermatSession {


    private Map<String, FermatSession> sessionMap;

    public ComboType2FermatSession(String publicKey, A fermatApp, R resourceProviderManager, ErrorManager errorManager) {
        super(publicKey, fermatApp, resourceProviderManager, errorManager);
        sessionMap = new HashMap<>();
    }

    public void addSession(String appPublicKey, FermatSession session) throws InvalidParameterException {
        if (sessionMap.containsKey(appPublicKey))
            throw new InvalidParameterException("Session already exist");
        sessionMap.put(appPublicKey, session);
    }

    @Override
    public <T extends FermatSession> T getFermatSession(String appPublicKey, Class<T> sessionType) throws InvalidParameterException {
        if (!sessionMap.containsKey(appPublicKey))
            throw new InvalidParameterException(new StringBuilder().append("Session not exist for pk: ").append(appPublicKey).toString(), new StringBuilder().append("Pk valids in this ComboSession: ").append(Arrays.toString(sessionMap.keySet().toArray())).toString());
        return (T) sessionMap.get(appPublicKey);
    }
}
