package unit.com.bitdubai.fermat_api.layer.all_definition.util.Version;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rodrigo on 7/21/15.
 */
public class HashTest {

    @Test
    public void equalHashTest() {
        Version version1 = new Version(1, 2, 3);
        Version version2 = new Version("1.2.3");

        Assert.assertEquals(version1.hashCode(), version2.hashCode());

        Version version3 = version2;
        Assert.assertEquals(version2.hashCode(), version3.hashCode());
    }

    @Test
    public void differentHashTest() {
        Version version1 = new Version(1, 2, 3);
        Version version2 = new Version("1.3.3");

        Assert.assertNotEquals(version1.hashCode(), version2.hashCode());

    }
}
