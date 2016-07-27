package unit.com.bitdubai.fermat_api.layer.all_definition.util.Version;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rodrigo on 7/21/15.
 */
public class EqualTest {

    @Test
    public void equalTrue() {
        Version version1 = new Version(1, 2, 3);
        Version version2 = new Version(1, 2, 3);

        Assert.assertTrue(version1.equals(version2));
    }

    @Test
    public void equalFalse() {
        Version version1 = new Version(1, 2, 3);
        Version version2 = new Version(1, 0, 0);

        Assert.assertFalse(version1.equals(version2));
    }

    @Test
    public void equalTrue2() {
        Version version1 = new Version(1, 2, 3);
        Version version2 = version1;

        Assert.assertTrue(version1.equals(version2));
    }
}
