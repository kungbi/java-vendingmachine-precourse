package vendingmachine.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import vendingmachine.domain.Product;

public class ProductRepository implements Repository<Product> {
    private final List<Product> products = new ArrayList<>();

    @Override
    public void add(Product product) {
        addValidate(product);
        products.add(product);
    }

    private void addValidate(Product product) {
        if (product.getQuantity() < 1) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (this.exists(product.getName())) {
            throw new IllegalArgumentException("Product already exists");
        }
        if (product.getPrice() < 1_000) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (product.getPrice() % 10 != 0) {
            throw new IllegalArgumentException("Price must be a multiple of 10");
        }
    }

    @Override
    public void remove(Product data) {

    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Optional<Product> findByName(String name) {
        return products.stream().filter(product -> product.getName().equals(name)).findFirst();
    }

    @Override
    public List<Product> findAll() {
        return List.copyOf(products);
    }

    @Override
    public void update(String name, Product newData) {

    }

    @Override
    public boolean exists(String name) {
        return this.findByName(name).isPresent();
    }
}
