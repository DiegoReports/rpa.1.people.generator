package rpa.people.generator;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import rpa.people.generator.browser.DriverFactory;
import rpa.people.generator.model.Person;

public class Main {
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "resources\\chromedriver.exe");
		List<Person> personList = generateNames();
		
		//VERIFICANDO SE O PERSONLIST ESTA VAZIO
		if(!personList.isEmpty()) {
			createOutputExcel(personList);
		}
	}
	
	private static List<Person> generateNames() {
		// CRIANDO PERSONLIST NOVA VAZIA PARA SER POPULADA
		List<Person> personList = new ArrayList<>();
		
		try  (DriverFactory driverFactory = new DriverFactory()) {
			WebDriver driver = driverFactory.getChromeDriver();
			
			// Entrando na URL
			driver.get("https://pt.fakenamegenerator.com/");
			
			//APLICANDO FILTROS
			new Select (driver.findElement(By.id("gen"))).selectByVisibleText("Aleatório");
			new Select (driver.findElement(By.id("n"))).selectByVisibleText("Brasil");
			new Select (driver.findElement(By.id("c"))).selectByVisibleText("Brasil");
			
			
			//LACO DE REPETICAO P/ GERAR NOMES
			for (int counter = 0; counter < 10; counter++) {

				//CLICK PARA GERAR PESSOA
				driver.findElement(By.id("genbtn")).click();
				
				//XPATH's PARA COLETAR INFOS
				String fullName = driver.findElement(By.xpath("//div[@class='address']/h3")).getText();
				String[] addressRows = driver.findElement(By.xpath("//div[@class='address']/div[@class='adr']")).getText().split("\n");
				
				String address = addressRows[0];
				String city = addressRows[1].split("-")[0]; 
				String uf = addressRows[1].split("-")[1];
				String zipCode = addressRows[2];
				
				String country = driver.findElement(By.xpath("//div[@id='nameSetApps']/div//a")).getText();
				
				//REALIZANDO ANCORAGEM PARA PEGAR ENDEREÇO DE E-MAIL
				String email = driver.findElement(By.xpath("//dt[contains(text(), 'Endereço de e-mail')]/following::dd[1]")).getText().split("\n")[0];
				
				//ADICIONANDO PARA DENTRO DA LISTA, JA PASSANDO COM OS CONTRUTORORES
				personList.add(new Person(fullName, email, email, country, uf, city, zipCode));
			}
			
			//personList.forEach(person -> System.out.println(person.getFullName()+ " - " + person.getEmail()));
			
			return personList;
			
		} catch (Exception e) {
			System.out.println(e.getCause() + " - " + e.getMessage());
		}
		
		return personList;
	}
	
	private static void createOutputExcel(List<Person> personList) {

	}
}
