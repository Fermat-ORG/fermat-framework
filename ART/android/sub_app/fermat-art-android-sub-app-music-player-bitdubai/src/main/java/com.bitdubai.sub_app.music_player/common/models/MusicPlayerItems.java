package com.bitdubai.sub_app.music_player.common.models;

import java.util.UUID;

/**
 * Created by Miguel Payarez on 16/03/16.
 */
public class MusicPlayerItems {

    private String title;
    private int imagen;
    private byte[] song;
    private UUID song_id;

    public MusicPlayerItems(String title,int imagen,byte[] song,UUID song_id){

        this.title = title;
        this.imagen = imagen;
        this.song=song;
        this.song_id=song_id;
    }

    // getters & setters
    public String getTitle(){return title;}

    public int getImagen(){return imagen;}

    public byte[] getSong(){return song;}

    public UUID getSong_id() {return song_id;}

}
