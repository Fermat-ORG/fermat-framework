package unit.com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentsEnumType;

import com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentsEnumType;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Unit Test para el metodo getValue() de CryptoBrokerWalletFragmentsEnumType
 * <p/>
 * Created by nelson on 17/09/15.
 */
public class GetValueTest {
    @Test
    public void recibeString_valueFound() throws Exception {
        final String ENUM_TYPE_CODE = "MF";

        CryptoBrokerWalletFragmentsEnumType value = CryptoBrokerWalletFragmentsEnumType.getValue(ENUM_TYPE_CODE);
        assertThat(value).isNotNull();
    }

    @Test
    public void recibeString_valueNotFound_returnNull() throws Exception {
        CryptoBrokerWalletFragmentsEnumType value = CryptoBrokerWalletFragmentsEnumType.getValue("");
        assertThat(value).isNull();
    }

    @Test
    public void recibeNull_valueNotFound_returnNull() throws Exception {
        CryptoBrokerWalletFragmentsEnumType value = CryptoBrokerWalletFragmentsEnumType.getValue(null);
        assertThat(value).isNull();
    }
}