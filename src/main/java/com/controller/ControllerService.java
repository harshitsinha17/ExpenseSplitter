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
	
	@RequestMapping(value="/calculate", method = RequestMethod.POST, produces="application/json")
	@ResponseStatus(value=HttpStatus.OK)
	public List<Users> getOwedMoney(@RequestBody PaymentDetails details) {
		
		StringBuffer borrowerList = new StringBuffer();
		float totalAmountOwed = 0f;
		
		float multiplier = getMultiplier(details.getSplit_by());
		System.out.println("The negative amount signify amount owed by others to him");
		
		System.out.println("************");
		
		Users payer = getPayer(users, details.getPaid_by());
		System.out.println("The payer is "+payer.getName());
		System.out.println("The extra amount paid by him is "+ ( details.getAmount() * multiplier  ) );
		if(null != payer) {
			payer.setAmount( payer.getAmount() + (-1 * details.getAmount() * multiplier ) );
			
		}
		
		System.out.println("************");
		
		System.out.println("The payer amount has been updated to "+payer.getAmount());
		
		for(int i = 0;i<users.size();i++) {
			if( !( details.getPaid_by() ).equalsIgnoreCase(  (users.get(i)).getName()  ) ) {
				( users.get(i) ).setAmount( ( users.get(i) ).getAmount() + ( ( details.getAmount() * multiplier ) / (users.size()-1) )  );
			
				System.out.println("The borrowers amount has been updated to "+users.get(i).toString());
				
			}
		}
		
		System.out.println("************");
		
		
		
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
	
	
	public Users getPayer(List<Users> users, String paid_by) {
		
		for(Users u : users ) {
			if( (u.getName()).equalsIgnoreCase(paid_by) ) {
				return u;
			}
		}
		return null;
	}
	
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
