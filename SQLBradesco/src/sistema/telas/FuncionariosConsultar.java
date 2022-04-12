package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sistema.BancoDeDados;
import sistema.Navegador;
import sistema.entidades.Funcionario;
import sistema.telas.*;

public class FuncionariosConsultar extends JPanel {

	Funcionario funcionarioAtual;
	JLabel labelTitulo, labelFuncionario;
	JTextField campoFuncionario;
	JButton botaoPesquisar, botaoEditar, botaoExcluir;
	DefaultListModel<Funcionario> listasFuncionariosModelo = new DefaultListModel();
	JList<Funcionario> listaFuncionarios;

	public FuncionariosConsultar() {
		criarComponentes();
		criarEventos();

	}

	private void criarComponentes() {
		// TODO Auto-generated method stub
		setLayout(null);

		labelTitulo = new JLabel("Cadastro de Funcionários", JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
		labelFuncionario = new JLabel("Nome do funcionário", JLabel.LEFT);
		campoFuncionario = new JTextField();
		botaoPesquisar = new JButton("Pesquisar Funcionário");
		botaoEditar = new JButton("Editar Funcionário");
		botaoEditar.setEnabled(false);
		botaoExcluir = new JButton("Excluir Funcionário");
		botaoExcluir.setEnabled(false);
		listasFuncionariosModelo = new DefaultListModel();
		listaFuncionarios = new JList();
		listaFuncionarios.setModel(listasFuncionariosModelo);
		listaFuncionarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		labelTitulo.setBounds(20, 20, 660, 40);
		labelFuncionario.setBounds(150, 120, 400, 20);
		campoFuncionario.setBounds(150, 140, 400, 40);
		botaoPesquisar.setBounds(560, 140, 130, 40);
		listaFuncionarios.setBounds(150, 200, 400, 240);
		botaoEditar.setBounds(560, 360, 130, 40);
		botaoExcluir.setBounds(560, 400, 130, 40);

		add(labelTitulo);
		add(labelFuncionario);
		add(campoFuncionario);
		add(botaoPesquisar);
		add(listaFuncionarios);
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
				sqlPesquisarFuncionarios(campoFuncionario.getText());
			}
		});

		botaoEditar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Navegador.funcionariosEditar(funcionarioAtual);
			}
		});

		botaoExcluir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sqlDeletarFuncionario();
			}
		});

		listaFuncionarios.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				funcionarioAtual = listaFuncionarios.getSelectedValue();
				if (funcionarioAtual == null) {
					botaoEditar.setEnabled(false);
					botaoExcluir.setEnabled(false);
				} else {
					botaoEditar.setEnabled(true);
					botaoExcluir.setEnabled(true);
				}
			}
		});

	}

	private void sqlPesquisarFuncionarios(String nome) {
		//Conexao
				java.sql.Connection conexao;
				
				//Instrução SQL
				Statement instruçãoSQL;
				
				//Resultados
				ResultSet resultados;
				
				try {
					
					//Conectando ao banco de dados
					
					conexao = DriverManager.getConnection(BancoDeDados.stringDeConexão, BancoDeDados.usuario, BancoDeDados.senha);
					
					//Criando instrução SQL
					instruçãoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					resultados = instruçãoSQL.executeQuery("SELECT * FROM funcionarios WHERE nome like '%"+nome+"%' order by nome ASC");
					listasFuncionariosModelo.clear();
					
					while (resultados.next()) {
						Funcionario funcionario = new Funcionario();
						funcionario.setId(resultados.getInt("id"));
						funcionario.setNome(resultados.getString("nome"));
						funcionario.setSobrenome(resultados.getString("sobrenome"));
						funcionario.setDataNascimento(resultados.getString("dataNascimento"));
						funcionario.setEmail(resultados.getString("email"));
						if (resultados.getString("cargo") != null) funcionario.setCargo (Integer.parseInt(resultados.getString("cargo"))); 
						funcionario.setSalario(Double.parseDouble(resultados.getString("salario")));
						
						listasFuncionariosModelo.addElement(funcionario);
						
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar funcionários.");
					java.util.logging.Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
				}
	}
	
	private void sqlDeletarFuncionario() {
		String pergunta = "Deseja realmente excluir o funcionário "+funcionarioAtual.getNome()+"?";
		
		int confirmacao = JOptionPane.showConfirmDialog(null, pergunta, "Excluir", JOptionPane.YES_NO_OPTION);
		if (confirmacao == JOptionPane.YES_OPTION) {
			
			//Conexao
			java.sql.Connection conexao;
			
			//Instrução SQL
			Statement instruçãoSQL;
			
			//Resultados
			ResultSet resultados;
			
			try {
				
				//Conectando ao banco de dados
				
				conexao = DriverManager.getConnection(BancoDeDados.stringDeConexão, BancoDeDados.usuario, BancoDeDados.senha);
				
				//Criando instrução SQL
				instruçãoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				instruçãoSQL.executeUpdate("DELETE funcionarios WHERE id="+funcionarioAtual.getId()+"");
				
				JOptionPane.showMessageDialog(null, "Funcionário deletado com sucesso");
				Navegador.inicio();
				
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir o funcionário.");
				java.util.logging.Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
			}
			
		}
	}

}
