package com.ml.linkTracer.dao;

import com.ml.linkTracer.model.request.CreateLinkDTO;
import com.ml.linkTracer.model.response.LinkDTO;

public interface LinkDAO {
    public LinkDTO add(CreateLinkDTO link);
    public LinkDTO modify(int id, LinkDTO link);
    public LinkDTO get(int id);
}
