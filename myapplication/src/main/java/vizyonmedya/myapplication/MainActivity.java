package vizyonmedya.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.net.Uri;
import android.widget.ViewSwitcher;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
    ImageButton facebook, twitter, instagram, youtube, pinterest, cathas, catsport, catnehar, catoutlet;
    ImageView imageLogo;
    ViewSwitcher viewSwitcher;

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
        viewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);

        final Animation inAnim = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        final Animation outAnim = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right );

        viewSwitcher.setInAnimation(inAnim);
        viewSwitcher.setOutAnimation(outAnim);

        /*For first step removed the listview*/
//        new parseMagic().execute();
    }


    @Override
    public void onDestroy() {
        liste.setAdapter(null);
        super.onDestroy();
    }

    class parseMagic extends AsyncTask<String, String, String> {
        String url = "http://www.hasema.com/UPLOAD/urun_expsecenek.xml";
        xmlHandler xmlr = new xmlHandler();
        ArrayList<SECENEK> secList = new ArrayList<>();
        ArrayList<String> modelNames = new ArrayList<>();
        ArrayList<String> modelTexts = new ArrayList<>();
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
                    modelNames = new ArrayList<>();
                    modelTexts = new ArrayList<>();
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


                } catch (Exception ignored) {
                }

            }

        });

        /**
         * * Method for Setting the Height of the ListView dynamically.
         * *** Hack to fix the issue of not showing all the items of the ListView
         * *** when placed inside a ScrollView  ***
         */
        public void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null)
                return;

            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            int totalHeight = 0;
            View view = null;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                view = listAdapter.getView(i, view, listView);
                if (i == 0)
                    view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

                view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += view.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }

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
            setListViewHeightBasedOnChildren(liste);
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

    public void animateSmallerStart(ImageButton imageb) {
        animClearAll();
        Animation animMoveAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scalesmaller);
        animMoveAnimation.setFillAfter(true);
        animMoveAnimation.setFillEnabled(true);
//        animimage.clearAnimation();
        imageb.startAnimation(animMoveAnimation);
    }

    private void animClearAll() {
        cathas = (ImageButton) findViewById(R.id.hasemabayan);
        catsport = (ImageButton) findViewById(R.id.hasemasport);
        catnehar = (ImageButton) findViewById(R.id.nehar);
        catoutlet = (ImageButton) findViewById(R.id.outlet);
        cathas.clearAnimation();
        catsport.clearAnimation();
        catnehar.clearAnimation();
        catoutlet.clearAnimation();
    }


    public void addListenerOnButton() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


                if (v.getId() == R.id.hasemabayan || v.getId() == R.id.hasemasport || v.getId() == R.id.nehar || v.getId() == R.id.outlet) {
                    animateSmallerStart((ImageButton) findViewById(v.getId()));
                    if (v.getId() == R.id.hasemabayan) {
                        new parseMagic().execute();
                        viewSwitcher.showNext();
                    }

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
        cathas = (ImageButton) findViewById(R.id.hasemabayan);
        cathas.setOnClickListener(listener);
        catsport = (ImageButton) findViewById(R.id.hasemasport);
        catsport.setOnClickListener(listener);
        catnehar = (ImageButton) findViewById(R.id.nehar);
        catnehar.setOnClickListener(listener);
        catoutlet = (ImageButton) findViewById(R.id.outlet);
        catoutlet.setOnClickListener(listener);

    }


}
