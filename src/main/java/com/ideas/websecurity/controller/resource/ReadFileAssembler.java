package com.ideas.websecurity.controller.resource;

import com.ideas.websecurity.controller.AccessController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ReadFileAssembler implements ResourceAssembler<String, Resource<String>> {
    @Override
    public Resource<String> toResource(String s) {
        return new Resource<>(s,
                linkTo(methodOn(AccessController.class).getFiles()).withRel("You can access all files here [GET]")
        );
    }
}
