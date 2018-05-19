package com.noumanch.selalf.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.PorterDuff
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInApi
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
/*import com.google.code.linkedinapi.client.LinkedInApiClient
import com.google.code.linkedinapi.client.LinkedInApiClientFactory
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken*/
import com.jaychang.sa.AuthCallback
import com.jaychang.sa.SimpleAuth
import com.jaychang.sa.SimpleAuthActivity
import com.jaychang.sa.SocialUser
import com.noumanch.selalf.R
import com.noumanch.selalf.utils.Config
import com.noumanch.selalf.utils.StaticVariables
import org.json.JSONException
import org.json.JSONObject
import java.util.Arrays
import java.util.HashMap
import java.util.Locale

import com.noumanch.selalf.activities.Signup.Companion.isRTL
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import com.twitter.sdk.android.core.models.User
import java.lang.NullPointerException

import com.linkedin.platform.APIHelper
import com.linkedin.platform.LISessionManager
import com.linkedin.platform.errors.LIApiError
import com.linkedin.platform.errors.LIAuthError
import com.linkedin.platform.listeners.ApiListener
import com.linkedin.platform.listeners.ApiResponse
import com.linkedin.platform.listeners.AuthListener
import com.linkedin.platform.utils.Scope
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import java.io.UnsupportedEncodingException

