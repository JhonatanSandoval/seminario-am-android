package com.academiamoviles.seminario.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MiFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        Log.i("Firebase", "onTokenRefresh");

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("Firebase", "token: " + token);

    }
}
