package org.iclass.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {
	private long tno;
	private String title;
	
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dueDate;
	//private Date dueDate;
	
	private String writer;
	private boolean finished;
}

