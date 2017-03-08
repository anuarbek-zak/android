package com.example.anuarbek.readfilefromserver;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    File ourFolder;
    EditText url;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = (EditText) findViewById(R.id.url);

        if(Build.VERSION.SDK_INT>=19){
            ourFolder = getFilesDir();
        }else{
            ourFolder = new
                    File(Environment.getRootDirectory()
                    .getAbsolutePath());
        }
        if(!ourFolder.exists()){
            ourFolder.mkdirs();
        }
        Log.d("ourfolder", ourFolder.listFiles().length+"");
        for(File file:ourFolder.listFiles()){
            Log.d("My", file.getName()+" "+file.getAbsolutePath()+" "+file.isDirectory());
        }

        Button download = (Button) findViewById(R.id.download);
        Button show = (Button) findViewById(R.id.show);
        tv = (TextView) findViewById(R.id.textView);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(url.getText().equals("")) return;
                String mUrl=url.getText().toString();
                InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, mUrl,
                        new Response.Listener<byte[]>() {
                            @Override
                            public void onResponse(byte[] response) {
                                // TODO handle the response
                                try {
                                    Log.d("response",String.valueOf(response));
                                    if (response!=null) {
                                        FileOutputStream outputStream;
                                        String uniqueName =(new Date().getTime())+".docx";
                                        Log.d("filename",String.valueOf(uniqueName));
                                        outputStream = openFileOutput(uniqueName, Context.MODE_PRIVATE);
                                        Log.d("ourfolder2", ourFolder.listFiles().length+"");
                                        outputStream.write(response);
                                        outputStream.close();
                                        Toast.makeText(MainActivity.this, "Download complete.", Toast.LENGTH_LONG).show();
                                        for(File file:ourFolder.listFiles()){
                                            Log.d("My", file.getName()+" "+file.getAbsolutePath()+" "+file.isDirectory());
                                        }
                                    }
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                                    e.printStackTrace();
                                }
                            }
                        } ,new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO handle the error
                        error.printStackTrace();
                    }
                },
                        null);
                RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
                mRequestQueue.add(request);

            }

        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File[] arr =  ourFolder.listFiles();
                File f = arr[arr.length-1];
                try {
                    Log.d("filename",f.getName().toString());
                    FileInputStream fis = MainActivity.this.openFileInput(f.getName().toString());
                    InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        Log.d("line",line);
                        sb.append(line).append("\n");
                    }
                    tv.setText(sb);
                } catch (FileNotFoundException e) {
                } catch (UnsupportedEncodingException e) {
                } catch (IOException e) {
                }

            }
        });
    }
}



class InputStreamVolleyRequest extends Request<byte[]> {
    private final Response.Listener<byte[]> mListener;
    private Map<String, String> mParams;

    //create a static map for directly accessing headers
    public Map<String, String> responseHeaders ;

    public InputStreamVolleyRequest(int method, String mUrl ,Response.Listener<byte[]> listener,
                                    Response.ErrorListener errorListener, HashMap<String, String> params) {
        // TODO Auto-generated constructor stub

        super(method, mUrl, errorListener);
        // this request would never use cache.
        setShouldCache(false);
        mListener = listener;
        mParams=params;
    }

    @Override
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return mParams;
    };


    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {

        //Initialise local responseHeaders map with response headers received
        responseHeaders = response.headers;

        //Pass the response data here
        return Response.success( response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}
