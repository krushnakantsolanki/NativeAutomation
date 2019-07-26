package com.highq.nativeautomationlibrary.utils.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.web.sugar.Web;
import androidx.test.espresso.web.webdriver.DriverAtoms;
import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;


import com.highq.nativeautomationlibrary.R;

import timber.log.Timber;

import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.clearElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.findElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.webClick;
import static androidx.test.espresso.web.webdriver.DriverAtoms.webScrollIntoView;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public abstract class BaseCollaborate {

    protected int loginEmailIdColl = R.string.loginEmailColl;

    protected int loginPasswordColl = R.string.loginPasswordColl;

//    protected SharedPreferences sharedPreferences;

    protected Context targetContext;

//    protected SharedPreferences getSharedPreferences() {
//        if (sharedPreferences == null)
//            sharedPreferences = getTargetContext().getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences;
//    }


    protected Context getTargetContext() {
        if (targetContext == null)
            targetContext = getInstrumentation().getTargetContext();
        return targetContext;
    }

    protected Web.WebInteraction getViewWithIdWebview(String resId) {
        return onWebView().withElement(findElement(Locator.ID, resId));
    }

    protected Web.WebInteraction getViewWithXpathWebview(String xpath) {
        return onWebView().withElement(findElement(Locator.XPATH, xpath));
    }

    protected Web.WebInteraction getViewWithLinkWebview(String linkText) {
        return onWebView().withElement(findElement(Locator.LINK_TEXT, linkText));
    }

    protected void fillEditTextWebview(String resId, String text) {
        getViewWithIdWebview(resId).perform(DriverAtoms.webKeys(text));
    }

    protected void enterEmailPasswordCollaborate(String email, String password) {
        fillEditTextWebview(getString(loginEmailIdColl), email);
        fillEditTextWebview(getString(loginPasswordColl), password);
    }
//    protected void enterEmailPasswordSystemAdminCollaborate() {
//        fillEditTextWebview(getString(R.string.loginEmailColl), getString(R.string.system_admin_userEmail));
//        fillEditTextWebview(getString(R.string.loginPasswordColl), getString(R.string.system_admin_userPassword));
//    }

    protected void clearElementbyIdWebview(String resId) {
        getViewWithIdWebview(resId).perform(clearElement());
    }

    protected void clickElementbyIdWebview(String resId) {
        getViewWithIdWebview(resId).perform(webClick());
    }

    protected void clickElementbyXPathWebview(String xpath) {
        getViewWithXpathWebview(xpath).perform(webClick());
    }

    protected void clickElementbyLinkWebview(String linkText) {
        getViewWithLinkWebview(linkText).perform(webClick());
    }

    /**
     * Login at collaborate for setting prerequisite.
     * Sometimes webview is opening mobile view screen instead of collaborate screen so in catch clock it has been logged in with application
     *
     * @return
     */
    protected boolean loginCollaborate(String email, String password) {
        try {
            enterEmailPasswordCollaborate(email, password);
            clickElementbyXPathWebview(".//*[@id='margleft7']");
            clickElementbyXPathWebview(".//*[@id='Login']//button[text()='Sign in']");
        } catch (Exception e) {

            try {
                loginApplication(email, password);
            } catch (Exception e1) {
                e1.printStackTrace();
                return false;
            }
        }
        return true;
    }

    abstract void loginApplication(String email, String password) throws UiObjectNotFoundException;

    abstract UiObject findObjectUiAutomator(int instance, String text, Class className);

    abstract UiObject findObjectUiAutomatorDesc(String desc, Class className);


    abstract void getAndClickObject(String text, String resourceId, Class className) throws UiObjectNotFoundException;

    abstract void getAndClickObjectDesc(String textDesc, String resourceId, Class className) throws UiObjectNotFoundException;

    abstract UiObject findObjectUiAutomatorWithResourceId(String resourceId, Class className);

    abstract UiObject findObjectUiAutomator(String text, Class className);

    abstract void getAndClickObject(String text, Class classes) throws UiObjectNotFoundException;

    abstract void getAndClickObject(int index, String text, Class classes) throws UiObjectNotFoundException;

    /**
     * @param stringId
     * @return
     */
    protected String getString(int stringId) {
        return getAppContext().getString(stringId);
    }

    /**
     * @param stringId
     * @return
     */
    protected String[] getStringArray(int stringId) {
        return getAppContext().getResources().getStringArray(stringId);
    }


    protected Context getAppContext() {
        return ApplicationProvider.getApplicationContext();
    }
    /**
     * @param stringId
     * @param argument
     * @return
     */
    protected String getString(int stringId, Object... argument) {
        return ApplicationProvider.getApplicationContext().getString(stringId, argument);
    }



    public boolean isHomePage() {
        try {
            getViewWithXpathWebview(".//div[@class='dashTitle clearfix'][contains(.,'Recent activity')]");
            getViewWithXpathWebview(".//span[@class='token-label'][contains(.,'Sites I am a member of')]\")");
            return true;
        } catch (Exception e) {
            Timber.e(e);
            return false;
        }
    }

//    @Override
//    public void onResponseReceived(int type, Object response) {
//    }
//
//    @Override
//    public void showProgress(String message) {
//
//    }
//
//    @Override
//    public void stopProgress() {
//
//    }
//
//    @Override
//    public Activity getActivityContext() {
//        return null;
//    }
//
//    @Override
//    public void noInternet() {
//
//    }
//
//    @Override
//    public void webServiceError(String title, String errormsg) {
//
//    }
}
