package modulo12.com.br.neoz;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by maxim on 10/02/2017.
 */

public class CustomList extends ArrayAdapter<Bitmap> {
    private ArrayList<Bitmap> logosList;
    private Activity context;

    public CustomList (Activity context, ArrayList<Bitmap> logos) {
        super(context, R.layout.logos_list, logos);
        this.context = context;
        this.logosList = logos;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.logos_list, null, true);
        ImageView imageView = (ImageView) listViewItem.findViewById(R.id.logoImage);

        imageView.setImageBitmap(logosList.get(position));
        return listViewItem;

    }



}
