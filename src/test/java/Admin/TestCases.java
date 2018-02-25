package Admin;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.concurrent.TimeUnit;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotSame;

public class TestCases {
    //private String AdminURL = "https://52.151.17.83/admin/";
    private String AdminURL = "https://localhost:8081";
    private boolean verboseMessages = false;        // true will write each action to the console
    private int secondsToTimeout = 2;
    private int numberRetriesFindElement = 2;
    private int SleepForSave = 2000;                // wait after clicking Save
    private int NormalSleepTime = 500;              // increases reliability
    private int NumberOfChangesToDiscard = 10;
    private int NumberOfRulesToAdd = 11;
    private enum Browser {CHROME, FIREFOX, IE, EDGE};
    private enum LocateType {ID, NAME, LINK_TEXT, CSS_SELECTOR, CLASS_NAME, XPATH};
    private enum Rules {USERAGENT, XCARMODEL, XMARKET, XNTGVERION, XSSIV, XVIN};
    private enum Environments {DEVELOPMENT, TEST, PRODUCTION}
    private boolean runChrome = true;               // true will runs Chrome cases, false reports Pass without running
    private boolean runFirefox = false;              //                Firefox
    private boolean runIE = true;                   //                Internet Explorer (not running on Win10)
    private boolean runEdge = true;                 //                Edge (only running on Win10)

    @Test
    public void Chrome_DiscardChanges() {
        if (runChrome)
            DiscardChanges(Browser.CHROME);
    }

    @Test
    public void Chrome_AddMultipleRules() {
        if (runChrome)
            AddMultipleRules(Browser.CHROME);
    }

    @Test
    public void Chrome_AddRemove_UserAgent_Development() {
        if (runChrome)
            AddRemove(Browser.CHROME, Rules.USERAGENT, Environments.DEVELOPMENT);
    }

    @Test
    public void Chrome_AddRemove_XCarModel_Development() {
        if (runChrome)
            AddRemove(Browser.CHROME, Rules.XCARMODEL, Environments.DEVELOPMENT);
    }

    @Test
    public void Chrome_AddRemove_XMarket_Development() {
        if (runChrome)
            AddRemove(Browser.CHROME, Rules.XMARKET, Environments.DEVELOPMENT);
    }

    @Test
    public void Chrome_AddRemove_XNtgVersion_Development() {
        if (runChrome)
            AddRemove(Browser.CHROME, Rules.XNTGVERION, Environments.DEVELOPMENT);
    }

    @Test
    public void Chrome_AddRemove_XSSIV_Development() {
        if (runChrome)
            AddRemove(Browser.CHROME, Rules.XSSIV, Environments.DEVELOPMENT);
    }

    @Test
    public void Chrome_AddRemove_XVIN_Development() {
        if (runChrome)
            AddRemove(Browser.CHROME, Rules.XVIN, Environments.DEVELOPMENT);
    }

    @Test
    public void Chrome_AddRemove_UserAgent_Test() {
        if (runChrome)
            AddRemove(Browser.CHROME, Rules.USERAGENT, Environments.TEST);
    }

    @Test
    public void Chrome_AddRemove_XCarModel_Test() {
        if (runChrome)
            AddRemove(Browser.CHROME, Rules.XCARMODEL, Environments.TEST);
    }

    @Test
    public void Chrome_AddRemove_XMarket_Test() {
        if (runChrome)
            AddRemove(Browser.CHROME, Rules.XMARKET, Environments.TEST);
    }

    @Test
    public void Chrome_AddRemove_XNtgVersion_Test() {
        if (runChrome)
            AddRemove(Browser.CHROME, Rules.XNTGVERION, Environments.TEST);
    }

    @Test
    public void Chrome_AddRemove_XSSIV_Test() {
        if (runChrome)
            AddRemove(Browser.CHROME, Rules.XSSIV, Environments.TEST);
    }

    @Test
    public void Chrome_AddRemove_XVIN_Test() {
        if (runChrome)
            AddRemove(Browser.CHROME, Rules.XVIN, Environments.TEST);
    }

