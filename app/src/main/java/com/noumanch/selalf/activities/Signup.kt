package com.noumanch.selalf.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.ybq.android.spinkit.SpinKitView
import com.jaychang.sa.AuthCallback
import com.jaychang.sa.SimpleAuth
import com.jaychang.sa.SocialUser
import com.noumanch.selalf.R
import com.noumanch.selalf.utils.StaticVariables

import org.json.JSONException
import org.json.JSONObject
import org.json.XML
import org.xml.sax.XMLReader

import java.io.UnsupportedEncodingException
import java.util.Arrays
import java.util.HashMap
import java.util.Locale

import butterknife.OnClick

import fr.arnaudguyon.xmltojsonlib.XmlToJson

class Signup : AppCompatActivity()/* ,GoogleListener*/ {

     var lastname: EditText?=null
     var firstname: EditText?=null
     var email: EditText?=null
    var passwd: EditText?=null
     var signup: Button?=null
    //var googlerhelper: GoogleHelper?=null

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
        setContentView(R.layout.activity_signup)

//        googlerhelper = GoogleHelper(this, this, null)
        lastname = findViewById(R.id.last_name)
        spinKitView = findViewById(R.id.spin_kit)
        firstname = findViewById(R.id.first_name)
        email = findViewById(R.id.email)
        passwd = findViewById(R.id.passwd)
        signup = findViewById(R.id.signin_btn)

        signup!!.setOnClickListener {
            if (!TextUtils.isEmpty(lastname!!.text.toString()) && !TextUtils.isEmpty(firstname!!.text.toString())
                    && !TextUtils.isEmpty(email!!.text.toString()) && !TextUtils.isEmpty(passwd!!.text.toString())) {
                doTask("")
            } else {
                Toast.makeText(this@Signup, "Enter the Data ", Toast.LENGTH_SHORT).show()
            }
        }
        forPasswordEditText()
    }

    fun forPasswordEditText() {
        // Workaround https://issuetracker.google.com/issues/37082815 for Android 4.4+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isRTL(this@Signup)) {

            // Force a right-aligned text entry, otherwise latin character input,
            // like "abc123", will jump to the left and may even disappear!
            passwd!!.textDirection = View.TEXT_DIRECTION_RTL

            // Make the "Enter password" hint display on the right hand side
            passwd!!.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        }

        passwd!!.addTextChangedListener(object : TextWatcher {

            internal var inputTypeChanged: Boolean = false

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {

                // Workaround https://code.google.com/p/android/issues/detail?id=201471 for Android 4.4+
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isRTL(this@Signup)) {
                    if (s.length > 0) {
                        if (!inputTypeChanged) {

                            // When a character is typed, dynamically change the EditText's
                            // InputType to PASSWORD, to show the dots and conceal the typed characters.
                            passwd!!.inputType = InputType.TYPE_CLASS_TEXT or
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD or
                                    InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

                            // Move the cursor to the correct place (after the typed character)
                            passwd!!.setSelection(s.length)

                            inputTypeChanged = true
                        }
                    } else {
                        // Reset EditText: Make the "Enter password" hint display on the right
                        passwd!!.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

                        inputTypeChanged = false
                    }
                }
            }
        })


    }

    /**
     * send the attendence
     */
    fun doTask(json: String) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val drawable = ProgressBar(this).indeterminateDrawable.mutate()
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN)
        }

        spinKitView!!.visibility = View.VISIBLE

        val url = resources.getString(R.string.signup_url)

        val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener { response ->
                    Log.i("RESPONSE", "" + response)

                    try {
                        val xmlToJson = XmlToJson.Builder(response).build()
                        // convert to a JSONObject
                        val jsonObject = xmlToJson.toJson()

                        val prestashop = jsonObject!!.getJSONObject("prestashop")
                        val customer = prestashop.getJSONObject("customer")
                        val email = customer.getString("email")
                        val password = passwd!!.text.toString()
                        val i = Intent()
                        i.putExtra("email", email)
                        i.putExtra("pass", password)
                        setResult(RESULT_OK, i)
                        finish()
                        Log.wtf("TEST", "onResponse: " + jsonObject.toString())
                        Toast.makeText(this@Signup, "Account Created Sucessfully", Toast.LENGTH_SHORT).show()
                        if (spinKitView != null)
                            spinKitView!!.visibility = View.GONE

                        if (spinKitView != null)
                            spinKitView!!.visibility = View.GONE
                    } catch (e: Exception) {
                        e.printStackTrace()
                        if (spinKitView != null)
                            spinKitView!!.visibility = View.GONE
                        Toast.makeText(this@Signup, "Fail to Create Account", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener {
                    spinKitView!!.visibility = View.GONE

                    Toast.makeText(this@Signup, "Internet is Not working correctly", Toast.LENGTH_SHORT).show()
                }) {

            override fun getBodyContentType(): String {
                return "application/xml; charset=" + paramsEncoding
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray? {
                val postData = /*"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +*/
                        "<prestashop xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                                "<customer>\n" +
                                "<passwd>" + passwd!!.text.toString() +
                                "</passwd>\n" +
                                "<lastname>" + lastname!!.text.toString() +
                                "</lastname>\n" +
                                "<firstname>" + firstname!!.text.toString() +
                                "</firstname>\n" +
                                "<email>" + email!!.text.toString() +
                                "</email><id_default_group>3</id_default_group>\n" +
                                "</customer>\n" +
                                "</prestashop>"
                Log.wtf("TEst", "getBody: " + postData)
                try {
                    return postData?.toByteArray(charset(paramsEncoding))
                } catch (uee: UnsupportedEncodingException) {

                    return null
                }

            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
        return

    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        //googlerhelper!!.onActivityResult(requestCode, resultCode, data)

    }


    // EVENT CLICK BUTTON SIMPLE
    fun loginButtonGoogle(view: View) {
        //googlerhelper!!.performSignIn(this@Signup)

    }


    private fun toast(msg: String) {
        Toast.makeText(this@Signup, msg, Toast.LENGTH_LONG).show()
    }

    fun loginButtonLinken(view: View) {

    }

    fun loginButtonFB(view: View) {

        val scopes = Arrays.asList("user_birthday", "user_friends")

        /*        SimpleAuthFacebookKt.connectFacebook(scopes, new AuthCallback() {
            @Override
            public void onSuccess(SocialUser socialUser) {

                Toast.makeText(Signup.this, ""+socialUser.fullName, Toast.LENGTH_SHORT).show();
                //ProfileActivity.start(MainActivity.this, FACEBOOK, socialUser);
            }

            @Override
            public void onError(Throwable error) {
                toast(error.getMessage());
            }

            @Override
            public void onCancel() {
                toast("Canceled");
            }
        });*/
    }

    fun loginButtonTwitter(view: View) {
       /* SimpleAuth.connectTwitter(object : AuthCallback {
            override fun onSuccess(socialUser: SocialUser) {
                toast("Login sucess fully "+socialUser.fullName)
                //ProfileActivity.start(this@MainActivity, TWITTER, socialUser)
            }

            override fun onError(error: Throwable) {
                toast(error.message ?: "")
            }

            override fun onCancel() {
                toast("Canceled")
            }
        })*/
        /*SimpleAuth.getInstance().connectTwitter(new AuthCallback() {
            @Override
            public void onSuccess(SocialUser socialUser) {
                //ProfileActivity.start(MainActivity.this, TWITTER, socialUser);
                Toast.makeText(Signup.this, ""+socialUser.fullName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable error) {
                toast(error.getMessage());
            }

            @Override
            public void onCancel() {
                toast("Canceled");
            }
        });*/

    }

    companion object {

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

