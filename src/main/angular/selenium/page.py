from element import BasePageElement
from locators import MainPageLocators

class SearchTextElement(BasePageElement):
    """This class gets the search text from the specified locator"""

    #The locator for search box where search string is entered

class BasePage(object):
    """Base class to initialize the base page that will be called from all pages"""

    def __init__(self, driver):
        self.driver = driver


class MainPage(BasePage):
    """Home page action methods come here."""

    def is_title_matches(self,title):
        """Verifies that the hardcoded text appears in page title"""
        return title in self.driver.title

    def click_button(self):
        """Triggers the search"""
        element = self.driver.find_element_by_css_selector('button.ng-star-inserted')
        element.click()


class GooglePage(BasePage):
    """Google page action methods come here."""

    def is_title_matches_google(self, title):
      """Verifies that the hardcoded text appears in page title"""
      return title in self.driver.title

    def click_next(self):
      """Triggers the search"""
      element = self.driver.find_element_by_id('identifierNext')
      element.click()

    def click_next2(self):
      """Triggers the search"""
      element = self.driver.find_element_by_id('passwordNext')
      element.click()

