package jpabasic.aop.dto.response;

import java.time.LocalDateTime;

import jpabasic.aop.entity.Post;
import lombok.Getter;

@Getter
public class FindPostResponseDto {

	private final String title;
	private final String userName;
	private final String contents;
	private final LocalDateTime updatedAt;

	public FindPostResponseDto(Post findPost) {
		this.title = findPost.getTitle();
		this.userName = findPost.getUserName();
		this.contents = findPost.getContents();
		this.updatedAt = findPost.getUpdatedAt();
	}
}