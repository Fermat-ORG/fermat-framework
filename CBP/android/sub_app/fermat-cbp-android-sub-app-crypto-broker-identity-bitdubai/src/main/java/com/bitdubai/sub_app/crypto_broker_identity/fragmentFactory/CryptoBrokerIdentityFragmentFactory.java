package com.bitdubai.sub_app.crypto_broker_identity.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_broker_identity.fragments.CreateCryptoBrokerIdentityFragment;
import com.bitdubai.sub_app.crypto_broker_identity.fragments.CryptoBrokerIdentityListFragment;
import com.bitdubai.sub_app.crypto_broker_identity.fragments.CryptoBrokerImageCropperFragment;
import com.bitdubai.sub_app.crypto_broker_identity.fragments.EditCryptoBrokerIdentityFragment;
import com.bitdubai.sub_app.crypto_broker_identity.fragments.GeolocationBrokerIdentityFragment;

import static com.bitdubai.sub_app.crypto_broker_identity.fragmentFactory.CryptoBrokerIdentityFragmentsEnumType.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY_FRAGMENT;
import static com.bitdubai.sub_app.crypto_broker_identity.fragmentFactory.CryptoBrokerIdentityFragmentsEnumType.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY_FRAGMENT;
import static com.bitdubai.sub_app.crypto_broker_identity.fragmentFactory.CryptoBrokerIdentityFragmentsEnumType.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_GEOLOCATION_IDENTITY_FRAGMENT;
import static com.bitdubai.sub_app.crypto_broker_identity.fragmentFactory.CryptoBrokerIdentityFragmentsEnumType.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_IMAGE_CROPPER_FRAGMENT;
import static com.bitdubai.sub_app.crypto_broker_identity.fragmentFactory.CryptoBrokerIdentityFragmentsEnumType.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_MAIN_FRAGMENT;

/**
 * Created by Nelson Ramirez on 2015.09
 */
public class CryptoBrokerIdentityFragmentFactory extends FermatFragmentFactory<ReferenceAppFermatSession<CryptoBrokerWalletModuleManager>, SubAppResourcesProviderManager, CryptoBrokerIdentityFragmentsEnumType> {

    @Override
    public AbstractFermatFragment getFermatFragment(CryptoBrokerIdentityFragmentsEnumType fragments) throws FragmentNotFoundException {
        if (fragments == CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_MAIN_FRAGMENT)
            return CryptoBrokerIdentityListFragment.newInstance();

        if (fragments == CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_IMAGE_CROPPER_FRAGMENT)
            return CryptoBrokerImageCropperFragment.newInstance();

        if (fragments == CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY_FRAGMENT)
            return CreateCryptoBrokerIdentityFragment.newInstance();

        if (fragments == CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY_FRAGMENT)
            return EditCryptoBrokerIdentityFragment.newInstance();
        if (fragments == CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_GEOLOCATION_IDENTITY_FRAGMENT)
            return GeolocationBrokerIdentityFragment.newInstance();

        throw createFragmentNotFoundException(fragments);
    }

    @Override
    public CryptoBrokerIdentityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CryptoBrokerIdentityFragmentsEnumType.getValue(key);
    }

    private FragmentNotFoundException createFragmentNotFoundException(FermatFragmentsEnumType fragments) {
        String possibleReason, context;

        if (fragments == null) {
            possibleReason = "The parameter 'fragments' is NULL";
            context = "Null Value";
        } else {
            possibleReason = "Not found in switch block";
            context = fragments.toString();
        }

        return new FragmentNotFoundException("Fragment not found", new Exception(), context, possibleReason);
    }

}
