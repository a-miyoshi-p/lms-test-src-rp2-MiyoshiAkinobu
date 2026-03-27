package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.netty.handler.timeout.TimeoutException;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
		//待ち処理
		visibilityTimeout(By.xpath("//*"), 3);
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
			//タイムアウトの場合
		}

		//遷移先タイトル一致確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		//エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {
		//登録確認のため常時表示の時刻を取得
		String testTime = webDriver.findElement(By.id("now")).getText();
		//勤怠情報変更画面で出勤ボタン押下
		webDriver.findElement(By.xpath("//input[@name='punchIn']")).click();
		//ダイアログ表示されOKを押下
		webDriver.switchTo().alert().accept();
		//自画面遷移のためタイトル一致再確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		//「勤怠情報の登録が完了しました」表示確認
		assertTrue(webDriver.findElement(By.xpath("//span[text()='勤怠情報の登録が完了しました。']")).isDisplayed());
		//テスト実施日の出勤列に打刻時間が表示されていること確認
		//結果と比較(処理時間によって分がまたがった際、エラーの可能性あり)
		assertEquals(testTime,
				"現在の時刻は　"
						+ webDriver.findElement(By.xpath("//*[@id='main']/div[3]/div/table/tbody/tr[2]/td[3]"))
								.getText()
						+ "　です。");
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {
		//登録確認のため常時表示の時刻を取得
		String testTime = webDriver.findElement(By.id("now")).getText();
		//勤怠情報変更画面で出勤ボタン押下
		webDriver.findElement(By.xpath("//input[@name='punchOut']")).click();
		//ダイアログ表示されOKを押下
		webDriver.switchTo().alert().accept();
		//自画面遷移のためタイトル一致再確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		//「勤怠情報の登録が完了しました」表示確認
		assertTrue(webDriver.findElement(By.xpath("//span[text()='勤怠情報の登録が完了しました。']")).isDisplayed());
		//テスト実施日の出勤列に打刻時間が表示されていること確認
		//結果と比較(処理時間によって分がまたがった際、エラーの可能性あり)
		assertEquals(testTime,
				"現在の時刻は　"
						+ webDriver.findElement(By.xpath("//*[@id='main']/div[3]/div/table/tbody/tr[2]/td[4]"))
								.getText()
						+ "　です。");
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
