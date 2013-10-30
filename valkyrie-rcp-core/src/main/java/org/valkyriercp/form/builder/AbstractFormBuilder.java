package org.valkyriercp.form.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.Assert;
import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.factory.ComponentFactory;
import org.valkyriercp.form.binding.Binding;
import org.valkyriercp.form.binding.BindingFactory;
import org.valkyriercp.form.binding.swing.ComboBoxBinder;
import org.valkyriercp.rules.constraint.Constraint;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for form builders.
 *
 * @author oliverh
 * @author Mathias Broekelmann
 */
@Configurable
public abstract class AbstractFormBuilder {

	private final BindingFactory bindingFactory;

    @Autowired
	private ComponentFactory componentFactory;

	private FormComponentInterceptor interceptor;

    @Autowired
	private FormComponentInterceptorFactory interceptorFactory;

	/**
	 * Default constructor providing the {@link BindingFactory}.
	 *
	 * @param bindingFactory the factory creating the {@link Binding}s.
	 */
	protected AbstractFormBuilder(BindingFactory bindingFactory) {
		Assert.notNull(bindingFactory);
		this.bindingFactory = bindingFactory;
	}

	/**
	 * Returns the {@link FormComponentInterceptor} that will be used when
	 * creating a component with this builder.
	 */
	protected FormComponentInterceptor getFormComponentInterceptor() {
		if (interceptor == null) {
			interceptor = interceptorFactory.getInterceptor(getFormModel());
		}

		return interceptor;
	}

	/**
	 * Set the factory that delivers the {@link FormComponentInterceptor}.
	 */
	public void setFormComponentInterceptorFactory(FormComponentInterceptorFactory interceptorFactory) {
		this.interceptorFactory = interceptorFactory;
		this.interceptor = null;
	}

	/**
	 * Returns the {@link ComponentFactory} that delivers all the visual
	 * components.
	 */
	protected ComponentFactory getComponentFactory() {
		return componentFactory;
	}

	/**
	 * Set the {@link ComponentFactory}.
	 */
	public void setComponentFactory(ComponentFactory componentFactory) {
		this.componentFactory = componentFactory;
	}

	/**
	 * Returns the {@link BindingFactory}.
	 */
	protected BindingFactory getBindingFactory() {
		return bindingFactory;
	}

	/**
	 * Convenience method to return the formModel that is used in the
	 * {@link BindingFactory} and that should be used in the builder.
	 */
	protected FormModel getFormModel() {
		return bindingFactory.getFormModel();
	}

	/**
	 * Create a binding by looking up the appropriate registered binding.
	 *
	 * @param fieldName the name of the property to bind.
	 * @return the {@link Binding} for the property which provides a component
	 * that is bound to the valueModel of the property.
	 */
	protected Binding createDefaultBinding(String fieldName) {
		return getBindingFactory().createBinding(fieldName);
	}

	/**
	 * Create a binding that uses the given component instead of its default
	 * component.
	 *
	 * @param fieldName the name of the property to bind.
	 * @param component the component to bind to the property.
	 * @return the {@link Binding} that binds the component to the valuemodel of
	 * the property.
	 */
	protected Binding createBinding(String fieldName, JComponent component) {
		return getBindingFactory().bindControl(component, fieldName);
	}

	/**
	 * Create a binding that uses the given component instead of its default
	 * component. Additionally providing a context which is used by the binding
	 * to allow custom settings.
	 *
	 * @param fieldName the name of the property to bind.
	 * @param component the component to bind to the property.
	 * @param context a map of with additional settings providing a specific
	 * context.
	 * @return the {@link Binding} that binds the component to the valuemodel of
	 * the property.
	 */
	protected Binding createBinding(String fieldName, JComponent component, Map context) {
		return getBindingFactory().bindControl(component, fieldName, context);
	}

	/**
	 * Creates a component which is used as a selector in the form. This
	 * implementation creates a {@link JComboBox}
	 *
	 * @param fieldName the name of the field for the selector
	 * @param filter an optional filter constraint
	 * @return the component to use for a selector, not null
	 */
	protected JComponent createSelector(String fieldName, Constraint filter) {
		Map context = new HashMap();
		context.put(ComboBoxBinder.FILTER_KEY, filter);
		return getBindingFactory().createBinding(JComboBox.class, fieldName).getControl();
	}

	/**
	 * Creates a component which is used as a scrollpane for a component
	 *
	 * @param fieldName the fieldname for the scrollpane
	 * @param component the component to place into the scrollpane
	 * @return the scrollpane component
	 */
	protected JComponent createScrollPane(String fieldName, JComponent component) {
		return getComponentFactory().createScrollPane(component);
	}

	/**
	 * Create a password field for the given property.
	 *
	 * @param fieldName the name of the property.
	 * @return the password field.
	 */
	protected JPasswordField createPasswordField(String fieldName) {
		return getComponentFactory().createPasswordField();
	}

	/**
	 * Create a textarea for the given property.
	 *
	 * @param fieldName the name of the property.
	 * @return the textarea.
	 */
	protected JComponent createTextArea(String fieldName) {
		return getComponentFactory().createTextArea(5, 40);
	}

	/**
	 * Create a label for the property.
	 *
	 * @param fieldName the name of the property.
	 * @param component the component of the property which is related to the
	 * label.
	 * @return a {@link JLabel} for the property.
	 */
	protected JLabel createLabelFor(String fieldName, JComponent component) {
		JLabel label = getComponentFactory().createLabel("");
		getFormModel().getFieldFace(fieldName).configure(label);
		label.setLabelFor(component);

		FormComponentInterceptor interceptor = getFormComponentInterceptor();
		if (interceptor != null) {
			interceptor.processLabel(fieldName, label);
		}

		return label;
	}
}
