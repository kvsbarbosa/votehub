package br.com.votehub.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import br.com.votehub.controller.BusinessException;
import br.com.votehub.controller.ControllerProposta;
import br.com.votehub.controller.ControllerVotacao;
import net.miginfocom.swing.MigLayout;

public class ADMCadProposta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fieldTituloCad;
	private JTextField fieldDescricaoCad;
	private JTextField filedIdVotacao;
	private JComboBox<Object> comboBoxNumeroVotacao;
	// private JComboBox<String> comboBoxRespostaCad;

	public ADMCadProposta() {
		setTitle("Cadastro de Proposta");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 501, 381);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(164, 247, 176));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("fill", "[][][][][][][]", "[][][][][][][][]"));
		
		

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		contentPane.add(panel, "cell 2 1 3 6,grow");
		panel.setLayout(new MigLayout("fill", "[][][][][][]", "[][][][][][][][][][][][][][][][]"));

		ImageIcon pp = new ImageIcon("./icons/menu_consulta/con_votacao.png");
		Image ppImg = pp.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		ImageIcon resizedPp = new ImageIcon(ppImg);
		JLabel icon = new JLabel(resizedPp);
		panel.add(icon, "cell 0 1 6 1,alignx center,aligny center");
		
		JLabel lblCadCandidato = new JLabel("Cadastro de Proposta");
		panel.add(lblCadCandidato, "cell 0 2 6 1,alignx center");
		lblCadCandidato.setFont(new Font("Tahoma", Font.BOLD, 22));


		JButton btnVoltarCad = new JButton("VOLTAR");
		btnVoltarCad.setBounds(44, 570, 81, 23);
		btnVoltarCad.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnVoltarCad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ADMCadastro admCadastro = new ADMCadastro();
				admCadastro.setVisible(true);
				dispose();
			}
		});

		JLabel lblCadTitulo = new JLabel("Título:");
		lblCadTitulo.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblCadTitulo, "cell 1 6,alignx trailing");

		fieldTituloCad = new JTextField();
		panel.add(fieldTituloCad, "cell 2 6 2 1,growx");
		fieldTituloCad.setColumns(10);

		JLabel lblCadDescricao = new JLabel("Descrição:");
		lblCadDescricao.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblCadDescricao, "cell 1 8,alignx trailing,aligny baseline");

		// comboBoxRespostaCad = new JComboBox<>(new String[]{"Sim", "Provavelmente
		// sim", "Talvez", "Provavelmente não", "não"});
		// comboBoxRespostaCad.setBounds(248, 200, 359, 20);
		// panel.add(comboBoxRespostaCad, "cell 2 4 6 1,growx");

		fieldDescricaoCad = new JTextField();
		panel.add(fieldDescricaoCad, "cell 2 8 2 1,growx");
		fieldDescricaoCad.setColumns(10);

		JLabel lblCadIdVotacao = new JLabel("Nº da Votação:");
		lblCadIdVotacao.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblCadIdVotacao, "cell 1 10,alignx trailing");

		comboBoxNumeroVotacao = new JComboBox<>();
		comboBoxNumeroVotacao.setBounds(248, 250, 359, 20);
		panel.add(comboBoxNumeroVotacao);
		restaurarIdEleicaoCombobox();
		
		JButton btnCadastrar = new JButton("CADASTRAR");
		btnCadastrar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCadastrar.setPreferredSize(new Dimension(100, 20));
		panel.add(btnCadastrar, "cell 5 14,alignx center,aligny center");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String titulo = fieldTituloCad.getText();
				String descricao = fieldDescricaoCad.getText();
				String idVotacaoText = (String) comboBoxNumeroVotacao.getSelectedItem();
				if (idVotacaoText.isBlank()) {
					JOptionPane.showMessageDialog(null, "todos os campos devem estar preenchidos", "Erro",JOptionPane.ERROR_MESSAGE);
					return;
				}
				int id_votacao = Integer.parseInt(idVotacaoText);

				ControllerProposta contVotante = new ControllerProposta();
				try {
					contVotante.registrarProposta(titulo, descricao, id_votacao);
					JOptionPane.showMessageDialog(null, "Proposta cadastrada com sucesso!");
				} catch (BusinessException error) {
					JOptionPane.showMessageDialog(null, error.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException error2) {
					JOptionPane.showMessageDialog(null, error2.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton btnCadVoltar = new JButton("VOLTAR");
		btnVoltarCad.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnVoltarCad.setPreferredSize(new Dimension(100, 20));
		panel.add(btnVoltarCad, "cell 0 14,alignx center,aligny center");
		btnCadVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ADMCadastro admcadastro = new ADMCadastro();
				admcadastro.setVisible(true);
				dispose();
			}
		});
	}
	public void restaurarIdEleicaoCombobox() {
		try {
			ControllerVotacao contVotacao = new ControllerVotacao();
			ResultSet rs = contVotacao.exibirIdVotacaoProposta();

			while (rs.next()) {
				String id = Integer.toString(rs.getInt("id_votacao"));
				comboBoxNumeroVotacao.addItem(id);
			}
		} catch (SQLException error) {

			JOptionPane.showMessageDialog(null, error.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}

	}

}
