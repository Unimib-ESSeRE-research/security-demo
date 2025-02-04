package org.grantharper.websecurity.dao;

import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class BankAccountInsecureDao {

	// Pattern to validate only letters (uppercase and lowercase)
	private static final Pattern VALID_PATTERN = Pattern.compile("^[a-zA-Z]+$");

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void updateBalance(Long accountId, Double balance){
		
		StringBuilder queryBuilder = new StringBuilder("UPDATE bank_account SET balance=");
		queryBuilder.append(balance);
		queryBuilder.append(" WHERE account_id=");
		queryBuilder.append(accountId);
		queryBuilder.append(";");
		
		jdbcTemplate.execute(queryBuilder.toString());
	}
	
	//hack '; drop table customer; --
	//this will delete the customer table from the database
	//'; update bank_account set balance=999999999 where customer_id=1; --
	public void updateCustomerName(String firstName, String lastName, String username) throws IllegalArgumentException{

		StringBuilder queryBuilder = new StringBuilder("UPDATE customer SET first_name='");

		if (isValid(firstName) && isValid(lastName) && isValid(username)) {

			queryBuilder.append(firstName);
			queryBuilder.append("', last_name='");
			queryBuilder.append(lastName);
			queryBuilder.append("' WHERE username='");
			queryBuilder.append(username);
			queryBuilder.append("';");

		} else {
			throw new IllegalArgumentException("Invalid input");
		}

		
		jdbcTemplate.execute(queryBuilder.toString());
	}

	/**
	 * Checks if the string contains only letters (a-z, A-Z).
	 * 
	 * @param input The string to check
	 * @return true if valid, false otherwise
	 */
	private static boolean isValid(String input) {
		return VALID_PATTERN.matcher(input).matches();
	}
	
}