class Login : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private var callbackManager: CallbackManager? = null
    private var loginButton: LoginButton? = null
    private var l_Button: Button? = null

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("check", "Connection Failed" + p0.errorCode + "\t " + p0.toString())
    }

    private var signin_btn: Button? = null
    private val mGoogleSignInClient: GoogleSignInApi? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private var continueAsGuestTV: TextView? = null
    private var forgot_password: TextView? = null

    private var tloginButton: TwitterLoginButton? = null
    private var firstname: String? = null
    private var lastname: String? = null
    private var tpassword: String? = null
    private var temail: String? = null

    var password: EditText? = null
    var email: EditText? = null

    internal var spinKitView: SpinKitView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!StaticVariables.language) {

            val languageToLoad = "ar" // your language
            val locale = Locale(languageToLoad)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            baseContext.resources.updateConfiguration(config,
                    baseContext.resources.displayMetrics)

        }
        //SignUp("")
        FacebookSdk.sdkInitialize(Login@ this);
        Twitter.initialize(this)
        val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(TwitterAuthConfig(resources.getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),
                        resources.getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build()
        Twitter.initialize(config)
        setContentView(R.layout.activity_login2)
        setupGoogleAPI()

        spinKitView = findViewById(R.id.spin_kit)
        callbackManager = CallbackManager.Factory.create()

        loginButton = findViewById<View>(R.id.login_button) as LoginButton
        loginButton!!.setReadPermissions(Arrays.asList(EMAIL))

        tloginButton = findViewById<View>(R.id.logi_button) as TwitterLoginButton
        // If you are using in a fragment, call loginButton.setFragment(this);
        tloginButton!!.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>) {
                // Do something with result, which provides a TwitterSession for making API calls
                Log.e("result", "result $result")
                val twitterApiClient = TwitterCore.getInstance().apiClient
                val accountService = twitterApiClient.accountService
                val call = accountService.verifyCredentials(true, true, true)
                call.enqueue(object : Callback<User>() {
                    override fun success(result: Result<User>) {
                        //here we go User details
                        firstname = result.data.screenName
                        lastname = result.data.name
                        tpassword = "twitter"
                        temail = result.data.email
                        SignUp(firstname!!, lastname!!, tpassword!!, temail!!)
                        //StaticVariables.setCurrentUserId(this@Login, result.data.id.toString(), result.data.screenName, result.data.name, "", result.data.email)

                        //Log.e("result", "result user " + result.data.idStr)
                        /*val imageUrl = result.data.profileImageUrl
                        val email = result.data.email
                        val userName = result.data.name*/
                        Log.e("result", "result user " + result.data.name)

                    }

                    override fun failure(exception: TwitterException) {
                        exception.printStackTrace()
                    }
                })
            }

            override fun failure(exception: TwitterException) {
                // Do something on failure
                exception.printStackTrace()
            }
        }
        // Callback registration
        loginButton!!.registerCallback(callbackManager!!,
                object : FacebookCallback<LoginResult> {
                    override fun onError(error: FacebookException?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onCancel() {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onSuccess(result: LoginResult?) {
                        Log.i("check", "JSON" + result.toString())
                    }
                })



        LoginManager.getInstance().registerCallback(callbackManager!!,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        // App code
                        Log.i("check", "On suucess Login Manager" + loginResult.accessToken)
                        val request = GraphRequest.newMeRequest(
                                loginResult.accessToken
                        ) { `object`, response ->
                            // Insert your code here
                            try {
                                firstname = `object`.get("first_name").toString()
                                lastname = `object`.get("last_name").toString()
                                tpassword = "facebook"
                                temail = `object`.get("email").toString()
                                SignUp(firstname!!, lastname!!, tpassword!!, temail!!)
                                //StaticVariables.setCurrentUserId(this@Login, `object`.get("id").toString(), `object`.get("last_name").toString(), `object`.get("first_name").toString(), "", `object`.get("email").toString())
                                //StaticVariables.setIndicatorOfLogin(true)
                                /*val i = Intent(this@Login, Dashboard::class.java)
                                i.putExtra("id", `object`.get("id").toString())
                                startActivity(i)*/
                                Log.i("check", "JSON" + `object`.toString())
                                Log.i("check", "On suucesse Login Manager=" + `object`.get("id").toString())
                                Log.i("check", "On suucesse Login Manager=" + `object`.get("name").toString())
                                Log.i("check", "On suucesse Login Manager=" + `object`.get("email").toString())
                                Log.i("check", "On suucesse Loggin Manager=" + `object`.get("first_name").toString())
                                Log.i("check", "On suucesse Loggin Manager=" + `object`.get("last_name").toString())
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }

                        val parameters = Bundle()
                        parameters.putString("fields", "id,name,email,first_name,last_name")
                        request.parameters = parameters
                        request.executeAsync()
                    }

                    override fun onCancel() {
                        // App code
                    }

                    override fun onError(exception: FacebookException) {
                        // App code
                    }
                })
        email = findViewById<EditText>(R.id.etUserName) as EditText
        password = findViewById<EditText>(R.id.passwd_et) as EditText
        continueAsGuestTV = findViewById<View>(R.id.continue_as_guest) as TextView
        signin_btn = findViewById<View>(R.id.signin_btn) as Button
        var fb_btn = findViewById<ImageView>(R.id.fb_btn)
        forgot_password = findViewById<View>(R.id.forgot_password) as TextView
        languageSetup()
        val content = SpannableString(getString(R.string.u_continue_as_guest_u))
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        continueAsGuestTV!!.text = content


        signin_btn!!.setOnClickListener {
            doTask()
            //startActivity(new Intent(Login.this,Dashboard.class));
        }
        continueAsGuestTV!!.setOnClickListener {
            StaticVariables.setIndicatorOfLogin(this@Login,false)
            startActivity(Intent(this@Login, Dashboard::class.java))
            //finish();
            //startActivity(new Intent(Login.this,Dashboard.class));
        }
        forgot_password!!.setOnClickListener { startActivity(Intent(this@Login, ForgotPassword::class.java)) }
        forPasswordEditText()
        fb_btn.setOnClickListener {
            //loginButtonFB()
            loginButton!!.performClick()
        }
    }

    private fun setupGoogleAPI() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    fun forPasswordEditText() {
        // Workaround https://issuetracker.google.com/issues/37082815 for Android 4.4+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isRTL(this@Login)) {

            // Force a right-aligned text entry, otherwise latin character input,
            // like "abc123", will jump to the left and may even disappear!
            password!!.textDirection = View.TEXT_DIRECTION_RTL

            // Make the "Enter password" hint display on the right hand side
            password!!.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        }

        password!!.addTextChangedListener(object : TextWatcher {

            internal var inputTypeChanged: Boolean = false

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {

                // Workaround https://code.google.com/p/android/issues/detail?id=201471 for Android 4.4+
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isRTL(this@Login)) {
                    if (s.length > 0) {
                        if (!inputTypeChanged) {

                            // When a character is typed, dynamically change the EditText's
                            // InputType to PASSWORD, to show the dots and conceal the typed characters.
                            password!!.inputType = InputType.TYPE_CLASS_TEXT or
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD or
                                    InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

                            // Move the cursor to the correct place (after the typed character)
                            password!!.setSelection(s.length)

                            inputTypeChanged = true
                        }
                    } else {
                        // Reset EditText: Make the "Enter password" hint display on the right
                        password!!.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

                        inputTypeChanged = false
                    }
                }
            }
        })


    }

    private fun languageSetup() {
        if (!StaticVariables.language) {
            //email.setText("ايميل");
            //email.setText("ايميل");
        }
    }

    /** send the attendence */
    fun doTask() {
        /*       final ProgressDialog progressDialog = new ProgressDialog(Login.this) ;
        progressDialog.setMessage(getResources().getString(R.string.progress_text));
        progressDialog.setIndeterminate(true);*/

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val drawable = ProgressBar(this).indeterminateDrawable.mutate()
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN)
            //progressDialog.setIndeterminateDrawable(drawable);
        }
        //progressDialog.setCancelable(false);

        spinKitView!!.visibility = View.VISIBLE

        val url = resources.getString(R.string.login_url)

        val stringRequest = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    Log.i("RESPONSE", "" + response)
                    try {
                        val array = JSONObject(response)
                        val status = array.getBoolean("status")
                        if (status) {

                            val id = array.getString("id")
                            val lastname = array.getString("lastname")
                            val firstname = array.getString("firstname")
                            //String phone = array.getString("phone");
                            val email = array.getString("email")
                            if (spinKitView != null)
                                spinKitView!!.visibility = View.GONE
                            Toast.makeText(this@Login, "Login Sucessfully", Toast.LENGTH_SHORT).show()
                            StaticVariables.setIndicatorOfLogin(this@Login,true)
                            StaticVariables.setCurrentUserId(this@Login, id, lastname, firstname, "", email)
                            val i = Intent(this@Login, Dashboard::class.java)
                            i.putExtra("id", id)
                            startActivity(i)
                        } else {
                            Toast.makeText(this@Login, "Password is incorrect", Toast.LENGTH_SHORT).show()
                        }
                        Log.wtf("Test", "onResponse: " + array.toString())
                        if (spinKitView != null)
                            spinKitView!!.visibility = View.GONE
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        if (spinKitView != null)
                            spinKitView!!.visibility = View.GONE

                        Toast.makeText(this@Login, "Error due to JSON ", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener {
                    if (spinKitView != null)
                        spinKitView!!.visibility = View.GONE
                    Toast.makeText(this@Login, "Internet is Not working correctly", Toast.LENGTH_SHORT).show()
                }) {
            /*@Override
            public String getBodyContentType() {

                //return "application/json; charset=utf-8";

            }*/

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                super.getParams()
                val value = HashMap<String, String>()
                value.put("email", email!!.text.toString())
                value.put("password", password!!.text.toString())
                return value
            }

        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
        return
    }

    fun signUpAction(view: View) {
        startActivity(Intent(this@Login, Signup::class.java))
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        // googlerhelper!!.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                val emailS = data.getStringExtra("email")
                val passwdS = data.getStringExtra("pass")
                email!!.setText(emailS)
                password!!.setText(passwdS)
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                handleSignInResult(result)
            }
        } else {
            tloginButton!!.onActivityResult(requestCode, resultCode, data)
            callbackManager!!.onActivityResult(requestCode, resultCode, data)
            LISessionManager.getInstance(this@Login).onActivityResult(this@Login, requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount
            Toast.makeText(applicationContext, "You are successfully Signed In", Toast.LENGTH_LONG).show()
            try {
                Log.i("check", "NAMe=" + account!!.id + "\n Email:" + account.email)
                firstname = account.displayName
                lastname = account.familyName
                tpassword = "facebook"
                temail = account.email
                SignUp(firstname!!, lastname!!, tpassword!!, temail!!)
                /* StaticVariables.setIndicatorOfLogin(true)
                 StaticVariables.setCurrentUserId(this@Login, account.id, account.familyName, account.displayName, "", account.email)
                 val i = Intent(this@Login, Dashboard::class.java)
                 i.putExtra("id", account.id)
                 startActivity(i)*/
            } catch (e: NullPointerException) {
                Log.i("check", e.toString())
            }

        }
    }


    // EVENT CLICK BUTTON SIMPLE
    fun loginButtonGoogle(view: View) {
        signIn()

    }

    private fun signIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun toast(msg: String) {
        Toast.makeText(this@Login, msg, Toast.LENGTH_LONG).show()
    }


    fun loginButtonLinken(view: View) {
        //linkedInLogin();
//        Toast.makeText(this@Login, "You clicked Linked", Toast.LENGTH_LONG).show()
        handleLogin();
    }

    private fun handleLogin() {
        LISessionManager.getInstance(applicationContext).init(this, buildScope(), object : AuthListener {
            override fun onAuthSuccess() {
                // Authentication was successful.  You can now do
                // other calls with the SDK.

                fetchPersonalInfo()
            }

            override fun onAuthError(error: LIAuthError) {
                // Handle authentication errors
                Log.e("SAFI", error.toString())
            }
        }, true)
    }

    private fun fetchPersonalInfo() {
        val url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,public-profile-url,picture-url,email-address,picture-urls::(original))"
        Log.i("check", "Context:" + applicationContext)
        val apiHelper = APIHelper.getInstance(applicationContext)
        apiHelper.getRequest(this, url, object : ApiListener {
            override fun onApiSuccess(apiResponse: ApiResponse) {
                // Success!
                Log.i("check", "onApiSuccess$apiResponse")
                try {
                    val jsonObject = apiResponse.responseDataAsJson
                    val firstName = jsonObject!!.getString("firstName")
                    val lastName = jsonObject.getString("lastName")
                    val pictureUrl = jsonObject.getString("pictureUrl")
                    val emailAddress = jsonObject.getString("emailAddress")
                    SignUp(firstName, lastName, "linkedin", emailAddress)
                    /*StaticVariables.setCurrentUserId(this@Login, "420", lastName, firstName, "", emailAddress)
                    StaticVariables.setIndicatorOfLogin(true)
                    val i = Intent(this@Login, Dashboard::class.java)
                    i.putExtra("id", "420")
                    startActivity(i)*/
                    Toast.makeText(applicationContext, "Your are signin as: $firstName", Toast.LENGTH_LONG).show()
                    /*
                    Picasso.with(getApplicationContext()).load(pictureUrl).into(imgProfile);
*/

                    /*  val sb = StringBuilder()
                      sb.append("First Name: $firstName")
                      sb.append("\n\n")
                      sb.append("Last Name: $lastName")
                      sb.append("\n\n")
                      sb.append("Email: $emailAddress")*/

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun onApiError(liApiError: LIApiError) {
                // Error making GET request!
                Log.e("SAFI", liApiError.message)
            }
        })
    }

    // Build the list of member permissions our LinkedIn session requires
    private fun buildScope(): Scope {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE, Scope.R_EMAILADDRESS)
    }

    fun connectTwitter() {

    }

    fun twitterLogin(view: View) {
        try {
            tloginButton!!.performClick()
        } catch (ex: Exception) {

        }
    }

    fun SignUp(first: String, last: String, password: String, email: String) {
        Log.i("check", "First-> " + first + " Last->" + last + " Password->" + password + " Email->" + email)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val drawable = ProgressBar(this).indeterminateDrawable.mutate()
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN)
        }

        //spinKitView!!.visibility = View.VISIBLE

        val url = resources.getString(R.string.signup_url)

        val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener { response ->
                    Log.i("check", "Response" + response)

                    try {
                        val xmlToJson = XmlToJson.Builder(response).build()
                        // convert to a JSONObject
                        val jsonObject = xmlToJson.toJson()
                        try {
                            val `object` = JSONObject(jsonObject.toString())
                            //Log.i("check","json= "+object);
                            val object1 = `object`.get("prestashop") as JSONObject
                            val object2 = object1.get("customer") as JSONObject

                            Log.i("check", "ID= " + object2.getString("id").toString())
                            StaticVariables.setCurrentUserId(this@Login, object2.getString("id").toString(),
                                    last, first, "", email);
                            StaticVariables.setIndicatorOfLogin(this@Login,true)

                            val i = Intent(this@Login, Dashboard::class.java)
                            i.putExtra("id", object2.getString("id").toString())
                            startActivity(i)
                        } catch (e: JSONException) {
                            Log.i("check", e.toString())
                            e.printStackTrace()
                        }

                        Log.i("check", "onResponse: " + jsonObject.toString())

                        Toast.makeText(this@Login, "Account Created Sucessfully", Toast.LENGTH_SHORT).show()

                    } catch (e: Exception) {
                        e.printStackTrace()
                        /*          if (spinKitView != null)
                                      spinKitView!!.visibility = View.GONE*/
                        Toast.makeText(this@Login, "Fail to Create Account", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener {
                    /*   spinKitView!!.visibility = View.GONE*/
                    it.printStackTrace();
                    Toast.makeText(this@Login, "Internet is Not working correctly" + it, Toast.LENGTH_SHORT).show()
                }) {

            override fun getBodyContentType(): String {
                return "application/xml; charset=" + paramsEncoding
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray? {
                val postData =
                        "<prestashop xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                                "<customer>\n" +
                                "<passwd>" + password +
                                "</passwd>\n" +
                                "<lastname>" + last +
                                "</lastname>\n" +
                                "<firstname>" + "n" +
                                "</firstname>\n" +
                                "<email>" + email +
                                "</email><id_default_group>3</id_default_group>\n" +
                                "</customer>\n" +
                                "</prestashop>"
                Log.i("check", "getBody: " + postData)
                try {
                    return postData?.toByteArray(charset(paramsEncoding))
                } catch (uee: UnsupportedEncodingException) {
                    Log.i("check", "UnSupported Exception")
                    return null
                }

            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        val socketTimeout = 30000
        val policy = DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        stringRequest.retryPolicy = policy
        requestQueue.add(stringRequest)
        return

    }


    companion object {

        const val FACEBOOK = "FACEBOOK"
        const val GOOGLE = "GOOGLE"
        const val TWITTER = "TWITTER"
        const val INSTAGRAM = "INSTAGRAM"
        private val RC_SIGN_IN = 188
        const val EMAIL = "email"


        fun isRTL(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                context.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
                // Another way:
                // Define a boolean resource as "true" in res/values-ldrtl
                // and "false" in res/values
                // return context.getResources().getBoolean(R.bool.is_right_to_left);
            } else {
                false
            }
        }
    }


}
