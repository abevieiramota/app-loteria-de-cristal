package br.com.abevieiramota.gui.wmain;

import static com.google.common.base.Preconditions.checkNotNull;

import br.com.abevieiramota.model.Loteria;

public class Parametros {
	
	private static Loteria loteria;
	
	public static void setLoteria(Loteria loteria) {
		checkNotNull(loteria);
		
		Parametros.loteria = loteria;
	}
	
	public static Loteria getLoteria() {
		return Parametros.loteria;
	}

}
