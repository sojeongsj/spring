package org.iclass.controller;

import javax.validation.Valid;

import org.iclass.dto.PageRequestDTO;
import org.iclass.dto.TodoDto;
import org.iclass.service.TodoService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("todo")
@RequiredArgsConstructor
public class TodoController {

	private final TodoService todoService;

	@GetMapping("/register")
	public String register() {
		log.info(">>>>>>> /todo/register GET 요청을 처리합니다. ");
		return "todo/register";
	}

	//@Valid 는 TodoDto 값의 유효성 검사를 동작시킴. TodoDto에서 정한 규칙에 오류가 있으면 
	//BindingResult 객체에 저장.
	@PostMapping("/register")
	public String registerAction(@Valid TodoDto dto, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		log.info(">>>>>>> /todo/register POST 요청을 처리합니다. param : {}", dto);

		if (bindingResult.hasErrors()) {	//validation 오류가 있으면
			log.info(">>>>> validator error {}", bindingResult.getAllErrors());
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			//오류가 있으면 register.html 뷰까지 전달하므로 boot4board 프로젝트의 write.html 을 참고해서 출력하세요.
			return "redirect:/todo/register";	//유효성 검증 오류가 있으면 register로 리다이렉트
		}

		todoService.register(dto);

		return "redirect:/todo/list";		//유효성 검증 통과하고 저장하면 list로 리다이렉트
	}

	@GetMapping("/list")
	public void list(@Valid PageRequestDTO pageRequestDTO, BindingResult bindingResult, Model model) {
		log.info(">>>>>>> /todo/list GET 요청을 처리합니다.");
		log.info(">>>>>>> pageRequestDto : {}",pageRequestDTO);
		
		if(bindingResult.hasErrors()) {
			pageRequestDTO = PageRequestDTO.builder().build();
		}
		pageRequestDTO = PageRequestDTO.of(pageRequestDTO.getPage(), pageRequestDTO.getSize());
		model.addAttribute("responseDto", todoService.getList(pageRequestDTO));
	}

	/*
	 * @GetMapping("/list") public void list(Model model) {
	 * log.info(">>>>>>> /todo/list GET 요청을 처리합니다."); model.addAttribute("todoList",
	 * todoService.getAll()); }
	 */
	// 조회와 수정 기능으로 변경
	@GetMapping({ "/read", "modify" })
//	public void read(long tno, Model model) {
	public void read(long tno, PageRequestDTO pageRequestDTO, Model model) {
		TodoDto dto = todoService.getOne(tno);
		model.addAttribute("dto", dto);
	}
	/*
	 * @GetMapping("/read") public void read(long tno, Model model) { TodoDto dto =
	 * todoService.getOne(tno); model.addAttribute("dto", dto); }
	 */

	@PostMapping("/remove")
	public String remvoe(long tno, 
			//페이징에서 추가
			PageRequestDTO pageRequestDTO,
			RedirectAttributes redirectAttributes) {
		log.info(" >>>> remove tno : {}", tno);

		todoService.remove(tno);
		
		//페이징에서 추가
		redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
		redirectAttributes.addAttribute("size", pageRequestDTO.getSize());
		return "redirect:/todo/list";
	}

	@PostMapping("/modify")
	public String modifyAction(@Valid TodoDto dto, 
			//페이징에서 추가
			PageRequestDTO pageRequestDTO,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		log.info(">>>>>>> /todo/modify POST 요청을 처리합니다. param : {}", dto);

		if (bindingResult.hasErrors()) {
			log.info(">>>>> validator error {}", bindingResult.getAllErrors());
			
			redirectAttributes.addFlashAttribute("tno", dto.getTno());
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/todo/register";
		}

		todoService.modify(dto);
		
		//페이징에서 추가
		redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
		redirectAttributes.addAttribute("size", pageRequestDTO.getSize());

		return "redirect:/todo/list";
	}

}
