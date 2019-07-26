package com.highq.nativeautomationlibrary.utils.commonutility;

import androidx.test.runner.screenshot.BasicScreenCaptureProcessor;

import java.io.File;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class CustomScreenCaptureProcessor extends BasicScreenCaptureProcessor {
    public CustomScreenCaptureProcessor() {
        this.mDefaultScreenshotPath = new File(new File(getExternalStoragePublicDirectory(DIRECTORY_PICTURES), "drive_screenshots").getAbsolutePath(), "screenshots");


    }
}