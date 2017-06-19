package innova4b.com.usingdefaultcamera;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    final int PHOTO_RESULT = 1;
    private Uri mLastPhotoURI =null;
    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);
    }

    private Uri createFileURI(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());
        String filename = "PHOTO_" + timeStamp + ".jpg";
        return Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), filename));
    }

    public void takePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            mLastPhotoURI = createFileURI();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mLastPhotoURI);
            startActivityForResult(takePictureIntent, PHOTO_RESULT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_RESULT && resultCode == RESULT_OK){
            mImageView.setImageBitmap(BitmapFactory.decodeFile(mLastPhotoURI.getPath()));
        }
    }
}
