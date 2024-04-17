package component;

import java.time.LocalDate;

public class Debit extends Flow {

	public Debit(String comment, int identifier, double amount, int targetAccountNumber, boolean effect,
			LocalDate date) {
		super(comment, identifier, amount, targetAccountNumber, effect, date);
		// TODO Auto-generated constructor stub
	}

}
