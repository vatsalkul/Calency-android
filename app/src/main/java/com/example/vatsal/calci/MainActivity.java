package com.example.vatsal.calci;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button zero;
    private Button div;
    private Button mul;
    private Button add;
    private Button sub;
    private Button clr;
    private Button equal;
    private Button convert;
    private TextView display;
    private TextView result;
    private final char ADDITION = '+';
    private final char SUBTRACTION = '-';
    private final char MULTIPLICATION = '*';
    private final char DIVISION = '/';
    private final char EQU = 0;
    private double val1 = Double.NaN;
    private double val2;
    private char ACTION;
    private TextView usd;
    private TextView euro;
    private TextView pound;
    private double inputvalue;
    private String resultCurr;
    private String resultEUR;
    private String resultGBP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = (TextView)findViewById(R.id.linkGitHub);
        text.setMovementMethod(LinkMovementMethod.getInstance());

        setupUIView();

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "0");
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "1");
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "2");
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "3");
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "4");
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "5");
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "6");
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "7");
            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "8");
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText().toString() + "9");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
                ACTION = ADDITION;
                result.setText(String.valueOf(val1) + "+");
                display.setText(null);
            }
        });


        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
                ACTION = SUBTRACTION;
                result.setText(String.valueOf(val1) + "-");
                display.setText(null);
            }
        });

        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
                ACTION = MULTIPLICATION;
                result.setText(String.valueOf(val1) + "*");
                display.setText(null);
            }
        });

        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
                ACTION = DIVISION;
                result.setText(String.valueOf(val1) + "/");
                display.setText(null);
            }
        });

        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
                ACTION = EQU;
                result.setText(result.getText().toString() + String.valueOf(val2) + "=" + String.valueOf(val1));
                display.setText(null);
            }
        });

        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display.getText().length()>0){
                    CharSequence name = display.getText().toString();
                    display.setText(name.subSequence(0, name.length()-1));
                }
                else {
                    val1 = Double.NaN;
                    val2 = Double.NaN;
                    display.setText(null);
                    result.setText(null);
                    usd.setText(null);
                    euro.setText(null);
                    pound.setText(null);
                }
            }
        });

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usd.setText("...");
                euro.setText("...");
                pound.setText("...");

                if(display.getText().toString().trim().length() > 0){
                    String TextValue = display.getText().toString();
                    inputvalue = Double.parseDouble(TextValue);

                    new calculate().execute();
                    new calculateEUR().execute();
                    new calculatePound().execute();
                }
            }
        });
    }

    private void setupUIView(){
        zero = (Button)findViewById(R.id.b0);
        one = (Button)findViewById(R.id.b1);
        two = (Button)findViewById(R.id.b2);
        three = (Button)findViewById(R.id.b3);
        four = (Button)findViewById(R.id.b4);
        five = (Button)findViewById(R.id.b5);
        six = (Button)findViewById(R.id.b6);
        seven = (Button)findViewById(R.id.b7);
        eight = (Button)findViewById(R.id.b8);
        nine = (Button)findViewById(R.id.b9);
        add = (Button)findViewById(R.id.badd);
        sub = (Button)findViewById(R.id.bsub);
        div = (Button)findViewById(R.id.bdiv);
        mul = (Button)findViewById(R.id.bmul);
        clr = (Button)findViewById(R.id.bclr);
        equal = (Button)findViewById(R.id.bequals);
        display = (TextView)findViewById(R.id.tvDisp);
        result = (TextView)findViewById(R.id.tvRes);
        usd = (TextView)findViewById(R.id.tvusd);
        euro = (TextView)findViewById(R.id.tveur);
        pound = (TextView)findViewById(R.id.tvpound);
        convert = (Button)findViewById(R.id.bconvert);

    }
    private void compute(){
        if(!Double.isNaN(val1)){
            val2 = Double.parseDouble(display.getText().toString());

            switch (ACTION){
                case ADDITION:
                   val1= val1+val2;
                   break;
                case SUBTRACTION:
                    val1= val1-val2;
                    break;
                case DIVISION:
                    val1= val1/val2;
                    break;
                case MULTIPLICATION:
                    val1= val1*val2;
                    break;
                case EQU:
                    break;
            }
        }
        else{
            val1 = Double.parseDouble(display.getText().toString());
        }
    }

    public class calculate extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String strings) {
            double inrtousd, inrtousdres;
            inrtousd = Double.parseDouble(resultCurr);
            inrtousdres = inputvalue * inrtousd;
            usd.setText(""+ String.format("%.2f", inrtousdres)+" $");

        }

        @Override
        protected String doInBackground(String... strings) {
            String uRl;
            try {
                uRl = getJson("https://free.currconv.com/api/v7/convert?q=INR_USD&compact=ultra&apiKey=your-api-key");
                JSONObject INRtoObj;
                INRtoObj = new JSONObject(uRl);
                resultCurr = INRtoObj.getString("INR_USD");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return resultCurr;



        }

        public String getJson(String url) throws ClientProtocolException, IOException{

            StringBuilder build = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String con;
            while ((con = reader.readLine()) != null){
                build.append(con);
            }
            return build.toString();
        }
    }

    public class calculateEUR extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String strings) {
            double inrtousd, inrtousdres;
            inrtousd = Double.parseDouble(resultEUR);
            inrtousdres = inputvalue * inrtousd;
            euro.setText(""+ String.format("%.2f", inrtousdres)+ " €");

        }

        @Override
        protected String doInBackground(String... strings) {
            String uRl;
            try {
                uRl = getJson("https://free.currconv.com/api/v7/convert?q=INR_EUR&compact=ultra&apiKey=your-api-key");
                JSONObject INRtoObj;
                INRtoObj = new JSONObject(uRl);
                resultEUR = INRtoObj.getString("INR_EUR");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resultEUR;



        }

        public String getJson(String url) throws ClientProtocolException, IOException{

            StringBuilder build = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String con;
            while ((con = reader.readLine()) != null){
                build.append(con);
            }
            return build.toString();
        }
    }

    public class calculatePound extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String strings) {
            double inrtousd, inrtousdres;
            inrtousd = Double.parseDouble(resultGBP);
            inrtousdres = inputvalue * inrtousd;
            pound.setText(""+ String.format("%.2f", inrtousdres)+ " £");

        }

        @Override
        protected String doInBackground(String... strings) {
            String uRl;
            try {
                uRl = getJson("https://free.currconv.com/api/v7/convert?q=INR_GBP&compact=ultra&apiKey=your-api-key");
                JSONObject INRtoObj;
                INRtoObj = new JSONObject(uRl);
                resultGBP = INRtoObj.getString("INR_GBP");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resultGBP;



        }

        public String getJson(String url) throws ClientProtocolException, IOException{

            StringBuilder build = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String con;
            while ((con = reader.readLine()) != null){
                build.append(con);
            }
            return build.toString();
        }
    }
}
