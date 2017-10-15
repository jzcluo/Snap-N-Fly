package com.example.zluo.snap_and_fly;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

/**
 * Created by apple on 10/15/17.
 */

public class WebPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webpage);

        FloatingActionButton back = (FloatingActionButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(WebPage.this, MainActivity.class);
                startActivity(myIntent);

            }
        });
        {
            String cityName = getIntent().getStringExtra("cityName").toString();

            WebView webview = (WebView) findViewById(R.id.web);
            webview.loadUrl("https://www.expedia.com/Flights-Search?flight-type=on&starDate=10%2F15%2F2017&endDate=10%2F17%2F2017&_xpid=11905%7C1&mode=search&trip=roundtrip&leg1=from%3AAtlanta%2Cto%3A" + cityName + "%2Cdeparture%3A10%2F15%2F2017TANYT&leg2=from%3Awuhan%2Cto%3AAtlanta%2Cdeparture%3A10%2F15%2F2017TANYT&passengers=children%3A0%2Cadults%3A1%2Cseniors%3A0%2Cinfantinlap%3AY");
        }
    }
}
