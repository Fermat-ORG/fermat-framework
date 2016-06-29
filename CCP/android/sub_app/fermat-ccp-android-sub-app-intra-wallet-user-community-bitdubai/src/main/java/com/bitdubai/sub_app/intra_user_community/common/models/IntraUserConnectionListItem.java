package com.bitdubai.sub_app.intra_user_community.common.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.bitdubai.sub_app.intra_user_community.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Matias Furszyfer on 2015.08.31..
 * modified by Jose Mnuel De Sousa Dos Santos 2016.01.17
 */
public class IntraUserConnectionListItem implements Item {

    private String name;
    private String profilePhrase;
    private byte[] profileImage;

    //esto puede ser un enum con los distintos estados de la conexion
    private String connectionStatus;


    public IntraUserConnectionListItem(String name, String profilePhrase, byte[] profileImage, String connectionStatus) {
        this.name = name;
        this.profilePhrase = profilePhrase;
        this.profileImage = profileImage;
        this.connectionStatus = connectionStatus;
    }

   /* public static ArrayList<IntraUserConnectionListItem> getTestData(Resources resources) {
        ArrayList<IntraUserConnectionListItem> lst = new ArrayList<IntraUserConnectionListItem>();
        FermatDrawable d = resources.getDrawable(R.drawable.profile_image); // the drawable (Captain Obvious, to the rescue!!!)
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] bitmapdata = stream.toByteArray();
        IntraUserConnectionListItem intraUserConnectionListItem = new IntraUserConnectionListItem("Matias Furszyfer", "Keep moving forward", bitmapdata, "connect");
        lst.add(intraUserConnectionListItem);

        d = resources.getDrawable(R.drawable.deniz_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)

        if (d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            intraUserConnectionListItem = new IntraUserConnectionListItem("Deniz Perez", "Love playing with bitcoin", bitmapdata, "disconnect");
            lst.add(intraUserConnectionListItem);
        }
        d = resources.getDrawable(R.drawable.kimberly_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)

        if (d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            intraUserConnectionListItem = new IntraUserConnectionListItem("Kimberly Reumw", "Moving around the world", bitmapdata, "connect");
            lst.add(intraUserConnectionListItem);
        }
        d = resources.getDrawable(R.drawable.ginny_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)

        if (d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            intraUserConnectionListItem = new IntraUserConnectionListItem("Giany Reumw", "Making people laugh", bitmapdata, "connect");
            lst.add(intraUserConnectionListItem);
        }
        d = resources.getDrawable(R.drawable.brant_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)
        if (d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            intraUserConnectionListItem = new IntraUserConnectionListItem("Brant Wess", "Nothing to say", bitmapdata, "disconnect");
            lst.add(intraUserConnectionListItem);
        }
        d = resources.getDrawable(R.drawable.taylor_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)

        if (d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            intraUserConnectionListItem = new IntraUserConnectionListItem("Taylor eustar", "Like you", bitmapdata, "connect");
            lst.add(intraUserConnectionListItem);
        }
        return lst;

    }*/

   /* public static ArrayList<IntraUserConnectionListItem> getTestDataExample(Resources resources) {
        ArrayList<IntraUserConnectionListItem> lst = new ArrayList<IntraUserConnectionListItem>();
        FermatDrawable d = resources.getDrawable(R.drawable.profile_image); // the drawable (Captain Obvious, to the rescue!!!)
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] bitmapdata = stream.toByteArray();
        IntraUserConnectionListItem intraUserConnectionListItem = new IntraUserConnectionListItem("Matias Furszyfer", "Keep moving forward", bitmapdata, "connect");
        lst.add(intraUserConnectionListItem);

        d = resources.getDrawable(R.drawable.kimberly_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)

        if(d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            intraUserConnectionListItem = new IntraUserConnectionListItem("Kimberly Reumw", "Moving around the world", bitmapdata, "connect");
            lst.add(intraUserConnectionListItem);
        }
        d = resources.getDrawable(R.drawable.lucia_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)

        if(d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            intraUserConnectionListItem = new IntraUserConnectionListItem("Lucia Perez", "Want to play with you", bitmapdata, "disconnect");
            lst.add(intraUserConnectionListItem);
        }

        d = resources.getDrawable(R.drawable.guillermo_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)

        if(d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            intraUserConnectionListItem = new IntraUserConnectionListItem("joe Reumw", "Moving around the world", bitmapdata, "connect");
            lst.add(intraUserConnectionListItem);
        }
        return lst;


    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePhrase() {
        return profilePhrase;
    }

    public void setProfilePhrase(String profilePhrase) {
        this.profilePhrase = profilePhrase;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    @Override
    public boolean isSection() {
        return false;
    }

    @Override
    public boolean isConnection() {
        return true;
    }
}
