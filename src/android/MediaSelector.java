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
import android.app.AlertDialog;
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
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaResourceApi;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class VideoPlayer extends CordovaPlugin implements OnCompletionListener, OnPreparedListener, OnErrorListener, OnDismissListener {

    protected static final String LOG_TAG = "VideoPlayer";

    protected static final String ASSETS = "/android_asset/";

    public CallbackContext callbackContext = null;

    private Dialog dialog;

    private VideoView videoView;
    private View toolbar;
    private View commentInputView;
    private RecyclerView commentList;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager viewManager;
    private Video videoObject;
    private User currentUser;
    public Boolean userHasUpvoted = false;
    public Boolean userHasCommented = false;
    public Boolean userIsFollowing = false;
    private MediaPlayer player;

    private String userDidLikeMediaWithGuid;

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

            return true;
        } else if (action.equals("didFetchComments")) {
            // clear old list
            final JSONArray comments = args.getJSONArray(0);
            userHasCommented = args.getBoolean(1);
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
                dialog.dismiss();
            }

            if (callbackContext != null) {
                PluginResult result;
                if (this.userDidLikeMediaWithGuid != null) {
                    result = new PluginResult(PluginResult.Status.OK, "likedGuid:" + this.userDidLikeMediaWithGuid);
                } else {
                    result = new PluginResult(PluginResult.Status.OK);
                }
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

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) cordova.getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);

        int playerWidth = displayMetrics.widthPixels;
        double playerHeight;
        double aspectRatio;
        JSONObject tempVideoObject;
        try {
            aspectRatio = options.getDouble("aspectRatio");
            playerHeight = playerWidth * aspectRatio;
            tempVideoObject = options.getJSONObject("videoObject");
            videoObject = new Video().constructor(path, playerHeight, tempVideoObject);
            currentUser = new User().constructor(options.getJSONObject("currentUser"));
            userHasUpvoted = options.getBoolean("userHasUpvoted");
            userIsFollowing = options.getBoolean("userIsFollowing");
        } catch (Exception e) {
            playerHeight = (double) playerWidth;
        }

        toolbar = createToolBar();

        commentList = createRecyclerView(videoObject);

        main.addView(toolbar);
//        main.addView(videoView);
        main.addView(commentList);



        commentInputView = LayoutInflater.from(cordova.getContext()).inflate(R.layout.comment_input_view, null);
        LinearLayout.LayoutParams commentBarLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        commentInputView.setLayoutParams(commentBarLayout);
        main.addView(commentInputView);

        Button sendCommentButton = commentInputView.findViewById(R.id.send_comment_button);
        EditText sendCommentTextValue = commentInputView.findViewById(R.id.add_comment_text);
        sendCommentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        addComment(sendCommentTextValue.getText().toString());
                    }
                });
            }
        });
