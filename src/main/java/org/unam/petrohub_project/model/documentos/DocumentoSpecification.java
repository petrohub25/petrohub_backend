package org.unam.petrohub_project.model.documentos;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DocumentoSpecification {
    public static Specification<Documento> withFilters(ParamDocumentoData paramDocumentoData) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (paramDocumentoData.titulo() != null) {
                predicates.add(criteriaBuilder.like(root.get("titulo").as(String.class), "%" + paramDocumentoData.titulo() + "%" ));
            }
            if (paramDocumentoData.etiquetas() != null) {
                predicates.add(criteriaBuilder.like(root.get("etiquetas").as(String.class), "%" + paramDocumentoData.etiquetas() + "%" ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
