package com.bitdubai.reference_wallet.fan_wallet.common.models;

import android.graphics.Bitmap;

import java.util.UUID;

/**
 * Created by Miguel Payarez on 16/03/16.
 */
public class SongItems {

    private String song_name;
    private Bitmap imagen;
    private String artist_name;
    private String status;
    private UUID song_id;
    private int progress;
    private boolean progressbarvissible;
    private boolean isItemSelected;
    private String description;
    public SongItems(Bitmap imagen, String song_name, String artist_name, String status,UUID song_id, int progress, boolean progressbarvissible,String description){

        this.imagen = imagen;
        this.song_name = song_name;
        this.artist_name=artist_name;
        this.status=status;
        this.song_id=song_id;
        this.progress=progress;
        this.progressbarvissible=progressbarvissible;
        this.description=description;
    }

    // getters & setters

    public Bitmap getImagen(){return imagen;}

    public String getSong_name(){return song_name;}

    public String getArtist_name(){return artist_name;}

    public String getStatus(){return status;}

    public int getProgress() {return progress;}

    public UUID getSong_id() {return song_id;}

    public String getDescription(){return description;}

    public boolean isProgressbarvissible() { return progressbarvissible;  }

    public boolean isItemSelected() { return isItemSelected;  }

    public void setProgress(int newprogress){progress=newprogress;}

    public void setStatus(String newstatus){status=newstatus;}

    public void setProgressbarvissible(boolean newprogressbarvissible) {progressbarvissible = newprogressbarvissible;}

    public void setItemSelected(boolean isItemSelected) {this.isItemSelected = isItemSelected;}

    public void setSong_id(UUID newsongid){song_id=newsongid;}

    public void setDescription(String description){this.description=description;}

}
