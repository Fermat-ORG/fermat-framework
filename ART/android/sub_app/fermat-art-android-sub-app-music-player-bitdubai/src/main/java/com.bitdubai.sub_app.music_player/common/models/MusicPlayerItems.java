package com.bitdubai.sub_app.music_player.common.models;

import java.util.UUID;

/**
 * Created by Miguel Payarez on 16/03/16.
 */
public class MusicPlayerItems {

    private String song_name;
    private String artist_name;
    private int imagen;
    private byte[] song;
    private UUID song_id;

    public MusicPlayerItems(String artist_name,String song_name,int imagen,byte[] song,UUID song_id){
        this.artist_name=artist_name;
        this.song_name = song_name;
        this.imagen = imagen;
        this.song=song;
        this.song_id=song_id;
    }

    // getters & setters


    public String getArtist_name() {        return artist_name; }

    public String getSong_name(){return song_name;}


    public int getImagen(){return imagen;}

    public byte[] getSong(){return song;}

    public UUID getSong_id() {return song_id;}

}
