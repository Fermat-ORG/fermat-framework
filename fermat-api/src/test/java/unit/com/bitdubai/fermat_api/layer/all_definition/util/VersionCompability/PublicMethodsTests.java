package unit.com.bitdubai.fermat_api.layer.all_definition.util.VersionCompability;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.VersionCompatibility;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rodrigo on 7/29/15.
 */
public class PublicMethodsTests {

    @Test
    public void isValidInPlatformTest() throws InvalidParameterException {
        //Platform version es 1.0.0
        VersionCompatibility versionCompability = new VersionCompatibility(new Version("0.2.0"), new Version(2, 0, 0));
        Assert.assertTrue(versionCompability.isValidInPlatform());


        versionCompability = new VersionCompatibility(new Version("1.2.0"), new Version(2, 0, 0));
        Assert.assertFalse(versionCompability.isValidInPlatform());
    }

    @Test(expected = InvalidParameterException.class)
    public void invalidaValidityVersionsTest() throws InvalidParameterException {

        VersionCompatibility versionCompability = new VersionCompatibility(new Version("3.2.0"), new Version(2, 0, 0));
    }

    public void isValidInObjectVersionTest() throws InvalidParameterException {

        VersionCompatibility versionCompability = new VersionCompatibility(new Version("1.2.0"), new Version(2, 0, 0));
        Assert.assertTrue(versionCompability.isValidInVersion(new Version("1.3.3")));

        versionCompability = new VersionCompatibility(new Version("1.2.0"), new Version(2, 0, 0));
        Assert.assertFalse(versionCompability.isValidInVersion(new Version("5.3.3")));
    }


}
