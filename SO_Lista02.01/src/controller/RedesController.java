package controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class RedesController {

	public RedesController() {
		super();
	}
	
	// Método para identificar o tipo de SO
	private String os() {
		
		String nome = System.getProperty("os.name");
		
		return nome;
	}
	
	public void ip() {
		
		String sistema = os();
		String leitura;
		
		// .contains() é case sensitive, logo
		// usa-se .toLowerCase() para deixar tudo tudo em minusculo
		if(sistema.toLowerCase().contains("linux")) {
			leitura = lerProcesso("ip addr");
		}else {
			leitura = lerProcesso("IPCONFIG");
		}

		String[] vetorString = leitura.split("\n");
		
		String parametro;
		
		if(sistema.toLowerCase().contains("linux")) {
			parametro = "inet "; 	// O espaco e destinado para ele pegar apenas o Inet e nao o Inet6
		}else {
			parametro = "ipv4. ";
		}
		
		System.out.println("Os protocolos "+parametro.toUpperCase()+"deste "+sistema+" sao: \n");
		
		for(String linha: vetorString) {
			if(linha.toLowerCase().contains(parametro)) System.out.println(linha);
		}
		System.out.println("\n");
		
		//System.out.println(leitura)
	}
	
	private String lerProcesso(String comando) {
		
		String[] comandoArr = comando.split(" ");
		String conteudo;
		
		try {

			Process processo = Runtime.getRuntime().exec(comandoArr);
			
			// Lendo o fluxo de bits
			InputStream fluxo = processo.getInputStream();
			
			// Convertendo para String e passando para o buffer de leitura
			InputStreamReader leitorFluxo = new InputStreamReader(fluxo);
			BufferedReader bufferFluxo = new BufferedReader(leitorFluxo);
			
			StringBuffer buffer = new StringBuffer();
			
			conteudo = bufferFluxo.readLine();	
			
			while(conteudo!=null) {
				buffer.append(conteudo);
				buffer.append("\n");				// Por mais que tenha quebra de linha na leitrua do 
													// terminal, é necessário por para configuraçao da String
													// Senão printa tudo junto
				conteudo = bufferFluxo.readLine();
			}
			
			conteudo = buffer.toString();
			
		}catch(Exception exc) {
			conteudo = exc.getMessage();
			System.err.println(exc.getMessage());
		}
		
		return conteudo;
	}
	
	// O comando para chamar o ping varia entre Linux e Windows, mas, basicamente
	// ping -4 -n(-c para Linux) 10 www.site.com
	// -4 é para IPV4
	// 10 é o número de tentativas
	public void ping() {
		
		String sistema = os();
		String leitura, linha ="";
		String med, div, fim;
		
		System.out.println("Calculando o ping medio de conexao com www.google.com: ");
		
		if(sistema.toLowerCase().contains("linux")) {
			leitura = lerProcesso("ping -4 -c 10 www.google.com.br");
		}else {
			leitura = lerProcesso("ping -4 -n 10 www.google.com.br");
		}

		String[] vetorString = leitura.split("\n");
		
		if(sistema.toLowerCase().contains("windows")) {
			
			med = "dia";
			div = ",";
			fim = "dia";
			
		}else {
			
			med = "mdev";
			div = "/";
			fim = "ms";
						
		}
		
		for(String i: vetorString) {
			if(i.toLowerCase().contains(med)) linha = i;
		}
		
		String[] media = linha.split(div);
		
		for(String i: media){
			if(i.toLowerCase().contains(fim)) System.out.println("Média do ping: "+i);
		}
		
	}
	
}
