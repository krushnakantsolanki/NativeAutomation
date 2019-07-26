package com.highq.nativeautomationlibrary.utils.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;


import com.highq.nativeautomationlibrary.R;
import com.highq.nativeautomationlibrary.utils.EspressoIdlingResource;
import com.highq.nativeautomationlibrary.utils.commonutility.EspressoConstants;
import com.highq.nativeautomationlibrary.utils.commonutility.RecyclerViewAction;
import com.highq.nativeautomationlibrary.utils.commonutility.RecyclerViewMatcher;
import com.highq.nativeautomationlibrary.utils.commonutility.ScreenshotTestRule;


import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;
import org.junit.Rule;
import org.junit.rules.RuleChain;

import timber.log.Timber;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.StringStartsWith.startsWith;


public class BaseTest extends BaseCollaborate {


    protected int alertTitleId = R.id.alertTitle;

    protected int alertMessageId = android.R.id.message;

    protected int alertfirstButtonId = android.R.id.button1;

    protected int alertsecondButtonId = android.R.id.button2;

//    protected int yesString = R.string.yes;
//
//    protected int noString = R.string.no;
//
//    protected int titleId = R.id.title;
//
//    protected int signinString = R.string.signin;
//
//    protected int etInstanceUrlId = R.id.et_instance_url;
//
//    protected ViewInteraction nextButton;

 //   protected int nextButtonId = R.id.next;

 //   protected int nextButtonString = R.string.next;

 //   protected int progressBar = R.id.loader;

  //  protected int progressMessage = R.string.checking_instanceurl;

    protected int loginEmailId = R.string.loginEmailId;

    protected int loginPassword = R.string.loginPassword;

    protected int commonWaitTime = 1000;
    protected int mediumWaitTime = 3000;
    protected int longWaitTime = 5000;

 //   protected int tvWithoutPassCode = R.id.tvWithoutPassCode;

    protected int button1 = android.R.id.button1;


//    protected boolean mIsLoggedIn;
//    protected int correctInstanceUrlString = R.string.correctInstanceUrl;

    @Rule
    public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule();

