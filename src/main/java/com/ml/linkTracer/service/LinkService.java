package com.ml.linkTracer.service;

import com.ml.linkTracer.model.request.CreateLinkDTO;
import com.ml.linkTracer.model.response.LinkDTO;
import com.ml.linkTracer.model.response.MetricsDTO;

public interface LinkService {
    LinkDTO createNewLink(CreateLinkDTO newLink);
    LinkDTO redirect(String linkId, String password);
    MetricsDTO getMetrics(String linkId);
    LinkDTO invalidateLink(String linkId);
}
