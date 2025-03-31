
package UserManagement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UpdateUser {
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
        WebElement userNameInput = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/div[2]//input")));
        userNameInput.sendKeys(userName);

        // Chờ ô nhập password xuất hiện và nhập thông tin
        WebElement passwordInput = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/div[3]//input")));
        passwordInput.sendKeys(password);

        // Click nút login
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form/div[4]//button")));
        loginButton.click();

        // Chờ trang chuyển hướng sau khi đăng nhập thành công (hoặc phần tử xác định)
        wait.until(ExpectedConditions.urlContains("device-map"));
    }

    @Test
    public void updateUser() {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Chờ User Tab được thêm vào DOM & hiển thị
        WebElement userTab = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[3]/span[2]/span")));
        userTab.click();
        // Chờ nút Chi tiết hiển thị và click vào nó
        WebElement detailButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "/html/body/div[1]/div/div/main/div/div[2]/div/div/div/div/div/div/table/tbody/tr[2]/td[7]/div/div[1]/button")));
        detailButton.click();

        // Chờ nút Cập nhật hiển thị và click vào nó
        WebElement updateButton = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("/html/body/div[2]/div/div[3]/div/div[2]/div/form/div[2]/button[1]")));
        updateButton.click();

        // Nhập Tên người dùng
        WebElement userNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("/html/body/div[3]/div/div[2]/div/div[1]/div/div[2]/form/div[1]/div/div[2]/div/div/input[1]")));
        userNameInput.sendKeys(Keys.CONTROL + "a");
        userNameInput.sendKeys(Keys.DELETE);
        userNameInput.sendKeys("Nguyễn Văn A");

        // Nhập Tên đăng nhập
        WebElement loginNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "/html/body/div[3]/div/div[2]/div/div[1]/div/div[2]/form/div[2]/div[1]/div/div[2]/div/div/input")));
        loginNameInput.sendKeys(Keys.CONTROL + "a");
        loginNameInput.sendKeys(Keys.DELETE);
        loginNameInput.sendKeys("nguyenvana");

        // Click nút Lưu
        WebElement saveButton = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("/html/body/div[3]/div/div[2]/div/div[1]/div/div[2]/form/button")));
        saveButton.click();
        // Kiểm tra box thông báo cập nhật thành công
        try {
            WebElement successBox = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/section/div/div")));
            System.out.println("Cập nhật thành công: " + successBox.getText());
        } catch (TimeoutException e) {
            System.out.println("Cập nhật không thành công.");
        }
    }

    @Test
    public void updatePassword() {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Chờ User Tab được thêm vào DOM & hiển thị
        WebElement userTab = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[3]/span[2]/span")));
        userTab.click();
        // Chờ nút Chi tiết hiển thị và click vào nó
        WebElement detailButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "/html/body/div[1]/div/div/main/div/div[2]/div/div/div/div/div/div/table/tbody/tr[2]/td[7]/div/div[1]/button")));
        detailButton.click();
        WebElement updatePasswordButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//form/div[2]/button[3]")));
        updatePasswordButton.click();
        WebElement PasswordIdInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/div[1]/div/div[2]/div/div/span/input")));

        PasswordIdInput.sendKeys("nguyenvana");
        WebElement RePasswordIdInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/div[2]/div/div[2]/div/div/span/input")));

        RePasswordIdInput.sendKeys("nguyenvana");
        WebElement saveNewPasswordButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//form/button")));
        saveNewPasswordButton.click();
        try {
            WebElement successBox = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/section/div/div")));
            System.out.println("Cập nhật thành công: " + successBox.getText());
        } catch (TimeoutException e) {
            System.out.println("Cập nhật không thành công.");
        }
    }

    @Test
    public void ChangeRole() {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Chờ User Tab được thêm vào DOM & hiển thị
        WebElement userTab = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[3]/span[2]/span")));
        userTab.click();
        // Chờ nút Chi tiết hiển thị và click vào nó
        WebElement detailButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "/html/body/div[1]/div/div/main/div/div[2]/div/div/div/div/div/div/table/tbody/tr[2]/td[7]/div/div[1]/button")));
        detailButton.click();
        WebElement NewroleButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//form/div[2]/button[2]")));
        NewroleButton.click();
        WebElement Newrole = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//form/div/div[2]/div/div[2]")));
        Newrole.click();
        WebElement Pickrole = wait
                .until(ExpectedConditions.elementToBeClickable(By.className("ant-select-item-option-content")));
        Pickrole.click();
        WebElement saveNewRoleButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form/button")));
        saveNewRoleButton.click();
        try {
            WebElement successBox = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/section/div/div")));
            System.out.println("Đổi quyền thành công: " + successBox.getText());
        } catch (TimeoutException e) {
            System.out.println("Đổi quyền` không thành công.");
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
