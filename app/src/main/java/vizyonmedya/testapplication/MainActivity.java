package vizyonmedya.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends Activity {

    private Spinner spinner1;
    private Button btnSubmit;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
    }

    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editText = (EditText) findViewById(R.id.editText);
                String sUsername = editText.getText().toString();
                if (sUsername.matches("") || spinner1.getSelectedItemPosition() == 0) {
                    Toast.makeText(MainActivity.this, "" + getBaseContext().getString(R.string.mailstr1), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String subject = "\u00A9" + getBaseContext().getString(R.string.mailstr2);
                    String body = "" + String.valueOf(spinner1.getSelectedItem()) + " " + getBaseContext().getString(R.string.mailstr3)
                            + "\n" + getBaseContext().getString(R.string.mailstr4);
                    try {
                        /*GMailSender mailSender = new GMailSender("kisrasumeyye@gmail.com", "Kisra6362970");
                        try{
                            mailSender.sendSomeMail(subject, body, "kisrasumeyye@gmail.com", sUsername);
                        }
                        catch(Exception exc){

                        }
                        Log.i("Finished sending email", "now");
                        editText.setText("");
                        spinner1.setSelected(false);*/
                        sendEmail();
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    protected void parseXml(){
        try {
            URL url = new URL("http://www.hasema.com/UPLOAD/GoogleShopXml.xml");
            URLConnection conn = url.openConnection();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(conn.getInputStream());

            NodeList nodes = doc.getElementsByTagName("item");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                NodeList title = element.getElementsByTagName("title");
                Element _title = (Element) title.item(0);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] recipients = {editText.getText().toString()};
        Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        // prompts email clients only
        email.setType("text/plain");
        email.putExtra(Intent.EXTRA_EMAIL, recipients);
        email.putExtra(Intent.EXTRA_SUBJECT, "\u00A9" + getBaseContext().getString(R.string.mailstr2));
        email.putExtra(Intent.EXTRA_TEXT, "" + String.valueOf(spinner1.getSelectedItem()) + " " + getBaseContext().getString(R.string.mailstr3)
                + "\n" + getBaseContext().getString(R.string.mailstr4));

        String subject = "\u00A9" + getBaseContext().getString(R.string.mailstr2);
        String body = "" + String.valueOf(spinner1.getSelectedItem()) + " " + getBaseContext().getString(R.string.mailstr3)
                + "\n" + getBaseContext().getString(R.string.mailstr4);
        try {
            startActivity(Intent.createChooser(email, "Send mail..."));
            finish();
            Log.i("Finished sending email", "now");
            editText.setText("");
            spinner1.setSelected(false);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


}
