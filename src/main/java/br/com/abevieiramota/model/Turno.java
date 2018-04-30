package br.com.abevieiramota.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "turno")
public class Turno {

	@Id
	@Column(name = "id_turno")
	private Integer id;
	@Column(name = "label")
	private String label;
	@Column(name = "hora_min")
	private Integer horaMinima;
	@Column(name = "hora_max")
	private Integer horaMaxima;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHoraMinima() {
		return horaMinima;
	}

	public void setHoraMinima(Integer horaMinima) {
		this.horaMinima = horaMinima;
	}

	public Integer getHoraMaxima() {
		return horaMaxima;
	}

	public void setHoraMaxima(Integer horaMaxima) {
		this.horaMaxima = horaMaxima;
	}

	@Override
	public String toString() {
		return this.label;
	}

	@Override
	public boolean equals(Object other) {

		if (!(other instanceof Turno)) {
			return false;
		}

		Turno otherTurno = (Turno) other;

		return this.id == otherTurno.id;
	}
	
	@Override
	public int hashCode() {
		
		return this.id.hashCode();
	}

}