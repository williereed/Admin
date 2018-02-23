package Admin;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertNotSame;

public class TestCases {
    //private String AdminURL = "https://52.151.17.83/admin/";
    private String AdminURL = "https://localhost:8081";
    private boolean verboseMessages = false;                   // true will write each action to the console
    private int secondsToTimeout = 2;
    private int numberRetriesFindElement = 2;
    private int SleepForSave = 2000;                // wait after clicking Sleep
    private int NormalSleepTime = 500;              // increases reliability
    private enum Browser {CHROME, FIREFOX, IE, EDGE};
    private enum LocateType {ID, NAME, LINK_TEXT, CSS_SELECTOR, CLASS_NAME, XPATH};
    private enum Rules {USERAGENT, XCARMODEL, XMARKET, XNTGVERION, XSSID, XVIN};
    private enum Environments {DEVELOPMENT, TEST, PRODUCTION}
    private boolean runChrome = true;               // true will runs Chrome cases, false reports Pass without running
    private boolean runFirefox = true;              //                Firefox
    private boolean runIE = true;                   //                Internet Explorer (not running on Win10)
    private boolean runEdge = true;                 //                Edge (only running on Win10)

    @Test
    public void Chrome_AddRemove_UserAgent_Development() {
        if (runChrome)
            RunCase(Browser.CHROME, Rules.USERAGENT, Environments.DEVELOPMENT);
    }

    @Test
    public void Chrome_AddRemove_XCarModel_Development() {
        if (runChrome)
            RunCase(Browser.CHROME, Rules.XCARMODEL, Environments.DEVELOPMENT);
    }

    @Test
    public void Chrome_AddRemove_XMarket_Development() {
        if (runChrome)
            RunCase(Browser.CHROME, Rules.XMARKET, Environments.DEVELOPMENT);
    }

    @Test
    public void Chrome_AddRemove_XNtgVersion_Development() {
        if (runChrome)
            RunCase(Browser.CHROME, Rules.XNTGVERION, Environments.DEVELOPMENT);
    }

    @Test
    public void Chrome_AddRemove_XSSID_Development() {
        if (runChrome)
            RunCase(Browser.CHROME, Rules.XSSID, Environments.DEVELOPMENT);
    }

    @Test
    public void Chrome_AddRemove_XVIN_Development() {
        if (runChrome)
            RunCase(Browser.CHROME, Rules.XVIN, Environments.DEVELOPMENT);
    }

    @Test
    public void Chrome_AddRemove_UserAgent_Test() {
        if (runChrome)
            RunCase(Browser.CHROME, Rules.USERAGENT, Environments.TEST);
    }

    @Test
    public void Chrome_AddRemove_XCarModel_Test() {
        if (runChrome)
            RunCase(Browser.CHROME, Rules.XCARMODEL, Environments.TEST);
    }

    @Test
    public void Chrome_AddRemove_XMarket_Test() {
        if (runChrome)
            RunCase(Browser.CHROME, Rules.XMARKET, Environments.TEST);
    }

    @Test
    public void Chrome_AddRemove_XNtgVersion_Test() {
        if (runChrome)
            RunCase(Browser.CHROME, Rules.XNTGVERION, Environments.TEST);
    }

    @Test
    public void Chrome_AddRemove_XSSID_Test() {
        if (runChrome)
            RunCase(Browser.CHROME, Rules.XSSID, Environments.TEST);
    }

    @Test
    public void Chrome_AddRemove_XVIN_Test() {
        if (runChrome)
            RunCase(Browser.CHROME, Rules.XVIN, Environments.TEST);
    }

    @Test
    public void Firefox_AddRemove_UserAgent_Development() {
        if (runFirefox)
            RunCase(Browser.FIREFOX, Rules.USERAGENT, Environments.DEVELOPMENT);
    }

    @Test
    public void Firefox_AddRemove_XCarModel_Development() {
        if (runFirefox)
            RunCase(Browser.FIREFOX, Rules.XCARMODEL, Environments.DEVELOPMENT);
    }

    @Test
    public void Firefox_AddRemove_XMarket_Development() {
        if (runFirefox)
            RunCase(Browser.FIREFOX, Rules.XMARKET, Environments.DEVELOPMENT);
    }

    @Test
    public void Firefox_AddRemove_XNtgVersion_Development() {
        if (runFirefox)
            RunCase(Browser.FIREFOX, Rules.XNTGVERION, Environments.DEVELOPMENT);
    }

    @Test
    public void Firefox_AddRemove_XSSID_Development() {
        if (runFirefox)
            RunCase(Browser.FIREFOX, Rules.XSSID, Environments.DEVELOPMENT);
    }

    @Test
    public void Firefox_AddRemove_XVIN_Development() {
        if (runFirefox)
            RunCase(Browser.FIREFOX, Rules.XVIN, Environments.DEVELOPMENT);
    }

    @Test
    public void Firefox_AddRemove_UserAgent_Test() {
        if (runFirefox)
            RunCase(Browser.FIREFOX, Rules.USERAGENT, Environments.TEST);
    }

