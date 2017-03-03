package modulo12.com.br.neoz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import modulo12.com.br.neoz.MapAPI.Source;

import static modulo12.com.br.neoz.MapAPI.Links.LINK_NEWS_API;

public class NewspapersListOld extends AppCompatActivity {

    public String categorySelected;
    public ArrayList<Bitmap> logosJornals;
    public ProgressBar progressBar;
    public TextView txtGettingNewspapers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspapers_list_old);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.rgb(219,124,0), android.graphics.PorterDuff.Mode.MULTIPLY);
        txtGettingNewspapers = (TextView) findViewById(R.id.txtGettingNewspapers);

        progressBar.setVisibility(View.GONE);
        txtGettingNewspapers.setVisibility(View.GONE);

        categorySelected = (String) getIntent().getCharSequenceExtra("categorySelected");
        NewspapersByCategory newspapersByCategory = new NewspapersByCategory();
        logosJornals = new ArrayList<>();
        newspapersByCategory.execute(LINK_NEWS_API+categorySelected);

    }

    private class NewspapersByCategory extends AsyncTask<String, Void, APIMap> {

        protected void onPreExecute () {
            super.onPreExecute();
        }

        @Override
        protected APIMap doInBackground(String... urls) {
            try {
                APIMap apiMap;
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
            }
        }
    }
}
