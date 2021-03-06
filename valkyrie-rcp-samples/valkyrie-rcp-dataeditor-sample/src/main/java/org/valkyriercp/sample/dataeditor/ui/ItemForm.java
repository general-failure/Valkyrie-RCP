package org.valkyriercp.sample.dataeditor.ui;

import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.builder.MigLayoutFormBuilder;
import org.valkyriercp.sample.dataeditor.domain.Item;
import org.valkyriercp.widget.TabbedForm;

public class ItemForm extends TabbedForm
{
    protected Tab[] getTabs()
    {
        MigLayoutFormBuilder builder = new MigLayoutFormBuilder(getBindingFactory());
        setFocusControl(builder.addPropertyAndLabel("name", "wrap")[1]);
        builder.addPropertyAndLabel("description", "wrap");
        builder.addPropertyAndLabelWithBinder("supplier", "supplierBinder", "grow");

        // or you could use the following block, which uses the generic lookupBinder
        // and configures it through context parameters
        /*
        Map<String, Object> binderContext = new HashMap<String, Object>();
        binderContext.put(LookupBinder.REQUIRED_SOURCE_CLASS_KEY, Supplier.class);
        binderContext.put(LookupBinder.DATA_EDITOR_ID_KEY, "supplierDataEditor");
        binderContext.put(LookupBinder.OBJECT_LABEL_FUNCTION_KEY, new Function<Supplier, String>() {
            @Override
            public String apply(Supplier supplier) {
                if(supplier == null)
                    return null;
                return supplier.getName();
            }
        });
        binderContext.put(LookupBinder.CREATE_FILTER_FROM_FIELD_FUNCTION_KEY, new Function<String, SupplierFilter>() {
            @Override
            public SupplierFilter apply(String textFieldValue) {
                SupplierFilter s = new SupplierFilter();
                s.setNameContains(textFieldValue);
                return s;
            }
        });
        binderContext.put(LookupBinder.DIALOG_SIZE_KEY, new Dimension(800,600));
        builder.addPropertyAndLabel("supplier", "genericLookupBinder", binderContext);
         */

        return new Tab[] { new Tab("detail", builder.getPanel())};
    }

    @Override
    public FormModel createFormModel() {
        return getApplicationConfig().formModelFactory().createFormModel(new Item(),"itemForm");
    }
}
