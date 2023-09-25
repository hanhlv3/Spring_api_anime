package com.api_anime.anime.service;

import com.api_anime.anime.entity.Category;
import com.api_anime.anime.entity.EvaluateFilm;
import com.api_anime.anime.entity.Film;
import com.api_anime.anime.entity.User;
import com.api_anime.anime.execptions.BadRequestException;
import com.api_anime.anime.model.EvaluateModel;
import com.api_anime.anime.model.FilmModel;
import com.api_anime.anime.repository.CategoryRepository;
import com.api_anime.anime.repository.EvaluateFilmRepository;
import com.api_anime.anime.repository.FilmRepository;
import com.api_anime.anime.repository.UserRepository;
import com.api_anime.anime.utils.CheckFile;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EvaluateFilmRepository evaluateFilmRepository;

    private final Path storageFolder = Paths.get("uploads");

    public FilmServiceImpl() {
        try {
            Files.createDirectories(storageFolder);
        }catch (IOException exception) {
            throw new RuntimeException("Cannot initialize storage", exception);
        }
    }

    @Override
    public Optional<Film> getFilmById(long id) {

        Optional<Film> film = filmRepository.findById(id);
        return film;
    }

    @Override
    public List<Film> getAllFimls() {
        List<Film> filmList = filmRepository.findAll();

        for (Film f: filmList) {
            for (Category cat: f.getCategories()) {
                cat.setFilms(null);
            }
        }
        return filmList;
    }

    @Override
    public List<Film> getFilmByCategories(long id) {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found category"));
        List<Film> filmList = filmRepository.findAllByCategories(category);
        return filmList;
    }

    @Override
    public Film insertFilm(MultipartFile file, FilmModel filmModel) throws IOException, ExecutionException, InterruptedException {

        boolean checkFileImage = CheckFile.isImageFile(file);
        if (!checkFileImage) throw new RuntimeException("File must have image file");

        // rename file
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String generatedFileName = UUID.randomUUID().toString().replace("-", "");
        generatedFileName = generatedFileName + "." + fileExtension;

        Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
        Files.write(destinationFilePath, file.getBytes());

        Film film = new Film();

        List<Category> categoryList = new ArrayList<>();
        // save category_film_map
        for (long id: filmModel.getCategories()) {
            Category category = categoryRepository.findById(id).orElseThrow(() -> new  EntityNotFoundException("Not found category"));
            categoryList.add(category);
        }

        film.setCategories(categoryList);
        film.setFilmName(filmModel.getFilmName());
        film.setDescription(filmModel.getDescription());
        film.setEpisodesQuantity(filmModel.getEpisodesQuantity());
        film.setReleaseDate(filmModel.getReleaseDate());
        film.setImg(generatedFileName);

        filmRepository.save(film);

        return film;
    }

    @Override
    public byte[] getImage(String fileName) {

        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else {
                throw new RuntimeException(
                        "Could not read file: " + fileName);
            }
        }
        catch (IOException exception) {
            throw new RuntimeException("Could not read file: " + fileName, exception);
        }
    }

    @Override
    public Film updateFilm(long id, MultipartFile file, FilmModel filmModel) throws IOException {


        // check film is exits
        Optional<Film> film = filmRepository.findById(id);
        if(film.isEmpty()) throw  new BadRequestException();

        Film filmUpdate = film.get();

        // check has update image
        if(!file.isEmpty()) {
            // delete old image
            String oldImage = filmUpdate.getImg();

            Path filePathOld = Paths.get("uploads/" + oldImage);
            Files.delete(filePathOld);

            // add new image
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName + "." + fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);

            // update new image
            filmUpdate.setImg(generatedFileName);
        }



        List<Category> categoryList = new ArrayList<>();
        filmUpdate.getCategories().clear();

        for (long i: filmModel.getCategories()) {
            Category cat = categoryRepository.findById(i).orElseThrow(() -> new EntityNotFoundException("New Category not found"));
            categoryList.add(cat);

        }
        // update film
        filmUpdate.setCategories(categoryList);
        filmUpdate.setFilmName(filmModel.getFilmName());
        filmUpdate.setReleaseDate(filmModel.getReleaseDate());
        filmUpdate.setUpdatedAt(Calendar.getInstance().getTime());
        filmUpdate.setEpisodesQuantity(filmModel.getEpisodesQuantity());
        filmUpdate.setDescription(filmModel.getDescription());

        filmRepository.save(filmUpdate);

        return filmUpdate;
    }


    @Override
    public void deleteFilm(Film film) {
        // delete image
        String oldImage = film.getImg();
        Path filePathOld = Paths.get("uploads/" + oldImage);

        film.getCategories().clear();
        try {
            Files.delete(filePathOld);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        filmRepository.delete(film);
    }


    @Override
    public void evaluateFilm(String email, EvaluateModel evaluateModel) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Film film = filmRepository.findById(evaluateModel.getFilmId()).orElseThrow(() -> new EntityNotFoundException("Not found film"));

        EvaluateFilm evaluateFilm = evaluateFilmRepository.findByFilmAndUser(film, user);

        if (evaluateFilm == null) {
           evaluateFilm = new EvaluateFilm();
        }

        evaluateFilm.setFilm(film);
        evaluateFilm.setUser(user);
        evaluateFilm.setEvaluateValue((int) evaluateModel.getEvaluateValue());
        evaluateFilmRepository.save(evaluateFilm);
    }
}
