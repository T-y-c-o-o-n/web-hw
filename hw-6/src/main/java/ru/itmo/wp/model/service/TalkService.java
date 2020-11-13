package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    public List<Talk> findAll() {
        return talkRepository.findAll();
    }

    public void validate(long sourceUserId, String targetLogin, String text) throws ValidationException {
        if (userRepository.find(sourceUserId) == null) {
            throw new ValidationException("you are hacker !");
        }

        if (Strings.isNullOrEmpty(targetLogin) || userRepository.findByLogin(targetLogin) == null) {
            throw new ValidationException("chose correct receiver");
        }

        if (Strings.isNullOrEmpty(text)) {
            throw new ValidationException("text is empty");
        }
        if (text.length() >= 100) {
            throw new ValidationException("text should be shorter than 100 symbols");
        }
    }

    public void addTalk(Talk talk) {
        talkRepository.save(talk);
    }
}
