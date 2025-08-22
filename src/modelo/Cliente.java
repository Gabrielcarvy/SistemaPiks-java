/**
 * SI - POO - Prof. Fausto Ayres
 *
 */
package modelo;

public class Cliente {
	private int cpf;
	private String nome;
	private Conta conta; // o relacionamento 1-1 implementado

	public Cliente(int cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
	}

	public Cliente(int cpf, String nome, Conta conta) {
		this.cpf = cpf;
		this.nome = nome;
		this.conta = conta;
	}

	public int getCpf() {
		return cpf;
	}

	public void setCpf(int cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
	public String toString() {
		return "Cliente [cpf= " + cpf + ", nome= " + nome + ", contaId= " + conta.getId()) + " ] ";
	}
}
