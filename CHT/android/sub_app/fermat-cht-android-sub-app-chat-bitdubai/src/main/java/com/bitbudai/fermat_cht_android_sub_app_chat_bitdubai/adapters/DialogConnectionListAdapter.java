package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ContactList;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Connection List Adapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/01/16.
 * @version 1.0
 *
 */

public class DialogConnectionListAdapter extends ArrayAdapter<String> {//public class ChatListAdapter extends FermatAdapter<ChatsList, ChatHolder> {//ChatFactory


    List<ContactList> contactsList = new ArrayList<>();
    ArrayList<String> contactinfo=new ArrayList<String>();
    ArrayList<Bitmap> contacticon=new ArrayList<Bitmap>();
    ArrayList<UUID> contactid=new ArrayList<UUID>();

    Typeface tf;
    int Viewer = 0;
    View item;
    private ChatManager chatManager;
    private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatSessionReferenceApp chatSession;

    public DialogConnectionListAdapter(Context context, ArrayList contactinfo, ArrayList contacticon, ArrayList contactid, ErrorManager errorManager) {
        super(context, R.layout.cht_dialog_connections_list_item, contactinfo);
        //tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue Medium.ttf");
        this.contactinfo = contactinfo;
        this.contacticon = contacticon;
        this.contactid = contactid;
        this.errorManager = errorManager;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.cht_dialog_connections_list_item, null, true);
            try {
                ImageView imagen = (ImageView) item.findViewById(R.id.icon);
                imagen.setImageBitmap(getRoundedShape(contacticon.get(position), 300));//imagen.setImageBitmap(getRoundedShape(decodeFile(getContext(), contacticon.get(position)), 300));

                TextView contactname = (TextView) item.findViewById(R.id.text1);
                contactname.setText(contactinfo.get(position));
                //contactname.setTypeface(tf, Typeface.NORMAL);

            } catch (Exception e) {
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
            return item;
    }

    public void refreshEvents(ArrayList contactinfo, ArrayList contacticon, ArrayList contactid) {
        this.contactinfo=contactinfo;
        this.contacticon=contacticon;
        this.contacticon=contacticon;
        notifyDataSetChanged();
    }

    public static Bitmap decodeFile(Context context,int resId) {
// decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, o);
// Find the correct scale value. It should be the power of 2.
        final int REQUIRED_SIZE = 300;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true)
        {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale++;
        }
// decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeResource(context.getResources(), resId, o2);
    }

    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage,int width) {
        // TODO Auto-generated method stub
        int targetWidth = width;
        int targetHeight = width;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

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