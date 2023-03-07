package com.controle.bibliotheque.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "Livres")
public class Livre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 50, nullable = false)
	private String titre;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(nullable = false, updatable = false)
	private String maison_idition;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(nullable = false)
	private Date date_sortie;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(nullable = false)
	private String auteur;

	@Column(nullable = false)
	private int nombre_page;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(nullable = false, updatable = false, unique = true)
	private long isbn;
	

	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	//@JsonFormat(pattern = "yyyyy-mm-dd hh:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column( nullable = false)
	private Date date_derniere_consultation;

	@Column(nullable = false)
	private Boolean diponible;

}




///////Main//////
package org.example;

public class Main {
    public static void main(String[] args) {

        ComputeData data = new ComputeData();
        DisplayData[] result = data.processData();
        for(int i = 0; i< result.length; ++i) {
            System.out.println(result[i].toString());
        }
    }
}
///////CoefficientCotisation/////
package org.example;

import java.time.LocalDate;
import java.time.Period;

public enum CoefficientCotisation {

	/** Values **/
	MOINS_50(0.2f, 0.17f),
	ENTRE_50_60(0.13f, 0.13f),
	ENTRE_60_65(0.075f, 0.09f),
	PLUS_65(0.05f, 0.075f);
	
	/** Attributes **/
	private float coeffEmployee;
	private float coeffEmployer;
	
	/**
	 * Constructors
	 */
	CoefficientCotisation(float coeffEmployee, float coeffEmployer) {
		this.coeffEmployee = coeffEmployee;
		this.coeffEmployer = coeffEmployer;
	}

	/** Getters & Setters **/
	public float getCoeffEmployee() {
		return coeffEmployee;
	}

	public void setCoeffEmployee(float coeffEmployee) {
		this.coeffEmployee = coeffEmployee;
	}

	public float getCoeffEmployer() {
		return coeffEmployer;
	}

	public void setCoeffEmployer(float coeffEmployer) {
		this.coeffEmployer = coeffEmployer;
	}
	
	/** Get value by age **/
	public static CoefficientCotisation getCoefficientsByDateNaissance(LocalDate dateNaissance) {
		int age = Period.between(dateNaissance, LocalDate.now()).getYears();  
		if(age <= 50) return MOINS_50;
		if(age > 50 && age <= 60) return ENTRE_50_60;
		if(age > 60 && age <= 65) return ENTRE_60_65;
		else return PLUS_65;
	}
}

////ComputeData//////
package org.example;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ComputeData {

	Map<Integer, Grade> grades = new HashMap<Integer, Grade>();
	List<Employee> employees = new ArrayList<Employee>();
	
	public void setData() {
		
		// Cr�ation des grades
		Grade manager = new Grade(1, "Manager operationnel", 25000);
		grades.put(1, manager);
		Grade chef = new Grade(2, "Chef de departement", 20000);
		grades.put(2, chef);
		Grade cadreSup = new Grade(3, "Cadre superieur", 15000);
		grades.put(3, cadreSup);
		Grade cadreCom = new Grade(4, "Cadre commercial", 12500);
		grades.put(4, cadreCom);
		Grade ing = new Grade(5, "Ingenieur", 10000);
		grades.put(5, ing);
		Grade tech = new Grade(6, "Technicien", 8000);
		grades.put(6, tech);
		Grade autre = new Grade(7, "Autre", 5000);
		grades.put(7, autre);
		
		// Cr�ation des employ�s
		Grade gradeEmp = this.getRandomGrade(grades);
		Employee employee = new Employee("BENKHALIL Mouad", "23/06/1987", gradeEmp);
		employees.add(employee);
		gradeEmp = this.getRandomGrade(grades);
		employee = new Employee("BENCHAKIB Laila", "05/05/1988", gradeEmp);
		employees.add(employee);
		gradeEmp = this.getRandomGrade(grades);
		employee = new Employee("BOULMANE Sarah", "05/07/1999", gradeEmp);
		employees.add(employee);
		gradeEmp = this.getRandomGrade(grades);
		employee = new Employee("CIQAL Mohamed", "19/02/1980", gradeEmp);
		employees.add(employee);
		gradeEmp = this.getRandomGrade(grades);
		employee = new Employee("CHABA Meriem", "01/02/1973", gradeEmp);
		employees.add(employee);
		gradeEmp = this.getRandomGrade(grades);
		employee = new Employee("DEDDI Salah", "15/03/1957", gradeEmp);
		employees.add(employee);
		gradeEmp = this.getRandomGrade(grades);
		employee = new Employee("ENNAJAR Fouad", "08/08/1962", gradeEmp);
		employees.add(employee);
		gradeEmp = this.getRandomGrade(grades);
		employee = new Employee("MERZOUKI Bilal", "10/12/1968", gradeEmp);
		employees.add(employee);
		gradeEmp = this.getRandomGrade(grades);
		employee = new Employee("WADOUD Faouzi", "05/07/1963", gradeEmp);
		employees.add(employee);
		gradeEmp = this.getRandomGrade(grades);
		employee = new Employee("ZEROUAL Adnane", "19/02/1955", gradeEmp);
		employees.add(employee);
	}
	
	private Grade getRandomGrade(Map<Integer, Grade> grades) {
		int min = 1, max = 7;
	    Random rand = new Random();
		int index =  rand.nextInt((max - min) + 1) + min;
		return grades.get(index);
	}

	public DisplayData[] processData() {
		DisplayData[] result = new DisplayData[10];
		this.setData();
		Collections.sort(this.employees);
		int i = 0;
		for(Employee unEmpl : this.employees) {
			CoefficientCotisation coeff = CoefficientCotisation.getCoefficientsByDateNaissance(unEmpl.getDateNaissance());
			int age = Period.between(unEmpl.getDateNaissance(), LocalDate.now()).getYears();  
			int salary =  unEmpl.getGrade().getSalary();
			float cotisation = salary * (coeff.getCoeffEmployee() + coeff.getCoeffEmployer());
			String cotisationStr = String.format("%.2f", cotisation);
			result[i] = new DisplayData(unEmpl.getNomComplet(), Integer.toString(age), unEmpl.getGrade().getLibelle(), cotisationStr);
			++i;
		}
		return result;
	}
}

