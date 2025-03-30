package org.unam.petrohub_project.controller;

import java.net.URI;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.unam.petrohub_project.model.documentos.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.util.UriComponentsBuilder;
import org.unam.petrohub_project.service.DocumentoService;

@RestController
@RequestMapping("/")
public class DocumentoController {
    private final DocumentoRepository documentoRepository;
    private final DocumentoService documentoService;

    public DocumentoController(DocumentoRepository documentoRepository, DocumentoService documentoService) {
        this.documentoRepository = documentoRepository;
        this.documentoService = documentoService;
    }

    @GetMapping("")
    public ResponseEntity<Page<ListDocumentoData>> showDocuments(@PageableDefault(size = 4, sort = "documentoId")Pageable pageable) {
        Page<ListDocumentoData> documentos = documentoRepository.findAll(pageable).map(ListDocumentoData::new);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(documentos);
    }

    @PostMapping("/subir")
    public ResponseEntity<ResponseDocumentoData> saveDocument(@RequestParam("archivo")MultipartFile archivo, @RequestParam("titulo") String titulo, @RequestParam("tags") String tags, UriComponentsBuilder uriBuilder) throws IOException {
        DocumentoRegistryData documentoRegistryData = new DocumentoRegistryData(titulo, tags);
        Documento documento = documentoService.save(archivo, documentoRegistryData);
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
