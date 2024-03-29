package com.highq.nativeautomationlibrary.utils.commonutility;

import android.graphics.Bitmap;

import androidx.test.runner.screenshot.ScreenCapture;
import androidx.test.runner.screenshot.ScreenCaptureProcessor;
import androidx.test.runner.screenshot.Screenshot;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.IOException;
import java.util.HashSet;

import timber.log.Timber;

public class ScreenshotTestRule extends TestWatcher {

    @Override
    protected void failed(Throwable e, Description description) {
        super.failed(e, description);
        Timber.e("on test case failed");
        takeScreenshot(description);
    }

    private void takeScreenshot(Description description) {
        String filename = description.getTestClass().getSimpleName() + "-" + description.getMethodName();

        ScreenCapture capture = Screenshot.capture();
        capture.setName(filename);
        capture.setFormat(Bitmap.CompressFormat.PNG);

        HashSet<ScreenCaptureProcessor> processors = new HashSet<>();
        processors.add(new CustomScreenCaptureProcessor());

        try {
            capture.process(processors);
        } catch (IOException e) {
            e.printStackTrace();
            Timber.e("take screenshot failed");
        }
    }
}