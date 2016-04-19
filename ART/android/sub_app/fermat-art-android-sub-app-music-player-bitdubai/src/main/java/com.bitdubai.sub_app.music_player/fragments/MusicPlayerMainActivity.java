package com.bitdubai.sub_app.music_player.fragments;


import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_art_api.layer.sub_app_module.music_player.MusicPlayerModuleManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.music_player.MusicPlayerPreferenceSettings;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
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


    Button bplay;
    Button bbb;
    Button bff;
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

    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {
            musicPlayerSession = ((MusicPlayerSession) appSession);
            musicPlayermoduleManager = musicPlayerSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            System.out.println("HERE START MUSIC PLAYER");

            try {
                musicPlayerSettings = musicPlayermoduleManager.getSettingsManager().loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                musicPlayerSettings = null;
            }

            if (musicPlayerSettings == null) {
                musicPlayerSettings = new MusicPlayerPreferenceSettings();
                musicPlayerSettings.setIsPresentationHelpEnabled(true);
                try {
                    musicPlayermoduleManager.getSettingsManager().persistSettings(appSession.getAppPublicKey(), musicPlayerSettings);
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.ART_MUSIC_PLAYER, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }


        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.ART_MUSIC_PLAYER, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }


    }


    public static MusicPlayerMainActivity newInstance() {
        return new MusicPlayerMainActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.art_music_player_activity,container,false);
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.musicplayer_background_viewpager);
        bplay = (Button) view.findViewById(R.id.play);
        bbb = (Button) view.findViewById(R.id.back);
        bff = (Button) view.findViewById(R.id.forward);
        pb=(SeekBar) view.findViewById(R.id.progressBar);
        tiempo=(TextView) view.findViewById((R.id.tiempo));
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        song=(TextView)view.findViewById(R.id.songname);



        pb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mp.isPlaying() && fromUser){
                    mp.seekTo(progress*1000);
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
                        songposition=position;
                        clickplay(position);
                    }
                })
        );


        bplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
                songPlayerThread = new ThreadSong(false);
                songPlayerThread.execute();

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

        return view;
    }


    void loadmysong(){
        List<WalletSong> mysong=new ArrayList<>();
        List<MusicPlayerItems> songview=new ArrayList<>();
        try {
            mysong=musicPlayermoduleManager.getAvailableSongs();
            if(mysong.size()<1){
                Toast.makeText(view.getContext(),"No song, dowload with the FanWallet",Toast.LENGTH_LONG).show();
            }else{
                for(WalletSong walletSong:mysong){
                    songview.add(new MusicPlayerItems(walletSong.getComposers(), walletSong.getName(),R.drawable.adam,walletSong.getSongBytes(),walletSong.getSongId()));
                }
                adapter.setFilter(songview);
            }
        } catch (CantGetSongListException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.ART_MUSIC_PLAYER, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

        }
    }





    private void clickplay(int position) {


   /*     try {
            AssetFileDescriptor afd = getResources().openRawResourceFd(items.get(position).getSong());

            Toast.makeText(view.getContext(), items.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            songposition=position;
            song.setText(items.get(position).getTitle());
            miTareaAsincrona = new ThreadSong(false);
            miTareaAsincrona.execute();
            stop();
            mp.reset();
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
            mp.prepare();
            mp.start();
            pb.setMax( (int)(mp.getDuration()/1000));

            afd.close();
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Unable to play audio queue do to exception: " + e.getMessage(), e);
        } catch (IllegalStateException e) {
            Log.e(TAG, "Unable to play audio queue do to exception: " + e.getMessage(), e);
        } catch (IOException e) {
            Log.e(TAG, "Unable to play audio queue do to exception: " + e.getMessage(), e);
        }*/

        try {


            // create temp file that will hold byte array
            File tempMp3 = File.createTempFile("i_know_now", "mp3", view.getContext().getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(musicPlayermoduleManager.getSongWithBytes(items.get(position).getSong_id()).getSongBytes());
            fos.close();

            // Tried reusing instance of media player
            // but that resulted in system crashes...
          //  MediaPlayer mediaPlayer = new MediaPlayer();

            // Tried passing path directly, but kept getting

            FileInputStream fis = new FileInputStream(tempMp3);
            mp.setDataSource(fis.getFD());


            Toast.makeText(view.getContext(), items.get(position).getSong_name(), Toast.LENGTH_SHORT).show();
            songposition=position;
            song.setText(items.get(position).getSong_name());
            songPlayerThread = new ThreadSong(false);
            songPlayerThread.execute();
 //           stop();
 //           mp.reset();

            mp.prepare();
            mp.start();


            pb.setMax( (int)(mp.getDuration()/1000));




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
            }else if(pause){
                mp.start();
                pause=false;
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
        } catch (IllegalStateException e) {
            Log.e(TAG, "Unable to play audio queue do to exception: " + e.getMessage(), e);
        }
    }


    void init(){
        pb.setProgress(0);
        tiempo.setText("");
        song.setText("");
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
                songposition = items.size();
                clickplay(songposition);
            } else {
                clickplay(songposition);
            }
        }else{
            clickplay(songposition);
        }
    }


    public class ThreadSong extends AsyncTask<Void, Float, Void> {
        private boolean cancelmusicplayer;

        public ThreadSong(boolean cancelmusicplayer) {this.cancelmusicplayer = cancelmusicplayer;}


        @Override
        protected void onPreExecute() {}

        @Override
        protected Void doInBackground(Void... withNotUse) {

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
        }

        String crono(float tiempo){
            float horas,minutos;
            int segundos;
            String conteo;
            minutos=tiempo/60;
            if(minutos>0){
                segundos=(int)tiempo-(int)(tiempo/60)*60;
                if(segundos<10){
                    conteo=String.valueOf((int)(minutos))+":0"+segundos;
                }else{
                    conteo=String.valueOf((int)(minutos))+":"+segundos;
                }
            }else{
                if(tiempo<10){
                    conteo="0:0"+(int)tiempo;
                }else{
                    conteo="0:"+(int)tiempo;
                }

            }
            return conteo;
        }


     /*   @Override
        protected void onPostExecute(Integer cantidadProcesados) {
            //TV_mensaje.setText("DESPUÉS de TERMINAR la descarga. Se han descarcado "+cantidadProcesados+" imágenes. Hilo PRINCIPAL");
            Log.v(TAG, "DESPUÉS de TERMINAR la descarga. Se han descarcado "+cantidadProcesados+" imágenes. Hilo PRINCIPAL");

            tiempo.setTextColor(Color.GREEN);
        }*/


     /*   @Override
        protected void onCancelled (Integer cantidadProcesados) {
         //   TV_mensaje.setText("DESPUÉS de CANCELAR la descarga. Se han descarcado "+cantidadProcesados+" imágenes. Hilo PRINCIPAL");
            Log.v(TAG, "DESPUÉS de CANCELAR la descarga. Se han descarcado "+cantidadProcesados+" imágenes. Hilo PRINCIPAL");

            tiempo.setTextColor(Color.RED);
        }*/

    }


}





