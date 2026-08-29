package br.com.barros.Cinema.database.model;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

@Builder
@Entity
@Table(name = "role")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class RoleEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Override
    public @Nullable String getAuthority() {
        return nome;
    }
}
