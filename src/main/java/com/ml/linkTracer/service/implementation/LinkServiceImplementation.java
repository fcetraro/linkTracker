package com.ml.linkTracer.service.implementation;

import com.ml.linkTracer.dao.LinkDAO;
import com.ml.linkTracer.exception.ApiRequestException;
import com.ml.linkTracer.exception.InvalidURLException;
import com.ml.linkTracer.model.request.CreateLinkDTO;
import com.ml.linkTracer.model.response.LinkDTO;
import com.ml.linkTracer.model.response.MetricsDTO;
import com.ml.linkTracer.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class LinkServiceImplementation implements LinkService {
    @Autowired
    private LinkDAO repository;
    @Override
    public LinkDTO createNewLink(CreateLinkDTO newLink) {
        return repository.add(newLink);
    }

    private boolean isURL(String url) {
        try {
            (new java.net.URL(url)).openStream().close();
            return true;
        } catch (Exception e) { }
        return false;
    }

    @Override
    public LinkDTO redirect(String linkId, String password) {
        try{
            LinkDTO fromDataBase = repository.get(Integer.parseInt(linkId));
            if(!password.equals(fromDataBase.getPassword())){
                throw new Exception("Not valid Password");
            }
            if(!isURL(fromDataBase.getUrl())) {
                throw new Exception("Not valid URL");
            }
            if(!fromDataBase.isActive()) {
                throw new Exception("Not active URL");
            }
            fromDataBase.setTimesUsed(fromDataBase.getTimesUsed()+1);
            repository.modify(fromDataBase.getId(),fromDataBase);
            return fromDataBase;
        } catch (Exception e) {
            throw new InvalidURLException(e.getMessage(), e);
        }
    }

    @Override
    public MetricsDTO getMetrics(String linkId) {
        try{
            LinkDTO fromDataBase = repository.get(Integer.parseInt(linkId));
            MetricsDTO newMetrics = new MetricsDTO();
            newMetrics.setTimesUsed(fromDataBase.getTimesUsed());
            newMetrics.setUrl(fromDataBase.getUrl());
            return newMetrics;
        } catch (Exception e) {
            throw new ApiRequestException("Id ingresado no valido", e);
        }
    }

    @Override
    public LinkDTO invalidateLink(String linkId) {
        try{
            LinkDTO fromDataBase = repository.get(Integer.parseInt(linkId));
            fromDataBase.setActive(false);
            repository.modify(fromDataBase.getId(),fromDataBase);
            return fromDataBase;
        } catch (Exception e) {
            throw new ApiRequestException("Id ingresado no valido", e);
        }
    }
}
