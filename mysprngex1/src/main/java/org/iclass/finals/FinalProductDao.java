package org.iclass.finals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
public class FinalProductDao {
	
	private final int count;
	
	public FinalProductDao(@Value("10") int count) {
		System.out.println("★★FinalProductDao★★");
		this.count = count;
		
	}
	
	public int product() {
		System.out.println("---- ProductDao product() 메소드 ----");
		System.out.println("수량 : " + count);
		return count;
	}
}
