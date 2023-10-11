package org.iclass.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import org.iclass.config.MybatisConfig;
import org.iclass.dto.PageRequestDTO;
import org.iclass.dto.TodoDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

@DisplayName("TodoMapper의 curd 동작이 되어야 함")
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations ="classpath:META-INF/spring/applicationContext.xml" )
@ContextConfiguration(classes = MybatisConfig.class)
@Slf4j
public class TodoMapperTest {

		@Autowired
		private TodoMapper dao;
		
		@Disabled
		@Test
		@DisplayName("insert를 여러행 해보기. 페이징 테스트를 위한 데이터 추가")
		void insertMany() {
			IntStream.range(1, 20).forEach(i -> {
			
			TodoDto dto = TodoDto.builder()
			.title("집에가고싶다" + i)
			.writer("twice" + (char)('A'+i))
			.finished(true)
			.dueDate(LocalDate.of(2023, 10, 3))
			//.dueDate(Date.valueOf("2023-10-01"))
			.build();
				
			dao.insert(dto);
			});
		}
		
		@Test
		@DisplayName("다양한 조건을 검색")
		void search() {
			
			//PageRequestDTO pageRequestDTO = PageRequestDTO.of(1, 10,new String[] {"t","w"},"집에",false,
			//		null,null);
			PageRequestDTO pageRequestDTO = PageRequestDTO.of(1, 10,null,null,false,
					LocalDate.of(2023, 9, 29),LocalDate.of(2023, 10, 2));
			
			List<TodoDto> list = dao.selectPageList(pageRequestDTO);
			list.forEach(dto -> log.info(">>>>> search result : {} ",dto));
			
			log.info(">>>>>>>>>>>>total count : {}",dao.getCount(pageRequestDTO));
		}
		
		@Disabled
		
		@Test
		@DisplayName("Todo테이블에 insert가 되어야 함")
		void insert() {
			
				TodoDto dto = TodoDto.builder()
						.title("mybatis  공부")
						.writer("momo")
						.dueDate(LocalDate.of(2023, 10, 1))
						//.dueDate(Date.valueOf("2023-10-01"))
						.build();
			log.info(">>>>>dto : {}",dto);
			assertNotEquals(0, dao.insert(dto));
		}
		
		@Disabled
		@Test
		void selectOne()  {
			TodoDto dto = dao.selectOne(20114L);
			log.info(">>>>>>>>>> dto : {}", dto);
			assertNotNull(dto);
			
		}
	
		

}
