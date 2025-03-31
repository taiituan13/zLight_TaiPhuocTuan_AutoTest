package ActivityManagement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
// import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
// import org.openqa.selenium.JavascriptExecutor;
// import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.By;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SearchActivity {
        public String url = "https://zlight.stlsolution.com/";
        public WebDriver driver;

        public String userName = "paul3004";
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

        public void searchActivity() {
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

                // Click vào tab Lịch sử
                WebElement historyTab = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("/html/body/div[1]/div/div/header/div[1]/div/ul/li[5]")));
                historyTab.click();

                // Nhập tên cần tìm kiếm "paul"
                WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                                "/html/body/div[1]/div/div/main/div/div[1]/div/div[2]/span/span/span[1]/input")));
                searchInput.sendKeys("paul");

                // Đợi kết quả tải xong
                try {
                        Thread.sleep(2000); // Đợi 2 giây để dữ liệu được tải
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }

                // Lấy nội dung cột thứ 3 của mỗi dòng và so sánh với từ khóa tìm kiếm
                java.util.List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
                // boolean found = false;
                for (WebElement row : rows) {
                        WebElement thirdColumn = row.findElement(By.xpath("td[3]"));
                        String columnText = thirdColumn.getText();
                        System.out.println("Nội dung cột 3: " + columnText);
                        if (columnText.contains("paul")) {
                                System.out.println("Tìm kiếm thành công: " + columnText);
                        }
                }

                // Assert.assertTrue(found, "Không tìm thấy kết quả phù hợp với từ khóa tìm kiếm");
        }

        @AfterTest
        public void tearDown() {
                if (driver != null) {
                        driver.quit();
                }
        }

}
