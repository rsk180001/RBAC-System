package com.ideas.websecurity.controller;

import com.ideas.websecurity.controller.resource.AllFilesAssembler;
import com.ideas.websecurity.controller.resource.FileResourceAssembler;
import com.ideas.websecurity.controller.resource.ReadFileAssembler;
import com.ideas.websecurity.dto.FileMetadata;
import com.ideas.websecurity.service.FileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController	
@RequestMapping(path = "/public")
public class AccessController {
    /*http://localhost:8080/swagger-ui.html#/*/

    private FileService fileService;
    private FileResourceAssembler fileResourceAssembler;
    private AllFilesAssembler allFilesAssembler;
    private ReadFileAssembler readFileAssembler;

    @Autowired
    public AccessController(FileService fileService, FileResourceAssembler fileResourceAssembler, AllFilesAssembler allFilesAssembler, ReadFileAssembler readFileAssembler) {
        this.fileService = fileService;
        this.fileResourceAssembler = fileResourceAssembler;
        this.allFilesAssembler = allFilesAssembler;
        this.readFileAssembler = readFileAssembler;
    }

    @ApiOperation("It will list all file metadata")
    @GetMapping(path = "/files")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public Resources<Resource<FileMetadata>> getFiles() 
    {
        List<Resource<FileMetadata>> allFiles = fileService.getAllFiles().stream()
                .map(fileResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(allFiles, linkTo(methodOn(AccessController.class).getFiles()).withSelfRel());
    }

    @ApiOperation("It will fetch file metadata by name")
    @GetMapping(path = "/files/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public Resource<FileMetadata> fileByName(@PathVariable String name) {
        return fileResourceAssembler.toResource(fileService.getFileByName(name));
    }

    @ApiOperation("It will fetch file content by name")
    @GetMapping(path = "/files/{name}/read")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public Resource<String> readFileByName(@PathVariable String name) {
        return readFileAssembler.toResource(fileService.getReadFileByName(name));
    }

    @ApiOperation("You can upload file here")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/uploadFile")
    public Resource<FileMetadata> uploadFile(@RequestParam("file") MultipartFile file) {
        FileMetadata fileMetadata = fileService.storeFile(file);
        return allFilesAssembler.toResource(fileMetadata);
    }

    @ApiOperation("It will delete file by name")
    @DeleteMapping(path = "/files/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResourceSupport> deleteFileByName(@PathVariable String name) {
        boolean deleteSuccessful = fileService.deleteFileByName(name);
        return deleteSuccessful ? ResponseEntity.ok(allFilesAssembler.toResource(new FileMetadata(name, "F:/E/JAVA/files/")))
                : getResponse(HttpStatus.LOCKED, "Unable to delete file. It might be locked by some another process. Please try after some time.");
    }

    private ResponseEntity<ResourceSupport> getResponse(HttpStatus locked, String message) 
    {
        return ResponseEntity.status(locked)
                .body(new VndErrors.VndError(locked.name(), message));
    }

}
