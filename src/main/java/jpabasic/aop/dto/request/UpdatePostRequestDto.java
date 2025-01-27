package jpabasic.aop.dto.request;


import lombok.Getter;

@Getter
public class UpdatePostRequestDto {

	private String password;
	private String title;
	private String contents;
}
