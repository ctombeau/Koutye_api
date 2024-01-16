package com.chrisnor.koutye.service.serviceimpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chrisnor.koutye.repository.UtilisateurRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilisateurServiceImplTest {
	@Autowired
	private UtilisateurRepository utilRepo;
	
	@Test
	public void Loging(String username, String password) {
		
	}
}
