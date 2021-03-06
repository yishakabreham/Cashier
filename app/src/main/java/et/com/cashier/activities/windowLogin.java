package et.com.cashier.activities;

import androidx.appcompat.app.AppCompatActivity;
import et.com.cashier.R;
import et.com.cashier.network.retrofit.pojo.Config;
import et.com.cashier.network.retrofit.pojo.Configuration;
import et.com.cashier.network.retrofit.pojo.UserInformation;
import et.com.cashier.network.retrofit.API;
import et.com.cashier.network.retrofit.post.UserCredential;
import et.com.cashier.utilities.CommonElements;
import et.com.cashier.utilities.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.io.IOException;

public class windowLogin extends AppCompatActivity
{
    //region Configurations
    public static boolean CHILD_POLICY_ENABLED = false;
    public static double CHILD_DISCOUNT = 0.0;

    //endregion
    private UserInformation userInformation;
    private EditText txtUserName, txtPassword;
    private Button btnLogin;
    private CheckBox checkBoxRemember;

    private SharedPreferences sharedPreferences;
    private static final String userPreference = "userPreference";
    private static final String userName = "userNameKey";
    private static final String password = "passwordKey";

    private static HubConnection hubConnection;
    public static HubConnection getHubConnection()
    {
        return hubConnection;
    }

    private boolean thingsConfigured = false;
    public static Configuration systemConfigurations;

    private windowProgress progress;

    public void setHubConnection(HubConnection hubConnection) {
        this.hubConnection = hubConnection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_login);

        init();

        setHubConnection(HubConnectionBuilder.create("http://192.168.1.155:8101/chat").build());
        new HubConnectionTask().execute(getHubConnection());
    }

    class HubConnectionTask extends AsyncTask<HubConnection, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(HubConnection... hubConnections) {
            HubConnection hubConnection = hubConnections[0];
            hubConnection.start().blockingAwait();
            return null;
        }
    }

    private void init() {
        btnLogin = findViewById(R.id.btnLogin);
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        checkBoxRemember = findViewById(R.id.cbxRemember);

        popUserData();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(txtUserName.getText() != null && !txtUserName.getText().toString().equals("") &&
                        txtPassword.getText() != null && !txtPassword.getText().toString().equals(""))
                {
                    String userName = txtUserName.getText().toString();
                    String password = txtPassword.getText().toString();

                    String[] params = new String[2];
                    params[0] = userName;
                    params[1] = password;

                    GetUser(userName, password);
                    //systemConfigurations(userInformation.getToken());
                }
                else
                {
                    View rootLayout = findViewById(R.id.rootLayout);
                    Snackbar snackbar = Snackbar
                            .make(rootLayout, Html.fromHtml("<B>Error</B><Br/>please type your username and password"), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }
    private void systemConfigurations(String token)
    {
        API.configurations(token).getSystemConfigurations()
                .enqueue(new Callback<Configuration>() {
                    @Override
                    public void onResponse(Call<Configuration> call, Response<Configuration> response)
                    {
                        if(response.isSuccessful() && response.code() == 200)
                        {
                            systemConfigurations = response.body();
                            populateConfigurations(systemConfigurations);

                            Intent intent = new Intent(windowLogin.this, windowDashboard.class);
                            intent.putExtra("user", userInformation.getUser());
                            intent.putExtra("company", userInformation.getCompany());
                            intent.putExtra("token", userInformation.getToken());

                            if (checkBoxRemember.isChecked())
                                RememberUser(txtUserName.getText().toString(), txtPassword.getText().toString());
                            progress.hideProgress();
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Configuration> call, Throwable t)
                    {
                    }
                });
    }

    private void GetUser(String userName, final String password)
    {
        progress = windowProgress.getInstance();
        progress.showProgress(windowLogin.this, "Logging in", false);

        UserCredential userCredential = new UserCredential();
        userCredential.setUserName(userName);
        userCredential.setPassword(password);

        API.userLogin().getUser(userCredential)
                .enqueue(new Callback<UserInformation>() {
                    @Override
                    public void onResponse(Call<UserInformation> call, Response<UserInformation> response)
                    {
                        if(response.isSuccessful() && response.code() == 200)
                        {
                            userInformation = response.body();

                            if(userInformation != null)
                            {
                                systemConfigurations(userInformation.getToken());
                            }
                        }
                        else if(response.code() == 401)
                        {
                            View rootLayout = findViewById(R.id.rootLayout);
                            Snackbar snackbar = Snackbar
                                    .make(rootLayout, Html.fromHtml("<B>Unauthorized user</B><Br/>username or password incorrect"), Snackbar.LENGTH_LONG);
                            snackbar.show();
                            progress.hideProgress();
                        }
                        else if(response.code() == 500)
                        {
                            View rootLayout = findViewById(R.id.rootLayout);
                            Snackbar snackbar = Snackbar
                                    .make(rootLayout, Html.fromHtml("<B>Server error</B><Br/>the server is temporarily down"), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                        progress.hideProgress();
                    }

                    @Override
                    public void onFailure(Call<UserInformation> call, Throwable t)
                    {
                        View rootLayout = findViewById(R.id.rootLayout);
                        Snackbar snackbar = Snackbar
                                .make(rootLayout, Html.fromHtml("<B>Error</B><Br/>error occurred while fetching data from server"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                        progress.hideProgress();
                    }
                });
    }
    private void RememberUser(String _userName, String _password)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences = getSharedPreferences(userPreference,
                Context.MODE_PRIVATE);
        if (!sharedPreferences.contains(userName))
        {
            if(!_userName.equals(sharedPreferences.getString(userName, "")))
                editor.putString(userName, _userName);
        }
        if (!sharedPreferences.contains(password))
        {
            if(!_password.equals(sharedPreferences.getString(password, "")))
                editor.putString(password, _password);
        }
        editor.commit();
    }
    private void popUserData()
    {
        sharedPreferences = getSharedPreferences(userPreference,
                Context.MODE_PRIVATE);
        if (sharedPreferences.contains(userName))
        {
            txtUserName.setText(sharedPreferences.getString(userName, ""));
        }
        if (sharedPreferences.contains(password)) {
            txtPassword.setText(sharedPreferences.getString(password, ""));
        }
    }
    private void populateConfigurations(Configuration configuration)
    {
        Config config;
        if(configuration != null)
        {
            config = CommonElements.getSystemConfiguration(Constants.ENABLE_CHILD_POLICY);
            CHILD_POLICY_ENABLED = config != null && config.getCurrentValue().toLowerCase().equals("true");

            config = CommonElements.getSystemConfiguration(Constants.CHILDREN_DISCOUNT);
            CHILD_DISCOUNT = config != null && !config.getCurrentValue().isEmpty() ? Double.parseDouble(config.getCurrentValue()) : 0.0;
        }
    }
}
