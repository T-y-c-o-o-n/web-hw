package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @noinspection UnstableApiUsage
 */
public class UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private static final String PASSWORD_SALT = "177d4b5f2e4f4edafa7404533973c04c513ac619";

    public void validateRegistration(User user, String password, String passwordConfirmation) throws ValidationException {
        if (Strings.isNullOrEmpty(user.getLogin())) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8 letters");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }

        if (Strings.isNullOrEmpty(user.getEmail())) {
            throw new ValidationException("Email is required");
        }
        if (!(user.getEmail().indexOf('@') > 0 && user.getEmail().lastIndexOf('@') + 1 < user.getEmail().length()
                && user.getEmail().indexOf('@') == user.getEmail().lastIndexOf('@'))) {
            throw new ValidationException("Incorrect email");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("Email is already in user");
        }

        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4 characters");
        }
        if (password.length() > 12) {
            throw new ValidationException("Password can't be longer than 12 characters");
        }

        if (Strings.isNullOrEmpty(passwordConfirmation)) {
            throw new ValidationException("Password Confirmation is required");
        }
        if (!password.equals(passwordConfirmation)) {
            throw new ValidationException("Passwords are not equal");
        }
    }

    public void register(User user, String password) {
        userRepository.save(user, getPasswordSha(password));
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashBytes((PASSWORD_SALT + password).getBytes(StandardCharsets.UTF_8)).toString();
    }

    public User find(Long id) {
        return userRepository.find(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void validateEnter(String loginOrEmail, String password) throws ValidationException {
        User user = findByLoginOrEmailAndPassword(loginOrEmail, password);
        if (user == null) {
            throw new ValidationException("Invalid login/email or password");
        }
    }

    public User findByLoginOrEmailAndPassword(String loginOrEmail, String password) {
        User user = userRepository.findByLoginAndPasswordSha(loginOrEmail, getPasswordSha(password));
        if (user == null) {
            user = userRepository.findByEmailAndPasswordSha(loginOrEmail, getPasswordSha(password));
        }
        return user;
    }

    public void validateUpdatingAdmin(long loggedUserId, String targetIdString) throws ValidationException {
        User user = userRepository.find(loggedUserId);
        if (user == null) {
            throw new ValidationException("You are hacker! To do this you should log in");
        }
        if (!user.isAdmin()) {
            throw new ValidationException("To control admins you should be admin");
        }

        boolean incorrectId = false;
        try {
            long targetId = Long.parseLong(targetIdString);
            if (userRepository.find(targetId) == null) {
                incorrectId = true;
            }
        } catch (NumberFormatException e) {
            incorrectId = true;
        }
        if (incorrectId) {
            throw new ValidationException("you try to admin/disadmin incorrect user");
        }
    }

    public void updateAdmin(long id, boolean admin) {
        userRepository.updateAdmin(id, admin);
    }
}
