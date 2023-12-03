package com.chrisnor.koutye.service.serviceimpl;


import java.io.File;
import java.io.FileInputStream;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.exception.InvalidInputException;
import com.chrisnor.koutye.model.TypeUtilisateur;
import com.chrisnor.koutye.model.Utilisateur;
import com.chrisnor.koutye.repository.TypeUtilisateurRepository;
import com.chrisnor.koutye.repository.UtilisateurRepository;
import com.chrisnor.koutye.service.UtilisateurService;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService{

	private PasswordEncoder passwordEncoder;
	
	private UtilisateurRepository utilisateurRepo;
	
	private TypeUtilisateurRepository typeUtilRepo;
	
	private ModelMapper modelMapper;

	private EntityManager em;
	
	@Autowired
	private JwtEncoder jwtEncoder;

	@Override
	@Transactional
	public UtilisateurDto PutUtilisateur(Long id, UtilisateurDto utilDto) {
		Utilisateur util = utilisateurRepo.findById(id).get();
		TypeUtilisateur typeUtil = typeUtilRepo.findByNomType(utilDto.getNomType());
		
		if(util != null)
		{
			Query q = em.createNativeQuery("update utilisateur set nom=:userNom,prenom=:userPrenom,email=:userEmail,"
					+ "username=:userName,password=:userPassword,phone=:userPhone,photo=:userPhoto,id_type=:userType,"
					+ "modification_date=:userDate where utilisateur_id=:userId",UtilisateurDto.class);
			q.setParameter("userNom", utilDto.getNom());
			q.setParameter("userPrenom", utilDto.getPrenom());
			q.setParameter("userEmail", utilDto.getEmail());
			q.setParameter("userName", utilDto.getUsername());
			q.setParameter("userPassword", passwordEncoder.encode(utilDto.getPassword()));
			q.setParameter("userPhone", utilDto.getPhone());
			q.setParameter("userPhoto", utilDto.getPhoto());
			q.setParameter("userType", typeUtil.getIdType());
			q.setParameter("userDate", LocalDateTime.now());
			q.setParameter("userId", id);
			q.executeUpdate();
			
			return utilDto;
		}
		else
		{
			return null;
		}
		
	}

	@Override
	public Optional<UtilisateurDto> getUtilisateur(String username) {
        
		Utilisateur util = utilisateurRepo.findUtilisateurByUsername(username).orElseThrow(()-> new UsernameNotFoundException("user not found for username: "+username));
		
		if(util != null)
		{
			return Optional.of(modelMapper.map(util, UtilisateurDto.class));
		}
		else
		{
			return null;
		}
		
	}

	@Override
	@Transactional
	public UtilisateurDto Login(String username, String password) {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur = utilisateurRepo.findUtilisateurByUsername(username).orElseThrow(()-> new UsernameNotFoundException("user not found for username: "+username));
		
		if(utilisateur != null && passwordEncoder.matches(password, utilisateur.getPassword()))
		{
			Query req = em.createNativeQuery("update utilisateur set login_date=? where utilisateur_id=?");
			req.setParameter(1, LocalDateTime.now());
			req.setParameter(2, utilisateur.getUtilisateurId());
			req.executeUpdate();
			//utilisateur.setLoginDate(LocalDateTime.now());
			return modelMapper.map(utilisateur, UtilisateurDto.class);
		}
		else
		{
			return null;
		}
		
	}

	@Override
	public List<UtilisateurDto> getAllUtilisateurs() {
		
		List<Utilisateur> utilisateurs = (List<Utilisateur>) utilisateurRepo.findAll();
		return  utilisateurs.stream()
				.map(utilisateur-> modelMapper.map(utilisateur, UtilisateurDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public UtilisateurDto PostUtilisateur( UtilisateurDto utilDto) {
		Utilisateur util = new Utilisateur();
		TypeUtilisateur typeUtil = new TypeUtilisateur();
	    
		if(utilisateurRepo.findUtilisateurByUsername(utilDto.getUsername())==null && 
				utilisateurRepo.findUtilisateurByEmail(utilDto.getEmail())==null)
		{

			typeUtil = typeUtilRepo.findByNomType(utilDto.getNomType());
			String nom= utilDto.getNom();
			String prenom = utilDto.getPrenom();
			String email = utilDto.getEmail();
			String username = utilDto.getUsername();
			String password = passwordEncoder.encode(utilDto.getPassword());
			String phone = utilDto.getPhone();
			String photo = utilDto.getPhoto(); //picture;
			LocalDateTime creation_date = LocalDateTime.now();
			Long idType = typeUtil.getIdType();
			
			Query q = em.createNativeQuery("insert into utilisateur(nom,prenom,email,username,password,phone,photo,id_type,creation_date)"
					+ "values(?,?,?,?,?,?,?,?,?)",UtilisateurDto.class);
			q.setParameter(1, nom);
			q.setParameter(2, prenom);
			q.setParameter(3, email);
			q.setParameter(4, username);
			q.setParameter(5, password);
			q.setParameter(6, phone);
			q.setParameter(7, photo);
			q.setParameter(8, idType);
			q.setParameter(9, creation_date);
			
			q.executeUpdate();
			return utilDto;
		}
		else
		{
			return null;
		}
		//return modelMapper.map(util, UtilisateurDto.class);
	}

	@Override
	public long FindIdTypeByNomType(String nomType) {
		
		Query id = em.createNativeQuery("select id_type from type_utilisateur where nom_type= ?");
		return 0;
	}

	
	@Override
	public Map<String, String> GenerateToken(String username, Authentication authentication) {
		Instant instant = Instant.now();
		String scope = authentication.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.joining(" "));
		JwtClaimsSet jwtClaimsSet = JwtClaimsSet
									.builder()
									.issuedAt(instant)
									.expiresAt(instant.plus(10,ChronoUnit.MINUTES))
									.subject(username)
									.claim("scope", scope)
									.build();
		JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
									JwsHeader.with(MacAlgorithm.HS512).build(),
									jwtClaimsSet
								);
		String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
		return Map.of("access-token",jwt);
	}
	
	
   
}
