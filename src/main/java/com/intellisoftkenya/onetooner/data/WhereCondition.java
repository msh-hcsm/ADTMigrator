package com.intellisoftkenya.onetooner.data;

import java.sql.Types;

/**
 * An abstraction of an SQL WHERE clause condition.
 *
 * @author gitahi
 */
public class WhereCondition {

    /**
     * The column + operator concatenation for the WHERE clause condition. E.g.
     * "employee_id ="
     */
    private final String columnAndOperator;

    /**
     * The value for the WHERE clause condition.
     */
    private final Object value;

    /**
     * The SQL type ({@link Types}) of the {@link WhereCondition#value}.
     */
    private final int valueType;

    /**
     * The operator to use to combine this condition with another condition in
     * the same WHERE clause. Defaults to "AND".
     */
    private String combinationOperator = "AND";

    public WhereCondition(String columnAndOperator, Object value, int valueType) {
        this.columnAndOperator = columnAndOperator;
        this.value = value;
        this.valueType = valueType;
    }

    public String getColumnAndOperator() {
        return columnAndOperator;
    }

    public Object getValue() {
        return value;
    }

    public int getValueType() {
        return valueType;
    }

    public String getCombinationOperator() {
        return combinationOperator;
    }

    public void setCombinationOperator(String combinationOperator) {
        this.combinationOperator = combinationOperator;
    }
}
