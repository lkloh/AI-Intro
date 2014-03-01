
public class TestingAnimals {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Dog dog = new Dog();
		Cat cat = new Cat();
		
		dog.setAge(3);
		dog.setName("Sparky");
		
		cat.setAge(8);
		cat.setName("Fluffy");
			
		dog.printInfo();
		cat.printInfo();
		
		dog.bark();
		cat.meow();

	}

}
