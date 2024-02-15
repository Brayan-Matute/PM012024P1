package com.example.pm012024p1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import android.widget.Toast;

public class ActivityVideo extends AppCompatActivity {


    static final int peticion_video = 104;

    VideoView videoView;

    Button btnVideo;

    Button btncompartir;
    Uri videoUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);


        videoView = findViewById(R.id.videoView2);
        btnVideo = findViewById(R.id.btnvideo);
        btncompartir = findViewById(R.id.btncompartir);


        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarVideo();
            }
        });

        btncompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compartirVideo();
            }
        });
    }







    private void grabarVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, peticion_video);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == peticion_video && resultCode == RESULT_OK) {
            videoUri = data.getData();
            if (videoUri != null) {
                videoView.setVideoURI(videoUri);
                videoView.start();
            } else {
                Toast.makeText(this, "No se pudo obtener la URI del video", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void compartirVideo() {
        if (videoUri != null) {
            Intent compartirIntent = new Intent(Intent.ACTION_SEND);
            compartirIntent.setType("video/*");
            compartirIntent.putExtra(Intent.EXTRA_STREAM, videoUri);
            startActivity(Intent.createChooser(compartirIntent, "Compartir video"));
        } else {
            Toast.makeText(this, "No hay video para compartir", Toast.LENGTH_SHORT).show();
        }
    }


}





