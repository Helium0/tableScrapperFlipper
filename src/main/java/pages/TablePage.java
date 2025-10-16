package pages;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TablePage {

    private WebDriver driver;

    @FindBy(xpath = "//div[@id='table']//*[contains(@class,'floating-header')]//th[contains(@style,'31px')]")
    private List<WebElement> tableColumnsElements;

    @FindBy(xpath = "//div[@id='table']//*[contains(@class,'data-table')]/tbody/*[not(contains(@style,'display'))]")
    private List<WebElement> tableRowsElements;

    @FindBy(xpath = "//table[contains(@class,'floating-header')]//th[contains(@style,'31px')]/a | //table[contains(@class,'floating-header')]//th[contains(@style,'31px')]/span")
    private List<WebElement> headerTitles;


    public TablePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    private void writeHeaderTitlesInToExcelFile(Row headerRow) {
        String [] words = new String[headerTitles.size()];
        for(int i = 0; i < words.length; i++) {
            headerRow.createCell(i).setCellValue(headerTitles.get(i).getText());
        }
    }

    public void writeScrappedTableInToExcelFile(Sheet sheet) {
        int columns = tableColumnsElements.size();
        int rows = tableRowsElements.size();
        Row headerRow = sheet.createRow(0);
        writeHeaderTitlesInToExcelFile(headerRow);
        for(int r = 1; r <= rows; r++) {
            Row dataRow = sheet.createRow(r);
            for (int c = 1; c <= columns; c++) {
                String scrappedText = driver.findElement(By.xpath("//div[@id='table']//*[contains(@class,'data-table')]/tbody/*[not(contains(@style,'display'))]"
                                +"["+r+"]"+"/td"+"["+c+"]"))
                        .getText();
                dataRow.createCell(c-1).setCellValue(scrappedText);
            }
        }
    }
}
