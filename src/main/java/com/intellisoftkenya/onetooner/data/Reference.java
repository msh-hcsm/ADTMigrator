package com.intellisoftkenya.onetooner.data;

import com.intellisoftkenya.onetooner.api.translator.ValueInferrer;
import com.intellisoftkenya.onetooner.api.translator.ValueTranslator;
import com.intellisoftkenya.onetooner.business.OneToOneMigrator;

/**
 * A reference to an Destination database table to which this column is a
 * foreign key.
 */
public class Reference {

    /**
     * Defines the set of actions that may be be taken if the reference is null after
     * all strategies configured for it have been exhausted. Supported strategies
     * are read from lookup table, infer from lookup table, create new value or
     * borrow value from preceeding record.
     */
    public enum NullAction {

        THROW_EXCEPTION,
        LOG_WARNING,
        DO_NOTHING
    }

    /**
     * Name of the referenced table
     */
    private final String table;

    /**
     * Name of the column containing the text by which to query for primary
     * keys. Defaults to "name" if not set.
     */
    private String column = "name";

    /**
     * Name of the referenced primary key. Defaults to "id" if not set.
     */
    private String pk = "id";

    /**
     * A String value that may optionally be appended in front of the values for
     * this column before using it to reference a destination table. It is used
     * in conjunction with {@link Column#prefix}.
     */
    private String prefix;

    /**
     * Whether or not an attempt should be made to infer the referenced value by
     * some custom means if such a value cannot be established normally. Useful
     * when a source column contains legacy data that is not part of an existing
     * referential relationship.
     *
     * Defaults to false if not set. This strategy is used first i.e. if reading
     * out of a lookup table produces null. See {@link OneToOneMigrator#setParameterFromReference(
     * com.intellisoftkenya.onetooner.data.Reference, java.lang.String) }
     */
    private boolean inferable = false;

    /**
     * Whether or not a record should be created if the text from
     * {@link Reference#column} is missing. Records are created subject to the
     * table allowing insertion to column alone. If for instance there are other
     * non-nullable columns which do not have defaults, creation will fail.
     *
     * Defaults to false if not set. This strategy is used second i.e. if the
     * inferring strategy produces null or is turned off. See {@link OneToOneMigrator#setParameterFromReference(
     * com.intellisoftkenya.onetooner.data.Reference, java.lang.String) }
     */
    private boolean creatable = false;

    /**
     * Whether or not a referenced value should be borrowed from the preceeding
     * record if none is available for the current record. Useful when formally
     * one-to-one Source database relationships have been converted into
     * one-to-many relationships. Records from the many side of the relationship
     * must be ordered by the referencing column for this to work correctly.
     *
     * Defaults to false if not set. This strategy is used last i.e. if the
     * create and borrow strategies produce no values or are turned off. See {@link OneToOneMigrator#setParameterFromReference(
     * com.intellisoftkenya.onetooner.data.Reference, java.lang.String) }
     */
    private boolean borrowable = false;

    /**
     * Whether or not a reference value must be found either from by reading it
     * out of a lookup table, inferring it, creating it or borrowing it, in that
     * order. If set to false then an exception is thrown if none of these
     * strategies produces a value.
     */
    private NullAction nullAction = NullAction.DO_NOTHING;

    /**
     * A {@link ValueTranslator} to process a reference value to be used as the
     * data for cells in this column. This is useful when the value cannot be
     * easily deduced and must be obtained by some non-trivial means.
     */
    private ValueTranslator valueTranslator;

    /**
     * A {@link ValueInferrer} to process a reference value to be used as the
     * data for cells in this column. This is useful when the source value has
     * been obsoleted or otherwise can't be used as-is.
     */
    private ValueInferrer valueInferrer;

    public Reference(String table) {
        this.table = table;
    }
    
    public Reference(String table, NullAction nullAction) {
        this(table);
        this.nullAction = nullAction;
    }
    
    public Reference(String table, boolean creatable) {
        this(table);
        this.creatable = creatable;
    }

    public Reference(String table, boolean creatable, ValueTranslator valueTranslator) {
        this(table, creatable);
        this.valueTranslator = valueTranslator;
    }

    public Reference(String table, boolean creatable, NullAction nullAction, ValueTranslator valueTranslator) {
        this(table, creatable);
        this.nullAction = nullAction;
        this.valueTranslator = valueTranslator;
    }

    public Reference(String table, String column) {
        this(table);
        this.column = column;
    }

    public Reference(String table, String column, NullAction nullAction) {
        this(table, column);
        this.nullAction = nullAction;
    }

    public Reference(String table, String column, String prefix) {
        this(table, column);
        this.prefix = prefix;
    }

    public Reference(String table, String column, String prefix, NullAction nullAction) {
        this(table, column);
        this.prefix = prefix;
        this.nullAction = nullAction;
    }

    public Reference(String table, String column, boolean borrowable, String prefix) {
        this(table, column);
        this.borrowable = borrowable;
        this.prefix = prefix;
    }

    public Reference(String table, String column, boolean borrowable, String prefix, NullAction nullAction) {
        this(table, column, borrowable, prefix);
        this.nullAction = nullAction;
    }

    public String getTable() {
        return table;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isInferable() {
        return inferable;
    }

    public void setInferable(boolean inferable) {
        this.inferable = inferable;
    }

    public boolean isCreatable() {
        return creatable;
    }

    public void setCreatable(boolean creatable) {
        this.creatable = creatable;
    }

    public boolean isBorrowable() {
        return borrowable;
    }

    public void setBorrowable(boolean borrowable) {
        this.borrowable = borrowable;
    }

    public NullAction getNullAction() {
        return nullAction;
    }

    public void setNullAction(NullAction nullAction) {
        this.nullAction = nullAction;
    }

    public ValueTranslator getValueTranslator() {
        return valueTranslator;
    }

    public void setValueTranslator(ValueTranslator valueTranslator) {
        this.valueTranslator = valueTranslator;
    }

    public ValueInferrer getValueInferrer() {
        return valueInferrer;
    }

    public void setValueInferrer(ValueInferrer valueInferrer) {
        this.valueInferrer = valueInferrer;
    }
}
