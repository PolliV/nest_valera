import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Tests {
    private static Playwright play2;
    private static Browser br2;
    private static Page page2;

    @BeforeEach
    public void before(){
        play2 = Playwright.create();
        br2 = play2.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page2 = br2.newPage();
        page2.navigate("https://uvaleronchika.ru/");
    }

    @Test
    @DisplayName("Авторизация")
    public void test_01() {

        /* assertThat(page2.getByTitle("Войти")).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(500));
        assertThat(page2.getByTitle("Зарегистрироваться")).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(500));
        assertThat(page2.getByTitle("На главную")).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(500));
        */

        assertThat(page2.locator("xpath=//html/body/div[1]/div[1]/div/ul/li[6]/a")).isVisible(); //вход
        assertThat(page2.locator("xpath=//html/body/div[1]/div[1]/div/ul/li[7]/a")).isVisible(); //регистрация
        assertThat(page2.locator("xpath=//html/body/div[1]/div[3]/div/div[1]/a")).isVisible(); //главная


        //локатор входа и клик
        Locator l3_vh = page2.locator("xpath=//html/body/div[1]/div[1]/div/ul/li[6]/a");
        l3_vh.click();

        //ввод email и пароля
        page2.getByPlaceholder("Email").fill("vershinina_polli@mail.ru");
        page2.locator("xpath=//html/body/div[1]/div[8]/div/div/div/div/div[2]/div/div[2]/div[2]/div[2]/input").fill("qwerty");

        //локатор войти и клик
        Locator l4_vh = page2.locator("xpath=//html/body/div[1]/div[8]/div/div/div/div/div[2]/div/div[3]/div[2]/input[2]");
        l4_vh.click();

        //видимость личного кабинета
        assertThat(page2.locator("xpath=//html/body/div[1]/div[1]/div/ul/li[6]/a")).isVisible();
    }

    @Test
    @DisplayName("Нахождение товара")
    public void test_02() {
        //System.out.println(page2.title());
        //page2.getByPlaceholder("Найти товары").fill("Фетр");

        //видимость строки поиска и ввод значения
        assertThat(page2.getByRole(AriaRole.SEARCHBOX, new Page.GetByRoleOptions().setName("Найти товары"))).isVisible();
        page2.getByRole(AriaRole.SEARCHBOX, new Page.GetByRoleOptions().setName("Найти товары")).fill("Фетр");

        /*page2.getByRole(AriaRole.SEARCHBOX, new Page.GetByRoleOptions().setName("Найти товары")).pressSequentially("Фетр", new Locator.PressSequentiallyOptions().setDelay(100));*/

        //нажатие enter
        page2.getByRole(AriaRole.SEARCHBOX, new Page.GetByRoleOptions().setName("Найти товары")).press("Enter");

        //Locator l2 =page2.locator(".search-button");

        //проверка наличия товара из фетра
        assertThat(page2.getByTitle("Комплект деталей из мягкого корейского фетра &quot;Ёлка пряничная&quot;")).isVisible();
    }
}
