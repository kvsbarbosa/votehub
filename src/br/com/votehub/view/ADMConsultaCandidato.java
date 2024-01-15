package br.com.votehub.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
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

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import br.com.votehub.controller.BusinessException;
import br.com.votehub.controller.ControllerCandidato;
import br.com.votehub.model.DAOs.DbException;
import br.com.votehub.model.DAOs.DbIntegrityException;
import br.com.votehub.model.vo.Candidato;
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

	/**
	 * Launch the application.
	 */
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

	public ADMConsultaCandidato() {
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

		ImageIcon cc = new ImageIcon("./icons/menu_consulta/con_cand.png");
		Image ccImg = cc.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		ImageIcon resizedCc = new ImageIcon(ccImg);
		JLabel lblNewLabel = new JLabel(resizedCc);
		panelEsquerda.add(lblNewLabel, "cell 6 0, alignx center");

		JLabel lblTitulo = new JLabel("Candidatos");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelEsquerda.add(lblTitulo, "cell 6 1,alignx center");

		JLabel lblNumeroCandidato = new JLabel("N° Candidato:");
		lblNumeroCandidato.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelEsquerda.add(lblNumeroCandidato, "cell 5 4,alignx right");
		textFieldNumeroCandidato = new JTextField();
		panelEsquerda.add(textFieldNumeroCandidato, "cell 6 4 2 1,growx");

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelEsquerda.add(lblNome, "cell 5 5,alignx right");
		textFieldNome = new JTextField();
		panelEsquerda.add(textFieldNome, "cell 6 5 2 1,growx");

		JLabel lblCargo = new JLabel("Cargo:");
		lblCargo.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelEsquerda.add(lblCargo, "cell 5 6,alignx right");
		textFieldCargo = new JTextField();
		panelEsquerda.add(textFieldCargo, "cell 6 6 2 1,growx");

		URL resource = ADMConsultaCandidato.class.getClassLoader().getResource("icons8-câmera-100.png");

		JLabel lblVotacao = new JLabel("ID Votação:");
		lblVotacao.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelEsquerda.add(lblVotacao, "cell 5 7,alignx right");
		textFieldVotacao = new JTextField();
		textFieldVotacao.setEditable(false);
		panelEsquerda.add(textFieldVotacao, "cell 6 7,growx");
		lblImg = new JLabel("");
		lblImg.setIcon(new ImageIcon(resource));
		panelEsquerda.add(lblImg, "cell 6 9,alignx center");

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

		panelEsquerda.add(btnAddImg, "cell 7 9");

		JButton btnDeletar = new JButton("Deletar");
		panelEsquerda.add(btnDeletar, "cell 7 11");
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

		JButton btnEditar = new JButton("Editar");
		panelEsquerda.add(btnEditar, "cell 5 11,alignx center");
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
		panelEsquerda.add(btnConsultar, "cell 6 11,alignx center");
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

		JButton btnVoltar = new JButton("VOLTAR");
		panelEsquerda.add(btnVoltar, "cell 6 14,alignx center");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ADMMenuConsulta tela = new ADMMenuConsulta();
				tela.setVisible(true);
				dispose();
			}
		});
		
		JPanel panelDireita = new JPanel();
		panelDireita.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelDireita.setBackground(new Color(164, 247, 176));
		getContentPane().add(panelDireita, "cell 1 0,grow");
		panelDireita.setLayout(new MigLayout("fill", "[][][]", "[][][][][][][][][][][][]"));

		listModel = new DefaultListModel<>();
		JLabel lblTxtGeral = new JLabel("Candidatos cadastrados:");
		lblTxtGeral.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelDireita.add(lblTxtGeral, "cell 1 2,alignx center,aligny center");
		list = new JList<>(listModel);
		list.setBackground(SystemColor.menu);
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelDireita.add(list, "cell 1 3,alignx center,aligny center");
		atualizarListaCandidatos();
	}

	private void atualizarListaCandidatos() {
		listModel.clear();
		ControllerCandidato controllerCandidato = new ControllerCandidato();
		List<Candidato> Candidatos = controllerCandidato.ExibirCandidatos();
		for (Candidato candidato : Candidatos) {
			listModel.addElement("Numero: " + candidato.getNumero_candidato() + " | Nome: " + candidato.getNome()
					+ " | Cargo: " + candidato.getCargo());
		}
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

}
