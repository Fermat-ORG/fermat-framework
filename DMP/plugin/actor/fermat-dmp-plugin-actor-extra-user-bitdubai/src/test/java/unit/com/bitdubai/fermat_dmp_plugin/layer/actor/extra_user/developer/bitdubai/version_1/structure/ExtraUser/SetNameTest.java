package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser;

import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by Manuel Perez on 27/07/15.
 */
public class SetNameTest {

    ExtraUser testExtraUser=new ExtraUser();
    String testName="testName";

    @Test
    public void setNameTest_setAString_theNameIsSetInTheClass() throws Exception{

        testExtraUser.setName(testName);

    }

    @Test
    public void setNameTest_setAStringGetTheStringName_returnsAValidName() throws Exception{

        String returnName;
        testExtraUser.setName(testName);
        returnName=testExtraUser.getName();
        Assertions.assertThat(returnName)
                .isNotNull()
                .isEqualTo(testName);

    }

    @Test
    public void setNameTest_setANullStringGetTheStringName_returnsNull() throws Exception{

        String returnName;
        testExtraUser.setName(null);
        returnName=testExtraUser.getName();
        Assertions.assertThat(returnName).isNull();

    }

}
