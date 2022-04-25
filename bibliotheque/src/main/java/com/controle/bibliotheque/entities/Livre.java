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
