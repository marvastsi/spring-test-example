package com.marvastsi.test_example.user;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository todoRepository;

	public void delete(User todo) {
		todoRepository.delete(todo);
	}

	public User save(User todo) {
		return todoRepository.save(todo);
	}

	public User update(User todo) {
		return todoRepository.save(todo);
	}

	public User findById(String id) {
		Optional<User> address = todoRepository.findById(id);
		return address.get();
	}

	public List<User> findAll() {
		return todoRepository.findAll();
	}
}
