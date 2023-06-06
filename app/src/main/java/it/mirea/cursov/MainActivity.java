package it.mirea.cursov;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import it.mirea.cursov.adapter.CategoryAdapter;
import it.mirea.cursov.model.Category;

public class MainActivity extends AppCompatActivity implements Adaptery.OnItemClickListener {
    public static final String EXTRA_URL = "I_image";
    public static final String EXTRA_NAME = "N_name";
    public static final String EXTRA_DESCRIPTION = "D_description";

    Button btnLogOut;
    FirebaseAuth mAuth;

    RecyclerView categoryRecycler;
    CategoryAdapter categoryAdapter;
    private static String JSON_URL = "https://run.mocky.io/v3/4634a538-d591-4422-adfb-3458f5b172fe";

    List<it.mirea.cursov.CsModelClass> csList;
    RecyclerView recyclerView;

    public MainActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogOut = findViewById(R.id.btnLogout);
        mAuth = FirebaseAuth.getInstance();

        btnLogOut.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "Драма"));
        categoryList.add(new Category(2, "Экшн"));
        categoryList.add(new Category(3, "Триллер"));
        categoryList.add(new Category(4, "Комедия"));
        categoryList.add(new Category(5, "Фэнтези"));

        setCategoryRecycler(categoryList);

        csList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);

        MainActivity.GetData getData = new MainActivity.GetData();
        getData.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    private void setCategoryRecycler(List<Category> categoryList){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        categoryRecycler = findViewById(R.id.categoryRecycler);
        categoryRecycler.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this, categoryList);
        categoryRecycler.setAdapter(categoryAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent kinoIntent = new Intent(this, KinoPage.class);
        it.mirea.cursov.CsModelClass clickedItem = csList.get(position);

        kinoIntent.putExtra(EXTRA_URL, clickedItem.getI_image());
        kinoIntent.putExtra(EXTRA_NAME, clickedItem.getN_name());
        kinoIntent.putExtra(EXTRA_DESCRIPTION, clickedItem.getD_description());

        startActivity(kinoIntent);
    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            try{
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("movies");
                for (int i = 0 ; i < jsonArray.length() ; i++){

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    it.mirea.cursov.CsModelClass model = new it.mirea.cursov.CsModelClass();
                    model.setN_name(jsonObject1.getString("title"));
                    model.setD_description(jsonObject1.getString("description"));
                    model.setR_rate(jsonObject1.getString("rating"));
                    model.setG_genre(jsonObject1.getString("genre"));
                    model.setI_image(jsonObject1.getString("poster"));

                    csList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(csList);
        }
    }

    private void PutDataIntoRecyclerView(List<it.mirea.cursov.CsModelClass> csList){

        it.mirea.cursov.Adaptery adaptery = new it.mirea.cursov.Adaptery(this, csList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptery);

        adaptery.setOnItemClickListener(MainActivity.this);

    }
    public void note(View view){
        Intent intent = new Intent(this, it.mirea.cursov.note.MainActivity.class);
        startActivity(intent);

    }

}