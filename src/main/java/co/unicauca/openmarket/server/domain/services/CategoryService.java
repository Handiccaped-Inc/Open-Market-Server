package co.unicauca.openmarket.server.domain.services;

import co.unicauca.openmarket.server.access.ICategoryRepository;
import co.unicauca.openmarket.commons.domain.Category;
import co.unicauca.openmarket.commons.infra.JsonError;
import co.unicauca.openmarket.commons.infra.Utilities;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    ICategoryRepository repository;

    public CategoryService(ICategoryRepository repository) {
        this.repository = repository;
    }

    public synchronized List<Category> findAll() {
        return repository.findAll();
    }

    public String save(Category category) {

        List<JsonError> errors = new ArrayList<>();

        if (category.getName().isEmpty()) {
            errors.add(new JsonError("400", "BAD_REQUEST", "Nombre debe ser obligatorio. "));
        }

        if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorsJson = gson.toJson(errors);
            return errorsJson;
        }

        return "";
    }

    public List<Category> findAllCategory() {
        List<Category> category = new ArrayList<>();
        category = repository.findAll();

        return category;
    }

    public Category findCategoryById(Long id) {
        return repository.findById(id);
    }

    public String deleteCategory(Long id) {
        String respuesta;
        if (repository.delete(id)) {
            respuesta = "ok";
        } else {
            respuesta = "Error";
        }

        return respuesta;
    }

    public String editCategory(Long categoryId, Category category) {
        String respuesta;

        // Validate category
        if (category == null || category.getName().isEmpty()) {
            return "Error";
        }

        if (repository.edit(categoryId, category)) {
            respuesta = "ok";
        } else {
            respuesta = "Error";
        }

        return respuesta;

    }

    public List<Category> findCategoryByName(String name) {
        List<Category> category = new ArrayList<>();
        category = repository.findByName(name);

        return category;
    }
}
