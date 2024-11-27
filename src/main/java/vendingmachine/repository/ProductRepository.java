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
        if (this.exists(product.getName())) {
            throw new IllegalArgumentException("Product already exists");
        }
    }

    public int getMinPrice() {
        List<Product> availableProducts = this.products.stream().filter(Product::isAvailableToBuy).toList();
        return availableProducts.stream().mapToInt(Product::getPrice).min().orElse(0);
    }

    public int getTotalQuantity() {
        return this.products.stream().mapToInt(Product::getQuantity).sum();
    }

    public boolean isStockEmpty() {
        if (0 < this.getTotalQuantity()) {
            return false;
        }
        return true;
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
        this.products.removeIf(product -> product.getName().equals(name));
        this.products.add(newData);
    }

    @Override
    public boolean exists(String name) {
        return this.findByName(name).isPresent();
    }
}
