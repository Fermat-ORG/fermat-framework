package com.bitdubai.sub_app.music_player.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_art_api.layer.sub_app_module.music_player.MusicPlayerModuleManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.music_player.MusicPlayerPreferenceSettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongListException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.WalletSong;
import com.bitdubai.reference_wallet.fan_wallet.util.ManageRecyclerviewClickEvent;
import com.bitdubai.sub_app.music_player.R;
import com.bitdubai.sub_app.music_player.common.adapters.MusicPlayerAdapter;
import com.bitdubai.sub_app.music_player.common.models.MusicPlayerItems;
import com.bitdubai.sub_app.music_player.session.MusicPlayerSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Miguel Payarez on 13/04/16.
 */
public class MusicPlayerMainActivity extends AbstractFermatFragment {

    //FermatManager
    private MusicPlayerSession musicPlayerSession;
    private MusicPlayerModuleManager musicPlayermoduleManager;
    private MusicPlayerPreferenceSettings musicPlayerSettings;
    private ErrorManager errorManager;


    ImageButton bplay;
    ImageButton bbb;
    ImageButton bff;
    SeekBar pb;
    TextView tiempo;
    TextView song;


    RecyclerView recyclerView;
    private MusicPlayerAdapter adapter;
    private RecyclerView.LayoutManager lManager;
    List<MusicPlayerItems> items=new ArrayList();

    MediaPlayer mp = new MediaPlayer();
    private final String TAG="art_mplayer";
    ThreadSong songPlayerThread;
    boolean pause=false;
    int songposition=0;

    Map<String,Integer> rela=new HashMap<String,Integer>();

    boolean firstTime=true;

    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {
            musicPlayerSession = ((MusicPlayerSession) appSession);
            musicPlayermoduleManager = musicPlayerSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            System.out.println("HERE START MUSIC PLAYER");

            if(musicPlayerSession.getThreadsong()!=null){
                System.out.println("ART_ IT IS PLAYING");
                songPlayerThread=musicPlayerSession.getThreadsong();
                mp=musicPlayerSession.getMusicPlayer();

                bplay = musicPlayerSession.getBplay();
                bbb = musicPlayerSession.getBbb();
                bff = musicPlayerSession.getBff();
                pb = musicPlayerSession.getPb();
                tiempo = musicPlayerSession.getTiempo();
                recyclerView = musicPlayerSession.getRecyclerView();
                song = musicPlayerSession.getSong();

                adapter=musicPlayerSession.getAdapter();

                view=musicPlayerSession.getView();

                firstTime=false;

                System.out.println("ART_ I CAN LISTEN");
            }



            try {
                musicPlayerSettings = musicPlayermoduleManager.loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                musicPlayerSettings = null;
            }

            if (musicPlayerSettings == null) {
                musicPlayerSettings = new MusicPlayerPreferenceSettings();
                musicPlayerSettings.setIsPresentationHelpEnabled(true);
                if(musicPlayermoduleManager!=null){
                    musicPlayermoduleManager.persistSettings(appSession.getAppPublicKey(), musicPlayerSettings);
                }

            }


        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(
                        SubApps.ART_MUSIC_PLAYER,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        e);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        musicPlayerSession.setMusicThread(songPlayerThread);
        musicPlayerSession.setMusicPlayer(mp);

        musicPlayerSession.setBplay(bplay);
        musicPlayerSession.setBbb(bbb);
        musicPlayerSession.setBff(bff);
        musicPlayerSession.setPb(pb);
        musicPlayerSession.setRecyclerView(recyclerView);
        musicPlayerSession.setTiempo(tiempo);
        musicPlayerSession.setSong(song);

        musicPlayerSession.setAdapter(adapter);

        musicPlayerSession.setView(view);

    }

