package br.com.jajm.beerstore.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cerveja {
	
	@Id
	@SequenceGenerator(name = "seq_cerveja", sequenceName = "seq_cerveja", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cerveja")
	@EqualsAndHashCode.Include
	private Long codigo;
	
	@NotBlank(message = "cervejas-1")
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "cervejas-2")
	private TipoCerveja tipo;
	
	@NotNull(message = "cervejas-3")
	@DecimalMin(value = "0", message = "cervejas-4")
	private BigDecimal volume;
	
	@JsonIgnore
	public boolean isNova() {
		return this.getCodigo() == null;
	}
	
	@JsonIgnore
	public boolean existente() {
		return this.getCodigo() != null;
	}

}