    @Test
    public void Firefox_AddRemove_XCarModel_Test() {
        if (runFirefox)
            RunCase(Browser.FIREFOX, Rules.XCARMODEL, Environments.TEST);
    }

    @Test
    public void Firefox_AddRemove_XMarket_Test() {
        if (runFirefox)
            RunCase(Browser.FIREFOX, Rules.XMARKET, Environments.TEST);
    }

    @Test
    public void Firefox_AddRemove_XNtgVersion_Test() {
        if (runFirefox)
            RunCase(Browser.FIREFOX, Rules.XNTGVERION, Environments.TEST);
    }

    @Test
    public void Firefox_AddRemove_XSSID_Test() {
        if (runFirefox)
            RunCase(Browser.FIREFOX, Rules.XSSID, Environments.TEST);
    }

    @Test
    public void Firefox_AddRemove_XVIN_Test() {
        if (runFirefox)
            RunCase(Browser.FIREFOX, Rules.XVIN, Environments.TEST);
    }

    public void RunCase(Browser browser, Rules rule, Environments environment) {
        VerboseMessages("RunCase " + browser + " " + rule + " " + environment);
        WebDriver driver = BrowserFactory(browser);
        DeleteAllRules(driver);

        // Add new rule
        try {
            Add_Rule(driver, rule, environment);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        // Delete the rule
        try {
            Remove_Rule(driver, 0);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        driver.close();
    }

    private void Sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        }
        catch (Exception ex) {}
    }

    private void Add_Rule(WebDriver driver, Rules rule, Environments environment) {
        VerboseMessages("Add_Rule " + rule + " " + environment);
        int initialNumRules = GetCountOfRules(driver);
        int newRuleNumber = initialNumRules + 1;
        String ruleValueToEnter = "";

        // Set the Header field
        Select ruleSelect = new Select(driver.findElement(By.id("input-header")));
        Sleep(NormalSleepTime);
        switch (rule)
        {
            case USERAGENT:
                ruleValueToEnter = "user-agent " + newRuleNumber;
                ruleSelect.selectByVisibleText("USER-AGENT");
                break;
            case XCARMODEL:
                ruleValueToEnter = "x-car-model " + newRuleNumber;
                ruleSelect.selectByVisibleText("X-CAR-MODEL");
                break;
            case XMARKET:
                ruleValueToEnter = "x-market " + newRuleNumber;
                ruleSelect.selectByVisibleText("X-MARKET");
                break;
            case XNTGVERION:
                ruleValueToEnter = "x-ntg-version " + newRuleNumber;
                ruleSelect.selectByVisibleText("X-NTG-VERSION");
                break;
            case XSSID:
                ruleValueToEnter = "x-ssid " + newRuleNumber;
                ruleSelect.selectByVisibleText("X-SSID");
                break;
            case XVIN:
                ruleValueToEnter = Integer.toString(newRuleNumber);
                while (ruleValueToEnter.length() < 17)
                    ruleValueToEnter = ruleValueToEnter + "0";
                ruleSelect.selectByVisibleText("X-VIN");
                break;
        }

        // Set the Rule field
        WebElement inputRule = Locate(driver, LocateType.ID, "input-rule", "input-rule");
        inputRule.sendKeys(ruleValueToEnter);
        Sleep(NormalSleepTime);

        // Set the Target field
        Select targetSelect = new Select(driver.findElement(By.id("input-target")));
        switch(environment) {
            case DEVELOPMENT:
                targetSelect.selectByVisibleText("Development");
                break;
            case TEST:
                targetSelect.selectByVisibleText("Test");
                break;
            case PRODUCTION:
                targetSelect.selectByVisibleText("Production");
                break;
        }

        // Click the Commit field
        WebElement commitField = Locate(driver, LocateType.ID, "btn-add-rule", "btn-add-rule");
        commitField.click();
        Sleep(NormalSleepTime);

        // Click the Save button
        WebElement buttonSave = Locate(driver, LocateType.ID, "btn-save", "btn-save");
        buttonSave.click();
        Sleep(SleepForSave);

        // Verify a rule has been added using count of rules
        assertNotSame("Failed to add rule... count of rules did not increase. ", initialNumRules, GetCountOfRules(driver));

        // TODO fix this for multiple rows, currently expecting new rule to be first row

        // Verify values in the rule
        WebElement cell1 = Locate(driver, LocateType.XPATH, "//*[@id='table-tbody']/tr/td[2]", "cell1");
        String cell1text = cell1.getText();
        VerboseMessages("    " + cell1text);
        assertNotSame("Cell 1 expected: " + rule + " actual: " + cell1text, rule, cell1text);

        WebElement cell2 = Locate(driver, LocateType.XPATH, "//*[@id='table-tbody']/tr/td[3]", "cell2");
        String cell2text = cell2.getText();
        VerboseMessages("    " + cell2text);
        assertNotSame("Cell 2 expected: " + ruleValueToEnter + " actual: " + cell2text, ruleValueToEnter, cell2text);

        WebElement cell3 = Locate(driver, LocateType.XPATH, "//*[@id='table-tbody']/tr/td[4]", "cell3");
        String cell3text = cell3.getText();
        VerboseMessages("    " +cell3text );
        assertNotSame("Cell 3 expected: " + environment + " actual: " + cell3text, environment, cell3text);

    }

