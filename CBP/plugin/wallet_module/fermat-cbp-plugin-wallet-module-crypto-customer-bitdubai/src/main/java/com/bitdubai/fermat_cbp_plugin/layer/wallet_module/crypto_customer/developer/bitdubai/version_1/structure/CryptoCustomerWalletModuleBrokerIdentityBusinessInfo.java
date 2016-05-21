package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.MerchandiseExchangeRate;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.BrokerIdentityBusinessInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 24/11/15.
 */
public class CryptoCustomerWalletModuleBrokerIdentityBusinessInfo implements BrokerIdentityBusinessInfo, Serializable {

    private String publicKey;
    private String alias;
    private byte[] img;
    private Currency merchandiseCurrency;

    private List<MerchandiseExchangeRate> quotes;


    public CryptoCustomerWalletModuleBrokerIdentityBusinessInfo(String alias, byte[] img, String publicKey, Currency merchandiseCurrency) {
        this.alias = alias;
        this.img = img;
        this.publicKey = publicKey;
        this.merchandiseCurrency = merchandiseCurrency;
        quotes = new ArrayList<>();
    }

    public CryptoCustomerWalletModuleBrokerIdentityBusinessInfo(ActorIdentity brokerIdentity, Currency merchandiseCurrency) {
        this.alias = brokerIdentity.getAlias();
        this.img = brokerIdentity.getProfileImage();
        this.publicKey = brokerIdentity.getPublicKey();
        this.merchandiseCurrency = merchandiseCurrency;
        quotes = new ArrayList<>();
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public FermatEnum getMerchandise() {
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
    public List<MerchandiseExchangeRate>  getQuotes() {
        return quotes;
    }

    @Override
    public void setNewProfileImage(byte[] imageBytes) {
        img = imageBytes;
    }

    @Override
    public ExposureLevel getExposureLevel() {
        return ExposureLevel.PUBLISH;
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
