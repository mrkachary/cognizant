package com.cognizant.customer.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cognizant.customer.helper.CustomerMapper;
import com.cognizant.customer.model.CustomerDTO;

@Repository
public class CustomerRepository {


	@Autowired
	CustomerMapper customerMapper;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * save a customer 
	 * 
	 * @param toModel
	 * @return
	 */
	@Transactional
	public CustomerDTO save(CustomerDTO toModel) {
		entityManager.persist(toModel.getAddress());
		entityManager.persist(toModel);
		return toModel;
	}

	/**
	 * retrieve all customers
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerDTO> getAllCustomers() {
		Query query = entityManager.createNamedQuery("findAllCustomers", CustomerDTO.class);
		return query.getResultList();
	}
	
	/**
	 * retrieve customer by firstname and/or lastname
	 * 
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerDTO> getAllCustomerByName(String firstName, String lastName) {
		Query query = null;
		if(firstName==null && lastName!=null) {
			query = entityManager.createNamedQuery("findAllCustomersByLastname", CustomerDTO.class);
			query.setParameter("lastName", "%"+lastName.trim()+"%");
		} else if(firstName!=null&& lastName==null) {
			query = entityManager.createNamedQuery("findAllCustomersByFirstame", CustomerDTO.class);
			query.setParameter("firstName", "%"+firstName.trim()+"%");
		} else if(firstName==null && lastName==null) {
			query = entityManager.createNamedQuery("findAllCustomers", CustomerDTO.class);
		} else {
			query = entityManager.createNamedQuery("findAllCustomersByFirstnameAndLastname", CustomerDTO.class);
			query.setParameter("firstName", "%"+firstName.trim()+"%");
			query.setParameter("lastName", "%"+lastName.trim()+"%");
		}
		return query.getResultList();
	}

	public CustomerDTO getCustomerById(Long custId) {
		return entityManager.find(CustomerDTO.class, custId);
	}

	@Transactional
	public CustomerDTO updateLivingAddress(CustomerDTO updatedCustomer) {
		entityManager.find(CustomerDTO.class, updatedCustomer.getCustId());
		entityManager.persist(updatedCustomer.getAddress());
		entityManager.merge(updatedCustomer);
		return updatedCustomer;
	}

}
