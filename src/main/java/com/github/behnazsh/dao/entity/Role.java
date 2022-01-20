package com.github.behnazsh.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author Behnaz Sh
 */

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "name")
	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "permission_roles",
			joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "permission_role_fk_1"))},
			inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id" ,foreignKey = @ForeignKey(name = "permission_role_fk_2"))})
	private List<Permission> permissions;
}
