/*
 * ******************************************************************************
 * Copyright (c) 2019 or the year of first publication, if earlier, HighQ Solutions Limited or its licensors
 * ******************************************************************************
 */

package com.highq.nativeautomationlibrary.utils;

import java.util.concurrent.atomic.AtomicInteger;

import androidx.test.espresso.IdlingResource;

import static androidx.core.util.Preconditions.checkNotNull;

/**
 * An simple counter implementation of that determines idleness by
 * maintaining an internal counter. When the counter is 0 - it is considered to be idle, when it is
 * non-zero it is not idle. This is very similar to the way a Semaphore
 * behaves.
 * This class can then be used to wrap up operations that while in progress should block tests from
 * accessing the UI.
 */
public final class SimpleCountingIdlingResource implements IdlingResource {

    private final String mResourceName;

    private final AtomicInteger counter = new AtomicInteger(0);

    // written from main thread, read from any thread.
    private ResourceCallback resourceCallback;

    /**
     * Creates a SimpleCountingIdlingResource
     *
     * @param resourceName the resource name this resource should report to Espresso.
     */
    public SimpleCountingIdlingResource(String resourceName) {
        mResourceName = checkNotNull(resourceName);
    }

    @Override
    public String getName() {
        return mResourceName;
    }

    @Override
    public boolean isIdleNow() {
        return counter.get() == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }

    /**
     * Used for unit testing
     *
     * @return
     */
    public ResourceCallback getResourceCallback() {
        return resourceCallback;
    }

    /**
     * Increments the count of in-flight transactions to the resource being monitored.
     */
    public void increment() {
        counter.getAndIncrement();
    }

    /**
     * Used for unit test
     */
    public int getCounterValue() {
        return counter.get();
    }

    /**
     * Used for unit test
     */
    public void resetCounter() {
        counter.set(0);
    }

    /**
     * Decrements the count of in-flight transactions to the resource being monitored.
     * <p>
     * If this operation results in the counter falling below 0 - an exception is raised.
     *
     * @throws IllegalStateException if the counter is below 0.
     */
    public void decrement() {
        int counterVal = counter.get();
        if (counterVal > 0)
            counterVal = counter.decrementAndGet();
        if (counterVal <= 0 && null != resourceCallback) {
            // we've gone from non-zero to zero. That means we're idle now! Tell espresso.
            resetCounter();
            resourceCallback.onTransitionToIdle();
        }
    }
}

