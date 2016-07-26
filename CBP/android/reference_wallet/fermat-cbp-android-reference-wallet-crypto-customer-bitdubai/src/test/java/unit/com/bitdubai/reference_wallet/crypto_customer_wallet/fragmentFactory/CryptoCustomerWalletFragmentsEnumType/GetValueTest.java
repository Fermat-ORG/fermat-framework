package unit.com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory.CryptoCustomerWalletFragmentsEnumType;

import com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory.CryptoCustomerWalletFragmentsEnumType;

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

        CryptoCustomerWalletFragmentsEnumType value = CryptoCustomerWalletFragmentsEnumType.getValue(ENUM_TYPE_CODE);
        assertThat(value).isNotNull();
    }

    @Test
    public void recibeString_valueNotFound_returnNull() throws Exception {
        CryptoCustomerWalletFragmentsEnumType value = CryptoCustomerWalletFragmentsEnumType.getValue("");
        assertThat(value).isNull();
    }

    @Test
    public void recibeNull_valueNotFound_returnNull() throws Exception {
        CryptoCustomerWalletFragmentsEnumType value = CryptoCustomerWalletFragmentsEnumType.getValue(null);
        assertThat(value).isNull();
    }
}