package Acts;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.example.weather_.R;

import java.util.Observable;

import View.*;

public class StartAct extends AppCompatActivity {
    VideoView videoView;
    int Resid= R.raw.yu;
    Uri uri;
    HomePageFragment homePageFragment;
    ForeCastFragment foreCastFragment;
    AboutFragment aboutFragment;
    FragmentManager fragmentManager;
    Fragment fragment_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        fragment_main = getSupportFragmentManager().findFragmentById(R.id.fragment_main);
        videoView = findViewById(R.id.videoViewForFra);
        uri = Uri.parse("android.resource://"+getPackageName()+"/"+Resid);
        videoView.setVideoURI(uri);
        videoView.start();
        System.out.println("Video play 1");
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVideoURI(uri);
                videoView.start();
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNav);
        fragmentManager = getSupportFragmentManager();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_forecast)
            {
                showF(2);

            }
            if (item.getItemId() == R.id.navigation_home)
            {
                showF(1);
            }
            if (item.getItemId() == R.id.navigation_about)
            {
                showF(3);
            }
            return true;
        });
        showF(1);
        //
        androidx.lifecycle.Observer<Integer> videoObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                System.out.println("JRT: LiveData Changed "+ Resid);
                if (integer!=Resid)
                {
                    System.out.println("!!!"+integer+" COMP: "+Resid);
                    Resid = integer;
                    uri = Uri.parse("android.resource://"+getPackageName()+"/"+Resid);
                    videoView.setVideoURI(uri);
                    videoView.start();
                    System.out.println("Video play 2");
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            videoView.setVideoURI(uri);
                            videoView.start();
                        }
                    });
                }
                System.out.println("???!"+integer+" COMP: "+Resid);
                uri = Uri.parse("android.resource://"+getPackageName()+"/"+Resid);
                videoView.setVideoURI(uri);
                videoView.start();
                System.out.println("Video play 3");
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        videoView.setVideoURI(uri);
                        videoView.start();
                    }
                });
            }
        };
        if (homePageFragment!=null)
        {
            homePageFragment.getVideoFeed().observe(this,videoObserver);
        }
        else
        {
            System.out.println("JRT: Fra not ready");
        }
    }
    private void showF(int page)
    {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        hideF(ft);
        switch (page)
        {
            case 1:
                if (homePageFragment != null)
                {
                    ft.show(homePageFragment);
                    if (foreCastFragment!=null){
                    ft.hide(foreCastFragment);}
                    if (aboutFragment!=null){
                    ft.hide(aboutFragment);}
                    System.out.println(1+" case:1 TTT");
                }
                else
                {
                    homePageFragment = new HomePageFragment();
                    ft.add(R.id.fragment_main,homePageFragment);
                    if (foreCastFragment!=null){
                    ft.hide(foreCastFragment);}
                    if (aboutFragment!=null){
                    ft.hide(aboutFragment);}
                    System.out.println(1 +"Com case: 1 TTT");
             }
                break;
            case 2:
                if (foreCastFragment != null)
                {
                    if (homePageFragment!=null){
                        ft.hide(homePageFragment);}
                    if (aboutFragment!=null){
                        ft.hide(aboutFragment);}
                    ft.show(foreCastFragment);
                    System.out.println(2+" case: 2 TTT");
                }
                else
                {
                    if (homePageFragment!=null){
                        ft.hide(homePageFragment);}
                    if (aboutFragment!=null){
                        ft.hide(aboutFragment);}
                    foreCastFragment = new ForeCastFragment();
                    ft.add(R.id.fragment_main,foreCastFragment);
                    System.out.println(2+"Com case: 2 TTT");
                }
                break;
            case 3:
                if (aboutFragment != null)
                {
                    if (foreCastFragment!=null){
                        ft.hide(foreCastFragment);}
                    if (homePageFragment!=null){
                        ft.hide(homePageFragment);}
                    ft.show(aboutFragment);
                    System.out.println(3+" case: 3 TTT");
                }
                else
                {
                    if (foreCastFragment!=null){
                        ft.hide(foreCastFragment);}
                    if (homePageFragment!=null){
                        ft.hide(homePageFragment);}
                    aboutFragment = new AboutFragment();
                    ft.add(R.id.fragment_main,aboutFragment);
                    System.out.println(3+"Com case: 3 TTT");
                }
                break;
        }
        ft.commit();
    }
    public void hideF(FragmentTransaction ft)
    {
        if (homePageFragment!=null)
        {
            ft.hide(homePageFragment);
        }
        if (aboutFragment!=null)
        {
            ft.hide(aboutFragment);
        }
        if (homePageFragment!=null)
        {
            ft.hide(homePageFragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        uri = Uri.parse("android.resource://"+getPackageName()+"/"+Resid);
        videoView.setVideoURI(uri);
        videoView.start();
        System.out.println("Video play 3");
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVideoURI(uri);
                videoView.start();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        uri = Uri.parse("android.resource://"+getPackageName()+"/"+Resid);
        videoView.setVideoURI(uri);
        videoView.start();
        System.out.println("Video play 3");
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVideoURI(uri);
                videoView.start();
            }
        });
    }
}