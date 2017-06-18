package edu.upc.eetac.dsa.eetakemongoandroid;

import java.util.List;

import edu.upc.eetac.dsa.eetakemongoandroid.Model.Atack;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Markers;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Party;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Ignacio on 26/05/2017.
 */

public interface JSONservice {
    String URL_Local="http://10.0.2.2:8080/";
    String URL="http://192.168.1.47:8080/";

    @POST("myapp/web/SingIn")
    Call<User> SingIn(@Body User user);

    @POST("myapp/web/createUser")
    Call<User> logIn(@Body User user);

    @POST("myapp/web/addAEetakemonsToUser")
    Call<User> addAEetakemonsToUser(@Body User user);

    @GET("myapp/web/getAllEetakemons")
    Call<List<Eetakemon>> getAllEetakemons(@Header("Autorization") String Autorization);

    @GET("myapp/web/getCompleteUserByUsername/{username}")
    Call<User> getCompletUserByUsername(@Path("username") String username, @Header("Autorization") String Autorization);

    @GET("myapp/{name}/getEetakemons")
    Call<List<Eetakemon>> getEetakemons(@Path("name") String name, @Header("Autorization") String Autorization);


    @POST("myapp/web/markers")
    Call<List<Markers>> miPos(@Body Markers markers, @Header("Autorization") String Autorization);

    @POST("myapp/web/nearMarkers")
    Call<List<Markers>> allPos(@Body Markers markers, @Header("Autorization") String Autorization);

    @POST("myapp/web/markers")
    Call<Party>resgisterCandidate(@Body User user, @Header("Autorization") String Autorization);

    @GET("myapp/web/markers")
    Call<Party>getParty(@Header("Autorization") String Autorization);

    @POST()
    Call<Party>doAtack(@Body Atack atack,@Header("Autorization") String Autorization);

    @GET()
    Call<Party>reciveAtack(@Header("Autorization") String Autorization);
}
