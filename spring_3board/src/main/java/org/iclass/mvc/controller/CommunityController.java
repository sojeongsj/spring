package org.iclass.mvc.controller;

import java.time.LocalDate;

import org.iclass.mvc.dto.Community;
import org.iclass.mvc.dto.CommunityComments;
import org.iclass.mvc.service.CommunityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.Builder.Default;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/community")
@Slf4j
public class CommunityController {
	
	private final CommunityService service;
	
	private CommunityController(CommunityService service) {
		this.service = service;
	}
	
	@GetMapping("/list")
	public void list(@RequestParam(defaultValue = "1")	//파라미터 값이 없으면 오류. 기본값 설정
			int page, Model model) {	//메소드 인자 int page 는 url의 파라미터
		//Model : view 로 전달될 데이터를 주로 저장
		//addAttribute 메소드 : request.setAttribute와 동일(session 도 Model 사용 가능)
		model.addAttribute("list", service.pagelist(page).get("list"));
		model.addAttribute("paging", service.pagelist(page).get("paging"));
		model.addAttribute("today",LocalDate.now());
	}
	
	@GetMapping("/read")
	public void read(long idx, @ModelAttribute("page") 
	//파라미터로 받은 값을 Model 객체 - model.addAttribute와 같이 저장
	int page, Model model) {
		model.addAttribute("vo",service.selectByIdx(idx));
		model.addAttribute("cmtlist",service.commentsList(idx));
		
		
	}
	
	@GetMapping("/write")
	public void write() {
		//글쓰기 GET 요청은 view name만 지정하고 끝
	}
	
	@PostMapping("/write")
	public String save(Community dto
			,RedirectAttributes reAttr
			) {	//파라미터가 많을 때, 그것들을 필드로 갖는 dto 또는 map으로 전달받기
		log.info("dto : {}",dto);
		service.insert(dto);
		
		reAttr.addFlashAttribute("message","글 등록이 완료되었습니다.");
		return "redirect:/community/list";
	}
	//location.href='list.jsp'는 자바스크립트 - 클라이언트 브라우저에서 주소 변경
	//response.sendRedirect("list.jsp")는 서버에서 클라이언트가 새로 보낼 요청을 강제로 실행
	//			ㄴ POST 요청을 처리한 후에는 redirect를 함
	
	@PostMapping("/updateAction")
	public String updateAction(int page, 
			Community dto,
			//Model model
			RedirectAttributes redirectAttributes
			) {
		service.update(dto);
		//수정 후 다시 글 상세보기
		redirectAttributes.addAttribute("idx",dto.getIdx());
		redirectAttributes.addAttribute("page", page);
		redirectAttributes.addFlashAttribute("message","글 수정이 완료되었습니다.");
		return "redirect:/community/read";
		
	}
	
	@PostMapping("/update")
	public void update(long idx, @ModelAttribute("page") int page, Model model) {
		model.addAttribute("vo", service.selectByIdx(idx));
	}
	
	@PostMapping("/delete")
	public String delete(int page,long idx,
			RedirectAttributes redirectAttributes) {
		service.delete(idx);
		redirectAttributes.addAttribute("page",page);	//@ModelAttribute
		redirectAttributes.addFlashAttribute("message","글 삭제가 완료되었습니다.");
		return "redirect:/community/list";
	}
	
	
	 @PostMapping("/comments") 
	 public String comments(int page, int f ,CommunityComments dto,
			 RedirectAttributes redirectAttributes) {
	 log.info(">>>>>>> dto : {}",dto); 
	 service.comments(dto,f);
	 redirectAttributes.addAttribute("page",page);
	 redirectAttributes.addAttribute("idx",dto.getMref());
	 if(f==1) {
		 redirectAttributes.addFlashAttribute("message","댓글 등록이 완료되었습니다.");
	 }
	 else if(f==2) {
		 redirectAttributes.addFlashAttribute("message","댓글 삭제가 완료되었습니다.");
	 }
	 //return "redirect:/community/read?page="+page+"&idx="+dto.getMref(); 
	 return "redirect:/community/read"; //리다이렉트 애트리뷰트 사용하므로 쿼리스트링 사용X 
	 }
	
	
	
}
