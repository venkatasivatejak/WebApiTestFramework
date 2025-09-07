package com.tmb.driver;


import com.tmb.driver.entity.WebDriverData;
import com.tmb.driver.factory.DriverFactory;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.Objects;

import static com.tmb.config.factory.ConfigFactory.getConfig;

public final class Driver {

  private Driver() {
  }

  public static void initDriverForWeb() {
    if (Objects.isNull(DriverManager.getDriver())) {
      WebDriverData driverData = new WebDriverData(getConfig().browser(), getConfig().browserRemoteMode());
      WebDriver driver = DriverFactory
        .getDriverForWeb(getConfig().browserRunMode())
        .getDriver(driverData);
      setupDriverTimeouts(driver);
      DriverManager.setDriver(driver);
      loadURL();
    }
  }

  public static void loadURL() {
    DriverManager.getDriver().get(getConfig().webUrl());
  }

  public static void quitDriver() {
    if (Objects.nonNull(DriverManager.getDriver())) {
      DriverManager.getDriver()
        .quit();
      DriverManager.unload();
    }
  }

  /**
   * Sets up all the standard timeouts for the WebDriver instance
   *
   * @param driver WebDriver instance to configure
   */
  private static void setupDriverTimeouts(WebDriver driver) {
    // Set implicit wait timeout
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

//    // Set page load timeout
//    driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT);
//
//    // Set script timeout
//    driver.manage().timeouts().scriptTimeout(SCRIPT_TIMEOUT);
  }

}
