package com.tarasevich.test;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tarasevich.test.Constants.Constants;
import com.tarasevich.test.Map.MapsActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

//import static com.tarasevich.test.CameraFragment.REQUEST_IMAGE_CAPTURE;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;


    static  final int PERMISSION_REQ_CODE = 0;


//    @BindView(R.id.nav_header_user_name)
//    TextView navHeaderUserName;

    private Uri photoUri;
     static final int CODE_URI_PHOT0 = 1;

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        String login = getIntent().getStringExtra("login");
//        navHeaderUserName.setText(login);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_frame);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFullImage();
                requestMultiplePermissions();

            }
        });

        if(fragment == null){
          fragment = new PhotoFragment();
            fm.beginTransaction().replace(R.id.fragment_frame,fragment)
                    .commit();

        }
        if (fragment == null){
            fragment = new CameraFragment();
            fm.beginTransaction().replace(R.id.fragment_frame,fragment)
                    .commit();
        }
}


   private void saveFullImage(){
         Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         File file = new File(Environment.getExternalStorageDirectory(),"photo.jpg");
         photoUri = Uri.fromFile(file);
         intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
         startActivityForResult(intent,CODE_URI_PHOT0);

   }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logout) {
           getSharedPreferences(Constants.SHARED,MODE_PRIVATE).edit().clear().apply();
                Intent intent = new Intent(this,StartActivity.class);
                startActivity(intent);
                finish();
               return true;
        }
               return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_map) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this,PhotoFragment.class);
            startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void requestMultiplePermissions(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        },PERMISSION_REQ_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_CODE && grantResults.length == 3){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
            if (grantResults[1]==PackageManager.PERMISSION_GRANTED){

            }
            if (grantResults[2] == PackageManager.PERMISSION_GRANTED){

            }
        }
    }

}
