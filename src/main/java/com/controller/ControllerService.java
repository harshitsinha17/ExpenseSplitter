package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.participant.Users;
import com.payment.PaymentDetails;

@RestController
@RequestMapping(value = "/api/v1/splitter")
public class ControllerService {
	
	List<Users> users  = new ArrayList<>();
	
	
	ControllerService(){
		users.add(new Users("Ram"));
		users.add(new Users("Ajay"));
		users.add(new Users("Kumar"));
		users.add(new Users("Krish"));
	}
	
	/*
	* Method to handle calculation of money owed by the other participants when one of the participant 
	* shares the expense report (POST).
	*
	* @param  details
	*		: The detailed report of the expense shared by one of the partipant to be feeded
	*		  into the system.
	* @return representing list of particpant details along with the money they owe.
	*/
	
	@RequestMapping(value="/calculate", method = RequestMethod.POST, produces="application/json")
	@ResponseStatus(value=HttpStatus.OK)
	public List<Users> getOwedMoney(@RequestBody PaymentDetails details) {
		
		StringBuffer borrowerList = new StringBuffer();
		float totalAmountOwed = 0f;
		
		float multiplier = getMultiplier(details.getSplit_by());
		System.out.println("The negative amount signify amount owed by others to him");
		
		Users payer = getPayer(users, details.getPaid_by());
		
		if(null == payer) {
			
			System.out.println("Payer could not be found in DB.");
			return users;
			
		}
		System.out.println("The payer is "+payer.getName());
		System.out.println("The extra amount paid by him is "+ ( details.getAmount() * multiplier  ) );
		payer.setAmount( payer.getAmount() + (-1 * details.getAmount() * multiplier ) );
		
		System.out.println("The payer amount has been updated to "+payer.getAmount());
		
		for(int i = 0;i<users.size();i++) {
			if( !( details.getPaid_by() ).equalsIgnoreCase(  (users.get(i)).getName()  ) ) {
				( users.get(i) ).setAmount( ( users.get(i) ).getAmount() + ( ( details.getAmount() * multiplier ) / (users.size()-1) )  );
			
				System.out.println("The borrowers amount has been updated to "+users.get(i).toString());
				
			}
		}
		
		
		
		for(Users participants : users) {
			if(participants.getAmount()<0) {
				totalAmountOwed = totalAmountOwed+(-1*participants.getAmount());
			}
			else {
				borrowerList.append(participants.getName()+", ");
			}
		}
		
		System.out.println("Scenario : "+totalAmountOwed+" amount is owed by "+borrowerList);
		
		return users;
	}
	
	/*
	* Method to get the details of the participant from the expense report shared 
	*
	* @param  users
	*		: The whole list of participants from where the details of the 
	*		  Payer is to be extracted and returned.
	* @param paid_by
	*		: The name of the person who made the expense.
	* @return User object response representing the details of the payer from local DB.
	*/
	
	public Users getPayer(List<Users> users, String paid_by) {
		
		for(Users participant : users ) {
			if( (participant.getName()).equalsIgnoreCase(paid_by) ) {
				return participant;
			}
		}
		return null;
	}
	
	
	/*
	* Method to calculate the multiplier based on the type of split passed from shared report
	* whether it is to split equally or some another ratio.
	*
	* @param  splitType:
	*		    Whether the money is to be shared equally or in some other ratio.
	*		: .
	* @return multiplier
	*/
	
	public float getMultiplier(String splitType) {
		
		System.out.println("Split type + "+splitType);
		float multiplier = 0f;
		
		if(splitType.equalsIgnoreCase("equal")) {
			multiplier = (float)(users.size()-1)/users.size();
		}
		else {
			multiplier = ((float)Integer.parseInt(splitType) )/100;
			
		}
		return multiplier;
	}
	
}
