package vizyonmedya.testapplication;

/**
 * Created by Verisun on 21.7.2015.
 */
import java.util.ArrayList;
        import android.util.Log;
        import vizyonmedya.testapplication.SECENEK;
        import org.xml.sax.Attributes;
        import org.xml.sax.SAXException;
        import org.xml.sax.helpers.DefaultHandler;

public class xmlHandler extends DefaultHandler {

    Boolean currentElement = false;
    String currentValue = "";
    SECENEK myxml= null;
    private ArrayList<SECENEK> myxmlList = new ArrayList<SECENEK>();

    public ArrayList<SECENEK> getXmlList() {
        return myxmlList;
    }

    // Called when tag starts
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        currentElement = true;
        currentValue = "";
        if (localName.equals("SECENEK")) {
            myxml = new SECENEK();
            Log.w("new secenek","yo");
            Log.w("Size---&gt;",String.valueOf(myxmlList.size()));
        }

    }

    // Called when tag closing
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        currentElement = false;

        /** set value */
        if (localName.equalsIgnoreCase("Urun_Kodu")){
            myxml.setUrun_Kodu(currentValue);
            Log.d("Urun_Kodu",currentValue);
        }
        else if (localName.equalsIgnoreCase("Urun_Adi"))
            myxml.setUrun_Adi(currentValue);
        else if (localName.equalsIgnoreCase("Urun_Kategori_Adi"))
            myxml.setUrun_Kategori_Adi(currentValue);
        else if (localName.equalsIgnoreCase("Urun_Marka_Adi"))
            myxml.setUrun_Marka_Adi(currentValue);
        else if (localName.equalsIgnoreCase("Urun_Stok_Durumu"))
            myxml.setUrun_Stok_Durumu(currentValue);
        else if (localName.equalsIgnoreCase("Urun_Stoku"))
            myxml.setUrun_Stoku(currentValue);
        else if (localName.equalsIgnoreCase("Detay_Kodu_1"))
            myxml.setDetay_Kodu_1(currentValue);
        else if (localName.equalsIgnoreCase("Detayi_Adi_1"))
            myxml.setDetayi_Adi_1(currentValue);
        else if (localName.equalsIgnoreCase("Detayi_Kodu_2"))
            myxml.setDetayi_Kodu_2(currentValue);
        else if (localName.equalsIgnoreCase("Detayi_Adi_2"))
            myxml.setDetayi_Adi_2(currentValue);
        else if (localName.equalsIgnoreCase("Urun_URLi"))
            myxml.setUrun_URLi(currentValue);
        else if (localName.equalsIgnoreCase("Urun_Resmi"))
            myxml.setUrun_Resmi(currentValue);
        else if (localName.equalsIgnoreCase("Urun_Fiyati"))
            myxml.setUrun_Fiyati(currentValue);
        else if (localName.equalsIgnoreCase("Urun_Fiyati_KDVsiz"))
            myxml.setUrun_Fiyati_KDVsiz(currentValue);
    }

    // Called to get tag characters
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (currentElement) {
            currentValue = currentValue + new String(ch, start, length);

        }

    }

}
