package com.paperwrk.gdgjsshackathon;

import android.annotation.TargetApi;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import static com.paperwrk.gdgjsshackathon.utils.ApiKey.YOUTUBE_API_KEY;


public class ActivityPlayer extends AppCompatActivity
        implements YouTubePlayer.OnInitializedListener {

    String youtube_videoID;
    private RelativeLayout mContainer;
    private ProgressBar progressBar;
    private String postUrl;
    private WebView mWebViewComments;
    boolean isLoading;
    private WebView mWebviewPop;
    private static final int NUMBER_OF_COMMENTS = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);

        youtube_videoID = getIntent().getExtras().getString("videoID");
        mWebViewComments =  findViewById(R.id.commentsView);
        //mWebViewComments.setOnTouchListener(this);
        mContainer =  findViewById(R.id.webview_frame);
        progressBar =  findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //contentView = (RelativeLayout) findViewById(R.id.rootView);

        String comment_url = youtube_videoID;
        postUrl = "https://www.google.com/watch?v="+comment_url;

        YouTubePlayerSupportFragment frag = (YouTubePlayerSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.youtube_fragment);
        frag.initialize(YOUTUBE_API_KEY,this);
        setLoading(true);
        loadComments();


    }


    private void loadComments() {
        mWebViewComments.setWebViewClient(new UriWebViewClient());
        mWebViewComments.setWebChromeClient(new UriChromeClient());
        mWebViewComments.getSettings().setJavaScriptEnabled(true);
        mWebViewComments.getSettings().setAppCacheEnabled(true);
        mWebViewComments.getSettings().setDomStorageEnabled(true);
        mWebViewComments.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebViewComments.getSettings().setSupportMultipleWindows(true);
        mWebViewComments.getSettings().setSupportZoom(false);
        mWebViewComments.getSettings().setBuiltInZoomControls(false);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            mWebViewComments.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebViewComments, true);
        }

        // facebook comment widget including the article url
        String html = "<!doctype html> <html lang=\"en\"> <head></head> <body> " +
                "<div id=\"fb-root\"></div> <script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = \"//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6\"; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'facebook-jssdk'));</script> " +
                "<div class=\"fb-comments\" data-href=\"" + postUrl + "\" " +
                "data-numposts=\"" + NUMBER_OF_COMMENTS + "\" data-order-by=\"reverse_time\">" +
                "</div> </body> </html>";

        mWebViewComments.loadDataWithBaseURL("http://www.nothing.com", html, "text/html", "UTF-8", null);
        mWebViewComments.setMinimumHeight(200);
    }

    private void setLoading(boolean isLoading) {
        this.isLoading = isLoading;

        if (isLoading)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);

        invalidateOptionsMenu();
    }



    private class UriWebViewClient extends WebViewClient {
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String host = Uri.parse(url).getHost();
            return !host.equals("m.facebook.com");

        }
         @TargetApi(Build.VERSION_CODES.N)
         @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
             String host = Uri.parse(String.valueOf(request)).getHost();
             return !host.equals("m.facebook.com");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String host = Uri.parse(url).getHost();
            if (url.contains("/plugins/close_popup.php?reload")) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        mContainer.removeView(mWebviewPop);
                        loadComments();
                    }
                }, 600);
            }
            setLoading(false);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            setLoading(false);
        }
    }

    class UriChromeClient extends WebChromeClient {
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg) {
            mWebviewPop = new WebView(getApplicationContext());
            mWebviewPop.setVerticalScrollBarEnabled(false);
            mWebviewPop.setHorizontalScrollBarEnabled(false);
            mWebviewPop.setWebViewClient(new UriWebViewClient());
            mWebviewPop.setWebChromeClient(this);
            mWebviewPop.getSettings().setJavaScriptEnabled(true);
            mWebviewPop.getSettings().setDomStorageEnabled(true);
            mWebviewPop.getSettings().setSupportZoom(false);
            mWebviewPop.getSettings().setBuiltInZoomControls(false);
            mWebviewPop.getSettings().setSupportMultipleWindows(true);
            mWebviewPop.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            mContainer.addView(mWebviewPop);
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(mWebviewPop);
            resultMsg.sendToTarget();

            return true;
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage cm) {
            return true;
        }

        @Override
        public void onCloseWindow(WebView window) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebViewComments.clearCache(true);
    }



    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            youTubePlayer.loadVideo(youtube_videoID);
            youTubePlayer.setShowFullscreenButton(true);
        }
    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getApplicationContext(),
                "Unable to Load the Video! Please Try Again",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}