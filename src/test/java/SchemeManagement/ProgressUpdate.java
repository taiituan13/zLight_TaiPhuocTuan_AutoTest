package SchemeManagement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
// import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
// import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
// import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.By;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ProgressUpdate {
    public String url = "https://zlight.stlsolution.com/";
    public WebDriver driver;

    public String userName = "User301";
    public String password = "user@123";

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
        WebElement userNameInput = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/div[2]//input")));
        userNameInput.sendKeys(userName);

        // Chờ ô nhập password xuất hiện và nhập thông tin
        WebElement passwordInput = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/div[3]//input")));
        passwordInput.sendKeys(password);

        // Click nút login
        WebElement loginButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//form/div[4]//button")));
        loginButton.click();

        // Chờ trang chuyển hướng sau khi đăng nhập thành công
        wait.until(ExpectedConditions.urlContains("device-map"));
    }

    @Test
    public void updateProgress() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Chờ ô nhập username xuất hiện và nhập thông tin
        WebElement userNameInput = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/div[2]//input")));
        userNameInput.sendKeys(userName);

        // Chờ ô nhập password xuất hiện và nhập thông tin
        WebElement passwordInput = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/div[3]//input")));
        passwordInput.sendKeys(password);

        // Click nút login
        WebElement loginButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//form/div[4]//button")));
        loginButton.click();

        // Chờ trang chuyển hướng sau khi đăng nhập thành công
        wait.until(ExpectedConditions.urlContains("device-map"));
        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Chuyển qua tab "Kế hoạch được giao"
        WebElement assignedPlanTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[1]/div/div/header/div[1]/div/ul/li[3]")));
        assignedPlanTab.click();

        // Nhấn vào chi tiết kế hoạch được giao
        WebElement assignedPlanDetail = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(
                        "/html/body/div[1]/div/div/main/div/div[2]/div[2]/div/div/div/div/div/div/div/div/table/tbody/tr[2]/td[7]/div/div/span")));
        assignedPlanDetail.click();

        // Nhấn vào nút cập nhật
        WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(
                        "//button[contains(@class, 'ant-btn-primary') and contains(@class, 'ant-btn-color-primary') and span[text()='Cập nhật']]")));
        updateButton.click();

        // Xóa và điền phần trăm tiến độ mới
        WebElement progressInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//form/div[1]/div[2]/div/div[2]/div[1]/div/div/div/div[2]/input")));
        progressInput.sendKeys(Keys.CONTROL + "a");
        progressInput.sendKeys(Keys.BACK_SPACE);

        progressInput.sendKeys("80"); // Cập nhật tiến độ thành 80%

        // Nhấn nút cập nhật
        WebElement confirmUpdateButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//form/button")));
        confirmUpdateButton.click();

        System.out.println("Cập nhật tiến độ thành công!");
        // Chờ toast message xuất hiện
        WebElement toastMessage = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("Toastify__toast")));

        // Lấy nội dung của toast message
        String messageText = toastMessage.getText();
        System.out.println("Toast message: " + messageText);

    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
