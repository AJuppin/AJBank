package component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

//1.2.1 Creation of the account class
//1.2.3 Creation of the tablea account
public abstract class Account {

	private static int newAccountNumber = 0;

	protected String label;
	protected double balance;
	protected int accountNumber = 0;
	protected Client client;

	public Account(String label, Client client) {
		this.label = label;
		this.client = client;
		this.accountNumber = ++newAccountNumber;
	}

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getBalance() {
		return balance;
	}

	// 1.3.5 Updating accounts
	public void setBalance(Flow flow) {
		if (flow instanceof Credit) {
			if (this.accountNumber == flow.getTargetAccountNumber()) {
				this.balance += flow.getAmount();
			}
		}
		if (flow instanceof Debit) {
			if (this.accountNumber == flow.getTargetAccountNumber()) {
				this.balance -= flow.getAmount();
			}
		}
		if (flow instanceof Transfert) {
			Transfert transfert = (Transfert) flow; // conversion de type pour accéder à accountNumberIssuing
			if (this.accountNumber == transfert.getAccountNumberIssuing()) {
				this.balance -= transfert.getAmount();
			}
			if (this.accountNumber == transfert.getTargetAccountNumber()) {
				this.balance += transfert.getAmount();

			}
		}
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "Account [label=" + label + ", balance=" + balance + ", accountNumber=" + accountNumber + ", client="
				+ client + "]";
	}

	public List<Account> loading(List<Client> clients) {
		List<Account> Accounts = new ArrayList<Account>();
		for (Client client : clients) {
			CurrentAccount ca = new CurrentAccount("compte courant du client " + client.getName(), client);
			SavingsAccount sa = new SavingsAccount("compte sauvegardé du client " + client.getName(), client);
			Accounts.add(ca);
			Accounts.add(sa);
		}
		return Accounts;
	}

	public void displayAccount(List<Account> Accounts) {
		Accounts.stream().map(ca -> ca.toString()).forEach(System.out::println);

	}

	// 1.3.1 Adaptation of the table of accounts
	public Set<Account> specificAccount(List<Account> accounts) {
		Set<Account> accountsAccess = new HashSet<Account>();
		for (Account account : accounts) {
			accountsAccess.add(account);
		}
		return accountsAccess;
	}

	// 1.3.5 Updating accounts
	public void transferFlowToAccount(Set<Account> accounts, List<Flow> flows) {
		for (Account account : accounts) {
			for (Flow flow : flows) {
				account.setBalance(flow);
			}
		}
		Predicate<Account> hasNegativeBalance = testAccount -> testAccount.getBalance() < 0;
		Optional<Account> accountWithNegativeBalance = accounts.stream().filter(hasNegativeBalance).findAny();
		if (accountWithNegativeBalance.isPresent()) {
			System.out.println("compte avec un solde négatif : " + accountWithNegativeBalance.get());
		} else {
			System.out.println("Aucun compte avec un solde négatif.");
		}
	}

	// 1.3.1 Adaptation of the table of accounts
	public void displaySpecificAccount(Set<Account> accounts) {
		accounts.stream().sorted(Comparator.comparingDouble(Account::getBalance)).map(account -> account.toString())
				.forEach(System.out::println);
	}

}
