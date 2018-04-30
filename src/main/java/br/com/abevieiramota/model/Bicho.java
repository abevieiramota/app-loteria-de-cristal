package br.com.abevieiramota.model;

public enum Bicho {
	AVESTRUZ,
	AGUIA,
	BURRO,
	BORBOLETA,
	CACHORRO,
	CABRA,
	CARNEIRO,
	CAMELO,
	COBRA,
	COELHO,
	CAVALO,
	ELEFANTE,
	GALO,
	GATO,
	JACARE,
	LEAO,
	MACACO,
	PORCO,
	PAVAO,
	PERU,
	TOURO,
	TIGRE,
	URSO,
	VEADO,
	VACA;

	public static Bicho fromResultado(String resultado, Dezena tipoDezena) {
		Integer dezena = Integer.valueOf(tipoDezena.extract(resultado));

		if (dezena == 0) {
			dezena = 100;
		}

		return Bicho.values()[(dezena - 1) / 4];
	}
}