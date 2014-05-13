package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.data.OneToOne;

/**
 * Goes through each transaction item and determines if it represents drugs in
 * or drugs out. That determination is made by examining both the account type
 * and the transaction type involved. The values are then updated accordingly.
 * 
 * This is necessary since the default migration puts the same value under in 
 * and out whereas only one of those attributes should have a value for any
 * given transaction item.
 * 
 * @author gitahi
 */
public class TransactionInOutUpdater implements ExtraProcessor {

    @Override
    public void process(OneToOne oto) {
        /**
         * Algorithm
         * ---------
         * Get each transaction item
         * Get the transaction to which this transaction item belongs
         * Get the transaction type of this transaction
         * Get the account of this transaction item
         * Get the account type of the account above
         * Then run the following if statement based on transaction type/account type combination
         * Action values mean the following:
         * UIN: Units In Null - This transaction represents Units Out so set Units In to NULL
         * UON: Units Out Null - This transaction represents Units In so set Units Out to NULL
         * *******************************************************************
         * Transaction Type     || Account Type     || Action
         * *********************||******************||************************
         * ---------------------||------------------||------------------------
         * Received from        || Supplier         || UIN
         * ---------------------||------------------||------------------------
         * Received from        || Store            || UIN
         * ---------------------||------------------||------------------------
         * Received from        || Patient          || UIN
         * ---------------------||------------------||------------------------
         */
    }   
}
