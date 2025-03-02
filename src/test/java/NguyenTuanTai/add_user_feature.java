package NguyenTuanTai;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import org.openqa.selenium.By;

import io.github.bonigarcia.wdm.WebDriverManager;

public class add_user_feature {
    public String url = "https://zlight.stlsolution.com/";
    public WebDriver driver;
    
    public String userName = "dev0";
    public String password = "dev@123";
    
    @BeforeTest 
    public void launchBrowser() {
        System.out.println("Chạy trình duyệt Chrome...");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
    }
    
    @Test
    public void login() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Chờ ô nhập username xuất hiện và nhập thông tin
        WebElement userNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/div[2]//input")));
        userNameInput.sendKeys(userName);

        // Chờ ô nhập password xuất hiện và nhập thông tin
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/div[3]//input")));
        passwordInput.sendKeys(password);

        // Click nút login
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form/div[4]//button")));
        loginButton.click();

        // Chờ trang chuyển hướng sau khi đăng nhập thành công (hoặc phần tử xác định)
        wait.until(ExpectedConditions.urlContains("device-map"));
    }

    @Test(dependsOnMethods = "login")
    public void addUser() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Chờ User Tab được thêm vào DOM & hiển thị
        WebElement userTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[3]/span[2]/span")));
        userTab.click();

        // Chờ nút "Add User" xuất hiện rồi click
        WebElement addUserButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//main//button")));
        addUserButton.click();
    }
}
