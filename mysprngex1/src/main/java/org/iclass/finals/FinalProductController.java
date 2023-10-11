package org.iclass.finals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FinalProductController {
	
	private final FinalProductService service;
	
	public int product() {
		System.out.println("♥♥FinalProductController♥♥");
		int result = service.product();
		System.out.println("dao result");
		return result;
		
		
	}
}
