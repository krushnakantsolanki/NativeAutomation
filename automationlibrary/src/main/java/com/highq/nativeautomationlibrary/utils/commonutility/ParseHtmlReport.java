package com.highq.nativeautomationlibrary.utils.commonutility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ParseHtmlReport {

    public void parseHtml(String html) {
        Document doc = Jsoup.parse(html, "UTF-8");
        Elements testscounter = doc.getElementsByClass("counter");

        RetryListener.sumOfAllScenarioCount = Integer.parseInt(testscounter.get(0).text());
        RetryListener.sumOfAllFailScenarioCount = Integer.parseInt(testscounter.get(1).text());
        RetryListener.sumOfAllPassScenarioCount = RetryListener.sumOfAllScenarioCount - RetryListener.sumOfAllFailScenarioCount;
        Elements successRate = doc.getElementsByClass("percent");
        RetryListener.passRateFinalRatio = successRate.get(0).text();
        RetryListener.setFinalTime(testscounter.get(2).text());
        Element tabClass;
        if (RetryListener.sumOfAllFailScenarioCount == 0) {
            RetryListener.setBuildStatus(RetryListener.success);
            tabClass = doc.getElementById("tab1");
        } else {
            RetryListener.setBuildStatus(RetryListener.failed);
            tabClass = doc.getElementById("tab2");
        }
        Elements testcases = tabClass.select("a[href]");
        ArrayList<String> testCasesList = new ArrayList<>();
        for (Element testCase : testcases) {
            testCasesList.add(testCase.text());
        }
        RetryListener.setTotalTestCases(testCasesList.size());
        Elements allData = tabClass.select("td");
        int i = 0;

        String status;
        int totalPass = 0;
        int totalFail = 0;
        for (Element element : allData) {
            if (i == 5)
                i = 0;
            if (i == 0) {
                if (element.getElementsByClass("success").size() > 0) {
                    status = "PASS";
                    totalPass++;
                } else {
                    status = "FAIL";
                    totalFail++;
                }

                RetryListener.tetCaseStatus.add(status);
            } else if (i == 1)
                RetryListener.totalScenario.add(element.text());
            else if (i == 2) {
                RetryListener.skip.add(0);
                RetryListener.fail.add(element.text());
                if (element.text().equalsIgnoreCase("0"))
                    RetryListener.pass.add(RetryListener.totalScenario.get(RetryListener.totalScenario.size() - 1));
                else
                    RetryListener.pass.add(String.valueOf(Integer.parseInt(RetryListener.totalScenario.get(RetryListener.totalScenario.size() - 1)) - Integer.parseInt(element.text())));

            } else if (i == 3)
                RetryListener.timeList.add(element.text());
            else if (i == 4) {
                RetryListener.passRate.add(element.text());
            }
            i++;
        }
        RetryListener.setTotalPass(totalPass);
        RetryListener.setTotalFail(totalFail);

        for (int j = 0; j < testCasesList.size(); j++) {
            RetryListener.getTestcaseFinalStatus().put(testCasesList.get(j), RetryListener.tetCaseStatus.get(j));
        }

    }
}
