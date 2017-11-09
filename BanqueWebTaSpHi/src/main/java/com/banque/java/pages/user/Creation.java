package com.banque.java.pages.user;

import java.util.List;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.Select;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;

import com.banque.java.components.GenericSelectModel;
import com.banque.java.pages.Index;
import com.banque.modele.Role;
import com.banque.modele.User;
import com.banque.service.IUserService;
import com.banque.service.exception.LoginDejaExistantException;
import com.banque.service.exception.TechnicalException;

public class Creation {

    @Component(id = "createuser_form")
    private Form createUserForm;

    @Validate("required,minlength=4,maxlength=15")
    private String loginValue;

 
    @Validate("required,minlength=4,maxlength=15")
    private String passwordValue;

    
    @Component
    private TextField login;
    
    @Component
    private PasswordField password;
  
    
    @Component
    private Select role;

    @Inject
    private IUserService userService;

    @InjectPage
    private Index index;
    
    private GenericSelectModel<Role> modelRoles;
    
    @Inject
    private PropertyAccess _access;
    
    @Persist
    @Validate("required")
    private Role roleChoose;
    
    @Inject
	private Messages messages;
    	
    
    private static final String MESSAGE_EXIST_LOGIN  = "login.exist";



    
    void onActivate() throws Exception
    {
      List<Role> listRole = this.getListRole();
      if (listRole != null){
          modelRoles = new GenericSelectModel<Role>(listRole,Role.class,"label","id",_access);
      }
    }


    /**
     * @return the loginValue
     */
    public String getLoginValue() {
        return loginValue;
    }

    /**
     * @param loginValue the loginValue to set
     */
    public void setLoginValue(String loginValue) {
        this.loginValue = loginValue;
    }
    
    

    public String getPasswordValue() {
		return passwordValue;
	}


	public void setPasswordValue(String passwordValue) {
		this.passwordValue = passwordValue;
	}


	public Object onSuccess() throws Exception {
		try {
			User user = new User();
			user.setLogin(this.loginValue);
			user.setPassword(this.passwordValue);
			user.setRole(userService.findRoleById(this.roleChoose.getId()));
			userService.createUser(user,this.passwordValue);
		} catch (final LoginDejaExistantException exception) {
			createUserForm.recordError(this.login, messages.format(MESSAGE_EXIST_LOGIN, exception.getLogin()));
			return null;
		} catch (final Exception exception) {
			throw exception;
		}

		return index;
	}

    public List<Role> getListRole() throws Exception {
        try {
            return userService.findRoles();
        } catch (TechnicalException exception) {
            throw exception;
        }
    }


    public GenericSelectModel<Role> getModelRoles() {
        return modelRoles;
    }


    public void setModelRoles(GenericSelectModel<Role> modelRoles) {
        this.modelRoles = modelRoles;
    }


    public Role getRoleChoose() {
        return roleChoose;
    }


    public void setRoleChoose(Role roleChoose) {
        this.roleChoose = roleChoose;
    }
    
   
}
