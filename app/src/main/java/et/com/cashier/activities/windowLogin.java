package et.com.cashier.activities;

import androidx.appcompat.app.AppCompatActivity;
import et.com.cashier.R;
import et.com.cashier.model.Company;
import et.com.cashier.network.retrofit.pojo.User;
import et.com.cashier.network.retrofit.pojo.UserInformation;
import et.com.cashier.network.volley.HttpHandler;
import et.com.cashier.network.retrofit.API;
import et.com.cashier.network.retrofit.post.UserCredential;
import et.com.cashier.utilities.windowProgress;
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

import org.json.JSONObject;

public class windowLogin extends AppCompatActivity
{
    private UserInformation userInformation;
    private EditText txtUserName, txtPassword;
    private Button btnLogin;
    private CheckBox checkBoxRemember;

    private String jsonStr;
    private static String urlLogin = "http://192.168.1.155:8101/Authentication/loginuser?username=%s&password=%s";

    private SharedPreferences sharedPreferences;
    private static final String userPreference = "userPreference";
    private static final String userName = "userNameKey";
    private static final String password = "passwordKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_login);

        init();
    }

    private void init() {
        btnLogin = findViewById(R.id.btnLogin);
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        checkBoxRemember = findViewById(R.id.cbxRemember);

        PopUserData();
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

                    //new GetUser().execute(params);
                    GetUser_(userName, password);
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
    private void GetUser_(String userName, final String password)
    {
        final windowProgress progress = windowProgress.getInstance();
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
                            Intent intent = new Intent(windowLogin.this, windowDashboard.class);
                            intent.putExtra("user", userInformation.getUser());
                            intent.putExtra("company", userInformation.getCompany());
                            intent.putExtra("token", userInformation.getToken());

                            if(checkBoxRemember.isChecked())
                                RememberUser(txtUserName.getText().toString(), txtPassword.getText().toString());
                            startActivity(intent);
                        }
                        else if(response.code() == 401)
                        {
                            View rootLayout = findViewById(R.id.rootLayout);
                            Snackbar snackbar = Snackbar
                                    .make(rootLayout, Html.fromHtml("<B>Unauthorized user</B><Br/>username or password incorrect"), Snackbar.LENGTH_LONG);
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
    private void PopUserData()
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
    private class GetUser extends AsyncTask<String[], Void, UserInformation>
    {
        private UserInformation getUser(String userName, String password)
        {
            UserInformation userInformation = new UserInformation();
            HttpHandler httpHandler = new HttpHandler();

            String formattedURL;
            String newURL;

            formattedURL = String.format(urlLogin, userName, password);
            newURL = formattedURL.replaceAll(" ", "%20");
            String[] response = httpHandler.makeServiceCall(newURL);

            if(response != null)
            {
                int statusCode = Integer.parseInt(response[0]);
                userInformation.setApproved(statusCode);
                if(statusCode == 200)
                {
                    jsonStr = response[1];
                    if(jsonStr != null && !jsonStr.toLowerCase().equals("null\n"))
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            userInformation.setToken(jsonObject.getString("token"));
                            JSONObject jsonObjectUser = jsonObject.getJSONObject("user");
                            if(jsonObjectUser != null)
                            {
                                User user = new User();
                                user.setId(jsonObjectUser.getString("id"));
                                user.setName(jsonObjectUser.getString("name"));
                                user.setDob(jsonObjectUser.getString("dob"));
                                user.setTitle(jsonObjectUser.getString("title"));
                                user.setGender(jsonObjectUser.getString("gender"));
                                user.setPosition(jsonObjectUser.getString("position"));
                                user.setActive(jsonObjectUser.getBoolean("isActive"));

                                userInformation.setUser(user);
                            }
                            JSONObject jsonObjectCompany = jsonObject.getJSONObject("company");
                            if(jsonObjectCompany != null)
                            {
                                Company company = new Company();
                                company.setBrandName(jsonObjectCompany.getString("brandName"));
                                company.setTradeName(jsonObjectCompany.getString("tradeName"));

                                userInformation.setCompany(company);
                            }
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                }
                else
                {
                    userInformation.setApproved(0);
                }
            }
            return userInformation;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            windowProgress progress = windowProgress.getInstance();
            progress.showProgress(windowLogin.this, "Logging in", false);
        }

        @Override
        protected UserInformation doInBackground(String[]... voids)
        {
            String[] parameters = voids[0];
            return getUser(parameters[0], parameters[1]);
        }

        @Override
        protected void onPostExecute(UserInformation userInformation)
        {
            super.onPostExecute(userInformation);
            if(userInformation != null && userInformation.getApproved() == 200)
            {
                Intent intent = new Intent(windowLogin.this, windowDashboard.class);
                intent.putExtra("user", userInformation.getUser());
                intent.putExtra("company", userInformation.getCompany());

                windowProgress progress = windowProgress.getInstance();
                progress.hideProgress();

                startActivity(intent);
            }
            else if(userInformation != null && userInformation.getApproved() == 0)
            {
                windowProgress progress = windowProgress.getInstance();
                progress.hideProgress();

                View rootLayout = findViewById(R.id.rootLayout);
                Snackbar snackbar = Snackbar
                        .make(rootLayout, Html.fromHtml("<B>Unauthorized user</B><Br/>username or password incorrect"), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            else
            {
                windowProgress progress = windowProgress.getInstance();
                progress.hideProgress();

                View rootLayout = findViewById(R.id.rootLayout);
                Snackbar snackbar = Snackbar
                        .make(rootLayout, Html.fromHtml("<B>Error</B><Br/>error occurred while fetching data from server"), Snackbar.LENGTH_LONG);
                snackbar.show();
                return;
            }
        }
    }
}
