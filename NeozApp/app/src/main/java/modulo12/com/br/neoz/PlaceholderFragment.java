package modulo12.com.br.neoz;

/**
 * Created by maximillianfx on 22/02/17.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import modulo12.com.br.neoz.Adapters.CardsAdapter;
import modulo12.com.br.neoz.ListData.CardsData;
import modulo12.com.br.neoz.MapAPI.Article;
import modulo12.com.br.neoz.MapAPI.Source;
import modulo12.com.br.neoz.MapAPI.Links;

import static modulo12.com.br.neoz.MapAPI.Links.SORT_BY;
import static modulo12.com.br.neoz.MapAPI.Links.LINK_SOURCE_API;
import static modulo12.com.br.neoz.MapAPI.Links.API_KEY;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {


    public View rootView;
    public ProgressBar progressBar;
    public String newspaper;
    public CardsAdapter cardsAdapter;
    public List<CardsData> cardsDataList = new ArrayList<>();
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    public static String ARGS_NEWSPAPER = "newspaper_id";

    public PlaceholderFragment() {
        Log.i("Entrou","CONSTRUTOR FRAGMENT");
        Log.i("Saiu","CONSTRUTOR FRAGMENT");
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(String linkArticles) {
        Log.i("Entrou","new instance fragment");
        Bundle args = new Bundle();
        args.putString("ArticlesLink", linkArticles);
        PlaceholderFragment fragment = new PlaceholderFragment();
        fragment.setArguments(args);
        Log.i("Saiu","new instance fragment");
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Entrou","on create view fragment");
        rootView = inflater.inflate(R.layout.fragment_news_papers_viewer, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listaCartoes);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.rgb(197,80,22), android.graphics.PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.GONE);

        //recyclerView = (RecyclerView)rootView.findViewById(R.id.listaCartoes);
        //recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(getActivity());
        //recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(cardsAdapter);


        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format, String.valueOf(getArguments().getInt(ARG_SECTION_NUMBER)),getArguments().getString(ARG_SECTION_NAME)));
        Log.i("saiu","on create view fragment");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(getActivity());
        cardsAdapter = new CardsAdapter(getActivity(),cardsDataList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cardsAdapter);
        GetArticles getArticles = new GetArticles();
        getArticles.execute(getArguments().getString("ArticlesLink"));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Entrou em","on create");
    }

    @Override
    public void onResume() {
        super.onResume();
        cardsDataList.clear();
        cardsAdapter.notifyDataSetChanged();
    }



    /*--------------------------------------AsynTask--------------------------------------------*/


    private class GetArticles extends AsyncTask<String, Void, ExecuteArticles> {

        protected void onPreExecute () {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ExecuteArticles doInBackground(String... urls) {
            try {
                ExecuteArticles executeArticles;
                ObjectMapper mapper = new ObjectMapper();
                executeArticles = mapper.readValue(new URL(urls[0]), ExecuteArticles.class);
                return executeArticles;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ExecuteArticles result) {
            super.onPostExecute(result);
            if (result != null) {
                //progressBar.setVisibility(View.GONE);
                Log.i("Resultado de noticias",result.getStatus());
                Log.i("Newspaper",result.getSource());


                for (Article article: result.getArticles()) {
                    String dataOriginal = article.getPublishedAt();
                    String dataFormated = "";
                    String hour = "";
                    if (dataOriginal != null) {
                        dataFormated = dataOriginal.substring(8, 10) + "/" + dataOriginal.substring(5, 7) + "/" + dataOriginal.substring(0, 4);
                        hour = dataOriginal.substring(11, 13) + ":" + dataOriginal.substring(14, 16);
                    }
                    cardsDataList.add(new CardsData(article.getTitle(),article.getDescription(),dataFormated,hour,article.getUrl()));
                }

                LogosDownloader logosDownloader = new LogosDownloader();
                logosDownloader.execute(result.getArticles());

            }
        }
    }

    private class LogosDownloader extends AsyncTask<List<Article>, Void, ArrayList<Bitmap>> {

        protected void onPreExecute () {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Bitmap> doInBackground(List<Article>... article) {
            Bitmap downloaded;
            URL urlLogosNewsAPI;
            HttpURLConnection urlConnection = null;
            ArrayList<Bitmap> imgsArticle = new ArrayList<>();
            try {
                for (Article article1 : article[0]) {
                    urlLogosNewsAPI = new URL(article1.getUrlToImage());
                    urlConnection = (HttpURLConnection) urlLogosNewsAPI.openConnection();
                    urlConnection.connect();
                    InputStream inputStream = urlConnection.getInputStream();
                    downloaded = BitmapFactory.decodeStream(inputStream);
                    imgsArticle.add(downloaded);
                }
                return imgsArticle;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> imgsArticles) {
            super.onPostExecute(imgsArticles);
            if (imgsArticles != null) {
                progressBar.setVisibility(View.GONE);
                int contador = 0;
                for (Bitmap img : imgsArticles) {
                    cardsDataList.get(contador).setImagemNoticia(img);
                    contador++;
                }
                cardsAdapter.notifyDataSetChanged();
                Log.i("LogosResult","Logos capturados");
            }
        }
    }

}