package vizyonmedya.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import mysources.ImageLoading.IntentIntegrator;
import mysources.ImageLoading.LazyAdapter;

public class MainActivity extends Activity {

    List<String> list;
    GridView grid;
    xmlHandler xmlr;
    private String urunadi = "uadi";
    private String urundetayi1 = "udetayi1";
    private String urundetayi2 = "udetayi2";
    private String urunfiyati = "ufiyati";
    URUNSECENEKLER secenekler;
    ListView liste;
    LazyAdapter lazyadapter;
    ImageButton facebook, twitter, instagram, youtube, pinterest;
    ImageView imageLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animateStart();
        addListenerOnButton();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        new parseMagic().execute();
    }

    @Override
    public void onDestroy() {
        liste.setAdapter(null);
        super.onDestroy();
    }

    class parseMagic extends AsyncTask<String, String, String> {
        String url = "http://www.hasema.com/UPLOAD/urun_expsecenek.xml";
        String response = null;
        ProgressDialog progress = null;
        TextView xmlOutput;
        xmlHandler xmlr = new xmlHandler();
        ArrayList<SECENEK> secList = new ArrayList<SECENEK>();
        ArrayList<String> modelNames = new ArrayList<String>();
        ArrayList<String> modelTexts = new ArrayList<String>();
        Thread[] threads = new Thread[2];
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL _url = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) _url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();

                    SAXParserFactory spf = SAXParserFactory.newInstance();
                    SAXParser sp = spf.newSAXParser();
                    XMLReader xr = sp.getXMLReader();
                    InputStream stream = conn.getInputStream();

                    InputSource is = new InputSource(url);
                    is.setEncoding("UTF-8");

                    sp.parse(is, xmlr);
                    secList = xmlr.getXmlList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //progress.dismiss();
                    modelNames = new ArrayList<String>();
                    modelTexts = new ArrayList<String>();
                    String oldurun = "";
                    for (int i = 0; i < secList.size(); i++) {
                        SECENEK sec = secList.get(i);
                        if (!oldurun.equals(sec.getUrun_Kodu())) {
                            String urnresmi = sec.getUrun_Resmi();
                            String urntext = sec.getUrun_Adi() + " " + sec.getDetay_Kodu_1();
                            modelNames.add(urnresmi);
                            modelTexts.add(urntext);
                        }
                        oldurun = sec.getUrun_Kodu();
                    }
                    String mnamesArray[] = modelNames.toArray(new String[modelNames.size()]);
                    String mtextArray[] = modelTexts.toArray(new String[modelTexts.size()]);
                    Log.w("look at the images!", "");
                    liste = (ListView) findViewById(R.id.listView1);
                    lazyadapter = new LazyAdapter(MainActivity.this, mnamesArray, mtextArray);
                    liste.setAdapter(lazyadapter);


                } catch (Exception exc) {
                }

            }

        });

        @Override
        protected void onPreExecute() {
            threads[0] = thread1;
            threads[1] = thread2;
        }

        @Override
        protected String doInBackground(String... arg0) {

            return "";

        }


        protected void onPostExecute(String ab) {
            try {
                threads[0].start();
                threads[0].join();
                threads[1].start();
                threads[1].join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected void onProgressUpdate(String... text) {

        }
    }

    public void animateStart() {
        imageLogo = (ImageView) findViewById(R.id.imageView);
        Animation logoMoveAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale);
        logoMoveAnimation.setFillAfter(true);
        logoMoveAnimation.setFillEnabled(true);
        imageLogo.clearAnimation();
        imageLogo.startAnimation(logoMoveAnimation);
    }

    public void addListenerOnButton() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                String _url = "";
                switch (v.getId()) {
                    case (R.id.btnfacebook):
                        _url = "https://www.facebook.com/alternatifmayo";
                        break;  // add here

                    case (R.id.btninstagram):
                        _url = "https://instagram.com/hasemaofficial/";
                        break;  // add here

                    case (R.id.btntwitter):
                        _url = "https://twitter.com/HasemaTekstil";
                        break;  // add here

                    case (R.id.btnyoutube):
                        _url = "https://www.youtube.com/channel/UCdFDSXpWnWOGZyCWgLztg5Q";
                        break;  // add here

                    case (R.id.btnpinterest):
                        _url = "https://www.pinterest.com/hasema/";
                        break;  // add here

                }
                if (_url != "") {
                    Uri uri = Uri.parse(_url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        };
        facebook = (ImageButton) findViewById(R.id.btnfacebook);
        facebook.setOnClickListener(listener);
        twitter = (ImageButton) findViewById(R.id.btntwitter);
        twitter.setOnClickListener(listener);
        instagram = (ImageButton) findViewById(R.id.btninstagram);
        instagram.setOnClickListener(listener);
        pinterest = (ImageButton) findViewById(R.id.btnpinterest);
        pinterest.setOnClickListener(listener);
        youtube = (ImageButton) findViewById(R.id.btnyoutube);
        youtube.setOnClickListener(listener);
    }


}
