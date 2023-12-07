package br.com.votehub.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.votehub.controller.ControllerProposta;
import br.com.votehub.model.DAOs.DB;

public class ApurarProposta extends JFrame {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement stt = null;
	PreparedStatement stt1 = null;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableResposta;
	private JComboBox<String> comboBoxPropostas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApurarProposta frame = new ApurarProposta();
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
	public ApurarProposta() {

		setBackground(Color.LIGHT_GRAY);
		setForeground(Color.LIGHT_GRAY);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 778, 603);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Resultado da votação de propostas");
		lblNewLabel.setBounds(5, 5, 820, 26);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));
		contentPane.add(lblNewLabel);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(68, 484, 89, 23);
		contentPane.add(btnVoltar);

		JLabel lblProposta = new JLabel("Selecione a proprosta :");
		lblProposta.setBounds(137, 108, 170, 30);
		lblProposta.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblProposta);

		comboBoxPropostas = new JComboBox();
		comboBoxPropostas.setBounds(323, 113, 170, 30);
		contentPane.add(comboBoxPropostas);
		restaurarTituloCombobox();
		comboBoxPropostas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apurarVotosProposta();
            }
        });
		
		DefaultTableModel tableModel = new DefaultTableModel();
		
		tableResposta = new JTable(tableModel);
        tableModel.setColumnIdentifiers(new Object[]{"Número do Candidato", "Nome", "Votos", "Foto"});
        tableResposta.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        tableResposta.setDoubleBuffered(true);
        tableResposta.getColumnModel().getColumn(0).setPreferredWidth(114);
        tableResposta.setBackground(Color.LIGHT_GRAY);
        tableResposta.setBounds(137, 270, 548, 80);
        contentPane.add(tableResposta);
        apurarVotosProposta();
        
        
		JLabel lblNewLabel_1 = new JLabel("Resultados");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(387, 235, 100, 14);
		contentPane.add(lblNewLabel_1);
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Apuracao apvoto = new Apuracao();
				apvoto.setVisible(true);
				dispose();
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

	public void apurarVotosProposta() {
		DefaultTableModel tableModel = (DefaultTableModel) tableResposta.getModel();
		tableModel.setRowCount(0);
		String propostaSelecionada = (String) comboBoxPropostas.getSelectedItem();

		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT\r\n" + "    p.id_proposta,\r\n" + "    p.titulo AS pergunta,\r\n"
					+ "    r.resposta,\r\n" + "    COUNT(r.id_respostaproposta) AS numero_respostas_totais\r\n"
					+ "FROM\r\n" + "    proposta p\r\n" + "LEFT JOIN\r\n"
					+ "    respostaproposta r ON p.id_proposta = r.id_proposta\r\n" + "WHERE\r\n" + "    p.titulo = '"
					+ propostaSelecionada + "'\r\n" + "GROUP BY\r\n" + "    p.id_proposta, p.titulo, r.resposta;");

			while (rs.next()) {
				Vector<Object> row = new Vector<>();
				row.add(rs.getString("id_proposta"));
				row.add(rs.getString("pergunta"));
				row.add(rs.getString("resposta"));
				row.add(rs.getInt("numero_respostas_totais"));
				tableModel.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closestatement(st);
		}
	}
}
