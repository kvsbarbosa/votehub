package br.com.votehub;

import java.util.Scanner;

import br.com.votehub.model.DAOs.VotoDAO;
import br.com.votehub.model.DAOs.candidatoDAO;
import br.com.votehub.model.DAOs.votanteDAO;
import br.com.votehub.model.criptografia.CriptografiaVotante;
import br.com.votehub.model.vo.Candidato;
import br.com.votehub.model.vo.Votante;
import br.com.votehub.model.vo.Voto;

public class Main {

	public static void main(String[] args) {
		System.out.println(CriptografiaVotante.obterChaveSecreta());

		Main programa = new Main();
		programa.operação();
	}

	public void operação() {
		Scanner sc = new Scanner(System.in);
		System.out.println("operação 1: adicionar votante");
		System.out.println("operação 2: adicionar candidato");
		System.out.println("operação 3: adicionar voto");
		int op = sc.nextInt();
				if(op == 1) {
					addVotante();
				}
				if(op == 2) {
					addCandidato();
				}
				if(op == 3) {
					addVoto();
				}else {
					System.out.println("digite uma operação valida");
				}
		
	}

	
	
	private void addVotante() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Digite o id do votante: ");
		int id = sc.nextInt();
		System.out.print("Digite o nome do votante: ");
		String nome = sc.next();
		System.out.print("Digite o cpf do votante: ");
		String cpf = sc.next();
		System.out.print("Digite a ocupação do votante: ");
		String ocupacao = sc.next();

		Votante v = new Votante(id, nome, cpf, ocupacao);

		votanteDAO vdao = new votanteDAO();
		vdao.addVotante(v);
		sc.close();
	}

	private void addCandidato() {

		Scanner sc = new Scanner(System.in);
		System.out.println("Nome do candidato:");
		String nome = sc.next();
		System.out.println("ID do candidato:");
		int id = sc.nextInt();
		System.out.println("Cargo do candidato:");
		String cargo = sc.next();

		Candidato c = new Candidato(nome, id, cargo);
		candidatoDAO cdao = new candidatoDAO();
		cdao.addCandidato(c);
		sc.close();

	}

	private void addVoto() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Digite o id do voto: ");
		int id = sc.nextInt();
		System.out.print("Digite o id do candidato a ser votado: ");
		int id_candidato = sc.nextInt();
		System.out.print("Digite o id do vontate que vai votar: ");
		int id_votante = sc.nextInt();

		Voto vt = new Voto(id, id_candidato, id_votante);

		VotoDAO vtdao = new VotoDAO();
		vtdao.addVoto(vt);
		sc.close();
	}

}
