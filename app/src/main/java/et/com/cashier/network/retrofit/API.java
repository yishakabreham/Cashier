package et.com.cashier.network.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API
{
    private static Retrofit retrofit = null;
    public static APIInterface userLogin()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.155:8101/Authentication/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        //Creating object for our interface
        APIInterface api = retrofit.create(APIInterface.class);
        return api; // return the APIInterface object
    }
    public static APIInterface tripList(String token)
    {
        Retrofit retrofit = null;
        TokenInterceptor interceptor = new TokenInterceptor(token);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("http://192.168.1.155:8101/Trips/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        APIInterface api = retrofit.create(APIInterface.class);
        return api;
    }
    public static APIInterface seatArrangement(String token)
    {
        Retrofit retrofit = null;
        TokenInterceptor interceptor = new TokenInterceptor(token);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://192.168.1.155:8101/Trips/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface api = retrofit.create(APIInterface.class);
        return api;
    }
    public static APIInterface configurations(String token)
    {
        Retrofit retrofit = null;
        TokenInterceptor interceptor = new TokenInterceptor(token);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://192.168.1.155:8101/Miscellaneous/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface api = retrofit.create(APIInterface.class);
        return api;
    }
    public static APIInterface consigneeDetail(String token)
    {
        Retrofit retrofit = null;
        TokenInterceptor interceptor = new TokenInterceptor(token);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://192.168.1.155:8101/Trips/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface api = retrofit.create(APIInterface.class);
        return api;
    }
    public static APIInterface postTransaction(String token)
    {
        Retrofit retrofit = null;
        TokenInterceptor interceptor = new TokenInterceptor(token);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://192.168.1.155:8101/Trips/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface api = retrofit.create(APIInterface.class);
        return api;
    }
}
