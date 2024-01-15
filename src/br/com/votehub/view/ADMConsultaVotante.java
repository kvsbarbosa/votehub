package br.com.votehub.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.votehub.model.vo.Proposta;
import br.com.votehub.model.vo.Votante;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import br.com.votehub.controller.BusinessException;
import br.com.votehub.controller.ControllerProposta;
import br.com.votehub.controller.ControllerVotante;
import br.com.votehub.model.DAOs.DbIntegrityException;
import br.com.votehub.model.DAOs.VotanteDAO;
import javax.swing.JList;
import javax.swing.border.BevelBorder;

public class ADMConsultaVotante extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldId;
	private JTextField textFieldNome;
	private JTextField textFieldMatricula;
	private JList<String> list;
	private DefaultListModel<String> listModel;

	public ADMConsultaVotante() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		getContentPane().setLayout(new MigLayout("fill", "[409px][383px]", "[487px]"));

		JPanel panelEsquerda = new JPanel();
		panelEsquerda.setBackground(SystemColor.menu);
		getContentPane().add(panelEsquerda, "cell 0 0,grow");
		panelEsquerda
				.setLayout(new MigLayout("fill", "[][][][][][][][][][][][]", "[][][][][][][][][][][][][][][][][]"));

		ImageIcon cc = new ImageIcon("./icons/menu_consulta/con_vot.png");
		Image ccImg = cc.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		ImageIcon resizedCc = new ImageIcon(ccImg);
		JLabel lbl = new JLabel(resizedCc);
		panelEsquerda.add(lbl, "cell 5 0, alignx center");

		JLabel lblVotantes = new JLabel("Votantes");
		lblVotantes.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelEsquerda.add(lblVotantes, "cell 5 1,alignx center");

		JLabel lblMatricula = new JLabel("Matrícula");
		lblMatricula.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelEsquerda.add(lblMatricula, "cell 4 4,alignx right");

		textFieldMatricula = new JTextField();
		panelEsquerda.add(textFieldMatricula, "cell 5 4 2 1,growx");
		textFieldMatricula.setColumns(10);

		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelEsquerda.add(lblId, "cell 4 5,alignx right");

		textFieldId = new JTextField();
		panelEsquerda.add(textFieldId, "cell 5 5 2 1,growx");
		textFieldId.setColumns(10);
		textFieldId.setEditable(false);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelEsquerda.add(lblNome, "cell 4 6,alignx right");

		textFieldNome = new JTextField();
		panelEsquerda.add(textFieldNome, "cell 5 6 2 1,growx");
		textFieldNome.setColumns(10);

		JButton btnEditar = new JButton("Editar");
		panelEsquerda.add(btnEditar, "cell 4 11,alignx center,aligny center");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					if (textFieldId.getText().isEmpty() || textFieldMatricula.getText().isEmpty()
							|| textFieldNome.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de editar.");
						return;
					}

					int idVotante = Integer.parseInt(textFieldId.getText());
					String novaMatricula = textFieldMatricula.getText();
					String novoNome = textFieldNome.getText();

					ControllerVotante controller = new ControllerVotante();
					controller.atualizarVotante(idVotante, novaMatricula, novoNome);

					JOptionPane.showMessageDialog(null, "Votante atualizado com sucesso.");
				} catch (BusinessException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				} catch (SQLException e1) {
					JOptionPane.showConfirmDialog(null, e1.getMessage());
				}
			}
		});

		JButton btnConsultar = new JButton("Consultar");
		panelEsquerda.add(btnConsultar, "cell 5 11,alignx center");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String matricula = textFieldMatricula.getText();
				ControllerVotante controllerVotante = new ControllerVotante();
				Votante votante = controllerVotante.buscarVotante(matricula);

				if (votante == null) {
					JOptionPane.showMessageDialog(null, "Votante não encontrado.");
				} else {
					textFieldId.setText(Integer.toString(votante.getId_votante()));
					textFieldNome.setText(votante.getNome());
//		            textFieldSenha.setText(votante.getSenha());
				}
			}
		});

		JButton btnDeletar = new JButton("Deletar");
		panelEsquerda.add(btnDeletar, "cell 6 11,alignx center");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					if (textFieldId.getText().isEmpty() || textFieldMatricula.getText().isEmpty()
							|| textFieldNome.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de deletar.");
						return;
					}

					int idVotante = Integer.parseInt(textFieldId.getText());

					ControllerVotante controller = new ControllerVotante();
					controller.deletarVotante(idVotante);

					textFieldMatricula.setText("");
					textFieldNome.setText("");
					textFieldId.setText("");
//		            textFieldSenha.setText("");

					JOptionPane.showMessageDialog(null, "Votante deletado com sucesso.");
				} catch (BusinessException error) {
					JOptionPane.showMessageDialog(null, "O votante não pode ser deletado pois já votou.");
				} catch (DbIntegrityException error2) {
					JOptionPane.showMessageDialog(null, error2.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton btnVoltar = new JButton("VOLTAR");
		panelEsquerda.add(btnVoltar, "cell 5 14,alignx center");

		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ADMMenuConsulta consulta = new ADMMenuConsulta();
				consulta.setVisible(true);
				dispose();
			}
		});

		JPanel panelDireita = new JPanel();
		panelDireita.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelDireita.setBackground(new Color(164, 247, 176));
		getContentPane().add(panelDireita, "cell 1 0,grow");
		panelDireita.setLayout(new MigLayout("fill", "[][][]", "[][][][][][][][][][][][]"));

		listModel = new DefaultListModel<>();

		JLabel lblNewLabel = new JLabel("Votantes Cadastrados");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelDireita.add(lblNewLabel, "cell 1 2,alignx center,aligny center");
		list = new JList<>(listModel);
		list.setBackground(SystemColor.menu);
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelDireita.add(list, "cell 1 3,alignx center,aligny center");
		atualizarListaVotantes();
	}

	private void atualizarListaVotantes() {

		listModel.clear();

		ControllerVotante controllerVotante = new ControllerVotante();
		List<Votante> votantes = controllerVotante.ExibirVotantes();
		for (Votante votante : votantes) {
			listModel.addElement("ID: " + votante.getId_votante() + " | Nome: " + votante.getNome());
		}
	}
}