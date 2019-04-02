//package com.moust.cordova.videoplayer;
//
//import android.annotation.TargetApi;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnCancelListener;
//import android.content.DialogInterface.OnDismissListener;
//import android.content.res.AssetFileDescriptor;
//import android.content.res.Resources;
//import android.graphics.Color;
//import android.graphics.PorterDuff;
//import android.graphics.Rect;
//import android.graphics.drawable.Drawable;
//import android.media.MediaPlayer;
//import android.media.MediaPlayer.OnCompletionListener;
//import android.media.MediaPlayer.OnErrorListener;
//import android.media.MediaPlayer.OnPreparedListener;
//import android.net.Uri;
//import android.os.Build;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.Display;
//import android.view.Gravity;
//import android.view.SurfaceHolder;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.WindowManager.LayoutParams;
//import android.widget.FrameLayout;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.VideoView;
//
//import org.apache.cordova.CallbackContext;
//import org.apache.cordova.CordovaArgs;
//import org.apache.cordova.CordovaPlugin;
//import org.apache.cordova.CordovaResourceApi;
//import org.apache.cordova.PluginResult;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class VideoPlayer extends CordovaPlugin implements OnCompletionListener, OnPreparedListener, OnErrorListener, OnDismissListener {
//
//    protected static final String LOG_TAG = "VideoPlayer";
//
//    protected static final String ASSETS = "/android_asset/";
//
//    private CallbackContext callbackContext = null;
//
//    private Dialog dialog;
//
//    private VideoView videoView;
//    private ViewGroup.LayoutParams originalWebViewParams;
//    private MediaPlayer player;
//
//    /**
//     * Executes the request and returns PluginResult.
//     *
//     * @param action        The action to execute.
//     * @param args          JSONArray of arguments for the plugin.
//     * @param callbackId    The callback id used when calling back into JavaScript.
//     * @return              A PluginResult object with a status and message.
//     */
//    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
//        if (action.equals("play")) {
//            this.callbackContext = callbackContext;
//
//            CordovaResourceApi resourceApi = webView.getResourceApi();
//            String target = args.getString(0);
//            final JSONObject options = args.getJSONObject(1);
//
//            String fileUriStr;
//            try {
//                Uri targetUri = resourceApi.remapUri(Uri.parse(target));
//                fileUriStr = targetUri.toString();
//            } catch (IllegalArgumentException e) {
//                fileUriStr = target;
//            }
//
//            Log.v(LOG_TAG, fileUriStr);
//
//            final String path = stripFileProtocol(fileUriStr);
//
//            // Create dialog in new thread
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                public void run() {
//                    openVideoDialog(path, options);
//                }
//            });
//
//            // Don't return any result now
//            PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
//            pluginResult.setKeepCallback(true);
//            callbackContext.sendPluginResult(pluginResult);
//
//            return true;
//        }
//        else if (action.equals("close")) {
//            if(player != null && player.isPlaying()) {
//                player.stop();
//                player.release();
//            }
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                public void run() {
//                    if (originalWebViewParams != null) {
//                        View localWebViewRef = webView.getView();
//                        localWebViewRef.setLayoutParams(originalWebViewParams);
//                    }
//                    if (videoView != null) {
//                        ((ViewGroup) videoView.getParent()).removeView(videoView);
//                    }
//                }
//            });
//            if (callbackContext != null) {
//                PluginResult result = new PluginResult(PluginResult.Status.OK);
//                result.setKeepCallback(false); // release status callback in JS side
//                callbackContext.sendPluginResult(result);
//                callbackContext = null;
//            }
//
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * Removes the "file://" prefix from the given URI string, if applicable.
//     * If the given URI string doesn't have a "file://" prefix, it is returned unchanged.
//     *
//     * @param uriString the URI string to operate on
//     * @return a path without the "file://" prefix
//     */
//    public static String stripFileProtocol(String uriString) {
//        if (uriString.startsWith("file://")) {
//            return Uri.parse(uriString).getPath();
//        }
//        return uriString;
//    }
//
//    protected int getStatusBarHeight() {
//        float statusBarHeight = 24;
//        return (int) convertPixelsToDp(statusBarHeight, cordova.getContext());
//    }
//
//    public static double convertPixelsToDp(float px, Context context){
//        float density = context.getResources().getDisplayMetrics().density + 0.5f;
//        return (double) (px / density);
//    }
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    protected void openVideoDialog(String path, JSONObject options) {
//        // Let's create the main dialog
////        dialog = new Dialog(cordova.getActivity(), android.R.style.Theme_NoTitleBar);
////        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
////        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////        dialog.setCancelable(true);
////        dialog.setOnDismissListener(this);
////        dialog.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
//
//        // Main container layout
////        main.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
////        main.setOrientation(LinearLayout.VERTICAL);
////        main.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
////        main.setVerticalGravity(Gravity.CENTER_VERTICAL);
//        WindowManager mW = (WindowManager)cordova.getActivity().getSystemService(Context.WINDOW_SERVICE);
//        int screenWidth = mW.getDefaultDisplay().getWidth();
//        int screenHeight = mW.getDefaultDisplay().getHeight();
//
//        videoView = new VideoView(cordova.getActivity());
//
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        WindowManager windowmanager = (WindowManager) cordova.getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
//
//        int playerWidth = displayMetrics.widthPixels;
//        int playerHeight;
//        double marginTop;
//        double aspectRatio;
//        try {
//            aspectRatio = options.getDouble("aspectRatio");
//            marginTop = options.getDouble("marginTop");
//            playerHeight = (int) (playerWidth * aspectRatio);
//        } catch (Exception e) {
//            playerHeight = playerWidth;
//            marginTop = 24;
//        }
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(playerWidth, playerHeight);
//        int statusBarHeight = getStatusBarHeight();
//        int totalTopMargin = (int) (marginTop + statusBarHeight);
////        double pixelsToDp = convertPixelsToDp((float)50, cordova.getContext());
//        params.topMargin = totalTopMargin;
//        videoView.setLayoutParams(params);
////        cordova.getActivity().addContentView(videoView, params);
////        webView.getView().bringToFront();
//
//        View localWebViewRef = webView.getView();
//        originalWebViewParams = localWebViewRef.getLayoutParams();
//        ViewGroup localViewGroup = (ViewGroup) localWebViewRef.getParent();
//        localViewGroup.removeAllViews();
//
//        LinearLayout.LayoutParams webViewParams = new LinearLayout.LayoutParams(playerWidth, screenHeight);
//
//        localWebViewRef.setBackgroundColor(Color.TRANSPARENT);
//        cordova.getActivity().addContentView(videoView, params);
//        cordova.getActivity().addContentView(localWebViewRef, webViewParams);
//
//
//        player = new MediaPlayer();
//        player.setLooping(true);
//        player.setOnPreparedListener(this);
//        player.setOnCompletionListener(this);
//        player.setOnErrorListener(this);
//
//        if (path.startsWith(ASSETS)) {
//            String f = path.substring(15);
//            AssetFileDescriptor fd = null;
//            try {
//                fd = cordova.getActivity().getAssets().openFd(f);
//                player.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
//            } catch (Exception e) {
//                PluginResult result = new PluginResult(PluginResult.Status.ERROR, e.getLocalizedMessage());
//                result.setKeepCallback(false); // release status callback in JS side
//                callbackContext.sendPluginResult(result);
//                callbackContext = null;
//                return;
//            }
//        }
//        else {
//            try {
//                player.setDataSource(path);
//            } catch (Exception e) {
//                PluginResult result = new PluginResult(PluginResult.Status.ERROR, e.getLocalizedMessage());
//                result.setKeepCallback(false); // release status callback in JS side
//                callbackContext.sendPluginResult(result);
//                callbackContext = null;
//                return;
//            }
//        }
//
//        try {
//            float volume = Float.valueOf(options.getString("volume"));
//            Log.d(LOG_TAG, "setVolume: " + volume);
//            player.setVolume(volume, volume);
//        } catch (Exception e) {
//            PluginResult result = new PluginResult(PluginResult.Status.ERROR, e.getLocalizedMessage());
//            result.setKeepCallback(false); // release status callback in JS side
//            callbackContext.sendPluginResult(result);
//            callbackContext = null;
//            return;
//        }
//
//        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            try {
//                int scalingMode = options.getInt("scalingMode");
//                switch (scalingMode) {
//                    case MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING:
//                        Log.d(LOG_TAG, "setVideoScalingMode VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING");
//                        player.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
//                        break;
//                    default:
//                        Log.d(LOG_TAG, "setVideoScalingMode VIDEO_SCALING_MODE_SCALE_TO_FIT");
//                        player.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
//                }
//            } catch (Exception e) {
//                PluginResult result = new PluginResult(PluginResult.Status.ERROR, e.getLocalizedMessage());
//                result.setKeepCallback(false); // release status callback in JS side
//                callbackContext.sendPluginResult(result);
//                callbackContext = null;
//                return;
//            }
//        }
//
//        final SurfaceHolder mHolder = videoView.getHolder();
//        mHolder.setKeepScreenOn(true);
//        mHolder.addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                player.setDisplay(holder);
//                try {
//                    player.prepare();
//                } catch (Exception e) {
//                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, e.getLocalizedMessage());
//                    result.setKeepCallback(false); // release status callback in JS side
//                    callbackContext.sendPluginResult(result);
//                    callbackContext = null;
//                }
//            }
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//                player.release();
//            }
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
//        });
//
////        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
////        lp.copyFrom(dialog.getWindow().getAttributes());
////        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
////        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
////
////        dialog.setContentView(main);
////        dialog.show();
////        dialog.getWindow().setAttributes(lp);
//    }
//
//    @Override
//    public boolean onError(MediaPlayer mp, int what, int extra) {
//        Log.e(LOG_TAG, "MediaPlayer.onError(" + what + ", " + extra + ")");
//        if(mp.isPlaying()) {
//            mp.stop();
//        }
//        mp.release();
////        dialog.dismiss();
//        return false;
//    }
//
//    @Override
//    public void onPrepared(MediaPlayer mp) {
//        mp.start();
//    }
//
//    @Override
//    public void onCompletion(MediaPlayer mp) {
//        Log.d(LOG_TAG, "MediaPlayer completed");
//        if(mp.isPlaying()) {
//            mp.stop();
//        }
////        View localWebViewRef = webView.getView();
////        ViewGroup localViewGroup = (ViewGroup) localWebViewRef.getParent();
//
//        mp.release();
//        cordova.getActivity().runOnUiThread(new Runnable() {
//            public void run() {
//                ((ViewGroup) videoView.getParent()).removeView(videoView);
//            }
//        });
////        dialog.dismiss();
//    }
//
//    @Override
//    public void onDismiss(DialogInterface dialog) {
//        Log.d(LOG_TAG, "Dialog dismissed");
//        if (callbackContext != null) {
//            PluginResult result = new PluginResult(PluginResult.Status.OK, "navigateBack");
//            result.setKeepCallback(false); // release status callback in JS side
//            callbackContext.sendPluginResult(result);
//            callbackContext = null;
//        }
//    }
//
////    private RelativeLayout createToolBar() {
////        toolbar = new RelativeLayout(cordova.getActivity());
////        toolbar.setBackgroundColor(Color.BLACK);
////        // Close/Done button
////        Resources activityRes = cordova.getActivity().getResources();
////        ImageButton close = new ImageButton(cordova.getActivity());
////        RelativeLayout.LayoutParams closeLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
////        closeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
////        close.setLayoutParams(closeLayoutParams);
////        close.setId(Integer.valueOf(5));
////        int closeResId = activityRes.getIdentifier("ic_action_previous_item", "drawable", cordova.getActivity().getPackageName());
////        Drawable closeIcon = activityRes.getDrawable(closeResId);
////        if (Build.VERSION.SDK_INT >= 16)
////            close.setBackground(null);
////        else
////            close.setBackgroundDrawable(null);
////        close.setImageDrawable(closeIcon);
////        close.setColorFilter(Color.WHITE);
////        close.setScaleType(ImageView.ScaleType.FIT_CENTER);
////        if (Build.VERSION.SDK_INT >= 16)
////            close.getAdjustViewBounds();
////
////        close.setOnClickListener(new View.OnClickListener() {
////            public void onClick(View v) {
////                backButtonSelected();
////            }
////        });
////        toolbar.addView(close);
////        return toolbar;
////    }
////
////    private void backButtonSelected() {
////        PluginResult result = new PluginResult(PluginResult.Status.OK);
////        result.setKeepCallback(false); // release status callback in JS side
////        callbackContext.sendPluginResult(result);
////    }
//}


package com.moust.cordova.videoplayer;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;
import android.widget.VideoView;

import com.streetkingpins.streetkingpins.R;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaResourceApi;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class VideoPlayer extends CordovaPlugin implements OnCompletionListener, OnPreparedListener, OnErrorListener, OnDismissListener {

    protected static final String LOG_TAG = "VideoPlayer";

    protected static final String ASSETS = "/android_asset/";

    private CallbackContext callbackContext = null;

    private Dialog dialog;

    private VideoView videoView;
    private Toolbar toolbar;
    private RecyclerView commentList;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager viewManager;
    private Video videoObject;
    private MediaPlayer player;

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action        The action to execute.
     * @param args          JSONArray of arguments for the plugin.
     * @param callbackId    The callback id used when calling back into JavaScript.
     * @return              A PluginResult object with a status and message.
     */
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("play")) {
            this.callbackContext = callbackContext;

            CordovaResourceApi resourceApi = webView.getResourceApi();
            String target = args.getString(0);
            final JSONObject options = args.getJSONObject(1);

            String fileUriStr;
            try {
                Uri targetUri = resourceApi.remapUri(Uri.parse(target));
                fileUriStr = targetUri.toString();
            } catch (IllegalArgumentException e) {
                fileUriStr = target;
            }

            Log.v(LOG_TAG, fileUriStr);

            final String path = stripFileProtocol(fileUriStr);

            // Create dialog in new thread
            cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    openVideoDialog(path, options);
                }
            });

            // Don't return any result now
            PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
            pluginResult.setKeepCallback(true);
            callbackContext.sendPluginResult(pluginResult);
            callbackContext = null;

            return true;
        } else if (action.equals("didFetchComments")) {
            // clear old list
            final JSONArray comments = args.getJSONArray(0);
            List<Comment> list = new ArrayList<Comment>();
            for(int i = 0; i < comments.length(); i++){
                Comment newComment = new Comment().constructor((JSONObject)comments.get(i));
                list.add(newComment);
            }

            cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    adapter.commentList.clear();
                    adapter.commentList = list;
                    adapter.notifyDataSetChanged();
                }
            });
        } else if (action.equals("close")) {
            if (dialog != null) {
                if(player.isPlaying()) {
                    player.stop();
                }
                player.release();
                dialog.dismiss();
            }

            if (callbackContext != null) {
                PluginResult result = new PluginResult(PluginResult.Status.OK);
                result.setKeepCallback(false); // release status callback in JS side
                callbackContext.sendPluginResult(result);
                callbackContext = null;
            }

            return true;
        }
        return false;
    }

    /**
     * Removes the "file://" prefix from the given URI string, if applicable.
     * If the given URI string doesn't have a "file://" prefix, it is returned unchanged.
     *
     * @param uriString the URI string to operate on
     * @return a path without the "file://" prefix
     */
    public static String stripFileProtocol(String uriString) {
        if (uriString.startsWith("file://")) {
            return Uri.parse(uriString).getPath();
        }
        return uriString;
    }

    protected int getStatusBarHeight() {
        float statusBarHeight = 24;
        return (int) convertPixelsToDp(statusBarHeight, cordova.getContext());
    }

    public static double convertPixelsToDp(float px, Context context){
        float density = context.getResources().getDisplayMetrics().density + 0.5f;
        return (double) (px / density);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void openVideoDialog(String path, JSONObject options) {
        // Let's create the main dialog
        dialog = new Dialog(cordova.getActivity(), android.R.style.Theme_NoTitleBar);
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setOnDismissListener(this);
        dialog.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        // Main container layout
        LinearLayout main = new LinearLayout(cordova.getActivity());
        main.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        main.setOrientation(LinearLayout.VERTICAL);
        main.setHorizontalGravity(Gravity.FILL_HORIZONTAL);
        main.setVerticalGravity(Gravity.TOP);

//        WindowManager mW = (WindowManager)cordova.getActivity().getSystemService(Context.WINDOW_SERVICE);
//        int screenWidth = mW.getDefaultDisplay().getWidth();
//        int screenHeight = mW.getDefaultDisplay().getHeight();
//
//        videoView = new VideoView(cordova.getActivity());
//
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) cordova.getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);

        int playerWidth = displayMetrics.widthPixels;
        double playerHeight;
        double marginTop;
        double aspectRatio;
        JSONObject tempVideoObject;
        try {
            aspectRatio = options.getDouble("aspectRatio");
            marginTop = options.getDouble("marginTop");
            playerHeight = playerWidth * aspectRatio;
            tempVideoObject = options.getJSONObject("videoObject");
            videoObject = new Video().constructor(path, playerHeight, tempVideoObject);
        } catch (Exception e) {
            playerHeight = (double) playerWidth;
            marginTop = 24;
        }

