public class Person {
	private String _firstName, _lastName;
	
	public void setFirstName(String f) {
		_firstName = f;
	}
	
	public String getFirstName() {
		return _firstName;
	}
	
	public void setLastName(String l) {
		_lastName = l;
	}
	
	public String getLastName() {
		return _lastName;
	}
	
	public String getFullName() {
		return _firstName + " " + _lastName;
	}
}
