package unit.com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.DeveloperBitdubai;

import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.DeveloperBitDubai;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertNotNull;


/**
 * Created by root on 18/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructTest {

    DeveloperBitDubai developerBitDubai;

    @Test
    public void testConstructor_NotNull() throws Exception {
        developerBitDubai = new DeveloperBitDubai();
        assertNotNull(developerBitDubai);
    }

}