//        videoView = new VideoView(cordova.getActivity());
//        videoView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(playerWidth, playerHeight);
//        videoView.setLayoutParams(params);

        // videoView.setVideoURI(uri);
        // videoView.setVideoPath(path);
        toolbar = createToolBar();
        commentList = createRecyclerView(videoObject);

        main.addView(toolbar);
//        main.addView(videoView);
        main.addView(commentList);

        player = new MediaPlayer();
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);

        if (path.startsWith(ASSETS)) {
            String f = path.substring(15);
            AssetFileDescriptor fd = null;
            try {
                fd = cordova.getActivity().getAssets().openFd(f);
                player.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            } catch (Exception e) {
                PluginResult result = new PluginResult(PluginResult.Status.ERROR, e.getLocalizedMessage());
                result.setKeepCallback(false); // release status callback in JS side
                callbackContext.sendPluginResult(result);
                callbackContext = null;
                return;
            }
        }
        else {
            try {
                player.setDataSource(path);
            } catch (Exception e) {
                PluginResult result = new PluginResult(PluginResult.Status.ERROR, e.getLocalizedMessage());
                result.setKeepCallback(false); // release status callback in JS side
                callbackContext.sendPluginResult(result);
                callbackContext = null;
                return;
            }
        }

        try {
            float volume = Float.valueOf(options.getString("volume"));
            Log.d(LOG_TAG, "setVolume: " + volume);
            player.setVolume(volume, volume);
        } catch (Exception e) {
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, e.getLocalizedMessage());
            result.setKeepCallback(false); // release status callback in JS side
            callbackContext.sendPluginResult(result);
            callbackContext = null;
            return;
        }

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            try {
                int scalingMode = options.getInt("scalingMode");
                switch (scalingMode) {
                    case MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING:
                        Log.d(LOG_TAG, "setVideoScalingMode VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING");
                        player.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                        break;
                    default:
                        Log.d(LOG_TAG, "setVideoScalingMode VIDEO_SCALING_MODE_SCALE_TO_FIT");
                        player.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                }
            } catch (Exception e) {
                PluginResult result = new PluginResult(PluginResult.Status.ERROR, e.getLocalizedMessage());
                result.setKeepCallback(false); // release status callback in JS side
                callbackContext.sendPluginResult(result);
                callbackContext = null;
                return;
            }
        }

