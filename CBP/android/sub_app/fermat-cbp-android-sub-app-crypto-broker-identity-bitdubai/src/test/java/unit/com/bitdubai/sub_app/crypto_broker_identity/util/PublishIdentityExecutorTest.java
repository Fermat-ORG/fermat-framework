package unit.com.bitdubai.sub_app.crypto_broker_identity.util;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.sub_app.crypto_broker_identity.util.PublishIdentityExecutor;

import org.junit.Test;

import unit.com.bitdubai.sub_app.crypto_broker_identity.TestCryptoBrokerIdentityModuleManager;
import unit.com.bitdubai.sub_app.crypto_broker_identity.TestCryptoBrokerIdentitySubAppSession;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 17/10/15.
 */
public class PublishIdentityExecutorTest {
    TestCryptoBrokerIdentitySubAppSession testSession;

    @Test
    public void testDataNotValid() {
        PublishIdentityExecutor executor = new PublishIdentityExecutor(null, false);

        int result = executor.execute();
        assertThat(result).isEqualTo(PublishIdentityExecutor.INVALID_ENTRY_DATA);
    }

    @Test
    public void execute_TryPublish_GenerateException() {
        testSession = new TestCryptoBrokerIdentitySubAppSession(SubApps.CBP_CRYPTO_BROKER_IDENTITY, false);
        testSession.setModuleManagerAction(TestCryptoBrokerIdentityModuleManager.PUBLISH_IDENTITY_THROW_EXCEPTION);

        PublishIdentityExecutor executor = new PublishIdentityExecutor(testSession, true);

        int result = executor.execute();
        assertThat(result).isEqualTo(PublishIdentityExecutor.EXCEPTION_THROWN);
    }

    @Test
    public void execute_TryUnPublish_GenerateException() {
        testSession = new TestCryptoBrokerIdentitySubAppSession(SubApps.CBP_CRYPTO_BROKER_IDENTITY, true);
        testSession.setModuleManagerAction(TestCryptoBrokerIdentityModuleManager.UNPUBLISH_IDENTITY_THROW_EXCEPTION);

        PublishIdentityExecutor executor = new PublishIdentityExecutor(testSession, false);

        int result = executor.execute();
        assertThat(result).isEqualTo(PublishIdentityExecutor.EXCEPTION_THROWN);
    }

    @Test
    public void execute_PublishIdentity() {
        testSession = new TestCryptoBrokerIdentitySubAppSession(SubApps.CBP_CRYPTO_BROKER_IDENTITY, false);
        testSession.setModuleManagerAction(TestCryptoBrokerIdentityModuleManager.PUBLISH_IDENTITY_RUN_OK);
        PublishIdentityExecutor executor = new PublishIdentityExecutor(testSession, true);

        int result = executor.execute();
        assertThat(result).isEqualTo(PublishIdentityExecutor.SUCCESS);
    }

    @Test
    public void execute_UnPublishIdentity() {
        testSession = new TestCryptoBrokerIdentitySubAppSession(SubApps.CBP_CRYPTO_BROKER_IDENTITY, true);
        testSession.setModuleManagerAction(TestCryptoBrokerIdentityModuleManager.UNPUBLISH_IDENTITY_RUN_OK);

        PublishIdentityExecutor executor = new PublishIdentityExecutor(testSession, false);

        int result = executor.execute();
        assertThat(result).isEqualTo(PublishIdentityExecutor.SUCCESS);
    }

    @Test
    public void execute_DoNothing() {
        testSession = new TestCryptoBrokerIdentitySubAppSession(SubApps.CBP_CRYPTO_BROKER_IDENTITY, true);
        testSession.setModuleManagerAction(TestCryptoBrokerIdentityModuleManager.UNPUBLISH_IDENTITY_RUN_OK);
        PublishIdentityExecutor executor = new PublishIdentityExecutor(testSession, true);

        int result = executor.execute();
        assertThat(result).isEqualTo(PublishIdentityExecutor.DATA_NOT_CHANGED);
    }
}