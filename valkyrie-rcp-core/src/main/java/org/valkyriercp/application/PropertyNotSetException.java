package org.valkyriercp.application;

/**
 * Indicates that the initialization of an object has not set a required
 * property.
 *
 * @author Kevin Stembridge
 * @since 0.3
 *
 */
public class PropertyNotSetException extends ConfigurationException {

	private static final long serialVersionUID = 6848949416219396182L;

	/**
	 * Throws an instance of this exception if the given {@code propertyValue}
	 * is null.
	 *
	 * @param propertyValue The value of the property.
	 * @param propertyName The name of the property.
	 * @param beanClass The class on which the property is supposed to be set.
	 *
	 * @throws PropertyNotSetException if {@code propertyValue} is null.
	 */
	public static void throwIfNull(Object propertyValue, String propertyName, Class beanClass) {

		if (propertyValue == null) {
			throw new PropertyNotSetException(beanClass, propertyName);
		}

	}

	private final Class beanClass;

	private final String propertyName;

	/**
	 * Creates a new {@code PropertyNotSetException} with the specified bean
	 * class and property name.
	 *
	 * @param beanClass The class of the JavaBean that has an uninitialized
	 * property.
	 * @param propertyName The name of the property that has not been set.
	 */
	public PropertyNotSetException(Class beanClass, String propertyName) {
		this("The [" + propertyName + "] property of class [" + beanClass + "] has not been initialized.", beanClass,
				propertyName);

	}

	private PropertyNotSetException(String message, Class beanClass, String propertyName) {
		super(message);
		this.beanClass = beanClass;
		this.propertyName = propertyName;

	}

	/**
	 * Returns the class of the JavaBean that has the uninitialized property.
	 * @return Returns the value of the beanClass field.
	 */
	public Class getBeanClass() {
		return this.beanClass;
	}

	/**
	 * Returns the name of the property that has not been set.
	 * @return Returns the value of the propertyName field.
	 */
	public String getPropertyName() {
		return this.propertyName;
	}

}