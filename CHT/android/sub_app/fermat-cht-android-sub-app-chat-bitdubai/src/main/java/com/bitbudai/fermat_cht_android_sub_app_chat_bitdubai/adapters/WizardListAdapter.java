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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

import java.util.ArrayList;
import java.util.UUID;

//import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatsList;

/**
 * Contact List Adapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/01/16.
 * @version 1.0
 */

public class WizardListAdapter extends ArrayAdapter<String> {

    ArrayList<String> contactinfo = new ArrayList<String>();
    ArrayList<Bitmap> contacticon = new ArrayList<Bitmap>();
    ArrayList<UUID> contactid = new ArrayList<UUID>();
    private ErrorManager errorManager;
    ImageView imagen;
    TextView contactname;

    public WizardListAdapter(Context context, ArrayList contactinfo, ArrayList contacticon, ArrayList contactid,
                             ErrorManager errorManager) {
        super(context, R.layout.contact_list_item, contactinfo);
        this.contactinfo = contactinfo;
        this.contacticon = contacticon;
        this.contactid = contactid;
        this.errorManager = errorManager;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View item = inflater.inflate(R.layout.wizard_list_item, null, true);
        try {
            imagen = (ImageView) item.findViewById(R.id.icon);//imagen.setImageResource(contacticon.get(position));//contacticon[position]);
            imagen.setImageBitmap(getRoundedShape(contacticon.get(position), 400));//imagen.setImageBitmap(getRoundedShape(decodeFile(getContext(), contacticon.get(position)), 300));
            final CheckBox check = (CheckBox) item.findViewById(R.id.checkBoxSelectContact);

            contactname = (TextView) item.findViewById(R.id.text1);
            contactname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check.setChecked(true);
                }
            });
            contactname.setText(contactinfo.get(position));
            //contactname.setTypeface(tf, Typeface.NORMAL);

            final int pos = position;
            imagen.setOnClickListener(new View.OnClickListener() {
                // int pos = position;
                @Override
                public void onClick(View v) {
                    try {
                        CheckBox check = (CheckBox) item.findViewById(R.id.checkBoxSelectContact);
                        check.setChecked(true);

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

    public void refreshEvents(ArrayList contactinfo, ArrayList contacticon, ArrayList contactid) {
        this.contactinfo = contactinfo;
        this.contacticon = contacticon;
        this.contactid = contactid;
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