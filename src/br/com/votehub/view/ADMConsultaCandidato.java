package br.com.votehub.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.votehub.controller.BusinessException;
import br.com.votehub.controller.ControllerCandidato;
import br.com.votehub.controller.ControllerVotante;
import br.com.votehub.model.DAOs.DbIntegrityException;
import br.com.votehub.model.vo.Candidato;
import br.com.votehub.model.vo.Votante;
import net.miginfocom.swing.MigLayout;

public class ADMConsultaCandidato extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNumeroCandidato;
	private JTextField textFieldNome;
	private JTextField textFieldCargo;
	private JTextField textFieldVotacao;
	private JLabel lblImg;
	private String img_candidato;
	private JList<String> list;
    private DefaultListModel<String> listModel;

	public ADMConsultaCandidato() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 558, 398);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(164, 247, 176));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("fill", "[grow][][][][][][][][][][grow][][grow]", "[][][][][][][][]"));
		
		 listModel = new DefaultListModel<>();
	        
	        JLabel lblTxtGeral = new JLabel("Candidatos cadastrados :");
	        lblTxtGeral.setFont(new Font("Tahoma", Font.BOLD, 12));
	        contentPane.add(lblTxtGeral, "cell 3 2");
	        list = new JList<>(listModel);
	        list.setBackground(SystemColor.menu);
	        list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	        contentPane.add(list, "cell 4 0 1 3,alignx center,aligny center");
	        atualizarListaCandidatos();

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		contentPane.add(panel, "cell 1 0 1 3,alignx center,aligny center");
		panel.setPreferredSize(new Dimension(800, 600));
		panel.setLayout(new MigLayout("fill", "[][grow][][][][][grow][][][][grow][][][][][]",
				"[][][][][][][][][][][][][][][][][][][][][][][][][][][][]"));

		JLabel lblTitulo = new JLabel("Candidatos");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 17));
		panel.add(lblTitulo, "cell 6 1,alignx center");

		JLabel lblNumeroCandidato = new JLabel("N° Candidato");
		lblNumeroCandidato.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lblNumeroCandidato, "cell 2 7,alignx right");
		textFieldNumeroCandidato = new JTextField();
		panel.add(textFieldNumeroCandidato, "cell 5 7 3 1,growx");

		JLabel lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lblNome, "cell 2 9,alignx right");
		textFieldNome = new JTextField();
		panel.add(textFieldNome, "cell 5 9 3 1,growx");

		JLabel lblCargo = new JLabel("Cargo");
		lblCargo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lblCargo, "cell 2 11,alignx right");
		textFieldCargo = new JTextField();
		panel.add(textFieldCargo, "cell 5 11 4 1,growx");

		JLabel lblVotacao = new JLabel("ID Votação");
		lblVotacao.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lblVotacao, "cell 2 13,alignx right");

		URL resource = ADMConsultaCandidato.class.getClassLoader().getResource("icons8-câmera-100.png");
		textFieldVotacao = new JTextField();
		textFieldVotacao.setEditable(false);
		panel.add(textFieldVotacao, "cell 5 13,growx");
		lblImg = new JLabel("");
		lblImg.setIcon(new ImageIcon(resource));
		panel.add(lblImg, "cell 6 15,alignx center");

		JButton btnAddImg = new JButton("Adicionar Foto");
		btnAddImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addImg();
				} catch (BusinessException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		panel.add(btnAddImg, "cell 9 15,growx");

		JButton btnEditar = new JButton("Editar");
		panel.add(btnEditar, "cell 5 19,alignx center,aligny center");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textFieldNumeroCandidato.getText().isEmpty() || textFieldNome.getText().isEmpty()
							|| textFieldCargo.getText().isEmpty() || textFieldVotacao.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de editar.");
						return;
					}

					String numeroCandidato = textFieldNumeroCandidato.getText();
					String novoNome = textFieldNome.getText();
					String novoCargo = textFieldCargo.getText();
					int idVotacao = Integer.parseInt(textFieldVotacao.getText());

					ControllerCandidato controller = new ControllerCandidato();
					controller.atualizarCandidato(numeroCandidato, novoNome, novoCargo, idVotacao, img_candidato);

					JOptionPane.showMessageDialog(null, "Candidato atualizado com sucesso.");
				} catch (BusinessException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});

		JButton btnConsultar = new JButton("Consultar");
		panel.add(btnConsultar, "cell 6 19,alignx center");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String numeroCandidato = textFieldNumeroCandidato.getText();
				ControllerCandidato controllerCandidato = new ControllerCandidato();
				Candidato candidato = controllerCandidato.buscarCandidato(numeroCandidato);

				if (candidato == null) {
					JOptionPane.showMessageDialog(null, "Candidato não encontrado.");
				} else {
					textFieldNome.setText(candidato.getNome());
					textFieldCargo.setText(candidato.getCargo());
					textFieldVotacao.setText(Integer.toString(candidato.getId_votacao()));
					exibirImagemNoLabel(lblImg, candidato.getImg_candidato());
				}
			}
		});

		JButton btnDeletar = new JButton("Deletar");
		panel.add(btnDeletar, "cell 7 19,alignx center");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String numeroCandidato = textFieldNumeroCandidato.getText();

					ControllerCandidato controller = new ControllerCandidato();
					controller.deletarCandidato(numeroCandidato);

					textFieldNumeroCandidato.setText("");
					textFieldNome.setText("");
					textFieldCargo.setText("");
					textFieldVotacao.setText("");
					lblImg.setIcon(new ImageIcon(resource));

					JOptionPane.showMessageDialog(null, "Candidato deletado com sucesso.");
				} catch (BusinessException error) {
					JOptionPane.showMessageDialog(null, "O candidato não pode ser deletado.");
				} catch (DbIntegrityException error2) {
					JOptionPane.showMessageDialog(null, error2.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		}); 

		JButton btnVoltar = new JButton("VOLTAR");
		panel.add(btnVoltar, "cell 0 26,growx");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ADMMenuConsulta consulta = new ADMMenuConsulta();
				consulta.setVisible(true);
				dispose();
			}
		});
	}

	private void exibirImagemNoLabel(JLabel label, String caminhoImagem) {
		ImageIcon icon = new ImageIcon(caminhoImagem);
		Image imagem = icon.getImage();
		Image novaImagem = imagem.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		label.setIcon(new ImageIcon(novaImagem));
	}

	public void addImg() throws BusinessException, IOException {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar Arquivo");
		jfc.setFileFilter(
				new FileNameExtensionFilter("Arquivo de Imagens (*.PNG, *.JPG, *.JPEG)", "png", "jpg", "jpeg"));
		int resultado = jfc.showOpenDialog(this);

		if (resultado == JFileChooser.APPROVE_OPTION) {
			FileInputStream fis = new FileInputStream(jfc.getSelectedFile());
			int tamanho = (int) jfc.getSelectedFile().length();
			Image img = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblImg.getWidth(), lblImg.getHeight(),
					Image.SCALE_SMOOTH);
			lblImg.setIcon(new ImageIcon(img));
			img_candidato = jfc.getSelectedFile().getAbsolutePath();
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ADMConsultaCandidato frame = new ADMConsultaCandidato();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	 private void atualizarListaCandidatos() {
		  
	        listModel.clear();

	        ControllerCandidato controllerCandidato = new ControllerCandidato();
	        List<Candidato> Candidatos = controllerCandidato.ExibirCandidatos();
	        for (Candidato candidato : Candidatos) {
	            listModel.addElement("Numero: " + candidato.getNumero_candidato() + " | Nome: " + candidato.getNome() + " | Cargo: " + candidato.getCargo());
	        }
	    }
}
