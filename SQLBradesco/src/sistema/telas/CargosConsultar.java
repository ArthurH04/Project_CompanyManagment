package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sistema.BancoDeDados;
import sistema.Navegador;
import sistema.entidades.Cargo;

public class CargosConsultar extends JPanel {

	Cargo cargoAtual;
	JLabel labelTitulo, labelCargo;
	JTextField campoCargo;
	JButton botaoPesquisar, botaoEditar, botaoExcluir;
	DefaultListModel<Cargo> listaCargosModelo = new DefaultListModel();
	JList<Cargo> listaCargos;

	public CargosConsultar() {
		criarComponentes();
		criarEventos();
	}

	private void criarComponentes() {
		// TODO Auto-generated method stub
		setLayout(null);

		labelTitulo = new JLabel("Consulta de cargos", JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));

		labelCargo = new JLabel("Nome do cargo", JLabel.LEFT);
		campoCargo = new JTextField();
		botaoPesquisar = new JButton("Pesquisar cargo");
		botaoEditar = new JButton("Editar cargo");
		botaoEditar.setEnabled(false);
		botaoExcluir = new JButton("Excluir cargo");
		botaoExcluir.setEnabled(false);
		listaCargosModelo = new DefaultListModel();
		listaCargos = new JList();
		listaCargos.setModel(listaCargosModelo);
		listaCargos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		labelTitulo.setBounds(20, 20, 660, 40);
		labelCargo.setBounds(150, 120, 400, 20);
		campoCargo.setBounds(150, 140, 400, 40);
		botaoPesquisar.setBounds(560, 140, 130, 40);
		listaCargos.setBounds(150, 200, 400, 240);
		botaoEditar.setBounds(560, 360, 130, 40);
		botaoExcluir.setBounds(560, 400, 130, 40);

		add(labelTitulo);
		add(labelCargo);
		add(campoCargo);
		add(botaoPesquisar);
		add(listaCargos);
		add(botaoEditar);
		add(botaoExcluir);

		setVisible(true);
	}

	private void criarEventos() {
		// TODO Auto-generated method stub
		botaoPesquisar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sqlPesquisarCargos(campoCargo.getText());
			}
		});

		botaoEditar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Navegador.cargosEditar(cargoAtual);
			}
		});

		botaoExcluir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sqlDeletarCargos();
			}
		});

		listaCargos.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub

				cargoAtual = listaCargos.getSelectedValue();
				if (cargoAtual == null) {
					botaoEditar.setEnabled(false);
					botaoExcluir.setEnabled(false);

				} else {
					botaoEditar.setEnabled(true);
					botaoExcluir.setEnabled(true);
				}

			}
		});

	}

	private void sqlPesquisarCargos(String nome) {

		// Conexao
		java.sql.Connection conexao;

		// Instrução SQL
		Statement instruçãoSQL;

		// Resultados
		ResultSet resultados;

		try {

			// Conectando ao banco de dados

			conexao = DriverManager.getConnection(BancoDeDados.stringDeConexão, BancoDeDados.usuario,
					BancoDeDados.senha);

			// Criando instrução SQL
			instruçãoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultados = instruçãoSQL.executeQuery("SELECT * FROM Cargos WHERE nome like '%" + nome + "%'");

			while (resultados.next()) {
				Cargo cargo = new Cargo();
				cargo.setId(resultados.getInt("id"));
				cargo.setNome(resultados.getString("nome"));

				listaCargosModelo.addElement(cargo);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar os Cargos.");
			java.util.logging.Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	private void sqlDeletarCargos() {
		// TODO Auto-generated method stub

		int confirmacao = JOptionPane.showConfirmDialog(null,
				"Deseja realmente excluir o cargo" + cargoAtual.getNome() + "?", "Excluir", JOptionPane.YES_NO_OPTION);
		if (confirmacao == JOptionPane.YES_OPTION) {

			// Conexao
			java.sql.Connection conexao;

			// Instrução SQL
			Statement instruçãoSQL;

			// Resultados
			ResultSet resultados;

			try {
				// Conectando ao Banco de dados
				conexao = DriverManager.getConnection(BancoDeDados.stringDeConexão, BancoDeDados.usuario,
						BancoDeDados.senha);

				// Cria instrução SQL
				instruçãoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				instruçãoSQL.executeUpdate("DELETE cargos WHERE id=" + cargoAtual.getId() + "");

				JOptionPane.showMessageDialog(null, "Cargo " + cargoAtual + "deletado com sucesso");

			} catch (Exception e) {
				// TODO: handle exception

				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao deletar o cargo");
				java.util.logging.Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
			}

		}

	}

}
