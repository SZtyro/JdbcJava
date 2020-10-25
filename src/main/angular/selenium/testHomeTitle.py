import unittest
from selenium import webdriver
import page

class HomeTitle(unittest.TestCase):
    """A sample test class to show how page object works"""

    def setUp(self):
        self.driver = webdriver.Chrome(executable_path='chromedriver.exe')
        self.driver.get("http://localhost:4200/")

    def test_title_in_index(self):

        #Load the main page. In this case the home page.
        main_page = page.MainPage(self.driver)
        #Checks if the word is in title
        assert main_page.is_title_matches("AngularJDBC")

    def tearDown(self):
        self.driver.close()

if __name__ == "__main__":
    unittest.main()
