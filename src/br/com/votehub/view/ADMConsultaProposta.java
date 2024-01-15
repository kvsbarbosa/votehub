package br.com.votehub.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import br.com.votehub.controller.ControllerVotante;
import br.com.votehub.model.DAOs.DbIntegrityException;
import br.com.votehub.model.vo.Proposta;
import br.com.votehub.model.vo.Votante;
import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
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

import br.com.votehub.controller.BusinessException;
import br.com.votehub.controller.ControllerProposta;

public class ADMConsultaProposta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldId;
	private JTextField textFieldTitulo;
	private JTextField textFieldDescricao;
	private JList<String> list;
    private DefaultListModel<String> listModel;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ADMConsultaProposta frame = new ADMConsultaProposta();
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
	public ADMConsultaProposta() {
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

		ImageIcon cc = new ImageIcon("./icons/menu_consulta/con_pro.png");
		Image ccImg = cc.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		ImageIcon resizedCc = new ImageIcon(ccImg);
		JLabel lbl = new JLabel(resizedCc);
		panelEsquerda.add(lbl, "cell 5 0, alignx center");

		JLabel lblProposta = new JLabel("Candidatos");
		lblProposta.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelEsquerda.add(lblProposta, "cell 5 1,alignx center");
		
		JLabel lblId = new JLabel("ID: ");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelEsquerda.add(lblId, "cell 4 4,alignx right");
		
		textFieldId = new JTextField();
		panelEsquerda.add(textFieldId, "cell 5 4 2 1,growx");
		textFieldId.setColumns(10);
		
		JLabel lblTitulo = new JLabel("Título: ");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelEsquerda.add(lblTitulo, "cell 4 5,alignx right");
		
		textFieldTitulo = new JTextField();
		panelEsquerda.add(textFieldTitulo, "cell 5 5 2 1,growx");
		textFieldTitulo.setColumns(10);
		
		JLabel lblDescricao = new JLabel("Descrição: ");
		lblDescricao.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelEsquerda.add(lblDescricao, "cell 4 6,alignx right");
		
		textFieldDescricao = new JTextField();
		panelEsquerda.add(textFieldDescricao, "cell 5 6 2 1,growx");
		textFieldDescricao.setColumns(10);
		
		JButton btnEditar = new JButton("Editar");
		panelEsquerda.add(btnEditar, "cell 4 11,alignx center,aligny center");
		btnEditar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {

		            if (textFieldId.getText().isEmpty() || textFieldTitulo.getText().isEmpty() || textFieldDescricao.getText().isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de editar.");
		                return;
		            }

		            int id = Integer.parseInt(textFieldId.getText());
		            String novoTitulo = textFieldTitulo.getText();
		            String novaDescricao = textFieldDescricao.getText();

		            ControllerProposta controller = new ControllerProposta();
		            controller.atualizarProposta(id, novoTitulo, novaDescricao);

		            JOptionPane.showMessageDialog(null, "Proposta atualizada com sucesso.");
		        } catch (BusinessException ex) {
		            JOptionPane.showMessageDialog(null, ex.getMessage());
		        }
		    }
		});
		
		JButton btnConsultar = new JButton("Consultar");
		panelEsquerda.add(btnConsultar, "cell 5 11,alignx center");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					int idProposta = Integer.parseInt(textFieldId.getText());
					ControllerProposta controllerProposta = new ControllerProposta();
					Proposta proposta = controllerProposta.buscarProposta(idProposta);

					if (proposta == null) {
						JOptionPane.showMessageDialog(null, "Proposta não encontrada.");
					} else {
						textFieldTitulo.setText(proposta.getTitulo());
						textFieldDescricao.setText(proposta.getDescricao());
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Insira um ID válido.");
				}
			}
		});
		
		JButton btnDeletar = new JButton("Deletar");
		panelEsquerda.add(btnDeletar, "cell 6 11,alignx center");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					if (textFieldId.getText().isEmpty() || textFieldTitulo.getText().isEmpty()
							|| textFieldDescricao.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de deletar.");
						return;
					}

					int id = Integer.parseInt(textFieldId.getText());

					ControllerProposta controller = new ControllerProposta();
					controller.deletarProposta(id);

					textFieldId.setText("");
					textFieldTitulo.setText("");
					textFieldDescricao.setText("");

					JOptionPane.showMessageDialog(null, "Proposta deletada com sucesso.");
				} catch (BusinessException error) {
					JOptionPane.showMessageDialog(null, error.getMessage());
				} catch (DbIntegrityException error2) {
					JOptionPane.showMessageDialog(null, error2.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JButton btnVoltar = new JButton("VOLTAR");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ADMMenuConsulta tela = new ADMMenuConsulta();
				tela.setVisible(true);
				dispose();
			}
		});
		panelEsquerda.add(btnVoltar, "cell 5 14,alignx center");
		
		JPanel panelDireita = new JPanel();
		panelDireita.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelDireita.setBackground(new Color(164, 247, 176));
		getContentPane().add(panelDireita, "cell 1 0,grow");
		panelDireita.setLayout(new MigLayout("fill", "[][][]", "[][][][][][][][][][][][]"));
		
		JLabel lblNewLabel = new JLabel("Propostas Cadastradas:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelDireita.add(lblNewLabel, "cell 1 2,alignx center,aligny center");	
		
		listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setBackground(SystemColor.menu);
        list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelDireita.add(list, "cell 1 3,alignx center,aligny center");
        atualizarListaProposta();
		
	}
	
	 private void atualizarListaProposta() {
		  
	        listModel.clear();

	        ControllerProposta controllerProposta = new ControllerProposta();
	        List<Proposta> propostas = controllerProposta.ExibirPropostas();
	        for (Proposta proposta : propostas) {
	            listModel.addElement("ID : " + proposta.getId_Proposta() + " | Titulo : " + proposta.getTitulo());
	        }
	    }

}
