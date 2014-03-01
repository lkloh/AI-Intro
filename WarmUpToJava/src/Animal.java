
public abstract class Animal {

	private int age;
	private String name;
	
	public Animal() {
		age = 0;
		name = "";
	}
	
	public void setAge(int a) {
		age = a;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void printInfo() {
		System.out.println("Hi, I'm " + name + ". I'm " + age + " years old.");
	}


}
