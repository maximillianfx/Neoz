package modulo12.com.br.neoz;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.util.ArrayList;

import modulo12.com.br.neoz.MapAPI.Source;

import static modulo12.com.br.neoz.MapAPI.Links.API_KEY;
import static modulo12.com.br.neoz.MapAPI.Links.LINK_NEWS_API;
import static modulo12.com.br.neoz.MapAPI.Links.LINK_SOURCE_API;

public class NewsPapersViewer extends AppCompatActivity {


    public ProgressBar progressBar;
    public ArrayList<String> newspapersList;
    public ArrayList<String> newspapersID;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Entrou em","ONCREATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_papers_viewer);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.rgb(197,80,22), android.graphics.PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.GONE);

        String categorySelected = (String) getIntent().getCharSequenceExtra("categorySelected");
        NewspapersByCategory newspapersByCategory = new NewspapersByCategory();
        newspapersByCategory.execute(LINK_NEWS_API+categorySelected);
        Log.i("Saiu de","ONCREATE");
    }

    public void setUpUI () {
        Log.i("Entrou em","SETUPUI");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("Entrou em","onpagescrolled");

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("Entrou em","onpageselected");

                /*Fragment fr = mSectionsPagerAdapter.getItem(position);
                if (fr instanceof PlaceholderFragment) {
                    ((PlaceholderFragment) fr).updateContent(LINK_SOURCE_API+newspapersID.get(position)+API_KEY);
                }
               Log.i("Saiu de","onpageselected");*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("Entrou em","onpagescrollstatechanged");
            }
        });


        Log.i("Saiu de","SETUPUI");
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("Entrou em","ONCREATEOPTIONSMENU");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_papers_viewer, menu);
        Log.i("Saiu de","ONCREATEOPTIONSMENU");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("Entrou em","onoptionsitemselected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            Log.i("saiu de","construtor de sectionspager");
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("Entrou em","getitem");
            Log.i("Posicao Maluca",String.valueOf(position));

            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(LINK_SOURCE_API+newspapersID.get(position)+API_KEY);
        }

        @Override
        public int getCount() {
            Log.i("Entrou em","getcount");
            // Numero de jornais listados nas tabs
            return newspapersList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.i("Entrou em","getpagetitle");
            return newspapersList.get(position);
        }
    }



    /*--------------------------------------AsynTask--------------------------------------------*/

    private class NewspapersByCategory extends AsyncTask<String, Void, APIMap> {

        protected void onPreExecute () {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
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
                progressBar.setVisibility(View.GONE);
                newspapersList = new ArrayList<>();
                newspapersID = new ArrayList<>();
                for (Source src: result.getSources()) {
                    newspapersList.add(src.getName());
                    newspapersID.add(src.getId());
                }
                Log.i("Resultado",result.getStatus());
                setUpUI();
            }
        }
    }

    /*-------------------------------------------------------------------------------------------------*/


}
