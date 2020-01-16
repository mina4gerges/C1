package lb.edu.isae.tp00;

import java.util.HashMap;

public class Customer {
	private static final HashMap<String, Customer> customers = new HashMap<>();;
	private String _id;
	private String _firstname;
	private String _lastname;
	private String _telephone;
	private String _street1;
	private String _street2;
	private String _city;
	private String _state;
	private String _zipcode;
	private String _country;
	private String _mail;

	public String getId() {
		return _id;
	}

	public void setId(String id) {
		this._id = id;
	}

	public String getFirstname() {
		return _firstname;
	}

	public void setFirstname(String firstname) {
		this._firstname = firstname;
	}

	public String getLastname() {
		return _lastname;
	}

	public void setLastname(String lastname) {
		this._lastname = lastname;
	}

	public String getTelephone() {
		return _telephone;
	}

	public void setTelephone(String telephone) {
		this._telephone = telephone;
	}

	public String getStreet1() {
		return _street1;
	}

	public void setStreet1(String street1) {
		this._street1 = street1;
	}

	public String getStreet2() {
		return _street2;
	}

	public void setStreet2(String street2) {
		this._street2 = street2;
	}

	public String getCity() {
		return _city;
	}

	public void setCity(String city) {
		this._city = city;
	}

	public String getState() {
		return _state;
	}

	public void setState(String state) {
		this._state = state;
	}

	public String getZipcode() {
		return _zipcode;
	}

	public void setZipcode(String zipcode) {
		this._zipcode = zipcode;
	}

	public String getCountry() {
		return _country;
	}

	public void setCountry(String country) {
		this._country = country;
	}

	public String getMail() {
		return _mail;
	}

	public void setMail(String mail) {
		this._mail = mail;
	}

	public Customer(String id, String firstName, String lastName) {
		_id = id;
		_firstname = firstName;
		_lastname = lastName;
	}
	public Customer(String _id, String _firstname, String _lastname, String _telephone, String _street1,
			String _street2, String _city, String _state, String _zipcode, String _country, String _mail) {
		this(_id, _firstname, _lastname);
		this._telephone = _telephone;
		this._street1 = _street1;
		this._street2 = _street2;
		this._city = _city;
		this._state = _state;
		this._zipcode = _zipcode;
		this._country = _country;
		this._mail = _mail;
	}

	public boolean checkId(String id) {
		return id.contentEquals(_id);
	}

	public boolean checkData() {
		if (_id == null || "".equals(_id))
			return false;
		if (_firstname == null || "".equals(_firstname))
			return false;
		if (_lastname == null || "".equals(_lastname))
			return false;
		return true;
	}

	public String getCheckDataError() {
		if (_id == null || "".equals(_id))
			return "Invalid id";
		if (_firstname == null || "".equals(_firstname))
			return "Invalid first name";
		if (_lastname == null || "".equals(_lastname))
			return "Invalid last name";
		return "";
	}

	public boolean checkMail() {
		String regex = "^[\\w-_\\.+]+[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return _mail.matches(regex);
	}

	public static boolean insert(Customer customer) {
		customers.put(customer.getId(), customer);
		return true;
	}

	public static boolean remove(String sid) {
		Customer c = customers.remove(sid);
		if (c==null) return false;
		else return true;
	}

	public static Customer find(String s) {
		Customer c = customers.get(s);
		return c;
	}

	@Override
	public String toString() {
		return String.format(
				"Customer [_id=%s, _firstname=%s, _lastname=%s, _telephone=%s, _street1=%s, _street2=%s, "
				+ "_city=%s, _state=%s, _zipcode=%s, _country=%s, _mail=%s]",
				_id, _firstname, _lastname, _telephone, _street1, _street2, _city, _state, _zipcode, _country, _mail);
	}
	



}
