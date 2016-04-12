package com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.fragments;

/**
 * Created by edicson on 23/03/16.
 */

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;
import android.view.ViewGroup;


import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.sub_app.artist_identity.R;


public class CreateArtistIndentityFragment extends AbstractFermatFragment {

    public static CreateArtistIndentityFragment newInstance() {
        return new CreateArtistIndentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.dap_user_identity_menu_main, menu);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootLayout = inflater.inflate(R.layout.fragment_art_create_issuer_identity, container, false);
      //  initViews(rootLayout);
      //  setUpIdentity();

        return rootLayout;
    }


}