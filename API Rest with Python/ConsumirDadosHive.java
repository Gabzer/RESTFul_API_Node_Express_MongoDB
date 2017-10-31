import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * Classname             ConsumirDadosHive 
 * Date                  30/10/2017
 * author                Gabriel Zerbine
 * Copyright notice      Classe que consome dados disponibilizados por uma API Rest
 */

public class ConsumirDadosHive {

	public static void main(String[] args) {

	  try {	
		String string = args[0];
		/**
		 * Variáveis para o manuseio dos dados:
		 * 
		 * 	colunas = quais colunas se deseja consultar ou *;
		 * 	nome_tab = schema.nomedatabela;
		 * 	condicao = é só colocar a condição do where, se não houver NENHUMA colocar 0;
		 * 	compl = variável para outros comandos (order by, limit, etc...), se não houver NENHUM colocar 0.
		 * 
		 * OBS.: para essas duas últimas variáveis todo espaço em branco deve ser trocado por '_' 
		 * 								(ex.: em vez de 'limit 10' colocar 'limit_10')
		 * A string enviada deve conter o valor de cada uma dessas variáveis anteriores separados por '-'
		 * conforme o exemplo a seguir:
		 * Exemplo:  "documentonumero,telefone,score-default.telefones_nfe-0-limit_10"
		 */
		URL url = new URL("http://10.7.151.3:50111/hive/" + string);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();		
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		if (conn.getResponseCode() != 200) {	
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

	  } catch (MalformedURLException e) {
		System.out.println("Catch 1");
		e.printStackTrace();

	  } catch (IOException e) {
		System.out.println("Catch 2");
		e.printStackTrace();

	  }

	}

}
