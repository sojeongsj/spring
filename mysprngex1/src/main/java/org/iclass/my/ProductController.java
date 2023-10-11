package org.iclass.my;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductController {
	
	private ProductService service;
	
	
	/*
	 * public ProductController() {
	 * System.out.println("::::: ProductController 기본 생성자 :::::"); }
	 */
	 
	
	public void setService(ProductService service) {   
		//파라미터 의존관계 - 타입이 bean 이면 @Autowired(자동주입-ProductService type 일치하는 bean 선택)
		System.out.println("---- ProductController setter 메소드 ----");
		this.service = service;
	} 
	
	
	//2)생성자 주입
	//	@Autowired   //3)setter 주입할 때 주석처리
	public ProductController(ProductService service) {
		System.out.println("::::: ProductController 커스텀 생성자 - ProductService bean 주입 :::");
		this.service=service;
	}
	
	public void product() {
		System.out.println("---- ProductController product() 메소드 ----");
		service.product();
	}
}