//        final SurfaceHolder mHolder = videoView.getHolder();
//        mHolder.setKeepScreenOn(true);
//        mHolder.addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                player.setDisplay(holder);
//                try {
//                    player.prepare();
//                } catch (Exception e) {
//                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, e.getLocalizedMessage());
//                    result.setKeepCallback(false); // release status callback in JS side
//                    callbackContext.sendPluginResult(result);
//                    callbackContext = null;
//                }
//            }
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//                player.release();
//            }
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
//        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.setContentView(main);
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e(LOG_TAG, "MediaPlayer.onError(" + what + ", " + extra + ")");
        if(mp.isPlaying()) {
            mp.stop();
        }
        mp.release();
        dialog.dismiss();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(LOG_TAG, "MediaPlayer completed");
        mp.release();
        dialog.dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.d(LOG_TAG, "Dialog dismissed");
        if (callbackContext != null) {
            PluginResult result = new PluginResult(PluginResult.Status.OK);
            result.setKeepCallback(false); // release status callback in JS side
            callbackContext.sendPluginResult(result);
            callbackContext = null;
        }
    }

    private Toolbar createToolBar() {
//        LinearLayout toolbar = new LinearLayout(cordova.getActivity());
//        toolbar.setOrientation(LinearLayout.HORIZONTAL);
//        toolbar.setBackgroundColor(Color.BLACK);
//        // Close/Done button
//
        Resources activityRes = cordova.getActivity().getResources();
        ImageButton close = new ImageButton(cordova.getActivity());
        close.setId(Integer.valueOf(5));
        int closeResId = activityRes.getIdentifier("ic_action_remove", "drawable", cordova.getActivity().getPackageName());
        Drawable closeIcon = activityRes.getDrawable(closeResId);

        if (Build.VERSION.SDK_INT >= 16)
            close.setBackground(null);
        else
            close.setBackgroundDrawable(null);
        close.setImageDrawable(closeIcon);
        close.setColorFilter(Color.WHITE);
        close.setScaleType(ImageView.ScaleType.FIT_CENTER);
        if (Build.VERSION.SDK_INT >= 16)
            close.getAdjustViewBounds();

        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backButtonSelected();
            }
        });
