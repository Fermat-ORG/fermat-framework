package com.bitdubai.reference_niche_wallet.age.kids.boys.fragments;

/**
 * Created by ciencias on 25.11.14.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_api.layer.ccp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.ccp_network_service.wallet_resources.WalletResourcesManager;
import com.bitdubai.fermat_api.layer.ccp_engine.sub_app_runtime.enums.Wallets;
import com.bitdubai.fermat_dmp.wallet_runtime.R;
import com.bitdubai.reference_niche_wallet.age.kids.boys.Platform;


public class ProfileCardFrontFragment extends Fragment {

    /**
     * ProfileCardFrontFragment member variables.
     */

    private static final String ARG_POSITION = "position";
    private int position;
    private static Platform platform;
    private  static WalletResourcesManager walletResourceManger;
    /**
     * Constructor.
     */

    public static ProfileCardFrontFragment newInstance(int position) {

        walletResourceManger = platform.getWalletResourcesManager();
        walletResourceManger.setwalletType(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI);

        ProfileCardFrontFragment f = new ProfileCardFrontFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    /**
     * Fragment Class implementation.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        TextView name;
        byte[] imageResource;
        Bitmap bitmap;

        view = inflater.inflate(R.layout.wallets_kids_fragment_profile_card_front, container, false); //Contains empty RelativeLayout
        ImageView photo = (ImageView) view.findViewById(R.id.profile_Image);
        name = (TextView) view.findViewById(R.id.user_name);
        name.setText("Johnny");

        try{
            imageResource = walletResourceManger.getImageResource("kid_15.jpg");
            bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
            photo.setImageBitmap(bitmap);
        }
        catch (CantGetResourcesException e) {
                System.err.println("CantGetResourcesException: " + e.getMessage());

            }


        return view;
    }

    public  static void setPlatform (Platform platformWallet){
        platform = platformWallet;
    }
}