package et.com.cashier.network.retrofit;

import java.util.ArrayList;

import et.com.cashier.model.transaction.BaseTransaction;
import et.com.cashier.model.transaction.SaveResult;
import et.com.cashier.model.transaction.VoucherBuffer;
import et.com.cashier.network.retrofit.pojo.Configuration;
import et.com.cashier.network.retrofit.pojo.Consignee;
import et.com.cashier.network.retrofit.pojo.SingleSeatArrangement;
import et.com.cashier.network.retrofit.pojo.Trip;
import et.com.cashier.network.retrofit.pojo.UserInformation;
import et.com.cashier.network.retrofit.post.ItemCode;
import et.com.cashier.network.retrofit.post.TripSearchCriteria;
import et.com.cashier.network.retrofit.post.UserCredential;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface
{
    @POST("loginuser")
    Call<UserInformation> getUser(@Body UserCredential userCredential);
    @POST("getTripsByDate")
    Call<Trip> getTrips(@Body TripSearchCriteria tripSearchCriteria);
    @POST("getTripSeatArrangement")
    Call<SingleSeatArrangement> getSeatArrangement(@Body ItemCode itemCode);
    @POST("getConfigurations")
    Call<Configuration> getSystemConfigurations();
    @POST("getConsigneeByPhone")
    Call<Consignee> getConsigneeDetail(@Body ItemCode itemCode);
    @POST("saveTransaction")
    Call<SaveResult> saveTransaction(@Body BaseTransaction baseTransaction);
}
