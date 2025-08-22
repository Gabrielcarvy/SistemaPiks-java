/**
 * SI - POO - Prof. Fausto Ayres
 *
 */

package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Conta {
	private int id;
	private String chavePiks;
	private double saldo;
	private Cliente cliente;
	private ArrayList<Lancamento> lancamento = new ArrayList<Lancamento>();

	public Conta(int id, String chavePiks, double saldo, Cliente cliente) {
		this.id = id;
		this.chavePiks = chavePiks;
		this.saldo = saldo;
		this.cliente = cliente;
	}

	public Conta(int id, String chavePiks, double saldo) {
		this.id = id;
		this.chavePiks = chavePiks;
		this.saldo = saldo;x
	}

	public Conta(int id, String chavePiks) {
		this.id = id;
		this.chavePiks = chavePiks;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChavePiks() {
		return chavePiks;
	}

	public void setChavePiks(String chavePiks) {
		this.chavePiks = chavePiks;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) throws Exception {
		if (saldo < 0) {
			throw new Exception("Saldo insuficiente"); 
			// lancei essa exeção pois vira um método inválido se o saldo for
			// menor que 0 ne
		}
		this.saldo = saldo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ArrayList<Lancamento> getLancamento() {
		return lancamento;
	}

	public void debitar(double valor) throws Exception {
		if (valor > this.saldo) {
			throw new Exception("Saldo da conta está insuficiente.");
		}
		this.saldo -= valor;
		Lancamento lancar = new Lancamento(chavePiks, LocalDateTime.now(), valor, "-");
		lancamento.add(lancar);

	}

	public void creditar(double valor) {
		this.saldo += valor;
		Lancamento lancar = new Lancamento(chavePiks, LocalDateTime.now(), valor, "+");
		lancamento.add(lancar);

	}
	
	public void adicionar(Lancamento lancar) throws Exception {
		if (lancar == null) {
			throw new Exception("O lançamento foi inválidado");
		}
		this.lancamento.add(lancar);
	}


	public void transferir(Conta destino, double valor) throws Exception {
		if (destino == null) {
			throw new Exception("A conta destinária ela não pode ser nula.");
		}
		if (this == destino) { /* this está se referindo a conta dela mesmo */
			throw new Exception("Não é possível tranferir para a mesma conta");
		}
		if (valor <= 0) {
			throw new Exception("O valor que será transferido não pode ser zero e nem negatio.");

		}
		// verificar se o destino é nulo, dps se nao ta trasferindo pra msm conta e os
		// valores da conta
		this.debitar(valor);
		try {
			destino.creditar(valor);
		} catch (Exception e) {
			this.creditar(valor); // devolveremos o dinheiro
			throw new Exception("Falha de transferência de valor para outra conta." + e.getMessage());

		}
		// execeção ao tentar debitar o valor, porém se falhar devolve o debito para
		// conta
		// porém como o sistema é simples, não incrementei a exceção para o this debitar

	}

	
	@Override
	public String toString() {
		return "Conta [id: " + id + ", chavePiks: " + chavePiks + ", saldo: " + saldo + ", cpf: " + cliente.getCpf()
				+ ", nome: " + cliente.getNome() + "]";
	}

}
