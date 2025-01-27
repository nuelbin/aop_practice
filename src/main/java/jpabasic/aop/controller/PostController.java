package jpabasic.aop.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jpabasic.aop.common.aop.PostContextHolder;
import jpabasic.aop.dto.request.CreatePostRequestDto;
import jpabasic.aop.dto.request.DeletePostRequestDto;
import jpabasic.aop.dto.request.UpdatePostRequestDto;
import jpabasic.aop.dto.response.CreatePostResponseDto;
import jpabasic.aop.dto.response.FindPostResponseDto;
import jpabasic.aop.dto.response.UpdatePostResponseDto;
import jpabasic.aop.service.PostService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	// 게시글 생성 API
	@PostMapping
	public ResponseEntity<CreatePostResponseDto> createdPostAPI(@RequestHeader("Post-Id") String postId,
		@RequestBody CreatePostRequestDto createPostDto) {
		// 전역 공간에 데이터를 넣어줍니다.
		PostContextHolder.setPostInfo(postId);

		return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(createPostDto));
	}

	// 게시글 조회 API
	@GetMapping("/{post_id}")
	public ResponseEntity<FindPostResponseDto> findPostByIdAPI(
		@PathVariable("post_id") Long id) {

		// 보안이나 감사가 필요한 경우에는 선택적으로 AOP 사용이 가능하나 게시글 조회이기 때문에 저는 적용하지 않았습니다.

		return ResponseEntity.status(HttpStatus.OK).body(postService.findPostById(id));
	}

	//게시물 전체 조회 API
	@GetMapping("/post_list")
	public List<FindPostResponseDto> FindALlPostAPI() {

		// 단건 게시물 조회와 같은 이유로 AOP 를 적용하지 않았습니다.

		return postService.findAllPost();
	}

	//게시물 수정 API
	@PatchMapping("/{post_id}")
	public ResponseEntity<UpdatePostResponseDto> updatePostAPI(
		@PathVariable("post_id") Long postId,
		@RequestHeader("Post-Id") String headerPostId,
		@RequestBody UpdatePostRequestDto updatePostRequestDto) {

		// 전역 공간에 데이터를 넣어줍니다.
		PostContextHolder.setPostInfo(headerPostId);

		return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(postId, updatePostRequestDto));
	}

	//게시물 삭제 API
	@DeleteMapping("/{post_id}")
	public ResponseEntity<String> deletePostAPI(
		@PathVariable("post_id") Long postId,
		@RequestHeader("Post-Id") String headerPostId,
		@RequestBody DeletePostRequestDto deletePostDto) {

		// 전역 공간에 데이터를 넣어줍니다.
		PostContextHolder.setPostInfo(headerPostId);

		postService.deletePost(postId, deletePostDto);
		return ResponseEntity.status(HttpStatus.OK).body("게시글이 성공적으로 삭제되었습니다.");
	}
}
