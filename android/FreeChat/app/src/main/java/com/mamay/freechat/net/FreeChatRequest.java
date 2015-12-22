package com.mamay.freechat.net;

import android.os.AsyncTask;
import android.util.Log;

import com.mamay.freechat.App;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class FreeChatRequest extends AsyncTask<URL, Void, JSONObject> {

    private URL url;

    public FreeChatRequest(URL url) {
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(URL... params) {
        JSONObject object = null;
        try {
            object = App.getNetworkManager().request(url);
        } catch (IOException e) {
            Log.e("Request error", e.getMessage());
        }

        return object;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }
}
