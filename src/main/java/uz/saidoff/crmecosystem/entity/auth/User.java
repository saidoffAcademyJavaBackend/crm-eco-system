package uz.saidoff.crmecosystem.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.saidoff.crmecosystem.entity.*;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.enums.Permissions;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Builder
public class User extends AbsEntity implements UserDetails {
    @OneToOne
    private Attachment attachment;
    private String password;
    private String firstName;
    private String lastName;
    private String fatherName;

    @Column(unique = true)
    private String phoneNumber;

    private String secondPhoneNumber;

    private Date birthDate;

    private String birthPlace;

    private String currentResidence;

    private String numberOfChildren;

    private Boolean gender;

    @ManyToOne
    private Speciality speciality;

    @Column(unique = true)
    private String passportSeries;

    private Double salary;
    private Date startWork;
    private Date startStudying;
    private Integer warning;

    private double balance;

    @ManyToOne(optional = false)
    private Role role;

    @OneToMany
    private List<Notification> notifications;

    @OneToMany
    private List<ProjectUser> projectUsers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Warning> warnings;

    private boolean enabled = false;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean accountNonExpiredOrCredentialsNonExpired = true;


    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Permissions> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    public Integer getWarning() {
        return warnings != null ? warnings.size() : 0;
    }
}
