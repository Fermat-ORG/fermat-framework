package com.bitdubai.sub_app.chat_community.common.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.bitdubai.sub_app.chat_community.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * ChatContactListItem
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
public class ChatContactListItem implements Item {

    private String name;
    private String profilePhrase;
    private byte[] profileImage;

    //esto puede ser un enum con los distintos estados de la conexion
    private String connectionStatus;


    public ChatContactListItem(String name, String profilePhrase, byte[] profileImage, String connectionStatus) {
        this.name = name;
        this.profilePhrase = profilePhrase;
        this.profileImage = profileImage;
        this.connectionStatus = connectionStatus;
    }

    public static ArrayList<ChatContactListItem> getTestData(Resources resources) {
        ArrayList<ChatContactListItem> lst = new ArrayList<ChatContactListItem>();
        Drawable d = resources.getDrawable(R.drawable.cht_comm_btn_conect_background);//profile_image); // the drawable (Captain Obvious, to the rescue!!!)
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] bitmapdata = stream.toByteArray();
        ChatContactListItem chatContactListItem = new ChatContactListItem("Matias Furszyfer", "Keep moving forward", bitmapdata, "connect");
        lst.add(chatContactListItem);

        d = resources.getDrawable(R.drawable.cht_comm_btn_conect_background);//deniz_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)

        if (d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            chatContactListItem = new ChatContactListItem("Deniz Perez", "Love playing with bitcoin", bitmapdata, "disconnect");
            lst.add(chatContactListItem);
        }
        d = resources.getDrawable(R.drawable.cht_comm_btn_conect_background);//kimberly_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)

        if (d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            chatContactListItem = new ChatContactListItem("Kimberly Reumw", "Moving around the world", bitmapdata, "connect");
            lst.add(chatContactListItem);
        }
        d = resources.getDrawable(R.drawable.cht_comm_btn_conect_background);//ginny_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)

        if (d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            chatContactListItem = new ChatContactListItem("Giany Reumw", "Making people laugh", bitmapdata, "connect");
            lst.add(chatContactListItem);
        }
        d = resources.getDrawable(R.drawable.cht_comm_btn_conect_background);//brant_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)
        if (d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            chatContactListItem = new ChatContactListItem("Brant Wess", "Nothing to say", bitmapdata, "disconnect");
            lst.add(chatContactListItem);
        }
        d = resources.getDrawable(R.drawable.cht_comm_btn_conect_background);//taylor_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)

        if (d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            chatContactListItem = new ChatContactListItem("Taylor eustar", "Like you", bitmapdata, "connect");
            lst.add(chatContactListItem);
        }
        return lst;

    }

    public static ArrayList<ChatContactListItem> getTestDataExample(Resources resources) {
        ArrayList<ChatContactListItem> lst = new ArrayList<ChatContactListItem>();
        Drawable d = resources.getDrawable(R.drawable.cht_comm_btn_conect_background);//profile_image); // the drawable (Captain Obvious, to the rescue!!!)
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] bitmapdata = stream.toByteArray();
        ChatContactListItem intraUserConnectionListItem = new ChatContactListItem("Matias Furszyfer", "Keep moving forward", bitmapdata, "connect");
        lst.add(intraUserConnectionListItem);

        d = resources.getDrawable(R.drawable.cht_comm_btn_conect_background);//kimberly_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)

        if (d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            intraUserConnectionListItem = new ChatContactListItem("Kimberly Reumw", "Moving around the world", bitmapdata, "connect");
            lst.add(intraUserConnectionListItem);
        }
        d = resources.getDrawable(R.drawable.cht_comm_btn_conect_background);//lucia_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)

        if (d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            intraUserConnectionListItem = new ChatContactListItem("Lucia Perez", "Want to play with you", bitmapdata, "disconnect");
            lst.add(intraUserConnectionListItem);
        }

        d = resources.getDrawable(R.drawable.cht_comm_btn_conect_background);//guillermo_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)

        if (d != null) {
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmapdata = stream.toByteArray();
            intraUserConnectionListItem = new ChatContactListItem("joe Reumw", "Moving around the world", bitmapdata, "connect");
            lst.add(intraUserConnectionListItem);
        }
        return lst;

    }

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
