package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

import java.util.ArrayList;

/**
 * Profile List Adapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 03/03/16.
 * @version 1.0
 */
public class ProfileListAdapter extends ArrayAdapter<String> {//public class ChatListAdapter extends FermatAdapter<ChatsList, ChatMessageHolder> {//ChatFactory

    //List<ContactList> contactsList = new ArrayList<>();
    ArrayList<String> profileinfo = new ArrayList<>();
    ArrayList<Bitmap> profileicon = new ArrayList<>();
    ArrayList<String> profileid = new ArrayList<>();
    private FermatSession appSession;
    private ErrorManager errorManager;
    ImageView imagen;
    TextView contactname;
    private AdapterCallback mAdapterCallback;

    public ProfileListAdapter(Context context, ArrayList profileinfo, ArrayList profileicon, ArrayList profileid,
                              ErrorManager errorManager, FermatSession appSession, AdapterCallback mAdapterCallback) {
        super(context, R.layout.profile_list_item, profileinfo);
        this.profileinfo = profileinfo;
        this.profileicon = profileicon;
        this.profileid = profileid;
        this.errorManager = errorManager;
        this.appSession = appSession;
        try {
            this.mAdapterCallback = mAdapterCallback;
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.profile_list_item, null, true);
        try {
            imagen = (ImageView) item.findViewById(R.id.icon);//imagen.setImageResource(contacticon.get(position));//contacticon[position]);
            imagen.setImageBitmap(getRoundedShape(profileicon.get(position), 400));//imagen.setImageBitmap(getRoundedShape(decodeFile(getContext(), contacticon.get(position)), 300));

            contactname = (TextView) item.findViewById(R.id.text1);
            contactname.setText(profileinfo.get(position));
            //contactname.setTypeface(tf, Typeface.NORMAL);

            final int pos = position;
            imagen.setOnClickListener(new View.OnClickListener() {
                // int pos = position;
                @Override
                public void onClick(View v) {
                    try {
                        //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
                        appSession.setData(ChatSessionReferenceApp.PROFILE_DATA, null);//chatManager.getChatUserIdentity(profileid.get(pos)));
                        mAdapterCallback.onMethodCallback();//solution to access to changeactivity. j
                        //} catch (CantGetChatUserIdentityException e) {
                        //    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    } catch (Exception e) {
                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    }
                }
            });

        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
        return item;
    }

    public static interface AdapterCallback {
        void onMethodCallback();
    }

    public void refreshEvents(ArrayList profileinfo, ArrayList profileicon, ArrayList profileid) {
        this.profileinfo = profileinfo;
        this.profileicon = profileicon;
        this.profileid = profileid;
        notifyDataSetChanged();
    }

    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage, int width) {
        // TODO Auto-generated method stub
        int targetWidth = width;
        int targetHeight = width;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);
        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }
}