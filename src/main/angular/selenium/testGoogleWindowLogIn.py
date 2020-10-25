import unittest
from selenium import webdriver
from selenium.webdriver.support import expected_conditions as EC

from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.wait import WebDriverWait

import page

class GoogleWindow(unittest.TestCase):
    """A sample test class to show how page object works"""

    def setUp(self):
        self.driver = webdriver.Chrome(executable_path='chromedriver.exe')
        self.driver.get("http://localhost:4200/")
        self.driver.get("http://localhost:4200/")

    def test_title_in_google_window(self):
        self.driver.implicitly_wait(15)

        main = page.MainPage(self.driver)
        main.click_button()
        main.click_button()
        wait = WebDriverWait(self.driver, 10)
        wait.until(EC.number_of_windows_to_be(2))

        for handle in self.driver.window_handles:
            self.driver.switch_to.window(handle)
            google = page.GooglePage(self.driver)
            googleTitle = self.driver.title
            assert google.is_title_matches_google(googleTitle)

        #assert main.is_title_matches(googleTitle)
        #self.assertTrue('Logowanie – Kontax Google', googleTitle)

    def tearDown(self):
        self.driver.close()

if __name__ == "__main__":
    unittest.main()
