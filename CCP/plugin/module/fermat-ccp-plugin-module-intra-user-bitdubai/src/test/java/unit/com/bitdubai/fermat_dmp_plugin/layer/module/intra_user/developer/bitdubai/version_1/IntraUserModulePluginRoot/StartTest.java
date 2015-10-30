/*
package unit.com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraUserModulePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.IntraUsers.IntraUserSettings;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.interfaces.IntraUserIdentity;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraWalletUserModulePluginRoot;
import ErrorManager;

import junit.framework.TestCase;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

*/
/**
 * Created by natalia on 20/08/15.
 *//*

@RunWith(MockitoJUnitRunner.class)
public class StartTest extends TestCase {
    */
/**
     * DealsWithErrors interface Mocked
 *//*

    @Mock
    private ErrorManager mockErrorManager;

    */
/**
     * UsesFileSystem Interface member variables.
 *//*

    @Mock
    private PluginFileSystem mockPluginFileSystem;

    @Mock
    private PluginTextFile mockIntraUserLoginXml;

    @Mock
    IntraUserIdentity mockIntraUserIdentity;


    IntraUserSettings intraUserSettings;

    private IntraWalletUserModulePluginRoot testIntraUserModulePluginRoot;


    UUID pluginId;

    @Before
    public void setUp() throws Exception {

        pluginId= UUID.randomUUID();
        testIntraUserModulePluginRoot = new IntraWalletUserModulePluginRoot();
        testIntraUserModulePluginRoot.setPluginFileSystem(mockPluginFileSystem);
        testIntraUserModulePluginRoot.setErrorManager(mockErrorManager);

        setUpMockitoRules();

        testIntraUserModulePluginRoot.setId(pluginId);
    }

    public void setUpMockitoRules()  throws Exception{
        intraUserSettings = new IntraUserSettings();
        intraUserSettings.setLoggedInPublicKey(UUID.randomUUID().toString());
        when(mockPluginFileSystem.getTextFile(pluginId, pluginId.toString(), "intraUsersLogin", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT)).thenReturn(mockIntraUserLoginXml);
        when(mockIntraUserLoginXml.getContent()).thenReturn(XMLParser.parseObject(intraUserSettings));


    }


    @Test
    public void teststart_ThePlugInHasStartedOk_ThrowsCantStartPluginException() throws Exception {

        testIntraUserModulePluginRoot.start();
        ServiceStatus serviceStatus=testIntraUserModulePluginRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.STARTED);
    }


    @Test
    public void startTest_IntraUserModulePluginRootCanStarted_throwsCantStartPluginException() throws Exception {

        when(mockIntraUserLoginXml.getContent()).thenReturn("");

        catchException(testIntraUserModulePluginRoot).start();

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantStartPluginException.class);

    }
}*/
