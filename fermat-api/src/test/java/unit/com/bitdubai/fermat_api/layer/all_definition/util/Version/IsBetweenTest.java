package unit.com.bitdubai.fermat_api.layer.all_definition.util.Version;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rodrigo on 7/21/15.
 */
public class IsBetweenTest {

    @Test
    public void isBetweenTrue() {
        Version version = new Version(1, 4, 2);

        Version version1 = new Version(1, 0, 0);
        Version version2 = new Version("2.0.0");

        Assert.assertTrue(version.isBetween(version1, version2));
    }

    @Test
    public void isBetweenFalse() {
        Version version = new Version(1, 5, 2);

        Version version1 = new Version(1, 0, 0);
        Version version2 = new Version("1.4.0");

        Assert.assertFalse(version.isBetween(version1, version2));
    }
}
