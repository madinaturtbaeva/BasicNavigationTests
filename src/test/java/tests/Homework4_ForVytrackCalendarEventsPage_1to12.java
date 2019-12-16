package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.BrowserFactory;
import utilities.BrowserUtils;
import java.util.List;

public class Homework4_ForVytrackCalendarEventsPage_1to12 {

    private WebDriver driver;
    private WebDriverWait wait;



    @BeforeMethod
    public void setup(){
        driver = BrowserFactory.getDriver("chrome");
        driver.manage().window().maximize();
        driver.get("https://qa1.vytrack.com/");
        driver.findElement(By.name("_username")).sendKeys("storemanager85");
        driver.findElement(By.name("_password")).sendKeys("UserUser123", Keys.ENTER);
        //driver.findElement(By.name("_submit")).click();
        //BrowserUtils.waitUntilLoaderMaskDisappear();
        //driver.findElement(By.linkText("Activities")).click();
        //driver.findElement(By.linkText("Calendar Events")).click();
        BrowserUtils.wait(3);
        driver.findElement(By.xpath("//*[normalize-space()='Activities' and @class='title title-level-1']")).click();
        driver.findElement(By.xpath("//*[normalize-space()='Calendar Events' and @class='title title-level-2']")).click();
        BrowserUtils.wait(2);

    }

    @Test(description = "Verify that “view”, “edit” and “delete” options are available")
    public void test1(){
        Actions action = new Actions(driver);
        WebElement hover  = driver.findElement(By.xpath("//tr[4]//td[9]"));
        action.moveToElement(hover).build().perform();
        BrowserUtils.wait(1);
        List<WebElement> options = hover.findElements(By.xpath("//ul[@class=\"nav nav-pills icons-holder launchers-list\"]"));
        for (WebElement option: options){
            Assert.assertTrue(option.isDisplayed());
        }
    }

    @Test(description = "Verify that “Title” column still displayed")
    public void test2(){
        driver.findElement(By.xpath("//div[@class=\"column-manager dropdown\"]")).click();
        List<WebElement> checkboxes = driver.findElements(By.xpath("//tbody//tr//td//input[@id]"));
        for(WebElement checkbox: checkboxes){
            if(checkbox == checkboxes.get(0)){
                continue;
            }
            else {
                checkbox.click();
                //BrowserUtils.wait(1);
            }
        }
        WebElement title = driver.findElement(By.xpath("//th//a//span[@class='grid-header-cell__label']"));
        Assert.assertTrue(title.isDisplayed());
    }

    @Test(description = "Verify that “Save And Close”, “Save And New” " +
                         "and “Save” options are available")
    public void test3(){
        driver.findElement(By.xpath("//a[@title=\"Create Calendar event\"]")).click();
        BrowserUtils.wait(2);
        driver.findElement(By.cssSelector("[class='btn-success btn dropdown-toggle']")).click();
        List<WebElement> options = driver.findElements(By.xpath("//li//button"));
        for(WebElement option: options){
            Assert.assertTrue(option.isDisplayed());
        }
    }

    @Test(description = "Verify that “All Calendar Events” page subtitle is displayed")
    public void test4(){
        driver.findElement(By.xpath("//a[@title=\"Create Calendar event\"]")).click();
        BrowserUtils.wait(2);
        driver.findElement(By.linkText("Cancel")).click();
        WebElement events = driver.findElement(By.className("oro-subtitle"));
        Assert.assertTrue(events.isDisplayed());
    }

    @Test(description = "Verify that difference between end and start time is exactly 1 hour")
    public void test5(){
        driver.findElement(By.xpath("//a[@title=\"Create Calendar event\"]")).click();
        BrowserUtils.wait(2);
        String startTime = driver.findElement(By.xpath("//input[@name=\"oro_calendar_event_form[start]\"]")).getAttribute("value");
        String endTime = driver.findElement(By.xpath("//input[@name=\"oro_calendar_event_form[end]\"]")).getAttribute("value");
        System.out.println("Start time and date: "+ startTime);
        System.out.println("End time and date: "+ endTime);
        startTime = startTime.substring(11, 16).replace(":", "");
        endTime = endTime.substring(11, 16).replace(":", "");
        int start = Integer.parseInt(startTime);
        int end = Integer.parseInt(endTime);
        if(end-start == 100){
            System.out.println("Passed ");
        }else {
            System.out.println("Failed");
        }
    }



    @AfterMethod
    public void teardown() {

        driver.quit();
    }
}



