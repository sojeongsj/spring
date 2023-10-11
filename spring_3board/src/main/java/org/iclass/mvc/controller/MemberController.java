package org.iclass.mvc.controller;

import org.iclass.mvc.dto.BookUser;
import org.iclass.mvc.service.BookUserService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {		//회원가입 구현해보기
	
	private final BookUserService service;
	
	@RequestMapping("/test")
	public void test(@SessionAttribute("user") BookUser user) {	//로그인 방법 1로 되돌려놓고 테스트
		log.info("user test : {}",user);
	}
	
	@GetMapping("join")
	public void jj() {
		
	}

	@PostMapping("/save")
	public String save(BookUser dto,RedirectAttributes redirectAttributes) {
		service.join(dto);
		redirectAttributes.addFlashAttribute("message","회원가입이 완료되었습니다.");
		return "redirect:/login";
	}
}
