package page;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final String balanceStart = "баланс: "; // Оригинальная строка с опечаткой
    private final String balanceFinish = " p.";
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list__item div");
    private final SelenideElement reloadButton = $("[data-test-id='action-reload']");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        var text = getCard(cardInfo).getText();
        return extractBalance(text);
    }

    public void checkCardBalance(DataHelper.CardInfo cardInfo, int expectedBalance) {
        int actualBalance = getCardBalance(cardInfo);
        if (actualBalance != expectedBalance) {
            throw new AssertionError("Баланс карты " + cardInfo.getCardNumber() +
                    " должен быть " + expectedBalance +
                    ", но был " + actualBalance);
        }
    }

    // public int getCardBalance(int index) {
    // var text = cards.get(index).getText();
    // return extractBalance(text);
    //

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {
        getCard(cardInfo).$("button").click();
        return new TransferPage();
    }

    private SelenideElement getCard(DataHelper.CardInfo cardInfo) {
        return cards.findBy(Condition.attribute("data-test-id", cardInfo.getTestId()));
    }

    public void reloadDashboardPage() {
        reloadButton.click();
        heading.shouldBe(visible);
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}