    @Rule
    public RuleChain screenshotRule = RuleChain
            .outerRule(GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(new ScreenshotTestRule());


    /**
     * @param activityClass
     */
    public void launchActivity(Class activityClass) {
        EspressoIdlingResource.resetCounter();
        ActivityScenario.launch(activityClass);
    }

    /**
     *
     */
    protected void registerforIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getCounter());
    }

    public ViewInteraction getViewWithId(int resId) {
        return onView(allOf(withId(resId), isDisplayed()));
    }

    public ViewInteraction getViewWithIdString(int resId, int resString) {
        return onView(allOf(withId(resId), withText(resString), isDisplayed()));
    }

    public ViewInteraction getViewWithIdString(int resId, String resString) {
        return onView(allOf(withId(resId), withText(resString), isDisplayed()));
    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex;
            int viewObjHash;

            @SuppressLint("DefaultLocale")
            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("with index: %d ", index));
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (matcher.matches(view) && currentIndex++ == index) {
                    viewObjHash = view.hashCode();
                }
                return view.hashCode() == viewObjHash;
            }
        };
    }

    /**
     * @param resId
     * @param text
     */
    public void fillEditText(int resId, String text) {
        getViewWithId(resId)
                .perform(typeText(text), closeSoftKeyboard());
    }

    public ViewInteraction checkIfViewDisplayed(int resId, int resString) {
        return getViewWithIdString(resId, resString).check(matches(isDisplayed()));
    }

    public void clickOnPagerListItemView(int position, int view, int listId) {
        //   onView(AllOf.allOf(isDisplayed(), withId(listId))).perform(RecyclerViewActions.scrollToPosition(position));
        onView(allOf(isDisplayed(), allOf(withId(listId), isDisplayed())))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, RecyclerViewAction.clickChildViewWithId(view)));
    }

    public void swipeListItemView(int position, int listId, ViewAction viewAction) {
        //   onView(AllOf.allOf(isDisplayed(), withId(listId))).perform(RecyclerViewActions.scrollToPosition(position));
        onView(allOf(isDisplayed(), withId(listId)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, viewAction));
    }

    public ViewInteraction getViewWithIdIndex(int resId, int index) {
        return onView(withIndex(allOf(withId(resId), isDisplayed()), index));
    }

    public ViewAction setChecked(final boolean checked) {
        return new ViewAction() {
            @Override
            public BaseMatcher<View> getConstraints() {
                return new BaseMatcher<View>() {
                    @Override
                    public boolean matches(Object item) {
                        return isA(Checkable.class).matches(item);
                    }

                    @Override
                    public void describeMismatch(Object item, Description mismatchDescription) {
                    }

                    @Override
                    public void describeTo(Description description) {
                    }
                };
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                Checkable checkableView = (Checkable) view;
                checkableView.setChecked(checked);
            }
        };
    }

    public ViewInteraction checkIfViewDisplayedWithIndex(int resId, int index) {
        return getViewWithIdIndex(resId, index).check(matches(isDisplayed()));
    }

    public ViewInteraction checkIfViewDisplayed(int resId, String resString) {
        return getViewWithIdString(resId, resString).check(matches(isDisplayed()));
    }

    public ViewInteraction checkIfViewNotDisplayed(int resId, String resString) {
        return getViewWithId(resId).check(matches(not(withText(resString))));
    }

    public ViewInteraction checkIfViewDisplayedString(int resString) {
        return getViewWithString(resString).check(matches(isDisplayed()));
    }

    public ViewInteraction checkIfViewDisplayedString(String resString) {
        return getViewWithString(resString).check(matches(isDisplayed()));
    }

    public ViewInteraction clickViewDisplayedWithIndex(int index, String resString) {
        return getViewWithStringIndex(index, resString).perform(click());
    }

    public ViewInteraction getViewWithStringIndex(int index, String resString) {
        return onView(withIndex(allOf(withText(resString), isDisplayed()), index));
    }
    public void checkIfViewDisplayed(int resId) {
        getViewWithId(resId).check(matches(isDisplayed()));
    }
    public ViewInteraction checkIfViewDisplayedId(int resId) {
        return getViewWithId(resId).check(matches(isDisplayed()));
    }
    public void checkIfViewNotDisplayed(int resId) {
        getViewWithId(resId).check(matches(not(isDisplayed())));
    }

    public void clearEditTextBox(int resId) {
        getViewWithId(resId)
                .perform(clearText());
    }

    public void checkIfViewNotEnabled(ViewInteraction view) {
        view.check(matches(not(isEnabled())));
    }

    public void checkIfViewEnabled(ViewInteraction view) {
        view.check(matches(isEnabled()));
    }

    public void clickButtonOrView(int resId, int resString) {
        getViewWithIdString(resId, resString).perform(click());
    }

    public void clickButtonOrViewWithScroll(int resId, int resString) {
        getViewWithIdString(resId, resString).perform(scrollTo(), click());
    }
    public void clickButtonOrView(int resId) {
        getViewWithId(resId).perform(click());
    }


    public void clickButtonOrView(ViewInteraction viewInteraction) {
        viewInteraction.perform(click());
    }

    public void verifyTextOnPosition(int listId, int viewId, int position, String textToVerify) {
        onView(withRecyclerView(listId)
                .atPositionOnView(position, viewId))
                .check(matches(withText(textToVerify)));

    }
    public UiDevice getUIDeviceInstance() {
        return UiDevice.getInstance(androidx.test.platform.app.InstrumentationRegistry.getInstrumentation());
    }

    public UiSelector getUiSelector(int instance, Class className) {
        return new UiSelector()
                .instance(instance)
                .className(className);
    }

    public UiSelector getUiSelector(int instance, String text, Class className) {
        return new UiSelector()
                .instance(instance)
                .text(text)
                .className(className);
    }

    protected UiSelector getUiSelectorWithResourceId(String resourceId, Class className) {
        return new UiSelector()
                .resourceId(resourceId)
                .className(className);
    }

    protected UiSelector getUiSelectorWithResourceId(String resourceId, int instance, Class className) {
        return new UiSelector()
                .resourceId(resourceId)
                .instance(instance)
                .className(className);
    }
    protected UiSelector getUiSelector(String text, Class className) {
        return new UiSelector()
                .text(text)
                .className(className);
    }

    protected UiSelector getUiSelector(String text, String resourceId, Class className) {
        return new UiSelector()
                .resourceId(resourceId)
                .text(text)
                .className(className);
    }

    protected UiSelector getUiSelectorDesc(String textdesc, String resourceId, Class className) {
        return new UiSelector()
                .resourceId(resourceId)
                .description(textdesc)
                .className(className);
    }

    protected UiSelector getUiSelectorContain(String textContains, Class className) {
        return new UiSelector()
                .textContains(textContains)
                .className(className);
    }

    protected UiSelector getUiSelectorIndex(int index) {
        return new UiSelector()
                .index(index);

    }

    protected UiSelector getUiSelectorIndexClass(int index, Class className) {
        return new UiSelector()
                .index(index)
                .className(className);
    }

    protected UiSelector getUiSelectorDesc(String desc, Class className) {
        return new UiSelector()
                .description(desc)
                .className(className);
    }

    protected UiSelector getUiSelector(int instance, int index, String text, Class className) {
        return new UiSelector()
                .instance(instance)
                .index(index)
                .text(text)
                .className(className);
    }

    @Override
    public UiObject findObjectUiAutomatorWithResourceId(String resourceId, Class className) {
        UiObject uiObject = getUIDeviceInstance().findObject(getUiSelectorWithResourceId(resourceId, className));
        uiObject.waitForExists(EspressoConstants.WAIT_UIAUTOMATOR);
        return uiObject;
    }

    public UiObject findObjectUiAutomatorWithResourceId(String resourceId, int instance, Class className) {
        UiObject uiObject = getUIDeviceInstance().findObject(getUiSelectorWithResourceId(resourceId, instance, className));
        uiObject.waitForExists(EspressoConstants.WAIT_UIAUTOMATOR);
        return uiObject;
    }
    public UiObject findObjectUiAutomatorWithResourceIdNoWait(String resourceId, Class className) {
        return getUIDeviceInstance().findObject(getUiSelectorWithResourceId(resourceId, className));
    }

    public UiObject findObjectUiAutomator(int instance, int index, String text, Class className) {
        UiObject uiObject = getUIDeviceInstance().findObject(getUiSelector(instance, index, text, className));
        uiObject.waitForExists(EspressoConstants.WAIT_UIAUTOMATOR);
        return uiObject;
    }


    @Override
    public UiObject findObjectUiAutomator(int instance, String text, Class className) {
        UiObject uiObject = getUIDeviceInstance().findObject(getUiSelector(instance, text, className));
        uiObject.waitForExists(EspressoConstants.WAIT_UIAUTOMATOR);
        return uiObject;
    }

    @Override
    public UiObject findObjectUiAutomator(String text, Class className) {
        UiObject uiObject = getUIDeviceInstance().findObject(getUiSelector(text, className));
        uiObject.waitForExists(EspressoConstants.WAIT_UIAUTOMATOR);
        return uiObject;
    }


    public UiObject findObjectUiAutomator(String text, String resourceId, Class className) {
        UiObject uiObject = getUIDeviceInstance().findObject(getUiSelector(text, resourceId, className));
        uiObject.waitForExists(EspressoConstants.WAIT_UIAUTOMATOR);
        return uiObject;
    }

    public void verifyViewInListWithStartingText(int listId, int itemPosition, String startingText) {
        onView(allOf(isDisplayed(), withId(listId))).perform(RecyclerViewActions.actionOnItemAtPosition(itemPosition, scrollTo()))
                .check(matches(hasDescendant(withText(startsWith(startingText)))));
    }

    @Override
    protected void getAndClickObject(String text, String resourceId, Class className) throws UiObjectNotFoundException {
        UiObject uiObject = getUIDeviceInstance().findObject(getUiSelector(text, resourceId, className));
        uiObject.clickAndWaitForNewWindow();
    }

    @Override
    protected void getAndClickObjectDesc(String textdesc, String resourceId, Class className) throws UiObjectNotFoundException {
        UiObject uiObject = getUIDeviceInstance().findObject(getUiSelectorDesc(textdesc, resourceId, className));
        uiObject.clickAndWaitForNewWindow();
    }

    protected UiObject findObjectUiAutomatorContains(String textContains, Class className) {
        UiObject uiObject = getUIDeviceInstance().findObject(getUiSelectorContain(textContains, className));
        //   uiObject.waitForExists(R.integer.timout_ui_automator); umang
        return uiObject;
    }

    protected UiSelector getUiSelectorByText(String text) {
        return new UiSelector()
                .text(text);

    }

    protected UiObject findObjectUiAutomatorIndex(int index) {
        UiObject uiObject = getUIDeviceInstance().findObject(getUiSelectorIndex(index));
        //   uiObject.waitForExists(R.integer.timout_ui_automator); umang
        return uiObject;
    }

    protected UiObject findObjectUiAutomatorIndexClass(int index, Class className) {
        UiObject uiObject = getUIDeviceInstance().findObject(getUiSelectorIndexClass(index, className));
        return uiObject;
    }

    protected UiObject findObjectUiAutomatorDesc(String desc, Class className) {
        UiObject uiObject = getUIDeviceInstance().findObject(getUiSelectorDesc(desc, className));
        uiObject.waitForExists(R.integer.timout_ui_automator);
        return uiObject;
    }

    protected UiObject findObjectUiAutomator(int instance, int index, String text, Class className, boolean isCheckable, boolean isChecked) {
        UiObject uiObject = getUIDeviceInstance().findObject(getUiSelector(instance, index, text, className)
                .checkable(isCheckable)
                .checked(isChecked));
        // uiObject.waitForExists(R.integer.timout_ui_automator); umang
        return uiObject;
    }



    public ViewInteraction getViewWithString(int resString) {
        return onView(allOf(withText(resString), isDisplayed()));
    }

    public ViewInteraction getViewWithString(String resString) {
        return onView(allOf(withText(resString), isDisplayed()));
    }

    @Override
    protected void getAndClickObject(String text, Class classes) throws UiObjectNotFoundException {
        UiObject uiObject = getUiObject(text, classes);
        uiObject.clickAndWaitForNewWindow();
    }

    protected UiObject getUiObject(String text, Class classs) {
        UiObject uiObject = findObjectUiAutomator(text, classs);
        uiObject.waitForExists(EspressoConstants.WAIT_UIAUTOMATOR);
        return uiObject;
    }


    protected UiObject getUiObject(int index, String text, Class classs) {
        UiObject uiObject = findObjectUiAutomator(0, index, text, classs);
        uiObject.waitForExists(EspressoConstants.WAIT_UIAUTOMATOR);
        return uiObject;

    }

    @Override
    void getAndClickObject(int index, String text, Class classes) throws UiObjectNotFoundException {
        UiObject uiObject = getUiObject(index, text, classes);
        uiObject.clickAndWaitForNewWindow();
    }

    /**
     * Verify if signIn text is displayed on top in Login screen
     */
