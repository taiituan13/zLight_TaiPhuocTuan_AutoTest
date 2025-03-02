package scheme;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.By;
import io.github.bonigarcia.wdm.WebDriverManager;

public class addScheme {
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
        WebElement userNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/div[2]//input")));
        userNameInput.sendKeys(userName);

        // Chờ ô nhập password xuất hiện và nhập thông tin
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/div[3]//input")));
        passwordInput.sendKeys(password);

        // Click nút login
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form/div[4]//button")));
        loginButton.click();

        // Chờ trang chuyển hướng sau khi đăng nhập thành công
        wait.until(ExpectedConditions.urlContains("device-map"));
    }

    @Test(dependsOnMethods = "login")
    public void Scheme() throws InterruptedException {
        String schemeName = "kehoach1";
        String filePath = "C:\\\\Users\\\\84949\\\\Downloads\\\\KiemTra.docx";  // Đổi thành đường dẫn file thực tế
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Click vào menu Scheme
        WebElement schemeMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div/header/div[1]/div/ul/li[4]/div")));
        schemeMenu.click();

        // Click vào Scheme Management
        WebElement schemeManagement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/ul/li[1]/span[2]/span")));
        schemeManagement.click();

        // Click vào nút "Add Scheme"
        WebElement addScheme = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div/main/div/div[1]/div[1]/button")));
        addScheme.click();

        // Nhập tên kế hoạch (Scheme Name)
        WebElement schemeNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/div[1]/div/div[2]/div/div/span/input")));
        schemeNameInput.sendKeys(schemeName);

        // Tải file lên
        WebElement fileUpload = driver.findElement(By.id("mainFilePath"));
        fileUpload.sendKeys(filePath);

        // Cuộn xuống để hiển thị nút "Create Scheme"
     // Chờ nút "Tạo kế hoạch" xuất hiện trong DOM
        WebElement createScheme = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form/button")));

        // Cuộn xuống để đảm bảo phần tử hiển thị
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", createScheme);
        Thread.sleep(3000); // Đợi một chút để cuộn xong

        // Chờ đến khi có thể click
        wait.until(ExpectedConditions.visibilityOf(createScheme));
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createScheme);

//        createScheme.click();
        
        System.out.println("Scheme added successfully!");
    }
}
