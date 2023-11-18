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

		Main programa = new Main();
		programa.operação();
	}

	public void operação() {
		Scanner sc = new Scanner(System.in);
		System.out.println("operação 1: adicionar votante");
		System.out.println("operação 2: adicionar candidato");
		System.out.println("operação 3: adicionar voto");
		System.out.println("operação 4: mostrar votantes");
		System.out.print("digite a operação a ser realizada: ");
		int op = sc.nextInt();
		if (op == 1) {
			addVotante();
		}
		if (op == 2) {
			addCandidato();
		}
		if (op == 3) {
			addVoto();
		}if (op == 4) {
			mostrarVotantes();
		} else {
			System.out.println("digite uma operação valida");
		}
		sc.close();

	}

	private void addVotante() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Digite o id do votante: ");
		int id_votante = sc.nextInt();
		System.out.print("Digite o nome do votante: ");
		String matricula = sc.next();
		System.out.print("Digite o cpf do votante: ");
		String nome = sc.next();
		System.out.print("Digite a senha do votante: ");
		String senha = sc.next();
		System.out.print("Digite a ocupação do votante: ");
		String ocupação = sc.next();

		Votante v = new Votante(id_votante, matricula, nome, senha, ocupação);

		votanteDAO vdao = new votanteDAO();
		vdao.addVotante(v);
		sc.close();
	}

	private void addCandidato() {

		Scanner sc = new Scanner(System.in);
		System.out.println("digite o Numero do candidato:");
		String numero_candidato = sc.next();
		System.out.println("digite o nome do candidato:");
		String nome = sc.next();
		System.out.println("digite o Cargo do candidato:");
		String cargo = sc.next();

		Candidato c = new Candidato(numero_candidato, nome, cargo);
		candidatoDAO cdao = new candidatoDAO();
		cdao.addCandidato(c);
		sc.close();

	}

	private void addVoto() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Digite o id do voto: ");
		int id_voto = sc.nextInt();
		System.out.print("Digite o id do candidato a ser votado: ");
		String numero_candidato = sc.next();
		System.out.print("Digite o id do vontate que vai votar: ");
		int id_votante = sc.nextInt();

		Voto vt = new Voto(id_voto, numero_candidato, id_votante);

		VotoDAO vtdao = new VotoDAO();
		vtdao.addVoto(vt);
		sc.close();
	}

	private void mostrarVotantes() {
		votanteDAO cd = new votanteDAO();
		cd.mostrarVotantes();
	
	}

}
