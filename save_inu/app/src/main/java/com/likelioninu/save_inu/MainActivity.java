package com.likelioninu.save_inu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private String currentPage;
    private long pressedtime;
    private boolean constant;
    private boolean sessiondelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.main_activityScreen);
        progressBar = findViewById(R.id.loading);


        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);

        ///////////////////////////////


        ////////////////////////////////

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        SharedPreferences sp2 = getSharedPreferences("WW2", Context.MODE_PRIVATE);
        String url = sp2.getString("currentURL", "https://save-inu-hyuuuub.c9users.io/"); //첫 화면은 root 페이지로
        constant = sp2.getBoolean("booleanValue", true);
        sessiondelete = sp2.getBoolean("session", false); //세션 여부 판별(처음에는 false 값으로 session이 없음)

        if(constant==true){//앱이 첫 실행됨
            constant=false;
            SharedPreferences sp = getSharedPreferences("WW2", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("booleanValue", constant); //이후 첫 실행 아님을 판별
            editor.commit();

            if(sessiondelete == true){//session이 있으므로 쿠키삭제
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeSessionCookie();
            }
        }


        client();
        webView.loadUrl(url);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int gaze) {
                if(gaze<100){
                    progressBar.setVisibility(progressBar.VISIBLE);
                }
                else if(gaze == 100){
                    progressBar.setVisibility(progressBar.GONE);
                }
                else{
                    progressBar.setProgress(gaze);
                }
            }
        });

    }

    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected void onDestroy() {
        super.onDestroy();

        String url = webView.getUrl();
        SharedPreferences sp = getSharedPreferences("WW2", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("currentURL", url); //종료 시 현재 url 저장
        edit.commit();
    }

    ////////////////////////////////////////////////////////////////////////////////

    public void client(){
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onBackPressed() { //2번 누르면 어플 종료

        String currentUrl = webView.getUrl();
        Toast toast = null;

        if(webView.getUrl().equalsIgnoreCase(currentUrl)||webView.getUrl().equalsIgnoreCase(currentUrl)){
            if (System.currentTimeMillis() >= pressedtime + 2000) {
                pressedtime = System.currentTimeMillis();
                toast = Toast.makeText(this, "종료해도 시간은 간다구욧!!", Toast.LENGTH_SHORT); //한번 클릭 시 토스트문구
                toast.show();
                return;
            } else {

                if(toast != null){
                    toast.cancel();
                }

                super.onBackPressed();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////
}
