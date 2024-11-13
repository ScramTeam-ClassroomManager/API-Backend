package it.unical.classroommanager_api.dto;

import it.unical.classroommanager_api.enums.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
public final class UserDto implements UserDetails, Serializable {
    private Long id;
    private Integer serialNumber;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
    }

    public String getUsername() {
        return serialNumber.toString();
    }

}
