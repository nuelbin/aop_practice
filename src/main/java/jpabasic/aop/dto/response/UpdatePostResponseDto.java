package jpabasic.aop.dto.response;

import java.time.LocalDateTime;

import jpabasic.aop.entity.Post;
import lombok.Getter;

@Getter
public class UpdatePostResponseDto {
	private final String message;
	private final String title;
	private final String contents;
	private final LocalDateTime updatedAt;

	public UpdatePostResponseDto(String message ,Post post) {
		this.message = message;
		this.title = post.getTitle();
		this.contents = post.getContents();
		this.updatedAt = post.getUpdatedAt();
	}
}
