package com.acme.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.acme.db.mongodb.*;

public class Menus {
		int input;
		
		final String[] mainOps = {"1. Query", "2. Add", "3. Edit", "4. Remove", "5. Quit"};
		final String[] queryOps = {"1. Query All", "2. Query with Constraints", "3. Main Menu"};
		final String[] addOps = {"1. Add One", "2. Export CSV" ,"3. Main Menu"};
		final String[] editOps = {"1. Edit", "2. Main Menu"};
		final String[] removeOps = {"1. Remove all", "2. Remove with Contraints", "3. Main Menu"};
		QueryDriver qd = new QueryDriver();
		InsertDriver id = new InsertDriver();
		
		public void getNextMenu(int menuNum){
			switch (menuNum){
			case 1://provide Query Menu Options
				System.out.println("You select QUERY menu!");
				showMenu(queryOps); //show QUERY menu
				input = getInput(queryOps.length); //get QUERY Menu input
				queryChoice(input); //Execute Query Option
				while(input!=queryOps.length){
					if(input==queryOps.length){ //Go back to main menu
						showMenu(mainOps);
						break;
					}
					else{ //stay on QUERY menu
						showMenu(queryOps);
						input = getInput(queryOps.length);
						queryChoice(input);
					}
				}
				break;
			case 2: 
				System.out.println("You select ADD menu!");
				showMenu(addOps); //show ADD menu
				input = getInput(addOps.length); //get ADD Menu input
				addChoice(input); //Execute ADD Option
				while(input!=addOps.length){
					if(input==addOps.length){ //Go back to main menu
						showMenu(mainOps);
						break;
					}
					else{ //show ADD menu
						showMenu(addOps);
						input = getInput(addOps.length);
						addChoice(input);
					}
				}
				break;
			case 3:
				System.out.println("You select EDIT menu!");
				showMenu(editOps);
				getInput(editOps.length);
				//Handle Selection
				break;
			case 4:
				System.out.println("You select REMOVE menu!");
				showMenu(removeOps);
				getInput(removeOps.length);
				//Handle Selection
				break;
			case 5:
				System.out.println("Good Bye!");
				break;
			}	
		}

		public void queryChoice(int input){
			switch (input){
			case 1: //QUERY ALL in a collection	
				System.out.println("You chose to QUERY ALL");
				qd.query(input);
				break;
			case 2:
				System.out.println("You chose to QUERY WITH CONTSTRAINTS");
				qd.query(input);
				break;
			case 3:
				break;
			}
		}
		
		public void addChoice(int input){
			switch (input){
			case 1:	
				System.out.println("You chose to ADD Inventory!");
				id.insert();
				break;
			case 2:
				break;
			case 3:
				break;
			}
		}
		
		public void showMenu(String[] o){
			System.out.println("Select from the following Options:");
			for (int i =0; i<o.length; i++){
				System.out.printf("\t%-10s\n", o[i]);
			}
		}
		
		public int getInput(int menuSize){
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String s = br.readLine();
				int input = Integer.parseInt(s);
				boolean validInput = checkInput(input, menuSize);
				if (validInput){
					return input;
				}
				return 0;
			} catch (NumberFormatException e) {
				System.out.println("\nPlease enter a valid menu option!\n");
				//e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 0;
		}

		public boolean checkInput(int in, int max) {
		// TODO Auto-generated method stub
		if (in<1 || in>max){
			System.out.println("Please enter a valid Menu valid menu option no greater then " + max);
			return false;
		}
		return true;
	}
}
