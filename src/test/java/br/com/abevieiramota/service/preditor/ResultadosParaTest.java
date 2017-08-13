package br.com.abevieiramota.service.preditor;

import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.ResultadoBuilder;
import br.com.abevieiramota.model.Turno;

public class ResultadosParaTest {
	
	public static Resultado comPrimeiroPremio(String primeiroPremio) {
		Resultado resultado = new ResultadoBuilder().data("27/07/2013").premios(new String[]{
				primeiroPremio,
				"0383",
				"8157",
				"0863",
				"7532",
				"0874",
				"4668",
				"3967",
				"5237",
				"0375"
				
		}).turno(Turno.NOTURNO).build();
		
		return resultado;
	}

}