//    protected void verifySignInText() {
//        checkIfViewDisplayed(titleId, signinString);
//    }
//
//    /**
//     * Clear text from Instance Url edittextBox
//     */
//    protected void clearInstanceUrl() {
//        clearEditTextBox(etInstanceUrlId);
//    }
//
//    /**
//     * Check if next button is displayed
//     */
//    protected void getNextButton() {
//        if (nextButton == null)
//            nextButton = checkIfViewDisplayed(nextButtonId, nextButtonString);
//    }
//
//    /**
//     * get nextButton object and check if it's disable
//     */
//    protected void verifyNextButtonDisability() {
//        getNextButton();
//        checkIfViewNotEnabled(nextButton);
//    }

    protected void checkEnable(int viewId) {
        onView(allOf(isDisplayed(), withId(viewId))).check(matches(isEnabled()));
    }

    protected void checkNotEnable(int viewId) {
        onView(allOf(isDisplayed(), withId(viewId))).check(matches(not(isEnabled())));
    }

    /**
     * Enter correct Instance Url
     */
//    protected void enterCorrectInstanceUrl(int instanceUrl) {
//        fillEditText(etInstanceUrlId, getString(instanceUrl));
//    }
//
//    /**
//     * get nextButton object and check if it's enable
//     */
//    protected void verifyNextButtonEnability() {
//        getNextButton();
//        checkIfViewEnabled(nextButton);
//    }
//
//    /**
//     * get nextButton object and click on it
//     */
//    protected void clickNextButton() {
//        getNextButton();
//        clickButtonOrView(nextButton);
//    }

    protected void verifyLoginScreen() {
        getViewWithIdWebview(getString(loginEmailId));
        getViewWithIdWebview(getString(loginPassword));
    }

