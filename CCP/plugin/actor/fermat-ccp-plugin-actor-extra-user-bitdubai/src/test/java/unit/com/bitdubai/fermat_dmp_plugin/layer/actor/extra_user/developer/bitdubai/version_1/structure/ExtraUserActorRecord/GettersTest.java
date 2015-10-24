/*
package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserActorRecord;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantShowProfileImageException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserActorRecord;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;

*/
/**
 * Created by natalia on 03/09/15.
 *//*

public class GettersTest {

    ExtraUserActorRecord extraUserActorRecord;

    private String actorPrivateKey;

    private String actorPublicKey;

    private String name;

    private byte[] photo;

    @Mock
    private PluginFileSystem mockPluginFileSystem;

    @Before
    public void setUpVariable1(){

        name = "alias";
        actorPublicKey= "publicKey";
        actorPrivateKey= "privateKey";
        photo = new byte[10];


        extraUserActorRecord = new ExtraUserActorRecord(actorPublicKey,actorPrivateKey, name,photo);

    }

    @Test
    public void getNameAreEquals(){
        assertThat(extraUserActorRecord.getName()).isEqualTo(name);
    }

    @Test
    public void getPublicKeyAreEquals(){
        assertThat(extraUserActorRecord.getActorPublicKey()).isEqualTo(actorPublicKey);
    }

    @Test
    public void getProfileImage_AreEquals() throws CantShowProfileImageException {
        assertThat(extraUserActorRecord.getPhoto()).isEqualTo(photo);
    }

    @Test
    public void getType_AreEquals() throws CantShowProfileImageException {
        assertThat(extraUserActorRecord.getType()).isEqualTo(Actors.EXTRA_USER);
    }
}



*/
