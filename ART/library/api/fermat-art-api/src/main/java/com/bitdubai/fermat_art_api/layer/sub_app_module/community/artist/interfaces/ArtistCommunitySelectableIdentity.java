package com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantSelectIdentityException;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/6/16.
 */
public interface ArtistCommunitySelectableIdentity extends ActiveActorIdentityInformation {


    /**
     * The method <code>select</code> you can select an identity to work with.
     *
     * @return the profile image of the Artist
     */
    void select() throws CantSelectIdentityException;
}