//
//        player = new MediaPlayer();
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

    private View createToolBar() {
        View view = LayoutInflater.from(cordova.getContext()).inflate(R.layout.media_tool_bar, null);
        LinearLayout.LayoutParams toolBarLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(toolBarLayout);
        TextView userNameText = view.findViewById(R.id.tool_bar_user_name_value);
        ImageView userAvatarView = view.findViewById(R.id.tool_bar_user_avatar_view);
        TextView createdAtText = view.findViewById(R.id.tool_bar_created_at_text);
        TextView locationText = view.findViewById(R.id.tool_bar_location_value);
        ImageButton backButton = view.findViewById(R.id.tool_bar_back_button);
        userNameText.setText(videoObject.getOwnerUsername());
        createdAtText.setText(videoObject.getCreatedAt());
        locationText.setText(videoObject.getAddress());
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backButtonSelected();
            }
        });

        new DownloadImageTask(userAvatarView)
                .execute(videoObject.getOwnerProfileImageUrl());

        return view;
    }

    private RecyclerView createRecyclerView(Video videoObject) {
        ArrayList<Comment> newCells = new ArrayList<Comment>();

        this.viewManager = new LinearLayoutManager(cordova.getContext());
        RecyclerView commentList = new RecyclerView(cordova.getActivity());
        commentList.setBackgroundColor(Color.parseColor("#ffffff"));
        commentList.setLayoutManager(this.viewManager);
        adapter = new MyRecyclerViewAdapter(cordova.getContext(), this, newCells, videoObject);
        commentList.setAdapter(adapter);
        LinearLayout.LayoutParams recyclerLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        recyclerLayoutParams.weight = 1;
        commentList.setLayoutParams(recyclerLayoutParams);

        return commentList;
    }

    public void backButtonSelected() {
//        PluginResult result = new PluginResult(PluginResult.Status.OK, "close");
//        result.setKeepCallback(false); // release status callback in JS side
//        callbackContext.sendPluginResult(result);
        if (dialog != null) {
//            if(player.isPlaying()) {
//                player.stop();
//            }
//            player.release();
            dialog.dismiss();
        }
    }

    private void addComment(String withText) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("kingPinsUserGuid", this.currentUser.getGuid());
            jsonObject.put("mediaObjectGuid", this.videoObject.getVideoGuid());
            jsonObject.put("commentText", withText);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url("http://prod-api.street-kingpins.com/api/Comments")
                .addHeader("x-ikey", "sbkpX12!")
                .post(body)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            String resStr = response.body().string();
            if (response.code() == 200) {
                try {
                    JSONObject newCommentJSON = new JSONObject(resStr);
                    this.userHasUpvoted = true;
                    videoObject.setCommentCount(videoObject.getCommentCount() + 1);
                    Comment newComment = new Comment().constructor(newCommentJSON);
                    newComment.setCreatedAt("Just Now");
                    cordova.getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            userHasCommented = true;
                            EditText sendCommentTextValue = commentInputView.findViewById(R.id.add_comment_text);
                            sendCommentTextValue.setText("");
                            videoObject.setCommentCount(videoObject.getCommentCount() + 1);
                            adapter.commentList.add(0, newComment);
                            adapter.notifyDataSetChanged();
                            hideKeyboard();

                        }
                    });
                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + resStr + "\"");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upvoteMedia() {
        if (!this.userHasUpvoted) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("kingPinsUserGuid", this.currentUser.getGuid());
                jsonObject.put("mediaObjectGuid", this.videoObject.getVideoGuid());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            // put your json here
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder()
                    .url("http://prod-api.street-kingpins.com/api/Likes")
                    .addHeader("x-ikey", "sbkpX12!")
                    .post(body)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                String resStr = response.body().string();
                if (response.code() == 200) {
                    this.userHasUpvoted = true;
                    this.userDidLikeMediaWithGuid = this.videoObject.getVideoGuid();
                    videoObject.setLikeCount(videoObject.getLikeCount() + 1);
                    cordova.getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            adapter.notifyItemChanged(1);
                        }
                    });
                    PluginResult result = new PluginResult(PluginResult.Status.OK, "likedGuid:" + this.userDidLikeMediaWithGuid);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void toggleFollowUser() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("kingPinsUserGuid", this.currentUser.getGuid());
            jsonObject.put("mediaObjectGuid", this.videoObject.getVideoGuid());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request;
        Response response = null;

        if (!this.userIsFollowing) {
            request = new Request.Builder()
                    .url("http://prod-api.street-kingpins.com/api/FollowLinks/followEntity")
                    .addHeader("x-ikey", "sbkpX12!")
                    .post(body)
                    .build();
        } else {
            request = new Request.Builder()
                    .url("http://prod-api.street-kingpins.com/api/FollowLinks/unfollowEntity")
                    .addHeader("x-ikey", "sbkpX12!")
                    .post(body)
                    .build();
        }
        try {
            response = client.newCall(request).execute();
            String resStr = response.body().string();
            if (response.code() == 200) {
                this.userIsFollowing = !this.userIsFollowing;
                cordova.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        adapter.notifyItemChanged(1);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reportMedia(String withText) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("reportingUserGuid", this.currentUser.getGuid());
            jsonObject.put("mediaObjectGuid", this.videoObject.getVideoGuid());
            jsonObject.put("reportText", withText);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Response response = null;
        Request request = new Request.Builder()
                .url("http://prod-api.street-kingpins.com/api/MediaReports")
                .addHeader("x-ikey", "sbkpX12!")
                .post(body)
                .build();
        try {
            response = client.newCall(request).execute();
            String resStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteMedia() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("guid", this.videoObject.getVideoGuid());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Response response = null;
        Request request = new Request.Builder()
                .url("http://prod-api.street-kingpins.com/api/UserVideos/deleteUserVideo")
                .addHeader("x-ikey", "sbkpX12!")
                .post(body)
                .build();
        try {
            response = client.newCall(request).execute();
            String resStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void showActionDialog() {

        final CordovaInterface cordova = this.cordova;
        Runnable runnable = new Runnable() {
            public void run() {

                final AlertDialog.Builder builder;
                final AlertDialog actionDialog;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    builder = new AlertDialog.Builder(cordova.getActivity(), 1);
                } else {
                    builder = new AlertDialog.Builder(cordova.getActivity());
                }

                builder
                        .setTitle("More Options")
                        .setCancelable(true);

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                if (videoObject.getOwnerGuid().equals(currentUser.getGuid())) {
                    final String[] buttons = {"Delete Media"};
                    builder.setItems(buttons, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            deleteMedia();
                        }
                    });
                } else {
                    final String[] buttons = {"Report Media"};
                    builder.setItems(buttons, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            showReportMediaAlert();
                        }
                    });

                }

                builder.setOnCancelListener(new AlertDialog.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
                actionDialog = builder.create();
                actionDialog.show();
            }
        };
        this.cordova.getActivity().runOnUiThread(runnable);
    }

    public void showReportMediaAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(cordova.getContext());
        builder.setTitle("Report Media");
        builder.setMessage("Why are you reporting this media?");

