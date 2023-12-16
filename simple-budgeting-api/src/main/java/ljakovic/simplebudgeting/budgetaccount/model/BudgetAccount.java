package ljakovic.simplebudgeting.budgetaccount.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ljakovic.simplebudgeting.appuser.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BudgetAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Double totalResources;
    private Date dateCreated;
    private Date dateModified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private AppUser user;
}