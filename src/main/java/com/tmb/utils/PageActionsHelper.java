package com.tmb.utils;

import com.tmb.driver.DriverManager;
import org.openqa.selenium.By;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class PageActionsHelper {

  public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(180);
  public static final Duration DEFAULT_POLLING_TIME = Duration.ofMillis(500);

  private PageActionsHelper() {
  }

  public static void waitAndClick(By by) {
    waitForDomReady();
    DriverManager.getDriver().findElement(by).click();
  }

  public static void waitAndSendKeys(By by, String value) {
    waitForDomReady();
    DriverManager.getDriver().findElement(by).sendKeys(value);
  }

  public static void select(By by, Consumer<Select> consumer) {
    consumer.accept(new Select(DriverManager.getDriver().findElement(by)));
  }

  public static String getAttribute(By by, Function<WebElement, String> attributeFunction) {
    return attributeFunction.apply(DriverManager.getDriver().findElement(by));
  }

  public static boolean isPresent(By by, Predicate<WebElement> elementPredicate) {
    waitForDomReady();
    return elementPredicate.test(DriverManager.getDriver().findElement(by));
  }

  private static boolean isNotEndOfPage(String previousPageSource) {
    return !previousPageSource.equals(DriverManager.getDriver().getPageSource());
  }

  private static boolean isElementNotEnabled(WebElement element) {
    try {
      return !element.isDisplayed();
    } catch (NoSuchElementException e) {
      return true;
    }
  }

  private static boolean isElementNotEnabled(By by) {
    List<WebElement> elements = DriverManager.getDriver().findElements(by);
    if (!elements.isEmpty()) {
      return elements.get(0).getAttribute("visible").equalsIgnoreCase("false");
    }
    return true;
  }

  private static void waitForDomReady() {
    WebDriver driver = DriverManager.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    wait.pollingEvery(DEFAULT_POLLING_TIME);

    try {
      wait.until(domContentLoaded());
    } catch (Exception e) {
      // Log warning but continue as page might still be usable
      System.out.println("Warning: DOM ready state wait timed out: " + e.getMessage());
    }
  }

//  private static ExpectedCondition<Boolean> domReadyState() {
//    return driver -> {
//      try {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        return "complete".equals(js.executeScript("return document.readyState"));
//      } catch (Exception e) {
//        return false;
//      }
//    };
//  }

  /**
   * Creates an ExpectedCondition that checks if the DOM is ready.
   * This uses JavaScript to check the document.readyState property.
   * @return ExpectedCondition that returns true when DOM is ready
   */
  private static ExpectedCondition<Boolean> domContentLoaded() {
    return driver -> {
      try {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Check document.readyState
        boolean isDocumentReady = "complete".equals(js.executeScript("return document.readyState"));
        // Check if jQuery is active (if jQuery is used in the page)
        boolean isJQueryReady = (boolean) js.executeScript(
          "return (typeof jQuery === 'undefined' || jQuery.active === 0);");
        // Check if there are any active AJAX requests
        boolean isAjaxReady = (boolean) js.executeScript(
          "return (typeof window.Ajax === 'undefined' || window.Ajax.activeRequestCount === 0);");

        return isDocumentReady && isJQueryReady && isAjaxReady;
      } catch (Exception e) {
        return false;
      }
    };
  }



}
