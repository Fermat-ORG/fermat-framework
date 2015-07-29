package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Manuel Perez on 26/07/15.
 */
public class GetLogLevelByClassTest_NotSetLogginLevel {

<<<<<<< HEAD
    /**
     * I need to test the static method behavior and call the static LogLevel Map
     */
    @Test
    public void getLogLevelByClassTest_testingStaticBehaviorSetAValidArgument_returnNullLogLevel() throws Exception{

        LogLevel resultLogLevel=ExtraUserUserAddonRoot.getLogLevelByClass("com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser");
        Assertions.assertThat(resultLogLevel)
                .isNotNull();

=======
    ExtraUserUserAddonRoot extraUserUserAddonRoot;

    //TODO: This is not going to work in real life, the whole sense of the static logic of the method is going to bug this behaviour. This should be an issue of the logging system
    @Ignore
    @Test
    public void getLogLevelByClassTest_notSetLogginLevel_ReturnNull() throws Exception{
        LogLevel resultLogLevel =  ExtraUserUserAddonRoot.getLogLevelByClass("com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser");
        Assertions.assertThat(resultLogLevel).isNull();
>>>>>>> newbranch/master
    }

    @Test
    public void getLogLevelByClassTest_testingStaticBehaviorLevelNullArgument_returnNullLogLevel() throws Exception{

<<<<<<< HEAD
        LogLevel resultLogLevel=ExtraUserUserAddonRoot.getLogLevelByClass(null);
=======
        extraUserUserAddonRoot=new ExtraUserUserAddonRoot();
        LogLevel resultLogLevel =  ExtraUserUserAddonRoot.getLogLevelByClass(null);
>>>>>>> newbranch/master
        Assertions.assertThat(resultLogLevel)
                .isNotNull()
                .isEqualTo(LogManagerForDevelopers.DEFAULT_LOG_LEVEL);

    }

}