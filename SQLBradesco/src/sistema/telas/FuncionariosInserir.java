package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

//import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import sistema.BancoDeDados;
import sistema.Navegador;
import sistema.entidades.Cargo;
import sistema.entidades.Funcionario;

public class FuncionariosInserir extends JPanel {

	JLabel labelTitulo, labelNome, labelSobrenome, labelDataNascimento, labelEMail, labelCargo, labelSalario;
	JTextField campoNome;
	JTextField campoSobrenome;
	JTextField campoEmail;
	JFormattedTextField campoDataNascimento, campoSalario;
	JComboBox comboBoxCargo;
	JButton botaoGravar;

	public FuncionariosInserir() {
		criarComponentes();
		criarEventos();
		
		//21/11/2021
		Navegador.habilitaMenu();
	}

	private void criarComponentes() {
		// TODO Auto-generated method stub

		setLayout(null);

		labelTitulo = new JLabel("Cadastro de Funcionário", JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
		labelNome = new JLabel("Nome:", JLabel.LEFT);
		campoNome = new JTextField();
		labelSobrenome = new JLabel("Sobrenome:", JLabel.LEFT);
		campoSobrenome = new JTextField();
		labelDataNascimento = new JLabel("Data de Nascimento:", JLabel.LEFT);
		campoDataNascimento = new JFormattedTextField();

		try {
			MaskFormatter dateMask = new MaskFormatter("##/##/####");
			dateMask.install(campoDataNascimento);
		} catch (java.text.ParseException e) {
			// TODO: handle exception
			Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, e);

		}

		labelEMail = new JLabel("E-mail:", JLabel.LEFT);
		campoEmail = new JTextField();
		labelCargo = new JLabel("Cargo:", JLabel.LEFT);
		comboBoxCargo = new JComboBox();
		labelSalario = new JLabel("Salário:", JLabel.LEFT);
		DecimalFormat formatter = new DecimalFormat("###0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
		campoSalario = new JFormattedTextField(formatter);
		campoSalario.setValue(0.00);
		botaoGravar = new JButton("Adicionar");

		labelTitulo.setBounds(20, 20, 660, 40);
		labelNome.setBounds(150, 80, 400, 20);
		campoNome.setBounds(150, 100, 400, 40);
		labelSobrenome.setBounds(150, 140, 400, 20);
		campoSobrenome.setBounds(150, 160, 400, 40);
		labelDataNascimento.setBounds(150, 200, 400, 20);
		campoDataNascimento.setBounds(150, 220, 400, 40);
		labelEMail.setBounds(150, 260, 400, 20);
		campoEmail.setBounds(150, 280, 400, 40);
		labelCargo.setBounds(150, 320, 400, 20);
		comboBoxCargo.setBounds(150, 340, 400, 40);
		labelSalario.setBounds(150, 380, 400, 20);
		campoSalario.setBounds(150, 400, 400, 40);
		botaoGravar.setBounds(560, 400, 130, 40);

		add(labelTitulo);
		add(labelNome);
		add(campoNome);
		add(labelSobrenome);
		add(campoSobrenome);
		add(labelDataNascimento);
		add(campoDataNascimento);
		add(labelEMail);
		add(campoEmail);
		add(labelCargo);
		add(comboBoxCargo);
		add(labelSalario);
		add(campoSalario);
		add(botaoGravar);

		sqlCarregarCargos();

		setVisible(true);

	}

	private void criarEventos() {
		// TODO Auto-generated method stub

		botaoGravar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Funcionario novoFuncionario = new Funcionario();
				novoFuncionario.setNome(campoNome.getText());
				novoFuncionario.setSobrenome(campoSobrenome.getText());
				novoFuncionario.setDataNascimento(campoDataNascimento.getText());
				novoFuncionario.setEmail(campoEmail.getText());
				Cargo cargoSelecionado = (Cargo) comboBoxCargo.getSelectedItem();
				if (cargoSelecionado != null)
					novoFuncionario.setCargo(cargoSelecionado.getId());

				novoFuncionario.setSalario(Double.valueOf(campoSalario.getText().replace(",", ".")));

				sqlInserirFuncionario(novoFuncionario);

			}
		});

	}

	private void sqlCarregarCargos() {
		// TODO Auto-generated method stub

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
			resultados = instruçãoSQL.executeQuery("SELECT * from cargos order by nome asc");
			comboBoxCargo.removeAll();

			while (resultados.next()) {
				Cargo cargo = new Cargo();
				cargo.setId(resultados.getInt("id"));
				cargo.setNome(resultados.getString("nome"));
				comboBoxCargo.addItem(cargo);
			}

			conexao.close();

		} catch (SQLException e) {
			// TODO: handle exception

			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao carregar os cargos");
			java.util.logging.Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
		}

	}
	// Validando nome

	public void sqlInserirFuncionario(Funcionario novoFuncionario) {
		if (campoNome.getText().length() <= 3) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha o nome corretamente");
			return;
		}

		// Validando sobrenome

		if (campoSobrenome.getText().length() <= 3) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha o sobrenome corretamente");
			return;
		}

		// Validando salario
		if (Double.parseDouble(campoSalario.getText().replace(",", ".")) <= 100) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha o salário corretamente");

		}

		// Validando email
		Boolean emailValidado = false;
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		Pattern p = Pattern.compile(ePattern);
		Matcher m = p.matcher(campoEmail.getText());
		emailValidado = m.matches();

		if (!emailValidado) {
			JOptionPane.showMessageDialog(null, "Por favor, insira o email corretamente");
			return;
		}

		// Conexao
		java.sql.Connection conexao;

		// Instrução SQL
		PreparedStatement instruçãoSQL;

		try {
			// Conectando ao Banco de dados
			conexao = DriverManager.getConnection(BancoDeDados.stringDeConexão, BancoDeDados.usuario,
					BancoDeDados.senha);

			String template = "INSERT INTO funcionarios (nome,sobrenome,dataNascimento,email,cargo,salario)";
			template = template +"VALUES (?,?,?,?,?,?)";
			instruçãoSQL = conexao.prepareStatement(template);
			instruçãoSQL.setString(1, novoFuncionario.getNome());
			instruçãoSQL.setString(2, novoFuncionario.getSobrenome());
			instruçãoSQL.setString(3, novoFuncionario.getDataNascimento());
			instruçãoSQL.setString(4, novoFuncionario.getEmail());
			
			if (novoFuncionario.getCargo() > 0) {
				instruçãoSQL.setInt(5, novoFuncionario.getCargo());
			}else {
				instruçãoSQL.setNull(5, java.sql.Types.INTEGER);
			}
			
			instruçãoSQL.setString(6, Double.toString(novoFuncionario.getSalario()));
			instruçãoSQL.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Funcionário adicionado com sucesso!");
			Navegador.inicio();
			
			conexao.close();
			
		} catch (SQLException e) {
			// TODO: handle exception

			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o funcionário");
			java.util.logging.Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
		}

	}
}
