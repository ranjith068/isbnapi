package whatnowtravel.com.isbndb.rest;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import whatnowtravel.com.isbndb.Book;
import whatnowtravel.com.isbndb.R;
import whatnowtravel.com.isbndb.rest.exceptions.NoRestException;
import whatnowtravel.com.isbndb.rest.json.ParseBooksResult;


public class HttpCall{

    private Context context;

    public HttpCall(Context context){

        this.context = context;
        ISBN_URI = "http://isbndb.com/api/v2/json/"+context.getString(R.string.isbnkey)+"/books?q=";
    }


    public static final String BASE_URL = "http://isbndb.com";
    public String ISBN_URI ;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public String GetBooks(String word) throws NoRestException {

        StringBuilder sb = new StringBuilder();
        try {

            URL url = new URL(ISBN_URI +word);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new NoRestException();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            throw new NoRestException();

        } catch (IOException e) {
            e.printStackTrace();
            throw  new NoRestException();

        }
        return sb.toString();
    }

}
