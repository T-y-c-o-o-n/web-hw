package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.*;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.RoleRepository;
import ru.itmo.wp.repository.TagRepository;
import ru.itmo.wp.repository.UserRepository;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    /** @noinspection FieldCanBeLocal, unused */
    private final RoleRepository roleRepository;

    private final PostRepository postRepository;

    private final TagRepository tagRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PostRepository postRepository, TagRepository tagRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;

        for (Role.Name name : Role.Name.values()) {
            if (roleRepository.countByName(name) == 0) {
                roleRepository.save(new Role(name));
            }
        }
    }

    public User register(UserCredentials userCredentials) {
        User user = new User();
        user.setLogin(userCredentials.getLogin());
        userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(), userCredentials.getLogin(), userCredentials.getPassword());
        return user;
    }

    public boolean isLoginVacant(String login) {
        return userRepository.countByLogin(login) == 0;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public User findById(Long id) {
        return id == null ? null : userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }

    public void writePost(User user, PostCredentials postCredentials) {
        Post post = new Post();
        post.setTitle(postCredentials.getTitle());
        post.setText(postCredentials.getText());

        Set<Tag> tags = Arrays.stream(postCredentials.getTags().split("\\s+"))
                .filter((name) -> !name.isEmpty()).map(Tag::new).collect(Collectors.toSet());
        Set<Tag> updatedTags = new LinkedHashSet<>();
        for (Tag tag : tags) {
            if (tagRepository.countByName(tag.getName()) == 0) {
                tag = tagRepository.save(tag);
            } else {
                tag = tagRepository.findByName(tag.getName());
            }
            updatedTags.add(tag);
        }

        post.setTags(updatedTags);

        user.addPost(post);
        userRepository.save(user);
    }
}
