package unit.com.bitdubai.sub_app.crypto_broker_identity.fragmentFactory.CryptoBrokerIdentityFragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.sub_app.crypto_broker_identity.fragmentFactory.CryptoBrokerIdentityFragmentFactory;
import com.bitdubai.sub_app.crypto_broker_identity.fragmentFactory.CryptoBrokerIdentityFragmentsEnumType;

import org.junit.Before;
import org.junit.Test;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 17/09/15.
 */
public class GetFermatFragmentTest {
    private CryptoBrokerIdentityFragmentFactory fragmentFactory;
    private final String ENUM_TYPE_CODE = "MF";


    @Before
    public void setUp() throws Exception {
        fragmentFactory = new CryptoBrokerIdentityFragmentFactory();
    }

    @Test
    public void fragmentFound() throws Exception {
        CryptoBrokerIdentityFragmentsEnumType enumType = fragmentFactory.getFermatFragmentEnumType(ENUM_TYPE_CODE);
        FermatFragment actualFragment = fragmentFactory.getFermatFragment(enumType);

        assertThat(actualFragment).isInstanceOf(FermatFragment.class);
    }

    @Test
    public void fragmentNotFound() throws Exception {
        catchException(fragmentFactory).getFermatFragment(null);

        assertThat(caughtException()).isInstanceOf(FragmentNotFoundException.class);
    }
}