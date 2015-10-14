package unit.com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.structure.Repository;

import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantShowProfileImageException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.structure.Repository;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 09/09/15.
 */
public class GettersTest {
    Repository repository;

    private String path;

    private String skinName;

    private String navigationStructureVersion;

    @Before
    public void setUpVariable1(){


        path = "path1";
        skinName = "skinName1";
        navigationStructureVersion = "version1";


        repository = new Repository(skinName, navigationStructureVersion, path);

    }

    @Test
    public void getPathAreEquals(){
        assertThat(repository.getPath()).isEqualTo(path);
    }

    @Test
    public void getNavigationStructureVersion_AreEquals(){
        assertThat(repository.getNavigationStructureVersion()).isEqualTo(navigationStructureVersion);
    }

    @Test
    public void getSkinName_AreEquals(){
        assertThat(repository.getSkinName()).isEqualTo(skinName);
    }
}

