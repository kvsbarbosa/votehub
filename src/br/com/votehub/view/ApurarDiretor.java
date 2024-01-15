package br.com.votehub.view;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.votehub.controller.ControllerCandidato;
import br.com.votehub.model.DAOs.DB;
import net.miginfocom.swing.MigLayout;

public class ApurarDiretor extends JFrame {

    private static final long serialVersionUID = 1L;

    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement stt = null;
    PreparedStatement stt1 = null;

    private JPanel contentPane;
    private JTable table;
    private JComboBox<String> comboBoxVotacao;
    private DefaultTableModel tableModel = new DefaultTableModel();
    private JLabel lblNrVotos = new JLabel();
    private String idVotacaoSelecionada;

    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApurarDiretor frame = new ApurarDiretor();
			        frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    public ApurarDiretor() {
        setTitle("Apuração do Diretor");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(164, 247, 176));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("fill", "[grow]", "[][][][][][][][][][][][]"));

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        contentPane.add(panel, "alignx center,aligny center");
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setLayout(new MigLayout("fill", "[grow]", "[][][][][][][][][][][][]"));

        JLabel lblTitulo = new JLabel("Resultado da votação de diretor");
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 21));
        panel.add(lblTitulo, "cell 0 0, growx, wrap");

        JLabel lblVotacao = new JLabel("Selecione a votação:");
        lblVotacao.setFont(new Font("Tahoma", Font.BOLD, 12));
        panel.add(lblVotacao, "cell 0 2, alignx center, wrap");

        comboBoxVotacao = new JComboBox<>();
        panel.add(comboBoxVotacao, "cell 0 2, alignx center, wrap");
        comboBoxVotacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idVotacaoSelecionada = (String) comboBoxVotacao.getSelectedItem();
                apurarVotosDiretor();
                atualizarNumeroVotos(idVotacaoSelecionada);
            }
        });
        restaurarvotacaoCombobox();

        lblNrVotos = new JLabel("Número total de votos:");
        lblNrVotos.setFont(new Font("Tahoma", Font.BOLD, 12));
        panel.add(lblNrVotos, "cell 0 4, alignx center, wrap");

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.setColumnIdentifiers(new Object[] { "Número do Candidato", "Nome", "Votos" });
        table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        table.setDoubleBuffered(true);
        table.getColumnModel().getColumn(0).setPreferredWidth(114);
        table.setBackground(Color.LIGHT_GRAY);
        panel.add(table, "cell 0 6, grow, span, wrap");

        apurarVotosDiretor();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, "cell 0 8, grow, span, wrap");

        JButton btnVoltar = new JButton("VOLTAR");
        panel.add(btnVoltar, "cell 0 10, alignx center, wrap");
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Apuracao apvoto = new Apuracao();
                apvoto.setVisible(true);
                dispose();
            }
        });
    }

    public void apurarVotosDiretor() {
        if (tableModel == null) {
            tableModel = new DefaultTableModel();
        }
        tableModel.setRowCount(0);
        try {
            conn = DB.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT\r\n" + "	  candidato.numero_candidato,\r\n"
                    + "	  candidato.nome AS nome_candidato,\r\n"
                    + "	  COUNT(voto.id_voto) AS numero_de_votos\r\n" + "	  FROM\r\n"
                    + "		    candidato\r\n" + "	  LEFT JOIN\r\n"
                    + "		    voto ON candidato.numero_candidato = voto.numero_candidato\r\n" + "	  WHERE\r\n"
                    + "		    candidato.cargo = 'diretor'\r\n" + "   AND candidato.id_votacao = "
                    + idVotacaoSelecionada + "\r\n" + "	  GROUP BY\r\n"
                    + "		    candidato.numero_candidato, candidato.nome\r\n" + "	  ORDER BY\r\n"
                    + "		    numero_de_votos DESC;");

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("numero_candidato"));
                row.add(rs.getString("nome_candidato"));
                row.add(rs.getInt("numero_de_votos"));
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(rs);
            DB.closestatement(st);
        }
    }

    public void atualizarNumeroVotos(String idVotacao) {
        try {
            conn = DB.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT\r\n" + "    COUNT(voto.id_voto) AS numero_total_votos\r\n" + "FROM\r\n"
                    + "    voto\r\n" + "LEFT JOIN\r\n"
                    + "    candidato ON voto.numero_candidato = candidato.numero_candidato\r\n" + "WHERE\r\n"
                    + "    candidato.cargo = 'diretor' AND candidato.id_votacao = " + idVotacao + ";");

            if (rs.next()) {
                int numeroTotalVotos = rs.getInt("numero_total_votos");
                lblNrVotos.setText("Número total de votos na Votação de Diretor: " + numeroTotalVotos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(rs);
            DB.closestatement(st);
        }
    }

    public void restaurarvotacaoCombobox() {
        try {
            ControllerCandidato objCand = new ControllerCandidato();
            comboBoxVotacao.removeAllItems();
            ResultSet rs = objCand.exibirDiretor();
            while (rs.next()) {
                this.comboBoxVotacao.addItem(rs.getString("id_votacao"));
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
