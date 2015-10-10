package com.bitdubai.sub_app.crypto_broker_identity.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.sub_app.crypto_broker_identity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateIdentityFragment extends FermatFragment implements View.OnClickListener{

    private Button createButton;
    private EditText brokerNameTextView;
    private ImageView brokerImage;

    public CreateIdentityFragment() {
        // Required empty public constructor
    }

    public static CreateIdentityFragment newInstance() {
        return new CreateIdentityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootLayout = inflater.inflate(R.layout.fragment_create_identity, container, false);
        initViews(rootLayout);


        return rootLayout;
    }

    private void initViews(View layout) {
        createButton = (Button) layout.findViewById(R.id.create_button);
        brokerNameTextView = (EditText) layout.findViewById(R.id.broker_name);
        brokerImage = (ImageView) layout.findViewById(R.id.broker_image);

        brokerImage.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.create_button){
            Toast.makeText(getActivity(), "Click en Create Button", Toast.LENGTH_SHORT).show();
        }
    }
}
