package fr.univtln.sgrassell418.restapp.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by stephane on 04/04/16.
 */
public class ApiEventManager {

    private static ApiEventManager INSTANCE = null;
    private final static String apiURL = "http://10.42.4.118:9998/event/";
    private static Context context;

    public static ApiEventManager getINSTANCE(Context context){
        if(INSTANCE == null)
            INSTANCE = new ApiEventManager();
        INSTANCE.context = context;
        return INSTANCE;
    }

    public Event getEventById(int id) throws IOException, ExecutionException, InterruptedException {
        String response = new CallAPI_get().execute(Integer.toString(id)).get();
        Log.i("Server_Return", response);
        return Event.jsonToEvent(response);
    }

    public List<Event> getEventByActivity(String activity) throws IOException, ExecutionException, InterruptedException {
        String response = new CallAPI_get().execute(activity).get();
        Log.i("Server_Return", response);

        List<Event> events = new ArrayList<>();

        String[] parts = response.split("\n");
        for(String json : parts){
            events.add(Event.jsonToEvent(json));
        }

        return events;
    }

    public List<Event> getNextEvents(int nbEvents) throws IOException, ExecutionException, InterruptedException {
        String response = new CallAPI_get().execute("next/"+nbEvents).get();
        Log.i("Server_Return", response);

        List<Event> events = new ArrayList<>();

        String[] parts = response.split("\n");
        for(String json : parts){
            events.add(Event.jsonToEvent(json));
        }

        return events;
    }

    public String deleteEventbyId(int id) throws IOException, ExecutionException, InterruptedException {
        String response = new CallAPI_delete().execute(Integer.toString(id)).get();
        Log.i("Server_Return", response);
        return response;
    }

    public String putEvent(Event event) throws IOException, ExecutionException, InterruptedException {
        String response = new CallAPI_put().execute(event.mapToJson()).get();
        Log.i("Server_Return", response);
        return response;
    }

    public String postEvent(Event event) throws IOException, ExecutionException, InterruptedException {
        String response = new CallAPI_post().execute(event.mapToJson()).get();
        Log.i("Server_Return", response);
        return response;
    }




    // API REST GET METHOD Parameter has to be a string-parsed int)
    private class CallAPI_get extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String response = "";

            InputStream in = null;
            byte[] buffer = new byte[1024];

            try {
                URL url = new URL(apiURL+params[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                in = new BufferedInputStream(urlConnection.getInputStream());
                int bytesRead=0;

                while( (bytesRead = in.read(buffer)) != -1){
                    response = new String(buffer, 0, bytesRead);
                }

            } catch (Exception e ) {
                Log.e("ERROR_CallAPI_get", e.toString());
                return e.getMessage();
            }

            return response;
        }
    }

    // API REST DELETE METHOD (Parameter has to be a string-parsed int)
    private class CallAPI_delete extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String response = "";

            InputStream in = null;
            byte[] buffer = new byte[1024];

            try {
                URL url = new URL(apiURL+params[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("DELETE");

                in = new BufferedInputStream(urlConnection.getInputStream());
                int bytesRead = 0;

                while ((bytesRead = in.read(buffer)) != -1) {
                    response = new String(buffer, 0, bytesRead);
                }

            } catch (Exception e ) {
                Log.e("ERROR_CallAPI_delete", e.toString());
                return e.getMessage();
            }

            return response;
        }
    }

    // API REST PUT METHOD (Parameter has to be JSON string)
    private class CallAPI_put extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            String json = params[0]; // URL to call

            String response = "";

            InputStream in = null;
            byte[] buffer = new byte[1024];

            try {

                URL url = new URL(apiURL);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Content-type", "application/json");
                urlConnection.getOutputStream().write(json.getBytes("UTF-8"));

                in = new BufferedInputStream(urlConnection.getInputStream());
                int bytesRead = 0;

                while ((bytesRead = in.read(buffer)) != -1) {
                    response = new String(buffer, 0, bytesRead);
                }
            } catch (Exception e ) {
                Log.e("ERROR_CallAPI_put", e.toString());
                return e.getMessage();
            }

            return response;
        }
    }

    // API REST POST METHOD (Parameter has to be JSON string)
    private class CallAPI_post extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            String json = params[0]; // URL to call

            String response = "";

            InputStream in = null;
            byte[] buffer = new byte[1024];

            try {

                URL url = new URL(apiURL);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-type", "application/json");
                urlConnection.getOutputStream().write(json.getBytes("UTF-8"));

                in = new BufferedInputStream(urlConnection.getInputStream());
                int bytesRead = 0;

                while ((bytesRead = in.read(buffer)) != -1) {
                    response = new String(buffer, 0, bytesRead);
                }
            } catch (Exception e ) {
                Log.e("ERROR_CallAPI_post", e.toString());
                return e.getMessage();
            }

            return response;
        }
    }


}
