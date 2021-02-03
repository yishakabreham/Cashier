//package et.com.cashier.activities;
//
//import androidx.appcompat.app.AppCompatActivity;
//import et.com.cashier.R;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//
//import com.microsoft.signalr.HubConnection;
//import com.microsoft.signalr.HubConnectionBuilder;
//
//public class windowSplashScreen extends AppCompatActivity {
//
//    private static HubConnection hubConnection;
//    public static HubConnection getHubConnection() {
//        return hubConnection;
//    }
//
//    public void setHubConnection(HubConnection hubConnection) {
//        this.hubConnection = hubConnection;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_window_splash_screen);
//
//        setHubConnection(HubConnectionBuilder.create("http://192.168.1.235:8085/chat").build());
//        getHubConnection().on("broadcastMessage", (name, message)-> {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                }
//            });
//        }, String.class, String.class);
//
//        try {
//            getHubConnection().send("Send", "Mobile 1","message");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        new HubConnectionTask().execute(getHubConnection());
//    }
//
//    class HubConnectionTask extends AsyncTask<HubConnection, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(HubConnection... hubConnections) {
//            HubConnection hubConnection = hubConnections[0];
//            hubConnection.start().blockingAwait();
//            return null;
//        }
//    }
//}