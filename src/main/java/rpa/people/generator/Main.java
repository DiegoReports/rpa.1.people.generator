package rpa.people.generator;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
				
				//ADICIONANDO PARA DENTRO DA LISTA, JA PASSANDO COM OS CONsTRUTORORES
				personList.add(new Person(fullName, email, null, address, country, uf, city, zipCode));
			}

			return personList;
			
		} catch (Exception e) {
			System.out.println(e.getCause() + " - " + e.getMessage());
		}
		
		return personList;
	}
	
	//CRIANDO EXCEL DE SAIDA
	private static void createOutputExcel(List<Person> personList) {
		//personList.forEach(person -> System.out.println(person.getFullName()+ " - " + person.getEmail()));
		
		//USANDO TRY COM RESOURCES (DENTRO JÁ POSSUI METODO CLOSEABLE)
		try (Workbook workbook = new XSSFWorkbook()) {
			//CRIANDO ESTRURUA DE ARQUIVO (tipo .xlsx = XSSF)
			
			//CRIANDO ABA NA PLANILHA
			Sheet sheet = workbook.createSheet("people");
			//CRIANDO LINHAS DENTRO DO EXCEL (nºLinha)
			Row headerRow = sheet.createRow(0);
			//CRIANDO CADA CELULA (cabecalho)
			headerRow.createCell(0).setCellValue("fullName");
			headerRow.createCell(1).setCellValue("city");
			headerRow.createCell(2).setCellValue("country");
			headerRow.createCell(3).setCellValue("email");
			headerRow.createCell(4).setCellValue("address");
			headerRow.createCell(5).setCellValue("gender");
			headerRow.createCell(6).setCellValue("uf");
			headerRow.createCell(7).setCellValue("zipcode");
			
			//CRIANDO UMA NOVA LINHA A CADA PESSOA
			int line = 1; //COMECAR APÓS CABECALHO
			for(Person person : personList) {
				Row row = sheet.createRow(line);
				
				//PREENCHENDO CEDULAS
				row.createCell(0).setCellValue(person.getFullName());
				row.createCell(1).setCellValue(person.getCity());
				row.createCell(2).setCellValue(person.getCountry());
				row.createCell(3).setCellValue(person.getEmail());
				row.createCell(4).setCellValue(person.getAddress());
				row.createCell(5).setCellValue(person.getGender());
				row.createCell(6).setCellValue(person.getUf());
				row.createCell(7).setCellValue(person.getZipCode());
				
				line++;
				
			}
			
			//ENDERECO DO ARQUIVO DE SAIDA
			String outputFile = "C:\\Users\\diego\\Documents\\RPA INSIDERS\\rpa-turma-01\\rpa.1.people.generator\\data\\01.entrada\\people_file.xlsx";
			FileOutputStream outputStream = new FileOutputStream(outputFile);
			workbook.write(outputStream);
			
			//FECHANDO PROCESSO DA NOSSA WORKBOOK
			//workbook.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

