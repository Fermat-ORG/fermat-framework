package unit.com.bitdubai.fermat_api.layer.all_definition.util.ValidityVersion;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.ValidityVersion;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rodrigo on 7/29/15.
 */
public class PublicMethodsTests {

    @Test
    public void isValidInPlatformTest() throws InvalidParameterException {
        //Platform version es 1.0.0
        ValidityVersion validityVersion = new ValidityVersion(new Version("0.2.0"), new Version(2,0,0));
        Assert.assertTrue(validityVersion.isValidInPlatform());


        validityVersion = new ValidityVersion(new Version("1.2.0"), new Version(2,0,0));
        Assert.assertFalse(validityVersion.isValidInPlatform());
    }

    @Test (expected = InvalidParameterException.class)
    public void invalidaValidityVersionsTest() throws InvalidParameterException {

        ValidityVersion validityVersion = new ValidityVersion(new Version("3.2.0"), new Version(2,0,0));
    }

    public void isValidInObjectVersionTest() throws InvalidParameterException {

        ValidityVersion validityVersion = new ValidityVersion(new Version("1.2.0"), new Version(2,0,0));
        Assert.assertTrue(validityVersion.isValidInVersion(new Version("1.3.3")));

        validityVersion = new ValidityVersion(new Version("1.2.0"), new Version(2,0,0));
        Assert.assertFalse(validityVersion.isValidInVersion(new Version("5.3.3")));
    }



}
