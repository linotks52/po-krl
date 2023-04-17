
package pt.iscte_iul.ista.DIAM;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.kohsuke.github.*;

public class SaveCsv{

	public static void main(String[] args) throws IOException {

		InputStreamReader ir = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(ir);
		BufferedReader bufferedReader=null;
		BufferedWriter bufferedWriter=null;
		String  metodo ,OndeGuardar , nome ,DfilePath , SfilePath,donoR,repositorio,token;

		System.out.println("Especifique a Directoria do ficheiro Fonte\n");
		SfilePath=in.readLine();

		System.out.println("Nome que pretende dar ao ficheiro\n");
		nome =in.readLine()+".csv" ;

		System.out.println("Porfavor, especifique o metodo que pretende salvar o ficheiro(web ou localmente)\n");
		metodo = in.readLine();
		if(metodo.contentEquals("localmente")){

			System.out.println("Directoria onde pretende salvar o ficheiro\n");
			OndeGuardar = in.readLine();


			DfilePath=OndeGuardar + nome ;



			try {
				bufferedReader = new BufferedReader(new FileReader(SfilePath));
				bufferedWriter=new BufferedWriter(new FileWriter(DfilePath));

				String line;
				// Read each line of the source CSV file and write it to the destination CSV file
				while ((line = bufferedReader.readLine()) != null) {
					bufferedWriter.write(line);
					bufferedWriter.newLine();
				}
				System.out.println("Data written to the destination CSV file successfully.");
			}
			finally
			{
				// Close the BufferedReader and BufferedWriter
				bufferedReader.close();
				bufferedWriter.close();
			}







		}
		if(metodo.contentEquals("web")){

			// GitHub repository information
			System.out.println("GitHub Username:\n");
			donoR = in.readLine();
			System.out.println("Repositorio GitHub:\n");
			repositorio = in.readLine();


			// GitHub API token for authentication
			System.out.println("Token de autenticacao GitHub:\n");
			token = in.readLine();

			// Read the existing CSV file from disk
			Path path = Paths.get(SfilePath);
			byte[] fileContent = Files.readAllBytes(path);

			// Authenticate with GitHub using the API token
			GitHub github = new GitHubBuilder().withOAuthToken(token).build();

			// Get the repository to update the file in
			GHRepository repository = github.getRepository(donoR + "/" + repositorio);



			// Create a new file in the repository with the CSV data
			GHContentBuilder builder = repository.createContent();
			builder.content(fileContent).message("Added " + nome).path(nome).commit();

			// Print a message to indicate that the file was uploaded
			System.out.println("File " + nome + " was uploaded to " + donoR + "/" + repositorio);
		}
	}
}