    private void Remove_Rule(WebDriver driver, int ruleNumberToRemove) {
        VerboseMessages("Remove_Rule " + ruleNumberToRemove);
        int initialNumRules = GetCountOfRules(driver);
        WebElement removeRule = Locate(driver, LocateType.ID, "btn-remove-" + ruleNumberToRemove, "remove rule" + ruleNumberToRemove);
        removeRule.click();
        Sleep(NormalSleepTime);

        WebElement saveButton = Locate(driver, LocateType.ID, "btn-save", "Save Button");
        saveButton.click();
        Sleep(SleepForSave);

        // Verify rule has been removed
        assertNotSame("Failed to remove rule, count of rules not changed ", initialNumRules, GetCountOfRules(driver));
    }

    private void DeleteAllRules(WebDriver driver) {
        VerboseMessages("DeleteAllRules");
        WebElement existingRule = null;
        WebElement saveButton = null;
        int countDeleted = 0;
        while (1 > -1) {
            try {
                existingRule = CheckExists(driver, LocateType.ID, "btn-remove-0", "Remove Button 0");
                existingRule.click();
                saveButton = Locate(driver, LocateType.ID, "btn-save", "Save Button");
                saveButton.click();
                Sleep(SleepForSave);
            } catch (Exception ex) {
                VerboseMessages("    " + countDeleted + " rules deleted");
                return;
            }
        }
    }

    private int GetCountOfRules(WebDriver driver) {
        VerboseMessages("GetCountOfRules");
        int numRules = 0;
        while (numRules > -1) {
            try {
                CheckExists(driver, LocateType.ID, "btn-remove-" + numRules, "Remove Button " + numRules);
                numRules++;
            } catch (Exception ex) {
                VerboseMessages("    " + numRules + " rules found");
                return numRules;
            }
        }
        return numRules;
    }

    private void VerboseMessages(String message) {
        if (verboseMessages)
            System.out.println(message);
    }

    private WebDriver BrowserFactory(Browser browser) {
        VerboseMessages("BrowserFactory " + browser.name());
        WebDriver driver = null;

        switch (browser) {
            case CHROME:
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case IE:
                driver = new InternetExplorerDriver();
                break;
            case EDGE:
                driver = new EdgeDriver();
                break;
            default:
                driver = new ChromeDriver();
        }
        try {
            driver.manage().window().maximize();
        }
        catch (Exception ex) {}
        driver.manage().timeouts().implicitlyWait(secondsToTimeout, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(secondsToTimeout, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(secondsToTimeout, TimeUnit.SECONDS);

        driver.navigate().to(AdminURL);
        return driver;
    }

    private WebElement CheckExists(WebDriver driver, LocateType lt, String search, String message) {
        VerboseMessages("CheckExists " + message + " using " + lt + " " + search);
        switch (lt) {
            case ID:
                Sleep(NormalSleepTime);
                return driver.findElement(By.id(search));
            case NAME:
                Sleep(NormalSleepTime);
                return driver.findElement(By.name(search));
            case LINK_TEXT:
                Sleep(NormalSleepTime);
                return driver.findElement(By.linkText(search));
            case CSS_SELECTOR:
                Sleep(NormalSleepTime);
                return driver.findElement(By.cssSelector(search));
            case CLASS_NAME:
                Sleep(NormalSleepTime);
                return driver.findElement(By.className(search));
            default:
        }
        return null;
    }

    private WebElement Locate(WebDriver driver, LocateType lt, String search, String message) {
        VerboseMessages("Locate " + message + " using " + lt + " " + search);
        WebElement found = null;
        switch (lt) {
            case ID:
                for (int i = 0; i < numberRetriesFindElement; i++) {
                    Sleep(NormalSleepTime);
                    try {
                        found = driver.findElement(By.id(search));
                        break;
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                break;
            case NAME:
                for (int i = 0; i < numberRetriesFindElement; i++) {
                    try {
                        Thread.sleep(NormalSleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        found = driver.findElement(By.name(search));
                        break;
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                break;
            case LINK_TEXT:
                for (int i = 0; i < numberRetriesFindElement; i++) {
                    Sleep(NormalSleepTime);
                    try {
                        found = driver.findElement(By.linkText(search));
                        break;
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                break;
            case CSS_SELECTOR:
                for (int i = 0; i < numberRetriesFindElement; i++) {
                    Sleep(NormalSleepTime);
                    try {
                        found = driver.findElement(By.cssSelector(search));
                        break;
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                break;
            case CLASS_NAME:
                for (int i = 0; i < numberRetriesFindElement; i++) {
                    Sleep(NormalSleepTime);
                    try {
                        found = driver.findElement(By.className(search));
                        break;
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                break;
            case XPATH:
                for (int i = 0; i < numberRetriesFindElement; i++) {
                    Sleep(NormalSleepTime);
                    try {
                        found = driver.findElement(By.xpath(search));
                        break;
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                break;
            default:
                found = null;
        }
        return found;
    }
}