package br.com.abevieiramota.model;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dezena")
public class Dezena {

	@Id
	@Column(name = "id_dezena")
	private Integer id;
	@Column(name = "inicio")
	private Integer inicio;
	@Column(name = "fim	")
	private Integer fim;

	public String extract(String milhar) {
		checkNotNull(milhar);

		return milhar.substring(this.inicio, this.fim);
	}
}