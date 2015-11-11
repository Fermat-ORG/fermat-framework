package com.bitdubai.android_core.app.common.version_1.fragment_factory;


import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.factory.AssetFactoryFragmentFactory;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.factory.AssetIssuerCommunityFragmentFactory;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_identity_bitdubai.fragmentFactory.IssuerIdentityFragmentFactory;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.factory.CommunityUserFragmentFactory;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.factory.AssetRedeemPointCommunityFragmentFactory;
import com.bitdubai.sub_app.crypto_broker_identity.fragmentFactory.CryptoBrokerIdentityFragmentFactory;
import com.bitdubai.sub_app.crypto_customer_identity.fragmentFactory.CryptoCustomerIdentityFragmentFactory;
import com.bitdubai.sub_app.developer.FragmentFactory.DeveloperSubAppFragmentFactory;
import com.bitdubai.sub_app.intra_user_community.fragmentFactory.IntraUserFragmentFactory;
import com.bitdubai.sub_app.intra_user_identity.fragmentFactory.IntraUserIdentityFragmentFactory;
import com.bitdubai.sub_app.wallet_factory.factory.WalletFactoryFragmentFactory;
import com.bitdubai.sub_app.wallet_publisher.FragmentFactory.WalletPublisherFragmentFactory;
import com.bitdubai.sub_app.wallet_store.fragmentFactory.WalletStoreFragmentFactory;

/**
 * Created by Matias Furszyfer on 2015.08.19..
 */

public class SubAppFragmentFactory {

    public static com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppFragmentFactory getFragmentFactoryBySubAppType(SubApps subAppType) throws InvalidParameterException {
        switch (subAppType) {
            case CWP_WALLET_FACTORY:
                return new WalletFactoryFragmentFactory();
            case CWP_WALLET_STORE:
                return new WalletStoreFragmentFactory();
            case CWP_WALLET_PUBLISHER:
                return new WalletPublisherFragmentFactory();
            case CWP_DEVELOPER_APP:
                return new DeveloperSubAppFragmentFactory();
            case CWP_INTRA_USER_IDENTITY:
                return new IntraUserIdentityFragmentFactory();
            case CCP_INTRA_USER_COMMUNITY:
                return new IntraUserFragmentFactory();
            case DAP_ASSETS_FACTORY:
                return new AssetFactoryFragmentFactory();
            case DAP_ASSETS_COMMUNITY_ISSUER:
                return new AssetIssuerCommunityFragmentFactory();
            case DAP_ASSETS_COMMUNITY_USER:
                return new CommunityUserFragmentFactory();
            case DAP_ASSETS_COMMUNITY_REDEEM_POINT:
                return new AssetRedeemPointCommunityFragmentFactory();
            case DAP_ASSETS_IDENTITY_ISSUER:
                return new IssuerIdentityFragmentFactory();
            case CBP_CRYPTO_BROKER_IDENTITY:
                return new CryptoBrokerIdentityFragmentFactory();
            case CBP_CRYPTO_CUSTOMER_IDENTITY:
                return new CryptoCustomerIdentityFragmentFactory();
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + subAppType, "This Code Is Not Valid for the Plugins SubAppFragmentFactory");
        }
    }
}
