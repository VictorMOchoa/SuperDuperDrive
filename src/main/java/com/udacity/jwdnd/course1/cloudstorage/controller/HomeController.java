package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;


@Controller
public class HomeController {
    public final NoteService noteService;
    public final UserService userService;
    public final CredentialService credentialService;
    public final EncryptionService encryptionService;
    public final FileService fileService;

    public HomeController(NoteService noteService, UserService userService, CredentialService credentialService, EncryptionService encryptionService, FileService fileService) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
    }

    @RequestMapping("/home")
    public String getHomePage(Authentication authentication, Model model) {
        Integer userId = getUserIdFromAuth(authentication);
        List<Note> notes = noteService.getNotesForUser(userId);
        List<Credential> credentials = credentialService.getCredentialsForUser(userId);
        List<File> files = fileService.getFilesForUser(userId);
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        model.addAttribute("files", files);
        model.addAttribute("encryptionService",encryptionService);
        return "home";
    }

    private Integer getUserIdFromAuth(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        return user.getUserId();
    }

    @PostMapping("/createnote")
    public String createNote(Authentication authentication, @ModelAttribute NoteForm note, Model model) {
        if (note.getNoteId() == null) {
            Integer userId = getUserIdFromAuth(authentication);
            note.setUserId(userId);
            int noteId = noteService.addNote(note);

            if (noteWasCreatedSuccessfully(noteId)) {
                model.addAttribute("operationSuccess", true);
            } else {
                model.addAttribute("operationFailed", "Failed to create new note.");
            }
        } else {
            int noteId = noteService.updateNote(note);
            if (noteWasCreatedSuccessfully(noteId)) {
                model.addAttribute("operationSuccess", true);
            } else {
                model.addAttribute("operationFailed", "Failed to edit note.");
            }
        }
        return "result";
    }

    @GetMapping("/deletenote/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Model model){
        if (noteService.deleteNote(noteId) > 0) {
            model.addAttribute("operationSuccess", true);
        } else {
            model.addAttribute("operationFailed", "Failed to delete note.");
        }
        return "result";
    }

    @PostMapping("/savecredential")
    public String saveCredential(Authentication authentication, @ModelAttribute("credentialForm") CredentialForm credentialForm, Model model){
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        credentialForm.setUserId(user.getUserId());
        String key = createKey();
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), key);
        credentialForm.setPassword(encryptedPassword);

        if (credentialForm.getCredentialId() == null) {
            if (credentialService.addCredential(credentialForm, key) > 0) {
                model.addAttribute("operationSuccess", true);
            } else {
                model.addAttribute("operationFailed", "Failed to save credential.");
            }
        } else {
            if (credentialService.editCredential(credentialForm, key) > 0) {
                model.addAttribute("operationSuccess", true);
            } else {
                model.addAttribute("operationFailed", "Failed to edit credential.");
            }
        }

        return "result";
    }

    @PostMapping("/fileupload")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload, Model model) throws IOException {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        if (!fileService.filenameAvailable(fileUpload.getOriginalFilename(), user.getUserId())) {
            model.addAttribute("operationFailed", "File name not available. Could not upload.");
        } else {
            if (fileService.createFile(fileUpload, user.getUserId()) > 0) {
                model.addAttribute("operationSuccess", true);
            } else {
                model.addAttribute("operationFailed", "Failed to save file.");
            }
        }

        return "result";
    }

    @GetMapping("/viewfile/{fileId}")
    public ResponseEntity<Resource> viewFile(@PathVariable("fileId") Integer fileId) {
        File file = fileService.getFileById(fileId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(httpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + file.getFilename());
        httpHeaders.add("Cache-control", "no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Expires", "0");
        ByteArrayResource resource = new ByteArrayResource(file.getFiledata());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);
    }

    @GetMapping("/deletefile/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Model model){
        if (fileService.deleteFile(fileId) > 0) {
            model.addAttribute("operationSuccess", true);
        } else {
            model.addAttribute("operationFailed", "Failed to delete file.");
        }
        return "result";
    }

    @GetMapping("/deletecredential/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, Model model){
        if (credentialService.deleteCredential(credentialId) > 0) {
            model.addAttribute("operationSuccess", true);
        } else {
            model.addAttribute("operationFailed", "Failed to delete credential.");
        }
        return "result";
    }

    private String createKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    private boolean noteWasCreatedSuccessfully(int noteId) {
        return noteId > 0;
    }
}
