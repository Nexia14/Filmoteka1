package it.mirea.cursov;

import static it.mirea.cursov.MainActivity.EXTRA_DESCRIPTION;
import static it.mirea.cursov.MainActivity.EXTRA_NAME;
import static it.mirea.cursov.MainActivity.EXTRA_URL;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

public class KinoPage extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kino_page);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String name = intent.getStringExtra(EXTRA_NAME);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);

        ImageView cardImage = findViewById(R.id.image);
        TextView cardName = findViewById(R.id.name);
        TextView cardDescription = findViewById(R.id.description);

        Picasso.get().load(imageUrl).fit().centerInside().into(cardImage);
        cardName.setText(name);
        cardDescription.setText(description);
        cardDescription.setMovementMethod(new ScrollingMovementMethod());

    }

    public void share(View view) {
        TextView cardName = findViewById(R.id.name);
        TextView cardDescription = findViewById(R.id.description);
        String name = cardName.getText().toString();

        ImageView imageView = findViewById(R.id.image);
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        try {
            File file = new File(getApplicationContext().getExternalCacheDir(),
                    File.separator + name + ".jpg");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                    BuildConfig.APPLICATION_ID + ".provider", file);

            intent.putExtra(Intent.EXTRA_STREAM, photoURI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/jpg");

            String shareSubject = name;
            String shareBody = cardDescription.getText().toString();
            intent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
            intent.putExtra(Intent.EXTRA_TEXT, shareBody);

            startActivity(Intent.createChooser(intent, "Поделиться фильмом"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
