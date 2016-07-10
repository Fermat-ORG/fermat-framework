package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatUserIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Profile fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 03/03/16
 * @version 1.0
 *
 */
public class ProfileFragment extends AbstractFermatFragment {

    // Whether or not this fragment is showing in a two-pane layout
    private boolean mIsTwoPaneLayout;

    // Whether or not this is a search result view of this fragment, only used on pre-honeycomb
    // OS versions as search results are shown in-line via Action Bar search from honeycomb onward
    private boolean mIsSearchResultView = false;
    private ChatManager chatManager;
    private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings> settingsManager;
    private ChatSessionReferenceApp chatSession;
    private Toolbar toolbar;
    // Defines a tag for identifying log entries
    String TAG = "CHT_ProfileFragment";

    ArrayList<String> profilename=new ArrayList<>();
    ArrayList<Bitmap> profileicon=new ArrayList<>();
    ArrayList<String> profileid=new ArrayList<>();
    ArrayList<String> profilealias =new ArrayList<>();
    ChatUserIdentity cont;
    Typeface tf;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            chatSession=((ChatSessionReferenceApp) appSession);
            chatManager= chatSession.getModuleManager();
            //chatManager=moduleManager.getChatManager();
            errorManager=appSession.getErrorManager();
            toolbar = getToolbar();
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cht_ic_back_buttom));
        } catch (Exception e) {
            if(errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        // Check if this fragment is part of a two-pane set up or a single pane by reading a
        // boolean from the application resource directories. This lets allows us to easily specify
        // which screen sizes should use a two-pane layout by setting this boolean in the
        // corresponding resource size-qualified directory.
       // mIsTwoPaneLayout = getResources().getBoolean(R.bool.has_two_panes);

        // Let this fragment contribute menu items
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/HelveticaNeue Medium.ttf");
        View layout = inflater.inflate(R.layout.profile_detail_fragment, container, false);

        try {
            ChatUserIdentity con= chatSession.getSelectedProfile();
            profilename.add(con.getAlias());
            profileid.add(con.getPublicKey());
            profilealias.add(con.getAlias());
            ByteArrayInputStream bytes = new ByteArrayInputStream(con.getImage());
            BitmapDrawable bmd = new BitmapDrawable(bytes);
            profileicon.add(bmd.getBitmap());

            com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ProfileAdapter adapter=new com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ProfileAdapter(getActivity(), profilename,  profilealias, profileid, "detail", errorManager);
            FermatTextView name =(FermatTextView)layout.findViewById(R.id.contact_name);
            name.setText(profilealias.get(0));
            //name.setTypeface(tf, Typeface.NORMAL);
            FermatTextView id =(FermatTextView)layout.findViewById(R.id.uuid);
            id.setText(profileid.get(0).toString());
            //id.setTypeface(tf, Typeface.NORMAL);

            // create bitmap from resource
            //Bitmap bm = BitmapFactory.decodeResource(getResources(), contacticon.get(0));

            // set circle bitmap
            ImageView mImage = (ImageView) layout.findViewById(R.id.contact_image);
            mImage.setImageBitmap(getCircleBitmap(profileicon.get(0)));

            LinearLayout detalles = (LinearLayout)layout.findViewById(R.id.contact_details_layout);

            final int adapterCount = adapter.getCount();

            for (int i = 0; i < adapterCount; i++) {
                View item = adapter.getView(i, null, null);
                detalles.addView(item);
            }
        }catch (Exception e){
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.CHT_CHAT_OPEN_PROFILELIST, appSession.getAppPublicKey());
            }
        });

        // Inflate the list fragment layout
        return layout;//return inflater.inflate(R.layout.contact_list_fragment, container, false);
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        //final Paint paintBorder = new Paint();
       // paintBorder.setColor(Color.GREEN);
        //paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);
        //BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(output, canvas.getWidth(), canvas.getHeight(), false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //paint.setShader(shader);
        paint.setAntiAlias(true);
        //paintBorder.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);
        //int circleCenter = bitmap.getWidth() / 2;
        //int borderWidth = 2;
        //canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter + borderWidth - 4.0f, paintBorder);
        //canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter - 4.0f, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //canvas.drawBitmap(bitmap, circleCenter + borderWidth, circleCenter + borderWidth, paint);
        bitmap.recycle();
        return output;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
