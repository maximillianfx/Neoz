package modulo12.com.br.neoz.ListData;

import android.graphics.Bitmap;

/**
 * Created by maxim on 08/02/2017.
 */

public class CardsData {

    public String tituloNoticia;
    public String descricaoNoticia;
    public String dataNoticia;
    public String horaNoticia;
    public Bitmap imagemNoticia;
    public String linkNoticia;

    public CardsData(String tituloNoticia, String descricaoNoticia, String dataNoticia, String horaNoticia, String linkNoticia) {
        this.tituloNoticia = tituloNoticia;
        this.descricaoNoticia = descricaoNoticia;
        this.dataNoticia = dataNoticia;
        this.horaNoticia = horaNoticia;
        this.linkNoticia = linkNoticia;
    }

    public void setTituloNoticia(String tituloNoticia) {
        this.tituloNoticia = tituloNoticia;
    }

    public void setDescricaoNoticia(String descricaoNoticia) {
        this.descricaoNoticia = descricaoNoticia;
    }

    public void setImagemNoticia(Bitmap imagemNoticia) {
        this.imagemNoticia = imagemNoticia;
    }

    public void setDataNoticia(String dataNoticia) {
        this.dataNoticia = dataNoticia;
    }

    public void setHoraNoticia (String horaNoticia) {
        this.horaNoticia = horaNoticia;
    }

    public void setLinkNoticia (String linkNoticia) {
        this.linkNoticia = linkNoticia;
    }
}
