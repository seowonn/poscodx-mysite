package mysite.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import mysite.vo.BoardVo;

@Service
public class BoardService {

	public void addContents(BoardVo vo) {
		
	}
	
	public BoardVo getContents(Long id) {
		return null;
	}
	
	public BoardVo getContents(Long id, Long userId) {
		return null;
	}
	
	public void updateContents(BoardVo vo) {
		
	}
	
	public void deleteContents(Long id, Long userId) {
		
	}
	
	public Map<String, Object> getContentsList(int currentPage, String keyword) {
//		List<BoardVo> list = ;
//		int beginPage
//		int endPage
		// 이걸 다 Map으로 둘러싸서 한번에 보내기 위함. 
		// view의 pagination을 위한 데이터 값 계산
		return null;
	}
	
}
