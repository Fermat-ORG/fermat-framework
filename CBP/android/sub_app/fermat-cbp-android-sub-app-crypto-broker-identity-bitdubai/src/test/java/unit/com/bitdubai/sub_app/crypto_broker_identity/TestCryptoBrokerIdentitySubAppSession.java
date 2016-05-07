package unit.com.bitdubai.sub_app.crypto_broker_identity;

import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.crypto_broker_identity.session.CryptoBrokerIdentitySubAppSession;


/**
 * Created by nelson on 17/10/15.
 */
public class TestCryptoBrokerIdentitySubAppSession extends CryptoBrokerIdentitySubAppSession {
    TestCryptoBrokerIdentityModuleManager testModuleManager;

    public TestCryptoBrokerIdentitySubAppSession(InstalledSubApp subApps, boolean isPublished) {
        super(subApps, null, null);
        testModuleManager = new TestCryptoBrokerIdentityModuleManager();
        TestCryptoBrokerIdentityInformation identityInfo = new TestCryptoBrokerIdentityInformation("testAlias", new byte[0], isPublished);
        setData(IDENTITY_INFO, identityInfo);
    }

    @Override
    public ErrorManager getErrorManager() {
        return null;
    }

    @Override
    public CryptoBrokerIdentityModuleManager getModuleManager() {
        return testModuleManager;
    }

    public void setModuleManagerAction(short action) {
        testModuleManager.setAction(action);
    }
}
