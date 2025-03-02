package com.uwillno.uwb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.ClientCertRequest;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import android.Manifest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    WebView webView;



////            , saveContent;
//    // 成员变量来存储二进制数据
//    private byte[] data;
//    private String mimeType;  // 用于存储 MIME 类型
//
//    // for st
    private boolean cannotBack = false;
    //    private final int FILE_CHOOSER_REQUEST_CODE = 1;
//    private final int CAMERA_PERMISSION_REQUEST_CODE = 2;
    private ValueCallback<Uri[]> fileUploadCallback = null;
//    private Uri currentPhotoUri;
    private final ActivityResultLauncher<String> getContent =  registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            result -> {
                if (fileUploadCallback != null) {
                    Uri[] results = null;
                    if (result != null) {
                        results = new Uri[]{result};
                    }
                    // 触发文件选择回调
                    fileUploadCallback.onReceiveValue(results);
                    fileUploadCallback = null; // 处理完毕后清空回调
                }

            }
    );
//    // 声明 ActivityResultLauncher
//    private final ActivityResultLauncher<Intent> cameraLauncher =
//            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//                if (result.getResultCode() == RESULT_OK) {
//                    Intent data = result.getData();
//                    Uri[] results = null;
//                    if (data != null && data.getData() != null) {
//                        results = new Uri[]{data.getData()};
//                    } else if (currentPhotoUri != null) {
//                        results = new Uri[]{currentPhotoUri};
//                    }
//
//                    if (fileUploadCallback != null) {
//                        fileUploadCallback.onReceiveValue(results);
//                    }
//                    fileUploadCallback = null;
//                } else {
//                    if (fileUploadCallback != null) {
//                        fileUploadCallback.onReceiveValue(null);
//                    }
//                    fileUploadCallback = null;
//                }
//            });
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                launchCamera();
//            } else {
//                // Permission denied, show an error or request permission again
//                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void getCameraPermission() {
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
    }

    //重写onKeyDown()方法,继承自退出的方法
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (webView.canGoBack() && !cannotBack) {
                webView.goBack();
            } else {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AndroidBug5497Workaround.assistActivity(this);
                //        getContent = registerForActivityResult(
//                new ActivityResultContracts.GetContent(),
//                result -> {
//                    // 处理返回的 URI
//                    if (fileUploadCallback != null) {
//                        Uri[] results = null;
//                        // 处理返回的文件 Uri
//                        if (result != null) {
//                            results = new Uri[]{result};
//                        } else if (currentPhotoUri != null) {
//                            results = new Uri[]{currentPhotoUri};
//                        }
//                        // 触发文件选择回调
//                        if (results != null) {
//                            fileUploadCallback.onReceiveValue(results);
//                        }
//                        fileUploadCallback = null;
//                    }
//
//                }
//        );
//        saveContent = registerForActivityResult(
//
//                new ActivityResultContracts.CreateDocument("*/*"),  // 新的创建文件契约
//                uri -> {
//                    if (uri != null) {
//
//                        // 用户选择文件保存路径并返回了 URI
////                            writeToFile(uri);
//                    }
//                }
//        );


        webView = findViewById(R.id.webview);
//        if(url.)
//        webView.loadUrl("https://uwillno.com");
        Bundle bundle = getIntent().getBundleExtra("UWillno");
//        bundle.();
//        getIntent().putExtra()
        if (bundle != null) {
            String url = bundle.getString("url");
            cannotBack = bundle.getBoolean("back");
            if (!TextUtils.isEmpty(url)) {
                webView.loadUrl(url);
            } else
                webView.loadUrl("https://uwillno.com");
        } else {
//            webView.loadUrl("https://st.uwillno.com");
//                webView.loadDataWithBaseURL("https://st.uwillno.com");
        }

        // 添加 JavaScript 接口
//        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.setWebViewClient(new WebViewClient());
        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
//        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setDatabaseEnabled(true);
//        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");

//        settings.setCacheMode();
        webView.setDownloadListener((url, userAgent, contentDisposition, mimeType, contentLength) -> {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);

//            if (url.startsWith("blob:")) {
//                saveContent.launch("text/plain");
                // blob URL，提取其内容并保存为文件
                // 需要通过 WebView 执行 JavaScript 提取 blob 数据并保存到文件
//                String realUrl = url.replace("blob:", "");
//                webView.evaluateJavascript(
//                        "(function(url) {" +
//                                "  var xhr = new XMLHttpRequest();" +
//                                "  xhr.open('GET', url, true);" +
//                                "  xhr.responseType = 'blob';" +
//                                "  xhr.setRequestHeader('Content-type','" + mimeType +";charset=UTF-8');" +
//                                "  xhr.onload = function() {" +
//                                "    if (xhr.status === 200) {" +
//                                "      var blob = xhr.response;" +
//                                "      var reader = new FileReader();" +
//                                "      reader.onloadend = function() {" +
//                                "        var dataURL = reader.result;" +
//                                "        window.Android.saveBlobToFile(dataURL);" + // Android 层方法
//                                "      };" +
//                                "      reader.readAsDataURL(blob);" +
//                                "    }" +
//                                "  };" +
//                                "  xhr.onerror = function() {" +
//                                "    console.error('Error fetching blob');" +
//                                "  console.log('Request failed with status: ' + xhr.status);" +
//                                "  console.log('Status Text: ' + xhr.statusText,url);" +
//                                "  };" +
//                                "  xhr.send();" +
//                                "})('" + url + "');",
//                        null
//                );


//            } else if (url.startsWith("http") || url.startsWith("https")) {
//                // 普通的 HTTP/HTTPS URL，使用 DownloadManager 下载
//                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//                request.setTitle("下载中……");
//                request.setDescription("下载文件");
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "downloaded_file");
//
//                DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//                if (manager != null) {
//                    manager.enqueue(request);  // 添加到下载队列
//                }
//            }
        });
