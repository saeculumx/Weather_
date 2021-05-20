package View;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.weather_.R;

public class AboutFragment extends Fragment {

    Uri uri;
    View view;
    int Resid;
    Button feedback;
    Button viewGit;
    ImageView imageView;

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_about, container, false);
        imageView = view.findViewById(R.id.imageView);
        feedback = view.findViewById(R.id.SendFeedBack);
        viewGit = view.findViewById(R.id.viewongit);
        viewGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri web = Uri.parse("https://github.com/saeculumx/Weather_.git");
                Intent intent = new Intent(Intent.ACTION_VIEW,web);
                startActivity(intent);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] email = {"xhyx0811@163.com"};
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, email);
                intent.putExtra(Intent.EXTRA_CC, email);
                intent.putExtra(Intent.EXTRA_SUBJECT, "App FeedBack Weather_");
                intent.putExtra(Intent.EXTRA_TEXT, "Please state your problems");
                startActivity(Intent.createChooser(intent, "Please select an option"));
            }
        });
        return view;
    }
}