//////DisplayData///////
package org.example;

public class DisplayData {

	private String nomComplet;
	private String age;
	private String grade;
	private String cotisation;
	
	/**
	 * Constructor
	 */
	public DisplayData(String nomComplet, String age, String grade, String cotisation) {
		super();
		this.nomComplet = nomComplet;
		this.age = age;
		this.grade = grade;
		this.cotisation = cotisation;
	}


	/** Getters & Setters **/
	public String getNomComplet() {
		return nomComplet;
	}
	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getCotisation() {
		return cotisation;
	}
	public void setCotisation(String cotisation) {
		this.cotisation = cotisation;
	}

	/** toString **/
	@Override
	public String toString() {
	    return "Nom complet: "+this.nomComplet+" -Age : "+this.age+" ans - Grade : "
	    		+this.grade+" -Total de la cotisation : "+this.cotisation+"€";
	}
}
 /////Employee///////

package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Employee implements Comparable<Employee>{

	private String nomComplet;
	private String dateNaissance;
	private Grade grade;
	
	/**
	 * Constructor
	 */
	public Employee(String nomComplet, String dateNaissance, Grade grade) {
		super();
		this.nomComplet = nomComplet;
		this.dateNaissance = dateNaissance;
		this.grade = grade;
	}
	
	/** Getters & Setters **/
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	public String getNomComplet() {
		return nomComplet;
	}
	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}
	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	
	public LocalDate getDateNaissance() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(this.dateNaissance, formatter);
	}

	@Override
	public int compareTo(Employee o) {
		if(this.getGrade().getOrdreHierarchique() == o.getGrade().getOrdreHierarchique()) {
			return this.getNomComplet().compareTo(o.getNomComplet());
		}else {
			return this.getGrade().getOrdreHierarchique() - o.getGrade().getOrdreHierarchique();
		}
	}
	
	
}

/////Grade//////
package org.example;

public class Grade {

	private int ordreHierarchique;
	private String libelle;
	private int salary;
	
	/**
	 * Constructor
	 */
	public Grade(int ordreHierarchique, String libelle, int salary) {
		super();
		this.ordreHierarchique = ordreHierarchique;
		this.libelle = libelle;
		this.salary = salary;
	}
	
	/** Getters & Setters **/
	public int getOrdreHierarchique() {
		return ordreHierarchique;
	}
	public void setOrdreHierarchique(int ordreHierarchique) {
		this.ordreHierarchique = ordreHierarchique;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public int getSalary() {
		return salary>10000?10000 : salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	
}



