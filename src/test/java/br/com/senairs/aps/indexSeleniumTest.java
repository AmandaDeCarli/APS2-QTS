package br.com.senairs.aps;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import junit.framework.Assert;

public class indexSeleniumTest {

	private static WebDriver driver = null;

	String caminhoPagina = "C:\\Users\\amand\\eclipse-workspace\\APS2-QTS\\src\\main\\java\\br\\com\\senairs\\aps2\\index.html";

	@BeforeClass
	public static void setUpBefore() {
		System.setProperty("webdriver.chrome.driver", 
                "C:\\chromedriver_win32\\chromedriver.exe");
        

		driver = new ChromeDriver();
	}

	@Before
	public void setUp() {
		driver.get(caminhoPagina);

		limparCampos();
	}
	
	 @Test
	    public void testTituloPagina(){
	      driver.get("C:\\Users\\amand\\eclipse-workspace\\APS2-QTS\\src\\main\\java\\br\\com\\senairs\\aps2\\index.html");
	      String tituloExperado ="Trabalho 2-1"; 
	        
	      Assert.assertEquals(tituloExperado,driver.getTitle());
	    }

	@Test
	public void cadastroValido() {
		campoNome().sendKeys("Amanda");
		sexoSelect().selectByValue("f");
		campoEndereco().sendKeys("R 25 de Marco, 200 - SP");
		campoIdade().sendKeys("25");
		salvarBotao().click();

		Alert alert = driver.switchTo().alert();

		String esperado = "Cadastro realizado com sucesso";
		String atual = alert.getText();

		alert.accept();

		Assert.assertEquals(esperado, atual);
	}

	@Test
	public void testeNomeInvalido() {

		campoEndereco().sendKeys("R Julho, 5");
		campoIdade().sendKeys("25");
		sexoSelect().selectByValue("f");

		campoNome().sendKeys("");

		salvarBotao().click();

		String esperado = "Preencha o campo nome";
		String atual = getResultado();

		Assert.assertEquals(esperado, atual);
	}

	@Test
	public void testeNomeValido() {

		campoNome().sendKeys("Claudia Roseli");

		salvarBotao().click();

		String naoEsperado = "Preencha o campo nome";
		String atual = getResultado();

		Assert.assertNotSame(naoEsperado, atual);
	}

	@Test
	public void testeEnderecoInvalido() {
		campoNome().sendKeys("Antonio Lucio");
		campoIdade().sendKeys("58");
		sexoSelect().selectByValue("m");

		campoEndereco().sendKeys("");

		salvarBotao().click();

		String esperado = "Preencha o campo endereco";
		String atual = getResultado();

		Assert.assertEquals(esperado, atual);
	}

	@Test
	public void testeEnderecoValido() {

		campoEndereco().sendKeys("Avenida Souza Melo, Sarandi");
		salvarBotao().click();

		String naoEsperado = "Preencha o campo endereco";
		String atual = getResultado();

		Assert.assertNotSame(naoEsperado, atual);
	}

	@Test
	public void testeSexoInvalido() {
		campoNome().sendKeys("Fernando De Carli");
		campoEndereco().sendKeys("Rua Lacador, 555");
		campoIdade().sendKeys("20");

		sexoSelect().selectByValue("...");

		salvarBotao().click();

		String esperado = "Selecione um valor para o campo sexo";
		String atual = getResultado();

		Assert.assertEquals(esperado, atual);
	}

	@Test
	public void testeSexoValido() {

		sexoSelect().selectByValue("f");
		salvarBotao().click();

		String naoEsperado = "Selecione um valor para o campo sexo";
		String atual = getResultado();

		Assert.assertNotSame(naoEsperado, atual);
	}

	@Test
	public void testeIdadeInvalidoLetras() {
		campoNome().sendKeys("Clecy");
		campoEndereco().sendKeys("Rua Robert Augusto, 666");
		sexoSelect().selectByValue("f");

		campoIdade().sendKeys("lalala");

		salvarBotao().click();

		String esperado = "Preencha o campo idade, somente com numeros";
		String atual = getResultado();

		Assert.assertEquals(esperado, atual);
	}

	@Test
	public void testeIdadeInvalidoCampoVazio() {
		campoNome().sendKeys("Vera Regina");
		campoEndereco().sendKeys("Rua Lacador, 555");
		sexoSelect().selectByValue("f");

		campoIdade().sendKeys("");

		salvarBotao().click();

		String esperado = "Preencha o campo idade, somente com numeros";
		String atual = getResultado();

		Assert.assertEquals(esperado, atual);
	}

	@Test
	public void testeIdadeInvalidoComZero() {
		campoNome().sendKeys("Luciane");
		campoEndereco().sendKeys("Rua Talarico, 2 - Apt 2");
		sexoSelect().selectByValue("f");

		campoIdade().sendKeys("-1");

		salvarBotao().click();

		String esperado = "Preencha o campo idade com valores acima de 0";
		String atual = getResultado();

		Assert.assertEquals(esperado, atual);
	}

	@Test
	public void testeIdadeValido() {

		campoIdade().sendKeys("25");
		salvarBotao().click();

		String naoEsperadoUm = "Preencha o campo idade com valores acima de 0";
		String naoEsperadoDois = "Preencha o campo idade, somente com numeros";

		String atual = getResultado();

		Assert.assertNotSame(naoEsperadoUm, atual);
		Assert.assertNotSame(naoEsperadoDois, atual);

	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}

	private WebElement campoNome() {
		return driver.findElement(By.id("nome"));
	}

	private WebElement campoEndereco() {
		return driver.findElement(By.id("endereco"));
	}

	private WebElement campoIdade() {
		return driver.findElement(By.id("idade"));
	}

	private WebElement salvarBotao() {
		return driver.findElement(By.id("botao_somar"));
	}

	private Select sexoSelect() {
		Select selectSexo = new Select(driver.findElement(By.id("sexo")));

		return selectSexo;
	}

	private WebElement resultadoErro() {
		return driver.findElement(By.id("resultado"));
	}

	private void limparCampos() {
		campoNome().clear();
		campoIdade().clear();
		campoEndereco().clear();
		sexoSelect().selectByValue("...");
	}

	private String getResultado() {
		return resultadoErro().getAttribute("innerHTML");
	}

}