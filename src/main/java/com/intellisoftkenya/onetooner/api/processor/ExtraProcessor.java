package com.intellisoftkenya.onetooner.api.processor;

import com.intellisoftkenya.onetooner.business.OneToOneMigrator;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;

/**
 * Interface to be implemented by classes which provide auxiliary processing
 * that is not provided by {@link OneToOneMigrator}. Such processing may be
 * executed before or after the processing provided by {@link OneToOneMigrator}
 * according to the methods 
 * {@link OneToOne#addPreProcessor(com.intellisoftkenya.onetooner.api.processor.ExtraProcessor) }
 * and
 * {@link OneToOne#addPostProcessor(com.intellisoftkenya.onetooner.api.processor.ExtraProcessor) }.
 * 
 * If you ask for any instances of {@link SqlExecutor} or it's subclasses, in your
 * implementations of this interface, do not close them. The {@link OneToOneMigrator} 
 * object will complain. Let it take care of that business.
 * them in 
 *
 * @author gitahi
 */
public interface ExtraProcessor {

    /**
     * Provides any auxiliary pre or post processing of the associated {@link OneToOne}
     * object.
     * 
     * @param oto the {@link OneToOne} object associated with this processor
     * 
     * @throws java.lang.Exception if anything goes wrong with the processing
     */
    public void process(OneToOne oto) throws Exception ;
}
