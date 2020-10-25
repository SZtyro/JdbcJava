import unittest
from selenium import webdriver
from selenium.webdriver.support import expected_conditions as EC

from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.wait import WebDriverWait

import page

class GoogleLogin(unittest.TestCase):
    """A sample test class to show how page object works"""

    def setUp(self):
        self.driver = webdriver.Chrome(executable_path='chromedriver.exe')
        self.driver.get("http://localhost:4200/")
        self.driver.get("http://localhost:4200/")

    def test_login_in_google_window(self):
        self.driver.implicitly_wait(15)
        winHandleBefore = self.driver.current_window_handle
        main = page.MainPage(self.driver)
        main.click_button()
        main.click_button()
        wait = WebDriverWait(self.driver, 10)
        wait.until(EC.number_of_windows_to_be(2))

        for handle in self.driver.window_handles:
            self.driver.switch_to.window(handle)
            google = page.GooglePage(self.driver)
        login = self.driver.find_element_by_id("identifierId")
        login.send_keys("nwta1234nwta@gmail.com")
        google.click_next()
        password = self.driver.find_element_by_name("password")
        password.send_keys("Nwta1234!")
        google.click_next2()

        wait = WebDriverWait(self.driver, 10)
        wait.until(EC.number_of_windows_to_be(1))

        self.driver.switch_to.window(winHandleBefore)
        main.click_button()

        assert "Log out" not in self.driver.page_source

    def tearDown(self):
        self.driver.close()

if __name__ == "__main__":
    unittest.main()
