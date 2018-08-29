package com.example.moonlight.opencvcamera1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public  class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {


CameraBridgeViewBase  javaCameraView;
    TextView tv;
    Mat mat1,mat2,mat3;
    BaseLoaderCallback baseLoaderCallback;

    // Used to load the 'native-lib' library on application startup.
    static {
        try {
          System.loadLibrary("native-lib");
        }catch (Exception e)
        {
            e.printStackTrace();
        }



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
         tv = (TextView) findViewById(R.id.text);
        javaCameraView = (JavaCameraView) findViewById(R.id.javaCamera);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
       javaCameraView.setCvCameraViewListener(this);
       baseLoaderCallback=new BaseLoaderCallback(this) {
           @Override
           public void onManagerConnected(int status) {

               switch (status)
               {
                   case BaseLoaderCallback.SUCCESS:
                       javaCameraView.enableView();
                       break;

                       default:
                           super.onManagerConnected(status);
                           break;

               }
           }
       };

    }
    public void click(View view)
    {
        Intent i=new Intent(MainActivity.this,Main2Activity.class);
        startActivity(i);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mat1=new Mat(width,height,CvType.CV_8UC4);
        mat2=new Mat(width,height,CvType.CV_8UC4);

        mat3=new Mat(width,height,CvType.CV_8UC4);



    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        mat1=inputFrame.rgba();
        return mat1;
    }

    @Override
    public void onCameraViewStopped() {
        mat1.release();


    }



    @Override
    protected void onPause() {
        super.onPause();
        if (javaCameraView != null) {
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (javaCameraView != null) {
            javaCameraView.disableView();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()) {
            tv.setText(tv.getText()+"\n+ open cv is loaded");
            baseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS);

        } else {



        }
    }


        public native String stringFromJNI();

}
