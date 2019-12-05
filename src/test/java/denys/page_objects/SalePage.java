package denys.page_objects;

import denys.elements.Button;
import denys.elements.DropDownList;
import denys.helpers.StringProcessor;
import io.qameta.allure.Step;
import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import java.util.List;

import static denys.DriverManager.getDriver;

public class SalePage extends AbstractPage {

    private By gridViewBtn = By.xpath("//strong[@title='Grid']");
    private By showSelectionLst = By.xpath("(//select[@title='Results per page'])[1]");
    private By itemAllPrices = By.xpath("//div[@class='price-box']");
    private By itemOldPrice = By.xpath("//div[@class='price-box']/p[@class='old-price']");
    private By itemSalePrice = By.xpath("//div[@class='price-box']/p[@class='special-price']");

    @Getter
    private Button GridView = new Button(gridViewBtn, "Grid view icon");

    @Getter
    private DropDownList ShowSelectionList = new DropDownList(showSelectionLst, "SHOW list");


    @Step
    public SalePage clickgridView() {
        GridView.click();
        return this;
    }

    @Step
    public SalePage clickShowDropDown(String value) {
        ShowSelectionList.select(value);
        return this;
    }

    //Generates list of pairs (Old and Sale prices) for each element. For each pair compares Old and Sale values
    public void comparePrices() {
        List<WebElement> weList = getDriver().findElements(itemAllPrices);
        for (WebElement we : weList) {
            double oldPrice = StringProcessor.stringToDouble(we.findElement(itemOldPrice).getText());
            double salePrice = StringProcessor.stringToDouble(we.findElement(itemSalePrice).getText());
            Assert.assertTrue(String.format("Expected old price %s > than sale price %s", oldPrice, salePrice),oldPrice > salePrice);
        }


    }
}
