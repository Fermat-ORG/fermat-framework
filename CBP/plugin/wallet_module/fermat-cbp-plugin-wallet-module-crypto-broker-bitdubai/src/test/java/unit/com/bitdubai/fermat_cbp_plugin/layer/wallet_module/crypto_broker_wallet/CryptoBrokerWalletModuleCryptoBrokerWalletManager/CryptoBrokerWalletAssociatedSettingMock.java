package unit.com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker_wallet.CryptoBrokerWalletModuleCryptoBrokerWalletManager;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;

import java.util.UUID;


/**
 * Created by nelsonalfo on 19/04/16.
 */
public class CryptoBrokerWalletAssociatedSettingMock implements CryptoBrokerWalletAssociatedSetting {

    private UUID id;
    private Platforms platform;
    private String walletPublicKey;
    private Currency merchandise;
    private String bankAccount;

    public CryptoBrokerWalletAssociatedSettingMock(Platforms platform, String walletPublicKey, Currency merchandise, String bankAccount) {
        this.bankAccount = bankAccount;
        id = UUID.randomUUID();
        this.platform = platform;
        this.walletPublicKey = walletPublicKey;
        this.merchandise = merchandise;
    }

    public CryptoBrokerWalletAssociatedSettingMock(Currency merchandise) {
        id = UUID.randomUUID();
        this.walletPublicKey = "WALLET_PUBLIC_KEY";
        this.merchandise = merchandise;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {

    }

    @Override
    public String getBrokerPublicKey() {
        return WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();
    }

    @Override
    public void setBrokerPublicKey(String brokerPublicKey) {

    }

    @Override
    public Platforms getPlatform() {
        return platform;
    }

    @Override
    public void setPlatform(Platforms platform) {

    }

    @Override
    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    @Override
    public void setWalletPublicKey(String walletPublicKey) {

    }

    @Override
    public Currency getMerchandise() {
        return merchandise;
    }

    @Override
    public void setMerchandise(Currency merchandise) {

    }

    @Override
    public MoneyType getMoneyType() {
        return null;
    }

    @Override
    public void setMoneyType(MoneyType moneyType) {

    }

    @Override
    public String getBankAccount() {
        return bankAccount;
    }

    @Override
    public void setBankAccount(String bankAccount) {

    }
}
