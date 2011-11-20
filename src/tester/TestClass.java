package tester;

public class TestClass implements TestInterface{

	private String name;

	public TestClass(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void doIt() {
		System.out.println("It´s done.");
	}
	
	
}
