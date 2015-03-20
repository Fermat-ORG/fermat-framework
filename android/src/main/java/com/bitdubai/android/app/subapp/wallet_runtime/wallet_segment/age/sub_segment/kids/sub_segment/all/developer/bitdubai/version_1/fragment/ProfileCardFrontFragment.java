package com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment;

/**
 * Created by ciencias on 25.11.14.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitdubai.fermat_api.layer._10_network_service.wallet_resources.WalletResourcesManager;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_core.CorePlatformContext;
import com.bitdubai.fermat_core.Platform;
import com.bitdubai.smartwallet.R;
import com.bitdubai.android.app.common.version_1.classes.MyApplication;

public class ProfileCardFrontFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private  static WalletResourcesManager walletResourceManger;
    private int position;

    public static ProfileCardFrontFragment newInstance(int position) {
       // Platform platform = MyApplication.getPlatform();
       // CorePlatformContext platformContext = platform.getCorePlatformContext();
       // walletResourceManger = (WalletResourcesManager)platformContext.getPlugin(Plugins.WALLET_RESOURCES_NETWORK_SERVICE);
       // walletResourceManger.setwalletType(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI);

        ProfileCardFrontFragment f = new ProfileCardFrontFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        TextView name;
        String layoutContent = "";
        String strError = "";
     /*   try {
//inflate xml from string - error it is not currently possible to use LayoutInflater with an XmlPullParser over a plain XML file at runtime.
            walletResourceManger.setImageName("wallets_kids_fragment_contacts_filter.txt");
            layoutContent = walletResourceManger.getLayoutResource();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader("<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\" android:layout_width=\"match_parent\"  android:layout_height=\"match_parent\" android:orientation=\"vertical\"></LinearLayout>"));


            try{
                View view2 = inflater.inflate(xpp, container, false);
            }catch(Exception e){
                strError = e.getMessage();
                System.err.println(strError);
            }

        } catch (CantGetResourcesException e) {
            System.err.println("CantGetResourcesException: " + e.getMessage());
        } catch (XmlPullParserException e) {
            System.err.println("CantParseXMLlayout: " + e.getMessage());

        }*/
// *** TODO: Recupero el xml con las propiedades de cada item del layout y las aplico.

        view = inflater.inflate(R.layout.wallets_kids_fragment_profile_card_front, container, false); //Contains empty RelativeLayout

        name = (TextView) view.findViewById(R.id.user_name);
        name.setTypeface(MyApplication.getDefaultTypeface());
        name.setText("Johnny");

        return view;
    }


}