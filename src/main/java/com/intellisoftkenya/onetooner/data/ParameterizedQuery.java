package com.intellisoftkenya.onetooner.data;

import java.util.List;

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
    private List<Parameter> parameters;

    public ParameterizedQuery(String sql) {
        this.sql = sql;
    }

    public ParameterizedQuery(String sql, List<Parameter> parameters) {
        this.sql = sql;
        this.parameters = parameters;
    }

    public String getSql() {
        return sql;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
}
