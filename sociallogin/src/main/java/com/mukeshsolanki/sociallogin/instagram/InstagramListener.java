package com.mukeshsolanki.sociallogin.instagram;

public interface InstagramListener {

  void onInstagramSignInFail(String errorMessage);

  void onInstagramSignInSuccess(String authToken, String userId);
}
