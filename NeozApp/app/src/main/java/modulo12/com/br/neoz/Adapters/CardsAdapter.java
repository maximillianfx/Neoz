package modulo12.com.br.neoz.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import modulo12.com.br.neoz.ListData.CardsData;
import modulo12.com.br.neoz.R;

/**
 * Created by maxim on 08/02/2017.
 */

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardsViewHolder> {


    private List<CardsData> listaDadosCartoes;

    public CardsAdapter (List<CardsData> listaDadosCartoes) {
        this.listaDadosCartoes = listaDadosCartoes;
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
        cardsViewHolder.imagemNoticia.setImageBitmap(cardsData.imagemNoticia);
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

        public CardsViewHolder(View itemView) {
            super(itemView);
            tituloNoticia = (TextView) itemView.findViewById(R.id.txtTituloNoticia);
            descricaoNoticia = (TextView) itemView.findViewById(R.id.txtDescricaoNoticia);
            dataNoticia = (TextView) itemView.findViewById(R.id.txtDataNoticia);
            imagemNoticia = (ImageView) itemView.findViewById(R.id.imgCard);
        }
    }
}
