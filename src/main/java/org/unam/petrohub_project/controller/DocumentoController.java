package org.unam.petrohub_project.controller;

import java.net.URI;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PageableDefault;
import org.unam.petrohub_project.model.documentos.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/")
public class DocumentoController {
    private final DocumentoRepository documentoRepository;

    public DocumentoController(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    @PostMapping("/subir")
    public ResponseEntity<ResponseDocumentoData> saveDocument(@RequestBody @Valid DocumentoRegistryData documentoRegistryData, UriComponentsBuilder uriBuilder) {
        Documento documento = documentoRepository.save(new Documento(documentoRegistryData));
        ResponseDocumentoData responseDocumentoData = new ResponseDocumentoData(documento);
        URI url = uriBuilder.path("/documentos/{documentoId}").buildAndExpand(documento.getDocumentoId()).toUri();
        return ResponseEntity.created(url).body(responseDocumentoData);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<ListDocumentoData>> findDocumentsByFilters(@PageableDefault(size = 4, sort = "documentoId")Pageable pageable, @RequestBody ParamDocumentoData paramDocumentoData) {
        Specification<Documento> specification = DocumentoSpecification.withFilters(paramDocumentoData);
        Page<ListDocumentoData> documentos = documentoRepository.findAll(specification, pageable).map(ListDocumentoData::new);
        return ResponseEntity.ok(documentos);
    }
}
