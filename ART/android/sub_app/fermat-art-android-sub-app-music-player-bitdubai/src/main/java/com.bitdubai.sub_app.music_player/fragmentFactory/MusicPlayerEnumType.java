package com.bitdubai.sub_app.music_player.fragmentFactory;


import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Miguel Payarez on 08/04/16.
 */
public enum MusicPlayerEnumType implements FermatFragmentsEnumType<MusicPlayerEnumType> {

    ART_MUSIC_PLAYER_MAIN_ACTIVITY("ARTMPMA");


    private String key;

    MusicPlayerEnumType(String key){
        this.key=key;
    }

    public static MusicPlayerEnumType getValues(String name){
        for(MusicPlayerEnumType fragments: MusicPlayerEnumType.values()){
            if(fragments.getKey().equals(name)){
                return fragments;
            }

        }

        return null;
    }

    @Override
    public String getKey() {
        return key;
    }
}
