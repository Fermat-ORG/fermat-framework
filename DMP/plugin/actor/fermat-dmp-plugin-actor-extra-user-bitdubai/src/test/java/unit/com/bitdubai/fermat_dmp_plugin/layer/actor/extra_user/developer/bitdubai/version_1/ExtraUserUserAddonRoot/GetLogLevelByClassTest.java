package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manuel Perez on 26/07/15.
 */
public class GetLogLevelByClassTest {


    ExtraUserUserAddonRoot extraUserUserAddonRoot=new ExtraUserUserAddonRoot();

    String testStringClass="com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser";
    Map<String, LogLevel> testMap=new HashMap<>();

    @Test
    public void getLogLevelByClassTest_setLogginLevelSetStringValidArgument_getsValidLogginLevel() throws Exception{

        LogLevel resultLogLevel=extraUserUserAddonRoot.getLogLevelByClass(testStringClass);
        Assertions.assertThat(resultLogLevel)
                .isNotNull()
                .isEqualTo(LogLevel.MODERATE_LOGGING);

    }

    @Test
    public void getLogLevelByClassTest_setLogginLevelStringSetNotValidArgument_getsNull() throws Exception{

        LogLevel resultLogLevel=extraUserUserAddonRoot.getLogLevelByClass("");
        Assertions.assertThat(resultLogLevel).isNull();

    }

    @Before
    public void setUp() throws Exception{

        testMap.put(testStringClass,LogLevel.MODERATE_LOGGING);
        extraUserUserAddonRoot.setLoggingLevelPerClass(testMap);

    }

}
