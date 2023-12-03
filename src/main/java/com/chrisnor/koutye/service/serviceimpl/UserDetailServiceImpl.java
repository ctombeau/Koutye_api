package com.chrisnor.koutye.service.serviceimpl;

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
		if(username == null || username.isEmpty())
			throw new InvalidInputException();
	    Utilisateur utilisateur= utilisateurRepo.findUtilisateurByUsername(username).orElseThrow(()-> new UsernameNotFoundException("user not found for username: "+username));
		
	    //utilisateur.getRoles().stream().map(u->u.getTypeUtilisateur()).coll
	    UserDetails userDetails = User
	    						.withUsername(utilisateur.getUsername())
	    						.password(utilisateur.getPassword())
	    						//.authorities(utilisateur.getTypeUtilisateur())
	    						.build();
	    return userDetails;
	}

}
