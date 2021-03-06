package com.paymybuddy.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.app.models.Transaction;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>, PagingAndSortingRepository<Transaction, Integer>{

	//Return une transaction : ID Transaction
	@Query(value = "SELECT * FROM transaction u WHERE u.id_transaction = ?1", nativeQuery = true)
	public List<Transaction> findByid_Transaction(String str);
	
	//Return les transactions associer à un utilisateur. 
	@Query(value = "SELECT * FROM transaction u WHERE u.emitter_id = ?1 or u.receiver_id = ?1", nativeQuery = true)
	public List<Transaction> findAllTransactionByIdUser(int id);
	
	//Return les transactions associer à un utilisateur sans lien avec son account bank.
	@Query(value = "SELECT * FROM transaction u WHERE ( u.emitter_id = ?1 or u.receiver_id = ?1) AND u.is_account_bank = false  ",countQuery= "SELECT count(*) FROM transaction u  WHERE ( u.emitter_id = ?1 or u.receiver_id = ?1) AND u.is_account_bank = false ", nativeQuery = true)
	public Page<Transaction> pageableFindAllTransactionByIdUser(int idUser,Pageable pageable);
	
	//Return les transactions associer à un utilisateur en lien avec son account bank.
	@Query(value = "SELECT * FROM transaction u WHERE ( u.emitter_id = ?1 or u.receiver_id = ?1) AND u.is_account_bank = true  ",countQuery= "SELECT count(*) FROM transaction u  WHERE ( u.emitter_id = ?1 or u.receiver_id = ?1) AND u.is_account_bank = true ", nativeQuery = true)
	public Page<Transaction> pageableFindAllBankTransactionByIdUser(int idUser,Pageable pageable);
	
	
}
