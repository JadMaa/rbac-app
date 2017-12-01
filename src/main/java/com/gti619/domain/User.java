package com.gti619.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

// Model des utilisateurs dans la BD de mongo
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String username;
    private String password;
    private List<String> authorities = new ArrayList<>();
    private String hashVersion;

    private List<String> oldPasswords = new ArrayList<>();
    private int consecutiveAttemptsFail;
    private Date unlockDate;
    private boolean accountNonExpired, accountNonLocked, credentialsNonExpired, enabled;

    public User(String username, String password, List<String> authorities) {
        this.username = username;
        this.setPassword(password);
        //Bcrypt utilise la version Blowfish pour le hash
        this.hashVersion = "Blowfish";
        this.authorities = authorities;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.consecutiveAttemptsFail = 0;
    }

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(String.valueOf(this.authorities));
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // Hash le password
    public void setPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.password = bCryptPasswordEncoder.encode(password);
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public Date getUnlockDate() {
        return unlockDate;
    }

    public void setUnlockDate(Date unlockDate) {
        this.unlockDate = unlockDate;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getHashVersion() {
        return hashVersion;
    }

    public void setHashVersion(String hashVersion) {
        this.hashVersion = hashVersion;
    }

    public List<String> getOldPasswords() {
        return oldPasswords;
    }

    public void setOldPasswords(List<String> oldPasswords) {
        this.oldPasswords = oldPasswords;
    }

    public int getConsecutiveAttemptsFail() {
        return consecutiveAttemptsFail;
    }

    public void setConsecutiveAttemptsFail(int consecutiveAttemptsFail) {
        this.consecutiveAttemptsFail = consecutiveAttemptsFail;
    }
}
