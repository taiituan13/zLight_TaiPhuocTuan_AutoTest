package UserManagement;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DeleteUser {
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
    public void deleteUser() {

        String userNameToDelete = "Nguyen Van A";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        login();
        // Chờ User Tab xuất hiện và nhấp vào
        WebElement userTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[3]/span[2]/span")));
        userTab.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Chờ tbody xuất hiện
        WebElement tableBody = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody[@class='ant-table-tbody']")));

        // Lấy tất cả các hàng thực tế trong bảng (bỏ qua hàng `measure-row`)
        java.util.List<WebElement> rows = tableBody
                .findElements(By.xpath(".//tr[contains(@class, 'ant-table-row-level-0')]"));

        boolean userFound = false;

        for (WebElement row : rows) {
            java.util.List<WebElement> columns = row.findElements(By.xpath(".//td"));
            if (columns.size() >= 2) { // Đảm bảo có đủ cột
                String usernameInTable = columns.get(1).getText().trim();
                System.out.println("Tìm thấy user: " + usernameInTable);

                if (usernameInTable.equals(userNameToDelete)) {
                    userFound = true;

                    // Cuộn tới hàng nếu cần
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", row);

                    // Tìm nút xóa trong hàng (giả sử nằm ở cột cuối cùng)
                    WebElement deleteButton = row
                            .findElement(By.xpath(".//td[last()]//div[@class='ant-space-item'][2]//button"));
                    deleteButton.click();

                    // Chờ hộp thoại xác nhận và nhấn "Yes"
                    WebElement confirmButton = wait
                            .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[span[text()='Có']]")));
                    confirmButton.click();

                    // Kiểm tra nếu có thông báo thành công
                    WebElement toastMessage = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(By.className("Toastify__toast--success")));
                    String toastText = toastMessage.getText();

                    System.out.println("Người dùng '" + userNameToDelete + "' đã bị xóa, " + toastText);
                    break;
                }
            }
        }

        if (!userFound) {
            System.out.println("Không tìm thấy người dùng '" + userNameToDelete + "' trong danh sách.");
        }
    }
}
