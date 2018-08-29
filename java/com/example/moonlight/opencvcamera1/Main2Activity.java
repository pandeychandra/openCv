package com.example.moonlight.opencvcamera1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;

public class Main2Activity extends AppCompatActivity {
    Bitmap imageBitmap,grayBitmap;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView=(ImageView)findViewById(R.id.imageView);
        OpenCVLoader.initDebug();
    }
    public void gallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri filePath = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(imageBitmap);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        Button butto=findViewById(R.id.button2);
        butto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grayy(v);
            }
        });
    }

    private void grayy(View v) {
       Mat rbg=new Mat();
       

        BitmapFactory.Options o=new BitmapFactory.Options();
        o.inSampleSize=4;
        o.inDither=false;

        int width=imageBitmap.getWidth();
        int height=imageBitmap.getHeight();

        grayBitmap=Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565);

        Utils.bitmapToMat(imageBitmap,rbg);
        Mat grayMat = new Mat();
        try {
            Imgproc.cvtColor(rbg, grayMat, Imgproc.COLOR_RGB2GRAY);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Utils.matToBitmap(grayMat,grayBitmap);
        imageView.setImageBitmap(grayBitmap);

    }


}
