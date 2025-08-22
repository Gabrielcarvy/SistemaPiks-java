package modelo;

import java.time.LocalDateTime;

public class Lancamento {
	private LocalDateTime datahora;
	private double valor;
	private String tipo;

	public Lancamento(LocalDateTime datahora, double valor, String tipo) throws Exception {
		boolean tipoDebitoCredito = tipo.equals("+") || tipo.equals("-");
		if (!tipoDebitoCredito) {
			throw new Exception("O tipo da operação só pode ser (+) [credito] ou (-) [débito]");
		}
		this.datahora = datahora;
		this.valor = valor;
		this.tipo = tipo;

	}

	public LocalDateTime getDatahora() {
		return datahora;
	}

	public double getValor() {
		return valor;
	}

	public String getTipo() {
		return tipo;
	}

}