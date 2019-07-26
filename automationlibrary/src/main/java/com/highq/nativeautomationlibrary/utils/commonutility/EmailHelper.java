/*
 * Copyright (c) 2019 or the year of first publication, if earlier, HighQ Solutions Limited or its licensors
 */

/*
 *
 */
package com.highq.nativeautomationlibrary.utils.commonutility;

import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * The Class EmailHelper.
 * This class is use for Email Sending purpose all methods and variables are use for same
 *
 * @author shivani.pathak
 */
public class EmailHelper {

    /**
     * The Constant ROW.
     */
    private static final String ROW = "</TH></H3></TR>";

    /**
     * The Constant data.
     */
    private static final String data = "</TD>";

    /**
     * The Constant dateTimeFormate.
     */
    private static final String dateTimeFormate = "dd:MMM:yyyy:hh:ss";

    /**
     * The Constant success.
     */
    private static final String success = "Build Success";

    /**
     * The Constant bold.
     */
    private static final String bold = "<TD align=center><b>";

    /**
     * The Constant alignment.
     */
    private static final String alignment = "<TD align=center>";
    /**
     * The Constant colorcode.
     */
    private static final String[] colorcode = {"#009900", "#ff0000"};
    /**
     * The Constant statusColor.
     */
    private static final String[] statusColor = {" ", "#ff0000"};
    /**
     * The Full Test case Retry Set.
     *
     * @author Deepak.Rathod
     */
    public static Set<String> failretrylist = new TreeSet();
    /**
     * The logger.
     */
    protected Logger logger = Logger.getLogger(this.getClass().getName());
    /**
     * The to.
     */
    String to = "";

    /**
     * The toSummary.
     */
    String toSummary = "";
    /**
     * The from.
     */
    String from = "highq.jenkinsautomation@gmail.com";
    /**
     * The pass.
     */
    String pass = "Automation@123";
    /**
     * The host.
     */
    String host = "";
    /**
     * The subject prefix.
     */
    String subjectPrefix = "AndroidHighQDrive";
    /**
     * The result.
     */
    private String result;

    /**
     * Mail configuration.
     *
     * @throws Exception the exception
     */
    public void mailConfiguration() throws Exception {
        sendMailViaGmail();
    }


    /**
     * Send mail via gmail.
     *
     * @throws Exception the exception
     */
    public void sendMailViaGmail() throws Exception {

        commonSendMail();
        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.user", from);
        properties.put("mail.smtp.password", pass);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });
        MimeMessage message = new MimeMessage(session);
        MimeMessage message1 = new MimeMessage(session);
        message1.setHeader("Content-Type", "text/plain; charset=UTF-8");

        message.setFrom(new InternetAddress(from));
        message1.setFrom(new InternetAddress(from));
        InternetAddress[] iAdressArray = InternetAddress.parse(to);
        message.setRecipients(Message.RecipientType.TO, iAdressArray);
        InternetAddress[] iAdressArray1 = InternetAddress.parse(toSummary);
        message1.setRecipients(Message.RecipientType.TO, iAdressArray1);

        if (RetryListener.getBuildStatus().equalsIgnoreCase(success)) {
            message.setSubject(RetryListener.getBuildStatus() + " (" + RetryListener.getTotalPass() + " cases) " + getSubjectPrefix() + " at " + RetryListener.dateAndSystemTime(dateTimeFormate));
            message1.setSubject(RetryListener.getBuildStatus() + " (" + RetryListener.getTotalPass() + " cases) Test at " + RetryListener.dateAndSystemTime(dateTimeFormate) + " TEAM " + getSubjectPrefix());
        } else {
            message.setSubject(RetryListener.getBuildStatus() + " (" + RetryListener.getTotalFail() + " out of " + RetryListener.getTotalTestCases() + " cases) " + getSubjectPrefix() + " at " + RetryListener.dateAndSystemTime(dateTimeFormate));
            message1.setSubject(RetryListener.getBuildStatus() + " (" + RetryListener.getTotalFail() + " out of " + RetryListener.getTotalTestCases() + " cases) Test at " + RetryListener.dateAndSystemTime(dateTimeFormate) + " TEAM " + getSubjectPrefix());
        }

        message.setContent(result, "text/html");

        Transport transport = session.getTransport("smtp");
        transport.connect(host, from, pass);
        transport.sendMessage(message, message.getAllRecipients());

        String testSummury = "TotalTestCases = " + RetryListener.sumOfAllScenarioCount + "\n"
                + "PassedTestCases = " + RetryListener.sumOfAllPassScenarioCount + "\n"
                + "FailedTestCases = " + RetryListener.sumOfAllFailScenarioCount + "\n"
                + "SkippedTestCases = " + RetryListener.sumOfAllSkipScenarioCount + "\n"
                + "FacetName = " + getSubjectPrefix();
        message1.setContent(testSummury, "text/plain; charset=UTF-8");
        transport.sendMessage(message1, message1.getAllRecipients());

        transport.close();
