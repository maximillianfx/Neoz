package modulo12.com.br.neoz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void categorySelected (View v) {
        CardView cardView = (CardView) v;
        String category = cardView.getTag().toString();
        String categoryFormated = category.substring(0,1).toLowerCase()+category.substring(1);
        Intent newspapersIntent = new Intent(MainActivity.this,NewsPapersViewer.class);
        newspapersIntent.putExtra("categorySelected",categoryFormated);
        startActivity(newspapersIntent);
    }
}
