package com.vintagelibrary.backend.service

import com.vintagelibrary.backend.domain.entity.Book
import com.vintagelibrary.backend.domain.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class PostService(val bookRepository: BookRepository) {
    fun findById(id: Long) : Optional<Book> {
        return bookRepository.findById(id)
    }

    fun save(book: Book): Book {
        return bookRepository.save(book)
    }

    fun count() : Long{
        return bookRepository.count()
    }

    @Throws(IOException::class)
    fun imageUpload(
        uploadDir: String?, fileName: String,
        multipartFile: MultipartFile
    ) {
        val uploadPath: Path = Paths.get(uploadDir)
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath)
        }
        try {
            multipartFile.inputStream.use { inputStream ->
                val filePath: Path = uploadPath.resolve(fileName)
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
            }
        } catch (ioe: IOException) {
            throw IOException("Could not save image file: $fileName", ioe)
        }
    }
}