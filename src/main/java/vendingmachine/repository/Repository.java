package vendingmachine.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    void add(T data);

    void remove(T data);

    int getSize();

    Optional<T> findByName(String name);

    List<T> findAll();

    void update(String name, T newData);

    boolean exists(String name);

}
