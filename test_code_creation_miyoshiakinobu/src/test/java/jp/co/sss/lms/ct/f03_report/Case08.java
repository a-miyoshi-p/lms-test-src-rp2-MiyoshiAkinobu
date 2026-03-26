package jp.co.sss.lms.ct.f03_report;

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
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//コース詳細画面 2022年10月2日コースの詳細ボタン押下
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/div[2]/table/tbody/tr[2]/td[5]/form/input[3]"))
				.click();
		//待機処理
		visibilityTimeout(By.id("sectionDetail"), 2);
		//タイトル一致確認
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		//セクション詳細画面遷移し、押下したコースであることをセクション、日付で確認
		assertEquals("アルゴリズム、フローチャート 2022年10月2日",
				webDriver.findElement(By.cssSelector("#sectionDetail h2")).getText());
		//エビデンスのためスクロール調整
		scrollTo("200");
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//ボタン表示のためスクロール
		scrollTo("200");
		//「日報【デモ】を提出する」ボタンを押下
		webDriver.findElement(By.xpath("//input[@value='週報【デモ】を提出する']")).click();
		//待機処理
		visibilityTimeout(By.tagName("fieldset"), 2);

		//タイトル一致確認
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		//対象のレポート登録画面であることを日付で確認
		assertEquals("週報【デモ】 2022年10月2日", webDriver.findElement(By.cssSelector("#main h2")).getText());
		//エビデンスのためスクロール調整
		scrollTo("180");
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		//テキストエリアに入力
		webDriver.findElement(By.id("content_0")).sendKeys("5");
		webDriver.findElement(By.id("content_1")).sendKeys("入力テスト");
		//ボタン表示のためスクロール
		scrollTo("400");
		//提出するボタン押下
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();
		//待機処理
		visibilityTimeout(By.id("sectionDetail"), 2);
		//タイトル一致確認
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		//エビデンスのためスクロール調整
		scrollTo("200");
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		//上部メニュー「ようこそ○○さん」リンクをクリック
		webDriver.findElement(By.linkText("ようこそ受講生ＡＡ２さん")).click();
		//タイトル一致確認
		assertEquals("ユーザー詳細", webDriver.getTitle());
		//ボタン表示のためスクロール
		scrollTo("400");
		//週報登録確認
		assertEquals("2022年10月2日(日)", webDriver.findElement(By.xpath("//table[2]/tbody/tr[2]/td[1]")).getText());
		assertEquals("週報【デモ】", webDriver.findElement(By.xpath("//table[2]/tbody/tr[2]/td[2]")).getText());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		//該当レポートの詳細画面遷移
		webDriver.findElement(By.xpath("//table[2]/tbody/tr[2]/td[5]/form[1]")).click();
		//待機処理
		visibilityTimeout(By.xpath("//button[text()='フィードバックコメントを登録する']"), 2);
		//タイトル一致確認
		assertEquals("レポート詳細 | LMS", webDriver.getTitle());
		//レポート詳細画面で入力した情報一致確認
		assertEquals("5", webDriver.findElement(By.xpath("//div[@id='main']/div[2]/table/tbody/tr[1]/td")).getText());
		assertEquals("入力テスト",
				webDriver.findElement(By.xpath("//div[@id='main']/div[2]/table/tbody/tr[2]/td")).getText());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
