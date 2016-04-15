package com.bitdubai.sub_app.music_player.common.models;

/**
 * Created by Miguel Payarez on 16/03/16.
 */
public class MusicPlayerItems {

    private String title;
    private int imagen;
    private int song;

    public MusicPlayerItems(String title,int imagen,int song){

        this.title = title;
        this.imagen = imagen;
        this.song=song;
    }

    // getters & setters
    public String getTitle(){return title;}

    public int getImagen(){return imagen;}

    public int getSong(){return song;}


}
