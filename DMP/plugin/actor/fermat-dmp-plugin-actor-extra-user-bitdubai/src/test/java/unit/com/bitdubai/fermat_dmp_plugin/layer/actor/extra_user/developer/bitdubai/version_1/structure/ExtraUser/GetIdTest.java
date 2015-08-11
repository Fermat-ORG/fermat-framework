package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser;

import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by Manuel Perez on 27/07/15.
 */
public class GetIdTest {

    UUID testId=UUID.randomUUID();

    @Test
    public void setNameTest_callThisMethodWithoutSetAName_returnsNull() throws Exception{

        ExtraUser testExtraUser=new ExtraUser();
        UUID id=testExtraUser.getId();
        Assertions.assertThat(id).isNull();

    }

    @Test
    public void setIdTest_setAValidUUIDGetTheId_returnsAUUID() throws Exception{

        UUID returnId;
        ExtraUser testExtraUser=new ExtraUser();
        testExtraUser.setId(testId);
        returnId=testExtraUser.getId();
        Assertions.assertThat(returnId)
                .isNotNull()
                .isEqualTo(testId);

    }

    @Test
    public void setNameTest_setANullUUIDGetTheId_returnsUUID() throws Exception{

        UUID returnId;
        ExtraUser testExtraUser=new ExtraUser();
        testExtraUser.setName(null);
        returnId=testExtraUser.getId();
        Assertions.assertThat(returnId).isNull();

    }

}