    public static MusicPlayerMainActivity newInstance() {
        return new MusicPlayerMainActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        if(firstTime) {
            view=inflater.inflate(R.layout.art_music_player_activity,container,false);
      //      getActivity().getWindow().setBackgroundDrawableResource(R.drawable.musicplayer_background_viewpager);
            bplay = (ImageButton) view.findViewById(R.id.play);
            bbb = (ImageButton) view.findViewById(R.id.back);
            bff = (ImageButton) view.findViewById(R.id.forward);
            pb = (SeekBar) view.findViewById(R.id.progressBar);
            tiempo = (TextView) view.findViewById((R.id.tiempo));
            recyclerView = (RecyclerView) view.findViewById(R.id.rv);
            song = (TextView) view.findViewById(R.id.songname);


            /*final TextView titlebar=((TextView)getToolbar().getRootView().findViewById(R.id.txt_title));


            ViewTreeObserver observer = recyclerView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        recyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    DisplayMetrics metrics = view.getContext().getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;

                    //         System.out.println("metrics:" + width);
                    //         System.out.println("viewwithd:" + view.getWidth());
                    //         System.out.println("titlebar.getMaxWidth():" + titlebar.getMaxWidth());
                    titlebar.setPadding(((width / 2) - titlebar.getWidth()/2)-10, 0, 0, 0);

                }
            });*/



            pb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (mp.isPlaying() && fromUser) {
                        mp.seekTo(progress * 1000);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });


            recyclerView.setHasFixedSize(true);
            lManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(lManager);
            adapter = new MusicPlayerAdapter(items);
            recyclerView.setAdapter(adapter);

            init();

            recyclerView.addOnItemTouchListener(
                    new ManageRecyclerviewClickEvent(view.getContext(), new ManageRecyclerviewClickEvent.OnItemClickListener() {
                        @Override

                        public void onItemClick(View view, int position) {
                            songposition = position;
                            clickplay(position);
                        }
                    })
            );

            if(items.isEmpty()){
                recyclerView.setBackgroundResource(R.drawable.nomusic);
                view.findViewById(R.id.contents).setBackgroundResource(R.drawable.musicplayer_background_viewpager);
            }else{
                recyclerView.setBackgroundResource(R.drawable.musicplayer_background_viewpager);
                view.findViewById(R.id.contents).setBackgroundResource(R.drawable.musicplayer_background_viewpager);
            }


            bplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    play();

                }
            });

            bff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    nextsong();


                }
            });

            bbb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    backsong();

                }
            });


        }else{

            init();


        }


        return view;
    }


    void loadmysong(){
        int i=0;
        List<WalletSong> mysong=new ArrayList<>();
        List<MusicPlayerItems> songview=new ArrayList<>();
        List<MusicPlayerItems> newsongview=new ArrayList<>();
        try {
            mysong=musicPlayermoduleManager.getAvailableSongs();
            if(mysong.size()<1){
                Toast.makeText(view.getContext(),"No song, dowload with the FanWallet",Toast.LENGTH_LONG).show();
            }else{
                for(WalletSong walletSong:mysong){
                    songview.add(new MusicPlayerItems(walletSong.getComposers(),
                            walletSong.getName(),
                            BitmapFactory.decodeResource(view.getContext().getResources(),
                                    R.drawable.adam),
                            walletSong.getSongBytes(),
                            walletSong.getSongId()));
                    i=i+1;
                }
                adapter.setFilter(songview);
                for (int x=0;x<mysong.size();x++){
                    newsongview.add(new MusicPlayerItems(mysong.get(x).getComposers(),
                            mysong.get(x).getName(),
                            downloadBitmapAlbumArt(x),
                            mysong.get(x).getSongBytes(),
                            mysong.get(x).getSongId()));
                }
                adapter.setFilter(newsongview);
            }
        } catch (CantGetSongListException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.ART_MUSIC_PLAYER, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

        }
    }


    Bitmap downloadBitmapAlbumArt(int position){

        try{
        File tempMp3 = File.createTempFile("tempfermatmusic", "mp3", view.getContext().getCacheDir());
        tempMp3.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(tempMp3);
        fos.write(musicPlayermoduleManager.getSongWithBytes(items.get(position).getSong_id()).getSongBytes());
        fos.close();


        FileInputStream fis = new FileInputStream(tempMp3);


        final MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();


        metaRetriever.setDataSource(fis.getFD());


            //    final AssetFileDescriptor afd=getResources().openRawResourceFd(R.raw.calido_y_frio);
            //    metaRetriever.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());

            //Other Scenarios
            //    final String uriPath="android.resource://"+getPackageName()+"/raw/t";
            //   final Uri uri=Uri.parse(uriPath);
            //   mediaMetadataRetriever.setDataSource(getApplication(),uri);

            //   final AssetFileDescriptor afd=getAssets().openFd("t.mp4");
            //   mediaMetadataRetriever.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());

      //      System.out.println("TKY_URL_SONG:"+url);
            final byte[] art = metaRetriever.getEmbeddedPicture();
            return BitmapFactory.decodeByteArray(art, 0, art.length);
        } catch (Exception e) {
            System.out.println("Couldn't create album art: " + e.getMessage());
            return BitmapFactory.decodeResource(getResources(), R.drawable.no_found_art);
        }

    }



    private void clickplay(int position) {


        try {


            if(items.size()>0) {
                File tempMp3 = File.createTempFile("tempfermatmusic", "mp3", view.getContext().getCacheDir());
                tempMp3.deleteOnExit();
                FileOutputStream fos = new FileOutputStream(tempMp3);
                fos.write(musicPlayermoduleManager.getSongWithBytes(items.get(position).getSong_id()).getSongBytes());
                fos.close();


                if (mp.isPlaying() || pause) {
                    stop();
                    mp.reset();
                }


                FileInputStream fis = new FileInputStream(tempMp3);
                mp.setDataSource(fis.getFD());
                tempMp3.delete();

                //  Toast.makeText(view.getContext(), items.get(position).getSong_name(), Toast.LENGTH_SHORT).show();
                songposition = position;
                song.setText(items.get(position).getSong_name());


                mp.prepare();

                System.out.println("ART_MP_duration:" + mp.getDuration() / 1000);

                pb.setMax((int) (mp.getDuration() / 1000));

                songPlayerThread = new ThreadSong(false);
                songPlayerThread.execute();


            }


        }  catch (IOException e) {

        } catch (CantGetSongException e) {
            e.printStackTrace();
        }


    }


    private void play() {

        try {
            if(mp.isPlaying()){
                mp.pause();
                pause=true;
                bplay.setBackgroundResource(R.drawable.button_pause);
            }else if(pause){
                mp.start();
                pause=false;
                bplay.setBackgroundResource(R.drawable.button_play);
            }

        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Unable to play audio queue do to exception: " + e.getMessage(), e);
        } catch (IllegalStateException e) {
            Log.e(TAG, "Unable to play audio queue do to exception: " + e.getMessage(), e);
        }

    }


    private void stop(){
        try {
            mp.stop();
            pb.setProgress(0);
            tiempo.setText("0:00");
     //       songPlayerThread.cancelmusicplayer=true;
        } catch (IllegalStateException e) {
            Log.e(TAG, "Unable to play audio queue do to exception: " + e.getMessage(), e);
        }
    }


    void init(){
        if((!mp.isPlaying() || pause) && firstTime) {
            pb.setProgress(0);
            tiempo.setText("");
            song.setText("");
            bplay.setBackgroundResource(R.drawable.button_play);
        }

        loadmysong();
    }


    private void nextsong(){
        songposition=songposition+1;
        if(songposition>=items.size()){
            songposition=0;
            clickplay(songposition);
        }else{
            clickplay(songposition);
        }

    }


    private void backsong(){
        if((int)(mp.getCurrentPosition()/1000)<3) {
            songposition = songposition - 1;
            if (songposition < 0) {
                songposition = items.size()-1;
                clickplay(songposition);
            } else {
                clickplay(songposition);
            }
        }else{
            clickplay(songposition);
        }
    }


    public class ThreadSong extends AsyncTask<Void, Float, Boolean> {
        private boolean cancelmusicplayer;
        public ThreadSong(boolean cancelmusicplayer) {this.cancelmusicplayer = cancelmusicplayer;}


        @Override
        protected void onPreExecute() {
            mp.start();
        }

        @Override
        protected Boolean doInBackground(Void... withNotUse) {

            float progreso = 0.0f;
            while (true) {
                while (mp.isPlaying()) {

                    try {
                        Thread.sleep((long) (1000));
                    } catch (InterruptedException e) {
                        cancel(true); //Cancel if something go wrong

                    }

                    progreso = mp.getCurrentPosition() / 1000;

                    publishProgress(progreso);


                    if (cancelmusicplayer) {
                        cancel(true);
                    }
                }


            }


        }

        @Override
        protected void onProgressUpdate(Float... porcentajeProgreso) {
            tiempo.setText(""+crono(porcentajeProgreso[0])+"");
            Log.v(TAG, "songtime:"+porcentajeProgreso[0]+"");

            pb.setProgress( Math.round(porcentajeProgreso[0]) );

            if((mp.getCurrentPosition()/1000)>=(mp.getDuration()/1000)-1){
                System.out.println("ART_THIS IS THE END");
                nextsong();
            }

        }

        String crono(float time){
            float hour,min;
            int seg;
            String count;
            min=time/60;
            if(min>0){
                seg=(int)time-(int)(time/60)*60;
                if(seg<10){
                    count=String.valueOf((int)(min))+":0"+seg;
                }else{
                    count=String.valueOf((int)(min))+":"+seg;
                }
            }else{
                if(time<10){
                    count="0:0"+(int)time;
                }else{
                    count="0:"+(int)time;
                }

            }
            return count;
        }


/*
        protected void onPostExecute(Boolean cantidadProcesados) {

        }*/


     /*   @Override
        protected void onCancelled (Integer cantidadProcesados) {
         //   TV_mensaje.setText("DESPUÉS de CANCELAR la descarga. Se han descarcado "+cantidadProcesados+" imágenes. Hilo PRINCIPAL");
            Log.v(TAG, "DESPUÉS de CANCELAR la descarga. Se han descarcado "+cantidadProcesados+" imágenes. Hilo PRINCIPAL");

            tiempo.setTextColor(Color.RED);
        }*/

    }


}





