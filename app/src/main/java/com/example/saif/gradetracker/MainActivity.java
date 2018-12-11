package com.example.saif.gradetracker;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);


        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CardViewFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about_us) {
            Toast.makeText(this, "Grade Tracker. version 1.0.1, Developed By CodeHunter", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            vibrator.vibrate(30);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).addToBackStack("my_fragment").commit();
            // Handle the camera action
        } else if (id == R.id.nav_add_grade) {
            vibrator.vibrate(30);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AddFragment()).addToBackStack("my_fragment").commit();

        } else if (id == R.id.nav_show_grades) {
            vibrator.vibrate(30);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new GradesFragment()).addToBackStack("my_fragment").commit();

        } else if (id == R.id.nav_progress_graph) {
            vibrator.vibrate(30);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new PiegraphFragment()).addToBackStack("my_fragment").commit();

        } else if (id == R.id.nav_about_us) {
            vibrator.vibrate(30);
            Toast.makeText(this, "Grade Tracker. version 1.0.1, Developed By CodeHunter", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_feedback) {
            vibrator.vibrate(30);
            Toast.makeText(this, "Thanks for your feedback...", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
