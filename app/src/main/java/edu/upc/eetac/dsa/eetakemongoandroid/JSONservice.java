package edu.upc.eetac.dsa.eetakemongoandroid;

import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Ignacio on 26/05/2017.
 */

public interface JSONservice {
    String URL="http://10.0.2.2:8080/myapp/";

    @POST("LogIn")
    Call<User> login(@Body User user);


}
