package br.com.abevieiramota.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import br.com.abevieiramota.messages.Messages;

public enum Turno {

	DIURNO(new String[] { "14:00h", "14:40h", "14:45h", "14:30h" }),
	NOTURNO(new String[] { "19:10h", "19:20h", "19:30h", "19:00h" });

	private static final String MSG_ERRO_REPRESENTACAO = Messages.getString("erro.turno_inexistente");
	private static final Map<String, Turno> MAP_REPR;

	static {
		Map<String, Turno> _mapRepr = new HashMap<>();

		for (Turno turno : Turno.values()) {
			for (String repr : turno.reprs) {
				_mapRepr.put(repr, turno);
			}
		}

		MAP_REPR = Collections.unmodifiableMap(_mapRepr);
	}

	private String[] reprs;

	Turno(String[] reprs) {
		this.reprs = reprs;
	}

	public static Turno get(String repr) {
		if (!MAP_REPR.containsKey(repr)) {
			throw new IllegalArgumentException(String.format(MSG_ERRO_REPRESENTACAO, repr));
		}

		return MAP_REPR.get(repr);
	}

}