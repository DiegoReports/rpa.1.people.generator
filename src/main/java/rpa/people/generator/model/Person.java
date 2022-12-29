package rpa.people.generator.model;

public class Person {

	private String fullName;
	private String email;
	private String gender;
	private String country;
	private String uf;
	private String city;
	private String zipCode;
	
	//Constructor com mesmo nome da nossa classe
	//Obrigatoriamente precisara passar um nome completo e um Email
	public Person() {
		
	}
	
	//Constructor criado com generate
	public Person(String fullName, String email, String gender, String country, String uf, String city,
			String zipCode) {

		this.fullName = fullName;
		this.email = email;
		this.gender = gender;
		this.country = country;
		this.uf = uf;
		this.city = city;
		this.zipCode = zipCode;
	}



	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return " {fullName: '" + fullName + "', email: '" + email + "', gender: '" + gender + "', country: '" + country
				+ "', uf: '" + uf + "', city: '" + city + "', zipCode: '" + zipCode + "'}";
	}
	
}
