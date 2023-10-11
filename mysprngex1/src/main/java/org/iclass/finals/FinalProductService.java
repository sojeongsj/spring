package org.iclass.finals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FinalProductService {
	
	private final FinalProductDao dao;			

	
	public int product() {
		System.out.println("☆☆FinalProductService☆☆");
		int result = dao.product();
		System.out.println("dao result : " + result);
		return result;
		
	}
}
