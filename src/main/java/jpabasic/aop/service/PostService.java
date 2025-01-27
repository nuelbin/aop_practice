package jpabasic.aop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabasic.aop.common.PasswordEncoder;
import jpabasic.aop.common.aop.PostContextHolder;
import jpabasic.aop.dto.request.CreatePostRequestDto;
import jpabasic.aop.dto.request.DeletePostRequestDto;
import jpabasic.aop.dto.request.UpdatePostRequestDto;
import jpabasic.aop.dto.response.CreatePostResponseDto;
import jpabasic.aop.dto.response.FindPostResponseDto;
import jpabasic.aop.dto.response.UpdatePostResponseDto;
import jpabasic.aop.entity.Post;
import jpabasic.aop.repository.PostRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;
	private final PasswordEncoder passwordEncoder;

	// 게시글 생성 기능
	@Transactional
	public CreatePostResponseDto createPost(CreatePostRequestDto createPostDto) {
		String bcryptPassword = passwordEncoder.encode(createPostDto.getPassword());

		Post post = new Post(createPostDto.getTitle(),
			createPostDto.getUserName(),
			createPostDto.getContents(),
			bcryptPassword);

		Post savePost = postRepository.save(post);
		return new CreatePostResponseDto("게시글이 생성되었습니다.", savePost);
	}

	//게시물 조회 기능
	public FindPostResponseDto findPostById(Long id) {
		Post findPost = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

		return new FindPostResponseDto(findPost);
	}

	//게시물 전체 조회 기능
	public List<FindPostResponseDto> findAllPost() {
		List<Post> findAllPost = postRepository.findAll();
		List<FindPostResponseDto> findPostList = new ArrayList<>();

		for (Post post : findAllPost) {
			FindPostResponseDto findpostResponseDto = new FindPostResponseDto(post);
			findPostList.add(findpostResponseDto);
		}

		return findPostList;
	}

	// 게시글 수정 기능
	@Transactional
	public UpdatePostResponseDto updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("수정할 게시글을 찾을 수 없습니다."));

		// 글을 수정하려는 유저가 입력한 비밀번호가 데이터베이스에 기록된 비밀번호와 일치 하지 않을시
		if (!passwordEncoder.matches(updatePostRequestDto.getPassword(), post.getPassword())) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}

		post.updatePost();
		return new UpdatePostResponseDto("게시글이 수정되었습니다.", post);
	}

	// 게시글 삭제 기능(하드 딜리트)
	@Transactional
	public void deletePost(Long postId, DeletePostRequestDto deletePostDto) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new RuntimeException("삭제할 게시글을 찾을 수 없습니다."));

		// 글을 삭제하려는 유저가 입력한 비밀번호가 데이터베이스에 기록된 비밀번호와 일치 하지 않을시
		if (!passwordEncoder.matches(deletePostDto.getPassword(), post.getPassword())) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}

		postRepository.deleteById(postId);
	}

}


