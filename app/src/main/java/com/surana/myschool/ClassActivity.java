package com.surana.myschool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.surana.myschool.adpter.AdapterMessage;
import com.surana.myschool.item.ItemMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ClassActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 200;
    ImageButton btn_back,btn_select_image,btn_send;
    Button btnSendImage,btnSendImageLayoutBack,btn_nameClass;
    LinearLayout main_layout,sendImage_layout,send_layout;
    EditText message;
    FirebaseUser mUser;
    private static final String TAG = "My App";
    String tokenClass =null,class_name=null,name=null;
    Uri select;
    ImageView imageView;
    DatabaseReference mRef;
    StorageReference mStorageRef;
    ProgressDialog progressDialog;
    String year,mouth,day;
    RecyclerView message_recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        send_layout = findViewById(R.id.send_layout);
        main_layout = findViewById(R.id.class_main);
        sendImage_layout = findViewById(R.id.send_image_layout);

        message_recycle = findViewById(R.id.recycler_message_view);

        progressDialog = new ProgressDialog(ClassActivity.this);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference("image");

        message = findViewById(R.id.class_activity_message);

        btn_back = findViewById(R.id.class_back_btn);
        btn_nameClass =findViewById(R.id.class_name_btn);
        btn_select_image = findViewById(R.id.class_activity_btn_select_image);
        btn_send = findViewById(R.id.class_activity_btn_message);
        btnSendImage = findViewById(R.id.send_image_btn);
        btnSendImageLayoutBack = findViewById(R.id.send_cancel_btn);

        imageView = findViewById(R.id.image_view);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClassActivity.this,MainActivity.class));
                finish();
            }
        });


        getInit();
        getMessage();
        getUsername();
        getCurrentDay();

        tokenClass = getIntent().getStringExtra("token");
        class_name = getIntent().getStringExtra("name");
        btn_nameClass.setText(class_name);

        
        btn_select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSelect();
            }
        });

        btnSendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendImage();
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(message.getText().toString())){
                    sendMessage(message.getText().toString(),"text");
                    message.setText("");
                }
            }
        });

        btnSendImageLayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_layout.setVisibility(View.VISIBLE);
                sendImage_layout.setVisibility(View.GONE);
                select = null;
            }
        });

    }

    private void getInit() {
    }

    private void getUsername() {
        mRef.child("users").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                name = snapshot.child("username").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void sendImage() {
        if (select !=null){
            btnSendImage.setClickable(false);
            btnSendImageLayoutBack.setClickable(false);

            progressDialog.setTitle("Uploading....");
            progressDialog.show();

            String name = select.getLastPathSegment();
            String extension = name.substring(name.lastIndexOf("."));
            StorageReference storageReference = mStorageRef.child(getSaltString(20)+extension);
            UploadTask uploadTask = storageReference.putFile(select);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> downloadUrl = storageReference.getDownloadUrl();

                    downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            sendMessage(uri.toString(),"image");
                        }
                    });
                    dismissProgress();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress( UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressDialog.setTitle(progress + "% Uploaded");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dismissProgress();
                }
            });

        }
    }

    private void getMessage() {

        mRef.child("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                ArrayList<ItemMessage> messageArrayList = new ArrayList<>();

                for(DataSnapshot dataSnapshot : snapshot.child(tokenClass).getChildren()){
                    String type = dataSnapshot.child("type").getValue().toString();
                    String hour = dataSnapshot.child("hour").getValue().toString();
                    String min = dataSnapshot.child("min").getValue().toString();
                    String date = dataSnapshot.child("date").getValue().toString();
                    String message = dataSnapshot.child("message").getValue().toString();
                    String send_by = dataSnapshot.child("send_by").getValue().toString();
                    String uid_send_by = dataSnapshot.child("uid_send_by").getValue().toString();
                    String token = dataSnapshot.child("token").getValue().toString();

                    Boolean me = false;
                    if (mUser.getUid().equals(uid_send_by)){
                        me = true;
                    }
                    messageArrayList.add(new ItemMessage(message,send_by,token,type,hour+":"+min,"Date :-"+date,me));

                }

                message_recycle.setLayoutManager(new LinearLayoutManager(ClassActivity.this));
                AdapterMessage adapterMessage = new AdapterMessage(messageArrayList,ClassActivity.this);
                LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                Collections.sort(messageArrayList, Comparator.comparing(ItemMessage::getTime));
                message_recycle.setLayoutManager(linearLayout);
                message_recycle.setAdapter(adapterMessage);
                message_recycle.scrollToPosition(messageArrayList.size()-1);

                adapterMessage.notifyDataSetChanged();
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });

    }

    private void sendMessage(String message,String type) {
        String sendMessage = message;
        String send_by = mUser.getUid();
        String token = getSaltString(25);

        Map<String,String> hashMap =new HashMap<>();

        hashMap.put("message",sendMessage);
        hashMap.put("uid_send_by",send_by);
        hashMap.put("send_by",name);
        hashMap.put("token",token);
        hashMap.put("type",type);
        hashMap.put("hour", getCurrentTimeHour());
        hashMap.put("min", getCurrentTimeMin());
        hashMap.put("date", day+"/"+mouth+"/"+year);

        mRef.child("message").child(tokenClass).child(token).setValue(hashMap);
    }

    private void dismissProgress() {
        progressDialog.dismiss();
        btnSendImage.setClickable(true);
        btnSendImageLayoutBack.setClickable(true);
        send_layout.setVisibility(View.VISIBLE);
        sendImage_layout.setVisibility(View.GONE);
    }


    private void imageSelect() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE && data.getData() != null){
            select = data.getData();
            send_layout.setVisibility(View.GONE);
            sendImage_layout.setVisibility(View.VISIBLE);
            imageView.setImageURI(select);
        }

    }
    protected String getSaltString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    public String getCurrentTimeHour() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH");
        String strDate = mdformat.format(calendar.getTime());
        return  strDate;
    }
    public String getCurrentTimeMin() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("mm");
        String strDate = mdformat.format(calendar.getTime());
        return  strDate;
    }
    private void getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
        year = dateFormatYear.format(cal.getTime());
        SimpleDateFormat dateFormatMouth = new SimpleDateFormat("MM");
        mouth = dateFormatMouth.format(cal.getTime());
        SimpleDateFormat dateFormatDay = new SimpleDateFormat("dd");
        day = dateFormatDay.format(cal.getTime());
        //   mDaySelect.setText(day+" / "+mouth+" / "+year);
    }
}