// Set up the input
        final EditText input = new EditText(cordova.getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reportMedia(input.getText().toString());
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog reportMediaDialog = builder.create();
        reportMediaDialog.show();
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) cordova.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(commentInputView.getWindowToken(), 0);
    }
}

class MyRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public List<Comment> commentList;
    private Context mContext;
    private VideoPlayer mVideoPlayerRef;
    private Video videoObject;

    private static final int TYPE_VIDEO = 0;
    private static final int TYPE_ACTION = 1;
    private static final int TYPE_TEXT = 2;
    private static final int TYPE_ICON_TEXT = 3;
    private static final int TYPE_COMMENT = 4;

    public Drawable isUserHasCommented() {
        if (mVideoPlayerRef.userHasCommented) {
            return mContext.getResources().getDrawable( R.drawable.comment_filled );
        } else {
            return mContext.getResources().getDrawable( R.drawable.comment_unfilled );
        }
    }

    public Drawable isUserHasUpvoted() {
        if (mVideoPlayerRef.userHasUpvoted) {
            return mContext.getResources().getDrawable( R.drawable.upvote_filled );
        } else {
            return mContext.getResources().getDrawable( R.drawable.upvote_unfilled );
        }
    }

    public Drawable isUserIsFollowing() {
        if (mVideoPlayerRef.userIsFollowing) {
            return mContext.getResources().getDrawable( R.drawable.follow_user_filled );
        } else {
            return mContext.getResources().getDrawable( R.drawable.follow_user_unfilled );
        }
    }

    public MyRecyclerViewAdapter(Context context, VideoPlayer videoPlayerRef, List<Comment> commentList, Video videoObject) {
        this.commentList = commentList;
        this.mContext = context;
        this.mVideoPlayerRef = videoPlayerRef;
        this.videoObject = videoObject;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        switch (viewType) {
            case TYPE_VIDEO: {
                View view = LayoutInflater.from(context).inflate(R.layout.media_video_row, parent, false);
                if (videoObject != null) {
                    view.getLayoutParams().height = (int) videoObject.getVideoHeight();
                }
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
                if (videoObject != null) {
                    if (videoObject.getVideoCaption() == null || videoObject.getVideoCaption().equals("")) {
                        view.getLayoutParams().height = 0;
                    }
                }
                return new CaptionRowViewHolder(view);
            }
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder customViewHolder, int i) {
        if (i == 0) {
            customViewHolder.setIsRecyclable(false);
            customViewHolder.bind(this.videoObject.getVideoURL(), i, this);
        } else if (i == 1) {
            customViewHolder.bind(this.videoObject, i, this);
            customViewHolder.setCompletionHandler(new CompletionHandler() {
                @Override
                public void handle(String reason) {
//                JSONObject resultObject = new JSONObject();
//                PluginResult result = new PluginResult(PluginResult.Status.OK, "comment");
//                callbackContext.sendPluginResult(result);
                    if (reason == "more") {
                        mVideoPlayerRef.showActionDialog();
                    } else if (reason == "upvote") {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                mVideoPlayerRef.upvoteMedia();
                            }
                        });
                    } else if (reason == "comment") {
                        showKeyboard();
                    } else if (reason == "follow") {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                mVideoPlayerRef.toggleFollowUser();
                            }
                        });
                    }
                }
            });
        } else if (i == 2) {
            customViewHolder.bind(this.videoObject, i, this);
        } else if (i == 3) {
            customViewHolder.bind(this.videoObject, i, this);
            customViewHolder.setCompletionHandler(new CompletionHandler() {
                @Override
                public void handle(String reason) {
//                JSONObject resultObject = new JSONObject();
//                PluginResult result = new PluginResult(PluginResult.Status.OK, "comment");
//                callbackContext.sendPluginResult(result);
                    if (reason.equals("pushToLocation")) {
                        mVideoPlayerRef.backButtonSelected();
                        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, reason);
                        pluginResult.setKeepCallback(true);
                        mVideoPlayerRef.callbackContext.sendPluginResult(pluginResult);
                    }
                }
            });
        } else if (i == 4) {
            customViewHolder.bind(this.videoObject, i, this);
        } else {
            Comment comment = commentList.get(i - 5);
            customViewHolder.bind(comment, i, this);
            customViewHolder.setCompletionHandler(new CompletionHandler() {
                @Override
                public void handle(String reason) {
                    mVideoPlayerRef.backButtonSelected();
                    PluginResult result = new PluginResult(PluginResult.Status.OK, "pushToUser:" + reason);
                    result.setKeepCallback(true);
                    mVideoPlayerRef.callbackContext.sendPluginResult(result);
                }
            });
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

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
}

abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    CompletionHandler savedHandler;

    BaseViewHolder(View itemView) {
        super(itemView);
        itemView.setBackgroundColor(Color.parseColor("#ffffff"));
//        ButterKnife.bind(this, itemView);
    }
    public abstract void bind(T type, int row, MyRecyclerViewAdapter adapter);

    void setCompletionHandler(CompletionHandler h) {
        savedHandler = h;
    }
}

interface CompletionHandler {
    public void handle(String reason);
}

class VideoRowViewHolder extends BaseViewHolder<String> {
//    @BindView(R.id.videoView)
    VideoView videoView;

    VideoRowViewHolder(View itemView) {
        super(itemView);
        this.videoView = itemView.findViewById(R.id.videoView);
    }

    @Override
    public void bind(String videoURL, int row, MyRecyclerViewAdapter adapter) {
        if (!this.videoView.isPlaying()) {
            Uri videoURI = Uri.parse(videoURL);
            videoView.setVideoURI(videoURI);
            this.videoView.start();
            videoView.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });

            videoView.setOnErrorListener(new OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {

                    return false;
                }
            });
        }
    }
}

class ActionRowViewHolder extends BaseViewHolder<Video> {

    LinearLayout upvoteButton;
    LinearLayout commentButton;
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
        this.upvoteButton = view.findViewById(R.id.upvote_button);
        this.commentButton = view.findViewById(R.id.comment_button);
    }

    @Override
    public void bind(Video videoObject, int row, MyRecyclerViewAdapter adapter) {
        String likeCount = Integer.toString(videoObject.getLikeCount());
        String commentCount = Integer.toString(videoObject.getCommentCount());
        this.likeButtonText.setTextColor(Color.parseColor("#000000"));
        this.commentButtonText.setTextColor(Color.parseColor("#000000"));
        this.likeButtonText.setText(likeCount);
        this.commentButtonText.setText(commentCount);

        likeButtonIcon.setImageDrawable(adapter.isUserHasUpvoted());

        commentButtonIcon.setImageDrawable(adapter.isUserHasCommented());

        followButton.setImageDrawable(adapter.isUserIsFollowing());

        moreActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                savedHandler.handle("more");
//                PluginResult result = new PluginResult(PluginResult.Status.OK, "more");
//                result.setKeepCallback(true);
//                callbackContext.sendPluginResult(result);
            }
        });

        followButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                savedHandler.handle("follow");
