package org.unam.petrohub_project.service;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.unam.petrohub_project.model.documentos.Documento;
import org.unam.petrohub_project.model.documentos.DocumentoRegistryData;
import org.unam.petrohub_project.model.documentos.DocumentoRepository;

@Service
public class DocumentoService {

    @Value("${documentos.path}")
    private String documentosPath;

    private final DocumentoRepository documentoRepository;

    public DocumentoService(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    public Documento save(MultipartFile file, DocumentoRegistryData documentoRegistryData) throws IOException {
        Path directory = Paths.get(documentosPath).toAbsolutePath().normalize();

        if (!directory.toFile().exists()) {
            directory.toFile().mkdirs();
        }

        String fileName = file.getOriginalFilename();
        assert fileName != null;
        Path filePath = directory.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Documento documento = new Documento(documentoRegistryData, filePath.toString());

        return documentoRepository.save(documento);
    }
}
