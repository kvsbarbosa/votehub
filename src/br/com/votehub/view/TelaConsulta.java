package br.com.votehub.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;

public class TelaConsulta extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConsulta frame = new TelaConsulta();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaConsulta() {
		setTitle("Menu Consulta");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 501, 381);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("fill", "[grow][][][][][][][][][][grow][][grow]", "[][][][][][][][]"));

		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 5 0 1 3,alignx center,aligny center");
		panel.setPreferredSize(new Dimension(800, 600));
		panel.setLayout(
				new MigLayout("fill", "[grow][][][grow][][grow]", "[][][][][][][][][][][][][][][][][][][][][][]"));

		JLabel lblConsulta = new JLabel("Consulta");
		lblConsulta.setFont(new Font("Tahoma", Font.BOLD, 17));
		panel.add(lblConsulta, "cell 3 0,alignx center,aligny center");

		JButton btnCadVoltar = new JButton("VOLTAR");
		btnCadVoltar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCadVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ADMPrincipal admPrincipal = new ADMPrincipal();
				admPrincipal.setVisible(true);
				dispose();
			}
		});

		JButton btnConsultaEleitor = new JButton("CONSULTAR ELEITOR");
		btnConsultaEleitor.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnConsultaEleitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ADMConsultaEleitor consulta = new ADMConsultaEleitor();
				consulta.setVisible(true);
				dispose();

			}
		});

		panel.add(btnConsultaEleitor, "cell 3 3,growx,aligny center");

		JButton btnConsultarCandidato = new JButton("CONSULTAR CANDIDATO");
		btnConsultarCandidato.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnConsultarCandidato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ADMConsultaCandidato consulta = new ADMConsultaCandidato();
				consulta.setVisible(true);
				dispose();
			}
		});
		panel.add(btnConsultarCandidato, "cell 3 6,growx,aligny center");

		JButton btnConsultarEleicao = new JButton("CONSULTAR ELEIÇÃO");
		btnConsultarEleicao.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnConsultarEleicao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				ADMConsultaVotacao votacao;
//				try {
//					votacao = new ADMConsultaVotacao();
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				votacao.setVisible(true);
//				dispose();
			}
		});
		panel.add(btnConsultarEleicao, "cell 3 10,growx,aligny center");

		JButton btnNewButton = new JButton("CONSULTAR PROPOSTA");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ADMConsultaProposta proposta = new ADMConsultaProposta();
				proposta.setVisible(true);
				dispose();
			}
		});

		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(btnNewButton, "cell 3 14,growx,aligny center");
		panel.add(btnCadVoltar, "cell 3 21,alignx center,aligny center");

	}

}
