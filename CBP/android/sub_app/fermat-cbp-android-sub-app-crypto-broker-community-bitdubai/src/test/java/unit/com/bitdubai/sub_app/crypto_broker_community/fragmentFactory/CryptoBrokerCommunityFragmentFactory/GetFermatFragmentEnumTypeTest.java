package unit.com.bitdubai.sub_app.crypto_broker_community.fragmentFactory.CryptoBrokerCommunityFragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.sub_app.crypto_broker_community.fragmentFactory.CryptoBrokerCommunityFragmentFactory;
import com.bitdubai.sub_app.crypto_broker_community.fragmentFactory.CryptoBrokerCommunityFragmentsEnumType;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 17/09/15.
 */
public class GetFermatFragmentEnumTypeTest {
    private CryptoBrokerCommunityFragmentFactory fragmentFactory;
    private final String ENUM_TYPE_CODE = "MF";


    @Before
    public void setUp() throws Exception {
        fragmentFactory = new CryptoBrokerCommunityFragmentFactory();
    }


    @Test
    public void enumTypeFound() throws Exception {
        FermatFragmentsEnumType expectedEnumType = CryptoBrokerCommunityFragmentsEnumType.MAIN_FRAGMET;
        FermatFragmentsEnumType actualEnumType = fragmentFactory.getFermatFragmentEnumType(ENUM_TYPE_CODE);

        assertThat(actualEnumType).isEqualTo(expectedEnumType);
    }


    @Test
    public void enumTypeNotFound() throws Exception {
        FermatFragmentsEnumType actualEnumType = fragmentFactory.getFermatFragmentEnumType(null);

        assertThat(actualEnumType).isNull();
    }
}