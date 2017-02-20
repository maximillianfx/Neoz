package modulo12.com.br.neoz;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import modulo12.com.br.neoz.MapAPI.Source;

public class MainActivity extends AppCompatActivity {

    public static String LINK_NEWS_API = "https://newsapi.org/v1/sources";
    public ArrayList<String> sourcesNames = new ArrayList<String>();
    public ArrayList<Bitmap> logosBitmaps = new ArrayList<Bitmap>();
    public ListView listView;
    public APIMap apiMap;
    public Set<String> listCategories;
    public ProgressBar progressBar;
    public TextView txtGettingJornals, txtGettingLogos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listaCartoes);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);*/


        //CardsAdapter cardsAdapter = new CardsAdapter(criarLista());
        //recyclerView.setAdapter(cardsAdapter);

        //Captura de: Nome, URL e Logo dos jornais - FUNCIONANDO!
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txtGettingJornals = (TextView) findViewById(R.id.txtGettingJornals);
        txtGettingLogos = (TextView) findViewById(R.id.txtGettingLogos);
        listView = (ListView) findViewById(R.id.listViewLogos);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        SourcesAPI sourcesAPI = new SourcesAPI();
        listView.setVisibility(View.GONE);
        txtGettingLogos.setVisibility(View.GONE);
        txtGettingJornals.setVisibility(View.GONE);
        try {
            sourcesAPI.execute(LINK_NEWS_API);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void formatListCategories () {
        ArrayList<String> category = new ArrayList<>();
        for (Source src : apiMap.getSources()) {
            category.add(src.getCategory().substring(0,1).toUpperCase()+src.getCategory().substring(1));
        }
        listCategories = new HashSet<String>(category);
        category.clear();
        category.addAll(listCategories);
        Collections.sort(category);
        for (String str: category) {
            Log.i("Categoria",str);
        }
    }

    public void getURLLogos () {
        int sourcesSize = Integer.parseInt(sourcesNames.get(0));
        String [] logosURLs = new String[sourcesSize];
        for (int i = 141, j = 0; i < sourcesNames.size(); i++, j++) {
            logosURLs[j] = sourcesNames.get(i);
        }
        LogosDownloader logosDownloader = new LogosDownloader();
        logosDownloader.execute(logosURLs);
    }

    public void formatListView () {
        listView.setVisibility(View.VISIBLE);
        CustomList customList = new CustomList(this, logosBitmaps);
        listView.setAdapter(customList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Touch Click In: " + sourcesNames.get(position+1),Toast.LENGTH_LONG).show();
            }
        });
    }

    private class SourcesAPI extends AsyncTask<String, Void, APIMap> {

        protected void onPreExecute () {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.getIndeterminateDrawable().setColorFilter(Color.rgb(243,146,0), PorterDuff.Mode.MULTIPLY);
            txtGettingJornals.setVisibility(View.VISIBLE);
        }

        @Override
        protected APIMap doInBackground(String... urls) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                apiMap = mapper.readValue(new URL(urls[0]), APIMap.class);
                return apiMap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(APIMap result) {
            super.onPostExecute(result);
            if (result != null) {
                progressBar.setVisibility(View.GONE);
                txtGettingJornals.setVisibility(View.GONE);
                formatListCategories();
            }
        }
    }

    private class LogosDownloader extends AsyncTask<String, Void, ArrayList<Bitmap>> {

        protected void onPreExecute () {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            txtGettingLogos.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Bitmap> doInBackground(String... urlLogos) {
            Bitmap downloaded;
            ArrayList<Bitmap> resultFinal = new ArrayList<Bitmap>();
            URL urlLogosNewsAPI;
            HttpURLConnection urlConnection = null;
            try {
                for (String urlLogoX : urlLogos) {
                    urlLogosNewsAPI = new URL(urlLogoX);
                    urlConnection = (HttpURLConnection) urlLogosNewsAPI.openConnection();
                    urlConnection.connect();
                    InputStream inputStream = urlConnection.getInputStream();
                    downloaded = BitmapFactory.decodeStream(inputStream);
                    resultFinal.add(downloaded);
                }
                return resultFinal;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> logos) {
            super.onPostExecute(logos);
            for (Bitmap logo: logos) {
                logosBitmaps.add(logo);
            }
            progressBar.setVisibility(View.GONE);
            txtGettingLogos.setVisibility(View.GONE);
            formatListView();
        }
    }

    /*private List<CardsData> criarLista () {
        List<CardsData> result = new ArrayList<CardsData>();
        CardsData cardsData = new CardsData();
        cardsData.tituloNoticia = getResources().getString(R.string.titulo1);
        cardsData.descricaoNoticia = getResources().getString(R.string.desc1);
        cardsData.dataNoticia = getResources().getString(R.string.data1);
        cardsData.imagemNoticia = BitmapFactory.decodeResource(getResources(), R.drawable.imagem1);

        CardsData cardsData2 = new CardsData();
        cardsData2.tituloNoticia = getResources().getString(R.string.titulo2);
        cardsData2.descricaoNoticia = getResources().getString(R.string.desc2);
        cardsData2.dataNoticia = getResources().getString(R.string.data2);
        cardsData2.imagemNoticia = BitmapFactory.decodeResource(getResources(), R.drawable.protestos);

        CardsData cardsData3 = new CardsData();
        cardsData3.tituloNoticia = getResources().getString(R.string.titulo3);
        cardsData3.descricaoNoticia = getResources().getString(R.string.desc3);
        cardsData3.dataNoticia = getResources().getString(R.string.data3);
        cardsData3.imagemNoticia = BitmapFactory.decodeResource(getResources(), R.drawable.primeiraliga);

        result.add(cardsData);
        result.add(cardsData2);
        result.add(cardsData3);

        return result;
    }*/
}
