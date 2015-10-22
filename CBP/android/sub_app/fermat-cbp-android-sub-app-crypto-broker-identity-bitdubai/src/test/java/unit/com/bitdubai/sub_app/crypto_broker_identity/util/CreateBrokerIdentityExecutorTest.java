package unit.com.bitdubai.sub_app.crypto_broker_identity.util;

import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.sub_app.crypto_broker_identity.util.CreateBrokerIdentityExecutor;

import org.junit.Before;
import org.junit.Test;

import unit.com.bitdubai.sub_app.crypto_broker_identity.TestCryptoBrokerIdentityModuleManager;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 14/10/15.
 */
public class CreateBrokerIdentityExecutorTest {

    TestCryptoBrokerIdentityModuleManager testModuleManager;

    @Before
    public void setUp() {
        testModuleManager = new TestCryptoBrokerIdentityModuleManager();
    }

    @Test
    public void executeCreateIdentity_DataNotValid() {
        CreateBrokerIdentityExecutor executor;

        executor = new CreateBrokerIdentityExecutor(null, null, null, null);
        int result = executor.execute();
        assertThat(result).isEqualTo(CreateBrokerIdentityExecutor.INVALID_ENTRY_DATA);

        executor = new CreateBrokerIdentityExecutor(testModuleManager, null, new byte[0], null);
        result = executor.execute();
        assertThat(result).isEqualTo(CreateBrokerIdentityExecutor.INVALID_ENTRY_DATA);

        executor = new CreateBrokerIdentityExecutor(testModuleManager, null, new byte[0], null);
        result = executor.execute();
        assertThat(result).isEqualTo(CreateBrokerIdentityExecutor.INVALID_ENTRY_DATA);

        executor = new CreateBrokerIdentityExecutor(testModuleManager, null, new byte[0], "");
        result = executor.execute();
        assertThat(result).isEqualTo(CreateBrokerIdentityExecutor.INVALID_ENTRY_DATA);
    }

    @Test
    public void executeCreateIdentity_GenerateException() {

        testModuleManager.setAction(TestCryptoBrokerIdentityModuleManager.CREATE_IDENTITY_THROW_EXCEPTION);

        CreateBrokerIdentityExecutor executor = new CreateBrokerIdentityExecutor(testModuleManager, null, new byte[10], "Nelson");
        int result = executor.execute();
        assertThat(result).isEqualTo(CreateBrokerIdentityExecutor.EXCEPTION_THROWN);
    }

    @Test
    public void executeCreateIdentity_CreateIdentity() {
        String brokerName = "broker name";
        byte[] imageInBytes = new byte[10];

        testModuleManager.setAction(TestCryptoBrokerIdentityModuleManager.CREATE_IDENTITY_RETURN_IDENTITY);
        CreateBrokerIdentityExecutor executor = new CreateBrokerIdentityExecutor(testModuleManager, null, imageInBytes, brokerName);

        int result = executor.execute();
        assertThat(result).isEqualTo(CreateBrokerIdentityExecutor.SUCCESS);

        CryptoBrokerIdentityInformation identity = executor.getIdentity();
        assertThat(identity.getAlias()).isEqualTo(brokerName);
        assertThat(identity.getProfileImage()).isNotNull();
    }
}
