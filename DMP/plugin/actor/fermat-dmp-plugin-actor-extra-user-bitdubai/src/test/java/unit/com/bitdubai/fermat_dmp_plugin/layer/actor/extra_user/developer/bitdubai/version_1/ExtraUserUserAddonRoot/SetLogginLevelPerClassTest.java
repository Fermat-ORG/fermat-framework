package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manuel Perez on 28/07/15.
 */
public class SetLogginLevelPerClassTest {

    ExtraUserUserAddonRoot testExtraUserUserAddonRoot=new ExtraUserUserAddonRoot();
    Map<String, LogLevel> testLogginLevelMap;
    MockErrorManager mockErrorManager;
    String testStringClass="com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser";
    LogLevel testLogLevel=LogLevel.NOT_LOGGING;

    @Test(expected = Exception.class)
    public void setLogginLevelPerClassTest_setANullMapArgumentNullErrorManager_throwsAnException() throws Exception{

        testLogginLevelMap=null;
        mockErrorManager=null;
        testExtraUserUserAddonRoot.setLoggingLevelPerClass(testLogginLevelMap);

    }

    @Test
    public void setLogginLevelPerClassTest_setANullMapArgument_throwsAGenericException() throws Exception{

        testLogginLevelMap=null;
        mockErrorManager=new MockErrorManager();
        testExtraUserUserAddonRoot.setErrorManager(mockErrorManager);
        testExtraUserUserAddonRoot.setLoggingLevelPerClass(testLogginLevelMap);
        Assertions.assertThat(mockErrorManager).isNotNull();

    }

    @Test
    public void setLogginLevelPerClassTest_setAValidMapArgument_getsAValidLogLevel() throws Exception{

        LogLevel resultLogLevel;
        testLogginLevelMap=new HashMap<>();
        testLogginLevelMap.put(testStringClass,testLogLevel);
        testExtraUserUserAddonRoot.setLoggingLevelPerClass(testLogginLevelMap);
        resultLogLevel=testExtraUserUserAddonRoot.getLogLevelByClass(testStringClass);
        Assertions.assertThat(resultLogLevel)
                .isNotNull()
                .isEqualTo(LogLevel.NOT_LOGGING);

    }

    @Test
    public void setLogginLevelPerClassTest_setNullLevelLogInMapArgument_returnsNull() throws Exception{

        LogLevel resultLogLevel;
        testLogginLevelMap=new HashMap<>();
        testLogginLevelMap.put(testStringClass,null);
        testExtraUserUserAddonRoot.setLoggingLevelPerClass(testLogginLevelMap);
        resultLogLevel=testExtraUserUserAddonRoot.getLogLevelByClass(testStringClass);
        Assertions.assertThat(resultLogLevel)
                .isNull();

    }

    @Test
    public void setLogginLevelPerClassTest_setNullStringClassInMapArgument_throwsAGenericException() throws Exception{

        testLogginLevelMap=new HashMap<>();
        testLogginLevelMap.put(null,testLogLevel);
        mockErrorManager=new MockErrorManager();
        testExtraUserUserAddonRoot.setErrorManager(mockErrorManager);
        testExtraUserUserAddonRoot.setLoggingLevelPerClass(testLogginLevelMap);
        testExtraUserUserAddonRoot.getLogLevelByClass(testStringClass);
        Assertions.assertThat(mockErrorManager)
                .isNotNull();

    }

    @Test
    public void setLogginLevelPerClassTest_setNullValuesInMapArgument_returnsNull() throws Exception{

        LogLevel resultLogLevel;
        testLogginLevelMap=new HashMap<>();
        testLogginLevelMap.put(null,null);
        testExtraUserUserAddonRoot.setLoggingLevelPerClass(testLogginLevelMap);
        resultLogLevel=testExtraUserUserAddonRoot.getLogLevelByClass(testStringClass);
        Assertions.assertThat(resultLogLevel)
                .isNull();

    }

}
