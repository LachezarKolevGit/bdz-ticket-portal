package bg.tusofia.vvps.ticketsystem.client;

public class Client {

	private int age;
	private boolean hasFamily;
	private boolean hasFamilyWithChildBelow16;
	
	public Client(int age, boolean hasFamily, boolean hasFamilyWithChildBelow16) {
		this.age = age;
		 this. hasFamily = hasFamily;
		 this. hasFamilyWithChildBelow16 = hasFamilyWithChildBelow16;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isHasFamily() {
		return hasFamily;
	}

	public void setHasFamily(boolean hasFamily) {
		this.hasFamily = hasFamily;
	}

	public boolean isHasFamilyWithChildBelow16() {
		return hasFamilyWithChildBelow16;
	}

	public void setisHasFamilyWithChildBelow16(boolean hasFamilyWithChildBelow16) {
		this.hasFamilyWithChildBelow16 = hasFamilyWithChildBelow16;
	}
	
	
	
}
