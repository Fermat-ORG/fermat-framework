package com.bitdubai.sub_app.crypto_customer_identity.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_customer_identity.fragments.CreateCryptoCustomerIdentityFragment;
import com.bitdubai.sub_app.crypto_customer_identity.fragments.CryptoCustomerIdentityListFragment;
import com.bitdubai.sub_app.crypto_customer_identity.fragments.CryptoCustomerImageCropperFragment;
import com.bitdubai.sub_app.crypto_customer_identity.fragments.EditCryptoCustomerIdentityFragment;
import com.bitdubai.sub_app.crypto_customer_identity.fragments.GeolocationCustomerIdentityFragment;

import static com.bitdubai.sub_app.crypto_customer_identity.fragmentFactory.CryptoCustomerIdentityFragmentsEnumType.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT;
import static com.bitdubai.sub_app.crypto_customer_identity.fragmentFactory.CryptoCustomerIdentityFragmentsEnumType.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT;
import static com.bitdubai.sub_app.crypto_customer_identity.fragmentFactory.CryptoCustomerIdentityFragmentsEnumType.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_GEOLOCATION_IDENTITY_FRAGMENT;
import static com.bitdubai.sub_app.crypto_customer_identity.fragmentFactory.CryptoCustomerIdentityFragmentsEnumType.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_IMAGE_CROPPER_FRAGMENT;
import static com.bitdubai.sub_app.crypto_customer_identity.fragmentFactory.CryptoCustomerIdentityFragmentsEnumType.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoCustomerIdentityFragmentFactory extends FermatFragmentFactory<ReferenceAppFermatSession<CryptoCustomerIdentityModuleManager>, SubAppResourcesProviderManager, CryptoCustomerIdentityFragmentsEnumType> {


    @Override
    public AbstractFermatFragment getFermatFragment(CryptoCustomerIdentityFragmentsEnumType fragments) throws FragmentNotFoundException {

        if (fragments == CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT)
            return CryptoCustomerIdentityListFragment.newInstance();

        if (fragments == CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_IMAGE_CROPPER_FRAGMENT)
            return CryptoCustomerImageCropperFragment.newInstance();


        if (fragments == CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT)
            return CreateCryptoCustomerIdentityFragment.newInstance();

        if (fragments == CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT)
            return EditCryptoCustomerIdentityFragment.newInstance();
        if (fragments == CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_GEOLOCATION_IDENTITY_FRAGMENT)
            return GeolocationCustomerIdentityFragment.newInstance();

        throw createFragmentNotFoundException(fragments);
    }

    @Override
    public CryptoCustomerIdentityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CryptoCustomerIdentityFragmentsEnumType.getValue(key);
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
