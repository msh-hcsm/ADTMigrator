package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.business.LookupValueReader;
import com.intellisoftkenya.onetooner.data.Column;
import com.intellisoftkenya.onetooner.data.LookupValue;
import com.intellisoftkenya.onetooner.data.OneToOne;

/**
 * Processes a {@link LookupValue} to retrieve the primary key and then resets
 * the value of the affected destination {@link Column#value} to the retrieved 
 * value.
 *
 * @author gitahi
 */
public class LookupValuePkProcessor implements ExtraProcessor {

    @Override
    public void process(OneToOne oto) throws Exception {
        for (Column source : oto.getColumnMappings().keySet()) {
            if (source.getName() == null) {
                Column destination = oto.getColumnMappings().get(source);
                LookupValue lookupValue = (LookupValue) destination.getValue();
                destination.setValue(new LookupValueReader().readId(lookupValue.getTable(), 
                        lookupValue.getValue()));
            }
        }
    }
}
