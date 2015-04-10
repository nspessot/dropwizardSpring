/************************************************************************
 * FileName - UserResource.java
 * 
 * $Revision: 12 $
 * $Author: Nicolas $
 * $Date: Apr 9, 2015 $
 ************************************************************************/
package com.toptal.expense.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toptal.expense.model.User;
import com.toptal.expense.repository.UserRepository;

@Path("/users")
@Service
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Autowired
    private UserRepository userRepository;
    
    @GET
    public User getUser(){
        User user = new User();
        user.setFirstName("nicolas");
        user.setLastName("spessot");
        user.setUsername("nspessot");
        user.setEmail("spnico88@hotmail.com");
        userRepository.save(user);
        
        return user;
    }
    
}
