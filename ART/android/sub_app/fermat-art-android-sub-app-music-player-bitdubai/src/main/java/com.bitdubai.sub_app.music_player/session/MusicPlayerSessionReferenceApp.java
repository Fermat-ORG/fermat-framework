package com.bitdubai.sub_app.music_player.session;


import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_art_api.layer.sub_app_module.music_player.MusicPlayerModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.music_player.common.adapters.MusicPlayerAdapter;
import com.bitdubai.sub_app.music_player.fragments.MusicPlayerMainActivity;

/**
 * Created by Miguel Payarez on 14/03/16.
 */
public class MusicPlayerSessionReferenceApp extends AbstractReferenceAppFermatSession<InstalledSubApp,MusicPlayerModuleManager,SubAppResourcesProviderManager> {
    MusicPlayerMainActivity.ThreadSong threadsong=null;
    MediaPlayer mp=null;

    MusicPlayerAdapter adapter;

    ImageButton bplay=null;
    ImageButton bbb=null;
    ImageButton bff=null;
    SeekBar pb=null;
    RecyclerView recyclerView=null;
    TextView song=null;
    TextView tiempo;

    boolean pause;

    View view=null;


    public void setMusicThread(MusicPlayerMainActivity.ThreadSong threadsong){    this.threadsong=threadsong;}
    public MusicPlayerMainActivity.ThreadSong getThreadsong(){return threadsong; }

    public void setMusicPlayer(MediaPlayer mp){    this.mp=mp;}
    public MediaPlayer getMusicPlayer(){return mp; }

    public void setBplay(ImageButton bplay){    this.bplay=bplay;}
    public ImageButton getBplay(){return bplay; }

    public void setBbb(ImageButton bbb){    this.bbb=bbb;}
    public ImageButton getBbb(){return bbb; }

    public void setBff(ImageButton bff){    this.bff=bff;}
    public ImageButton getBff(){return bff; }

    public void setPb(SeekBar pb){    this.pb=pb;}
    public SeekBar getPb(){return pb; }

    public void setRecyclerView(RecyclerView recyclerView){    this.recyclerView=recyclerView;}
    public RecyclerView getRecyclerView(){return recyclerView; }

    public void setSong(TextView song){    this.song=song;}
    public TextView getSong(){return song; }

    public void setTiempo(TextView tiempo){    this.tiempo=tiempo;}
    public TextView getTiempo(){return tiempo; }

    public void setView(View view){this.view=view;}
    public View getView(){return view;}

    public void setAdapter(MusicPlayerAdapter adapter){this.adapter=adapter;}
    public MusicPlayerAdapter getAdapter(){return adapter;}

    public void setPause(boolean pause){this.pause=pause;}
    public boolean getPause(){return pause;}

}
