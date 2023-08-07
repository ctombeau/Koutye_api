package com.chrisnor.koutye.service.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.model.TypeUtilisateur;
import com.chrisnor.koutye.model.Utilisateur;
import com.chrisnor.koutye.repository.TypeUtilisateurRepository;
import com.chrisnor.koutye.repository.UtilisateurRepository;
import com.chrisnor.koutye.service.UtilisateurService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService{
	
	@Autowired
	private UtilisateurRepository utilisateurRepo;
	
	@Autowired
	private TypeUtilisateurRepository typeUtilRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EntityManager em;

	@Override
	public void PutUtilisateur(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Utilisateur> getUtilisateur(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UtilisateurDto Login(String username, String password) {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur = utilisateurRepo.findUtilisateurByUsernameAndPassword(username, password);
		
		return modelMapper.map(utilisateur, UtilisateurDto.class);
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
	public UtilisateurDto PostUtilisateur(UtilisateurDto utilDto) {
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
			String password = utilDto.getPassword();
			String phone = utilDto.getPhone();
			String photo = utilDto.getPhoto();
			Long idType = typeUtil.getIdType();
			
			Query q = em.createNativeQuery("insert into utilisateur(nom,prenom,email,username,password,phone,photo,id_type)"
					+ "values(?,?,?,?,?,?,?,?)",UtilisateurDto.class);
			q.setParameter(1, nom);
			q.setParameter(2, prenom);
			q.setParameter(3, email);
			q.setParameter(4, username);
			q.setParameter(5, password);
			q.setParameter(6, phone);
			q.setParameter(7, photo);
			q.setParameter(8, idType);
			
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

}
