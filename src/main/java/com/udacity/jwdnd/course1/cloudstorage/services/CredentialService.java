package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getCredentialsForUser(Integer userId) {
        return credentialMapper.getCredentialsForUser(userId);
    }

    public int addCredential(CredentialForm credentialForm, String key) {;
        Credential credential = new Credential(credentialForm.getUrl(), credentialForm.getUsername(), key, credentialForm.getPassword(), credentialForm.getUserId());
        return credentialMapper.create(credential);
    }

    public int editCredential(CredentialForm credentialForm, String key) {;
        Credential credential = credentialMapper.getCredential(credentialForm.getCredentialId());
        credential.setKey(key);
        credential.setPassword(credentialForm.getPassword());
        credential.setUrl(credentialForm.getUrl());
        credential.setUsername(credentialForm.getUsername());
        return credentialMapper.update(credential);
    }

    public int deleteCredential(Integer credentialId) {
        return credentialMapper.delete(credentialId);
    }
}
