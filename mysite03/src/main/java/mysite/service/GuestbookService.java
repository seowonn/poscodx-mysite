package mysite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mysite.repository.GuestbookRepository;
import mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	private GuestbookRepository guestbookRepository;
	
	public GuestbookService (GuestbookRepository guestbookRepository) {
		this.guestbookRepository = guestbookRepository;
	}

	public List<GuestbookVo> getContentsList() {
		return guestbookRepository.findAll();
	}
	
	public boolean deleteContents(Long id, String password) {
		return guestbookRepository.deleteByIdAndPassword(id, password) > 0 ? true : false;
	}
	
	public boolean addContents(GuestbookVo vo) {
		return guestbookRepository.insert(vo) > 0 ? true : false;
	}
}
