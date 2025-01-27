package jpabasic.aop.common.aop;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostContextHolder {

	// ThreadLocal <- 데이터가 임시로 머물 수 있는 곳.
	private static final ThreadLocal<String> postContext = new ThreadLocal<>();

	public static void setPostInfo(String PostInfo) {
		postContext.set(PostInfo);
	}

	// 전역 공간에 저장된 게시글 정보를 가져오는 로직
	public static String getPostInfo() {
		return postContext.get();
	}

	// 전역 공간에 저장된 데이터 값 삭제
	public static void clear() {
		postContext.remove();
	}
}