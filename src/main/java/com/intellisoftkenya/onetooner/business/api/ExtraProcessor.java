package com.intellisoftkenya.onetooner.business.api;

import com.intellisoftkenya.onetooner.business.OneToOneMigrator;
import com.intellisoftkenya.onetooner.data.OneToOne;

/**
 * Interface to be implemented by classes which provide auxiliary processing
 * that is not provided by {@link OneToOneMigrator}. Such processing may be
 * executed before or after the processing provided by {@link OneToOneMigrator}
 * according to the methods 
 * {@link OneToOne#setPreProcessor(com.intellisoftkenya.onetooner.business.api.ExtraProcessor)}
 * and
 * {@link OneToOne#setPostProcessor(com.intellisoftkenya.onetooner.business.api.ExtraProcessor)}.
 *
 * @author gitahi
 */
public interface ExtraProcessor {

    /**
     * Provides any auxiliary pre or post processing of the associated {@link OneToOne}
     * object.
     * 
     * @param oto the {@link OneToOne} object associated with this processor
     */
    public void process(OneToOne oto);
}