//    protected void logInApp(String userName, String password, int instanceUrl) throws UiObjectNotFoundException {
//        launchActivity(LoginActivity.class);
//        verifySignInText();
//        clearInstanceUrl();
//        verifyNextButtonDisability();
//        enterCorrectInstanceUrl(instanceUrl);
//        verifyNextButtonEnability();
//        clickNextButton();
//        try {
//            Thread.sleep(commonWaitTime);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        verifyProgressBar();
//        verifyLoginScreen();
//        //loginApplication(getString(R.string.system_admin_userEmail), getString(R.string.system_admin_userPassword));
//        loginApplication(userName, password);
//    }

    /**
     * Login in application with given email and password
     *
     * @param email
     * @param password
     * @throws UiObjectNotFoundException
     */
    @Override
    protected void loginApplication(String email, String password) throws UiObjectNotFoundException {
        initiateLogin(email, password);
        UiObject uiObject = getUiObject(getString(R.string.allow), Button.class);
        uiObject.clickAndWaitForNewWindow();
       // continueWithOutPasscode();
    }

//    void continueWithOutPasscode() {
//        try {
//            clickButtonOrView(tvWithoutPassCode);
//            clickButtonOrView(button1);
//        } catch (Exception e) {
//            Timber.e("Continue Without Passcode exception %s", e.getMessage());
//        }
//    }

    /**
     * Initiate Login process by providing email password and click on login button
     *
     * @param email
     * @param password
     * @throws UiObjectNotFoundException
     */
    protected void initiateLogin(String email, String password) throws UiObjectNotFoundException {
        enterEmailPassword(email, password);
        clickLogInbutton();
    }

    protected void enterEmailPassword(String email, String password) {
        clearElementbyIdWebview(getString(loginEmailId));
        fillEditTextWebview(getString(loginEmailId), email);
        clearElementbyIdWebview(getString(loginPassword));
        fillEditTextWebview(getString(loginPassword), password);
    }

    /**
     * Click Log in button webview application
     *
     * @throws UiObjectNotFoundException
     */
    protected void clickLogInbutton() throws UiObjectNotFoundException {
        UiObject buttonLogin = findObjectUiAutomator(getString(R.string.login_), Button.class);
        buttonLogin.waitForExists(R.integer.timout_ui_automator);
        buttonLogin.clickAndWaitForNewWindow();
    }


