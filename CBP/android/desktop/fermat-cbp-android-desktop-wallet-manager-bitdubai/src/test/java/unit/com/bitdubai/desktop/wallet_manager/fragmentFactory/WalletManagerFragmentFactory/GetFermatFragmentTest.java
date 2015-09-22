package unit.com.bitdubai.desktop.wallet_manager.fragmentFactory.WalletManagerFragmentFactory;

import com.bitdubai.desktop.wallet_manager.fragmentFactory.WalletManagerFragmentFactory;
import com.bitdubai.desktop.wallet_manager.fragmentFactory.WalletManagerFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;

import org.junit.Before;
import org.junit.Test;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 17/09/15.
 */
public class GetFermatFragmentTest {
    private WalletManagerFragmentFactory fragmentFactory;
    private final String ENUM_TYPE_CODE = "MF";


    @Before
    public void setUp() throws Exception {
        fragmentFactory = new WalletManagerFragmentFactory();
    }

    @Test
    public void fragmentFound() throws Exception {
        WalletManagerFragmentsEnumType enumType = fragmentFactory.getFermatFragmentEnumType(ENUM_TYPE_CODE);
        FermatFragment actualFragment = fragmentFactory.getFermatFragment(enumType);

        assertThat(actualFragment).isInstanceOf(FermatFragment.class);
    }

    @Test
    public void fragmentNotFound() throws Exception {
        catchException(fragmentFactory).getFermatFragment(null);

        assertThat(caughtException()).isInstanceOf(FragmentNotFoundException.class);
    }
}