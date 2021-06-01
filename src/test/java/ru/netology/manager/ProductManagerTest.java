package ru.netology.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.domain.Book;
import ru.netology.domain.Product;
import ru.netology.domain.Smartphone;
import ru.netology.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)

public class ProductManagerTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    ProductManager manager;

    Product first = new Book(1, "novel", 300, "Lermontov");
    Product second = new Book(2, "poem", 200, "Pushkin");
    Product third = new Smartphone(3, "Iphone", 55000, "China");
    Product fourth = new Smartphone(4, "Huawei", 33000, "China");

    @BeforeEach
    public void SetUp() {
        manager.add(first);
        manager.add(second);
        manager.add(third);
        manager.add(fourth);
    }


    @Test
    void shouldSearchByBookName() {
        Product[] returned = new Product[]{first, second, third, fourth};
        doReturn(returned).when(repository).findAll();
        Product[] expected = new Product[]{second};
        Product[] actual = manager.searchBy("poem");
        assertArrayEquals(expected, actual);

    }

    @Test
    void whenManagerIsEmpty()
    {
        ProductManager emptyManager = new ProductManager(repository);

        Product[] returned = new Product[]{};
        doReturn(returned).when(repository).findAll();

        Product[] expected = new Product[]{};
        Product[] actual = emptyManager.searchBy("some product");

        assertArrayEquals(expected, actual);
    }

    @Test
    void whenManagerHaveOneProductAndSearchingNotExistsProduct()
    {
        ProductManager emptyManager = new ProductManager(repository);
        emptyManager.add(first);

        Product[] returned = new Product[]{first};
        doReturn(returned).when(repository).findAll();

        Product[] expected = new Product[]{};
        Product[] actual = manager.searchBy("not existing title");
        assertArrayEquals(expected, actual);
    }

    @Test
    void whenHaveOneProductAndWeSearchIt()
    {
        ProductManager emptyManager = new ProductManager(repository);
        emptyManager.add(first);

        Product[] returned = new Product[]{first};
        doReturn(returned).when(repository).findAll();

        Product[] expected = new Product[]{first};
        Product[] actual = manager.searchBy("Lermontov");
        assertArrayEquals(expected, actual);
    }

    @Test
    void whenManagerHaveFewProductsAndSearchingOneOfThem()
    {
        ProductManager emptyManager = new ProductManager(repository);
        emptyManager.add(first);
        emptyManager.add(second);

        Product[] returned = new Product[]{first, second};
        doReturn(returned).when(repository).findAll();

        Product[] expected = new Product[]{second};
        Product[] actual = manager.searchBy("Pushkin");
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldSearchByBookAuthor() {
        Product[] returned = new Product[]{first, second, third, fourth};
        doReturn(returned).when(repository).findAll();
        Product[] expected = new Product[]{first};
        Product[] actual = manager.searchBy("Lermontov");
        assertArrayEquals(expected, actual);

    }
    @Test
    void shouldSearchBySmartPhoneName() {
        Product[] returned = new Product[]{first, second, third, fourth};
        doReturn(returned).when(repository).findAll();
        Product[] expected = new Product[]{fourth};
        Product[] actual = manager.searchBy("Huawei");
        assertArrayEquals(expected, actual);

    }
    @Test
    void shouldSearchBySmartPhoneProducer() {
        Product[] returned = new Product[]{first, second, third, fourth};
        doReturn(returned).when(repository).findAll();
        Product[] expected = new Product[]{third, fourth};
        Product[] actual = manager.searchBy("China");
        assertArrayEquals(expected, actual);

    }

}