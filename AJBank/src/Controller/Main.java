package Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import component.Account;
import component.Client;
import component.Credit;
import component.CurrentAccount;
import component.Debit;
import component.Flow;
import component.SavingsAccount;
import component.Transfert;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Client c = new Client();

		List<Client> clients = c.generateClient(2); // créer 2 clients

		CurrentAccount ca = new CurrentAccount();
		// instancie la classe current Account pour accéder aux méthdes de la classe
		// Account qui est abstraite

		List<Account> accounts = ca.loading(clients);
		// créer un compte sauvergardé et courant pr chaque client

		Set<Account> specificAccounts = ca.specificAccount(accounts);
		// transforme la liste des comptes en Set

		List<Flow> flows = loadFlow(accounts);
		// déclare un tableau de flow suivant les consignes

		ca.transferFlowToAccount(specificAccounts, flows);
		// modifie les soldes des comptes à partir du tableau flows en utilisant
		// setBalance modifié en fonction duu type de flow

		ca.displaySpecificAccount(specificAccounts);
		// affiche les comptes par ordre croissant de solde

		loadByJson(); // probleme de cast et de recuperation de données

	}

	// 1.3.4 Creation of the flow array
	public static List<Flow> loadFlow(List<Account> accounts) {
		List<Flow> flows = new ArrayList<Flow>();
		LocalDate currentDate = LocalDate.now();
		LocalDate futureDate = currentDate.plusDays(2);
		int counter = 0;
		for (Account account : accounts) {
			counter++;
			if (account instanceof CurrentAccount) {
				Credit credit = new Credit("A credit of 100.50€ on all current accounts in the array of accounts",
						counter, 100.5, account.getAccountNumber(), true, futureDate);
				flows.add(credit);
			} else if (account instanceof SavingsAccount) {
				Credit credit = new Credit("A credit of 1500€ on all savings accounts", counter, 1500,
						account.getAccountNumber(), true, futureDate);
				flows.add(credit);
			}
		}
		Debit debit1 = new Debit("a debit of 50€ from account n°1", 1, 50, accounts.get(0).getAccountNumber(), true,
				futureDate);
		flows.add(debit1);
		Transfert transfert = new Transfert("A transfer of 50 € from account n ° 1 to account n ° 2", 1, 50,
				accounts.get(1).getAccountNumber(), true, futureDate, accounts.get(0).getAccountNumber());
		flows.add(transfert);
		return flows;
	}

	// 2.1 JSON file of flows
	public static List<Flow> loadByJson() {
		JSONParser jsonParser = new JSONParser();
		List<Flow> flows = new ArrayList<Flow>();

		try {
			FileReader reader = new FileReader("data/flows.json");
			Object obj = jsonParser.parse(reader);
			JSONArray jsonArray = (JSONArray) obj;

			for (Object o : jsonArray) {

				JSONObject jsonObject = (JSONObject) o;

				String type = (String) jsonObject.get("type");
				String comment = (String) jsonObject.get("comment");
				long identifierLong = (long) jsonObject.get("identifier");
				int identifier = (int) identifierLong;
				double amount = (double) jsonObject.get("amount");
				long targetAccountNumberLong = (long) jsonObject.get("targetAccountNumber");
				int targetAccountNumber = (int) targetAccountNumberLong;
				boolean effect = (boolean) jsonObject.get("effect");
				String dateString = (String) jsonObject.get("date");
				LocalDate date = LocalDate.parse(dateString);

				if (type.equals("credit")) {
					Credit credit = new Credit(comment, identifier, amount, targetAccountNumber, effect, date);
					flows.add(credit);
				} else if (type.equals("debit")) {
					Debit debit = new Debit(comment, identifier, amount, targetAccountNumber, effect, date);
					flows.add(debit);
				} else if (type.equals("transfer")) {

					long accountNumberIssuingLong = (long) jsonObject.get("sourceAccountNumber");
					int accountNumberIssuing = (int) accountNumberIssuingLong;
					Transfert transfert = new Transfert(comment, identifier, amount, targetAccountNumber, effect, date,
							accountNumberIssuing);
					flows.add(transfert);
				}

			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return flows;
	}

}
