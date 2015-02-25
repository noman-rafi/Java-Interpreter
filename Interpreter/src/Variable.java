
public class Variable {
	String name;
	int value;
	
	Variable(String n, int v){
		this.name = n;
		this.value = v;
	}
	
	public int get(){
		return this.value;
	}
}
