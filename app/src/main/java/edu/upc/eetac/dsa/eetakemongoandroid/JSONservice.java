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
    String URL="http://192.168.1.55:8080/";
    //User
    @POST("myapp/UserService/singIn")
    Call<User> SingIn(@Body User user);

    @POST("myapp/UserService/createUser")
    Call<User> logIn(@Body User user);

    @GET("myapp/UserService/getCompleteUserByName/{username}")
    Call<User> getCompletUserByUsername(@Path("username") String username, @Header("Authoritzation") String Autorization);

    @POST("myapp/UserService/addAEetakemonsToUser")
    Call<User> addAEetakemonsToUser(@Body User user,@Header("Authoritzation") String Autorization);

    @GET("myapp/EetakemonService/getAllCompleteEetakemons")
    Call<List<Eetakemon>> getAllEetakemons(@Header("Authoritzation") String Autorization);

    //Markers
    @POST("myapp/UserService/markers")
    Call<List<Markers>> miPos(@Body Markers markers, @Header("Authoritzation") String Autorization);

    @POST("myapp/UserService/nearMarkers")
    Call<List<Markers>> allPos(@Body Markers markers, @Header("Authoritzation") String Autorization);
    //Party
    @POST("myapp/GameService/registerCandidate")
    Call<Party>resgisterCandidate(@Body User user, @Header("Authoritzation") String Autorization);

    @POST("myapp/GameService/getParty")
    Call<Party>getParty(@Body User user,@Header("Authoritzation") String Autorization);

    @POST("myapp/GameService/doAtack")
    Call<Party>doAtack(@Body Party party,@Header("Authoritzation") String Autorization);

}
