package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.jdi.connect.spi.Connection;
//import com.sun.org.slf4j.internal.Logger;
import sistema.entidades.Cargo;


import sistema.BancoDeDados;

public class CargosInserir extends JPanel {

	JLabel labelTitulo, labelCargo;
	JTextField campoCargo;
	JButton botaoGravar;
	
	public CargosInserir() {
		criarComponentes();
		criarEventos();
	}

	private void criarComponentes() {
		setLayout(null);
		
		labelTitulo = new JLabel("Cadastro de Cargo", JLabel.CENTER);
		labelTitulo.setFont(new Font (labelTitulo.getFont().getName(), Font.PLAIN, 20));
		labelCargo = new JLabel("Nome do cargo", JLabel.LEFT);
		campoCargo = new JTextField();
		botaoGravar = new JButton("Adicionar Cargo");
		
		labelTitulo.setBounds(20,20,660,40);
		labelCargo.setBounds(150,120,400,20);
		campoCargo.setBounds(150,140,400,40);
		botaoGravar.setBounds(250,380,200,40);
		
		add(labelTitulo);
		add(labelCargo);
		add(campoCargo);
		add(botaoGravar);
		
		setVisible(true);
		
	}

	private void criarEventos() {
		// TODO Auto-generated method stub
		botaoGravar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Cargo novoCargo = new Cargo();
				novoCargo.setNome(campoCargo.getText());
				
				sqlInserirCargo(novoCargo);
				
			}
		});
	}
	
	private void sqlInserirCargo(Cargo novoCargo) {
		
		//Validando nome
		if (campoCargo.getText().length() <= 3) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha o nome corretamente");
			return;		
		}
		
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
			instruçãoSQL.executeUpdate("INSERT INTO cargos (nome) VALUES ('"+novoCargo.getNome()+"')");
			
			JOptionPane.showMessageDialog(null, "Cargo adicionado com sucesso");
			
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o Cargo.");
			java.util.logging.Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
		}
		
	}

}
