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
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		//上部メニュー「ようこそ○○さん」リンクをクリック
		webDriver.findElement(By.linkText("ようこそ受講生ＡＡ２さん")).click();
		//タイトル一致確認
		assertEquals("ユーザー詳細", webDriver.getTitle());
		//エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//ボタン表示のためスクロール
		scrollTo("400");
		//2022年10月2日のレポート列の「修正する」ボタンを押下
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/table[2]/tbody/tr[2]/td[5]/form[2]")).click();
		//タイトル一致確認
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		//エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		//理解度：1を入力
		webDriver.findElement(By.id("intFieldValue_0")).click();
		webDriver.findElement(By.xpath("//option[@value='1']")).click();
		//要素表示のためスクロール
		scrollTo("400");
		//提出するボタンを押下
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();

		//エラー表示かクラスで確認
		assertTrue(webDriver.findElement(By.cssSelector("#intFieldName_0.errorInput")).isDisplayed());
		//エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		//理解度：1を削除し学習項目にテストと入力
		webDriver.findElement(By.id("intFieldName_0")).sendKeys("テスト");
		webDriver.findElement(By.id("intFieldValue_0")).click();
		webDriver.findElement(By.xpath("//option[1]")).click();
		//要素表示のためスクロール
		scrollTo("400");
		//提出するボタンを押下
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();

		//エラー表示かクラスで確認
		assertTrue(webDriver.findElement(By.cssSelector("#intFieldValue_0.errorInput")).isDisplayed());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		//学習項目削除
		webDriver.findElement(By.id("intFieldName_0")).clear();
		//目標達成度を11で入力
		webDriver.findElement(By.id("content_0")).clear();
		webDriver.findElement(By.id("content_0")).sendKeys("test");
		//要素表示のためスクロール
		scrollTo("400");
		//提出するボタンを押下
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();
		//エラー表示かクラスで確認
		assertTrue(webDriver.findElement(By.cssSelector("#content_0.errorInput")).isDisplayed());
		//エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		//目標達成度を11で入力
		webDriver.findElement(By.id("content_0")).clear();
		webDriver.findElement(By.id("content_0")).sendKeys("11");
		//提出するボタンを押下
		//要素表示のためスクロール
		scrollTo("400");
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();
		//エラー表示かクラスで確認
		assertTrue(webDriver.findElement(By.cssSelector("#content_0.errorInput")).isDisplayed());
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		//目標達成度・所感を未入力
		webDriver.findElement(By.id("content_0")).clear();
		webDriver.findElement(By.id("content_1")).clear();
		//要素表示のためスクロール
		scrollTo("400");
		//提出するボタンを押下
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();
		//エラー表示かクラスで確認
		assertTrue(webDriver.findElement(By.cssSelector("#content_0.errorInput")).isDisplayed());
		assertTrue(webDriver.findElement(By.cssSelector("#content_1.errorInput")).isDisplayed());

		//要素表示のためスクロール
		scrollTo("300");
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		//目標の達成度を初期値5に修正
		webDriver.findElement(By.id("content_0")).sendKeys("5");
		//所感・一週間の振り返りが2000文字超を入力 タイムアウトのため分割入力
		webDriver.findElement(By.id("content_1")).sendKeys(
				"1r67XRPy8dHtyJhU1OBVQSBeXUTv2AnODPDf0pnRiIDvhbphX16bxqX1fLwTJBE7cmeVawshEPCh7ta4Lge0L8UWM0JG5qS2AciOAStJXafFMqwyC4AcHlLMXqISS48lgFnBqE0O4DT7th0qV3EhOx1REuqPDL3nmbb6VlaSYpXVPMjcjTT7EdVd5GWfVDbcBraq1HipbvEJGPkd3ytqRIn8DTTJmGcRA3bq6LtYgw0QniUcfaFmA1FAJRWydPIi4glYCwtvkjdQoF6MHLMRd28AHsQhgmFQiw6XmT31j6W5bYVgi4QCtafn7OForNByIPXErBRLhLYTQQhiy4LVAct3WYFUypUllbYEfkNO0RLjwjWhfyWWXDqPYd3cRMh3J2oFvWTQFEWqMcmsOKJXUtdaFwMGxWWnVCYkKvK8bNpVKGt3V0F4hdK6wTvgE7UtrNCnyFPRyWnhDu1ou00pCOcM8UQyG3q3Q0OJ5XdDd0GiLigvY5OG4bOW6ThesvIgtxgdtEIya5IYQry6Ux6cyJnqrjM85UTCa2YW1vd70qhxlhCOrIvG6Kay1SNmh6WqyCh4VbKcn41kIQhmhsGYqqYI84RhLcI3QPBGgctoWOvUwhjyxuvhcMBaermypThuxcIlH7A4Ii3uIeLABNOmmBeAHiLihgUQlxiOx3kL1t4g6CrHJPFlKbbvmkttfN57whgCfYDw1qx3IK5YjfE63Vgmho2vPaheSScqpanao23un6cfcNRs88QFqUEumXxlAubqKMuBFoT7AGBVKiGIGgPmENPttcd8iaKGD56dIe0oOJF8I3vEGrGmObm8hJJhcU3sMOLt4jAkSmsRp3CWO11c0JxLddLbRfph6WlUvgKpLQC58pmscG3BWl65uN4KQBlL0wQArFtOVtlHWo7Ue83GIBYfje51pfSGlYv4NxIWpPbyXBKX3BqhpHBkehDv2BDNaJE8WcFlLbo8sBJIkOPIBIjp2BCWMIUsBRNAPAnDvsiIoU4tA0hesk1fauBadQElYDQ6VEW4TlW6S2JSXCQ7s7ptPXhXNvxc");
		webDriver.findElement(By.id("content_1")).sendKeys(
				"21jN2VBIyKvmuLX6vlMxC6XYVCdMkgoTASj4JUykj3STCof1jSWDb6OVbYUyEcpL8cDcWV2FuPkCtYMVlLWlJBQiVWXFwov5dehCMvqEujfBwfTgw7wnlO1vUWynxWF1PcBlCUa25RLBQOKttFQJ7G8cUs5quTOmwXfaB03mvG3U6N71JiWGjepRvpo7Fd8PnWGN8RRe8MrwAueu2QkiGAO0DVmQYstP81NP3PGhy6g3MJlTDtMSKbhLAkJKLEGorxLsOe4CN7bBAfFp40um0wsWWijA0ofEQnpRbKjCOEoXtnv5PGeuXc1QSW4d5aEIR7QXEwlK8j6nUj0igTwypSiVRI67edyWIPLvuMcW1KIAQas2cmaty5nT538Sdq8TYqeaY4FJtNCGKKCLrAnSSuOT1MFHUAqDLfhEgFgqUhAOySVLYQn88Yj4YXVmFIXw4tGfJuAfHvWSoNtwJ8BiDKkdYDjPyhUCjODfssvQlyH7J0KjtOmNyeJLAhrjq1ev26frDYpqNb3udtXHOeG5pdYcXFaNP4mxJpr7iIkjDYhXREl6dQx5qk51qvatUfq7GSC2ySAPTNThWg8bSNwINPFOmANwJX3aiHlJUe6MnbSql8hHxd076SlR3vCB60cDiDpX7KLlwFk7BPP0RGqELfVcppIK4YfJYyQC7EndMfYhaCoF7NbO2lBH1wlLeYOtwwkL1LrRJHDccm8aH2jfE4wgT3aVNPwupxtEKA6DBNmDT8xLdqSeyBryCTAJ3Bjjssdi3WcQBouH3oSwWd01VogbIwx1XYWp2oqPOsV5W4eGqx88Hpq1atGWMaOcgsxKVtpUDuXIxnx6JWsR2tPQSXGcjEaOOQ2l1q1njUT1QwiPnF4dYnHbfa2eJGp4GMyjeSlmV3YEnXSp5NAbbJlCJTdfI6IsTGGdPgbUybWdOKCI8");

		webDriver.findElement(By.id("content_2")).sendKeys(
				"1r67XRPy8dHtyJhU1OBVQSBeXUTv2AnODPDf0pnRiIDvhbphX16bxqX1fLwTJBE7cmeVawshEPCh7ta4Lge0L8UWM0JG5qS2AciOAStJXafFMqwyC4AcHlLMXqISS48lgFnBqE0O4DT7th0qV3EhOx1REuqPDL3nmbb6VlaSYpXVPMjcjTT7EdVd5GWfVDbcBraq1HipbvEJGPkd3ytqRIn8DTTJmGcRA3bq6LtYgw0QniUcfaFmA1FAJRWydPIi4glYCwtvkjdQoF6MHLMRd28AHsQhgmFQiw6XmT31j6W5bYVgi4QCtafn7OForNByIPXErBRLhLYTQQhiy4LVAct3WYFUypUllbYEfkNO0RLjwjWhfyWWXDqPYd3cRMh3J2oFvWTQFEWqMcmsOKJXUtdaFwMGxWWnVCYkKvK8bNpVKGt3V0F4hdK6wTvgE7UtrNCnyFPRyWnhDu1ou00pCOcM8UQyG3q3Q0OJ5XdDd0GiLigvY5OG4bOW6ThesvIgtxgdtEIya5IYQry6Ux6cyJnqrjM85UTCa2YW1vd70qhxlhCOrIvG6Kay1SNmh6WqyCh4VbKcn41kIQhmhsGYqqYI84RhLcI3QPBGgctoWOvUwhjyxuvhcMBaermypThuxcIlH7A4Ii3uIeLABNOmmBeAHiLihgUQlxiOx3kL1t4g6CrHJPFlKbbvmkttfN57whgCfYDw1qx3IK5YjfE63Vgmho2vPaheSScqpanao23un6cfcNRs88QFqUEumXxlAubqKMuBFoT7AGBVKiGIGgPmENPttcd8iaKGD56dIe0oOJF8I3vEGrGmObm8hJJhcU3sMOLt4jAkSmsRp3CWO11c0JxLddLbRfph6WlUvgKpLQC58pmscG3BWl65uN4KQBlL0wQArFtOVtlHWo7Ue83GIBYfje51pfSGlYv4NxIWpPbyXBKX3BqhpHBkehDv2BDNaJE8WcFlLbo8sBJIkOPIBIjp2BCWMIUsBRNAPAnDvsiIoU4tA0hesk1fauBadQElYDQ6VEW4TlW6S2JSXCQ7s7ptPXhXNvxc");
		webDriver.findElement(By.id("content_2")).sendKeys(
				"21jN2VBIyKvmuLX6vlMxC6XYVCdMkgoTASj4JUykj3STCof1jSWDb6OVbYUyEcpL8cDcWV2FuPkCtYMVlLWlJBQiVWXFwov5dehCMvqEujfBwfTgw7wnlO1vUWynxWF1PcBlCUa25RLBQOKttFQJ7G8cUs5quTOmwXfaB03mvG3U6N71JiWGjepRvpo7Fd8PnWGN8RRe8MrwAueu2QkiGAO0DVmQYstP81NP3PGhy6g3MJlTDtMSKbhLAkJKLEGorxLsOe4CN7bBAfFp40um0wsWWijA0ofEQnpRbKjCOEoXtnv5PGeuXc1QSW4d5aEIR7QXEwlK8j6nUj0igTwypSiVRI67edyWIPLvuMcW1KIAQas2cmaty5nT538Sdq8TYqeaY4FJtNCGKKCLrAnSSuOT1MFHUAqDLfhEgFgqUhAOySVLYQn88Yj4YXVmFIXw4tGfJuAfHvWSoNtwJ8BiDKkdYDjPyhUCjODfssvQlyH7J0KjtOmNyeJLAhrjq1ev26frDYpqNb3udtXHOeG5pdYcXFaNP4mxJpr7iIkjDYhXREl6dQx5qk51qvatUfq7GSC2ySAPTNThWg8bSNwINPFOmANwJX3aiHlJUe6MnbSql8hHxd076SlR3vCB60cDiDpX7KLlwFk7BPP0RGqELfVcppIK4YfJYyQC7EndMfYhaCoF7NbO2lBH1wlLeYOtwwkL1LrRJHDccm8aH2jfE4wgT3aVNPwupxtEKA6DBNmDT8xLdqSeyBryCTAJ3Bjjssdi3WcQBouH3oSwWd01VogbIwx1XYWp2oqPOsV5W4eGqx88Hpq1atGWMaOcgsxKVtpUDuXIxnx6JWsR2tPQSXGcjEaOOQ2l1q1njUT1QwiPnF4dYnHbfa2eJGp4GMyjeSlmV3YEnXSp5NAbbJlCJTdfI6IsTGGdPgbUybWdOKCI8");

		//要素表示のためスクロール
		scrollTo("400");
		//提出するボタンを押下
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();
		//エラー表示かクラスで確認
		assertTrue(webDriver.findElement(By.cssSelector("#content_1.errorInput")).isDisplayed());
		assertTrue(webDriver.findElement(By.cssSelector("#content_2.errorInput")).isDisplayed());

		//要素表示のためスクロール
		scrollTo("400");
		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
