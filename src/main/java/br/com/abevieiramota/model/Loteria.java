package br.com.abevieiramota.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "loteria")
public class Loteria {

	@Id
	@Column(name = "id_loteria")
	private Integer id;
	@Column(name = "label")
	private String label;
	@Column(name = "is_atualizavel")
	private Boolean isAtualizavel;
	@OneToOne(mappedBy = "loteria", fetch = FetchType.EAGER)
	private Configuracao configuracao;

	public Boolean isAtualizavel() {
		return this.isAtualizavel;
	}

	public Configuracao getConfiguracao() {
		return this.configuracao;
	}

	@Override
	public String toString() {
		return this.label;
	}
}