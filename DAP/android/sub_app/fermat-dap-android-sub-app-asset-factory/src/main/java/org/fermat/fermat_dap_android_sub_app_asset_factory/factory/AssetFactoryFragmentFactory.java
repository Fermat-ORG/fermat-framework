package org.fermat.fermat_dap_android_sub_app_asset_factory.factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import org.fermat.fermat_dap_android_sub_app_asset_factory.fragments.AssetEditorFragment;
import org.fermat.fermat_dap_android_sub_app_asset_factory.fragments.SettingsFactoryFragment;
import org.fermat.fermat_dap_android_sub_app_asset_factory.fragments.SettingsFactoryNetworkFragment;
import org.fermat.fermat_dap_android_sub_app_asset_factory.fragments.SettingsFactoryNotificationFragment;
import org.fermat.fermat_dap_android_sub_app_asset_factory.sessions.AssetFactorySessionReferenceApp;
import org.fermat.fermat_dap_android_sub_app_asset_factory.v3.fragments.DraftAssetsHomeFragment;
import org.fermat.fermat_dap_android_sub_app_asset_factory.v3.fragments.PublishedAssetsHomeFragment;
import org.fermat.fermat_dap_android_sub_app_asset_factory.v3.fragments.WizardCryptoFragment;
import org.fermat.fermat_dap_android_sub_app_asset_factory.v3.fragments.WizardMultimediaFragment;
import org.fermat.fermat_dap_android_sub_app_asset_factory.v3.fragments.WizardPropertiesFragment;
import org.fermat.fermat_dap_android_sub_app_asset_factory.v3.fragments.WizardVerifyFragment;

/**
 * AssetFactoryFragmentFactory
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public class AssetFactoryFragmentFactory extends FermatFragmentFactory<AssetFactorySessionReferenceApp, SubAppResourcesProviderManager, AssetFactoryFragmentsEnumType> {

    @Override
    public AbstractFermatFragment getFermatFragment(AssetFactoryFragmentsEnumType fragments) throws FragmentNotFoundException {
        if (fragments.equals(AssetFactoryFragmentsEnumType.DAP_SUB_APP_ASSET_FACTORY_EDITABLE_TAB_FRAGMENT))
            return DraftAssetsHomeFragment.newInstance();
        if (fragments.equals(AssetFactoryFragmentsEnumType.DAP_SUB_APP_ASSET_FACTORY_PUBLISHED_TAB_FRAGMENT))
            return PublishedAssetsHomeFragment.newInstance();
        if (fragments.equals(AssetFactoryFragmentsEnumType.DAP_SUB_APP_ASSET_EDITOR_ACTIVITY))
            return AssetEditorFragment.newInstance(DraftAssetsHomeFragment.getAssetForEdit());
        if (fragments.equals(AssetFactoryFragmentsEnumType.DAP_SUB_APP_ASSET_FACTORY_SETTINGS))
            return SettingsFactoryFragment.newInstance();
        if (fragments.equals(AssetFactoryFragmentsEnumType.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NETWORK_MAIN))
            return SettingsFactoryNetworkFragment.newInstance();
        if (fragments.equals(AssetFactoryFragmentsEnumType.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NOTIFICATIONS))
            return SettingsFactoryNotificationFragment.newInstance();
        if (fragments.equals(AssetFactoryFragmentsEnumType.DAP_SUB_APP_ASSET_FACTORY_WIZARD_MULTIMEDIA))
            return WizardMultimediaFragment.newInstance();
        if (fragments.equals(AssetFactoryFragmentsEnumType.DAP_SUB_APP_ASSET_FACTORY_WIZARD_PROPERTIES))
            return WizardPropertiesFragment.newInstance();
        if (fragments.equals(AssetFactoryFragmentsEnumType.DAP_SUB_APP_ASSET_FACTORY_WIZARD_CRYPTO))
            return WizardCryptoFragment.newInstance();
        if (fragments.equals(AssetFactoryFragmentsEnumType.DAP_SUB_APP_ASSET_FACTORY_WIZARD_VERIFY))
            return WizardVerifyFragment.newInstance();
        throw createFragmentNotFoundException(fragments);

    }

    @Override
    public AssetFactoryFragmentsEnumType getFermatFragmentEnumType(String key) {
        return AssetFactoryFragmentsEnumType.getValue(key);
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
