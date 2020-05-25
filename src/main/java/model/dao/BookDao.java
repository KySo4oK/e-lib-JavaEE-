package model.dao;

import model.entity.Book;
import model.entity.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookDao extends GenericDao<Book> {
    String SQL_DELETE_ID_IN_SHELF = "update shelf set book_id = null where book_id = ?";
    String SQL_INSERT_INTO_BOOK_AUTHOR = "insert into book_author (book_id, author_id)\n" +
            "values ((select currval(pg_get_serial_sequence('book', 'book_id'))), ?)";
    String SQL_FIND_ALL = "select book.book_id     as \"bookId\",\n" +
            "       book.name        as \"bookName\",\n" +
            "       book.name_ua     as \"bookNameUa\",\n" +
            "       book.available,\n" +
            "       tag.tag_id       as \"tagId\",\n" +
            "       tag.name_ua      as \"tagNameUa\",\n" +
            "       tag.name         as \"tagName\",\n" +
            "       author.author_id as \"authorId\",\n" +
            "       author.name_ua   as \"authorNameUa\",\n" +
            "       author.name      as \"authorName\",\n" +
            "       shelf.shelf_id   as \"shelf_id\"\n" +
            "from book\n" +
            "         left join tag on book.tag_id = tag.tag_id\n" +
            "         left join book_author on book.book_id = book_author.book_id\n" +
            "         left join author on book_author.author_id = author.author_id\n" +
            "         left join shelf on book.book_id = shelf.book_id";
    String SQL_FIND_BY_ID = SQL_FIND_ALL + " where book.book_id = ?";
    String SQL_FIND_BY_NAME = SQL_FIND_ALL + " where book.name = ?";
    String SQL_FIND_BY_NAME_UA = SQL_FIND_ALL + " where book.name_ua = ?";
    String SQL_INSERT_BOOK_FIELDS = "insert into book (name, name_ua, available, tag_id) values (?, ?, ?, ?)";
    String SQL_UPDATE = "update book set available = ? where book_id = ?";
    String SQL_DELETE = "delete from book where book_id = ?";
    String SQL_FIND_ALL_AVAILABLE = "select book.book_id     as \"bookId\",\n" +
            "       book.name        as \"bookName\",\n" +
            "       book.name_ua     as \"bookNameUa\",\n" +
            "       book.available,\n" +
            "       tag.tag_id       as \"tagId\",\n" +
            "       tag.name_ua      as \"tagNameUa\",\n" +
            "       tag.name         as \"tagName\",\n" +
            "       author.author_id as \"authorId\",\n" +
            "       author.name_ua   as \"authorNameUa\",\n" +
            "       author.name      as \"authorName\",\n" +
            "       shelf.shelf_id   as \"shelf_id\"\n" +
            "from (select * from book where available = true order by book_id limit ? offset ?) as book\n" +
            "         left join tag on book.tag_id = tag.tag_id\n" +
            "         left join book_author on book.book_id = book_author.book_id\n" +
            "         left join author on book_author.author_id = author.author_id\n" +
            "         left join shelf on book.book_id = shelf.book_id";
    String SQL_FIND_BY_FILTER = "select book.book_id     as \"bookId\",\n" +
            "       book.name        as \"bookName\",\n" +
            "       book.name_ua     as \"bookNameUa\",\n" +
            "       book.available,\n" +
            "       tag.tag_id       as \"tagId\",\n" +
            "       tag.name_ua      as \"tagNameUa\",\n" +
            "       tag.name         as \"tagName\",\n" +
            "       author.author_id as \"authorId\",\n" +
            "       author.name_ua   as \"authorNameUa\",\n" +
            "       author.name      as \"authorName\",\n" +
            "       shelf.shelf_id   as \"shelf_id\"\n" +
            "from (select * from book where available = true " +
            " and book.book_id in\n" +
            "      (select book_id\n" +
            "       from book_author\n" +
            "       where author_id in (select author_id\n" +
            "                           from author\n" +
            "                           where name in (SELECT * FROM unnest(?))))\n" +
            "  and book.book_id in (select book_id\n" +
            "                       from book\n" +
            "                       where tag_id in (select tag_id\n" +
            "                                        from tag\n" +
            "                                        where name in (SELECT * FROM unnest(?))))\n" +
            "  and book.name like ?" +
            " order by book_id limit ? offset ?) as book\n" +
            "         left join tag on book.tag_id = tag.tag_id\n" +
            "         left join book_author on book.book_id = book_author.book_id\n" +
            "         left join author on book_author.author_id = author.author_id\n" +
            "         left join shelf on book.book_id = shelf.book_id";

    String SQL_FIND_BY_FILTER_UA = "select book.book_id     as \"bookId\",\n" +
            "       book.name        as \"bookName\",\n" +
            "       book.name_ua     as \"bookNameUa\",\n" +
            "       book.available,\n" +
            "       tag.tag_id       as \"tagId\",\n" +
            "       tag.name_ua      as \"tagNameUa\",\n" +
            "       tag.name         as \"tagName\",\n" +
            "       author.author_id as \"authorId\",\n" +
            "       author.name_ua   as \"authorNameUa\",\n" +
            "       author.name      as \"authorName\",\n" +
            "       shelf.shelf_id   as \"shelf_id\"\n" +
            "from (select * from book where available = true " +
            " and book.book_id in\n" +
            "      (select book_id\n" +
            "       from book_author\n" +
            "       where author_id in (select author_id\n" +
            "                           from author\n" +
            "                           where name_ua in (SELECT * FROM unnest(?))))\n" +
            "  and book.book_id in (select book_id\n" +
            "                       from book\n" +
            "                       where tag_id in (select tag_id\n" +
            "                                        from tag\n" +
            "                                        where name_ua in (SELECT * FROM unnest(?))))\n" +
            "  and book.name_ua like ?" +
            " order by book_id limit ? offset ?) as book\n" +
            "         left join tag on book.tag_id = tag.tag_id\n" +
            "         left join book_author on book.book_id = book_author.book_id\n" +
            "         left join author on book_author.author_id = author.author_id\n" +
            "         left join shelf on book.book_id = shelf.book_id";

    Optional<Book> findByName(String name);

    Optional<Book> findByNameUa(String nameUa);

    List<Book> getBooksByFilter(String partOfName, String[] authors, String[] tags, Pageable pageable);

    List<Book> getBooksByFilterUa(String partOfName, String[] authors, String[] tags, Pageable pageable);

    List<Book> findAllByAvailableIsTrue(Pageable pageable);
}
