package com.acme.console;
public class console {

	public static void main(String[] args) {
		
	//Variables and Constants
		Menus ops = new Menus(); //Menus access object
		int input = 0;
	
	//present MAIN options menu
		System.out.println("----[Welcome to Acme Inventory Managment System]----");
		while(input!=ops.mainOps.length){
			ops.showMenu(ops.mainOps);
			//get user choice
			input = ops.getInput(ops.mainOps.length); //call getInput with size of main menu array
			//load next menu
			ops.getNextMenu(input); //load menu corresponding to user input
		}
		System.out.println("Program Terminated");
	}
	
	
}
