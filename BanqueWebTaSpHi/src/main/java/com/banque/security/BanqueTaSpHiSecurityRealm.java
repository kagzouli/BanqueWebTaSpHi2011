package com.banque.security;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.securityfilter.realm.SimpleSecurityRealmBase;
import org.springframework.stereotype.Component;

import com.banque.common.SpringApplicationContext;
import com.banque.modele.Role;
import com.banque.modele.User;
import com.banque.service.IUserService;
import com.banque.service.exception.TechnicalException;

@Component
public class BanqueTaSpHiSecurityRealm extends SimpleSecurityRealmBase{

    /** Logger **/
    private final static Logger LOG = Logger.getLogger(BanqueTaSpHiSecurityRealm.class);
    
    private IUserService userService = SpringApplicationContext.getBean(IUserService.class);
    

    /**
     * Authenticate a user.
     *
     * Implement this method in a subclass to avoid dealing with Principal objects.
     *
     * @param username a username
     * @param password a plain text password, as entered by the user
     *
     * @return null if the user cannot be authenticated, otherwise a Pricipal object is returned
     */
    public boolean booleanAuthenticate(String username, String password) {
        try {
        	
        	if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
        		return false;
        	}
        	
        	List<User> listUsers = userService.findAll();
        	LOG.info("Size : " + listUsers.size());
            User user = userService.findUserByLoginPassword(username, password);
            return  user != null && !StringUtils.isBlank(user.getLogin());
        }catch (Exception exception) {
            //Erreur technique - On renvoie faux mais il aurait fall
            LOG.error(exception.getMessage(),exception);
            throw new TechnicalException(exception.getMessage());
        }
    }

    /**
     * Test for role membership.
     *
     * Implement this method in a subclass to avoid dealing with Principal objects.
     *
     * @param username The name of the user
     * @param role name of a role to test for membership
     * @return true if the user is in the role, false otherwise
     */
    public boolean isUserInRole(String username, String roleName) {
        boolean isUserInRole = false;
        try {
        	final Role role = userService.findRoleByLogin(username);
        
        	isUserInRole = role != null && StringUtils.equals(roleName, role.getLabel());
        }catch (Exception exception) {
            //Erreur technique - On renvoie faux mais il aurait fall
            LOG.error(exception.getMessage(),exception);
            throw new TechnicalException(exception.getMessage());
        }
        return isUserInRole;

    }


}
