
package component;

import java.util.ArrayList;
import java.util.List;

public class Client {

	private static int newClientNumber = 0;

	private String name;
	private String firstname;
	private int clientNumber;

	public Client(String name, String firstname, int clientNumber) {
		this.name = name;
		this.firstname = firstname;
		this.clientNumber = ++newClientNumber;
	}

	// 1.1.1 Creation of the client class
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public int getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}

	@Override
	public String toString() {
		return "[name=" + name + ", firstname=" + firstname + ", clientNumber=" + clientNumber + "]";
	}

	public List<Client> generateClient(int numberClient) {
		List<Client> clients = new ArrayList<Client>();
		for (int i = 1; i <= numberClient; i++) {
			Client c = new Client("name" + i, "firstname" + i, i);
			clients.add(c);
		}
		return clients;
	}

	public void displayClient(List<Client> clients) {
		clients.stream().map(client -> client.toString()).forEach(System.out::println);

	}

}
