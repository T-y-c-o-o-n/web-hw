package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();

    public List<Talk> findAll() {
        return talkRepository.findAll();
    }

    public void addTalk(Talk talk) {
        talkRepository.save(talk);
    }
}
