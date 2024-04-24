package com.example.mainservice.specifications;

import com.example.mainservice.models.entities.Department;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.entities.UserDepartmentRole;
import com.example.mainservice.models.enums.UserRoleInDepartment;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserSpecifications {

    public static Specification<User> nameContainsIgnoreCase(String keyword) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }
            return builder.like(builder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<User> hasDepartmentIn(Set<UUID> departments) {
        return (root, query, builder) -> {
            // Создаем соединение между таблицами User и UserDepartmentRole
            Join<User, UserDepartmentRole> departmentRolesJoin = root.join("departmentRoles", JoinType.RIGHT);
            // Создаем предикат для проверки наличия записей в departmentRoles
            Predicate hasDepartmentRoles = builder.isNotEmpty(root.get("departmentRoles"));
            // Создаем предикат для проверки, принадлежит ли запись departmentRoles одному из переданных департаментов
            Predicate inDepartments = departmentRolesJoin.get("department").get("id").in(departments);
            // Создаем предикат для комбинации условий
            Predicate finalPredicate = builder.and(hasDepartmentRoles, inDepartments);
            // Применяем предикат к запросу
            return finalPredicate;
        };
    }

    public static Specification<User> middleNameContainsIgnoreCase(String keyword) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }
            return builder.like(builder.lower(root.get("middleName")), "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<User> surnameContainsIgnoreCase(String keyword) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }
            return builder.like(builder.lower(root.get("surname")), "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<User> hasDepartment(Boolean hasDepartment) {
        if (hasDepartment == null) {
            return null; // если значение hasDepartment null, возвращаем null для игнорирования фильтрации
        } else if (hasDepartment) {
            return (root, query, builder) -> builder.isNotEmpty(root.get("departmentRoles"));
        } else {
            return (root, query, builder) -> builder.isEmpty(root.get("departmentRoles"));
        }
    }
    public static Specification<User> hasRole(UserRoleInDepartment role) {
        return (root, query, builder) -> {
            if (role != null) {
                Join<User, UserDepartmentRole> departmentRoleJoin = root.join("departmentRoles");
                Predicate rolePredicate = builder.isMember(role, departmentRoleJoin.get("roles"));
                return rolePredicate;
            } else {
                return null;
            }
        };
    }
    public static Specification<User> hasRoleAndDepartment(UUID departmentId, UserRoleInDepartment role) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (departmentId != null && role != null) {
                Join<User, UserDepartmentRole> departmentRoleJoin = root.join("departmentRoles");
                Join<UserDepartmentRole, Department> departmentJoin = departmentRoleJoin.join("department");

                Predicate departmentIdPredicate = builder.equal(departmentJoin.get("id"), departmentId);
                Predicate rolePredicate = builder.isMember(role, departmentRoleJoin.get("roles"));

                predicate = builder.and(departmentIdPredicate, rolePredicate);
            } else if (departmentId != null) {
                Join<User, UserDepartmentRole> departmentRoleJoin = root.join("departmentRoles");
                Join<UserDepartmentRole, Department> departmentJoin = departmentRoleJoin.join("department");

                predicate = builder.equal(departmentJoin.get("id"), departmentId);
            } else if (role != null) {
                Join<User, UserDepartmentRole> departmentRoleJoin = root.join("departmentRoles");

                predicate = builder.isMember(role, departmentRoleJoin.get("roles"));
            }

            return predicate;
        };
    }
    public static Specification<User> withDepartmentIdEquals(UUID departmentId) {
        return (root, query, builder) -> {
            if (departmentId != null) {
                Join<User, UserDepartmentRole> departmentRoleJoin = root.join("departmentRoles");
                Join<UserDepartmentRole, Department> departmentJoin = departmentRoleJoin.join("department");
                Predicate departmentIdPredicate = builder.equal(departmentJoin.get("id"), departmentId);
                return departmentIdPredicate;
            } else {
                return null;
            }
        };
    }

    public static Specification<User> usernameContainsIgnoreCase(String keyword) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }
            return builder.like(builder.lower(root.get("username")), "%" + keyword.toLowerCase() + "%");
        };
    }
}
