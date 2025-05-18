package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private SelenideElement header = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private String button = "[data-test-id=action-deposit]";
    private String balanceStart = "баланс:";
    private String balanceFinish = " р.";

    public DashboardPage() {
        header.shouldBe(visible);
    }

    public int extractBalance(String text) {

        val start = text.indexOf(balanceStart);
        val finish = text.lastIndexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage pressReplCard(int index) {
        SelenideElement buttonReplCard = cards.get(index).find(button);
        buttonReplCard.click();
        return new TransferPage();
    }
}