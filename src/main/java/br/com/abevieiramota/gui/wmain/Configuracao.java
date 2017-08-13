package br.com.abevieiramota.gui.wmain;

import static com.google.common.base.Preconditions.checkNotNull;

import br.com.abevieiramota.model.TipoLoteria;

public class Configuracao {
	
	private static TipoLoteria tipoLoteria = TipoLoteria.LOCAL;
	
	public static void setTipoLoteria(TipoLoteria tipoLoteria) {
		checkNotNull(tipoLoteria);
		
		Configuracao.tipoLoteria = tipoLoteria;
	}
	
	public static TipoLoteria getTipoLoteria() {
		return Configuracao.tipoLoteria;
	}

}
