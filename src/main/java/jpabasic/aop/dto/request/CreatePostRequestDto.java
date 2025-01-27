package jpabasic.aop.dto.request;

import lombok.Getter;

@Getter
public class CreatePostRequestDto {

	private String title;
	private String userName;
	private String contents;
	private String password;
}
