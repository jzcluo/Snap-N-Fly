package com.example.zluo.snap_and_fly;

import android.content.ClipboardManager;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.zluo.snap_and_fly.HttpService.CVClient;
import com.example.zluo.snap_and_fly.HttpService.GeocodingService;
import com.example.zluo.snap_and_fly.RequestModel.BodyUrl;
import com.example.zluo.snap_and_fly.ResponseModel.GeocodingResponse;
import com.example.zluo.snap_and_fly.ResponseModel.MSCVResponse;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ImageActivity extends AppCompatActivity {


    private static final String MSCVAPI= "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/models/landmarks/";
    private static final String subscriptionKey = "694bdd8396634097a0e91db00327d558";

    private static final String GOOGLEAPI = "https://maps.googleapis.com/maps/api/geocode/";
    private static final String GOOGLEKEY = "AIzaSyDVNpBQ4IM_gVb-CHXE30XsPaD4yb-DSWg";

    private ImageView imageView;
    private TextView cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.imageView);
        cityName = (TextView) findViewById(R.id.name);

        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        String textOnClipboard = clipboard.getPrimaryClip().getItemAt(0).getText().toString();

        String image_url = null;
        //Toast.makeText(getApplicationContext(), textOnClipboard, Toast.LENGTH_LONG).show();
        Log.d("EDEbugg", textOnClipboard);
        if (textOnClipboard.matches("^http.*\\.jpg$")) {
            image_url = textOnClipboard;
        } else {
            Toast.makeText(this, "Please copy a url to your to your clipboard first", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
        }
        Log.d("EDEbuggimageurl", "FEEF" + image_url);

        getLandmarkName(image_url);
    }

    private void getLandmarkName(String url) {

        Picasso.with(getApplicationContext()).load(url).into(imageView);

        Retrofit mscvRetrofit = new Retrofit.Builder().
                baseUrl(MSCVAPI).
                addConverterFactory(GsonConverterFactory.create()).
                build();

        CVClient computerVisionClient = mscvRetrofit.create(CVClient.class);

        BodyUrl bodyUrl = new BodyUrl(url);

        Call<MSCVResponse> mscvResponse = computerVisionClient.getLandmark(subscriptionKey, "application/json", bodyUrl);


        mscvResponse.enqueue(new Callback<MSCVResponse>() {
            @Override
            public void onResponse(Call<MSCVResponse> call, Response<MSCVResponse> response) {
                Log.d("DEbug", response.toString());
                if (response.body() != null) {
                    Log.d("DEbug", response.toString());
                    String landmarkName = response.body().getResult().getLandmarks()[0].getName();
                    getCityName(landmarkName);
                }
            }

            @Override
            public void onFailure(Call<MSCVResponse> call, Throwable t) {
                Log.d("error", t.toString());
            }
        });
    }

    private void getCityName(final String landmarkName) {
        Log.d("debug", "In get city name");
        Retrofit geocodingRetrofit = new Retrofit.Builder().
                baseUrl(GOOGLEAPI).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        GeocodingService geocodingService = geocodingRetrofit.create(GeocodingService.class);
        Call<GeocodingResponse> geocodingResponse = geocodingService.getCityName(landmarkName.replaceAll("\\s", "\\+"), GOOGLEKEY);
        Log.d("Debug", landmarkName);
        geocodingResponse.enqueue(new Callback<GeocodingResponse>() {
            @Override
            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                Log.d("DEbug response", response.toString());
                GeocodingResponse.Result.Address[] result = response.body().getResults()[0].getAddress_components();
                String destinationCity = null;
                Log.d("Debug",result.toString());
                for (GeocodingResponse.Result.Address address : result) {
                    if (address.getTypes()[0].equals("locality")) {
                        destinationCity = address.getShort_name();
                    }
                }
                /*
                if (destinationCity != null) {
                    Intent intent = new Intent(ImageActivity.this, WebPage.class);
                    intent.putExtra("cityName", destinationCity);
                    startActivity(intent);
                } else
                */
                if (destinationCity == null) {
                    Toast.makeText(getApplicationContext(), "Sorry we could not locate this place", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ImageActivity.this, MainActivity.class));
                }

                cityName.setText(landmarkName + "\n" + destinationCity);

                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                final String finalDestinationCity = destinationCity;
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ImageActivity.this, WebPage.class);
                        intent.putExtra("cityName", finalDestinationCity);
                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onFailure(Call<GeocodingResponse> call, Throwable t) {

            }
        });
    }






}