//        Log.d("uwsb", WebView.getCurrentWebViewPackage().versionName);
        webView.setWebChromeClient(
                new WebChromeClient() {

                    @Override
                    public void onPermissionRequest(PermissionRequest request) {
//                        Log.d("uwsb",request.toString());
                        if (request.getResources().length == 1 && Objects.equals(request.getResources()[0], PermissionRequest.RESOURCE_VIDEO_CAPTURE) && hasCameraPermission()) {
                            request.grant(request.getResources());
                        } else {
                            Toast.makeText(MainActivity.this, "无相机权限", Toast.LENGTH_SHORT).show();
                            getCameraPermission();
//                            super.onPermissionRequest(request);
                        }
                    }

                    @Override
                    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//                        Log.d("uwsb", fileChooserParams.toString() + filePathCallback.toString());

                        // 清除先前的回调，避免回调泄漏
                        if (fileUploadCallback != null) {
                            fileUploadCallback.onReceiveValue(null);
                        }

                        // 更新当前的回调
                        fileUploadCallback = filePathCallback;

                        // 获取接受的文件类型
                        String[] acceptTypes = fileChooserParams.getAcceptTypes();

                        // 判断是否是图片类型
                        if (acceptTypes != null && Arrays.asList(acceptTypes).contains("image/*")) {
                            // 启动图片选择器
                            getContent.launch("image/*");
                        } else {
                            // 启动文件选择器，支持所有类型
                            getContent.launch("*/*");
                        }

                        return true;  // 表示我们已经处理了文件选择
                    }
                }

        );


    }


//    // 创建 WebAppInterface 内部类
//    public class WebAppInterface {
//        private Context context;
//
//        WebAppInterface(Context c) {
//            context = c;
//        }
//
//        @JavascriptInterface
//        public void saveBlobToFile(String base64) {
//            Log.d("uwsb",base64);
//            data = Base64.decode(base64, Base64.DEFAULT);
//
//        }
//    }

}
