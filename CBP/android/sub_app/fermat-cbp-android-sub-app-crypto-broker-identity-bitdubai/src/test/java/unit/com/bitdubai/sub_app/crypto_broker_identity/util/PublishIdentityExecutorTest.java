package unit.com.bitdubai.sub_app.crypto_broker_identity.util;

import com.bitdubai.sub_app.crypto_broker_identity.util.PublishIdentityWorker;

import org.junit.Test;

import unit.com.bitdubai.sub_app.crypto_broker_identity.TestCryptoBrokerIdentitySubAppSession;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 17/10/15.
 */
public class PublishIdentityExecutorTest {
    TestCryptoBrokerIdentitySubAppSession testSession;

    @Test
    public void testDataNotValid() {
//        PublishIdentityWorker executor = new PublishIdentityWorker(null, false);
//
//        int result = executor.execute();
//        assertThat(result).isEqualTo(PublishIdentityWorker.INVALID_ENTRY_DATA);
    }

    @Test
    public void execute_TryPublish_GenerateException() {
//        testSession = new TestCryptoBrokerIdentitySubAppSession(SubApps.CBP_CRYPTO_BROKER_IDENTITY, false);
//        testSession.setModuleManagerAction(TestCryptoBrokerIdentityModuleManager.PUBLISH_IDENTITY_THROW_EXCEPTION);
//
//        PublishIdentityExecutor executor = new PublishIdentityExecutor(testSession, true);
//
//        int result = executor.execute();
//        assertThat(result).isEqualTo(PublishIdentityExecutor.EXCEPTION_THROWN);
    }

    @Test
    public void execute_TryUnPublish_GenerateException() {
//        testSession = new TestCryptoBrokerIdentitySubAppSession(SubApps.CBP_CRYPTO_BROKER_IDENTITY, true);
//        testSession.setModuleManagerAction(TestCryptoBrokerIdentityModuleManager.HIDE_IDENTITY_THROW_EXCEPTION);
//
//        PublishIdentityExecutor executor = new PublishIdentityExecutor(testSession, false);
//
//        int result = executor.execute();
//        assertThat(result).isEqualTo(PublishIdentityExecutor.EXCEPTION_THROWN);
    }

    @Test
    public void execute_PublishIdentity() {
//        testSession = new TestCryptoBrokerIdentitySubAppSession(SubApps.CBP_CRYPTO_BROKER_IDENTITY, false);
//        testSession.setModuleManagerAction(TestCryptoBrokerIdentityModuleManager.PUBLISH_IDENTITY_RUN_OK);
//        PublishIdentityExecutor executor = new PublishIdentityExecutor(testSession, true);
//
//        int result = executor.execute();
//        assertThat(result).isEqualTo(PublishIdentityExecutor.SUCCESS);
    }

    @Test
    public void execute_UnPublishIdentity() {
//        testSession = new TestCryptoBrokerIdentitySubAppSession(SubApps.CBP_CRYPTO_BROKER_IDENTITY, true);
//        testSession.setModuleManagerAction(TestCryptoBrokerIdentityModuleManager.HIDE_IDENTITY_RUN_OK);
//
//        PublishIdentityExecutor executor = new PublishIdentityExecutor(testSession, false);
//
//        int result = executor.execute();
//        assertThat(result).isEqualTo(PublishIdentityExecutor.SUCCESS);
    }

    @Test
    public void execute_DoNothing() {
//        testSession = new TestCryptoBrokerIdentitySubAppSession(SubApps.CBP_CRYPTO_BROKER_IDENTITY, true);
//        testSession.setModuleManagerAction(TestCryptoBrokerIdentityModuleManager.HIDE_IDENTITY_RUN_OK);
//        PublishIdentityExecutor executor = new PublishIdentityExecutor(testSession, true);
//
//        int result = executor.execute();
//        assertThat(result).isEqualTo(PublishIdentityExecutor.DATA_NOT_CHANGED);
    }
}