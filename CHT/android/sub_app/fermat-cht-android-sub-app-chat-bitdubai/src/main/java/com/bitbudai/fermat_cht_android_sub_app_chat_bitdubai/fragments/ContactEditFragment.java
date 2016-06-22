package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ContactAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Contact fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 05/01/16
 * @version 1.0
 *
 */
public class ContactEditFragment
        extends AbstractFermatFragment<ReferenceAppFermatSession<ChatManager>, SubAppResourcesProviderManager>{

    public List<Contact> contacts;
    // Whether or not this fragment is showing in a two-pane layout
    private boolean mIsTwoPaneLayout;

    // Whether or not this is a search result view of this fragment, only used on pre-honeycomb
    // OS versions as search results are shown in-line via Action Bar search from honeycomb onward
    private boolean mIsSearchResultView = false;
    private ChatManager chatManager;
    private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatSettings> settingsManager;
    private ReferenceAppFermatSession<ChatManager> chatSession;
    private Toolbar toolbar;
    //Defines a tag for identifying log entries
    private static final String TAG = "CHT_ContactEditFragment";
    ArrayList<String> contactname=new ArrayList<>();
    ArrayList<Bitmap> contacticon=new ArrayList<>();
    ArrayList<String> contactid=new ArrayList<>();
    ArrayList<String> contactalias =new ArrayList<>();
    Contact cont;
    EditText aliasET ;
    Button saveBtn ;

    public static ContactEditFragment newInstance() {
        return new ContactEditFragment();}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mIsTwoPaneLayout = getResources().getBoolean(R.bool.has_two_panes);

        // Let this fragment contribute menu items
        //setHasOptionsMenu(true);

        try {
            //chatSession=((ChatSessionReferenceApp) appSession);
            chatManager= appSession.getModuleManager();
            //chatManager=moduleManager.getChatManager();
            errorManager=appSession.getErrorManager();
            toolbar = getToolbar();
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cht_ic_back_buttom));
        } catch (Exception e) {
           // CommonLogger.exception(TAG + "oncreate", e.getMessage(), e);
            if(errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View layout = inflater.inflate(R.layout.contact_edit_fragment, container, false);
        try {
            //Contact cont= chatSession.getSelectedContact();
            //TODO: metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
            List<ChatActorCommunityInformation> chatActorCommunityInformations = chatManager.listAllConnectedChatActor(
                    chatManager.newInstanceChatActorCommunitySelectableIdentity((ChatIdentity) chatManager.
                            getIdentityChatUsersFromCurrentDeviceUser().get(0)), 2000, 0);
             for (ChatActorCommunityInformation cont: chatActorCommunityInformations) {
                if (cont.getPublicKey() == chatSession.getData(ChatSessionReferenceApp.CONTACT_DATA)) {
                    contactname.add(cont.getAlias());
                    contactid.add(cont.getPublicKey());
                    contactalias.add(cont.getAlias());
                    ByteArrayInputStream bytes = new ByteArrayInputStream(cont.getImage());
                    BitmapDrawable bmd = new BitmapDrawable(bytes);
                    contacticon.add(bmd.getBitmap());
                    ContactAdapter adapter = new ContactAdapter(getActivity(), contactname, contactalias, contactid, "edit", errorManager);
                    //FermatTextView name =(FermatTextView)layout.findViewById(R.id.contact_name);
                    //name.setText(contactname.get(0));
                    //FermatTextView id =(FermatTextView)layout.findViewById(R.id.uuid);
                    //id.setText(contactid.get(0).toString());

                    // create bitmap from resource
                    //Bitmap bm = BitmapFactory.decodeResource(getResources(), contacticon.get(0));

                    // set circle bitmap
                    ImageView mImage = (ImageView) layout.findViewById(R.id.contact_image);
                    mImage.setImageBitmap(getCircleBitmap(contacticon.get(0)));
                    break;
                }
            }
            aliasET =(EditText)layout.findViewById(R.id.aliasEdit);
            aliasET.setText(contactalias.get(0));
            saveBtn = (Button) layout.findViewById(R.id.saveContactButton);
            RelativeLayout contain = (RelativeLayout) layout.findViewById(R.id.containere);
        }catch (Exception e){
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.CHT_CHAT_OPEN_CHATLIST, appSession.getAppPublicKey());
            }
        });

        return layout;
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();
        return output;
    }
}