//        toolbar.addView(close);
//
//        ImageView userAvatarImage =  new ImageView(cordova.getActivity());
//        LinearLayout.LayoutParams userAvatarViewLayoutParams = new LinearLayout.LayoutParams(60, 60);
//        userAvatarViewLayoutParams.topMargin = 5;
//        userAvatarImage.setLayoutParams(userAvatarViewLayoutParams);
//        userAvatarImage.setBackgroundColor(Color.WHITE);
//        toolbar.addView(userAvatarImage);
//
//        return toolbar;

        Toolbar toolbar = new Toolbar(cordova.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 168);
        toolbar.setLayoutParams(layoutParams);
        toolbar.setBackgroundColor(Color.BLACK);
        toolbar.setTitle("Skater Username");
        toolbar.setSubtitle("City, State");
        toolbar.setVisibility(View.VISIBLE);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.END;
        params.rightMargin = 0;
        close.setLayoutParams(params);
        toolbar.addView(close);

        return toolbar;
    }

    private RecyclerView createRecyclerView(Video videoObject) {
        ArrayList<Comment> newCells = new ArrayList<Comment>();

        this.viewManager = new LinearLayoutManager(cordova.getContext());
        RecyclerView commentList = new RecyclerView(cordova.getActivity());
        commentList.setBackgroundColor(Color.parseColor("#ffffff"));
        commentList.setLayoutManager(this.viewManager);
        adapter = new MyRecyclerViewAdapter(cordova.getContext(), newCells, videoObject);
        commentList.setAdapter(adapter);
        LinearLayout.LayoutParams recyclerLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        commentList.setLayoutParams(recyclerLayoutParams);

        return commentList;
    }

    private void backButtonSelected() {
        PluginResult result = new PluginResult(PluginResult.Status.OK);
        result.setKeepCallback(false); // release status callback in JS side
        callbackContext.sendPluginResult(result);
    }
}

class MyRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public List<Comment> commentList;
    private Context mContext;
    private Video videoObject;

    private static final int TYPE_VIDEO = 0;
    private static final int TYPE_ACTION = 1;
    private static final int TYPE_TEXT = 2;
    private static final int TYPE_ICON_TEXT = 3;
    private static final int TYPE_COMMENT = 4;

    public MyRecyclerViewAdapter(Context context, List<Comment> commentList, Video videoObject) {
        this.commentList = commentList;
        this.mContext = context;
        this.videoObject = videoObject;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        switch (viewType) {
            case TYPE_VIDEO: {
                View view = LayoutInflater.from(context).inflate(R.layout.media_video_row, parent, false);
                view.getLayoutParams().height = (int)videoObject.getVideoHeight();
                return new VideoRowViewHolder(view);
            }
            case TYPE_COMMENT: {
                View view = LayoutInflater.from(context).inflate(R.layout.media_comment_row, parent, false);
                return new CommentViewHolder(view);
            }
            case TYPE_ACTION: {
                View view = LayoutInflater.from(context).inflate(R.layout.media_action_row, parent, false);
                return new ActionRowViewHolder(view);
            }
            case TYPE_ICON_TEXT: {
                View view = LayoutInflater.from(context).inflate(R.layout.media_icon_row, parent, false);
                return new IconRowViewHolder(view);
            }
            case TYPE_TEXT: {
                View view = LayoutInflater.from(context).inflate(R.layout.media_caption_row, parent, false);
                return new CaptionRowViewHolder(view);
            }
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder customViewHolder, int i) {
        if (i == 0) {
            customViewHolder.bind(this.videoObject.getVideoURL(), i);
        } else if (i == 1) {
            customViewHolder.bind(this.videoObject, i);
        } else if (i == 2) {
            customViewHolder.bind(this.videoObject, i);
        } else if (i == 3) {
            customViewHolder.bind(this.videoObject, i);
        } else if (i == 4) {
            customViewHolder.bind(this.videoObject, i);
        } else {
            Comment comment = commentList.get(i - 5);
            customViewHolder.bind(comment, i);
        }
    }

    @Override
    public int getItemCount() {
        return (null != commentList ? (5 + commentList.size()) : 5);
    }

//    class VideoRowViewHolder extends RecyclerView.ViewHolder {
//        protected VideoView videoView;
//
//        public VideoRowViewHolder(View view) {
//            super(view);
//            this.videoView = view.findViewById(R.id.videoView);
//        }
//    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_VIDEO;
        } else if (position == 1) {
            return TYPE_ACTION;
        } else if (position == 2) {
            return TYPE_TEXT;
        } else if (position == 3) {
            return TYPE_ICON_TEXT;
        } else if (position == 4) {
            return TYPE_ICON_TEXT;
        } else {
            return TYPE_COMMENT;
        }
    }
}

abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    BaseViewHolder(View itemView) {
        super(itemView);
        itemView.setBackgroundColor(Color.parseColor("#ffffff"));
//        ButterKnife.bind(this, itemView);
    }
    public abstract void bind(T type, int row);
}

class VideoRowViewHolder extends BaseViewHolder<String> {
//    @BindView(R.id.videoView)
    VideoView videoView;

    VideoRowViewHolder(View itemView) {
        super(itemView);
        this.videoView = itemView.findViewById(R.id.videoView);
    }

    @Override
    public void bind(String videoURL, int row) {
        Uri videoURI = Uri.parse(videoURL);
        videoView.setVideoURI(videoURI);
        this.videoView.start();
    }
}

class ActionRowViewHolder extends BaseViewHolder<Video> {
    ImageButton likeButtonIcon;
    ImageButton commentButtonIcon;
    TextView likeButtonText;
    TextView commentButtonText;
    ImageButton followButton;
    ImageButton moreActionButton;

    ActionRowViewHolder(View view) {
        super(view);
        this.likeButtonText = view.findViewById(R.id.like_count_text);
        this.commentButtonText = view.findViewById(R.id.comment_count_text);
        this.likeButtonIcon = view.findViewById(R.id.upvote_button_icon);
        this.commentButtonIcon = view.findViewById(R.id.comment_button_icon);
        this.followButton = view.findViewById(R.id.follow_button);
        this.moreActionButton = view.findViewById(R.id.more_button);
    }

