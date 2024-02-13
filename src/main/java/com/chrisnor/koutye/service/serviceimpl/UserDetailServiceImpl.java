package com.chrisnor.koutye.service.serviceimpl;

import java.util.Arrays;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.chrisnor.koutye.exception.InvalidInputException;
import com.chrisnor.koutye.model.Utilisateur;
import com.chrisnor.koutye.repository.UtilisateurRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService{
	
	private UtilisateurRepository utilisateurRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(username == null || username.isEmpty() || username.isBlank())
			throw new InvalidInputException();
	    Utilisateur utilisateur= utilisateurRepo.findUtilisateurByUsername(username);
	                           
	    UserDetails userDetails = User 
	    						.withUsername(utilisateur.getUsername())
	    						.password(utilisateur.getPassword())
	    						.authorities(utilisateur.getTypeUtilisateur().getNomType())
	    						.build();
	    return userDetails;
	}

}
