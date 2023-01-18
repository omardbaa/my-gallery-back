package com.mygallery.services;


import com.mygallery.controllers.FileController;
import com.mygallery.dtos.FileDto;
import com.mygallery.dtos.FileTag;
import com.mygallery.enities.File;
import com.mygallery.enities.FileResponse;
import com.mygallery.enities.Folder;
import com.mygallery.enities.Tag;
import com.mygallery.repositories.FileRepository;
import com.mygallery.repositories.FileTagRepository;
import com.mygallery.repositories.FolderRepository;
import com.mygallery.repositories.TagRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileService {


    //Create path to upload file in local storage
    private final Path rootPath = Paths.get("uploads");
    @Autowired
    private final FileRepository fileRepository;
    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private FileTagRepository fileTagRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    //with this methode we can upload a file using MultipartFile interface

    public File Upload(MultipartFile file) throws IOException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        File formFile = new File(fileName, file.getContentType(), file.getSize());


        String extension = Optional.ofNullable(fileName).filter(f -> f.contains(".")).map(f -> f.substring(fileName.lastIndexOf(".") + 1)).get().toLowerCase();

        LinkOption[] linkOptions = new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
        try {
            if (Files.notExists(rootPath, linkOptions)) {

                Files.createDirectory(rootPath);


            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        try {
            fileRepository.save(formFile);
            Files.copy(file.getInputStream(), this.rootPath.resolve(formFile.getId() + "." + extension));
            formFile.setExtension(extension);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        String displayName = formFile.getId() + "." + extension;
        formFile.setDisplayName(displayName);

        String fileUrl = "http://localhost:8080/api/v1/file/display/" + formFile.getId() + "." + extension;

        formFile.setUrl(fileUrl);


        String fileUrlDowload = "http://localhost:8080/api/v1/file/download/" + formFile.getId() + "." + extension;

        formFile.setDownloadUrl(fileUrlDowload);
        return fileRepository.save(formFile);


    }


    //Load a file by id

    public Resource getFile(String id) {

        try {
            Path file = rootPath.resolve(id);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        //return fileRepository.findById(id).get();
    }


    //Load all files
    public Stream<File> getAllFiles() {
        Sort nameSort = Sort.by("name");
        Sort sizeSort = Sort.by("size");
        Sort groupBySort = sizeSort.and(nameSort);
        return fileRepository.findAll(groupBySort).stream();
    }

    public String Extension(String filename) {
        File file = new File();
        return FilenameUtils.getExtension(file.getName());
    }


    //Delete file by id
    public boolean delete(String id) {


        try {


            String extension = FilenameUtils.getExtension(fileRepository.getName(id));
            Path filepath = rootPath.resolve(id + "." + extension);

            Files.deleteIfExists(filepath);
            fileRepository.deleteById(id);
            return Files.deleteIfExists(filepath);


        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }


    }


    public File loadFileByName(String fileName) {
        return fileRepository.findByName(fileName);
    }

    public Optional<File> getfilebyId(String id) {
        return fileRepository.findById(id);
    }

    public String getFileType(String type) {

        return fileRepository.getType(type);
    }

    public File FindFileById(String fileId) {
        return fileRepository.findFileById(fileId);
    }


    public File findById(String id) {
        return fileRepository.findById(id).get();
    }


    public File findFileById(String Id) {
        return fileRepository.findFileById(Id);
    }


    public List<File> findPaginated(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<File> pagedResult = fileRepository.findAll(paging);

        return pagedResult.toList();
    }

    //    search by a keyword
    public List<File> listAll(String keyword) {
        if (keyword != null) {
            return fileRepository.search(keyword);
        }
        return fileRepository.findAll();
    }

    // convert Entity into DTO
    private FileDto mapToDTO(File file) {
        FileDto fileDto = new FileDto();
        fileDto.setId(file.getId());
        fileDto.setName(file.getName());
        fileDto.setExtension(file.getExtension());
        fileDto.setSize(file.getSize());
        fileDto.setType(file.getType());

        return fileDto;
    }

    // convert DTO to entity
    private File mapToEntity(FileDto fileDto) {
        File file = new File();
        file.setName(fileDto.getName());

        file.setExtension(fileDto.getExtension());
        file.setSize(fileDto.getSize());
        file.setType(fileDto.getType());
        return file;
    }


    //Display File content

    public FileResponse getAllFiles(int pageNo, int pageSize, String sortBy, String sortDir, String keyword) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<File> files = fileRepository.findAll(pageable, keyword);


        // get content for page object
        List<File> listOfFiles = files.getContent();


        List<FileDto> content = listOfFiles.stream().map(file -> mapToDTO(file)).collect(Collectors.toList());
        content.forEach(fileDto -> {
            String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile", fileDto.getId() + "." + fileDto.getExtension()).build().toString();

            fileDto.setUrl(url);
            String downloadUrl = MvcUriComponentsBuilder.fromMethodName(FileController.class, "download", fileDto.getId() + "." + fileDto.getExtension()).build().toString();

            fileDto.setDownloadUrl(downloadUrl);


        });
        FileResponse fileResponse = new FileResponse();
        fileResponse.setContent(content);
        fileResponse.setPageNo(files.getNumber());
        fileResponse.setPageSize(files.getSize());
        fileResponse.setTotalElements(files.getTotalElements());
        fileResponse.setTotalPages(files.getTotalPages());
        fileResponse.setLast(files.isLast());

        return fileResponse;
    }


    //Delete tage from file by id
    public void deleteTag(String fileId, Long tagId) {
        fileRepository.deleteTag(fileId, tagId);
    }


    //get all files of tag
    public List<File> getAllFilesOfTag(Long tagId) {
        Tag tag = this.tagRepository.findByTagId(tagId);
        List<File> files = (List<File>) tag.getFiles();
        return files;

    }


    //get all files of folder
    public List<File> getAllFilesOfFolder(Long folderId) {
        Folder folder = this.folderRepository.findByFolderId(folderId);
        List<File> files = (List<File>) folder.getFiles();
        return files;

    }


    public void addTagToFile(String fileId, Long tagId) {
        File file = fileRepository.findById(fileId).orElseThrow();
        Tag tag = tagRepository.findById(tagId).orElseThrow();
        FileTag fileTag = new FileTag();
        fileTag.setFile(file);
        fileTag.setTag(tag);
        fileTagRepository.save(fileTag);
    }


    public void removeTagFromFile(String fileId, Long tagId) {
        File file = fileRepository.findById(fileId).orElseThrow();
        Tag tag = tagRepository.findById(tagId).orElseThrow();
        FileTag fileTag = fileTagRepository.findByFileAndTag(file, tag);
        fileTagRepository.delete(fileTag);
    }

    public List<File> getFilesByTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow();
        return fileTagRepository.findByTag(tag).stream().map(FileTag::getFile).collect(Collectors.toList());
    }

    public List<Tag> getTagsByFile(String fileId) {
        File file = fileRepository.findById(fileId).orElseThrow();
        return fileTagRepository.findByFile(file).stream().map(FileTag::getTag).collect(Collectors.toList());
    }


}




