/**
 * SI - POO - Prof. Fausto Ayres
 *
 */
package modelo;

import java.time.LocalDateTime;

public class ContaEspecial extends Conta {
	private double limite;

	public ContaEspecial(int id, String chavePiks, double saldo, Cliente cliente, double limite) {
		super(id, chavePiks, saldo, cliente);
		this.limite = limite;
	}

	public ContaEspecial(int id, String chavePiks, double saldo, double limite) {
		super(id, chavePiks, saldo);
		this.limite = limite;
	}

	public ContaEspecial(int id, String chavePiks, double limite) {
		super(id, chavePiks);
		this.limite = limite;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}

	@Override
	public void debitar(double valor) throws Exception {
		if (valor > (getSaldo() + this.limite)) {
			throw new Exception("Saldo insuficiente");
		}
		setSaldo(getSaldo() - valor);
		Lancamento lancar = new Lancamento(getChavePiks(), LocalDateTime.now(), valor, "-");
		getLancamento().add(lancar);
	}

	@Override
	public String toString() {
		return "Conta Especial [id: " + getId() + ", chavepiks: " + getChavePiks() + ", saldo: " + getSaldo() + ", limite: " + limite
				+ ", cpf: " + getCliente().getCpf() + ", nome: " + getCliente().getNome() + "]";
	}

}
