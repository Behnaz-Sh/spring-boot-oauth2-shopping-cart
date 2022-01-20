package com.github.behnazsh.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Behnaz Sh
 */

@Entity
@Table(name = "permissions")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "name")
	private String name;

}
