/*
 * Copyright (c) 2019 or the year of first publication, if earlier, HighQ Solutions Limited or its licensors
 */

package com.highq.nativeautomationlibrary.utils.commonutility;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class RetryListener {
    /**
     * The Constant success.
     */
    public static final String success = "Build Success";
    public static final String failed = "Build Fail";
    /**
     * The list of time of every test.
     */
    public static List<String> timeList = new ArrayList<>();
    /**
     * The total scenario.
     */
    public static List<String> tetCaseStatus = new ArrayList<>();
    /**
     * The total scenario.
     */
    public static List<String> totalScenario = new ArrayList<>();
    /**
     * The pass.
     */
    public static List<String> pass = new ArrayList<>();
    /**
     * The skip.
     */
    public static List<Integer> skip = new ArrayList<>();
    /**
     * The fail.
     */
    public static List<String> fail = new ArrayList<>();
    /**
     * The pass rate.
     */
    public static List<String> passRate = new ArrayList<>();
    /**
     * The sum of all scenario count.
     */
    public static int sumOfAllScenarioCount = 0;
    /**
     * The sum of all pass scenario count.
     */
    public static int sumOfAllPassScenarioCount = 0;
    /**
     * The sum of all skip scenario count.
     */
    public static int sumOfAllSkipScenarioCount = 0;
    /**
     * The sum of all fail scenario count.
     */
    public static int sumOfAllFailScenarioCount = 0;
    /**
     * The pass rate final ratio.
     */
    public static String passRateFinalRatio;
    /**
     * The count of total fail testcase.
     */
    static int totalFail = 0;
    /**
     * The total time of execution.
     */
    private static String finalTime;
    /**
     * The list of final status(PASS/FAIL) of every test.
     */
    private static Map<String, String> testcaseFinalStatus = new LinkedHashMap<>();
    /**
     * The name of testcase.
     */
    private static String testcaseName;
    /**
     * The status of the build.
     */
    private static String buildStatus;
    /**
     * The count of total test cases.
     */
    private static int totalTestCases = 0;
    /**
     * The count of total pass testcase.
     */
    private static int totalPass = 0;
    /**
     * The testcase final fail scenario status.
     */
    private static Map<String, String> testcaseFinalFailScenarioStatus = new LinkedHashMap<>();
    /**
     * The total test scenario.
     */
    private static int totalTestScenario = 0;

    /**
     * Date and system time.
     *
     * @param dateAndTimeFormat the date and time format
     * @return the string
     * @author Deepak.Rathod
     */
    public static String dateAndSystemTime(String dateAndTimeFormat) {
        DateFormat dateFormat = new SimpleDateFormat(dateAndTimeFormat);
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Gets the count of total fail testcase.
     * <p>
     * \   * @return the count of total fail testcase
     */
    public static int getTotalFail() {
        return totalFail;
    }

    /**
     * Sets the count of total fail testcase.
     *
     * @param totalFail the count of new total fail testcase
     */
    public static void setTotalFail(int totalFail) {
        RetryListener.totalFail = totalFail;
    }

    /**
     * Gets the final status(PASS/FAIL) of all testcase.
     * <p>
     * \     * @return the testcase final status
     */
    public static Map<String, String> getTestcaseFinalStatus() {
        return testcaseFinalStatus;
    }

    /**
     * Gets the final status(Build Success/Build Failed) of Build.
     *
     * @return the builds the status
     */
    public static String getBuildStatus() {
        return buildStatus;
    }

    /**
     * Sets the status(Build Success/Build Failed) of Build.
     *
     * @param buildStatus the new status(Build Success/Build Failed) of the Build
     */
    public static void setBuildStatus(String buildStatus) {
        RetryListener.buildStatus = buildStatus;
    }

    /**
     * Gets the count of total test cases.
     *
     * @return the count of total test cases
     */
    public static int getTotalTestCases() {
        return totalTestCases;
    }

    /**
     * Sets the count of total test cases.
     *
     * @param totalTestCases the new count of total test cases
     */
    public static void setTotalTestCases(int totalTestCases) {
        RetryListener.totalTestCases = totalTestCases;
    }

    /**
     * Gets the count of total pass testcases.
     *
     * @return the count of total pass testcases
     */
    public static int getTotalPass() {
        return totalPass;
    }

    /**
     * Sets the count of total pass testcase.
     *
     * @param totalPass the new count of total pass testcase
     */
    public static void setTotalPass(int totalPass) {
        RetryListener.totalPass = totalPass;
    }

    /**
     * Gets the final time of execution.
     *
     * @return the final time of execution
     */
    public static String getFinalTime() {
        return finalTime;
    }

    /**
     * Sets the final time of execution.
     *
     * @param finalTime the new final time of execution
     */
    public static void setFinalTime(String finalTime) {
        RetryListener.finalTime = finalTime;
    }

    /**
     * System time.
     *
     * @return the string
     */
    public static String getSystemTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        df.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));

        Date dateobj = new Date();
        return df.format(dateobj);
    }
}