    public void DiscardChanges (Browser browser) {
        VerboseMessages("DiscardChanges " + browser);
        WebDriver driver = BrowserFactory(browser);
        try {
            DeleteAllRules(driver);
            int initialNumberOfRules = GetCountOfRules(driver);

            CreateChanges(driver, NumberOfChangesToDiscard);

            // Click the Discard Changes button
            WebElement discardChanges = Locate(driver, LocateType.ID, "btn-reset", "Discard Changes Button");
            for (int i = 0; i < 6; i++) {
                discardChanges.click();
                Sleep(NormalSleepTime);
            }

            // Verify a rule has been added using count of rules
            int finalNumberOfRules = GetCountOfRules(driver);
            assertEquals("Failed to Discard Changes, initial: " + initialNumberOfRules + " final: " +
                    finalNumberOfRules, initialNumberOfRules, finalNumberOfRules);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            Cleanup(driver);
        }
    }

    public void ClickSave(WebDriver driver) {
        // Click the Save button
        WebElement save = Locate(driver, LocateType.ID, "btn-save", "Save Button");
        save.click();
        Sleep(2000);
        driver.navigate().refresh();
    }

    public void AddMultipleRules (Browser browser) {
        VerboseMessages("AddMultipleRules " + browser);
        WebDriver driver = BrowserFactory(browser);
        try {
            DeleteAllRules(driver);
            CreateChanges(driver, NumberOfRulesToAdd);

            ClickSave(driver);
            // Verify a rule has been added using count of rules
            int finalNumberOfRules = GetCountOfRules(driver);
            assertEquals("Failed to Save rules, expected: " + NumberOfRulesToAdd + " actual: " +
                    finalNumberOfRules, NumberOfRulesToAdd, finalNumberOfRules);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            Cleanup(driver);
        }
    }

    public void CreateChanges (WebDriver driver, int changes) {
        WebElement inputRule = Locate(driver, LocateType.ID, "input-rule", "input-rule");
        for (int i = 0; i < changes; i++) {
            // Clear existing value
            inputRule.clear();
            Sleep(NormalSleepTime);
            // Enter a value in Rule
            inputRule.sendKeys(Integer.toString(i));
            Sleep(NormalSleepTime);
            // Click the Commit field
            WebElement commitField = Locate(driver, LocateType.ID, "btn-add-rule", "btn-add-rule");
            commitField.click();
            Sleep(NormalSleepTime);
        }

        // TODO get verification working
        // Verify Changes exist
        //List<WebElement> rows = driver.findElements(By.xpath("//*[@id='table']"));
        //int newNumberOfRules = rows.size();
        //assertEquals("Failed to created Changes, actual: " + newNumberOfRules + " expected: " + changes,
        //        newNumberOfRules, changes);

    }

    public void AddRemove(Browser browser, Rules rule, Environments environment) {
        VerboseMessages("RunCase " + browser + " " + rule + " " + environment);
        WebDriver driver = BrowserFactory(browser);
        try {
            DeleteAllRules(driver);

            // Add new rule
            Add_Rule(driver, rule, environment);

            // Delete the rule
            Remove_Rule(driver, 0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            Cleanup(driver);
        }
    }

    private void Cleanup(WebDriver driver) {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
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
            case XSSIV:
                ruleValueToEnter = "x-ssiv " + newRuleNumber;
                ruleSelect.selectByVisibleText("X-SSIV");
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

        ClickSave(driver);

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

        ClickSave(driver);

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
                ChromeOptions chromeOpt = new ChromeOptions();
                chromeOpt.addArguments("--log-level=3");
                System.setProperty("webdriver.chrome.args", "--disable-logging");
                System.setProperty("webdriver.chrome.silentOutput", "true");
                driver = new ChromeDriver(chromeOpt);
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
        Sleep(250);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(secondsToTimeout, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(secondsToTimeout, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(secondsToTimeout, TimeUnit.SECONDS);
        driver.navigate().to(AdminURL);
        Sleep(NormalSleepTime);
        String expectedTitleBarContains = "VIN-Router";
        String actualTitleBar = driver.getTitle();
        Boolean titleContainsVIN = actualTitleBar.contains(expectedTitleBarContains);
        if (!titleContainsVIN) {
            System.out.println("Failure VIN Router Admin Service is not running");
            Cleanup(driver);
            assertFalse("Failure VIN Router Admin Service is not running", true);
        }
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