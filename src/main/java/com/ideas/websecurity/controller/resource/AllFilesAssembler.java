package com.ideas.websecurity.controller.resource;

import com.ideas.websecurity.controller.AccessController;
import com.ideas.websecurity.dto.FileMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class AllFilesAssembler implements ResourceAssembler<FileMetadata, Resource<FileMetadata>> {
    @Override
    public Resource<FileMetadata> toResource(FileMetadata fileMetadata) {
        return new Resource<>(fileMetadata,
                linkTo(methodOn(AccessController.class).getFiles()).withRel("Your CRUD operation was successful. You can access all files here [GET]")
        );
    }
}
