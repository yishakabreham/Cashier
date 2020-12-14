package et.com.cashier.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import et.com.cashier.R;
import et.com.cashier.model.Company;
import et.com.cashier.model.User;
import et.com.cashier.model.UserInformation;
import et.com.cashier.network.HttpHandler;
import et.com.cashier.utilities.windowProgress;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.Serializable;

public class windowLogin extends AppCompatActivity {
    private EditText txtUserName, txtPassword;
    private Button btnLogin;
    private View root;

    private boolean userAuthenticated = false;
    private String jsonStr;
    private static String urlLogin = "http://192.168.1.155:8101/Authentication/loginuser?username=%s&password=%s";

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
        root = findViewById(android.R.id.content);

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

                    new GetUser().execute(params);
                }
                else
                {
                    View rootLayout = findViewById(R.id.rootLayout);
                    Snackbar snackbar = Snackbar
                            .make(rootLayout, Html.fromHtml("<B>Error</B><Br/>Please type your username and password"), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
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

            jsonStr = httpHandler.makeServiceCall(newURL);
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
            if(userInformation != null)
            {
                Intent intent = new Intent(windowLogin.this, windowDashboard.class);
                intent.putExtra("user", userInformation.getUser());
                intent.putExtra("company", userInformation.getCompany());

                windowProgress progress = windowProgress.getInstance();
                progress.hideProgress();

                startActivity(intent);
            }
        }
    }
}
