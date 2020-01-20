package com.yaps.petstore;

/**
 * This class represents a customer for the YAPS company.
 */
public final class Customer {

	// ======================================
	// =             Attributes             =
	// ======================================
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


	// ======================================
	// =            Constructors            =
	// ======================================
	public Customer() {
	}

	public Customer(final String id) {
		_id = id;
	}

	/**
	 * @param id
	 * @param firstname
	 * @param lastname
	 */
	public Customer(final String id, final String firstname, final String lastname)  {
		_id = id;
		_firstname = firstname;
		_lastname = lastname;
	}


	/**
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @param telephone
	 * @param street1
	 * @param street2
	 * @param city
	 * @param state
	 * @param zipcode
	 * @param country
	 */
	public Customer(String _id, String _firstname, String _lastname, String _telephone, String _street1, String _street2, String _city, String _state, String _zipcode, String _country) {
		this._id = _id;
		this._firstname = _firstname;
		this._lastname = _lastname;
		this._telephone = _telephone;
		this._street1 = _street1;
		this._street2 = _street2;
		this._city = _city;
		this._state = _state;
		this._zipcode = _zipcode;
		this._country = _country;
	}

	// ======================================
	// =           check methods          =
	// ======================================
	/**
	 * This method checks the integrity of the object data.
	 * 
	 * @throws CustomerCheckException if data is invalid
	 */
	public void checkData() throws CustomerCheckException {
		if (_firstname == null || "".equals(_firstname))
				throw new CustomerCheckException("Invalid customer first name");
		if (_lastname == null || "".equals(_lastname))
			throw new CustomerCheckException("Invalid customer last name");
	}

	// ======================================
	// =         Getters and Setters        =
	// ======================================
	public String getId() {
		return _id;
	}

	public String getFirstname() {
		return _firstname;
	}

	public void setFirstname(final String firstname) {
		_firstname = firstname;
	}

	public String getLastname() {
		return _lastname;
	}

	public void setLastname(final String lastname) {
		_lastname = lastname;
	}

	public String getTelephone() {
		return _telephone;
	}

	public void setTelephone(final String telephone) {
		_telephone = telephone;
	}

	public String getStreet1() {
		return _street1;
	}

	public void setStreet1(final String street1) {
		_street1 = street1;
	}

	public String getStreet2() {
		return _street2;
	}

	public void setStreet2(final String street2) {
		_street2 = street2;
	}

	public String getCity() {
		return _city;
	}

	public void setCity(final String city) {
		_city = city;
	}

	public String getState() {
		return _state;
	}

	public void setState(final String state) {
		_state = state;
	}

	public String getZipcode() {
		return _zipcode;
	}

	public void setZipcode(final String zipcode) {
		_zipcode = zipcode;
	}

	public String getCountry() {
		return _country;
	}

	public void setCountry(final String country) {
		_country = country;
	}

	public String toString() {
		final StringBuffer buf = new StringBuffer();
		buf.append("\n\tCustomer {");
		buf.append("\n\t\tId=").append(_id);
		buf.append("\n\t\tFirst Name=").append(_firstname);
		buf.append("\n\t\tLast Name=").append(_lastname);
		buf.append("\n\t\tTelephone=").append(_telephone);
		buf.append("\n\t\tStreet 1=").append(_street1);
		buf.append("\n\t\tStreet 2=").append(_street2);
		buf.append("\n\t\tCity=").append(_city);
		buf.append("\n\t\tState=").append(_state);
		buf.append("\n\t\tZipcode=").append(_zipcode);
		buf.append("\n\t\tCountry=").append(_country);
		buf.append("\n\t}");
		return buf.toString();
	}
    
}
