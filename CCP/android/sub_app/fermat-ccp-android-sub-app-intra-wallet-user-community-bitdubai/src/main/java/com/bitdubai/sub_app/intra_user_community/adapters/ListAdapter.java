package com.bitdubai.sub_app.intra_user_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.sub_app.intra_user_community.R;

import java.util.List;

/**
 * Created by Matias Furszyfer
 */

public class ListAdapter extends ArrayAdapter<CheckBoxListItem> {

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, List<CheckBoxListItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.itemlistrow, null);
        }

        CheckBoxListItem checkBoxListItem = getItem(position);

        if (checkBoxListItem != null) {

            ImageView imageView_Item =(ImageView) v.findViewById(R.id.imageView_Item);
            imageView_Item.setImageDrawable(checkBoxListItem.getIcon());
            imageView_Item.setImageBitmap(getRoundedShape(BitmapFactory.decodeByteArray(checkBoxListItem.getIntraUserIdentity().getProfileImage(), 0, checkBoxListItem.getIntraUserIdentity().getProfileImage().length)));

            FermatTextView textView_identity = (FermatTextView) v.findViewById(R.id.textView_identity);
            textView_identity.setText(checkBoxListItem.getIntraUserIdentity().getAlias());
        }

        return v;
    }

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 50;
        int targetHeight = 50;
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
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }

}