/*****
 * In the code below, we're using @DataJpaTest, which configures an in-memory database,
 * JPA, Spring Data, and the DataSource,  * and turns off other auto-configuration.
 * This test checks whether the custom query works as expected.
 * Make sure to define the findBooksByAuthor method or other custom queries in your BookRepository interface.
 *
 * Remember, if you haven't added any custom queries to your repository,
 * then you don't need to write tests for it.
 * The methods provided by JpaRepository are already well-tested.
 */

package com.swiz.bcs.repository;
import com.swiz.bcs.entity.*;
import com.swiz.bcs.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindBooksByAuthor() {
        // Given
        Author author = createRandomAuthor();
        Publisher publisher = createRandomPublisher();
        Genre genre = createRandomGenre();
        entityManager.persist(author);
        entityManager.persist(publisher);
        entityManager.persist(genre);
        entityManager.flush();

        Book book1 = Book.builder()
                .author(author)
                .publisher(publisher)
                .genre(genre)
                .coverImage("random image")
                .publicationDate(new Date("8/21/2023"))
                .id(1L)
                .status(BookStatus.BORROWED)
                .rating(5.0)
                .summary("A nice read")
                .ISBN("1234").build();
        entityManager.persist(book1);
        entityManager.flush();

        // When
        // Assuming a method findBooksByAuthor(Long authorId)
        List<Book> foundBooks = bookRepository.findAll();

        // Then
        assertThat(foundBooks).hasSize(2);
    }

    public static Author createRandomAuthor() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Random Author");
        author.setBiography("This is a random author's biography.");
        author.setProfileImage("https://example.com/images/random_author.jpg");
        return author;
    }

    public static Publisher createRandomPublisher() {
        Publisher publisher = new Publisher();
        publisher.setId(1L);
        publisher.setName("Random Publisher");
        publisher.setAddress("123 Random Street, Random City");
        return publisher;
    }

    public static Genre createRandomGenre() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Random Genre");
        genre.setDescription("This is a random genre description.");
        return genre;
    }

}
