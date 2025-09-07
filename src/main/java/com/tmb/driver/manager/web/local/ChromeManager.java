package com.tmb.driver.manager.web.local;

//import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class ChromeManager {

  private ChromeManager() {
    System.setProperty("wdm.debug", "true");
  }

  public static WebDriver getDriver() {
//    WebDriverManager.chromedriver().browserVersion("140.0.7339.41")
//      .avoidBrowserDetection()
//      .avoidOutputTree()
//      .ttl(86400)
//      .setup();

    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
    ChromeOptions options = new ChromeOptions();
    return new ChromeDriver(options);
  }
}
