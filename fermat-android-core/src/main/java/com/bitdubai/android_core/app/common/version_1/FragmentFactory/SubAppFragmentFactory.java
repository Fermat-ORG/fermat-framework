package com.bitdubai.android_core.app.common.version_1.FragmentFactory;


import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.sub_app.developer.FragmentFactory.DeveloperSubAppFragmentFactory;
import com.bitdubai.sub_app.intra_user.fragmentFactory.IntraUserFragmentFactory;
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
            case CWP_INTRA_USER:
                return new IntraUserFragmentFactory();
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + subAppType, "This Code Is Not Valid for the Plugins enum");
        }
    }
}
