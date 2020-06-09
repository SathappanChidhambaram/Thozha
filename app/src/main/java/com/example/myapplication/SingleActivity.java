package com.example.myapplication;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.utils.Utils;
import com.bumptech.glide.Glide;
import com.example.myapplication.utils.certModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class SingleActivity extends AppCompatActivity  {

    public TextView title,describe,link,lin,description;
    public ImageView image;
    public DatabaseReference reff;
    public LinearLayout forlink,fordown;
    public Button download;
    public FirebaseStorage storage = FirebaseStorage.getInstance();

    public String stitle="",simage="",sparent="",sgrand="",sgrand1="",sgrand2="",url1="",des;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

      title=findViewById(R.id.title);
      describe=findViewById(R.id.describe);
      link=findViewById(R.id.link);
      image=findViewById(R.id.image);
      lin=findViewById(R.id.lin);
      description=findViewById(R.id.description);
      forlink=findViewById(R.id.forlink);
      fordown=findViewById(R.id.fordown);
      download=findViewById(R.id.download);

        if(connectedToNetwork()){
            volley();
        }else{ NoInternetAlertDialog(); }

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {

               // Toast.makeText(SingleActivity.this,"haiclick",Toast.LENGTH_SHORT).show();

                downloadFile(SingleActivity.this,stitle,".pdf","downloads",url1);
              /*  try {
                    Toast.makeText(SingleActivity.this,"try",Toast.LENGTH_SHORT).show();
                        URL url = new URL(url1);
                        HttpURLConnection c = (HttpURLConnection) url.openConnection();
                        c.setRequestMethod("GET");
                        c.connect();
                        //String downloadFileName = downloadUrl.replace(Utils.mainUrl, "");

                        File outputFile = new File(apkStorage,url1);
                        if (!outputFile.exists()) {
                            outputFile.createNewFile();
                           // Log.e(TAG, "File Created");
                        }
                        FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                        InputStream is = c.getInputStream();//Get InputStream for connection

                        byte[] buffer = new byte[1024];//Set buffer type
                        int len1 = 0;//init length
                        while ((len1 = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, len1);//Write new file
                        }
                        fos.close();
                        is.close();
                    }

                }
                catch (Exception e)
                {

                }*/
                // Create a storage reference from our app

                }
        });



       // Toast.makeText(this, sid, Toast.LENGTH_SHORT).show();

    }


    public boolean connectedToNetwork() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.isConnected();
        }

        return false;

    }


    public void NoInternetAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You are not connected to the internet. ");
        builder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent openSettings = new Intent();
                openSettings.setAction(Settings.ACTION_WIRELESS_SETTINGS);
                openSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(openSettings);
            }
        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void volley() {

        Intent i=getIntent();
        stitle=i.getStringExtra("title");
        sparent=i.getStringExtra("parent");
        simage=i.getStringExtra("image");
        sgrand=i.getStringExtra("grand");
        sgrand1=i.getStringExtra("grand1");
        sgrand2=i.getStringExtra("grand2");
        reff = FirebaseDatabase.getInstance().getReference();
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(sgrand2.equals("")) {
                    forlink.setVisibility(View.VISIBLE);
                    fordown.setVisibility(View.GONE);
                    if(!sgrand.equals(""))
                    {
                        title.setText(dataSnapshot.child(sgrand).child(sparent).child(stitle).child("title").getValue().toString().toUpperCase());
                        if (sparent.equals("schemes")) {
                            lin.setText("Website Link:");
                            description.setText("Description");
                            des=dataSnapshot.child(sgrand).child(sparent).child(stitle).child("description").getValue().toString();
                            des=des.replace("_n","\n");
                            des=des.replace("_t","\t");
                            describe.setText(des);
                            // simage = dataSnapshot.child(sgrand).child(sparent).child(stitle).child("image").getValue().toString();
                            Glide.with(SingleActivity.this)
                                    .load(simage).fitCenter().override(1000, 1000).into(image);

                        } else {
                            lin.setText("Video Link:");
                            description.setText("Steps");
                            Glide.with(SingleActivity.this)
                                    .load(simage).fitCenter().override(1000, 1000).into(image);
                            des=dataSnapshot.child(sgrand).child(sparent).child(stitle).child("steps").getValue().toString();
                            des=des.replace("_n","\n");
                            des=des.replace("_t","\t");
                            describe.setText(des);

                        }
                        link.setText(dataSnapshot.child(sgrand).child(sparent).child(stitle).child("link").getValue().toString());
                        //ima=dataSnapshot.child(sgrand).child(sparent).child(stitle).child("image").getValue().toString();
                    }
                    else
                    {
                        title.setText(dataSnapshot.child(sparent).child(stitle).child("title").getValue().toString().toUpperCase());

                            lin.setText("Video Link:");
                            description.setText("Description");
                            Glide.with(SingleActivity.this)
                                    .load(simage).fitCenter().override(1000, 1000).into(image);
                            des=dataSnapshot.child(sparent).child(stitle).child("description").getValue().toString();
                        des=des.replace("_n","\n");
                        des=des.replace("_t","\t");
                        describe.setText(des);

                        link.setText(dataSnapshot.child(sparent).child(stitle).child("link").getValue().toString());

                    }
                }
                else
                {
                    title.setText(stitle.toUpperCase());
                    forlink.setVisibility(View.GONE);
                    fordown.setVisibility(View.VISIBLE);
                    des=dataSnapshot.child(sgrand2).child(sgrand1).child(sgrand).child(sparent).child(stitle).child("description").getValue().toString();
                    des=des.replace("_n","\n");
                    des=des.replace("_t","\t");
                    describe.setText(des);
                   // Toast.makeText(SingleActivity.this,sgrand2+"+"+sgrand1+"+"+sgrand+"+"+sparent+"+"+stitle,Toast.LENGTH_SHORT).show();
                    Glide.with(SingleActivity.this)
                            .load(simage).fitCenter().override(1000, 1000).into(image);
                    url1=dataSnapshot.child(sgrand2).child(sgrand1).child(sgrand).child(sparent).child(stitle).child("link").getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        link.setPaintFlags(link.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

       // if(simage!=null)
        //{
            Glide.with(this)
                    .load(simage).fitCenter().override(1000, 1000).into(image);
        //}

         // Toast.makeText(SingleActivity.this,simage,Toast.LENGTH_SHORT).show();
    }

    public void redirect(View V)
    {
         try {
             String linkto = link.getText().toString();
             Intent videointent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkto));
             startActivity(videointent);
         }
         catch(Exception e)
        {
            Toast.makeText(SingleActivity.this,"Sorry,Website Under Maintenance",Toast.LENGTH_SHORT).show();
        }

        //URL url=(URL)linkto;
        //videointent.setData(url);
    }

    public boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            return true;
        }
        return false;
    }

    public long downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {





            DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        return downloadmanager.enqueue(request);
    }
}
