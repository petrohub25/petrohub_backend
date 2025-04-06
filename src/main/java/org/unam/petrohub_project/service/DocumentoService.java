package org.unam.petrohub_project.service;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.unam.petrohub_project.model.documentos.Documento;
import org.unam.petrohub_project.model.documentos.DocumentoRegistryData;
import org.unam.petrohub_project.model.documentos.DocumentoRepository;

@Service
public class DocumentoService {

    private final CloudinaryService cloudinaryService;
    @Value("${documentos.path}")
    private String documentosPath;

    private final DocumentoRepository documentoRepository;

    public DocumentoService(DocumentoRepository documentoRepository, CloudinaryService cloudinaryService) {
        this.documentoRepository = documentoRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Transactional
    public Documento save(MultipartFile file, DocumentoRegistryData documentoRegistryData) throws IOException {
        Documento documento = new Documento(documentoRegistryData);
        documentoRepository.save(documento);

        try {
            String url = cloudinaryService.uploadFile(file);
            documento.setPath(url);
            documentoRepository.save(documento);
            return documento;
        } catch (Exception e) {
            throw new RuntimeException("Error al subir el archivo: "+e.getMessage());
        }
    }
}
