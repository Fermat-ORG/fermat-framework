package com.bitdubai.sub_app.music_player.fragmentFactory;


import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.music_player.fragments.MusicPlayerMainActivity;
import com.bitdubai.sub_app.music_player.session.MusicPlayerSessionReferenceApp;

/**
 * Created by Miguel Payarez on 08/04/16.
 */
public class MusicPlayerFragmentFactory extends FermatFragmentFactory<MusicPlayerSessionReferenceApp,SubAppResourcesProviderManager,MusicPlayerEnumType> {
    @Override
    protected AbstractFermatFragment getFermatFragment(MusicPlayerEnumType fragments) throws FragmentNotFoundException {
        if (fragments == null) {
            throw createFragmentNotFoundException(null);
        }if (fragments.equals(MusicPlayerEnumType.ART_MUSIC_PLAYER_MAIN_ACTIVITY))
        {
            return MusicPlayerMainActivity.newInstance();
        }



        throw createFragmentNotFoundException(fragments);
    }

    @Override
    public MusicPlayerEnumType getFermatFragmentEnumType(String key) {
        return MusicPlayerEnumType.getValues(key);
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
