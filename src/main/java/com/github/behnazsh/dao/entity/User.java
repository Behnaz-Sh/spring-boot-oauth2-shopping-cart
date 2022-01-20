package com.github.behnazsh.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author Behnaz Sh
 */

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class User extends BaseEntity {
   
	private static final long serialVersionUID = 7657451394244852266L;

    @Column(name = "user_name")
    private String username;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "enabled")
    private boolean enabled;
    
    @Column(name = "account_non_expired")
    private boolean accountNonExpired;
    
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;
    
    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_users",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "role_user_fk_1"))},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "role_user_fk_2"))})
    private List<Role> roles;

    public User(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        this.accountNonExpired = user.isAccountNonExpired();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
        this.accountNonLocked = user.isAccountNonLocked();
        this.roles = user.getRoles();
    }
}