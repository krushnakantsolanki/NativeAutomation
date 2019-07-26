/*
 * ******************************************************************************
 * Copyright (c) 2019 or the year of first publication, if earlier, HighQ Solutions Limited or its licensors
 * ******************************************************************************
 */

package com.highq.nativeautomationlibrary.utils;

import timber.log.Timber;


/**
 * Contains a static reference IdlingResource, and should be available only in a mock build type.
 */
public class EspressoIdlingResource {

    private static final String RESOURCE = "GLOBAL";

    private static SimpleCountingIdlingResource mCountingIdlingResource =
            new SimpleCountingIdlingResource(RESOURCE);

    private EspressoIdlingResource() {
        //not used
    }

    public static void increment() {
        mCountingIdlingResource.increment();
        Timber.e(">>>>>>>>>>>>>>>>>>>>>>>>>> Incremented ");
    }

    public static void decrement() {

        mCountingIdlingResource.decrement();
        Timber.e(">>>>>>>>>>>>>>>>>>>>>>>>>> Decremented");
    }


    /**
     * To get object of {@link SimpleCountingIdlingResource}
     *
     * @return
     */
    public static SimpleCountingIdlingResource getCounter() {
        return mCountingIdlingResource;
    }

    /**
     * To get object of {@link SimpleCountingIdlingResource}
     *
     * @return
     */
    public static void resetCounter() {
        mCountingIdlingResource.resetCounter();
    }

    /**
     * Used for Unit testing
     *
     * @return
     */
    public static EspressoIdlingResource getInstance() {
        return new EspressoIdlingResource();
    }
}
