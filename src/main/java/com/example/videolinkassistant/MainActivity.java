package com.example.videolinkassistant;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText videoLinkInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createContentView());
    }

    private View createContentView() {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(Color.rgb(247, 248, 252));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(28), dp(20), dp(24));
        scrollView.addView(root, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView title = new TextView(this);
        title.setText("视频网址助手");
        title.setTextSize(26);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setTextColor(Color.rgb(28, 31, 42));
        root.addView(title, matchWrap());

        TextView subtitle = new TextView(this);
        subtitle.setText("输入合法视频网页地址，并使用手机浏览器打开");
        subtitle.setTextSize(14);
        subtitle.setTextColor(Color.rgb(100, 105, 120));
        LinearLayout.LayoutParams subtitleParams = matchWrap();
        subtitleParams.topMargin = dp(6);
        root.addView(subtitle, subtitleParams);

        TextView inputLabel = new TextView(this);
        inputLabel.setText("视频网址");
        inputLabel.setTextSize(15);
        inputLabel.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        inputLabel.setTextColor(Color.rgb(45, 48, 60));
        LinearLayout.LayoutParams labelParams = matchWrap();
        labelParams.topMargin = dp(28);
        root.addView(inputLabel, labelParams);

        videoLinkInput = new EditText(this);
        videoLinkInput.setHint("https://...");
        videoLinkInput.setSingleLine(true);
        videoLinkInput.setTextSize(16);
        videoLinkInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);
        videoLinkInput.setPadding(dp(14), 0, dp(14), 0);
        videoLinkInput.setBackgroundResource(android.R.drawable.edit_text);
        LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(52));
        inputParams.topMargin = dp(8);
        root.addView(videoLinkInput, inputParams);

        LinearLayout actionRow = new LinearLayout(this);
        actionRow.setOrientation(LinearLayout.HORIZONTAL);
        actionRow.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rowParams.topMargin = dp(12);
        root.addView(actionRow, rowParams);

        Button clearButton = makeButton("清空");
        clearButton.setOnClickListener(v -> videoLinkInput.setText(""));
        actionRow.addView(clearButton, weightedButton());

        Button openButton = makeButton("打开视频网址");
        openButton.setOnClickListener(v -> openTypedUrl());
        LinearLayout.LayoutParams openButtonParams = weightedButton();
        openButtonParams.leftMargin = dp(10);
        actionRow.addView(openButton, openButtonParams);

        TextView quickLabel = new TextView(this);
        quickLabel.setText("常用平台");
        quickLabel.setTextSize(15);
        quickLabel.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        quickLabel.setTextColor(Color.rgb(45, 48, 60));
        LinearLayout.LayoutParams quickLabelParams = matchWrap();
        quickLabelParams.topMargin = dp(30);
        root.addView(quickLabel, quickLabelParams);

        addPlatformButton(root, "打开爱奇艺", "https://www.iqiyi.com");
        addPlatformButton(root, "打开腾讯视频", "https://v.qq.com");
        addPlatformButton(root, "打开优酷视频", "https://www.youku.com/");

        TextView notice = new TextView(this);
        notice.setText("提示：请仅访问你有权观看和使用的内容。本应用不提供会员破解、解析或付费内容绕过功能。");
        notice.setTextSize(13);
        notice.setTextColor(Color.rgb(190, 50, 50));
        notice.setGravity(Gravity.CENTER);
        notice.setPadding(dp(10), dp(14), dp(10), dp(14));
        LinearLayout.LayoutParams noticeParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        noticeParams.topMargin = dp(26);
        root.addView(notice, noticeParams);

        return scrollView;
    }

    private void addPlatformButton(LinearLayout parent, String label, String url) {
        Button button = makeButton(label);
        button.setOnClickListener(v -> openUrl(url));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(52));
        params.topMargin = dp(10);
        parent.addView(button, params);
    }

    private Button makeButton(String text) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(15);
        button.setAllCaps(false);
        return button;
    }

    private void openTypedUrl() {
        String url = videoLinkInput.getText().toString().trim();
        if (url.isEmpty()) {
            Toast.makeText(this, "请先输入视频网址", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }
        openUrl(url);
    }

    private void openUrl(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException | IllegalArgumentException e) {
            Toast.makeText(this, "无法打开该网址", Toast.LENGTH_SHORT).show();
        }
    }

    private LinearLayout.LayoutParams matchWrap() {
        return new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private LinearLayout.LayoutParams weightedButton() {
        return new LinearLayout.LayoutParams(0, dp(52), 1f);
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
