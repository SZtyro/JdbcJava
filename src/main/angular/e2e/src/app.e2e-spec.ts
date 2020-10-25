import { AppPage } from './app.po';
import { browser, logging, by, element } from 'protractor';

describe('workspace-project App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('tytul strony', () => {
    page.navigateTo();
    expect(browser.getTitle()).toEqual('AngularJDBC');
  });

  it('polaczenie z googlem', () => {
    page.navigateTo();
    element(by.partialButtonText("Sign in with Google!")).click();
    browser.getAllWindowHandles().then((handles) => {
      if(expect(handles.length > 1).toBe(true)) {
        browser.driver.switchTo().window(handles[1]);
        browser.driver.close();
        browser.driver.switchTo().window(handles[0]);
      }
    });
  });

  it('okno widgetow', () => {
    page.navigateTo();
    element(by.css('[ng-reflect-message="Widgets"]')).click();
    expect(element(by.css('[class="container-fluid"]')).isPresent()).toBe(true);
  });

  it('jezyk polski', () => {
    page.navigateTo();
    element(by.partialButtonText("EN")).click();
    expect(element(by.buttonText("PL")).isPresent()).toBe(true);
  });

  afterEach(async () => {
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(jasmine.objectContaining({
      level: logging.Level.SEVERE,
    } as logging.Entry));
  });

});
