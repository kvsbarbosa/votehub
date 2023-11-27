package br.com.votehub.view;

import javax.swing.JFrame;
import java.time.LocalDate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;

public class TelaVotacao {

	private JFrame frame;
	String ano = Integer.toString(LocalDate.now().getYear());
	private JButton botaoCancelar;
	private JButton botaoAvancar;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	private JComboBox comboBox_3;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;

	// CHAMANDO A TELA -> SwingUtilities.invokeLater(TelaVotacao::new);
	
	public TelaVotacao() {
		try {
			initialize();
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setVisible(true);
      } catch (Exception e) {
          e.printStackTrace();
          JOptionPane.showMessageDialog(null, "Erro ao inicializar a Tela de Votação: " + e.getMessage());
      }
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("fill", "[][][grow][][][20%,grow][grow][][grow][][]", "[][][][][][][][]"));
		
		botaoAvancar = new JButton("Avançar");
		botaoAvancar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		botaoAvancar.setHorizontalAlignment(SwingConstants.RIGHT);
		botaoAvancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		botaoCancelar = new JButton("  Cancelar");
		botaoCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		botaoCancelar.setHorizontalAlignment(SwingConstants.LEFT);
		botaoCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				SwingUtilities.invokeLater(LoginUsuario::new);
				frame.dispose();
			}
		});
		
		lblNewLabel = new JLabel("REITOR:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		frame.getContentPane().add(lblNewLabel, "cell 4 2 2 1,alignx center");
		
		comboBox = new JComboBox();
		frame.getContentPane().add(comboBox, "cell 6 2,growx");
		
		lblNewLabel_1 = new JLabel("DIRETOR:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		frame.getContentPane().add(lblNewLabel_1, "cell 4 3 2 1,alignx center");
		
		comboBox_1 = new JComboBox();
		frame.getContentPane().add(comboBox_1, "cell 6 3,growx");
		
		lblNewLabel_2 = new JLabel("DA:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		frame.getContentPane().add(lblNewLabel_2, "cell 4 4 2 1,alignx center");
		
		comboBox_2 = new JComboBox();
		frame.getContentPane().add(comboBox_2, "cell 6 4,growx");
		
		lblNewLabel_3 = new JLabel("New label");
		frame.getContentPane().add(lblNewLabel_3, "cell 4 5");
		
		comboBox_3 = new JComboBox();
		frame.getContentPane().add(comboBox_3, "cell 6 5,growx");
		frame.getContentPane().add(botaoCancelar, "flowx,cell 4 7,alignx center,aligny baseline");
		frame.getContentPane().add(botaoAvancar, "cell 6 7,alignx center,aligny baseline");
	}

}
