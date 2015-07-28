package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser;

import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by Manuel Perez on 27/07/15.
 */
public class GetNameTest {

    String testName="testName";

    @Test
    public void getNameTest_callThisMethodWithoutSetAName_returnsNull() throws Exception{

        ExtraUser testExtraUser=new ExtraUser();
        String name=testExtraUser.getName();
        Assertions.assertThat(name).isNull();

    }

    @Test
    public void getNameTest_setAStringGetTheStringName_returnsAValidName() throws Exception{

        String returnName;
        ExtraUser testExtraUser=new ExtraUser();
        testExtraUser.setName(testName);
        returnName=testExtraUser.getName();
        Assertions.assertThat(returnName)
                .isNotNull()
                .isEqualTo(testName);

    }

    @Test
    public void getNameTest_setANullStringGetTheStringName_returnsNull() throws Exception{

        String returnName;
        ExtraUser testExtraUser=new ExtraUser();
        testExtraUser.setName(null);
        returnName=testExtraUser.getName();
        Assertions.assertThat(returnName).isNull();

    }

}
