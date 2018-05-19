package com.mukeshsolanki.sociallogin.google;

public interface GoogleListener {
  void onGoogleAuthSignIn(String authToken, String userId);

  void onGoogleAuthSignInFailed(String errorMessage);

  void onGoogleAuthSignOut();
}
