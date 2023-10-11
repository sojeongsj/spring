package org.iclass.dao;

import java.util.List;
import java.util.Map;

import org.iclass.dto.PageRequestDTO;
import org.iclass.dto.TodoDto;

public interface TodoMapper {
	int insert(TodoDto dto);
	List<TodoDto> selectAll();
	TodoDto selectOne(long tno);
	//long getCount();
	long getCount(PageRequestDTO dto);
	//List<TodoDto> selectPageList(Map<String,Integer> map);
	List<TodoDto> selectPageList(PageRequestDTO map);
	
	
}
