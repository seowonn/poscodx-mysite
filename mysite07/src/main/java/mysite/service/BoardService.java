package mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import mysite.repository.BoardRepository;
import mysite.vo.BoardVo;

@Service
public class BoardService {
	
	private final int DEFAULT_PAGE_SIZE = 10;
	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public void addContents(BoardVo vo) {
		boardRepository.insert(vo);
	}
	
	public BoardVo getContents(Long id) {
		 return getContents(id, null);
	}
	
	public BoardVo getContents(Long id, Long userId) {
		return boardRepository.findById(id);
	}
	
	public void updateContents(BoardVo vo) {
		boardRepository.update(vo);
	}
	
	public void deleteContents(Long id, Long userId) {
		boardRepository.deleteByIdAndUserId(id, userId);
	}
	
	public Map<String, Object> getContentsList(int currentPage, String keyword) {
		Map<String, Object> resultMap = new HashMap<>();
		int offset = (currentPage - 1) * DEFAULT_PAGE_SIZE;
		
		List<BoardVo> list = boardRepository.findByKeyword(keyword, DEFAULT_PAGE_SIZE, offset);
		resultMap.put("list", list);
		
		int totalCount = boardRepository.countByKeyword(keyword);
		int totalPages = (int) Math.ceil((double) totalCount / DEFAULT_PAGE_SIZE);
		
		resultMap.put("totalCount", totalCount);
		resultMap.put("pageSize", DEFAULT_PAGE_SIZE);
		resultMap.put("currentPage", currentPage);
		resultMap.put("totalPages", totalPages);
		resultMap.put("keyword", keyword);
		return resultMap;
	}
	
}
