package br.com.abevieiramota.service.preditor;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;
import java.util.Stack;

import br.com.abevieiramota.model.Bicho;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.Premio;
import br.com.abevieiramota.model.Dezena;

public class Predicao {

	public static class PredicaoBuilder {
		private Predicao predicao;
		private Dezena tipoDezena;

		public PredicaoBuilder(Premio premio, Dezena tipoDezena) {
			checkNotNull(premio);
			checkNotNull(tipoDezena);

			this.predicao = new Predicao(premio);
			this.tipoDezena = tipoDezena;
		}

		public PredicaoBuilder atualizar(Resultado resultado) {
			String valorDoPremio = resultado.premio(this.predicao.premio);
			Bicho bichoDoPremio = Bicho.fromResultado(valorDoPremio, this.tipoDezena);

			Iterator<Resultado> maisVelhosIter = this.predicao.maisVelhos.iterator();

			while (maisVelhosIter.hasNext()) {
				Resultado maisVelhosLoopElem = maisVelhosIter.next();
				Bicho bichoLoopElem = Bicho.fromResultado(maisVelhosLoopElem.premio(this.predicao.premio), this.tipoDezena);
				if (bichoDoPremio.equals(bichoLoopElem)) {
					maisVelhosIter.remove();
				}
			}

			this.predicao.maisVelhos.push(resultado);

			return this;
		}

		public Predicao build() {
			this.predicao.ordenadoPorVelhice = this.predicao.maisVelhos
					.toArray(new Resultado[this.predicao.maisVelhos.size()]);

			return this.predicao;
		}
	}

	private Premio premio;
	private Stack<Resultado> maisVelhos;
	private Resultado[] ordenadoPorVelhice;

	private Predicao(Premio premio) {
		this.premio = premio;
		this.maisVelhos = new Stack<Resultado>();
	}

	public Resultado[] ordenadoPorVelhice() {
		return this.ordenadoPorVelhice;
	}

	public Premio getPremio() {
		return this.premio;
	}

	public Iterable<Resultado> getMaisVelhos() {
		return this.maisVelhos;
	}

}
