package modulo12.com.br.neoz;

/**
 * Created by maximillianfx on 22/02/17.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.util.ArrayList;

import modulo12.com.br.neoz.MapAPI.Source;
import modulo12.com.br.neoz.MapAPI.Links;

import static modulo12.com.br.neoz.MapAPI.Links.SORT_BY;
import static modulo12.com.br.neoz.MapAPI.Links.LINK_SOURCE_API;
import static modulo12.com.br.neoz.MapAPI.Links.API_KEY;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {


    public String newspaper;
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
    public static PlaceholderFragment newInstance(String newspaper) {
        Log.i("Entrou","new instance fragment");
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_NEWSPAPER, newspaper);
        fragment.setArguments(args);
        Log.i("Saiu","new instance fragment");
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Entrou","on create view fragment");
        View rootView = inflater.inflate(R.layout.fragment_news_papers_viewer, container, false);
        GetArticles getArticles = new GetArticles();
        Log.i("LINKTOP",LINK_SOURCE_API+getArguments().getString(ARGS_NEWSPAPER)+API_KEY);
        getArticles.execute(LINK_SOURCE_API+getArguments().getString(ARGS_NEWSPAPER)+API_KEY);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format, String.valueOf(getArguments().getInt(ARG_SECTION_NUMBER)),getArguments().getString(ARG_SECTION_NAME)));
        Log.i("saiu","on create view fragment");
        return rootView;
    }



    /*--------------------------------------AsynTask--------------------------------------------*/

    private class GetArticles extends AsyncTask<String, Void, ExecuteArticles> {

        protected void onPreExecute () {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
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
            }
        }
    }
}