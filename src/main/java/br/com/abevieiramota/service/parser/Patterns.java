package br.com.abevieiramota.service.parser;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.regex.Pattern;

import br.com.abevieiramota.model.Resultado.Premio;

/**
 * TODO: utilizar named groups
 */
public class Patterns {

	private static final String QUALQUER_COISA_MINIMO = ".*?";
	
	private static final String ER_DATA = "(\\d{2}/\\d{2}/\\d{4})";
	
	private static final String ER_PREMIO = 
				"<div class=\"numeros\">"+QUALQUER_COISA_MINIMO+
				
				"(\\d{4})"
			
				+QUALQUER_COISA_MINIMO+"</div>";
	
	public static final Pattern REGEX_DATA = Pattern.compile(ER_DATA);
	
	private static final String ER_RESULTADO = 
			"<div class=\"data"+QUALQUER_COISA_MINIMO+"\">"+QUALQUER_COISA_MINIMO+"Dia "+
	
					ER_DATA // grupo 1
					
			+" Extração das (14:\\d\\dh|19:\\d\\dh)"+QUALQUER_COISA_MINIMO+"</div>"+QUALQUER_COISA_MINIMO+

					ER_PREMIO+ // grupo 2 premio 1
					QUALQUER_COISA_MINIMO+
					ER_PREMIO+ // grupo 3 premio 2
					QUALQUER_COISA_MINIMO+
					ER_PREMIO+ // grupo 4 premio 3
					QUALQUER_COISA_MINIMO+
					ER_PREMIO+ // grupo 5 premio 4
					QUALQUER_COISA_MINIMO+
					ER_PREMIO+ // grupo 6 premio 5
					QUALQUER_COISA_MINIMO+
					ER_PREMIO+ // grupo 7 premio 6
					QUALQUER_COISA_MINIMO+
					ER_PREMIO+ // grupo 8 premio 7
					QUALQUER_COISA_MINIMO+
					ER_PREMIO+ // grupo 9 premio 8
					QUALQUER_COISA_MINIMO+
					ER_PREMIO+ // grupo 10 premio 9
					QUALQUER_COISA_MINIMO+
					ER_PREMIO // grupo 11 premio 10				
					;
	
	public enum GrupoDoRegex {
		DATA(1), TURNO(2);
		
		private static final int POSICAO_INICIAL_GRUPO_PREMIOS = 3;
		public static final int QUANTIDADE_DE_GRUPOS_ESPERADOS = 2 + Premio.values().length;
		public int grupo;
		
		GrupoDoRegex(int grupo) {
			this.grupo = grupo;
		}
		
		public static int grupoDoPremio(Premio premio) {
			checkNotNull(premio);
			
			return POSICAO_INICIAL_GRUPO_PREMIOS + premio.ordinal();
		}
	}
	
	public static final Pattern REGEX_RESULTADO = Pattern.compile(ER_RESULTADO, Pattern.DOTALL);
}
