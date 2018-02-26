package jp.co.rakus.merucari.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.rakus.merucari.repository.OriginalRepository;

@Service
public class OriginalService {

	@Autowired
	private OriginalRepository repository;
	
	public List<String> findAllDistinct() {
		return repository.findAllDistinct();
	}

}
