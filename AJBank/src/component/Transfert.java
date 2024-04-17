package component;

import java.time.LocalDate;

public class Transfert extends Flow {

	private int accountNumberIssuing;

	public Transfert(String comment, int identifier, double amount, int targetAccountNumber, boolean effect,
			LocalDate date, int accountNumberIssuing) {
		super(comment, identifier, amount, targetAccountNumber, effect, date);
		this.accountNumberIssuing = accountNumberIssuing;
	}

	public int getAccountNumberIssuing() {
		return accountNumberIssuing;
	}

	public void setAccountNumberIssuing(int accountNumberIssuing) {
		this.accountNumberIssuing = accountNumberIssuing;
	}

}
