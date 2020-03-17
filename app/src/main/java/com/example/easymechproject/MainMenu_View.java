package com.example.easymechproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainMenu_View extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    AdapterList adapterList;
    String[] title;
    String[] description;
    String[] email;
    int[] images;
    ArrayList<Mechanics> mechanics = new ArrayList<Mechanics>();

    RatingBar rate_bar;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu__view);




        toolbar = (Toolbar) findViewById(R.id.tool_Bar);
        toolbar.setTitle("Service Centers");
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        title = new String[]{"Auto Car Experts","HYDER KHAN MOTOR Garage","Sharayu Toyota","Auto Pro","Alcon Hyundai","Bavaria Motors Pvt Ltd"};

        description = new String[]{"256/5 Diaswaddo, Arpora Calangute Road, Arpora, Goa - 403516, Near Dream Circle","TALEIGAO Band, Santa Cruz Road Taleigao, Taleigao, Goa - 403001, Near By Essar Residency",
                "Survey No 116/3, National Highway No 17, Cortalim, Goa - 403710, Kesarval Verna, Opposite Kesarval Garden Hotel","Pundalik Nagar, Alto Porvorim, Goa - 403521, Nr Club De Goa",
                "Survey No 20/1, National Highway No 17, Porvorim, Goa - 403501, Near Damian De Goa Showroom","Plot Number 2 B, Verna, Goa - 403722, Phase 1A, Verna Industries Estate, NH."};

        email = new String[]{"autocarexperts@gmail.com","hyderkhan.goa@gmail.com","sharayutoyota@yahoo.com","autopro@hotmail.com","alconhyndai@gmail.com","bavariamotors@gmail.com"};

        images = new int[]{R.drawable.auto_experts, R.drawable.bavarati_auto,R.drawable.sharya_toyota,R.drawable.auto_pro,R.drawable.alcon_hyndai,R.drawable.haider_khan};

        listView = findViewById(R.id.listview_view);

        for(int i=0; i<title.length; i++){
            Mechanics mechanic = new Mechanics(title[i],description[i],email[i],images[i]);
            mechanics.add(mechanic);
        }
        adapterList = new AdapterList(this, mechanics);
        listView.setAdapter(adapterList);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mechanis_search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    adapterList.filter("");
                    listView.clearTextFilter();
                }
                else {
                    adapterList.filter(newText);
                }
                return false;
            }

        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.action_recent){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.about_us:
                startActivity(new Intent(MainMenu_View.this, About_US.class));
                break;
            case R.id.helping:
                startActivity(new Intent(MainMenu_View.this, HelpScreen.class));
                break;

            case R.id.F_A_Q:
                startActivity(new Intent(MainMenu_View.this, FAQ.class));
                break;


            case R.id.share_link:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "EasyMech");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG);
                }
                break;

            case R.id.Rate_us:
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=my packagename "));
                startActivity(i);
                break;

            case R.id.feedback:
                startActivity(new Intent(MainMenu_View.this, Feedback.class));
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

