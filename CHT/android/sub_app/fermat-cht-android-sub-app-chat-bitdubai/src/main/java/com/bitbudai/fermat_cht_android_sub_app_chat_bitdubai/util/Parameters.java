package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util;

/**
 * Created by miguel on 22/01/16.
 */
public class Parameters {
    private String titulo;
    private String subtitulo;

    public Parameters(String tit, String sub) {
        titulo = tit;
        subtitulo = sub;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }
}


