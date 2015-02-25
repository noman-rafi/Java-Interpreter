import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;


public class interpreter {
	
	//public Vector<String> lines = new Vector<String>(50);
	public String[] lines = new String[50];

	public Vector<String> vars = new Vector<String>(50);
	HashMap<String, Variable> variables = new HashMap();
	
	public void readFile(){
		
		try {
			BufferedReader buffRead = new BufferedReader(new FileReader("D:\\program.txt"));
			String currLine;
			try {
				int i =0;
				while ((currLine = buffRead.readLine()) != null) {
					//System.out.println(currLine);
					//lines.addElement(currLine);
					
					lines[i] = currLine;
					//System.out.println(lines.get(i));
					i++;
				}
				//System.out.println(i);
				i =0;
				//Iterator it = lines.iterator();
				
				//while(it.hasNext()){
				while(lines[i]!=null){
					//String[] parts = lines.get(i).split(" ");
					String[] parts = lines[i].split(" ");
					String part1 = parts[0];
					//String part2 = parts[1];
					//String part3 = parts[2];
					//String part4 = parts[3];
					
					if(part1.equals("let")){
						declare(i);
					}
					
					else if(part1.equals("print")){
						print(i);
					}
					
					else{
						arthOp(i);
					}
			
					//Variable v = new Variable(part2,Integer.parseInt(part4));
					//System.out.println("Value of x:" + ((Variable) variables.get(part2)).get());
					i++;
				}
				//System.out.println(i);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public int checkVar(String var){
		if(variables.containsKey(var)){
			return 1;
		}
		else
			return -1;
	}
	
	public void declare(int i){
		
		//String[] parts = lines.get(i).split(" ");
		String[] parts = lines[i].split(" ");
		String part1 = parts[0];
		String part2 = parts[1];
		String part3 = parts[2];
		String part4 = parts[3];
		if(checkVar(part2) == -1){
			Variable v = new Variable(part2,Integer.parseInt(part4));
			variables.put(part2, new Variable(part2,Integer.parseInt(part4)));

			//System.out.println("Value of "+part2+" declared: " + ((Variable) variables.get(part2)).get());
		}
		else{
			System.out.println("Error: Redeclaration of variable "+part2);
			System.exit(0);
		}
		
	}
	
	public void print(int i){
		//System.out.println("Printing");
		//String[] parts = lines.get(i).split(" ");
		String[] parts = lines[i].split(" ");
		String part1 = parts[0];
		String part2 = parts[1];
		
		if(variables.get(part2) != null){
			System.out.println(((Variable) variables.get(part2)).get());
		}
	}
	
	public void arthOp(int i){
		//String[] parts = lines.get(i).split(" ");
		String[] parts = lines[i].split(" ");
		for(int j=0;j<parts.length; j++){
			if(isNumeric(parts[j])){
			
			}
			else if(checkVar(parts[j]) == -1){
				System.out.println("Error: Unknown Variable "+parts[j]);
				System.exit(0);
			}
			j++;
		}
		//System.out.println("All variables OK!");
		String equation = "";
		if(!parts[1].equals("=")){
			System.out.println("Syntax error on token "+ parts[1]+", invalid Assignment Operator");
			System.exit(0);			
		}
		else if(parts[1].equals("=")){
			for(int j=2;j<parts.length; j++){
				
				if(parts[j].matches("^([A-Za-z])+$") && parts[j]!="+" && parts[j]!="*" && parts[j]!="-" && parts[j]!="/" && parts[j]!="="){
					//System.out.println(parts[j]);
					int value = ((Variable) variables.get(parts[j])).get();
					//System.out.println("Value: "+value);
					equation= equation.concat(Integer.toString(value));
				}
				else{
					equation= equation.concat(parts[j]);
				}
				equation= equation.concat(" ");
			}
		}
		
		//System.out.println(equation);
		
		ExpressionEvaluator EE = new ExpressionEvaluator();
		
		int result = EE.evaluate(equation);
		//System.out.println(result);
		
		variables.put(parts[0],new Variable(parts[0],result));
	}
	
	public boolean isNumeric(String s) {
		try  		{  
			double d = Double.parseDouble(s);  
		}  
		catch(NumberFormatException nfe)  		{  
			return false;  
		}  
		return true;  
	}
	


}
