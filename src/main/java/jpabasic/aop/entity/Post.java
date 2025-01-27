package jpabasic.aop.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "posts")
public class Post {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;
	// 글 제목
	@Column(length = 20)
	private String title;
	// 작성자 이름
	@Column(length = 10)
	private String userName;
	// 글 내용
	@Column(length = 200)
	private String contents;
	// 비밀번호
	private String password;

	@CreatedDate // 생성일
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@CreatedDate
	@LastModifiedDate // 수정일
	private LocalDateTime updatedAt;

	public Post(String title, String userName, String contents, String password) {
		this.title = title;
		this.userName = userName;
		this.contents = contents;
		this.password = password;
	}

	public void updatePost() {

	}
}