//                PluginResult result = new PluginResult(PluginResult.Status.OK, "follow");
//                callbackContext.sendPluginResult(result);
            }
        });

        upvoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                savedHandler.handle("upvote");
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                savedHandler.handle("comment");
            }
        });

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
    public void bind(Video videoObject, int row, MyRecyclerViewAdapter adapter) {
        if (row == 3) {
            // Location Row
            iconImageView.setBackgroundResource(R.drawable.map_marker_filled);
            textView.setText(videoObject.getAddress());
            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    savedHandler.handle("pushToLocation");
                }
            });
        } else if (row == 4) {
            // Tricks Row
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
    public void bind(Video videoObject, int row, MyRecyclerViewAdapter adapter) {
        if (videoObject.getVideoCaption() != "") {
            textView.setText(videoObject.getVideoCaption());
        } else {
            this.itemView.getLayoutParams().height = 0;
        }
    }
}


class CommentViewHolder extends BaseViewHolder<Comment> {

//    @BindView(R.id.comment_text)
    TextView commentTextView;
    TextView commenterUsernameText;
    TextView commentedOnDate;
    ImageView userProfileView;

    CommentViewHolder(View itemView) {
        super(itemView);
        this.commentTextView = itemView.findViewById(R.id.comment_text);
        this.commenterUsernameText = itemView.findViewById(R.id.commenter_username_text);
        this.commentedOnDate = itemView.findViewById(R.id.commented_on_date);
        this.userProfileView = itemView.findViewById(R.id.user_avatar_view);
    }

    @Override
    public void bind(Comment commentObject, int row, MyRecyclerViewAdapter adapter) {
        commentTextView.setText(commentObject.getCommentText());
        commenterUsernameText.setText(commentObject.getKingPinsUserName());
        commentedOnDate.setText(commentObject.getCreatedAt());

        userProfileView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                savedHandler.handle(commentObject.getKingPinsUserGuid());
            }
        });

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

    public String getKingPinsUserGuid() { return kingPinsUserGuid; }

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

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

class Video {
    private String videoURL;
    private String videoGuid;
    private String caption;
    private JSONObject geoPoint;
    private double videoHeight;
    private String address;
    private String ownerGuid;
    private String ownerProfileImageUrl;
    private String ownerUsername;
    private String createdAt;

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
            this.ownerUsername = videoObject.getString("ownerUsername");
            this.ownerProfileImageUrl = videoObject.getString("ownerProfileImageUrl");
            this.commentCount = videoObject.getInt("commentCount");
            this.likeCount = videoObject.getInt("likeCount");
            this.geoPoint = videoObject.getJSONObject("geoPoint");
            this.createdAt = videoObject.getString("createdAt");
            JSONArray arr = videoObject.getJSONArray("trickTypes");
            List<String> list = new ArrayList<String>();
            for(int i = 0; i < arr.length(); i++){
                list.add(arr.getJSONObject(i).getString("name"));
            }
            this.trickTypes = list;
        } catch (Exception e) {

        }
        try {
            this.caption = videoObject.getString("caption");
        } catch (Exception e) {
            Log.v("Error", "No Caption available");
        }
        try {
            this.address = videoObject.getString("address");
            this.city = videoObject.getString("city");
            this.state = videoObject.getString("state");
        } catch (Exception e) {
            Log.v("Error", "Error getting address, state, or city");
        }


        return this;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getVideoGuid() {
        return videoGuid;
    }

    public double getVideoHeight() {
        return videoHeight;
    }

    public String getVideoCaption() {
        return caption;
    }

    public String getAddress() {
        if (this.address != null) {
            return address;
        } else {
            try {
                return "Lat: " + this.geoPoint.get("lat") + ", Lng: " + this.geoPoint.get("lng");
            } catch (Exception e) {
                return "Unknown Location";
            }
        }
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

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public String getOwnerGuid() {
        return ownerGuid;
    }

    public String getOwnerProfileImageUrl() {
        return ownerProfileImageUrl;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public String getCreatedAt() {
        return createdAt;
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

class User {
    private String guid;
    private String profileImageURL;
    private String userName;

    public User constructor(JSONObject userObject) {

        try {
            this.guid = userObject.getString("guid");
            this.profileImageURL = userObject.getString("profileImageURL");
            this.userName = userObject.getString("userName");
        } catch (Exception e) {

        }


        return this;
    }

    public String getGuid() {
        return guid;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public String getUserName() {
        return userName;
    }
}
