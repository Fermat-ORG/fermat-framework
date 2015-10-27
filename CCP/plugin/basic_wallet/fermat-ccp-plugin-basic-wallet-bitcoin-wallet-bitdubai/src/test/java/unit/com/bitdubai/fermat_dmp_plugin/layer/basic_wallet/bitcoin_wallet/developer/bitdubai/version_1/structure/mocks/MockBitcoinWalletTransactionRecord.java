/*
package unit.com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.mocks;

import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;

import java.util.UUID;

*/
/**
 * Created by jorgegonzalez on 2015.07.14..
 *//*

public class MockBitcoinWalletTransactionRecord implements BitcoinWalletTransactionRecord {

    private static String ANY_BITCOIN_PUBLIC_ADDRESS_FROM = "11NvDRnGsX57oNsmEWRqzv8QQuK1GU9g6Mb";
    private static String ANY_BITCOIN_PUBLIC_ADDRESS_TO = "156jBLBvtaDccomhbiWDoySKuzx1AbWUG8";

    @Override
    public CryptoAddress getAddressFrom() {
        return new CryptoAddress(ANY_BITCOIN_PUBLIC_ADDRESS_FROM, CryptoCurrency.BITCOIN);
    }

    @Override
    public UUID getTransactionId() {
        return UUID.randomUUID();
    }

    @Override
    public CryptoAddress getAddressTo() {
        return new CryptoAddress(ANY_BITCOIN_PUBLIC_ADDRESS_TO, CryptoCurrency.BITCOIN);
    }

    @Override
    public long getAmount() {
        return 1L;
    }

    @Override
    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    @Override
    public String getMemo() {
        return "";
    }

    @Override
    public String getTransactionHash() {
        return CryptoHasher.performSha256("MOCK RECORD");
    }

    @Override
    public UUID getActorTo() {
        return UUID.randomUUID();
    }

    @Override
    public UUID getActorFrom() {
        return UUID.randomUUID();
    }

    @Override
    public Actors getActorToType() {
        return Actors.INTRA_USER;
    }

    @Override
    public Actors getActorFromType() {
        return Actors.EXTRA_USER;
    }
}
*/
