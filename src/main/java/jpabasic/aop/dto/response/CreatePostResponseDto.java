package jpabasic.aop.dto.response;

import java.time.LocalDateTime;

import jpabasic.aop.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CreatePostResponseDto {

	private String message;
	private String title;
	private String userName;
	private String contents;
	private LocalDateTime createdAt;

	public CreatePostResponseDto(String message, Post savePost) {
		this.message = message;
		this.title = savePost.getTitle();
		this.userName = savePost.getUserName();
		this.contents = savePost.getContents();
		this.createdAt = savePost.getCreatedAt();
	}

}

