package com.example.dell.myapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Sender_App extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private ImageView img;
    private LocalBroadcastManager broadcaster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender__app);
        broadcaster = LocalBroadcastManager.getInstance(this);
        img = (ImageView) findViewById(R.id.imageView1);
        Button send = (Button) findViewById(R.id.button1);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
// send intent to other activity
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT,
//                        "This is my text to send.");
//                sendIntent.putExtra("MyKey",
//                        "This is second my text to send using my key.");
//                sendIntent.setType("text/plain");
////                startActivity(Intent.createChooser(sendIntent, "Send"));
//                startActivity(sendIntent);


//                broadcast with sendBroadcast
                final Intent intent=new Intent();
                intent.setAction("com.pkg.perform.Ruby");
                intent.putExtra("KeyName","code1id");
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                intent.setComponent(
                        new ComponentName("com.example.dell.resiving","com.example.dell.resiving.MyBroadcastReceiver"));
                sendBroadcast(intent);




            }
        });

        Button sendImage = (Button) findViewById(R.id.button2);
        sendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(
//                        Intent.createChooser(intent, "Select Picture"),
//                        SELECT_PICTURE);
                startActivity(intent);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                img.setImageURI(selectedImageUri);
                sendImage(selectedImageUri);
            }
        }
    }

    void sendImage(Uri selectedImageUri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, selectedImageUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "Send Image"));
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
