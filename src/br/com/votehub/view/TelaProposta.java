package br.com.votehub.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.votehub.controller.BusinessException;
import br.com.votehub.controller.ControllerProposta;
import br.com.votehub.controller.ControllerRespostaProposta;
import br.com.votehub.model.vo.Votante;

public class TelaProposta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Votante vtt;
	private JComboBox<String> comboBoxPropostas;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaProposta frame = new TelaProposta(vtt);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaProposta(Votante vtt) {
		TelaProposta.vtt = vtt;
		setTitle("votação de proposta");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 5 0 1 3,alignx center,aligny center");
		panel.setPreferredSize(new Dimension(800, 600));
		panel.setLayout(null);

		JLabel lblCadCandidato = new JLabel("Votação de Proposta");
		lblCadCandidato.setBounds(312, 31, 177, 21);
		lblCadCandidato.setFont(new Font("Tahoma", Font.BOLD, 17));
		panel.add(lblCadCandidato);

		JButton btnVoltar = new JButton("VOLTAR");
		btnVoltar.setBounds(51, 547, 100, 30);
		panel.add(btnVoltar);
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaSelectVotacao admPrincipal = new TelaSelectVotacao(null);
				admPrincipal.setVisible(true);
				dispose();
			}
		});
		

		JLabel lblTextProposta = new JLabel("PROPOSTAS ABERTAS:");
		lblTextProposta.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTextProposta.setBounds(222, 169, 168, 30);
		panel.add(lblTextProposta);

		JLabel lblTextResposta = new JLabel("RESPOSTA:");
		lblTextResposta.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTextResposta.setBounds(276, 375, 100, 30);
		panel.add(lblTextResposta);

		comboBoxPropostas = new JComboBox();
		comboBoxPropostas.setBounds(427, 173, 150, 30);
		panel.add(comboBoxPropostas);
		restaurarTituloCombobox();

		JComboBox comboBoxResposta = new JComboBox<>(new String[]{"Sim", "Provavelmente sim", "Talvez", "Provavelmente não", "não"});
		comboBoxResposta.setBounds(427, 379, 150, 30);
		panel.add(comboBoxResposta);
		

		JButton btnDescricao = new JButton("Descrição da Proposta");
		btnDescricao.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnDescricao.setBounds(427, 260, 150, 23);
		panel.add(btnDescricao);
		btnDescricao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tituloSelecionado = (String) comboBoxPropostas.getSelectedItem();
				if (tituloSelecionado != null) {
					try {
						exibirDescricaoProposta(tituloSelecionado);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		JButton btnConfirmar = new JButton("CONFIRMAR");
		btnConfirmar.setBounds(689, 547, 100, 30);
		btnConfirmar.setToolTipText("");
		panel.add(btnConfirmar);
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tituloSelecionado = (String) comboBoxPropostas.getSelectedItem();
				ControllerProposta contProposta = new ControllerProposta();
				String resposta = (String) comboBoxResposta.getSelectedItem();
				int idProposta = 0;
				try {
					idProposta = contProposta.obterIdPorTitulo(tituloSelecionado);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {
					ControllerRespostaProposta contVoto = new ControllerRespostaProposta();
					contVoto.registrarRespostaProposta(resposta, idProposta);
				} catch (BusinessException error) {
					JOptionPane.showMessageDialog(null, error.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
				JOptionPane.showMessageDialog(null, "Resposta cadastrada com sucesso!");
			}
		});
		
	}

	public void restaurarTituloCombobox() {
		try {
			ControllerProposta objProposta = new ControllerProposta();
			ResultSet rs = objProposta.exibirTitulo();
			while (rs.next()) {
				this.comboBoxPropostas.addItem(rs.getString("titulo"));
			}
		} catch (SQLException error) {
			JOptionPane.showMessageDialog(null, error.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void exibirDescricaoProposta(String tituloProposta) throws SQLException {
	    ControllerProposta objProposta = new ControllerProposta();
	    String descricao = objProposta.obterDescricaoPorTitulo(tituloProposta);

	    if (descricao != null) {
	        // Substitui caracteres de quebra de linha do banco de dados por quebras de linha reais
	        descricao = descricao.replace("\\n", "<br>");

	        // Formata o texto como HTML
	        String htmlDesc = "<html><body style='width: 300px;'>" + descricao + "</body></html>";

	        // Exibe a descrição em um JOptionPane com suporte HTML
	        JOptionPane.showMessageDialog(this, htmlDesc, "Descrição da Proposta", JOptionPane.INFORMATION_MESSAGE);
	    } else {
	        JOptionPane.showMessageDialog(this, "Descrição não encontrada para a proposta selecionada.", "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	}
}