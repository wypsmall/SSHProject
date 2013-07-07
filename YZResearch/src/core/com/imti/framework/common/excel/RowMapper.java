package com.imti.framework.common.excel;

import java.util.List;

import jxl.Cell;


public interface RowMapper {
	Object mapRow(Cell [] row, int rowNum, List invalidCells) ;
}
