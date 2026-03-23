package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//上部メニュー「機能」をクリック
		webDriver.findElement(By.className("dropdown")).click();
		//プルダウンリストから「ヘルプ」をクリック
		webDriver.findElement(By.linkText("ヘルプ")).click();
		//遷移後、ヘルプ画面のタイトル一致確認
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//「よくある質問」をクリック
		webDriver.findElement(By.linkText("よくある質問")).click();

		//タブの切り替え
		Object[] windowHandle = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandle[1]);
		//開かれた別タブでタイトル一致確認
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		//キーワード検索
		webDriver.findElement(By.name("keyword")).sendKeys("キャンセル");
		webDriver.findElement(By.xpath("//input[@value='検索']")).click();

		//検索結果の確認 入力値:キーワード「キャンセル」、期待値:1件
		//検索結果のリスト表示確認＞0件出ないことを確認
		assertTrue(webDriver.findElement(By.id("question-h[${status.index}]")).isDisplayed());
		//このケースでは1件であること確認
		assertEquals(1, webDriver.findElements(By.id("question-h[${status.index}]")).size());
		//検索結果にキーワード「キャンセル」が含まれているか確認
		//※検索結果一つ目に、入力したキーワードが含まれているか部分一致確認
		assertTrue(webDriver.findElement(By.xpath("//*[@id=\"question-h[${status.index}]\"]/dt/span[2]")).getText()
				.contains("キャンセル"));
		//エビデンス取得のためスクロール ※キーワード欄と検索結果が収まる200pxを設定
		scrollTo("200");
		//エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		//キーワード検索のクリアボタンをクリック
		webDriver.findElement(By.xpath("//input[@value='クリア']")).click();
		//テキストボックスが空か確認
		assertEquals("", webDriver.findElement(By.name("keyword")).getText());
		//エビデンス取得のためスクロール
		scrollTo("180");
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
