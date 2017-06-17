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

import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;

/**
 * Created by Ignacio on 17/06/2017.
 */

public class AdapterPeople extends ArrayAdapter<User> {
private final Activity context;
private final List<User> users;

public AdapterPeople(Activity context, List<User> users) {
        super(context, R.layout.arraylist, users);
        this.context = context;
        this.users = users;
        }

public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.arraylist, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.Text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);

        txtTitle.setText(users.get(position).getName());
        Picasso.with(getContext()).load(JSONservice.URL + users.get(position).getImage()).into(imageView);

        return rowView;
        }
}
