package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.netty.handler.timeout.TimeoutException;

/**
 * 結合テスト 勤怠管理機能
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		//トップページにアクセス
		goTo("http://localhost:8080/lms");
		//タイトル一致確認
		assertEquals("ログイン | LMS", webDriver.getTitle());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		//初回ログイン済の受講生ユーザでログイン
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA02");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA02");
		webDriver.findElement(By.className("btn")).click();
		//遷移後、コース詳細画面のタイトル一致確認
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		//上部メニュー「勤怠」リンククリック
		webDriver.findElement(By.linkText("勤怠")).click();
		//過去日未入力状態の場合、ダイアログ表示されるので分岐処理
		//try~catch,明示待機処理
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
		try {
			//アラート待機　表示あり
			wait.until(ExpectedConditions.alertIsPresent());
			//ダイアログ表示されOKを押下
			webDriver.switchTo().alert().accept();
		} catch (TimeoutException e) {
			//タイムアウトの場合継続
		}

		//遷移先タイトル一致確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		//「勤怠情報を直接編集する」リンクをクリック
		webDriver.findElement(By.linkText("勤怠情報を直接編集する")).click();

		//遷移先確認（Test03と同タイトル）
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		//定時ボタンの存在で確認
		assertTrue(webDriver.findElement(By.xpath("//button[text()='定時']")).isDisplayed());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {
		//全ての日程の勤怠情報を設定
		List<WebElement> fixedTimeList = webDriver.findElements(By.xpath("//button[text()='定時']"));
		for (WebElement fixedTime : fixedTimeList) {
			fixedTime.click();
		}
		//要素表示のためスクロール
		scrollTo("300");
		//更新ボタンをクリック
		webDriver.findElement(By.xpath("//input[@value='更新']")).click();
		//ダイアログ表示されOKを押下
		webDriver.switchTo().alert().accept();

		//各セクションの出勤/退勤時間を確認　期待値：09:00/18:00
		//infoの列情報を繰り返し取得　出勤：td[3]　退勤：td[4]を定時と比較
		List<WebElement> punchInList = webDriver.findElements(By.xpath("//tbody/tr/td[3]"));
		List<WebElement> punchOutList = webDriver.findElements(By.xpath("//tbody/tr/td[4]"));

		for (WebElement punchInElement : punchInList) {
			assertEquals("09:00", punchInElement.getText());
		}
		for (WebElement punchOutElement : punchOutList) {
			assertEquals("18:00", punchOutElement.getText());
		}
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
