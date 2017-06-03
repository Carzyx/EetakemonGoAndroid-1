package edu.upc.eetac.dsa.eetakemongoandroid;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Markers;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Ignacio on 26/05/2017.
 */

public interface JSONservice {
    String URL="http://10.0.2.2:8080/";

    @POST("myapp/LogIn")
    Call<User> login(@Body User user);

    @GET("myapp/web/getAllEetakemons")
    Call<List<Eetakemon>>getAllEetakemons();

    @GET("myapp/{name}/getEetakemons")
    Call<List<Eetakemon>>getEetakemons(@Path("name")String name);

    @POST("myapp/web/markers")
    Call<List<Markers>>miPos(@Body LatLng latLng);

}
