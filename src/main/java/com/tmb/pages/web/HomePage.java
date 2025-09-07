package com.tmb.pages.web;

import com.tmb.pages.web.pagecomponents.homepage.SideMenuPanel;
import com.tmb.pages.web.topmenucomponent.MenuType;
import com.tmb.pages.web.topmenucomponent.SubMenuType;
import com.tmb.pages.web.validator.HomePageValidator;
import com.tmb.utils.PageActionsHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomePage {

  private final SideMenuPanel sideMenuPanel;
  private static final By HEADER = By.xpath("//h6");

  public HomePage() {
    this.sideMenuPanel = new SideMenuPanel();
  }

  public SystemUserPage navigateToSystemUsersPage() {
    sideMenuPanel.clickMenuItem(MenuType.ADMIN)
      .clickSubMenuItem(SubMenuType.USER_MANAGEMENT)
      .clickDropdownSubMenuItem(SubMenuType.USERS);
    return new SystemUserPage();
  }

  public HomePageValidator getHomePageValidator() {
    return HomePageValidator.builder()
      .isMarketplaceLinkPresent(sideMenuPanel.isMarketPlaceLinkPresent())
      .logoSourceText(sideMenuPanel.getLogoSourceString())
      .headerName(PageActionsHelper.getAttribute(HEADER, WebElement::getText))
      .build();
  }
}
