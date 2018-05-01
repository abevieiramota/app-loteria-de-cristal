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

	public static Bicho fromResultado(String resultado, Dezena dezena) {
		Integer dezenaValue = Integer.valueOf(dezena.extract(resultado));

		if (dezenaValue == 0) {
			dezenaValue = 100;
		}

		return Bicho.values()[(dezenaValue - 1) / 4];
	}
}