    @Override
    public void bind(Video videoObject, int row) {
        String likeCount = Integer.toString(videoObject.getLikeCount());
        String commentCount = Integer.toString(videoObject.getCommentCount());
        this.likeButtonText.setTextColor(Color.parseColor("#000000"));
        this.commentButtonText.setTextColor(Color.parseColor("#000000"));
        this.likeButtonText.setText(likeCount);
        this.commentButtonText.setText(commentCount);
    }
}

class IconRowViewHolder extends BaseViewHolder<Video> {

    //    @BindView(R.id.comment_text)
    TextView textView;
    ImageView iconImageView;

    IconRowViewHolder(View itemView) {
        super(itemView);
        this.textView = itemView.findViewById(R.id.icon_text_view);
        this.iconImageView = itemView.findViewById(R.id.icon_image_view);
    }

    @Override
    public void bind(Video videoObject, int row) {
        if (row == 3) {
            iconImageView.setBackgroundResource(R.drawable.map_marker_filled);
            textView.setText(videoObject.getAddress());
        } else if (row == 4) {
            iconImageView.setBackgroundResource(R.drawable.skateboarder_filled);
            textView.setText(videoObject.getTrickString());
        }
    }
}

class CaptionRowViewHolder extends BaseViewHolder<Video> {
    TextView textView;

