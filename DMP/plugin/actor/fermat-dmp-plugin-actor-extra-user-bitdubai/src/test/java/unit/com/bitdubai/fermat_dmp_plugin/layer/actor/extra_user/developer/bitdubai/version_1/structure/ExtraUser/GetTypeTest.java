package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.*;
/**
 * Created by Manuel Perez on 27/07/15.
 */
public class GetTypeTest {

    @Test
    public void getTypeTest_DefaultConstruction_IsExtraUser() throws Exception{
        ExtraUser testExtraUser=new ExtraUser();
        assertThat(testExtraUser.getType()).isEqualTo(Actors.EXTRA_USER);
    }

}
