package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.BrokerIdentityBusinessInfo;

/**
 * Created by nelson on 24/11/15.
 */
public class CryptoCustomerWalletModuleBrokerIdentityBusinessInfo implements BrokerIdentityBusinessInfo {

    private String publicKey;
    private String alias;
    private byte[] img;
    private FermatEnum merchandiseCurrency;


    public CryptoCustomerWalletModuleBrokerIdentityBusinessInfo(String alias, byte[] img, String publicKey, FermatEnum merchandiseCurrency) {
        this.alias = alias;
        this.img = img;
        this.publicKey = publicKey;
        this.merchandiseCurrency = merchandiseCurrency;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public FermatEnum getMerchandiseCurrency() {
        return merchandiseCurrency;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public byte[] getProfileImage() {
        return img;
    }

    @Override
    public void setNewProfileImage(byte[] imageBytes) {

    }

    @Override
    public boolean isPublished() {
        return true;
    }

    @Override
    public String createMessageSignature(String message) throws CantCreateMessageSignatureException {
        return null;
    }
}
