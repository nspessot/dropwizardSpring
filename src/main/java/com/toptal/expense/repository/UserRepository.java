/************************************************************************
 * FileName - UserRepository.java
 * 
 * $Revision: 12 $
 * $Author: Nicolas $
 * $Date: Apr 9, 2015 $
 ************************************************************************/
package com.toptal.expense.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.toptal.expense.model.User;

public interface UserRepository extends MongoRepository<User, String>{
}
