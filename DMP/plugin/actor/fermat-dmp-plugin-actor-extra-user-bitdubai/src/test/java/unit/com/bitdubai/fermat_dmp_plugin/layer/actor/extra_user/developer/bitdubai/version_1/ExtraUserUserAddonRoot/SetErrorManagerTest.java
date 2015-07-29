package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by Manuel Perez on 28/07/15.
 */
public class SetErrorManagerTest {

    @Test(expected = Exception.class)
    public void setErrorManagerTest_setNullErrorManagerAnExceptionIsThrown_throwsAnException() throws Exception{

        ExtraUserUserAddonRoot anotherExtraUserUserAddonRoot=new ExtraUserUserAddonRoot();
        MockErrorManager mockErrorManager=null;
        anotherExtraUserUserAddonRoot.setErrorManager(mockErrorManager);
        anotherExtraUserUserAddonRoot.createActor(null);
        Assertions.assertThat(mockErrorManager).isNotNull();

    }

    @Test
    public void setErrorManagerTest_setValidErrorManagerAnExceptionIsThrown_theErrorManagerContainsTheExceptionInformation() throws Exception{

        ExtraUserUserAddonRoot anotherExtraUserUserAddonRoot=new ExtraUserUserAddonRoot();
        MockErrorManager mockErrorManager=new MockErrorManager();
        anotherExtraUserUserAddonRoot.setErrorManager(mockErrorManager);
        anotherExtraUserUserAddonRoot.createActor(null);
        Assertions.assertThat(mockErrorManager).isNotNull();

    }

    @Test
    public void setErrorManagerTest_setValidErrorManager_theErrorManagerIsSet()throws Exception{

        ExtraUserUserAddonRoot extraUserUserAddonRoot=new ExtraUserUserAddonRoot();
        MockErrorManager mockErrorManager=new MockErrorManager();
        extraUserUserAddonRoot.setErrorManager(mockErrorManager);

    }




}
