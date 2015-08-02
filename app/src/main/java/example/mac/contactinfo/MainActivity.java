package example.mac.contactinfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.WindowCallbackWrapper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;


public class MainActivity extends Activity {

    private static final String TAG = "MyActivity";
    private Person persona;
    private static final int UPDATE_INFO = 1;
    private EditText editText;
    private EditText editText1;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        persona = getIntent().getParcelableExtra("persona");
        if(persona == null) {
            persona = new Person();
        }

        editText = (EditText) findViewById(R.id.editText);
        editText1 = (EditText) findViewById(R.id.editText1);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Log.v(TAG, "isTablet: " + isTablet);

        if(!isTablet(this)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setContactInfo();
    }

    private void setContactInfo() {
        editText.setText(persona.getNombre());
        editText1.setText(persona.getApellido());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static boolean isTablet(Context ctx){
        return (ctx.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public void getBigger(View view){
        persona.setNombre(editText.getText().toString());
        persona.setApellido(editText1.getText().toString());

        intent = new Intent(MainActivity.this, ImageBigger.class);
        intent.putExtra("persona", persona);

        startActivityForResult(intent, UPDATE_INFO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_INFO && resultCode == RESULT_OK) {
            persona = data.getParcelableExtra("persona");
            setContactInfo();
            Log.v(TAG, "MANOLIN " + persona.getNombre());
        }

    }
}
