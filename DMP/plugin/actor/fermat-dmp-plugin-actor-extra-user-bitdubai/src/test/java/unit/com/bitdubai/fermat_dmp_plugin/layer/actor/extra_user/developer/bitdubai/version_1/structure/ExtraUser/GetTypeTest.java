package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by Manuel Perez on 27/07/15.
 */
public class GetTypeTest {

    @Test
    public void getTypeTest_callingThisMethod_returnsNull() throws Exception{

        ExtraUser testExtraUser=new ExtraUser();
        Actors actors=testExtraUser.getType();
        Assertions.assertThat(actors).isNull();

    }

}
