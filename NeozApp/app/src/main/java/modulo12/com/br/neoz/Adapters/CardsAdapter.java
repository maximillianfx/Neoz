package modulo12.com.br.neoz.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import modulo12.com.br.neoz.ListData.CardsData;
import modulo12.com.br.neoz.R;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardsViewHolder> {


    private List<CardsData> listaDadosCartoes;
    private Context context;

    public CardsAdapter (Context context, List<CardsData> listaDadosCartoes) {
        this.context = context;
        this.listaDadosCartoes = listaDadosCartoes;
    }

    public Context getContext () {
        return this.context;
    }

    @Override
    public int getItemCount() {
        return listaDadosCartoes.size();
    }

    @Override
    public void onBindViewHolder (CardsViewHolder cardsViewHolder, int i) {
        CardsData cardsData = listaDadosCartoes.get(i);
        cardsViewHolder.tituloNoticia.setText(cardsData.tituloNoticia);
        cardsViewHolder.descricaoNoticia.setText(cardsData.descricaoNoticia);
        cardsViewHolder.dataNoticia.setText(cardsData.dataNoticia);
        cardsViewHolder.horaNoticia.setText(cardsData.horaNoticia);
        cardsViewHolder.imagemNoticia.setImageBitmap(cardsData.imagemNoticia);
        cardsViewHolder.linkNoticia = cardsData.linkNoticia;
    }

    @Override
    public CardsViewHolder onCreateViewHolder (ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards_layout, viewGroup, false);
        return new CardsViewHolder(view);
    }



    public static class CardsViewHolder extends RecyclerView.ViewHolder {

        protected TextView tituloNoticia;
        protected TextView descricaoNoticia;
        protected TextView dataNoticia;
        protected ImageView imagemNoticia;
        protected TextView horaNoticia;
        protected String linkNoticia;

        public CardsViewHolder(View itemView) {
            super(itemView);
            tituloNoticia = (TextView) itemView.findViewById(R.id.txtTituloNoticia);
            descricaoNoticia = (TextView) itemView.findViewById(R.id.txtDescricaoNoticia);
            dataNoticia = (TextView) itemView.findViewById(R.id.txtDataNoticia);
            imagemNoticia = (ImageView) itemView.findViewById(R.id.imgCard);
            horaNoticia = (TextView)itemView.findViewById(R.id.txtHoraNoticia);
        }
    }
}