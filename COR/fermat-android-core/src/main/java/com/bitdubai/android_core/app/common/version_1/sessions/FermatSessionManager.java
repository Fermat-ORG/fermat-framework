package com.bitdubai.android_core.app.common.version_1.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractComboFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.ComboType2FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2016.02.22..
 */
public class FermatSessionManager {

    private Map<String, FermatSession> lstAppSession;


    public FermatSessionManager() {
        lstAppSession = new HashMap<>();
    }


    public Map<String, FermatSession> listOpenApps() {
        return lstAppSession;
    }

    public FermatSession openAppSession(FermatApp app, ErrorManager errorManager, ModuleManager moduleManager, boolean isForSubSession) {
        FermatSession AppsSession = buildReferenceSession(app, moduleManager, errorManager);
        if (!isForSubSession) lstAppSession.put(app.getAppPublicKey(), AppsSession);
        return AppsSession;
    }

    public FermatSession openAppSession(FermatApp app, ErrorManager errorManager, ModuleManager[] moduleManager, boolean isForSubSession) {
        FermatSession AppsSession = buildComboAppSession(app, errorManager, moduleManager);
        if (!isForSubSession) lstAppSession.put(app.getAppPublicKey(), AppsSession);
        return AppsSession;
    }

    public FermatSession openComboAppSession(FermatApp app, ErrorManager errorManager, boolean isForSubSession) {
        FermatSession AppsSession = buildComboAppSession(app, errorManager);
        if (!isForSubSession) lstAppSession.put(app.getAppPublicKey(), AppsSession);
        return AppsSession;
    }

    public ComboType2FermatSession openComboAppType2Session(FermatApp app, ErrorManager errorManager, boolean isForSubSession) {
        ComboType2FermatSession AppsSession = buildComboAppType2Session(app, errorManager);
        if (!isForSubSession) lstAppSession.put(app.getAppPublicKey(), AppsSession);
        return AppsSession;
    }


    public boolean closeAppSession(String subAppPublicKey) {
        try {
            lstAppSession.remove(subAppPublicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }

    public FermatSession getAppsSession(String appPublicKey) {
        if (appPublicKey == null)
            throw new NullPointerException("Publick key de la app se encuentra en null");
        return lstAppSession.get(appPublicKey);
    }

    public boolean isSessionOpen(String appPublicKey) {
        return lstAppSession.containsKey(appPublicKey);
    }


    public FermatSession buildReferenceSession(FermatApp fermatApp, ModuleManager manager, ErrorManager errorManager) {
        AbstractReferenceAppFermatSession session = new AbstractReferenceAppFermatSession(fermatApp.getAppPublicKey(), fermatApp, errorManager, manager, null);
//        session.setErrorManager(errorManager);
//        session.setModuleManager(manager);
//        session.setFermatApp(fermatApp);
//        session.setPublicKey(fermatApp.getAppPublicKey());
        return session;
    }

    public FermatSession buildComboAppSession(FermatApp fermatApp, ErrorManager errorManager, ModuleManager... manager) {
        AbstractComboFermatSession session = new AbstractComboFermatSession(fermatApp.getAppPublicKey(), fermatApp, null, errorManager, manager);
        return session;
    }

    public ComboType2FermatSession buildComboAppType2Session(FermatApp app, ErrorManager errorManager) {
        return new ComboType2FermatSession(app.getAppPublicKey(), app, null, errorManager);
    }
}
