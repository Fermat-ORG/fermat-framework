package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Manuel Perez on 26/07/15.
 */
public class GetLogLevelByClassTest_NotSetLogginLevel {

    ExtraUserUserAddonRoot extraUserUserAddonRoot;

    /**
     * I need to test the static method behavior and call the static LogLevel Map
     */
    @Test
<<<<<<< HEAD
    public void getLogLevelByClassTest_testingStaticBehaviorSetAValidArgument_returnNullLogLevel() throws Exception{

        LogLevel resultLogLevel=extraUserUserAddonRoot.getLogLevelByClass("com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser");
        Assertions.assertThat(resultLogLevel)
                .isNull();
=======
    public void getLogLevelByClassTest_notSetLogginLevel_returnDEFAULT_LOG_LEVEL() throws Exception{

        LogLevel resultLogLevel=extraUserUserAddonRoot.getLogLevelByClass("com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser");
        Assertions.assertThat(resultLogLevel)
                .isNotNull()
                .isEqualTo(LogLevel.MINIMAL_LOGGING);
>>>>>>> 3d6a2dd17f9f882b4df2074701a059f003aa3bfe

    }

    @Test
<<<<<<< HEAD
    public void getLogLevelByClassTest_testingStaticBehaviorLevelNullArgument_returnNullLogLevel() throws Exception{
=======
    public void getLogLevelByClassTest_NotSetLogginLevelNullArgument_returnDEFAULT_LOG_LEVEL() throws Exception{
>>>>>>> 3d6a2dd17f9f882b4df2074701a059f003aa3bfe

        extraUserUserAddonRoot=new ExtraUserUserAddonRoot();
        LogLevel resultLogLevel=extraUserUserAddonRoot.getLogLevelByClass(null);
        Assertions.assertThat(resultLogLevel)
                .isNotNull()
                .isEqualTo(LogLevel.MINIMAL_LOGGING);

    }

}
