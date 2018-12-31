package com.marketplace.web;

import com.marketplace.entity.Users;
import com.marketplace.ejb.UsersFacade;
import com.marketplace.utils.PasswordAuthentication;
import com.marketplace.utils.Regex;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named("usersController")
@SessionScoped
public class UsersController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final int REQUEST_INVALID = 0;
	private static final int REQUEST_RESET_PASSWORD = 1;
	private static final int REQUEST_ACCOUNT_VERIFICATION = 2;
	
	private static final PasswordAuthentication PASSWORD_AUTH = new PasswordAuthentication();
	
	private Users user;
	private TextFields textFields;
	private String requestedUserKey;
	private Users requestedUserResult;
    @EJB private UsersFacade usersFacade;
	
	public UsersController() {
		textFields = new TextFields();
	}
	
	public Users getUser() {
		return user;
	}
	
	public UsersFacade getFacade() {
		return usersFacade;
	}
	
	public TextFields getTextFields() {
		return textFields;
	}

	public String getRequestedUserKey() {
		return requestedUserKey;
	}

	public void setRequestedUserKey(String requestedUserKey) {
		this.requestedUserKey = requestedUserKey;
	}
	
	public Users getRequestedUserResult() {
		return requestedUserResult;
	}
	
	/*********************
		PAGES LOADING
		-> Method is called whenever any page in "users/" load
	*********************/
	// called whenever any has page loaded (POST)
 	public void onPageLoaded() {
		textFields.setName("");
		textFields.setEmail("");
		textFields.setPassword("");
		textFields.setRepeatPassword("");

		textFields.setNameError(null);
		textFields.setEmailError(null);
		textFields.setPasswordError(null);
		textFields.setRepeatPasswordError(null);
	}
	
	public String onResetPasswordRequested() {
		if (user != null) { // user is already logged-in
			return "/404?faces-redirect=true"; // 404 page
		}
		
		if (requestedUserKey == null) {
			return "/404?faces-redirect=true"; // 404 page
		}
		
		List<Users> list = usersFacade.findRequestKey(requestedUserKey);
		if (list.isEmpty() == true) {
			list.clear();
			return "/404?faces-redirect=true"; // 404 page
		}
		
		requestedUserResult = list.get(0);
		if (requestedUserResult.getRequestType() != REQUEST_RESET_PASSWORD) {
			requestedUserResult = null;
			list.clear();
			return "/404?faces-redirect=true"; // request for different purpose
		}
		
		if (requestedUserResult.getRequestTimestamp().before(new Timestamp(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(60))) == true) {
			requestedUserResult.setRequestKey(null);
			requestedUserResult.setRequestTimestamp(null);
			requestedUserResult.setRequestType(REQUEST_INVALID);
			
			usersFacade.edit(requestedUserResult);
			requestedUserResult = null;
			
			list.clear();
			return "/404?faces-redirect=true"; // request expired
		}
		
		list.clear();
		
		return null;	
	}
	
	// when the user clicks "not you?" link
	public String onResetPasswordDenied() {
		if (requestedUserResult == null || user != null) {
			return "/404?faces-redirect=true";
		}
		
		requestedUserResult.setRequestKey(null);
		requestedUserResult.setRequestTimestamp(null);
		requestedUserResult.setRequestType(REQUEST_INVALID);
		
		usersFacade.edit(requestedUserResult);
		requestedUserResult = null;
		
		return "/index?faces-redirect=true";
	}
	
	// AccountVerification.xhtml page
	public String onAccountVerificationRequested() {
		if (user != null) { // user is already logged-in
			return "/404?faces-redirect=true"; // 404 page
		}
		
		if (requestedUserKey == null) {
			return "/404?faces-redirect=true"; // 404 page
		}
		
		List<Users> list = usersFacade.findRequestKey(requestedUserKey);
		if (list.isEmpty() == true) {
			list.clear();
			return "/404?faces-redirect=true"; // 404 page
		}
		
		Users selectedUser = list.get(0);
		list.clear();	
		
		if (selectedUser.getRequestType() != REQUEST_ACCOUNT_VERIFICATION) {
			return "/404?faces-redirect=true"; // 404 page
		}
		
		// verify user
		selectedUser.setRequestKey(null);
		selectedUser.setRequestType(REQUEST_INVALID);
	
		usersFacade.edit(selectedUser);
		
		user = selectedUser;
		
		return null;	
	}

	/*********************
		LOGIN BUTTON
		-> Method is called from the "users/Login.xhtml" file
	*********************/
	public String onSignIn() {
		// email validation
		if (getTextFields().getEmail().length() == 0) {
			getTextFields().setEmailError("Please enter your email");
			return null;
		} 
		
		List<Users> list = usersFacade.findEmail(getTextFields().getEmail());
		if (list.isEmpty() == true) {
			getTextFields().setEmailError("Email entered is not register");
			list.clear();
			return null;
		}
		
		// if email is verified
		if (list.get(0).getRequestType() == REQUEST_ACCOUNT_VERIFICATION) {
			getTextFields().setEmailError("Email entered is not verified account");
			list.clear();
			return null;
		}
		
		// password authentication
		if (getTextFields().getPassword().length() == 0) {
			getTextFields().setPasswordError("Please enter your password");
			list.clear();
			return null;
		} 
		
		if (PASSWORD_AUTH.authenticate(getTextFields().getPassword().toCharArray(), list.get(0).getPasswordHash()) == false) {
			getTextFields().setPasswordError("Incorrect password");
			list.clear();
			return null;
		}
		
		// user has logged-in
		user = list.get(0);
		list.clear();
			
		return "/index?faces-redirect=true";
	}
	
	public String onResendAccountVerification() {
		// email validation
		if (getTextFields().getEmail().length() == 0) {
			getTextFields().setEmailError("Please enter your email");
			return null;
		} 
		
		List<Users> list = usersFacade.findEmail(getTextFields().getEmail());
		if (list.isEmpty() == true) {
			getTextFields().setEmailError("Email entered is not register");
			list.clear();
			return null;
		}
		
		if (list.get(0).getRequestType() != REQUEST_ACCOUNT_VERIFICATION) {
			getTextFields().setEmailError("Email entered is already verified");
			list.clear();
			return null;
		}
		
		Users selectedUser = list.get(0);
		list.clear();
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		String hash = PASSWORD_AUTH.hash((getTextFields().getEmail() + ipAddress).toCharArray());
		
		selectedUser.setRequestKey(hash);
		selectedUser.setRequestTimestamp(null);
		selectedUser.setRequestType(REQUEST_ACCOUNT_VERIFICATION);
					
		usersFacade.edit(selectedUser);
			
		// send email
		String url = "Goto this link to verify email: http://localhost:8080/MarketPlace/users/AccountVerification.xhtml?key=" + hash;
		getTextFields().setEmailError(url);
	
		return null;
	}
	
	/*********************
		REGISTER BUTTON
		-> Method is called from the "users/Register.xhtml" file
	*********************/	
	public String onSignUp() {
		Matcher matches;
		
		// username validation
		if (getTextFields().getName().length() == 0) {
			getTextFields().setNameError("Please enter your name");
		} else {
			matches = Regex.NAME.matcher(getTextFields().getName());
			if (matches.find() == false) {
				getTextFields().setNameError("Invalid name entered");
			}
		}
		
		// email validation
		if (getTextFields().getEmail().length() == 0) {
			getTextFields().setEmailError("Please enter your email");
		} else {
			matches = Regex.EMAIL.matcher(getTextFields().getEmail());
			if (matches.find() == false) {
				getTextFields().setEmailError("Invalid email entered");
			}
			else { // email existance check
				List<Users> list = usersFacade.findEmail(getTextFields().getEmail());
				if (list.isEmpty() == false) {
					getTextFields().setNameError("");
					getTextFields().setEmailError("Email entered is already register");
					list.clear();
					return null;
				}
				
				list.clear();
			}
		}
		
		// password strength check
		if (getTextFields().getPassword().length() == 0) {
			getTextFields().setPasswordError("Please enter a password");
		} else {
			matches = Regex.PASSWORD.matcher(getTextFields().getPassword());
			if (matches.find() == false) {
				getTextFields().setPasswordError("Password is weak");
			}
			else { // repeat password match check
				if (getTextFields().getRepeatPassword().length() == 0 || getTextFields().getRepeatPassword().equals(getTextFields().getPassword()) == false) {
					getTextFields().setRepeatPasswordError("Passwords don't match");
				}
			}
		}
		
		// if no errors in the end, register user
		if (getTextFields().isErrorFree() == true) {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}

			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
			
			String hash = PASSWORD_AUTH.hash((getTextFields().getEmail() + ipAddress).toCharArray());
			
			Users newUser = new Users(getTextFields().getName(), getTextFields().getEmail(), PASSWORD_AUTH.hash(getTextFields().getPassword().toCharArray()), currentTimestamp);
			newUser.setIpAddress(ipAddress);
			newUser.setLastLoginTimestamp(currentTimestamp);
			newUser.setRequestKey(hash);
			newUser.setRequestTimestamp(null);
			newUser.setRequestType(REQUEST_ACCOUNT_VERIFICATION);
					
			usersFacade.create(newUser);
			
			// send email
			String url = "Goto this link to verify your email: http://localhost:8080/MarketPlace/users/AccountVerification.xhtml?key=" + hash;
			getTextFields().setRepeatPasswordError(url);
			return null;
			
			//return "/index?faces-redirect=true";
		}
		
		return null;
	}
	
	/*********************
		LANGUAGE BUTTON
		-> Method is called from the "templates/Header.xhtml" file
	*********************/
	public String getLanguage() {
		if (FacesContext.getCurrentInstance().getViewRoot().getLocale() == Locale.ENGLISH) {
			return "en";
		} else if (FacesContext.getCurrentInstance().getViewRoot().getLocale() == Locale.FRENCH) {
			return "fr";
		}

		return "en";
	}

	public String onChangeLanguage(String language) {
		if (language.equalsIgnoreCase("en")) {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
		} else if (language.equalsIgnoreCase("fr")) {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.FRENCH);
		}

		return null;
	}
	
	/*********************
		LOGOUT BUTTON
		-> Method is called from the "templates/Header.xhtml" file
	*********************/
	public String onSignOut() {
		HttpSession session = (HttpSession) FacesContext
			.getCurrentInstance()
			.getExternalContext()
			.getSession(false);
		
		HttpServletRequest request = (HttpServletRequest) FacesContext
			.getCurrentInstance()
			.getExternalContext()
			.getRequest();
		
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		
		user.setLastLoginTimestamp(new Timestamp(System.currentTimeMillis()));
		user.setIpAddress(ipAddress);
		usersFacade.edit(user);
	
		session.invalidate();
		
		return "/index?faces-redirect=true";
	}
	
	/*********************
		FORGOT PASSWORD BUTTON
		-> Method is called from the "users/ForgotPassword.xhtml" file
	*********************/
	public String onForgotPassword() {
		// email validation
		if (getTextFields().getEmail().length() == 0) {
			getTextFields().setEmailError("Please enter your email");
			return null;
		} 
		
		List<Users> list = usersFacade.findEmail(getTextFields().getEmail());
		if (list.isEmpty() == true) {
			getTextFields().setEmailError("Email entered is not register");
			list.clear();
			return null;
		}
		
		if (list.get(0).getRequestType() == REQUEST_ACCOUNT_VERIFICATION) {
			getTextFields().setEmailError("Email entered is not verified account");
			list.clear();
			return null;
		}
		
		Users selectedUser = list.get(0);
		list.clear();
		
		String hash = PASSWORD_AUTH.hash((selectedUser.getEmail() + selectedUser.getIpAddress()).toCharArray());
		
		selectedUser.setRequestKey(hash);
		selectedUser.setRequestTimestamp(new Timestamp(System.currentTimeMillis()));
		selectedUser.setRequestType(REQUEST_RESET_PASSWORD);
		
		usersFacade.edit(selectedUser);
		
		// send email
		String url = "Goto this link to reset password: http://localhost:8080/MarketPlace/users/ResetPassword.xhtml?key=" + hash;
		getTextFields().setEmailError(url);
		return null;
		//return "/index?faces-redirect=true";
	}
	
	/*********************
		RESET PASSWORD BUTTON
		-> "users/ResetPassword.xhtml" handling
	*********************/
	public String onResetPassword() {
		// password strength check
		if (getTextFields().getPassword() == null) {
			getTextFields().setPasswordError("Please enter a password");
			return null;
		} 
		
		Matcher matches = Regex.PASSWORD.matcher(getTextFields().getPassword());
		if (matches.find() == false) {
			getTextFields().setPasswordError("Password is weak");
			return null;
		}
		
		if (getTextFields().getRepeatPassword() == null || getTextFields().getRepeatPassword().equals(getTextFields().getPassword()) == false) {
			getTextFields().setRepeatPasswordError("Passwords don't match");
			return null;
		}
		
		requestedUserResult.setPasswordHash(PASSWORD_AUTH.hash(getTextFields().getPassword().toCharArray()));
		requestedUserResult.setLastLoginTimestamp(new Timestamp(System.currentTimeMillis()));
		requestedUserResult.setRequestKey(null);
		requestedUserResult.setRequestTimestamp(null);
		requestedUserResult.setRequestType(REQUEST_INVALID);
		
		usersFacade.edit(requestedUserResult);
		
		// auto login
		user = requestedUserResult;
		requestedUserResult = null;
		
		return "/index?faces-redirect=true";
	}
	
	public class TextFields {
		private String name, nameError;
		private String email, emailError;
		private String password, passwordError;
		private String repeatPassword, repeatPasswordError;

		public TextFields() {
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getRepeatPassword() {
			return repeatPassword;
		}

		public void setRepeatPassword(String repeatPassword) {
			this.repeatPassword = repeatPassword;
		}

		// ERROR MESSAGES 
		public String getNameError() {
			return nameError;
		}

		public void setNameError(String nameError) {
			this.nameError = nameError;
		}

		public String getEmailError() {
			return emailError;
		}

		public void setEmailError(String emailError) {
			this.emailError = emailError;
		}

		public String getPasswordError() {
			return passwordError;
		}

		public void setPasswordError(String passwordError) {
			this.passwordError = passwordError;
		}

		public String getRepeatPasswordError() {
			return repeatPasswordError;
		}

		public void setRepeatPasswordError(String repeatPasswordError) {
			this.repeatPasswordError = repeatPasswordError;
		}
		
		public boolean isErrorFree() {
			return (nameError == null && emailError == null && passwordError == null && repeatPasswordError == null);
		}
	}
}
