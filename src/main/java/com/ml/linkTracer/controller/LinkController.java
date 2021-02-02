package com.ml.linkTracer.controller;

import com.ml.linkTracer.model.request.CreateLinkDTO;
import com.ml.linkTracer.model.request.RedirectDTO;
import com.ml.linkTracer.model.response.LinkDTO;
import com.ml.linkTracer.model.response.MetricsDTO;
import com.ml.linkTracer.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LinkController {
    @Autowired
    private LinkService service;
    @PostMapping("/link")
    public LinkDTO addNewLink(@RequestBody CreateLinkDTO link){
        return service.createNewLink(link);
    }
    @GetMapping("/redirect/{linkId}/{password}")
    public ModelAndView redirect(@PathVariable String linkId, @PathVariable String password){
        String url = service.redirect(linkId, password).getUrl();
        return new ModelAndView("redirect:"+url);
    }
    @GetMapping("/metrics/{linkId}")
    public MetricsDTO getMetrics(@PathVariable String linkId){
        return service.getMetrics(linkId);
    }
    @PostMapping("/invalidate/{linkId}")
    public LinkDTO invalidateLink(@PathVariable String linkId){
        return service.invalidateLink(linkId);
    }
}