//    protected boolean isUserLoggedIn() {
//        // Log.d(TAG, "access token>>>> " + pref.getString(Constant.PREF_ACCESS_TOKEN, ""));
//        sharedPreferences = getSharedPreferences();
//        if (TextUtils.isEmpty(sharedPreferences.getString(Constant.PREF_ACCESSTOKEN, "")) || Constant.ZERO.equalsIgnoreCase(sharedPreferences.getString(Constant.PREF_ACCESSTOKEN, "")))
//            mIsLoggedIn = false;
//        else
//            mIsLoggedIn = true;
//        return mIsLoggedIn;
//    }

    /**
     * verify progressbar and progress message
     */
//    protected void verifyProgressBar() {
//        getViewWithIdString(progressBar, progressMessage);
//    }


//    protected void loginAndMoveToMainActivity(String userName, String password, int instanceUrl) throws UiObjectNotFoundException {
//        if (!mIsLoggedIn)
//            logInApp(userName, password, instanceUrl);
//        else {
//            launchActivity(MainActivity.class);
//        }
//    }

    /**
     * Logout current logged-in user
     */
//    public boolean logoutCurrentUserIfNeedLogin(String email, boolean isForcefullyLogout) {
//
//        if ((mIsLoggedIn && !compareEmail(email)) || (mIsLoggedIn && isForcefullyLogout)) {
//            openAndVerifyDrawerOpen();
//            clickButtonOrView(getViewWithString(R.string.logout));
//            try {
//                checkIfViewDisplayed(alertMessageId, R.string.logout_confirm);
//            } catch (Exception e) {
//                openAndVerifyDrawerOpen();
//                clickButtonOrView(getViewWithString(R.string.logout));
//                checkIfViewDisplayed(alertMessageId, R.string.logout_confirm);
//            }
//            clickButtonOrView(getViewWithString(yesString));
//            isUserLoggedIn();
//            return true;
//        }
//        return !mIsLoggedIn;
//    }
//
//
//    boolean compareEmail(String email) {
//        sharedPreferences = getSharedPreferences();
//        String userSavedEmail = sharedPreferences.getString(Constant.PREF_USER_EMAIL, "");
//        if (userSavedEmail != null && userSavedEmail.equalsIgnoreCase(email)) {
//            return true;
//        }
//        return false;
//    }

    public int getCountFromRecyclerView(@IdRes int RecyclerViewId) {
        final int[] COUNT = {0};
        Matcher matcher = new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                COUNT[0] = ((RecyclerView) item).getAdapter().getItemCount();
                return true;
            }

            @Override
            public void describeTo(Description description) {
            }
        };
        try {
            onView(allOf(withId(RecyclerViewId), isDisplayed())).check(matches(matcher));
        } catch (Exception e) {
            COUNT[0] = 0;
        }
        return COUNT[0];
    }

    /**
     * Perform action(Swipe left/right) on viewpager
     *
     * @param viewPagerId
     * @param viewACtion
     */
    public void performSwipe(int viewPagerId, ViewAction viewACtion) {
        onView(withId(viewPagerId)).perform(viewACtion);
    }

    protected void verifySnackBarText(String text) {
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(text)));
    }

    protected String getEditTextData(final Matcher<View> matcher) {
        final String[] stringHolder = {null};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(EditText.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a EditText";
            }

            @Override
            public void perform(UiController uiController, View view) {
                EditText editText = (EditText) view; //Save, because of check in getConstraints()
                stringHolder[0] = editText.getText().toString();
            }
        });
        return stringHolder[0];
    }

    protected String getTextViewData(final Matcher<View> matcher) {
        final String[] stringHolder = {null};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

    protected void verifyViewGone(int listId, int viewId, int position) {
        onView(withRecyclerView(listId)
                .atPositionOnView(position, viewId))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    protected void verifyViewVisible(int listId, int viewId, int position) {
        onView(withRecyclerView(listId)
                .atPositionOnView(position, viewId))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    public RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    protected void getIfCheckboxChecked(int resourceId, int index) {
        getViewWithIdIndex(resourceId, index).check(matches(isChecked()));
    }

    protected void getIfCheckboxNotChecked(int resourceId, int index) {
        getViewWithIdIndex(resourceId, index).check(matches(isNotChecked()));
    }
//    protected void launchOtherApp(String appPackage) {
//        UiDevice mDevice = getUIDeviceInstance();
//        android.content.Context context = ApplicationProvider.getApplicationContext();
//        final Intent intent = context.getPackageManager()
//                .getLaunchIntentForPackage(appPackage);
//        // Clear out any previous instances
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(intent);
//
//        // Wait for the app to appear
//        mDevice.wait(Until.hasObject(By.pkg(appPackage).depth(0)),
//                EspressoConstants.LAUNCH_TIMEOUT);
//    }
//
//    protected void pressKeyBoardButton(int viewId,int key) {
//        onView(withId(viewId)).perform(ViewActions.pressKey(key));
//    }
}

