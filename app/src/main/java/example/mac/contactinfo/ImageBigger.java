package example.mac.contactinfo;

import android.app.Activity;
import android.app.Notification;
import android.content.ContentResolver;
import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompatBase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class ImageBigger extends ActionBarActivity {

    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    Person persona;
    Intent i;

    private static final String TAG = "MyActivity";
    private EditText editText;
    private EditText editText1;

    public void takePhoto(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ImageButton imageView = (ImageButton) findViewById(R.id.imageButton);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        imageView.setImageBitmap(bitmap);
                        Toast.makeText(this, selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(imageUri != null){
            outState.putString("imageUri", imageUri.toString());
        }

        Log.v(TAG, "IMAGEURI: " + imageUri.toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String uriImage = savedInstanceState.getString("imageUri");
        ImageButton imageView = (ImageButton) findViewById(R.id.imageButton);
        ContentResolver cr = getContentResolver();
        Bitmap bitmap;

        try {
            imageUri = Uri.parse(uriImage);
            bitmap = android.provider.MediaStore.Images.Media
                    .getBitmap(cr, imageUri);
            imageView.setImageBitmap(bitmap);
            Toast.makeText(this, uriImage,
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                    .show();
            Log.e("Camera", e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_bigger);

        editText = (EditText) findViewById(R.id.editText);
        editText1 = (EditText) findViewById(R.id.editText1);

        i = getIntent();
        persona = i.getExtras().getParcelable("persona");
        //editText.setText(i.getStringExtra("name"));
        //editText1.setText(i.getStringExtra("lastname"));
        editText.setText(persona.getNombre());
        editText1.setText(persona.getApellido());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_bigger, menu);
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

    public void goBack(View v) {
        persona.setNombre(editText.getText().toString());
        persona.setApellido(editText1.getText().toString());

        i.putExtra("persona", persona);
        setResult(Activity.RESULT_OK, i);
        Log.v(TAG, "MANOLO" + persona.getNombre());

        finish();
    }
}
