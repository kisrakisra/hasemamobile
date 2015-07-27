package vizyonmedya.testapplication;

/**
 * Created by Verisun on 20.7.2015.
 */

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;

import android.widget.Toast;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

    private EditText editText;

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        editText = (EditText) parent.findViewById(R.id.editText);
        Toast.makeText(parent.getContext(),
                parent.getItemAtPosition(pos).toString() +
                        " marka tavsiyeniz",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
