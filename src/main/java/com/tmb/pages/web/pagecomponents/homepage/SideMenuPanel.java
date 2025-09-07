package com.tmb.pages.web.pagecomponents.homepage;

import com.tmb.pages.web.topmenucomponent.MenuType;
import com.tmb.pages.web.topmenucomponent.SubMenuType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.tmb.utils.PageActionsHelper.getAttribute;
import static com.tmb.utils.PageActionsHelper.isPresent;
import static com.tmb.utils.PageActionsHelper.waitAndClick;

public class SideMenuPanel {

  private static final String MENU = "//a[contains(.,'%s')]";
  private static final String SUB_MENU = "//span[@class='oxd-topbar-body-nav-tab-item' and contains(text(),'%s')]";
  private static final String DROPDOWN_SUB_MENU = "//a[@role='menuitem' and text()='%s']";
  private static final By LOGO = By.xpath("//img[@alt='client brand banner']");
  private static final By LINK_MARKET_PLACE = By.className("oxd-brand");

  public SideMenuPanel clickMenuItem(MenuType menuType) {
    String xpath = String.format(MENU, menuType.getName());
    waitAndClick(By.xpath(xpath));
    return this;
  }

  public SideMenuPanel clickSubMenuItem(SubMenuType subMenuType) {
    String xpath = String.format(SUB_MENU, subMenuType.getName());
    waitAndClick(By.xpath(xpath));
    return this;
  }

  public SideMenuPanel clickDropdownSubMenuItem(SubMenuType subMenuType) {
    String xpath = String.format(DROPDOWN_SUB_MENU, subMenuType.getName());
    waitAndClick(By.xpath(xpath));
    return this;
  }

  public String getLogoSourceString() {
    return getAttribute(LOGO, e -> e.getAttribute("src"));
  }

  public boolean isMarketPlaceLinkPresent() {
    return isPresent(LINK_MARKET_PLACE, WebElement::isDisplayed);
  }
}