//		Transport.send(message);
        logger.info("Sent message successfully....");

    }

    /**
     * Common Template to send mail.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @author divya.patel
     */
    private void commonSendMail() {

        String testcaseStatusColor;
        String color;
        int i = 0;
        //	validateAttributes();
        if (success.equalsIgnoreCase(RetryListener.getBuildStatus())) {
            color = colorcode[0];
        } else {
            color = colorcode[1];
        }

        try {
            StringWriter out = new StringWriter();
            PrintWriter writer = new PrintWriter(out);
            writer.print("<TABLE BORDER= 5 Align=center WIDTH=50%   CELLPADDING=4  CELLSPACING=3><TR><TH COLSPAN=8 bgcolor=#d9d9d9><BR><font color=#008ae6><H2>Report Summary</H2></font>");
            writer.print("<TR><TH><h3><font color=#008ae6 > Test Scenario Name </font></TH>");
            writer.print("<TH><h3><font color=#008ae6 > Status </font></TH>");
            writer.print("<TH><h3> <font color=#008ae6 >Time</font></TH>");
            writer.print("<TH><h3> <font color=#008ae6 >Total Testcases </font></TH>");
            writer.print("<TH><h3> <font color=#008ae6 >PASS Testcases </font></TH>");
            writer.print("<TH><h3> <font color=#008ae6 >SKIP Testcases </font></TH>");
            writer.print("<TH><h3> <font color=#008ae6 >FAIL Testcases </font></TH>");
            writer.print("<TH><h3> <font color=#008ae6 >PASS RATE </font></TH></TR>");

            for (java.util.Map.Entry<String, String> singleTC : RetryListener.getTestcaseFinalStatus().entrySet()) {
                if ("PASS".equalsIgnoreCase(singleTC.getValue())) {
                    testcaseStatusColor = statusColor[0];
                } else {
                    testcaseStatusColor = statusColor[1];
                    failretrylist.add(singleTC.getKey());
                }

                writer.println("<TR><TD>" + singleTC.getKey() + data);
                writer.println("<TD align=center><font color=" + testcaseStatusColor + "> " + singleTC.getValue() + "</font></TD>");
                writer.println(alignment + RetryListener.timeList.get(i) + data);
                writer.println(alignment + RetryListener.totalScenario.get(i) + data);
                writer.println(alignment + RetryListener.pass.get(i) + data);
                writer.println(alignment + RetryListener.skip.get(i) + data);
                writer.println(alignment + RetryListener.fail.get(i) + data);
                writer.println(alignment + RetryListener.passRate.get(i) + "</TD></TR>");
                i++;
            }

            writer.print("<TR><TH><h3><font color=#008ae6 > Final Statistics </font></TH>");
            writer.println("<TD align=center><b><font color=" + color + ">" + RetryListener.getBuildStatus() + "</b></font></TD>");
            writer.println(bold + RetryListener.getFinalTime() + data);
            writer.println(
                    bold + RetryListener.sumOfAllScenarioCount + data);
            writer.println(bold + RetryListener.sumOfAllPassScenarioCount + data);
            writer.println(bold + RetryListener.sumOfAllSkipScenarioCount + data);
            writer.println(bold + RetryListener.sumOfAllFailScenarioCount + data);
            writer.println(bold + RetryListener.passRateFinalRatio + "</TD></TR>");
            writer.println("<TR> <TH><font color=#008ae6 > TOTAL Scenarios </font></TH> <TH COLSPAN=7><H3>"
                    + RetryListener.getTotalTestCases() + EmailHelper.ROW);
            writer.println("<TR> <TH><font color=#008ae6 > TOTAL PASS </font></TH> <TH COLSPAN=7><H3>"
                    + RetryListener.getTotalPass() + EmailHelper.ROW);
            writer.println("<TR> <TH><font color=#008ae6 > TOTAL FAIL </font></TH> <TH COLSPAN=7><H3>"
                    + RetryListener.getTotalFail() + EmailHelper.ROW);
            writer.println("</TABLE>");
            writer.flush();
            result = out.toString();
        } catch (Exception e) {
            Log.e("Email", e.getMessage());
        }
    }

    /**
     * Sets the host.
     *
     * @param host the new host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Gets the to.
     *
     * @return the to
     */
    public String getTo() {
        return this.to;
    }

    /**
     * Sets the to.
     *
     * @param to the new to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Sets the to.
     *
     * @param toSummary the new toSummary
     */
    public void setToSummary(String toSummary) {
        this.toSummary = toSummary;
    }
    /**
     * Gets the from.
     *
     * @return the from
     */
    public String getFrom() {
        return this.from;
    }

    /**
     * Sets the from.
     *
     * @param from the new from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Gets the subject prefix.
     *
     * @return the subject prefix
     */
    public String getSubjectPrefix() {
        return this.subjectPrefix;
    }

    /**
     * Sets the subject prefix.
     *
     * @param subjectPrefix the new subject prefix
     */
    public void setSubjectPrefix(String subjectPrefix) {
        this.subjectPrefix = subjectPrefix;
    }
}
