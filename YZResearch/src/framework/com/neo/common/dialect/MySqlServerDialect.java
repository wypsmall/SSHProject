package com.neo.common.dialect;

import org.hibernate.dialect.SQLServerDialect;

public class MySqlServerDialect extends SQLServerDialect {
	static int getLastIndexOfOrderBy(String sql){
        return sql.toLowerCase().lastIndexOf("order by ");
    }

	@Override
	public String getLimitString(String querySelect, int offset, int limit) {
		int lastIndexOfOrderBy = getLastIndexOfOrderBy(querySelect);
        //�?���?order by 或第�?��的情况下
        if(lastIndexOfOrderBy<0 || querySelect.endsWith(")") || offset==0)
            return super.getLimitString(querySelect, 0, limit);
        else {
            //取出 order by 语句
            String orderby = querySelect.substring(lastIndexOfOrderBy, querySelect.length());
            //取出 from 前的内容
            int indexOfFrom = querySelect.toLowerCase().indexOf("from ");
            String selectFld = querySelect.substring(0,indexOfFrom);
            //取出 from 语句后的内容
            String selectFromTableAndWhere = querySelect.substring(indexOfFrom, lastIndexOfOrderBy);
            StringBuffer sql = new StringBuffer(querySelect.length()+100);
            sql.append("select * from (")
                .append(selectFld)
                .append(",ROW_NUMBER() OVER(").append(orderby).append(") as _page_row_num_hb ")
                .append(selectFromTableAndWhere).append(" ) temp ")
                .append(" where  _page_row_num_hb BETWEEN  ")
                .append(offset+1).append(" and ").append(limit);
            return sql.toString();
        }
	}

	@Override
	public boolean supportsLimitOffset() {
		return true;
	}
	
	
}
