package com.example.myapplication;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.utils.certModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BoxActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public String parent="",grand="",sfrommain="",grand1="",grand2="";

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;

    //@BindView(R.id.toolbar)
    //Toolbar toolbar;
    private Toolbar toolbar;

    // @BindView(R.id.swipe_refresh_recycler_list)
    // SwipeRefreshLayout swipeRefreshRecyclerList;

    private SwipeRefreshLayout swipeRefreshRecyclerList;
    private RecyclerViewAdapterTrainCert mAdapter;

    private ArrayList<certModel> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box);
        findViews();

        if(connectedToNetwork()){
            volley();
        }else{ NoInternetAlertDialog(); }
        swipeRefreshRecyclerList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Do your stuff on refresh
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (swipeRefreshRecyclerList.isRefreshing())
                            swipeRefreshRecyclerList.setRefreshing(false);
                    }
                }, 3000);

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar1);
        recyclerView = findViewById(R.id.recycler_view);
        swipeRefreshRecyclerList = findViewById(R.id.swipe_refresh_recycler_list);
        int c1=getResources().getColor(R.color.colorAccent);
        swipeRefreshRecyclerList.setColorSchemeColors(c1);
    }

    public void initToolbar(String title) {
        //String s=sfrommain;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
           // if (s.contains("+"))
             //   s = s.replace("+", "");

        getSupportActionBar().setTitle(title.toUpperCase());
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_search, menu);


        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(menu.findItem(R.id.action_search));

        SearchManager searchManager = (SearchManager) this.getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

        //changing edittext color
        EditText searchEdit = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEdit.setTextColor(Color.WHITE);
        searchEdit.setHintTextColor(Color.WHITE);
        searchEdit.setBackgroundColor(Color.TRANSPARENT);
        searchEdit.setHint("Search");

        InputFilter[] fArray = new InputFilter[2];
        fArray[0] = new InputFilter.LengthFilter(40);
        fArray[1] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {

                    if (!Character.isLetterOrDigit(source.charAt(i)))
                        return "";
                }


                return null;


            }
        };
        searchEdit.setFilters(fArray);
        View v = searchView.findViewById(androidx.appcompat.R.id.search_plate);
        v.setBackgroundColor(Color.TRANSPARENT);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println("change "+s);

                ArrayList<certModel> filterList = new ArrayList<certModel>();
                if (s.length() > 0) {
                    for (int i = 0; i < modelList.size(); i++) {
                        if (modelList.get(i).getTitle().toLowerCase().contains(s.toLowerCase())) {
                            filterList.add(modelList.get(i));
                            mAdapter.updateList(filterList);
                        }
                    }

                } else {

                    mAdapter.updateList(modelList);
                }
                return false;
            }
        });


        return true;
    }*/

    private void setAdapter() {




        mAdapter = new RecyclerViewAdapterTrainCert(BoxActivity.this, modelList);



        final GridLayoutManager layoutManager = new GridLayoutManager(BoxActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);



        mAdapter.SetOnItemClickListener(new RecyclerViewAdapterTrainCert.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, certModel model) {

                //handle item click events here
               if(model.getAddetails().equals("box"))
               {
                   String s = model.getTitle();
                   //Toast.makeText(BoxActivity.this,grand+"+"+s+"+"+sfrommain,Toast.LENGTH_SHORT).show();
                  Intent inten = (new Intent(BoxActivity.this, BoxActivity.class));
                   inten.putExtra("grand1",grand1);
                   inten.putExtra("grand",grand);
                   inten.putExtra("title","+"+ s);
                   inten.putExtra("Parent",sfrommain);
                   startActivity(inten);
               }
               else if(model.getAddetails().equals("list"))
               {
                   String tit=model.getTitle();
                   String img=model.getImage();
                  // Toast.makeText(BoxActivity.this,grand,Toast.LENGTH_SHORT).show();
                   Intent inten=new Intent(BoxActivity.this,ListDispActivity.class);
                   inten.putExtra("grand",grand);
                   inten.putExtra("title",tit);
                   inten.putExtra("image",img);
                   inten.putExtra("frommain",sfrommain);
                  
                   startActivity(inten);

               }
               else if(model.getAddetails().equals("single"))
               {
                   String tit=model.getTitle();
                   String img=model.getImage();
                   Intent inten=new Intent(BoxActivity.this,SingleActivity.class);
                   inten.putExtra("image",img);
                   inten.putExtra("title",tit);
                   inten.putExtra("parent",sfrommain);
                   inten.putExtra("grand",parent);
                   inten.putExtra("grand1",grand);
                   inten.putExtra("grand2",grand1);
                   startActivity(inten);
                  // Toast.makeText(BoxActivity.this,grand2+"+"+"+"+grand1+"+"+parent+"+"+sfrommain+"+"+tit,Toast.LENGTH_SHORT).show();
               }



            }
        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
                if(connectedToNetwork()){
                    volley();
                }else{ NoInternetAlertDialog(); }
            }
        });
        builder.setNegativeButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent openSettings = new Intent();
                openSettings.setAction(Settings.ACTION_SETTINGS);
                openSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(openSettings);
            }
        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void volley() {


        sfrommain=getIntent().getStringExtra("title");

        modelList=new ArrayList<certModel>();
        System.out.println("in t  "+sfrommain);


        if(sfrommain.equals("health") || sfrommain.equals("education") || sfrommain.equals("awareness") || sfrommain.equals("agriculture")) {
            initToolbar(sfrommain);
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(sfrommain);

            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    modelList.clear();
                    for (DataSnapshot d1 : dataSnapshot.getChildren()) {
                        //String chi=d1.get();
                        certModel obj = d1.getValue(certModel.class);


                       // Toast.makeText(BoxActivity.this,obj.toString(),Toast.LENGTH_SHORT).show();
                        modelList.add(obj);
                        System.out.println("in act  " + obj.getImage());
                        //Toast.makeText(BoxActivity.this,d1.getKey(),Toast.LENGTH_SHORT).show();


                    }
                    setAdapter();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        // ButterKnife.bind(this);

        else {
            grand=getIntent().getStringExtra("grand");
            if(grand.equals(""))
            {
                parent = getIntent().getStringExtra("Parent");
                //String child="";
                sfrommain = sfrommain.replace("+", "");
                initToolbar(sfrommain);
                grand = parent;
                //Toast.makeText(BoxActivity.this,grand+"hai",Toast.LENGTH_SHORT).show();
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(parent).child(sfrommain);
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        modelList.clear();
                        for (DataSnapshot d1 : dataSnapshot.getChildren()) {
                            //String chi=d1.get();
                            if (!d1.getKey().equals("addetails") && !d1.getKey().equals("title") && !d1.getKey().equals("image")) {
                                certModel obj = d1.getValue(certModel.class);


                                modelList.add(obj);
                                System.out.println("in act  " + obj.getImage());
                                //Toast.makeText(BoxActivity.this, d1.getKey(), Toast.LENGTH_SHORT).show();
                            }

                        }
                        setAdapter();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
            else
            {
                grand1=getIntent().getStringExtra("grand1");

                parent = getIntent().getStringExtra("Parent");
                //String child="";
                if(sfrommain.contains("+"))
                sfrommain = sfrommain.replace("+", "");

                 DatabaseReference db;
                //Toast.makeText(BoxActivity.this,grand1+"+"+grand+"+"+parent+"+"+sfrommain,Toast.LENGTH_SHORT).show();
                if(grand1.equals("")) {
                    db = FirebaseDatabase.getInstance().getReference().child(grand).child(parent).child(sfrommain);
                    initToolbar(sfrommain);
                   // Toast.makeText(BoxActivity.this,grand1+"+"+grand+"+"+parent+"+"+sfrommain,Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        parent=getIntent().getStringExtra("parent");
                        sfrommain=getIntent().getStringExtra("title");
                        String name=getIntent().getStringExtra("name");
                        initToolbar(name);

                        db = FirebaseDatabase.getInstance().getReference().child(grand1).child(grand).child(parent).child(sfrommain);

                    }
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        modelList.clear();
                        for (DataSnapshot d1 : dataSnapshot.getChildren()) {
                            //String chi=d1.get();
                            if (!d1.getKey().equals("addetails") && !d1.getKey().equals("title") && !d1.getKey().equals("image") && !d1.getKey().equals("name"))
                            {
                                certModel obj = d1.getValue(certModel.class);


                               modelList.add(obj);
                                //System.out.println("in act  " + obj.getImage());
                                // Toast.makeText(BoxActivity.this, d1.getKey(), Toast.LENGTH_SHORT).show();
                            }

                        }
                        setAdapter();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        }
        setAdapter();

    }

}
