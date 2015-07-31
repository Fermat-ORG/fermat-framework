package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser;

import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by Manuel Perez on 27/07/15.
 */
public class SetIdTest {

    ExtraUser testExtraUser=new ExtraUser();
    UUID testId=UUID.randomUUID();

    @Test
    public void setIdTest_setAValidUUID_theIdIsSetInTheClass() throws Exception{

        testExtraUser.setId(testId);

    }

    @Test
    public void setIdTest_setAValidUUIDGetTheId_returnsAUUID() throws Exception{

        UUID returnId;
        testExtraUser.setId(testId);
        returnId=testExtraUser.getId();
        Assertions.assertThat(returnId)
                .isNotNull()
                .isEqualTo(testId);

    }

    @Test
    public void setNameTest_setANullUUIDGetTheId_returnsUUID() throws Exception{

        UUID returnId;
        testExtraUser.setName(null);
        returnId=testExtraUser.getId();
        Assertions.assertThat(returnId).isNull();

    }

}