    CaptionRowViewHolder(View itemView) {
        super(itemView);
        this.textView = itemView.findViewById(R.id.caption_text_view);
    }

    @Override
    public void bind(Video videoObject, int row) {
        textView.setText(videoObject.getVideoCaption());
    }
}


class CommentViewHolder extends BaseViewHolder<Comment> {

//    @BindView(R.id.comment_text)
    TextView commentTextView;
    TextView commenterUsernameText;
    ImageView userProfileView;

    CommentViewHolder(View itemView) {
        super(itemView);
        this.commentTextView = itemView.findViewById(R.id.comment_text);
        this.commenterUsernameText = itemView.findViewById(R.id.commenter_username_text);
        this.userProfileView = itemView.findViewById(R.id.user_avatar_view);
    }

    @Override
    public void bind(Comment commentObject, int row) {
        commentTextView.setText(commentObject.getCommentText());
        commenterUsernameText.setText(commentObject.getKingPinsUserName());
        new DownloadImageTask(userProfileView)
                .execute(commentObject.getKingPinsUserProfileImageUrl());
    }
}

class Comment {
    private String kingPinsUserGuid;
    private String kingPinsUserName;
    private String kingPinsUserProfileImageUrl;
    private String guid;
    private String commentText;
    private String createdAt;

    public Comment constructor(JSONObject commentObject) {
        try {
            this.commentText = commentObject.getString("commentText");
            this.kingPinsUserGuid = commentObject.getString("kingPinsUserGuid");
            this.kingPinsUserName = commentObject.getString("kingPinsUserName");
            this.kingPinsUserProfileImageUrl = commentObject.getString("kingPinsUserProfileImageUrl");
            this.guid = commentObject.getString("guid");
            this.createdAt = commentObject.getString("createdAt");
        } catch (Exception e) {

        }

        return this;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getKingPinsUserProfileImageUrl() {
        return kingPinsUserProfileImageUrl;
    }

    public String getKingPinsUserName() {
        return kingPinsUserName;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}

class Video {
    private String videoURL;
    private String videoGuid;
    private String caption;
    private double videoHeight;
    private String address;
    private String ownerGuid;
    private String ownerProfileImageUrl;

    private List<String> trickTypes;
    private String city;
    private String state;
    private String country;

    private int likeCount = 0;
    private int commentCount = 0;

    public Video constructor(String videoURL, double videoHeight, JSONObject videoObject) {
        this.videoURL = videoURL;
//        this.videoGuid = videoGuid;
//        this.videoCaption = videoCaption;
        this.videoHeight = videoHeight;

        try {
            this.videoGuid = videoObject.getString("guid");
            this.ownerGuid = videoObject.getString("ownerGuid");
            this.address = videoObject.getString("address");
            this.city = videoObject.getString("city");
            this.caption = videoObject.getString("caption");
            this.state = videoObject.getString("state");
            this.commentCount = videoObject.getInt("commentCount");
            this.likeCount = videoObject.getInt("likeCount");
            JSONArray arr = videoObject.getJSONArray("trickTypes");
            List<String> list = new ArrayList<String>();
            for(int i = 0; i < arr.length(); i++){
                list.add(arr.getJSONObject(i).getString("name"));
            }
            this.trickTypes = list;
        } catch (Exception e) {

        }


        return this;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public double getVideoHeight() {
        return videoHeight;
    }

    public String getVideoCaption() {
        return caption;
    }

    public String getAddress() {
        return address;
    }

    public String getTrickString() {
        if (this.trickTypes != null && this.trickTypes.size() > 0) {
            return this.trickTypes.get(0);
        } else {
            return "No Trick";
        }
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }
}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
