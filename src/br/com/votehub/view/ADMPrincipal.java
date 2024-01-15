package br.com.votehub.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class ADMPrincipal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ADMPrincipal frame = new ADMPrincipal();
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
	public ADMPrincipal() {
		setTitle("Menu Principal");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 501, 381);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(164, 247, 176));
		contentPane.setBorder(new EmptyBorder(5, 20, 20, 20));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("fill", "[][][][][][][]", "[][][][][][][][][]"));
		
		JLabel lblMenu = new JLabel("Menu Principal");
		contentPane.add(lblMenu, "cell 0 0 7 1,alignx center");
		lblMenu.setFont(new Font("Tahoma", Font.BOLD, 22));
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(panel, "cell 0 1 7 8,grow");
		panel.setLayout(new MigLayout("fill", "[grow][][grow][][grow][][grow]", "[][][][][][][]"));
		
		ImageIcon cad = new ImageIcon("./icons/cadastrar.png");
		Image cadImg = cad.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon resizedCad = new ImageIcon(cadImg);
		JButton btnMenuCadastrar = new JButton("CADASTRAR");
		btnMenuCadastrar.setIcon(resizedCad);
		btnMenuCadastrar.setPreferredSize(new Dimension(220, 100));	
		panel.add(btnMenuCadastrar, "cell 1 1");
		btnMenuCadastrar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMenuCadastrar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnMenuCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ADMCadastro admCadastro = new ADMCadastro();
                admCadastro.setVisible(true);
                dispose();
            }
        });
		
		ImageIcon ap = new ImageIcon("./icons/apurar.png");
		Image apImg = ap.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon resizedap = new ImageIcon(apImg);
		JButton btnMenuApurar = new JButton("APURAR VOTOS");
		btnMenuApurar.setIcon(resizedap);
		btnMenuApurar.setPreferredSize(new Dimension(220, 100));

		panel.add(btnMenuApurar, "cell 3 1");
		btnMenuApurar.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		ImageIcon con = new ImageIcon("./icons/consultar.png");
		Image conImg = con.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon resizedCon = new ImageIcon(conImg);
		JButton btnMenuConsultar = new JButton("CONSULTAR");
		btnMenuConsultar.setIcon(resizedCon);
		btnMenuConsultar.setPreferredSize(new Dimension(220, 100));
		panel.add(btnMenuConsultar, "cell 5 1 2 1");
		btnMenuConsultar.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JButton btnNewButton = new JButton("VOLTAR");
		panel.add(btnNewButton, "cell 1 5,alignx center");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaInicial telaInicial = new TelaInicial();
				telaInicial.setVisible(true);
        		dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMenuConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ADMMenuConsulta consulta = new ADMMenuConsulta();
				consulta.setVisible(true);
				dispose();
			}
		});
		btnMenuApurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Apuracao apurar = new Apuracao();
				apurar.setVisible(true);
				dispose();
			}
		});
		
		JButton espaco = new JButton("CONSULTAR VOTANTE");
		espaco.setPreferredSize(new Dimension(220, 100));
		panel.add(espaco, "cell 1 3,growx,aligny center");
		espaco.setVisible(false);
	}

}
