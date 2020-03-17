package com.example.easymechproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Services_LIsts extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    ListView listView;
    ArrayList<Mechanics> list;
    AdapterList adapterList;
    RatingBar rate_bar;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth easyMechAuth;
    FirebaseUser admin;

    AdapterRecyclerGrid adapterRecyclerGrid;
    ArrayList<Services_Resources> arrayList;
    CoordinatorLayout coordinatorLayout;

    public String admin_name;

    private DatabaseReference easyMechDBRef, reference;
    private FirebaseUser easyMechCurrentUser;




    //TextView show_rate;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services__lists);


        easyMechAuth = FirebaseAuth.getInstance();
        admin = easyMechAuth.getCurrentUser();



        gridList();
        recyclerView = findViewById(R.id.recycleView_grid);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        adapterRecyclerGrid = new AdapterRecyclerGrid(this, arrayList);
        recyclerView.setAdapter(adapterRecyclerGrid);




        final BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && bottomNav.isShown()) {
                    bottomNav.setVisibility(View.GONE);
                } else if (dy < 0 ) {
                    bottomNav.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        toolbar = (Toolbar) findViewById(R.id.tool_Bar);
        easyMechAuth = FirebaseAuth.getInstance();
        easyMechCurrentUser = easyMechAuth.getCurrentUser();
        String service_email = easyMechCurrentUser.getEmail();
        if(service_email.equals("admin@email.com")){
            toolbar.setTitle("EasyMech (Admin)");
        }
        else if(service_email.equals("autocarexperts@gmail.com")){
            toolbar.setTitle("EasyMech (Service Center)");
        }
        else if(service_email.equals("hyderkhan.goa@gmail.com")){
            toolbar.setTitle("EasyMech (Service Center)");
        }
        else if(service_email.equals("sharayutoyota@yahoo.com")){
            toolbar.setTitle("EasyMech (Service Center)");
        }
        else if(service_email.equals("autopro@hotmail.com")){
            toolbar.setTitle("EasyMech (Service Center)");
        }
        else if(service_email.equals("alconhyndai@gmail.com")){
            toolbar.setTitle("EasyMech (Service Center)");
        }
        else if(service_email.equals("bavariamotors@gmail.com")){
            toolbar.setTitle("EasyMech (Service Center)");
        }
        else {
            toolbar.setTitle("EasyMech");
        }
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);


        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);



    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFrag = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFrag = new HomeFragment();
                            break;

                        case R.id.nav_account:
                            selectedFrag = new AccountFragment();
                            break;

                        case R.id.nav_notify:
                            selectedFrag = new NotifyFragment();
                            break;


                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                            selectedFrag).commit();
                    return true;
                }
            };


    private void gridList(){
        arrayList = new ArrayList<Services_Resources>();

        easyMechAuth = FirebaseAuth.getInstance();
        easyMechCurrentUser = easyMechAuth.getCurrentUser();

        arrayList.add(new Services_Resources("Repair Services","Repair your car",R.drawable.mechanic_icons));
        arrayList.add(new Services_Resources("Batteries","Battery issues",R.drawable.bat_tery));
        arrayList.add(new Services_Resources("Engine/ Transmission","Fix your car engine",R.drawable.en_gine));
        arrayList.add(new Services_Resources("Oil/ Filters","Change Oil and Filter",R.drawable.oil_filter));
        arrayList.add(new Services_Resources("Paint/ Denting","Paint your own car",R.drawable.paint));
        arrayList.add(new Services_Resources("Tires/ Wheels","Want new tires/ wheels?",R.drawable.tires_wheels));

        if(easyMechCurrentUser.getEmail().toString().equals("admin@email.com")){
            arrayList.add(new Services_Resources("Manage Booking Services","Functionality, Monitoring, Assessment and Report",R.drawable.admin_icon));
            arrayList.add(new Services_Resources("Manage Service Centers","Add, Remove or Update Service Centres",R.drawable.rep_air));
            arrayList.add(new Services_Resources("Manage System Users","Manage all users activities ",R.drawable.all_users));
        }
        easyMechAuth = FirebaseAuth.getInstance();
        easyMechCurrentUser = easyMechAuth.getCurrentUser();
        String service_email = easyMechCurrentUser.getEmail();
        if(service_email.equals("autocarexperts@gmail.com")){
            arrayList.add(new Services_Resources("Client Appoinments","List of all booked appointments by clients",R.drawable.user_books));
        }
        else if(service_email.equals("hyderkhan.goa@gmail.com")){
            arrayList.add(new Services_Resources("Client Appoinments","List of all booked appointments by clients",R.drawable.user_books));
        }
        else if(service_email.equals("sharayutoyota@yahoo.com")){
            arrayList.add(new Services_Resources("Client Appoinments","List of all booked appointments by clients",R.drawable.user_books));
        }
        else if(service_email.equals("autopro@hotmail.com")){
            arrayList.add(new Services_Resources("Client Appoinments","List of all booked appointments by clients",R.drawable.user_books));
        }
        else if(service_email.equals("alconhyndai@gmail.com")){
            arrayList.add(new Services_Resources("Client Appoinments","List of all booked appointments by clients",R.drawable.user_books));
        }
        else if(service_email.equals("bavariamotors@gmail.com")){
            arrayList.add(new Services_Resources("Client Appoinments","List of all booked appointments by clients",R.drawable.user_books));
        }





    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.about_us:
                startActivity(new Intent(Services_LIsts.this, About_US.class));
                break;
            case R.id.helping:
                startActivity(new Intent(Services_LIsts.this, HelpScreen.class));
                break;

            case R.id.F_A_Q:
                startActivity(new Intent(Services_LIsts.this, FAQ.class));
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
                startActivity(new Intent(Services_LIsts.this, Feedback.class));
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onDestroy() {
        super.onDestroy();
        //state is saved here.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

            switch (item.getItemId()){
                case R.id.logout_option:
                {
                    Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_LONG).show();
                    easyMechAuth.getInstance().signOut();
                    startActivity(new Intent(Services_LIsts.this,Base_Home.class));
                }
            }
        return true;
    }
}
