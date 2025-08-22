/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POO
 * Prof. Fausto Maranh�o Ayres
 **********************************/
package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import modelo.Cliente;
import modelo.Conta;
import modelo.ContaEspecial;
import modelo.Lancamento;

public class Repositorio {
	private static TreeMap<String, Conta> contasPIKS = new TreeMap<String, Conta>();
	private static TreeMap<Integer, Cliente> clientesCPF = new TreeMap<Integer, Cliente>();

	static {
		lerObjetos();
	}

	public static void adicionarConta(Conta conta) {
		contasPIKS.put(conta.getChavePiks(), conta);
	}

	public static void removerConta(Conta conta) throws Exception {
		if (conta.getSaldo() != 0) {
			throw new Exception("Esta Conta não pode ser removida por está com saldo diferente de 0");
		}
		contasPIKS.remove(conta.getChavePiks());
	}

	public static Conta localizarConta(String chave) {
		for (Conta c : contasPIKS.values()) {
			if (c.getChavePiks().equals(chave)) {
				return c;
			}
		}

		return null;
	}

	public static void adicionarCliente(Cliente cliente) {
		clientesCPF.put(cliente.getCpf(), cliente);
	}

	public static void removerCliente(Cliente c) {
		clientesCPF.remove(c.getCpf());
	}

	public static Cliente localizarCliente(int cpf) {
		return clientesCPF.get(cpf);
	}

	public static ArrayList<Conta> getContas() {
		return new ArrayList<>(contasPIKS.values());
	}

	public static ArrayList<Cliente> getClientes() {
		return new ArrayList<>(clientesCPF.values());
	}

	public static void lerObjetos() {
		try {
			// caso os arquivos nao existam, serao criados vazios
			File f1 = new File(new File(".\\contasPIKS.csv").getCanonicalPath());
			File f2 = new File(new File(".\\lancamentos.csv").getCanonicalPath());
			if (!f1.exists() || !f2.exists()) {
				System.out.println("criando arquivo .csv vazio");
				FileWriter arqconta = new FileWriter(f1);
				FileWriter arqlancamento = new FileWriter(f2);
				arqconta.close();
				arqlancamento.close();
				return;
			}
		} catch (Exception ex) {
			throw new RuntimeException("criacao dos arquivos vazios:" + ex.getMessage());
		}

		try {
			int id;
			double saldo, limite;
			String linha;
			String[] partes;
			Cliente cliente;
			Conta conta;
			String chave, nome;
			int cpf;

			File f1 = new File(new File(".\\contasPIKS.csv").getCanonicalPath());
			Scanner arqconta = new Scanner(f1);
			System.out.println("Repositorio - lendo objetos...");
			while (arqconta.hasNextLine()) {
				linha = arqconta.nextLine().trim();
				partes = linha.split(";");
				// System.out.println(Arrays.toString(partes));
				id = Integer.parseInt(partes[0]);
				chave = partes[1];
				saldo = Double.parseDouble(partes[2]);
				limite = Double.parseDouble(partes[3]); // <>0=ContaEspecial
				cpf = Integer.parseInt(partes[4]);
				nome = partes[5];
				cliente = new Cliente(cpf, nome);
				if (limite == 0.0)
					conta = new Conta(id, chave, saldo);
				else
					conta = new ContaEspecial(id, chave, saldo, limite);

				cliente.setConta(conta);
				conta.setCliente(cliente);
				adicionarConta(conta);
				adicionarCliente(cliente);
			}
			arqconta.close();

			Lancamento lanc;
			LocalDateTime datahora;
			double valor;
			String tipo;
			File f2 = new File(new File(".\\lancamentos.csv").getCanonicalPath());
			Scanner arqlan = new Scanner(f2);
			while (arqlan.hasNextLine()) {
				linha = arqlan.nextLine().trim();
				partes = linha.split(";");
				// System.out.println(Arrays.toString(partes));
				chave = partes[0];
				datahora = LocalDateTime.parse(partes[1], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
				valor = Double.parseDouble(partes[2]);
				tipo = partes[3];
				lanc = new Lancamento(datahora, valor, tipo);
				conta = localizarConta(chave);
				conta.adicionar(lanc);
			}
			arqlan.close();
		} catch (Exception ex) {
			throw new RuntimeException("leitura arquivo de contasPIKS:" + ex.getMessage());
		}

	}

	public static void gravarObjetos() {
		// gravar nos arquivos csv os objetos que est�o no reposit�rio
		try {
			File f1 = new File(new File(".\\contasPIKS.csv").getCanonicalPath());
			FileWriter arqconta = new FileWriter(f1);
			File f2 = new File(new File(".\\lancamentos.csv").getCanonicalPath());
			FileWriter arqlan = new FileWriter(f2);
			String linha;
			Double limite;
			System.out.println("Repositorio - gravando objetos...");
			for (Conta cta : contasPIKS.values()) {
				if (cta instanceof ContaEspecial esp)
					limite = esp.getLimite();
				else
					limite = 0.0;
				linha = cta.getId() + ";" + cta.getChavePiks() + ";" + cta.getSaldo() + ";" + limite + ";"
						+ cta.getCliente().getCpf() + ";" + cta.getCliente().getNome();
				arqconta.write(linha + "\n");
				// System.out.println("linha="+linha);

				if (!cta.getLancamento().isEmpty())
					for (Lancamento lan : cta.getLancamento()) {
						String s = lan.getDatahora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
						arqlan.write(cta.getChavePiks() + ";" + s + ";" + lan.getValor() + ";" + lan.getTipo() + "\n");
					}

			}
			arqconta.close();
			arqlan.close();

		} catch (Exception e) {
			throw new RuntimeException("problema na cria��o do arquivo  contasPIKS " + e.getMessage());
		}

	}
}
