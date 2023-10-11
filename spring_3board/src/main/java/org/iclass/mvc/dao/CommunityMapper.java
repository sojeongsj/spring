package org.iclass.mvc.dao;

import java.util.List;
import java.util.Map;
import org.iclass.mvc.dto.Community;

public interface CommunityMapper {
	//글목록 페이징
	List<Community> pagelist(Map<String,Integer> map);
	int count();
	//글 수정,글 읽기
	Community selectByIdx(long idx);
	//글 읽기 
	void setReadCount(long idx);
	//글 쓰기
	int insert(Community vo);
	int update(Community vo);
	int delete(long idx);
	
}
