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
    public SongItems(Bitmap imagen, String song_name, String artist_name, String status,UUID song_id, int progress, boolean progressbarvissible){

        this.imagen = imagen;
        this.song_name = song_name;
        this.artist_name=artist_name;
        this.status=status;
        this.song_id=song_id;
        this.progress=progress;
        this.progressbarvissible=progressbarvissible;
    }

    // getters & setters

    public Bitmap getImagen(){return imagen;}

    public String getSong_name(){return song_name;}

    public String getArtist_name(){return artist_name;}

    public String getStatus(){return status;}

    public int getProgress() {return progress;}

    public UUID getSong_id() {return song_id;}

    public boolean isProgressbarvissible() { return progressbarvissible;  }

    public void setProgress(int newprogress){progress=newprogress;}

    public void setStatus(String newstatus){status=newstatus;}

    public void setProgressbarvissible(boolean newprogressbarvissible) {progressbarvissible = newprogressbarvissible;}

    public void setSong_id(UUID newsongid){song_id=newsongid;}

}
