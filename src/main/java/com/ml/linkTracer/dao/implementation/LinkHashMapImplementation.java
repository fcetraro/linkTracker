package com.ml.linkTracer.dao.implementation;

import com.ml.linkTracer.dao.LinkDAO;
import com.ml.linkTracer.model.request.CreateLinkDTO;
import com.ml.linkTracer.model.response.LinkDTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class LinkHashMapImplementation implements LinkDAO {
    HashMap<Integer, LinkDTO> repository = new HashMap<>();
    int nextAvailableKey = 0;
    @Override
    public LinkDTO add(CreateLinkDTO link) {
        LinkDTO newLink = new LinkDTO();
        newLink.setUrl(link.getUrl());
        newLink.setTimesUsed(0);
        newLink.setActive(true);
        newLink.setPassword(link.getPassword());
        newLink.setId(nextAvailableKey);
        repository.put(nextAvailableKey, newLink);
        nextAvailableKey++;
        return newLink;
    }

    @Override
    public LinkDTO modify(int id, LinkDTO link) {
        if(repository.containsKey(id)) return repository.replace(id, link);
        return null;
    }

    @Override
    public LinkDTO get(int id) {
        if(repository.containsKey(id)) return repository.get(id);
        return null;
    }
}
