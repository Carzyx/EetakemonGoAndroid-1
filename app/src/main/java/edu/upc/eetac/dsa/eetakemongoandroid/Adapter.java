package edu.upc.eetac.dsa.eetakemongoandroid;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;

/**
 * Created by Ignacio on 03/06/2017.
 */

public class Adapter extends ArrayAdapter<Eetakemon> {
    private final Activity context;
    private final List<Eetakemon> eetakemons;

    public Adapter(Activity context, List<Eetakemon> eetakemons) {
        super(context, R.layout.arraylist, eetakemons);
        this.context = context;
        this.eetakemons = eetakemons;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.arraylist, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.Text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);

        txtTitle.setText(eetakemons.get(position).getName());
        Picasso.with(getContext()).load(JSONservice.URL + eetakemons.get(position).getImage()).into(imageView);
        if (eetakemons.get(position).getLevel() != 0) {
            TextView ps = (TextView) rowView.findViewById(R.id.listps);
            TextView level = (TextView) rowView.findViewById(R.id.listlevel);

            ps.setText("PS: " + String.valueOf(eetakemons.get(position).getPs()));
            level.setText("Level: " + String.valueOf(eetakemons.get(position).getLevel()));
        }
        return rowView;

    }
}
