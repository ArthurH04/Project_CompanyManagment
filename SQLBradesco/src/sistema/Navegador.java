package sistema;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


import sistema.entidades.Cargo;
import sistema.entidades.Funcionario;
import sistema.telas.CargosConsultar;
import sistema.telas.CargosEditar;
import sistema.telas.CargosInserir;
import sistema.telas.FuncionariosConsultar;
import sistema.telas.FuncionariosEditar;
import sistema.telas.FuncionariosInserir;
import sistema.telas.Inicio;
import sistema.telas.Login;

public class Navegador {

	private static boolean menuConstruido;
	private static boolean menuHabilitado;
	private static JMenuBar menuBar;
	private static JMenu menuArquivo, menuFuncionarios, menuCargos, menuRelatorios;
	private static JMenuItem miSair, miFuncionariosConsultar, miFuncionariosCadastrar, miCargosConsultar;
	private static JMenuItem miCargosCadastrar, miRelatoriosCargos, miRelatoriosSalarios;

	public static void login() {
		Sistema.tela = new Login();
		Sistema.frame.setTitle("Funcionários Company SA");
		Navegador.AtualizarTela();
	}

	public static void inicio() {
		Sistema.tela = new Inicio();
		Sistema.frame.setTitle("Funcionários Company SA");
		Navegador.AtualizarTela();
	}
	
	public static void funcionariosCadastrar(){
        Sistema.tela = new FuncionariosInserir();   
        Sistema.frame.setTitle("Funcionários Company SA - Cadastrar funcionários");     
        Navegador.AtualizarTela();
    }
    
    public static void funcionariosConsultar(){
        Sistema.tela = new FuncionariosConsultar();
        Sistema.frame.setTitle("Funcionários Company SA - Consultar funcionários");     
        Navegador.AtualizarTela();
    }
    
    public static void funcionariosEditar(Funcionario funcionario){
        Sistema.tela = new FuncionariosEditar(funcionario);  
        Sistema.frame.setTitle("Funcionários Company SA - Editar funcionários");           
        Navegador.AtualizarTela();
    }

	public static void cargosCadastar() {
		Sistema.tela = new CargosInserir();
		Sistema.frame.setTitle("Funcionários Company SA - Cadastar cargos");
		Navegador.AtualizarTela();
	}

	public static void cargosEditar(Cargo cargo) {
		Sistema.tela = new CargosEditar(cargo);
		Sistema.frame.setTitle("Funcionários Company SA - Editar cargos");
		Navegador.AtualizarTela();
	}

	public static void cargosConsultar() {
		Sistema.tela = new CargosConsultar();
		Sistema.frame.setTitle("Funcionários Company SA - Consultar cargos");
		Navegador.AtualizarTela();
	}
	

	// Método que remove a tela atual e adiciona a próxima tela 21 /11/ 2021
	public static void AtualizarTela() {
		Sistema.frame.getContentPane().removeAll();
		Sistema.tela.setVisible(true);
		Sistema.frame.add(Sistema.tela);

		Sistema.frame.setVisible(true);
	}

	private static void ConstruirMenu() {

		if (!menuConstruido) {
			menuConstruido = true;

			menuBar = new JMenuBar();

			// menu Arquivo
            menuArquivo = new JMenu("Arquivo");
            menuBar.add(menuArquivo);
            miSair = new JMenuItem("Sair");
            menuArquivo.add(miSair);
            
            // menu Funcionários
            menuFuncionarios = new JMenu("Funcionários");
            menuBar.add(menuFuncionarios);
            miFuncionariosCadastrar = new JMenuItem("Cadastrar");
            menuFuncionarios.add(miFuncionariosCadastrar);
            miFuncionariosConsultar = new JMenuItem("Consultar");
            menuFuncionarios.add(miFuncionariosConsultar);
            
            // menu Cargos
            menuCargos = new JMenu("Cargos");
            menuBar.add(menuCargos);
            miCargosCadastrar = new JMenuItem("Cadastrar");
            menuCargos.add(miCargosCadastrar);
            miCargosConsultar = new JMenuItem("Consultar");
            menuCargos.add(miCargosConsultar);
            
            // menu Relatórios
            menuRelatorios = new JMenu("Relatórios");
            menuBar.add(menuRelatorios);
            miRelatoriosCargos = new JMenuItem("Funcionários por cargos");
            menuRelatorios.add(miRelatoriosCargos);
            miRelatoriosSalarios = new JMenuItem("Salários dos funcionários");
            menuRelatorios.add(miRelatoriosSalarios);
            
            criarEventosMenu();

		}

	}

	public static void habilitaMenu() {
		// TODO Auto-generated method stub

		// Checa se tem menu
		if (!menuConstruido)
			ConstruirMenu();
		if (!menuHabilitado) {
			menuHabilitado = true;
			Sistema.frame.setJMenuBar(menuBar);
		}

	}

	private static void desabilitaMenu() {
		// Verufuca se o menu está habilitado
		if (menuHabilitado) {
			menuHabilitado = false;
			Sistema.frame.setJMenuBar(null);

		}

	}

	private static void criarEventosMenu() {
		miSair.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		//Funcionario
		miFuncionariosCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				funcionariosCadastrar();
			}
		});
		
		miFuncionariosConsultar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				funcionariosConsultar();
			}
		});
		
		//Cargos
		miCargosCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cargosCadastar();
			}
		});
		
		miCargosConsultar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cargosConsultar();
			}
		});
		
		miRelatoriosCargos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		miRelatoriosSalarios.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	
}
