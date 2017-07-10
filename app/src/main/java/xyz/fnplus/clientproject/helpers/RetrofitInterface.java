package xyz.fnplus.clientproject.helpers;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import xyz.fnplus.clientproject.models.DataModel;

/**
 * Created by krsnv on 05-Jul-17.
 */

public interface RetrofitInterface {

    @GET("get_loom.php")
    Call<DataModel> getLoomInfo(@Query("no") String loomNo);

    @FormUrlEncoded
    @POST("set_loom.php")
    Call<Void> setLoomInfo(@Field("loom") String loomdetails);
}