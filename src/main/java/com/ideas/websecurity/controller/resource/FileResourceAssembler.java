package com.ideas.websecurity.controller.resource;

import com.ideas.websecurity.controller.AccessController;
import com.ideas.websecurity.dto.FileMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class FileResourceAssembler implements ResourceAssembler<FileMetadata, Resource<FileMetadata>> {

    @Override
    public Resource<FileMetadata> toResource(FileMetadata fileMetadata) {
        return new Resource<>(fileMetadata,
                //uncomment this line if required
                //linkTo(methodOn(AccessController.class).fileByName(fileMetadata.getFileName())).withSelfRel(),
                linkTo(methodOn(AccessController.class).getFiles()).withRel("You can access all files here [GET]"),
                linkTo(methodOn(AccessController.class).readFileByName(fileMetadata.getFileName())).withRel("You can read this file here [GET]"),
                linkTo(methodOn(AccessController.class).deleteFileByName(fileMetadata.getFileName())).withRel("You can delete this file here [DELETE]")
        );
    }
}
