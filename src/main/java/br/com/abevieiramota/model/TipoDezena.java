package br.com.abevieiramota.model;

import static com.google.common.base.Preconditions.checkNotNull;

public enum TipoDezena {
	PRIMEIRA(0, 2), SEGUNDA(1, 3), TERCEIRA(2, 4);
	
	private int beginIndex;
	private int endIndex;
	private TipoDezena(int beginIndex, int endIndex) {
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
	}
	
	public String extract(String milhar) {
		checkNotNull(milhar);
		
		return milhar.substring(this.beginIndex, this.endIndex);
	}
}