package com.intellisoftkenya.onetooner.data;

import java.util.Map;

/**
 * An SQL query along with its parameters.
 * 
 * @author gitahi
 */
public class ParameterizedQuery {

    /**
     * The SQL query.
     */
    private final String sql;
    
    /**
     * The parameters for the SQL query.
     */
    private Map<Object, Integer> parameters;

    public ParameterizedQuery(String sql) {
        this.sql = sql;
    }

    public ParameterizedQuery(String sql, Map<Object, Integer> parameters) {
        this.sql = sql;
        this.parameters = parameters;
    }

    public String getSql() {
        return sql;
    }

    public Map<Object, Integer> getParameters() {
        return parameters;
    }

    public void setParameters(Map<Object, Integer> parameters) {
        this.parameters = parameters;
    }
}
