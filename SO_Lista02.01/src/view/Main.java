package view;

import controller.RedesController;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		// Instanciando os métodos da classe RedesController
		RedesController metodo = new RedesController();
		
		int opc = 0;
		
		do {
			
			opc = Integer.parseInt(JOptionPane.showInputDialog("Digite a opção desejada:\n"+
															   "1 - Buscar IPV4\n"+
															   "2 - Mensurar Ping\n"+
															   "9 - Sair"));
			
			switch(opc) {
				case 1:
					metodo.ip();
					break;
				
				case 2:
					metodo.ping();
					break;
					
				case 9:
					System.out.println("Fim do programa!");
					break;
					
				default:
					System.out.println("ERRO! Tente novamente!");
			}
			
		}while(opc!=9);

	}

}
